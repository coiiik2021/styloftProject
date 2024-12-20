package org.sale.project.service;

import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import lombok.experimental.FieldDefaults;
import org.sale.project.entity.Account;
import org.sale.project.entity.User;
import org.sale.project.mapper.UserMapper;
import org.sale.project.repository.AccountRepository;
import org.sale.project.repository.RoleRepository;
import org.sale.project.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PACKAGE, makeFinal = true)
public class UserService {

    UserRepository userRepository;
    UserMapper userMapper;
    AccountRepository accountRepository;

    private final RoleRepository roleRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(String id) {
        return userRepository.findById(id).orElse(null);
    }

    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public Page<User> findAll(Specification<User> specification, Pageable pageable) {
        return userRepository.findAll(specification, pageable);
    }

    public User saveUser(User user) {
        if(userRepository.findByAccount(user.getAccount()) != null){
            user.setImage("/default.png");
            user.getAccount().setRole(roleRepository.findByName("USER"));
            accountRepository.save(user.getAccount());
            return userRepository.save(user);
        }
        return null;
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByAccount(accountRepository.findByEmail(email).get());
    }

    public int countUser() {
        return userRepository.findAll().size();
    }

    public User findUserByEmail(String email) {
        Optional<User> userOptional = userRepository.findByAccount(accountRepository.findByEmail(email).get());

        User user = userOptional.orElse(null);

            if(user == null && !email.equals("admin@gmail.com")){
                Account account = accountRepository.findByEmail(email).get();

                user = saveUser(User.builder().sex(-1).account(accountRepository.findByEmail(email).get()).build());
                account.setUser(user);
            }


        return user;
    }

    public void updateUser(String email, User userUpdate) {
        Optional<User> userOptional = userRepository.findByAccount(accountRepository.findByEmail(email).get());

        User user = new User();
        if (userOptional.isPresent()) {
            user = userOptional.get();
        }
        user.setName(userUpdate.getName());
        user.setPhoneNumber(userUpdate.getPhoneNumber());
        user.setBirthDay(userUpdate.getBirthDay());
        user.setSex(userUpdate.getSex());
        System.out.println(user.getSex());

        if(userUpdate.getImage() != null){
            user.setImage(userUpdate.getImage());
        }

        Account account = accountRepository.findByEmail(email).get();

        user.setAccount(account);

        user = userRepository.save(user);

        account.setUser(user);

        accountRepository.save(user.getAccount());
    }


    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }

    public void updateUserAddress(String email, String newAddress) {
        // Tìm người dùng dựa trên email
        Optional<User> userOptional = userRepository.findByAccount(accountRepository.findByEmail(email).get());

        // Kiểm tra xem người dùng có tồn tại hay không
        if (userOptional.isPresent()) {
            User user = userOptional.get();

            // Cập nhật thuộc tính address
            user.setAddress(newAddress);

            // Lưu lại đối tượng user đã cập nhật
            userRepository.save(user);
        } else {
            // Nếu người dùng không tồn tại, bạn có thể xử lý lỗi ở đây, ví dụ: ném một exception
            throw new RuntimeException("User not found with email: " + email);
        }
    }


}
