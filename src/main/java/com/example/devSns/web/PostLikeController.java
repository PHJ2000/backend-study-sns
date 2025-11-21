package com.example.devSns.web;

import com.example.devSns.service.PostLikeService;
import com.example.devSns.web.dto.LikeRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts/{postId}/likes")
public class PostLikeController {

    private final PostLikeService postLikeService;

    public PostLikeController(PostLikeService postLikeService) {
        this.postLikeService = postLikeService;
    }

    /** 좋아요 누르기 */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void like(@PathVariable Long postId,
                     @Valid @RequestBody LikeRequest req) {
        postLikeService.like(req.memberId(), postId);
    }

    /** 좋아요 취소 */
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void unlike(@PathVariable Long postId,
                       @Valid @RequestBody LikeRequest req) {
        postLikeService.unlike(req.memberId(), postId);
    }

    /** 게시글 좋아요 개수 */
    @GetMapping("/count")
    public long count(@PathVariable Long postId) {
        return postLikeService.countLikes(postId);
    }
}
