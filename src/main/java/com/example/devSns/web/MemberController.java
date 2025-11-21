package com.example.devSns.web;

import com.example.devSns.domain.PostLike;
import com.example.devSns.service.MemberService;
import com.example.devSns.web.dto.*;
import com.example.devSns.domain.Member;
import com.example.devSns.domain.Post;
import com.example.devSns.domain.Comment;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    /** 멤버 생성 */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MemberResponse create(@Valid @RequestBody MemberCreateRequest req) {
        Member m = memberService.create(req.username(), req.nickname(), req.bio());
        return MemberResponse.from(m);
    }

    /** 멤버 검색 (q 없으면 전체) */
    @GetMapping
    public List<MemberResponse> search(@RequestParam(name = "q", required = false) String keyword) {
        return memberService.search(keyword).stream()
                .map(MemberResponse::from)
                .toList();
    }

    /** 멤버 단건 조회 */
    @GetMapping("/{memberId}")
    public MemberResponse get(@PathVariable Long memberId) {
        return MemberResponse.from(memberService.get(memberId));
    }

    /** 멤버의 게시글 보기 */
    @GetMapping("/{memberId}/posts")
    public List<PostResponse> posts(@PathVariable Long memberId) {
        List<Post> posts = memberService.getPosts(memberId);
        return posts.stream()
                .map(PostResponse::from)
                .toList();
    }

    /** 멤버의 댓글 보기 */
    @GetMapping("/{memberId}/comments")
    public List<CommentResponse> comments(@PathVariable Long memberId) {
        List<Comment> comments = memberService.getComments(memberId);
        return comments.stream()
                .map(CommentResponse::from)
                .toList();
    }

    /** 멤버가 좋아요 누른 게시글 보기 */
    @GetMapping("/{memberId}/likes")
    public List<PostResponse> likedPosts(@PathVariable Long memberId) {
        List<PostLike> likes = memberService.getLikes(memberId);
        return likes.stream()
                .map(pl -> PostResponse.from(pl.getPost()))
                .toList();
    }
}
