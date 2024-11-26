package org.sale.project.repository;

import org.sale.project.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository  extends JpaRepository<Role, String> {
    Role findByName(String name);
}