package org.burgeon.yi.basic.user.app.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.burgeon.yi.basic.user.client.dto.request.RegisterByMobileCaptchaRequest;
import org.burgeon.yi.basic.user.client.dto.request.RegisterSendMobileCaptchaRequest;
import org.burgeon.yi.basic.user.client.service.RegisterService;
import org.burgeon.yi.basic.user.domain.login.AggRegisterLauncher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author Sam Lu
 * @date 2022/07/02
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RegisterServiceImpl implements RegisterService {

    private final AggRegisterLauncher aggRegisterLauncher;

    /**
     * 注册成功的默认跳转地址
     */
    @Value("${app.config.register.defaultRedirectUrl:${server.host}/res/profile.html}")
    private String registerDefaultRedirectUrl;

    @Override
    public void sendMobileCaptcha(RegisterSendMobileCaptchaRequest request) {
        aggRegisterLauncher.sendMobileCaptcha(request.getMobile());
    }

    @Override
    public String registerWithMobileCaptcha(RegisterByMobileCaptchaRequest request) {
        aggRegisterLauncher.registerWithMobileCaptcha(request.getMobile(), request.getCode());
        return registerDefaultRedirectUrl;
    }

}
