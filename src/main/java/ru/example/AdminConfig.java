package ru.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import javax.annotation.PostConstruct;
import java.util.Optional;

@Configuration
public class AdminConfig {

    private final RoleRepository roleRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
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
        // Создаем роли с префиксом ROLE_ для корректной работы с Spring Security
        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseGet(() -> roleRepository.save(new Role("ROLE_USER")));

        Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                .orElseGet(() -> roleRepository.save(new Role("ROLE_ADMIN")));

        // Проверяем существование администратора и обновляем его при необходимости
        Optional<User> existingAdmin = userService.findUserByUsername("admin");
        
        if (existingAdmin.isPresent()) {
            User admin = existingAdmin.get();
            
            // Проверяем, имеет ли администратор роль ADMIN
            boolean hasAdminRole = admin.getRoles().stream()
                .anyMatch(role -> role.getName().equals("ROLE_ADMIN"));
            
            if (!hasAdminRole) {
                admin.addRole(adminRole);
            }
            
            // Проверяем, совпадает ли текущий пароль с установленным
            if (!passwordEncoder.matches(adminPassword, admin.getPassword())) {
                admin.setPassword(passwordEncoder.encode(adminPassword));
            }
            
            userService.saveUser(admin);
        } else {
            // Создаем нового администратора, если он не существует
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode(adminPassword));
            admin.addRole(adminRole);
            // Опционально можно добавить роль USER администратору
            admin.addRole(userRole);
            userService.saveUser(admin);
        }
    }
}