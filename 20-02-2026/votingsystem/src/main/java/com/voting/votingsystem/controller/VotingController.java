package com.voting.votingsystem.controller;

import com.voting.votingsystem.dto.AdminStats;
import com.voting.votingsystem.dto.LoginResponse;
import com.voting.votingsystem.dto.UserInfo;
import com.voting.votingsystem.entity.Candidate;
import com.voting.votingsystem.service.VotingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class VotingController {

    @Autowired
    private VotingService service;

    // ─────────────────────────────────────────────
    // AUTH
    // ─────────────────────────────────────────────

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody com.voting.votingsystem.entity.User user) {
        try {
            String result = service.register(user);
            if (result.startsWith("ERROR:")) {
                return ResponseEntity.badRequest()
                        .body(Map.of("message", result.substring(7).trim()));
            }
            return ResponseEntity.ok(Map.of("message", result));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("message", "Unexpected error during registration."));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody com.voting.votingsystem.entity.User body) {
        try {
            LoginResponse response = service.login(body.getEmail(), body.getPassword());
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(401)
                    .body(Map.of("message", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("message", "Unexpected error during login."));
        }
    }

    // ─────────────────────────────────────────────
    // CANDIDATES (public)
    // ─────────────────────────────────────────────

    @GetMapping("/candidates")
    public ResponseEntity<List<Candidate>> getCandidates() {
        return ResponseEntity.ok(service.getCandidates());
    }

    // ─────────────────────────────────────────────
    // VOTING
    // ─────────────────────────────────────────────

    @PostMapping("/vote")
    public ResponseEntity<?> vote(@RequestParam Long userId,
            @RequestParam Long candidateId) {
        try {
            String result = service.vote(userId, candidateId);
            if (result.startsWith("ERROR:")) {
                return ResponseEntity.badRequest()
                        .body(Map.of("message", result.substring(7).trim()));
            }
            return ResponseEntity.ok(Map.of("message", result));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("message", "Unexpected error during voting."));
        }
    }

    // ─────────────────────────────────────────────
    // ADMIN ENDPOINTS
    // ─────────────────────────────────────────────

    /** GET /api/admin/stats — election summary statistics */
    @GetMapping("/admin/stats")
    public ResponseEntity<AdminStats> getAdminStats() {
        return ResponseEntity.ok(service.getStats());
    }

    /** GET /api/admin/users — all registered users (no passwords) */
    @GetMapping("/admin/users")
    public ResponseEntity<List<UserInfo>> getUsers() {
        return ResponseEntity.ok(service.getAllUsers());
    }

    /** POST /api/admin/candidates — add a new candidate */
    @PostMapping("/admin/candidates")
    public ResponseEntity<?> addCandidate(@RequestBody Map<String, String> body) {
        try {
            Candidate c = service.addCandidate(body.get("name"), body.get("party"));
            return ResponseEntity.ok(c);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", e.getMessage()));
        }
    }

    /** DELETE /api/admin/candidates/{id} — remove a candidate */
    @DeleteMapping("/admin/candidates/{id}")
    public ResponseEntity<?> deleteCandidate(@PathVariable Long id) {
        try {
            service.deleteCandidate(id);
            return ResponseEntity.ok(Map.of("message", "Candidate deleted successfully."));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", e.getMessage()));
        }
    }

    /** POST /api/admin/reset — reset all votes */
    @PostMapping("/admin/reset")
    public ResponseEntity<?> resetVotes() {
        try {
            String result = service.resetAllVotes();
            return ResponseEntity.ok(Map.of("message", result));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("message", "Failed to reset votes."));
        }
    }
}
