package com.example.devSns.service;

import com.example.devSns.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final PostLikeRepository postLikeRepository;

    public MemberService(MemberRepository memberRepository,
                         PostRepository postRepository,
                         CommentRepository commentRepository,
                         PostLikeRepository postLikeRepository) {
        this.memberRepository = memberRepository;
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.postLikeRepository = postLikeRepository;
    }

    public Member create(String username, String nickname, String bio) {
        Member member = Member.builder()
                .username(username)
                .nickname(nickname)
                .bio(bio)
                .build();
        return memberRepository.save(member);
    }

    @Transactional(readOnly = true)
    public Member get(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Member not found"));
    }

    @Transactional(readOnly = true)
    public List<Member> search(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return memberRepository.findAll();
        }
        return memberRepository
                .findByUsernameContainingIgnoreCaseOrNicknameContainingIgnoreCase(keyword, keyword);
    }

    @Transactional(readOnly = true)
    public List<Post> getPosts(Long memberId) {
        ensureMemberExists(memberId);
        return postRepository.findByAuthorIdOrderByIdDesc(memberId);
    }

    @Transactional(readOnly = true)
    public List<Comment> getComments(Long memberId) {
        ensureMemberExists(memberId);
        return commentRepository.findByAuthorIdOrderByIdDesc(memberId);
    }

    @Transactional(readOnly = true)
    public List<PostLike> getLikes(Long memberId) {
        ensureMemberExists(memberId);
        return postLikeRepository.findByMemberIdOrderByIdDesc(memberId);
    }

    private void ensureMemberExists(Long memberId) {
        if (!memberRepository.existsById(memberId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Member not found");
        }
    }
}
