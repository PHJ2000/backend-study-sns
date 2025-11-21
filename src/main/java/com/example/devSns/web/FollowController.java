package com.example.devSns.web;

import com.example.devSns.service.FollowService;
import com.example.devSns.web.dto.MemberResponse;
import com.example.devSns.domain.Member;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/members/{memberId}")
public class FollowController {

    private final FollowService followService;

    public FollowController(FollowService followService) {
        this.followService = followService;
    }

    /** memberId 가 targetId 를 팔로우 */
    @PostMapping("/follow/{targetId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void follow(@PathVariable Long memberId, @PathVariable Long targetId) {
        followService.follow(memberId, targetId);
    }

    /** 언팔로우 */
    @DeleteMapping("/follow/{targetId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void unfollow(@PathVariable Long memberId, @PathVariable Long targetId) {
        followService.unfollow(memberId, targetId);
    }

    /** 나를 팔로우하는 사람들 */
    @GetMapping("/followers")
    public List<MemberResponse> followers(@PathVariable Long memberId) {
        List<Member> list = followService.getFollowers(memberId);
        return list.stream().map(MemberResponse::from).toList();
    }

    /** 내가 팔로우하는 사람들 */
    @GetMapping("/followings")
    public List<MemberResponse> followings(@PathVariable Long memberId) {
        List<Member> list = followService.getFollowings(memberId);
        return list.stream().map(MemberResponse::from).toList();
    }
}
