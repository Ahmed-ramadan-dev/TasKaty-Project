package com.spring.boot.Service.Security;
import com.spring.boot.Dto.Security.UserLoginDto;
import com.spring.boot.Vm.TokenResponseVm;
import org.springframework.web.bind.annotation.RequestParam;

public interface AuthService {
    TokenResponseVm login(UserLoginDto userLoginDto);
    TokenResponseVm refreshToken( String refreshToken);
    void logout(@RequestParam String refreshToken);
}
