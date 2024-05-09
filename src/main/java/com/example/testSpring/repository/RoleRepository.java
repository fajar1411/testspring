package com.example.testSpring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.testSpring.model.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    @Query(value = "SELECT r from Role r WHERE r.name = ?1  ")
    public Role findRole(String name);
}
