package com.example.pcenter.service.impl;

import com.example.pcenter.domain.user.User;
import com.example.pcenter.service.AuthService;
import com.example.pcenter.service.UserService;
import com.example.pcenter.web.dto.auth.JwtRequest;
import com.example.pcenter.web.dto.auth.JwtResponse;
import com.example.pcenter.web.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    private Logger logger = Logger.getLogger(AuthServiceImpl.class.getName());
    @Override
    public JwtResponse login(final JwtRequest loginRequest) {
        logger.info("Запуск процесса входа в личный кабинет(сервис)");
        JwtResponse jwtResponse = new JwtResponse();
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                loginRequest.getPassword()));
        User user = userService.getByUsername(loginRequest.getUsername());
        jwtResponse.setId(user.getId());
        jwtResponse.setUsername(user.getUsername());
        jwtResponse.setAccessToken(jwtTokenProvider.createAccessToken(user.getId(),
                user.getUsername(),
                user.getRoles()));
        jwtResponse.setRefreshToken(jwtTokenProvider.createRefreshToken(user.getId(),
                user.getUsername()));
        logger.info("Вход в ЛК завершен(сервис)");
        return jwtResponse;
    }

    @Override
    public JwtResponse refresh(String refreshToken) {
        logger.info("Смена пароля у пользователя(сервис)");
        return jwtTokenProvider.refreshUserTokens(refreshToken);

    }
}
