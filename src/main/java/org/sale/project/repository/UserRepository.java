package org.sale.project.repository;

import org.sale.project.entity.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.sale.project.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String>, JpaSpecificationExecutor<User> {
    Page<User> findAll(Pageable pageable);

    Page<User> findAll(Specification<User> spec, Pageable page);

    Optional<User> findByAccount(Account account);

}
