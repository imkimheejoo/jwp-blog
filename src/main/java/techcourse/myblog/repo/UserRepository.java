package techcourse.myblog.repo;

import org.springframework.data.repository.CrudRepository;
import techcourse.myblog.user.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);
}
