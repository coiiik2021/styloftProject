package org.sale.project.service;
import jakarta.mail.MessagingException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.RandomStringUtils;
import org.sale.project.entity.Account;
import org.sale.project.repository.AccountRepository;
import org.sale.project.service.email.EmailService;
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
    private final EmailService emailService;

    public void saveAccount(Account account) {

        accountRepository.save(account);
    }
    public void updateAccount(Account account) {
        Account oldAccount = accountRepository.findById(account.getId()).get();
        if (oldAccount != null) {
            oldAccount.setPassword(account.getPassword());
            accountRepository.save(oldAccount);
        }
    }

    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    public List<Account> findAllByPassword(String password) {
        password = passwordEncoder.encode(password);
        return accountRepository.findAllByPassword(password);
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

    public String forgotPassword(String email) throws MessagingException {
        Account account = findByEmail(email);
        if(account != null){
            String password = RandomStringUtils.randomAlphanumeric(12);
            account.setPassword(passwordEncoder.encode(password.toString()));
            accountRepository.save(account);
            String content =emailService.MailFoget(email,password);
            emailService.sendHtmlEmail(email,"THAY ĐỔI MẬT KHẨU",content);
            return password.toString();

        }
        return null;
    }


}