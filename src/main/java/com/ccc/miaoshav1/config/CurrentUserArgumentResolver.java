package com.ccc.miaoshav1.config;

import com.alibaba.fastjson.JSON;
import com.ccc.miaoshav1.annotation.CurrentUser;
import com.ccc.miaoshav1.domin.MiaoshaUser;
import com.ccc.miaoshav1.exception.GlobalException;
import com.ccc.miaoshav1.result.CodeMsg;
import com.ccc.miaoshav1.service.redis.RedisServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * 解析 @CurrentUser 注解参数：从请求 header 的 token 中取当前登录用户
 */
@Component
public class CurrentUserArgumentResolver implements HandlerMethodArgumentResolver {

    private static final String TOKEN_HEADER = "token";
    private static final String KEY_TOKEN_PREFIX = "token:";

    @Autowired
    private RedisServer redisServer;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(CurrentUser.class)
                && parameter.getParameterType() == MiaoshaUser.class;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                   NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        String token = webRequest.getHeader(TOKEN_HEADER);
        if (token == null || token.isEmpty()) {
            throw new GlobalException(CodeMsg.SESSION_ERROR);
        }
        String userJson = redisServer.get(KEY_TOKEN_PREFIX + token);
        if (userJson == null || userJson.isEmpty()) {
            throw new GlobalException(CodeMsg.SESSION_ERROR);
        }
        return JSON.parseObject(userJson, MiaoshaUser.class);
    }
}
