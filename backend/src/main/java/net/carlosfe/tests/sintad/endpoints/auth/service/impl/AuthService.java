package net.carlosfe.tests.sintad.endpoints.auth.service.impl;

import net.carlosfe.tests.sintad.endpoints.auth.model.AuthRequest;
import net.carlosfe.tests.sintad.endpoints.auth.model.LoginReponse;
import net.carlosfe.tests.sintad.endpoints.auth.service.IAuthService;
import net.carlosfe.tests.sintad.global.utils.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements IAuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthService(final AuthenticationManager authenticationManager, final JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public LoginReponse login(AuthRequest authRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        return new LoginReponse(authRequest.getUsername(), jwtUtil.generateToken(authRequest.getUsername()));
    }
}
