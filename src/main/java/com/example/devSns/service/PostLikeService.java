package com.example.devSns.service;

import com.example.devSns.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional
public class PostLikeService {

    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;

    public PostLikeService(MemberRepository memberRepository,
                           PostRepository postRepository,
                           PostLikeRepository postLikeRepository) {
        this.memberRepository = memberRepository;
        this.postRepository = postRepository;
        this.postLikeRepository = postLikeRepository;
    }

    public void like(Long memberId, Long postId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Member not found"));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found"));

        if (postLikeRepository.existsByMemberIdAndPostId(memberId, postId)) {
            return; // 이미 좋아요 누른 상태
        }

        PostLike like = PostLike.builder()
                .member(member)
                .post(post)
                .build();
        postLikeRepository.save(like);
    }

    public void unlike(Long memberId, Long postId) {
        postLikeRepository.deleteByMemberIdAndPostId(memberId, postId);
    }

    @Transactional(readOnly = true)
    public long countLikes(Long postId) {
        return postLikeRepository.countByPostId(postId);
    }
}
