package com.example.prac5_ser.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.prac5_ser.model.Guest;

@Repository
public interface GuestRepository extends JpaRepository<Guest, Long>{

}
