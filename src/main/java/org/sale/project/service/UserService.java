package org.sale.project.service;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import lombok.experimental.FieldDefaults;
import org.sale.project.entity.User;
import org.sale.project.mapper.UserMapper;
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
        if(userRepository.findByEmail(user.getEmail()) != null){
            user.setRole(roleRepository.findByName("USER"));
            return userRepository.save(user);
        }
        return null;
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public int countUser() {
        return userRepository.findAll().size();
    }

    public User findUserByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.orElse(null);
    }

    public void updateUser(String email, User userUpdate) {

        Optional<User> userOptional = userRepository.findByEmail(email);
        User user = new User();
        if (userOptional.isPresent()) {
            user = userOptional.get();
        } else {
            return;
        }
        userMapper.updateUser(user, userUpdate);
        userRepository.save(user);
    }


    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }

}
