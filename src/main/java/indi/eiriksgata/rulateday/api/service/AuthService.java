package indi.eiriksgata.rulateday.api.service;

public interface AuthService {
    void loginVerification(String cryptoData);

    void cryptoHeadersVerification(String cryptoData);

    String genCryptoData();
}
