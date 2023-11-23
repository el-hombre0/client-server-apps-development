package com.example.prac5_ser.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.prac5_ser.model.Guest;
import com.example.prac5_ser.repository.GuestRepository;

@RestController
@RequestMapping("/api/v1")
public class GuestController {


    @Autowired
    private GuestRepository guestRepository;

    //get all guests

    @GetMapping("/guests")
    public List<Guest> getAllGuests(){
        return guestRepository.findAll();
    }

    //create guest
    @PostMapping("/guests")
    public Guest createGuest(@RequestBody Guest guest){
        return guestRepository.save(guest);
    }
}

