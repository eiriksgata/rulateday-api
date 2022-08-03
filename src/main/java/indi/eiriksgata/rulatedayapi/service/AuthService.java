package indi.eiriksgata.rulatedayapi.service;

public interface AuthService {
    void cryptoLoginVerification(String cryptoData);

    void cryptoHeadersVerification(String cryptoData);

    String genCryptoData();
}
