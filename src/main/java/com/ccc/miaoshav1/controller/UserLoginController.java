package com.ccc.miaoshav1.controller;


import com.ccc.miaoshav1.result.Result;
import com.ccc.miaoshav1.service.common.UserLoginService;
import com.ccc.miaoshav1.vo.LoginVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@Tag(name = "ms系统登录")
public class UserLoginController {


    @Autowired
    private UserLoginService userLoginService;

    @PostMapping("/handleLogin")
    @Operation(description = "用户登录")
    @ApiResponse(description = "用户登录成功后返回token")
    public Result<String> handleMSLogin(
            @Valid @RequestBody LoginVO loginVO
    ){
        // LoginVO 中的参数校验由 @Valid + 自定义注解完成，校验失败由全局异常处理器统一拦截
        // 调用 service 完成账号密码合法性校验，成功返回 token
        String token = userLoginService.login(loginVO);
        return Result.success(token);
    }



}



