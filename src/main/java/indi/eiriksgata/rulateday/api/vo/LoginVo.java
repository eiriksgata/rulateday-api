package indi.eiriksgata.rulateday.api.vo;

import lombok.Data;

@Data
public class LoginVo {

    private String username;
    private String password;
    private long timestamp;
}
