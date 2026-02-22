package com.voting.votingsystem.service;

import com.voting.votingsystem.dto.AdminStats;
import com.voting.votingsystem.dto.LoginResponse;
import com.voting.votingsystem.dto.UserInfo;
import com.voting.votingsystem.entity.Candidate;
import com.voting.votingsystem.entity.User;
import com.voting.votingsystem.repository.CandidateRepository;
import com.voting.votingsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
public class VotingService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CandidateRepository candidateRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);

    // ─────────────────────────────────────────────
    // AUTH
    // ─────────────────────────────────────────────

    public String register(User user) {
        if (user.getName() == null || user.getName().isBlank())
            return "ERROR: Name is required.";
        if (user.getEmail() == null || user.getEmail().isBlank())
            return "ERROR: Email is required.";
        if (user.getPassword() == null || user.getPassword().length() < 6)
            return "ERROR: Password must be at least 6 characters.";
        if (userRepository.existsByEmail(user.getEmail().toLowerCase().trim()))
            return "ERROR: An account with this email already exists.";

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEmail(user.getEmail().toLowerCase().trim());
        user.setName(user.getName().trim());
        user.setRole("USER");
        user.setHasVoted(false);
        userRepository.save(user);
        return "Registration successful! You can now log in.";
    }

    public LoginResponse login(String email, String password) {
        if (email == null || email.isBlank() || password == null || password.isBlank())
            throw new RuntimeException("Email and password are required.");

        User user = userRepository
                .findByEmail(email.toLowerCase().trim())
                .orElseThrow(() -> new RuntimeException("No account found with this email."));

        if (!passwordEncoder.matches(password, user.getPassword()))
            throw new RuntimeException("Incorrect password.");

        return new LoginResponse(user.getId(), user.getName(), user.getEmail(), user.getRole());
    }

    // ─────────────────────────────────────────────
    // CANDIDATES
    // ─────────────────────────────────────────────

    public List<Candidate> getCandidates() {
        return candidateRepository.findAll();
    }

    // ─────────────────────────────────────────────
    // VOTING
    // ─────────────────────────────────────────────

    @Transactional
    public String vote(Long userId, Long candidateId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found."));

        if (user.isHasVoted())
            return "ERROR: You have already cast your vote.";

        Candidate candidate = candidateRepository.findById(candidateId)
                .orElseThrow(() -> new RuntimeException("Candidate not found."));

        candidate.setVoteCount(candidate.getVoteCount() + 1);
        user.setHasVoted(true);
        candidateRepository.save(candidate);
        userRepository.save(user);
        return "Vote cast successfully for " + candidate.getName() + " (" + candidate.getParty() + ")!";
    }

    // ─────────────────────────────────────────────
    // ADMIN
    // ─────────────────────────────────────────────

    /** Overall election statistics for the admin dashboard. */
    public AdminStats getStats() {
        List<Candidate> candidates = candidateRepository.findAll();
        List<User> users = userRepository.findAll();

        long totalUsers = users.stream().filter(u -> "USER".equals(u.getRole())).count();
        long totalVoters = users.stream().filter(User::isHasVoted).count();
        long totalVotes = candidates.stream().mapToLong(Candidate::getVoteCount).sum();
        long totalCandidates = candidates.size();

        Candidate leader = candidates.stream()
                .max(Comparator.comparingInt(Candidate::getVoteCount))
                .orElse(null);

        double turnout = totalUsers > 0
                ? Math.round((totalVoters * 1000.0 / totalUsers)) / 10.0
                : 0.0;

        return new AdminStats(
                totalUsers, totalVoters, totalCandidates, totalVotes,
                leader != null ? leader.getName() : "—",
                leader != null ? leader.getParty() : "—",
                turnout);
    }

    /** All registered users (no password) for admin user management. */
    public List<UserInfo> getAllUsers() {
        return userRepository.findAll().stream()
                .map(u -> new UserInfo(u.getId(), u.getName(), u.getEmail(), u.getRole(), u.isHasVoted()))
                .toList();
    }

    /** Add a new candidate. */
    public Candidate addCandidate(String name, String party) {
        if (name == null || name.isBlank())
            throw new RuntimeException("Candidate name is required.");
        if (party == null || party.isBlank())
            throw new RuntimeException("Party name is required.");

        Candidate c = new Candidate();
        c.setName(name.trim());
        c.setParty(party.trim());
        c.setVoteCount(0);
        return candidateRepository.save(c);
    }

    /** Delete a candidate by ID. */
    public void deleteCandidate(Long id) {
        if (!candidateRepository.existsById(id))
            throw new RuntimeException("Candidate not found.");
        candidateRepository.deleteById(id);
    }

    /** Reset all vote counts and clear every user's voted flag. */
    @Transactional
    public String resetAllVotes() {
        candidateRepository.findAll().forEach(c -> {
            c.setVoteCount(0);
            candidateRepository.save(c);
        });
        userRepository.findAll().forEach(u -> {
            if ("USER".equals(u.getRole())) {
                u.setHasVoted(false);
                userRepository.save(u);
            }
        });
        return "All votes have been reset successfully.";
    }
}
