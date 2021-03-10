package net.carlosfe.tests.sintad.endpoints.auth.service;

import net.carlosfe.tests.sintad.endpoints.auth.model.AuthRequest;
import net.carlosfe.tests.sintad.endpoints.auth.model.LoginReponse;

public interface IAuthService {
    LoginReponse login(AuthRequest authRequest);
}
