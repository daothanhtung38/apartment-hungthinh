package com.hungthinh.hungthinh.services.implement;

import com.hungthinh.hungthinh.exceptions.UserMessageException;
import com.hungthinh.hungthinh.mapper.UserMapper;
import com.hungthinh.hungthinh.models.dtos.user.UserCreateRequest;
import com.hungthinh.hungthinh.models.dtos.user.UserGetsResponse;
import com.hungthinh.hungthinh.models.dtos.user.UserUpdateRequest;
import com.hungthinh.hungthinh.models.entities.bases.User;
import com.hungthinh.hungthinh.models.entities.enums.UserRole;
import com.hungthinh.hungthinh.models.global.ApiResult;
import com.hungthinh.hungthinh.repositories.UserRepository;
import com.hungthinh.hungthinh.services.interfaces.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Override
    public ApiResult<List<UserGetsResponse>> getAllUser() {
        List<User> users = userRepository.findAll();
        List<UserGetsResponse> responseList = users.stream()
                .filter(user -> !user.getRole().equals(UserRole.RESIDENT))
                .map(userMapper::toResponse)
                .sorted(Comparator.comparing(UserGetsResponse::getLastModifiedTime).reversed()) // sắp xếp theo thời gian thay đổi
                .toList();
        return ApiResult.success(responseList, "Lay danh sách người dùng thành công");
    }

    @Override
    public ApiResult<UUID> createUser(UserCreateRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new UserMessageException("Tên đăng nhập đã tồn tại");
        }

        String hashPassword = passwordEncoder.encode(request.getPassword());

        User newUser = User.builder()
                .username(request.getUsername())
                .password(hashPassword)
                .role(UserRole.valueOf(request.getRole()))
                .build();
        userRepository.save(newUser);
        return ApiResult.success(newUser.getId(), "Thêm mới người dùng thành công");
    }

    @Override
    public ApiResult<String> updateUser(UUID userId, UserUpdateRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserMessageException("Người dùng không ton tại"));

        user.setUsername(request.getUsername());
        user.setRole(UserRole.valueOf(request.getRole()));
        userRepository.save(user);
        return ApiResult.success(null, "Cập nhật người dùng thành công");
    }

    @Override
    public ApiResult<String> deleteUser(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserMessageException("Nguoi dung không tồn tại"));
        userRepository.delete(user);
        return ApiResult.success(null, "Xóa người dung thành công");
    }
}
