package com.github.eiriksgata.rulateday.platform.service;

import com.github.eiriksgata.rulateday.platform.exception.CommonBaseException;
import com.github.eiriksgata.rulateday.platform.vo.AccessToken;
import com.github.eiriksgata.rulateday.platform.vo.UserPasswordResetVo;
import org.jetbrains.annotations.NotNull;
import org.springframework.transaction.annotation.Transactional;

public interface AuthService {
    AccessToken loginVerificationByVerityCode(String cryptoData);

    AccessToken loginVerificationBySliderVerify(@NotNull String cryptoData);

    @Transactional(rollbackFor = CommonBaseException.class)
    void userPasswordReset(UserPasswordResetVo userPasswordResetVo);

    void logout();
}
