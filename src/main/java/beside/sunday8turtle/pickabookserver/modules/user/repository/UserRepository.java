package beside.sunday8turtle.pickabookserver.modules.user.repository;

import beside.sunday8turtle.pickabookserver.modules.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    public User findByUsername(String username);
}