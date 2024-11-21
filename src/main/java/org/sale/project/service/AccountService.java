package org.sale.project.service;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.sale.project.entity.Account;
import org.sale.project.repository.AccountRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountService {

    AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    public void saveAccount(Account account) {

        accountRepository.save(account);
    }

    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    public Account findById(String id) {
        return accountRepository.findById(id).orElse(null);
    }

    public Account findByEmail(String email) {
        Optional<Account> accountOptional = accountRepository.findByEmail(email);

        return accountOptional.orElse(null);

    }

    public Page<Account> findAll(Pageable pageable) {
        return accountRepository.findAll(pageable);
    }


    public int countAccount(){
        return accountRepository.findAll().size();
    }

    public String forgotPassword(String email){
        Account account = findByEmail(email);
        if(account != null){
            Random random = new Random();
            int length = 6;
            StringBuilder password = new StringBuilder();

            for (int i = 0; i < length; i++) {
                password.append(random.nextInt(10));
            }
            System.out.println("password : " + password);

            account.setPassword(passwordEncoder.encode(password.toString()));
            accountRepository.save(account);
            return password.toString();

        }
        return null;
    }


}
