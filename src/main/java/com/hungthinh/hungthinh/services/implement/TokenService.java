package com.hungthinh.hungthinh.services.implement;



import com.hungthinh.hungthinh.models.dtos.auth.TokenResponse;
import com.hungthinh.hungthinh.models.security.JwtTokenProvider;
import com.hungthinh.hungthinh.services.interfaces.ITokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService implements ITokenService {
    private final JwtTokenProvider tokenProvider;

    @Override
    public TokenResponse generateTokens(UserDetails userDetails) {
        String accessToken = tokenProvider.generateAccessToken(userDetails);
        String refreshToken = tokenProvider.generateRefreshToken(userDetails);

        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .expiresIn(tokenProvider.extractExpiration(accessToken).getTime())
                .build();
    }

}

