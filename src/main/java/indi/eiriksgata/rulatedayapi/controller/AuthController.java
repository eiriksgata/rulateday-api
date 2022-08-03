package indi.eiriksgata.rulatedayapi.controller;

import indi.eiriksgata.rulatedayapi.service.AuthService;
import indi.eiriksgata.rulatedayapi.vo.ResponseBean;
import io.swagger.annotations.Api;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Api
@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    AuthService authService;

    @PutMapping("/authentication")
    public ResponseBean<?> authentication(@NotNull HttpServletRequest httpServletRequest, @NotNull HttpServletResponse response) {
        String authorization = httpServletRequest.getHeader("Authorization");
        authService.cryptoLoginVerification(authorization);
        response.setHeader("Authorization", authService.genCryptoData());
        return ResponseBean.success();
    }

}
