package com.example.devSns.service;

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
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    public PostService(PostRepository postRepository,
                       MemberRepository memberRepository) {
        this.postRepository = postRepository;
        this.memberRepository = memberRepository;
    }

    /** PostController.list 에서 쓰는 메서드 */
    @Transactional(readOnly = true)
    public List<Post> findAll() {
        return postRepository.findAll();
    }

    /** PostController.get 에서 쓰는 메서드 */
    @Transactional(readOnly = true)
    public Post findById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found"));
    }

    /** PostController.create 에서 쓰는 메서드 */
    public Post create(Long memberId, String title, String content) {
        Member author = memberRepository.findById(memberId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Member not found"));

        Post post = Post.builder()
                .author(author)
                .title(title)
                .content(content)
                .build();

        return postRepository.save(post);
    }

    public Post update(Long id, String title, String content) {
        Post post = findById(id);
        post.update(title, content);
        return post;
    }

    public void delete(Long id) {
        Post post = findById(id);
        postRepository.delete(post);
    }
}
