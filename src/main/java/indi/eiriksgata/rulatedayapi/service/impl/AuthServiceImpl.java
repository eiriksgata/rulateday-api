package indi.eiriksgata.rulatedayapi.service.impl;

import com.alibaba.fastjson.JSONObject;
import indi.eiriksgata.rulatedayapi.exception.CommonBaseException;
import indi.eiriksgata.rulatedayapi.exception.CommonBaseExceptionEnum;
import indi.eiriksgata.rulatedayapi.service.AuthService;
import indi.eiriksgata.rulatedayapi.service.CacheService;
import indi.eiriksgata.rulatedayapi.utils.AES;
import indi.eiriksgata.rulatedayapi.utils.Base64;
import indi.eiriksgata.rulatedayapi.utils.EncryptionUtil;
import indi.eiriksgata.rulatedayapi.utils.ExceptionUtils;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    byte[] key = new byte[]{0x12, 0x22, 0x34, 0x55, 0x12, 0x54, 0x35, 0x6, 0x34, 0x25, 0x12, 0x42, 0x4, 0x57, 0x55, 0x16};
    byte[] iv = new byte[]{0x5, 0x21, 0x3, 0x52, 0x47, 0x35, 0x62, 0x15, 0x75, 0x34, 0x21, 0x45, 0x12, 0x56, 0x32, 0x28};

    @Autowired
    CacheService cacheService;

    @Value("${spring.profiles.authorization.username}")
    String username;

    @Value("${spring.profiles.authorization.password}")
    String password;

    @Override
    public void cryptoLoginVerification(@NotNull String cryptoData) {
        byte[] originalData = Base64.decode(cryptoData.getBytes(StandardCharsets.UTF_8));
        byte[] decode = AES.decrypt(originalData, key, iv);
        JSONObject requestJson = JSONObject.parseObject(new String(decode));
        long dataTimestamp = requestJson.getLongValue("timestamp");
        long currentTimestamp = System.currentTimeMillis();
        if (requestJson.getString("token").equals(genToken()) &&
                dataTimestamp > currentTimestamp - 1000 * 60 * 5 &&
                dataTimestamp < currentTimestamp + 1000 * 60 * 5
        ) {
            return;
        }
        throw new CommonBaseException(CommonBaseExceptionEnum.ACCOUNTS_AUTHENTICATION_ERROR);
    }

    @Override
    public void cryptoHeadersVerification(String cryptoData) {
        if (cryptoData == null) {
            throw new CommonBaseException(CommonBaseExceptionEnum.TOKEN_NOT_EXIST_ERR);
        }
        try {
            byte[] originalData = Base64.decode(cryptoData.getBytes(StandardCharsets.UTF_8));
            byte[] decode = AES.decrypt(originalData, key, iv);
            JSONObject requestJson = JSONObject.parseObject(new String(decode));
            String token = requestJson.getString("token");
            if (cacheService.verification(token)) {
                return;
            }
        } catch (Exception e) {
            log.error("token:{},无法验证:{}", cryptoData, ExceptionUtils.getExceptionAllInfo(e));
        }
        throw new CommonBaseException(CommonBaseExceptionEnum.TOKEN_NOT_EXIST_ERR);
    }

    @Override
    public String genCryptoData() {
        String token = genToken();
        JSONObject json = new JSONObject();
        json.put("token", token);
        json.put("timestamp", System.currentTimeMillis());
        byte[] byteData = json.toJSONString().getBytes(StandardCharsets.UTF_8);
        byte[] encode = AES.encrypt(byteData, key, iv);
        cacheService.put(token);
        return new String(Base64.encode(encode), StandardCharsets.UTF_8);
    }

    public String genToken() {
        return EncryptionUtil.sha256(username + "&" + password);
    }


}
