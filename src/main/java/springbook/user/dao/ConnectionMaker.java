package springbook.user.dao;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Hobbit-Klaus on 2017-04-17.
 */
public interface ConnectionMaker {
    public Connection makeConnection() throws ClassNotFoundException, SQLException;
}
