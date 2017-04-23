package springbook.user.dao;

import springbook.user.domain.User;

import java.util.List;

/**
 * Created by Hobbit-Klaus on 2017-04-23.
 */
public interface UserDao {
    void add(User user);
    User get(String id);
    List<User> getAll();
    void deleteAll();
    int getCount();
    void update(User user1);
}
