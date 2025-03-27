package ru.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import javax.annotation.PostConstruct;

@Configuration
public class AdminConfig {

    private final RoleRepository roleRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder; // Внедрение существующего бина
    private final String adminPassword;

    @Autowired
    public AdminConfig(RoleRepository roleRepository, UserService userService, PasswordEncoder passwordEncoder,
                       @Value("${app.admin.password}") String adminPassword) {
        this.roleRepository = roleRepository;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.adminPassword = adminPassword;
    }

    @PostConstruct 
    public void init() {
        Role userRole = roleRepository.findByName("USER")
                .orElseGet(() -> roleRepository.save(new Role("USER")));

        Role adminRole = roleRepository.findByName("ADMIN")
                .orElseGet(() -> roleRepository.save(new Role("ADMIN")));

        userService.findUserByUsername("admin").ifPresentOrElse(
                user -> { /*ToDo maybe update password if doesn't match*/ },
                () -> {
                        User admin = new User();
                        admin.setUsername("admin");
                        admin.setPassword(passwordEncoder.encode(adminPassword)); // Использование внедренного PasswordEncoder
                        admin.addRole(adminRole);
                        userService.saveUser(admin);
                }
        );


    }
}