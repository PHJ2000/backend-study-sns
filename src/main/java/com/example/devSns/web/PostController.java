package com.example.devSns.web;

import com.example.devSns.domain.Post;
import com.example.devSns.service.PostService;
import com.example.devSns.web.dto.PostCreateRequest;
import com.example.devSns.web.dto.PostUpdateRequest;
import com.example.devSns.web.dto.PostResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService svc;

    public PostController(PostService svc) {
        this.svc = svc;
    }

    @GetMapping
    public List<PostResponse> list() {
        return svc.findAll().stream().map(PostResponse::from).toList();
    }

    @GetMapping("/{id}")
    public PostResponse get(@PathVariable Long id) {
        Post p = svc.findById(id);
        return PostResponse.from(p);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PostResponse create(@Valid @RequestBody PostCreateRequest req) {
        Post created = svc.create(
                req.memberId(),
                req.title(),
                req.content()
        );
        return PostResponse.from(created);
    }


    @PutMapping("/{id}")
    public PostResponse update(@PathVariable Long id, @Valid @RequestBody PostUpdateRequest req) {
        Post updated = svc.update(id, req.title(), req.content());
        return PostResponse.from(updated);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        svc.delete(id);
    }
}
