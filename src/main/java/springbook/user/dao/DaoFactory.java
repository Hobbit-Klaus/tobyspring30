package springbook.user.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import javax.sql.DataSource;

/**
 * Created by Hobbit-Klaus on 2017-04-17.
 */
@Configuration
public class DaoFactory {
    @Bean
    public UserDao userDao() {
        UserDao dao = new UserDao();
//        dao.setDataSource(anotherDataSource());
//        dao.setConnectionMaker(getConnectionMaker());
        return dao;
    }

    @Bean
    public DataSource anotherDataSource() {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();

        dataSource.setDriverClass(com.mysql.jdbc.Driver.class);
        dataSource.setUrl("jdbc:mysql://localhost/springbook_another");
        dataSource.setUsername("root");
        dataSource.setPassword("password");

        return dataSource;
    }

    @Bean
    public DataSource dataSource() {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();

        dataSource.setDriverClass(com.mysql.jdbc.Driver.class);
        dataSource.setUrl("jdbc:mysql://localhost/springbook");
        dataSource.setUsername("root");
        dataSource.setPassword("password");

        return dataSource;
    }

//    @Bean
//    public ConnectionMaker getConnectionMaker() {
//        return new AnotherSimpleConnectionMaker();
//    }
}
