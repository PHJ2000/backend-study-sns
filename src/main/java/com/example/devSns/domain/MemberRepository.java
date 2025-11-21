package com.example.devSns.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    // 멤버 검색용 (username 또는 nickname 기준)
    List<Member> findByUsernameContainingIgnoreCaseOrNicknameContainingIgnoreCase(
            String usernameKeyword,
            String nicknameKeyword
    );

    Optional<Member> findByUsername(String username);

    boolean existsByUsername(String username);
}
