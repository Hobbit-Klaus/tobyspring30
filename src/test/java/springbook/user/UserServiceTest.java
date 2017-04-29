package springbook.user;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.PlatformTransactionManager;
import springbook.user.dao.UserDao;
import springbook.user.domain.Level;
import springbook.user.domain.User;
import springbook.user.service.UserService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.fail;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static springbook.user.service.UserService.MIN_LOGCOUNT_FOR_SILVER;
import static springbook.user.service.UserService.MIN_RECOMMEND_FOR_GOLD;

/**
 * Created by Hobbit-Klaus on 2017-04-24.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/test-applicationContext.xml")
public class UserServiceTest {
    @Autowired
    private UserDao userDao;
    @Autowired
    private MailSender mailSender;
    @Autowired
    private UserService userService;
    @Autowired
    private PlatformTransactionManager transactionManager;

    private List<User> users;

    @Before
    public void setUp() {
        users = Arrays.asList(
                new User("a", "AAA", "1234", "aaa@aaa.com", Level.BASIC, MIN_LOGCOUNT_FOR_SILVER - 1, 0),
                new User("b", "BBB", "1234", "bbb@bbb.com", Level.BASIC, MIN_LOGCOUNT_FOR_SILVER, 0),
                new User("c", "CCC", "1234", "ccc@ccc.com", Level.SILVER, 60, MIN_RECOMMEND_FOR_GOLD - 1),
                new User("d", "DDD", "1234", "ddd@ddd.com", Level.SILVER, 60, MIN_RECOMMEND_FOR_GOLD),
                new User("e", "EEE", "1234", "eee@eee.com", Level.GOLD, 100, Integer.MAX_VALUE)
        );
    }

    @Test
    public void addWithOutLevel() {
        userDao.deleteAll();

        User user = users.get(0);
        user.setLevel(null);
        userService.add(user);

        assertThat(userDao.get(user.getId()).getLevel(), is(Level.BASIC));
    }

    @Test
    public void addWithLevel() {
        userDao.deleteAll();

        User user = users.get(4);
        userService.add(user);

        assertThat(userDao.get(user.getId()).getLevel(), is(Level.GOLD));
    }

    @Test
    @DirtiesContext
    public void upgradeLevels() throws Exception {
        userDao.deleteAll();
        for (User user : users) {
            userDao.add(user);
        }

        MockMailSender mockMailSender = new MockMailSender();
        userService.setMailSender(mockMailSender);

        userService.upgradeLevels();

        checkLevels(users.get(0), false);
        checkLevels(users.get(1), true);
        checkLevels(users.get(2), false);
        checkLevels(users.get(3), true);
        checkLevels(users.get(4), false);

        List<String> request = mockMailSender.getRequests();
        assertThat(request.size(), is(2));
        assertThat(request.get(0), is(users.get(1).getEmail()));
        assertThat(request.get(1), is(users.get(3).getEmail()));
    }

    private void checkLevels(User user, boolean upgraded) {
        User userUpdate = userDao.get(user.getId());
        if (upgraded) {
            assertThat(userUpdate.getLevel(), is(user.getLevel().nextLevel()));
        } else {
            assertThat(userUpdate.getLevel(), is(user.getLevel()));
        }
    }

    @Test
    public void upgradeAllOrNothing() throws Exception {
        UserService testUserService = new TestUserService(users.get(3).getId());
        testUserService.setUserDao(userDao);
        testUserService.setMailSender(mailSender);
        testUserService.setTransactionManager(transactionManager);

        userDao.deleteAll();
        for (User user : users) userDao.add(user);

        try {
            testUserService.upgradeLevels();
            fail("TestUserServiceException expected");
        } catch (TestUserServiceException e) {

        }

        checkLevels(users.get(1), false);
    }

    static class TestUserService extends UserService {
        private String id;

        private TestUserService(String id) {
            this.id = id;
        }

        @Override
        protected void upgradeLevel(User user) {
            if (user.getId().equals(this.id)) throw new TestUserServiceException();
            super.upgradeLevel(user);
        }
    }

    static class TestUserServiceException extends RuntimeException {
    }

    static class MockMailSender implements MailSender {
        private List<String> requests = new ArrayList<>();

        public List<String> getRequests() {
            return requests;
        }

        @Override
        public void send(SimpleMailMessage simpleMessage) throws MailException {
            requests.add(simpleMessage.getTo()[0]);
        }

        @Override
        public void send(SimpleMailMessage[] simpleMessages) throws MailException {

        }
    }
}
