package org.sale.project.service;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.sale.project.entity.Account;
import org.sale.project.repository.AccountRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountService {

    AccountRepository accountRepository;

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

        return accountOptional.isPresent() ? accountOptional.get() :  null;

    }

    public Page<Account> findAll(Pageable pageable) {
        return accountRepository.findAll(pageable);
    }


    public int countAccount(){
        return accountRepository.findAll().size();
    }
}
