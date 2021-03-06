package com.leoleo.film.utils.wechat;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.leoleo.film.utils.HttpClientUtil;
import com.leoleo.film.utils.StringTools;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class WechatUtils {

    private static int socketTimeout = 10000;// 连接超时时间，默认10秒
    private static int connectTimeout = 30000;// 传输超时时间，默认30秒


    /**
     * 获取用户信息
     * @param openId 用户标识
     * @return WeixinUserInfo
     */
    public static JSONObject getUserInfo(String appid, String secret, String openId) {
        // 拼接请求地址
        String requestUrl = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID";
       String access_token = getAccessToken(appid,secret);
       if (!StringTools.isNullOrEmpty(access_token)) {
           requestUrl = requestUrl.replace("ACCESS_TOKEN", access_token).replace("OPENID", openId);
           // 获取用户信息
           String result = HttpClientUtil.getGetResponse(requestUrl);
           JSONObject json = JSON.parseObject(result);
           return json;
       }
       return null;
    }
    /**
     * 获取accessToken
     * @param appid
     * @param secret
     * @return accessToken 接口访问凭证
     *
     */
    public static synchronized String getAccessToken(String appid, String secret) {
        String access_token =null;
        // 拼接请求地址
        String requestUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=SECRET";
        requestUrl = requestUrl.replace("APPID", appid).replace("SECRET", secret);
        // 获取accessToken
        String result = HttpClientUtil.getGetResponse(requestUrl);
        if (StringTools.isNullOrEmpty(result)||null==JSON.parseObject(result).get("access_token")){
            log.error("获取accessToken返回值为null ");
            result = HttpClientUtil.getGetResponse(requestUrl);
            int i =1;
            while (i<6){
                if (StringTools.isNullOrEmpty(result)||null==JSON.parseObject(result).get("access_token")) {
                    log.error("获取accessToken失败,重新尝试第"+i+"次");
                    result = HttpClientUtil.getGetResponse(requestUrl);
                }
                i++;
            }
        }
        if (!StringTools.isNullOrEmpty(result)&&null!=JSON.parseObject(result).get("access_token")){
            access_token=JSON.parseObject(result).get("access_token").toString();
            log.error("获取accessToken返回值为"+access_token);
        }
        return access_token;
    }

    /**
     * 发送模板消息
     * @param
     * @return WeixinUserInfo
     */
    public static boolean sendTemplateMessage(String access_token ,String  sendJson) {
        // 拼接请求地址
//        access_token = getAccessToken("wxc9f8070bf847afdb","ff9abc64cb6ce1965deacbc9a40d6e65");
//        log.warn("accesstoken为: "+access_token);
//        String officialAccountsOpenId = "oMImQ0bVScpLH7-PVo4nfM7vjyog";
        String requestUrl = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";
        requestUrl = requestUrl.replace("ACCESS_TOKEN",access_token);
//        Miniprogram miniprogram = new Miniprogram();
//        miniprogram.setAppid("wxcb530c140be871b2");
//        miniprogram.setPagepath("pages/myWaitting/myWaitting");
//        JSONObject json = new JSONObject();
//        TemplateSendData data = new TemplateSendData();
//        data.setFirst(new Keyword("你好,你有一个新的会议","#173177"));
//        data.setKeyword1(new Keyword("名称","#173177"));
//        data.setKeyword2(new Keyword("时间","#173177"));
//        data.setKeyword3(new Keyword("地点地点地点地点地点地点地点地点地点地点地点地点地点地点地点地点地点地点地点地点地点","#173177"));
//        data.setRemark(new Keyword("请及时参加会议","#173177"));
//        json.put("touser", officialAccountsOpenId);
//        json.put("template_id", "JWcS4nKbNJQV0qyRLk1dLsduX6K-h4NQnTQTBKhOZNU");
////        json.put("url", "http://weixin.qq.com/download");
//        json.put("miniprogram", miniprogram);
//        json.put("data", data);
//        log.warn("请求微信模板消息参数为: " + json.toString());
        log.warn("开始请求微信模板消息: " + requestUrl);
        String wxTemplateSendUrlResult = HttpClientUtil.sendJsonHttpPost(requestUrl,sendJson);
//        String wxTemplateSendUrlResult = HttpClientUtil.sendJsonHttpPost(requestUrl,json.toJSONString());
        log.warn("请求微信模板消息结果为: " + wxTemplateSendUrlResult);
        JSONObject wxTemplateSendUrlResultJson = JSONObject.parseObject(wxTemplateSendUrlResult);
        if (null!=wxTemplateSendUrlResultJson&&wxTemplateSendUrlResultJson.get("errmsg").equals("ok")){
            log.warn("模板消息发送成功");
            return true;
        }else {
            log.error("模板消息发送失败,返回结果为: "+wxTemplateSendUrlResultJson.toJSONString());
        }
        log.error("模板消息发送失败");
        return false;
    }

    public static void main(String[] args) {
//        String access_token = getAccessToken("wxc9f8070bf847afdb","ff9abc64cb6ce1965deacbc9a40d6e65");
//        System.out.println("get accesstoken is "+ access_token);
//        sendTemplateMessage("","");
    }
}
