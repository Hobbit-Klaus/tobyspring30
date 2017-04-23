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
import springbook.user.service.UserLevelUpgradeNormal;
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
                new User("a", "AAA", "1234", Level.BASIC, UserLevelUpgradeNormal.MIN_LOGCOUNT_FOR_SILVER - 1, 0),
                new User("b", "BBB", "1234", Level.BASIC, UserLevelUpgradeNormal.MIN_LOGCOUNT_FOR_SILVER, 0),
                new User("c", "CCC", "1234", Level.SILVER, 60, UserLevelUpgradeNormal.MIN_RECOMMEND_FOR_GOLD - 1),
                new User("d", "DDD", "1234", Level.SILVER, 60, UserLevelUpgradeNormal.MIN_RECOMMEND_FOR_GOLD),
                new User("e", "EEE", "1234", Level.GOLD, 100, Integer.MAX_VALUE)
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
    public void upgradeLevels() {
        userDao.deleteAll();
        for (User user : users) {
            userDao.add(user);
        }

        userService.upgradeLevels();

        checkLevels(users.get(0), false);
        checkLevels(users.get(1), true);
        checkLevels(users.get(2), false);
        checkLevels(users.get(3), true);
        checkLevels(users.get(4), false);
    }

    private void checkLevels(User user, boolean upgraded) {
        User userUpdate = userDao.get(user.getId());
        if (upgraded) {
            assertThat(userUpdate.getLevel(), is(user.getLevel().nextLevel()));
        } else {
            assertThat(userUpdate.getLevel(), is(user.getLevel()));
        }
    }
}
