package com.voting.votingsystem.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

import com.voting.votingsystem.service.VotingService;
import com.voting.votingsystem.entity.User;
import com.voting.votingsystem.entity.Candidate;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class VotingController {

    @Autowired
    private VotingService service;

    @PostMapping("/register")
    public String register(@RequestBody User user){
        return service.register(user);
    }

    @PostMapping("/login")
    public User login(@RequestBody User user){
        return service.login(user.getEmail(), user.getPassword());
    }

    @GetMapping("/candidates")
    public List<Candidate> getCandidates(){
        return service.getCandidates();
    }

    @PostMapping("/vote")
    public String vote(@RequestParam Long userId,
                       @RequestParam Long candidateId){
        return service.vote(userId, candidateId);
    }
}