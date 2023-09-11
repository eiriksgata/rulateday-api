package com.github.eiriksgata.rulateday.platform.service;

import com.github.eiriksgata.rulateday.platform.vo.AccessToken;

public interface AuthService {
    AccessToken loginVerification(String cryptoData);

    void cryptoHeadersVerification(String cryptoData);

}
