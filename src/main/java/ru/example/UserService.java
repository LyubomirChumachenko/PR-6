package ru.example;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new org.springframework.security.core.userdetails.User(
            user.getUsername(),
            user.getPassword(),
            user.getAuthorities()
        );
    }

    public void registerUser(String username, String password, String roleName) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("Пользователь с таким именем уже существует");
        }
    
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
    
        Role role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new RuntimeException("Роль не найдена"));
    
        user.getRoles().add(role);
    
        userRepository.save(user);
    }

    public void createAdminIfNotExists(String username, String password) {
        if (!userRepository.findByUsername(username).isPresent()) {
            Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                                 .orElseGet(() -> roleRepository.save(new Role("ROLE_ADMIN")));
            User admin = new User(username, password);
            admin.addRole(adminRole);
            userRepository.save(admin);
        }
    }
    
    public Optional<User> findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
