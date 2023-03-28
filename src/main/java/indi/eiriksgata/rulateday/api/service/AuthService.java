package indi.eiriksgata.rulateday.api.service;

public interface AuthService {
    void cryptoLoginVerification(String cryptoData);

    void cryptoHeadersVerification(String cryptoData);

    String genCryptoData();
}