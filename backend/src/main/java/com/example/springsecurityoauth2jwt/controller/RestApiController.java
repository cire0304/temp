package com.example.springsecurityoauth2jwt.controller;

import com.example.springsecurityoauth2jwt.domain.dto.AccountDto;
import com.example.springsecurityoauth2jwt.domain.entity.Account;
import com.example.springsecurityoauth2jwt.domain.entity.Role;
import com.example.springsecurityoauth2jwt.repository.RoleRepository;
import com.example.springsecurityoauth2jwt.repository.UserRepository;
import com.example.springsecurityoauth2jwt.security.model.PrincipalUser;
import com.example.springsecurityoauth2jwt.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class RestApiController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @GetMapping("/")
    public String home() {
        return "홈";
    }

    @GetMapping("/dash")
    public String dash() {
        return "대시보드";
    }

    @GetMapping("/user")
    public String user(@AuthenticationPrincipal PrincipalUser principalUser) {
        String username = principalUser.getUsername();

         Account account = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("잘못된 사용자 입니다."));

        return account.getUsername();
    }

    @GetMapping("/admin")
    public String admin() {
        return "어드민 페이지";
    }

    @PostMapping("/register")
    public String createUser(@RequestBody AccountDto accountDto) {
        Account account = Account.builder()
                .username(accountDto.getUsername())
                .password(accountDto.getPassword())
                .build();

        userService.createUser(account);
        return "요청 성공";
    }

    @GetMapping("/test")
    public void test() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication);
    }


}
