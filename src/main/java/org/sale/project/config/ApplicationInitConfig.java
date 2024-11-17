package org.sale.project.config;


import org.sale.project.entity.Account;
import org.sale.project.entity.Role;
import org.sale.project.entity.User;
import org.sale.project.repository.AccountRepository;
import org.sale.project.repository.RoleRepository;
import org.sale.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class  ApplicationInitConfig {
    @Autowired
    PasswordEncoder passwordEncoder;

    @Bean
    ApplicationRunner applicationRunner(AccountRepository accountRepository, RoleRepository roleRepository) {
        return args -> {
            if(roleRepository.findByName("USER") == null || roleRepository.findByName("ADMIN") == null) {
                Role roleAdmin = Role.builder()
                        .name("ADMIN")
                        .build();
                Role roleUser = Role.builder()
                        .name("USER")
                        .build();

                roleRepository.save(roleAdmin);
                roleRepository.save(roleUser);
            }
            if(accountRepository.findByEmail("admin@gmail.com").isEmpty()) {
                Account account = Account.builder()
                        .email("admin@gmail.com")
                        .password(passwordEncoder.encode("admin"))
                        .role(roleRepository.findByName("ADMIN"))
                        .build();

                accountRepository.save(account);
            }

        };
    }
}
