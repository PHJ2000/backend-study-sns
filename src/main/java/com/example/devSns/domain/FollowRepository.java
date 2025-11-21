package com.example.devSns.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    boolean existsByFollowerIdAndFollowingId(Long followerId, Long followingId);

    void deleteByFollowerIdAndFollowingId(Long followerId, Long followingId);

    List<Follow> findByFollowerId(Long followerId);   // 내가 팔로우하는 사람들
    List<Follow> findByFollowingId(Long followingId); // 나를 팔로우하는 사람들
}
