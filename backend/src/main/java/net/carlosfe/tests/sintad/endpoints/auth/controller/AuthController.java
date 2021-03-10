package net.carlosfe.tests.sintad.endpoints.auth.controller;


import net.carlosfe.tests.sintad.endpoints.auth.model.AuthRequest;
import net.carlosfe.tests.sintad.endpoints.auth.model.LoginReponse;
import net.carlosfe.tests.sintad.endpoints.auth.service.IAuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*", methods= {RequestMethod.POST})
public class AuthController {

    private final IAuthService authService;

    public AuthController(final IAuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginReponse> create(@RequestBody @Valid AuthRequest authRequest){
        return ResponseEntity.ok(authService.login(authRequest));
    }
}
