package zw.co.munyasys.users.dao;

import zw.co.munyasys.common.jpa.BaseDao;
import zw.co.munyasys.users.model.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends BaseDao<User> {

    Optional<User> findByUsername(String username);

    Optional<User> findByUsernameAndEnabledIsTrue(String username);

    Optional<User> findByEmail(String email);

    boolean existsByUsernameAndIdIsNot(String username, UUID id);

    boolean existsByEmail(String email);
}
