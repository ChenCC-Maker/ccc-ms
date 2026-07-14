package com.ccc.miaoshav1.vo;


import com.ccc.miaoshav1.validator.PasswordLength;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
// 用于生成一个包含所有字段的构造函数
@AllArgsConstructor
// 用于生成一个无参的构造函数
@NoArgsConstructor
public class LoginVO {

    @NotNull
    private String nickname;

    @NotNull
    @PasswordLength() // 自定义注解，校验密码长度是否符合规范，后续可增加更多的校验规则
    private String password;

}
