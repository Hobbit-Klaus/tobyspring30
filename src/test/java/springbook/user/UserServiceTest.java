package springbook.user;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import springbook.user.dao.UserDao;
import springbook.user.domain.Level;
import springbook.user.domain.User;
import springbook.user.service.UserService;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by Hobbit-Klaus on 2017-04-24.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/test-applicationContext.xml")
public class UserServiceTest {
    @Autowired
    UserService userService;

    @Autowired
    UserDao userDao;

    private List<User> users;

    @Before
    public void setUp() {
        users = Arrays.asList(
                new User("a", "AAA", "1234", Level.BASIC, 49, 0),
                new User("b", "BBB", "1234", Level.BASIC, 50, 0),
                new User("c", "CCC", "1234", Level.SILVER, 60, 29),
                new User("d", "DDD", "1234", Level.SILVER, 60, 30),
                new User("e", "EEE", "1234", Level.GOLD, 100, 100)
        );
    }

    @Test
    public void upgradeLevels() {
        userDao.deleteAll();
        for (User user : users) {
            userDao.add(user);
        }

        userService.upgradeLevels();

        checkLevels(users.get(0), Level.BASIC);
        checkLevels(users.get(1), Level.SILVER);
        checkLevels(users.get(2), Level.SILVER);
        checkLevels(users.get(3), Level.GOLD);
        checkLevels(users.get(4), Level.GOLD);
    }

    private void checkLevels(User user, Level expectedLevel) {
        User userUpdate = userDao.get(user.getId());
        assertThat(userUpdate.getLevel(), is(expectedLevel));
    }
}
