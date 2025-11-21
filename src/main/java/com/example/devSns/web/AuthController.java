package com.example.devSns.web;

import com.example.devSns.config.JwtTokenProvider;
import com.example.devSns.domain.Member;
import com.example.devSns.domain.MemberRepository;
import com.example.devSns.service.MemberService;
import com.example.devSns.web.dto.LoginRequest;
import com.example.devSns.web.dto.LoginResponse;
import com.example.devSns.web.dto.MemberCreateRequest;
import com.example.devSns.web.dto.MemberResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    public AuthController(MemberService memberService,
                          MemberRepository memberRepository,
                          JwtTokenProvider jwtTokenProvider,
                          PasswordEncoder passwordEncoder) {
        this.memberService = memberService;
        this.memberRepository = memberRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
    }

    /** 회원가입 */
    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public MemberResponse signup(@Valid @RequestBody MemberCreateRequest req) {
        Member member = memberService.create(
                req.username(),
                req.password(),
                req.nickname(),
                req.bio()
        );
        return MemberResponse.from(member);
    }

    /** 로그인 + JWT 발급 */
    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest req) {
        Member member = memberRepository.findByUsername(req.username())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED, "아이디 혹은 비밀번호가 올바르지 않습니다."));

        if (!passwordEncoder.matches(req.password(), member.getPassword())) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "아이디 혹은 비밀번호가 올바르지 않습니다.");
        }

        String token = jwtTokenProvider.createToken(member.getUsername(), member.getId());
        return LoginResponse.of(token, member);
    }
}
