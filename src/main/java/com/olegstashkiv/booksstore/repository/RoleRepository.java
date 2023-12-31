package com.olegstashkiv.booksstore.repository;

import com.olegstashkiv.booksstore.model.Role;
import com.olegstashkiv.booksstore.model.enums.RoleName;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(RoleName name);
}
