package springbook.user.dao;

/**
 * Created by Hobbit-Klaus on 2017-04-17.
 */
public class DaoFactory {
    public UserDao userDao() {
        return new UserDao(getConnectionMaker());
    }

    private ConnectionMaker getConnectionMaker() {
        return new AnotherSimpleConnectionMaker();
    }
}
