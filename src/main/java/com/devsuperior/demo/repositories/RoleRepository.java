package com.devsuperior.demo.repositories;

import com.devsuperior.demo.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
