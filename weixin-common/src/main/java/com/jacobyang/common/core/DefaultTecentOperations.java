package com.jacobyang.common.core;

import cn.hutool.core.io.FileUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.jacobyang.common.bean.param.TecentQrCodeTicketParam;
import com.jacobyang.common.bean.result.TecentAccessTokenResult;
import com.jacobyang.common.bean.result.TecentQrTicketResult;
import com.jacobyang.common.bean.result.TecentUserInfoResult;
import com.jacobyang.common.config.BaseConfig;
import com.jacobyang.common.request.AccountRequest;
import com.jacobyang.common.request.BaseRequest;
import com.jacobyang.common.request.UserRequest;

/**
 * @author .
 * @since 0.0.1
 */
public class DefaultTecentOperations implements TecentOperations {

    private BaseConfig baseConfig;

    public DefaultTecentOperations(BaseConfig baseConfig) {
        this.baseConfig = baseConfig;
    }

    @Override
    public TecentAccessTokenResult getAccessToken() {
        String url = BaseRequest.ACCESS_TOKEN_GET.replaceAll("APPID", baseConfig.getAppId());
        url = url.replaceAll("APPSECRET", baseConfig.getAppSecret());
        String result = HttpUtil.get(url);
        TecentAccessTokenResult tecentAccessTokenResult = JSONUtil.toBean(result, TecentAccessTokenResult.class);
        return tecentAccessTokenResult;
    }

    @Override
    public TecentUserInfoResult getUserInfo(String accessToken, String openId) {
        String url = UserRequest.USER_INFO_GET.replaceAll("ACCESS_TOKEN", accessToken);
        url = url.replaceAll("OPENID", openId);
        String result = HttpUtil.get(url);
        TecentUserInfoResult tecentUserInfoResult = JSONUtil.toBean(result, TecentUserInfoResult.class);
        return tecentUserInfoResult;
    }

    @Override
    public TecentQrTicketResult getQrCodeTicket(String accessToken, TecentQrCodeTicketParam param) {
        String url = AccountRequest.QRCODE_CREATE_TICKET_POST.replaceAll("TOKEN", accessToken);
        String a = JSONUtil.toJsonStr(param);
        String result = HttpUtil.post(url, a);
        TecentQrTicketResult tecentQrTicketResult = JSONUtil.toBean(result, TecentQrTicketResult.class);
        return tecentQrTicketResult;
    }

    @Override
    public long getQrCode(String ticket, String path) {
        String url = AccountRequest.QRCODE_CREATE_GET.replaceAll("TICKET", ticket);
        return HttpUtil.downloadFile(url, FileUtil.file(path));
    }
}