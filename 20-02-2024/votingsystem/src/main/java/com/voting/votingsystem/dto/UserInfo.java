package com.voting.votingsystem.dto;

/**
 * Safe user info for the admin users list (no password exposed).
 */
public record UserInfo(
        Long id,
        String name,
        String email,
        String role,
        boolean hasVoted) {
}
