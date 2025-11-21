package com.example.devSns.service;

import com.example.devSns.domain.Comment;
import com.example.devSns.domain.CommentRepository;
import com.example.devSns.domain.Member;
import com.example.devSns.domain.MemberRepository;
import com.example.devSns.domain.Post;
import com.example.devSns.domain.PostRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Transactional
public class CommentService {

    private final CommentRepository commentRepo;
    private final PostRepository postRepo;
    private final MemberRepository memberRepo;

    public CommentService(CommentRepository commentRepo,
                          PostRepository postRepo,
                          MemberRepository memberRepo) {
        this.commentRepo = commentRepo;
        this.postRepo = postRepo;
        this.memberRepo = memberRepo;
    }

    /** CommentController.list 에서 부르는 메서드 */
    @Transactional(readOnly = true)
    public List<Comment> list(Long postId) {
        ensurePostExists(postId);
        return commentRepo.findByPostIdOrderByIdAsc(postId);
    }

    @Transactional(readOnly = true)
    public Comment get(Long postId, Long commentId) {
        return commentRepo.findByIdAndPostId(commentId, postId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment not found"));
    }

    /** CommentController.create 에서 부르는 메서드 */
    public Comment create(Long postId, Long memberId, String content) {
        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found"));

        Member author = memberRepo.findById(memberId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Member not found"));

        Comment comment = Comment.builder()
                .content(content)
                .post(post)
                .author(author)
                .build();

        return commentRepo.save(comment);
    }

    public Comment update(Long postId, Long commentId, String content) {
        Comment comment = get(postId, commentId);
        comment.update(content);
        return comment;
    }

    public void delete(Long postId, Long commentId) {
        Comment comment = get(postId, commentId);
        commentRepo.delete(comment);
    }

    private void ensurePostExists(Long postId) {
        if (!postRepo.existsById(postId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found");
        }
    }
}
