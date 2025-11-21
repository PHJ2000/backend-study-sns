package com.example.devSns.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {

    // 멤버 검색용 (username 또는 nickname 기준)
    List<Member> findByUsernameContainingIgnoreCaseOrNicknameContainingIgnoreCase(
            String usernameKeyword,
            String nicknameKeyword
    );
}
