package com.hungthinh.hungthinh.controllers;

import com.hungthinh.hungthinh.models.global.ApiResult;
import com.hungthinh.hungthinh.models.dtos.auth.LoginRequest;
import com.hungthinh.hungthinh.models.dtos.auth.RegisterRequest;
import com.hungthinh.hungthinh.models.dtos.auth.TokenResponse;
import com.hungthinh.hungthinh.services.implement.AuthService;
import com.hungthinh.hungthinh.services.interfaces.ITokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController extends ApiBaseController {
    private final AuthenticationManager authenticationManager;
    private  final ITokenService tokenService;
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ApiResult<TokenResponse>> login(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication =authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                    )
            );

            UserDetails userDetails = (UserDetails)authentication.getPrincipal();
            TokenResponse tokens = tokenService.generateTokens(userDetails);

            return ResponseEntity.ok(ApiResult.success(tokens, "Đăng nhập thành công"));
        } catch (BadCredentialsException exception) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResult.fail("Tên đăng nhập hoặc mật khẩu không chính xác"));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResult<String>> register(RegisterRequest request) {
        return executeApiResult(() -> authService.register(request));
    }
}
