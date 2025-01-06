package com.project2.sec.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project2.sec.Model.User;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long>{
	
	 Optional<User> findByUsername(String username);

}
