package com.github.eiriksgata.rulateday.platform.service;

import com.github.eiriksgata.rulateday.platform.vo.AccessToken;
import org.jetbrains.annotations.NotNull;

public interface AuthService {
    AccessToken loginVerificationByVerityCode(String cryptoData);

    AccessToken loginVerificationBySliderVerify(@NotNull String cryptoData);
}
