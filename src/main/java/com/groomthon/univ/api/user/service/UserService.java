package com.groomthon.univ.api.user.service;

import com.groomthon.univ.api.user.dto.LoginRequestDto;
import com.groomthon.univ.api.user.dto.SignupRequestDto;
import com.groomthon.univ.api.user.entity.Role;
import com.groomthon.univ.api.user.entity.User;
import com.groomthon.univ.api.user.jwt.JwtService;
import com.groomthon.univ.api.user.repository.UserRepository;
import com.groomthon.univ.common.exception.NotFoundException;
import com.groomthon.univ.common.response.ErrorStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public void signupUser(SignupRequestDto signupDto) {

        String email = signupDto.getEmail().toLowerCase();

        User user = User.builder()
                .email(email)
                .username(signupDto.getUsername())
                .password(passwordEncoder.encode(signupDto.getPassword()))
                .role(Role.ROLE_USER)
                .build();

        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public String loginUser(LoginRequestDto loginDto) {

        String email = loginDto.getEmail().toLowerCase();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException(ErrorStatus.USER_NOT_FOUND.getMessage()));

        if (!passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("이메일 혹은 비밀번호 다시 확인해보세요.");
        }

        return jwtService.generateToken(user.getEmail(), user.getRole().name());
    }
}
