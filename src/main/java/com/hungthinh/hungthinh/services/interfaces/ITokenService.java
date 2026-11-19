package com.hungthinh.hungthinh.services.interfaces;

import com.hungthinh.hungthinh.models.dtos.auth.TokenResponse;
import org.springframework.security.core.userdetails.UserDetails;


public interface ITokenService {
    TokenResponse generateTokens(UserDetails userDetails);
}
