package com.spring.boot.Service.Impl.Security;

import com.spring.boot.Config.Security.TokenHandler;
import com.spring.boot.Dto.Security.TokenUserDto;
import com.spring.boot.Dto.Security.UserLoginDto;
import com.spring.boot.Mapper.UserMapper;
import com.spring.boot.Model.User;
import com.spring.boot.Repo.UserRepo;
import com.spring.boot.Service.Security.AuthService;
import com.spring.boot.Vm.TokenResponseVm;
import com.spring.boot.BundleMessage.Exception.SystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private @Lazy PasswordEncoder passwordEncoder;
    @Autowired
    private TokenHandler tokenHandler;

    @Override
    public TokenResponseVm login(UserLoginDto userLoginDto) {
        User user = userRepo.findByEmail(userLoginDto.getEmail())
                .orElseThrow(() -> new SystemException("user.name.not.found", HttpStatus.NOT_FOUND));

        if (!passwordEncoder.matches(userLoginDto.getPassword(), user.getPassword())) {
            throw new SystemException("user.password.not.match", HttpStatus.UNAUTHORIZED);
        }

        TokenUserDto tokenUserDto = UserMapper.USER_MAPPER.toTokenUserDto(user);
        String tokenAccess = tokenHandler.createAccessToken(tokenUserDto);
        String tokenRefresh = tokenHandler.createRefreshToken(tokenUserDto);

        return new TokenResponseVm(tokenAccess, tokenRefresh);
    }

    @Override
    public TokenResponseVm refreshToken(String refreshToken) {
        String newAccessToken = tokenHandler.refreshAccessToken(refreshToken);
        return new TokenResponseVm(newAccessToken, refreshToken);
    }

    @Override
    public void logout(String refreshToken) {
        tokenHandler.clearRefreshToken(refreshToken);
    }
}
