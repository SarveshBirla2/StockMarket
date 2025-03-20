package com.example.demo.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.model.User;

import jakarta.transaction.Transactional;

public interface UserDao extends JpaRepository<User ,Integer> {
		User findByName(String name);
		
		@Modifying
	    @Transactional
	    @Query("UPDATE User u SET u.password = :password WHERE u.name = :name")
	    void updatePassword(@Param("name") String name, @Param("password") String password);
		
		
		@Modifying
	    @Transactional
	    @Query("UPDATE User u SET u.balance = :balance WHERE u.name = :name")
	    void updateBalance(@Param("name") String name, @Param("balance") Double balance);
}