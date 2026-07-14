package com.ccc.miaoshav1.service.common;


import com.alibaba.fastjson.JSON;
import com.ccc.miaoshav1.dao.MiaoShaUserDao;
import com.ccc.miaoshav1.domin.MiaoshaUser;
import com.ccc.miaoshav1.exception.GlobalException;
import com.ccc.miaoshav1.result.CodeMsg;
import com.ccc.miaoshav1.service.redis.RedisServer;
import com.ccc.miaoshav1.util.MD5Util;
import com.ccc.miaoshav1.vo.LoginVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class UserLoginService {

    @Autowired
    private MiaoShaUserDao miaoShaUserDao;

    @Autowired
    private RedisServer redisServer;

    // 登录失败计数窗口（5分钟内连续输错5次，账号锁定半小时）
    private static final int FAIL_WINDOW_SECONDS = 300;
    private static final int FAIL_THRESHOLD = 5;
    private static final int LOCK_SECONDS = 1800;

    // token 在 Redis 中的有效期
    private static final int TOKEN_EXPIRE_SECONDS = 1800;

    private static final String KEY_FAIL_COUNT_PREFIX = "login_fail_count:";
    private static final String KEY_FAIL_WINDOW_PREFIX = "login_fail_window:";
    private static final String KEY_LOCK_PREFIX = "login_fail_lock:";
    private static final String KEY_TOKEN_PREFIX = "token:";

    /**
     * 登录校验：成功返回 token，失败抛 GlobalException
     */
    public String login(LoginVO loginVO) {
        String nickname = loginVO.getNickname();

        // Step1：判断账号是否已被锁定
        if (redisServer.exists(KEY_LOCK_PREFIX + nickname)) {
            throw new GlobalException(CodeMsg.LOGIN_LOCKED);
        }

        // Step2：根据 nickname 查询用户
        MiaoshaUser user = miaoShaUserDao.getByNickname(nickname);
        if (user == null) {
            throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
        }

        // Step3：密码校验--客户端已做一次 MD5，服务端再做一次 saltMD5
        String formPass = loginVO.getPassword();
        String dbPass = MD5Util.saltMD5(formPass, user.getSalt());
        if (!dbPass.equals(user.getPassword())) {
            recordLoginFailure(nickname);
            throw new GlobalException(CodeMsg.PASSWORD_ERROR);
        }

        // Step4：登录成功，清理失败计数
        redisServer.delete(KEY_FAIL_COUNT_PREFIX + nickname);
        redisServer.delete(KEY_FAIL_WINDOW_PREFIX + nickname);

        // Step5：更新最后登录时间和登录次数
        user.setLastLoginDate(new Date());
        miaoShaUserDao.updateLoginInfo(user);

        // Step6：生成 token 并写入 Redis
        String token = UUID.randomUUID().toString().replace("-", "");
        redisServer.setex(KEY_TOKEN_PREFIX + token, JSON.toJSONString(user), TOKEN_EXPIRE_SECONDS);

        return token;
    }

    /**
     * 记录登录失败次数，达到阈值则锁定账号
     */
    private void recordLoginFailure(String nickname) {
        long count = redisServer.increment(KEY_FAIL_COUNT_PREFIX + nickname);
        if (count == 1) {
            // 首次失败，开启 5 分钟统计窗口
            redisServer.setex(KEY_FAIL_WINDOW_PREFIX + nickname, "", FAIL_WINDOW_SECONDS);
        }
        if (count >= FAIL_THRESHOLD) {
            // 达到阈值，锁定账号半小时
            redisServer.setex(KEY_LOCK_PREFIX + nickname, "", LOCK_SECONDS);
        }
    }
}
