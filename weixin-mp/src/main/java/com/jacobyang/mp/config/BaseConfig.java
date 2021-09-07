package com.jacobyang.mp.config;

import lombok.Data;


/**
 * 微信基础配置Bean
 *
 * @author guochao
 * @since 0.0.1
 */
@Data
public class BaseConfig {
    /**
     * 公众平台用户唯一凭证
     */
    private String appId;
    /**
     * 公众平台用户唯一凭证密钥
     */
    private String appSecret;

    /**
     * 第三方用户唯一凭证
     */
    private String componentAppId;
    /**
     * 第三方用户唯一凭证密钥
     */
    private String componentAppSecret;
    /**
     * 第三方token
     */
    private String componentToken;
    /**
     * 第三方消息key
     */
    private String componentKey;

    public BaseConfig() {

    }

    public BaseConfig(String appId, String appSecret) {
        this.appId = appId;
        this.appSecret = appSecret;
    }

}