package com.hungthinh.hungthinh.services.implement;

import com.hungthinh.hungthinh.exceptions.UserMessageException;
import com.hungthinh.hungthinh.models.global.ApiResult;
import com.hungthinh.hungthinh.models.dtos.auth.RegisterRequest;
import com.hungthinh.hungthinh.models.entities.bases.User;
import com.hungthinh.hungthinh.models.entities.enums.UserRole;
import com.hungthinh.hungthinh.repositories.UserRepository;
import com.hungthinh.hungthinh.services.interfaces.IAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService implements IAuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public ApiResult<String> register(RegisterRequest request) {
        // kiểm tra user đã tồn tại chưa
        if ( userRepository.existsByUsername(request.getUsername())) {
            throw new UserMessageException("Tên đăng nhập đã tồn tại");
        };

        User user =  User.builder()
                .username(request.getUsername().trim())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(UserRole.RESIDENT) // mặc định người đăng kí là cư dân
                .build();

        userRepository.save(user);

        return ApiResult.success(null
                , "Đăng kí tài khoản thành công" );
    }
}
