package springbook.user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by Hobbit-Klaus on 2017-04-19.
 */
public class DeleteAllStatement implements  StatementStrategy {
    @Override
    public PreparedStatement makePreparedStatement(Connection c) throws SQLException {
        return c.prepareStatement("delete from users");
    }
}
