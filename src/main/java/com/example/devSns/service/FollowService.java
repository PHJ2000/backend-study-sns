package com.example.devSns.service;

import com.example.devSns.domain.Follow;
import com.example.devSns.domain.FollowRepository;
import com.example.devSns.domain.Member;
import com.example.devSns.domain.MemberRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Transactional
public class FollowService {

    private final MemberRepository memberRepository;
    private final FollowRepository followRepository;

    public FollowService(MemberRepository memberRepository,
                         FollowRepository followRepository) {
        this.memberRepository = memberRepository;
        this.followRepository = followRepository;
    }

    public void follow(Long followerId, Long followingId) {
        if (followerId.equals(followingId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "자기 자신은 팔로우할 수 없습니다.");
        }

        Member follower = getMember(followerId);
        Member following = getMember(followingId);

        if (followRepository.existsByFollowerIdAndFollowingId(followerId, followingId)) {
            // 이미 팔로우 중이면 무시
            return;
        }

        Follow follow = Follow.builder()
                .follower(follower)
                .following(following)
                .build();
        followRepository.save(follow);
    }

    public void unfollow(Long followerId, Long followingId) {
        followRepository.deleteByFollowerIdAndFollowingId(followerId, followingId);
    }

    @Transactional(readOnly = true)
    public List<Member> getFollowers(Long memberId) {
        getMember(memberId); // 존재 확인
        return followRepository.findByFollowingId(memberId)
                .stream()
                .map(Follow::getFollower)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<Member> getFollowings(Long memberId) {
        getMember(memberId); // 존재 확인
        return followRepository.findByFollowerId(memberId)
                .stream()
                .map(Follow::getFollowing)
                .toList();
    }

    private Member getMember(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Member not found"));
    }
}
