package com.leoleo.film.utils.facebook;

import com.leoleo.film.utils.HttpClientUtil;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class FacebookAuthUtil {
    private static final Logger logger = LoggerFactory.getLogger(FacebookAuthUtil.class);

    public static final String FB_AUTH_LOGIN_URL = "https://graph.facebook.com/oauth/access_token";
    public static final String FB_USERINFO_URL = "https://graph.facebook.com/v3.2/me";
    //appid和appSecret 是facebook上申请
    //AppId
    public static final String FB_APP_ID = "15751398427*****";
    //AppSecret
    public static final String FB_APP_KEY = "ac6fb2cda5d855fc20920289a4d*****";
    //获取用户的那些信息
    public static final String FB_USER_FIELDS = "id,cover,email,gender,name,languages,timezone,third_party_id,updated_time";


    public static FacebookUserInfo checkLoginWithToken(String accessToken) {
        try {
            String userUrl = String.format("%s?access_token=%s&fields=%s",
                    FB_USERINFO_URL, accessToken, FB_USER_FIELDS);
            String userRet = HttpClientUtil.getGetResponse(userUrl);
            FacebookUserInfo userInfoData = new FacebookUserInfo();
            JSONObject json = new JSONObject(userRet);
            userInfoData.setName(json.optString("name"));
            userInfoData.setFacebookUserId(json.optString("id"));
            return userInfoData;
        } catch (Exception ex) {
            logger.warn("verify the access token failed: {}", ex.getMessage());
            return null;
        }
    }
}

