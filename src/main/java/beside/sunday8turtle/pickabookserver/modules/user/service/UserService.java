package beside.sunday8turtle.pickabookserver.modules.user.service;

import beside.sunday8turtle.pickabookserver.modules.user.PrincipalDetails;
import beside.sunday8turtle.pickabookserver.modules.user.domain.User;
import beside.sunday8turtle.pickabookserver.modules.user.dto.UserSignUpRequestDTO;
import beside.sunday8turtle.pickabookserver.modules.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@Transactional
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User registerUser(UserSignUpRequestDTO request) {
        return userRepository.save(User.of(request.getEmail(), getEncodePassword(request.getPassword()), request.getNickname(), "USER"));
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    private String getEncodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    public Optional<User> getUserByEmailAndPassword(String email, String rawPassword) {
        return userRepository.findByEmail(email).filter(user -> user.matchesPassword(rawPassword, passwordEncoder));
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).orElse(null);
        return new PrincipalDetails(user);
    }
}
