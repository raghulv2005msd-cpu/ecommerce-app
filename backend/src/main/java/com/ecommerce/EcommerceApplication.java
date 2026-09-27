package com.ecommerce;

import com.ecommerce.model.Role;
import com.ecommerce.model.User;
import com.ecommerce.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@SpringBootApplication
public class EcommerceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EcommerceApplication.class, args);
    }

    /**
     * Seeds a default admin user (admin@ecommerce.com / Admin123!) on first boot,
     * so you can log in immediately and start managing products.
     */
    @Bean
    CommandLineRunner seedAdmin(UserRepository userRepository, PasswordEncoder encoder) {
        return args -> {
            if (userRepository.findByEmail("admin@ecommerce.com").isEmpty()) {
                User admin = User.builder()
                        .fullName("Store Admin")
                        .email("admin@ecommerce.com")
                        .password(encoder.encode("Admin123!"))
                        .roles(Set.of(Role.ROLE_ADMIN, Role.ROLE_USER))
                        .build();
                userRepository.save(admin);
            }
        };
    }
}
