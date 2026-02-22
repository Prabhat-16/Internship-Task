package com.voting.votingsystem.config;

import com.voting.votingsystem.entity.Candidate;
import com.voting.votingsystem.entity.User;
import com.voting.votingsystem.repository.CandidateRepository;
import com.voting.votingsystem.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Seeds initial data: candidates + default admin account.
 * Idempotent — only inserts if tables are empty.
 */
@Configuration
public class DataInitializer {

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    @Bean
    CommandLineRunner seedData(CandidateRepository candidateRepo,
            UserRepository userRepo) {
        return args -> {
            // Seed candidates
            if (candidateRepo.count() == 0) {
                candidateRepo.save(candidate("Arjun Mehta", "Progressive Party"));
                candidateRepo.save(candidate("Priya Sharma", "National Front"));
                candidateRepo.save(candidate("Rahul Singh", "United India"));
                candidateRepo.save(candidate("Kavya Patel", "Green Alliance"));
                candidateRepo.save(candidate("Amit Verma", "Reform Party"));
                System.out.println("✅ Sample candidates seeded.");
            }

            // Seed default admin
            if (!userRepo.existsByEmail("admin@votesecure.com")) {
                User admin = new User();
                admin.setName("Administrator");
                admin.setEmail("admin@votesecure.com");
                admin.setPassword(encoder.encode("Admin@1234"));
                admin.setRole("ADMIN");
                admin.setHasVoted(false);
                userRepo.save(admin);
                System.out.println("✅ Default admin seeded: admin@votesecure.com / Admin@1234");
            }
        };
    }

    private Candidate candidate(String name, String party) {
        Candidate c = new Candidate();
        c.setName(name);
        c.setParty(party);
        c.setVoteCount(0);
        return c;
    }
}
