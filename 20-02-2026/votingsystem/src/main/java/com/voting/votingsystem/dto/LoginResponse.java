package com.voting.votingsystem.dto;

/**
 * Data Transfer Object returned after a successful login.
 * Only exposes safe, non-sensitive fields to the frontend.
 */
public record LoginResponse(
        Long id,
        String name,
        String email,
        String role) {
}
