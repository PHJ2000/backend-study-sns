package com.example.devSns.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

    boolean existsByMemberIdAndPostId(Long memberId, Long postId);

    void deleteByMemberIdAndPostId(Long memberId, Long postId);

    long countByPostId(Long postId);

    List<PostLike> findByMemberIdOrderByIdDesc(Long memberId);
}
