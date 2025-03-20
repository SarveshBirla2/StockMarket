package com.example.demo.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.models.Share;


public interface ShareRepository extends JpaRepository<Share, String> {
}
