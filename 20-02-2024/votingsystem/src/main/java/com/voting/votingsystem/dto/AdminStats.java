package com.voting.votingsystem.dto;

/**
 * Summary stats for the admin dashboard.
 */
public record AdminStats(
        long totalUsers,
        long totalVoters, // users who have voted
        long totalCandidates,
        long totalVotes,
        String leadingCandidate,
        String leadingParty,
        double voterTurnout // percentage
) {
}
