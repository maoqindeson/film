package com.leoleo.film.utils.push;//package io.renren.modules.generator.utils.push;
//
//import com.tencent.xinge.ClickAction;
//import com.tencent.xinge.Message;
//import com.tencent.xinge.Style;
//import com.tencent.xinge.XingeApp;
//import io.renren.modules.generator.utils.StringTools;
//import org.json.JSONObject;
//
//import java.util.List;
//
///**
// * 安卓推送工具类,预先设置好各参数,只需传入username,推送标题,推送内容即可
// *
// * @author hujin
// *
// */
//public class AndroidXingeUtil {
//	private static Message message1;
//	private static XingeApp xinge = new XingeApp(2100270267l, "ffd2826d022dd6e39921d171598ccead");
//
//	public static JSONObject androidPush(String username, String title, String content, String activity) {
//		message1 = new Message();
//		// 设置消息类型为通知
//		message1.setType(Message.TYPE_NOTIFICATION);
//		// 设置通知样式
//		Style style = new Style(1);
//		// 设置样式为响铃且振动且可清楚
//		style = new Style(3, 1, 1, 1, 0);
//		// 设置点击通知点击触发事件
//		ClickAction action = new ClickAction();
//		// 设为点击打开app
//		action.setActionType(ClickAction.TYPE_ACTIVITY);
//		if (!StringTools.isNullOrEmpty(activity)) {
//			action.setActivity(activity);
//			;
//		}
//		// "com.jannual.servicehall.mvp.welfaregiftbox.WelfareGiftBoxActivity"
//		// 设置标题
//		message1.setTitle(title);
//		message1.setContent(content);
//		message1.setStyle(style);
//		message1.setAction(action);
//
//		// 设置允许推送时间范围为全天
//		// TimeInterval acceptTime1 = new TimeInterval(0, 0, 23, 59);
//		// message1.addAcceptTime(acceptTime1);
//		return xinge.pushSingleAccount(0, username, message1);
//	}
//
//	public static JSONObject androidBatchPush(List<String> users, String title, String content, String activity) {
//		message1 = new Message();
//		// 设置消息类型为通知
//		message1.setType(Message.TYPE_NOTIFICATION);
//		// 设置通知样式
//		Style style = new Style(1);
//		// 设置样式为响铃且振动且可清楚
//		style = new Style(3, 1, 1, 1, 0);
//		// 设置点击通知点击触发事件
//		ClickAction action = new ClickAction();
//		// 设为点击打开app
//		action.setActionType(ClickAction.TYPE_ACTIVITY);
//		if (!StringTools.isNullOrEmpty(activity)) {
//			action.setActivity(activity);
//			;
//		}
//		// "com.jannual.servicehall.mvp.welfaregiftbox.WelfareGiftBoxActivity"
//		// 设置标题
//		message1.setTitle(title);
//		message1.setContent(content);
//		message1.setStyle(style);
//		message1.setAction(action);
//
//		// 设置允许推送时间范围为全天
//		// TimeInterval acceptTime1 = new TimeInterval(0, 0, 23, 59);
//		// message1.addAcceptTime(acceptTime1);
//		return xinge.pushAccountList(0, users, message1);
//		// return xinge.pushSingleAccount(0, username, message1);
//	}
//	public static JSONObject androidPushAll(String title, String content, String activity) {
//		message1 = new Message();
//		// 设置消息类型为通知
//		message1.setType(Message.TYPE_NOTIFICATION);
//		// 设置通知样式
//		Style style = new Style(1);
//		// 设置样式为响铃且振动且可清楚
//		style = new Style(3, 1, 1, 1, 0);
//		// 设置点击通知点击触发事件
//		ClickAction action = new ClickAction();
//		// 设为点击打开app
//		action.setActionType(ClickAction.TYPE_ACTIVITY);
//		if (!StringTools.isNullOrEmpty(activity)) {
//			action.setActivity(activity);
//			;
//		}
//		// "com.jannual.servicehall.mvp.welfaregiftbox.WelfareGiftBoxActivity"
//		// 设置标题
//		message1.setTitle(title);
//		message1.setContent(content);
//		message1.setStyle(style);
//		message1.setAction(action);
//		return xinge.pushAllDevice(0,message1);
//	}
//}
