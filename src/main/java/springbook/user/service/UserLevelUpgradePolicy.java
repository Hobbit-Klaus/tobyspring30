package springbook.user.service;

import springbook.user.domain.User;

/**
 * Created by Hobbit-Klaus on 2017-04-24.
 */
public interface UserLevelUpgradePolicy {
    boolean canUpgradeLevel(User user);
    void upgradeLevel(User user);
}
