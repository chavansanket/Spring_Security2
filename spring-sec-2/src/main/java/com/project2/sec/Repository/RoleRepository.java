package com.project2.sec.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project2.sec.Model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

}
