package com.example.testSpring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.testSpring.model.Amartek;

@Repository
public interface AmartekRepository extends JpaRepository<Amartek, Integer> {

}