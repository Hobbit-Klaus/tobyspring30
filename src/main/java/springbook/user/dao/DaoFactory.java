package springbook.user.dao;

/**
 * Created by Hobbit-Klaus on 2017-04-17.
 */
public class DaoFactory {
    public UserDao userDao() {
        ConnectionMaker connectionMaker = new AnotherSimpleConnectionMaker();
        return new UserDao(connectionMaker);
    }
}
