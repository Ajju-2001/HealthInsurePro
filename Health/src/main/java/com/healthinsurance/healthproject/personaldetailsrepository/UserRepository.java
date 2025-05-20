package com.healthinsurance.healthproject.personaldetailsrepository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.healthinsurance.healthproject.personalentities.Users;

public interface UserRepository extends JpaRepository<Users, Integer>{
	Optional<Users> findByUsername(String username);
}
