package com.github.eiriksgata.rulateday.platform.controller;

import com.github.eiriksgata.rulateday.platform.vo.ResponseBean;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RbacController {

    public ResponseBean<?> role(){

        return ResponseBean.success();

    }


}
