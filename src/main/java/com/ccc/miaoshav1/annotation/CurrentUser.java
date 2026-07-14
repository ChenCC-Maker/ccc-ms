package com.ccc.miaoshav1.annotation;

import java.lang.annotation.*;

/**
 * 标注在接口方法参数上，表示从请求 header 的 token 中解析当前登录用户并注入
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CurrentUser {
}
