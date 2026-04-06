package com.ccc.miaoshav1.controller;


import com.ccc.miaoshav1.result.Result;
import com.ccc.miaoshav1.vo.LoginVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@Tag(name = "ms系统登录")
public class UserLoginController {


    @PostMapping("/handleLogin")
    @Operation(description = "用户登录")
    @ApiResponse(description = "用户登录成功后返回token")
    public Result<String> handleMSLogin(
            @Valid @RequestBody LoginVO loginVO
    ){
        // Step1：无需校验LoginVO中的参数，定义VO的同时已经完善了校验规则

        // Step2：校验账号、密码合法性

        // Step3：合法后，生成token返回
        return Result.success("chencc");
    }



}



