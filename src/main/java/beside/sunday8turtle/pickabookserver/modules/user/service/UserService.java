package beside.sunday8turtle.pickabookserver.modules.user.service;

import beside.sunday8turtle.pickabookserver.modules.user.domain.User;
import beside.sunday8turtle.pickabookserver.modules.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void createUser(User user) {
        user.setPassword(getEncodePassword(user.getPassword()));
        user.setRoles("USER");
        userRepository.save(user);
    }

    private String getEncodePassword(String password) {
        return passwordEncoder.encode(password);
    }
}
