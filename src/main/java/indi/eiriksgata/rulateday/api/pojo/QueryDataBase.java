package indi.eiriksgata.rulateday.api.pojo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * author: create by Keith
 * version: v1.0
 * description: indi.eiriksgata.rulateday.pojo
 * date: 2020/11/26
 **/
@Data
public class QueryDataBase {

    private Long id;

    @NotNull
    @NotBlank
    private String name;

    @NotNull
    @NotBlank
    private String describe;

}
