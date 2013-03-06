package com.chihuo.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.jpush.api.ErrorCodeEnum;
import cn.jpush.api.JPushClient;
import cn.jpush.api.MessageResult;

import com.chihuo.bussiness.Device;
import com.chihuo.util.CodeNotificationType;
import com.chihuo.util.PublicConfig;

@Service
@Transactional
public class NotificationService {

	public void sendNotificationToWaiter(String message, String title,
			Integer oid, Device device) {
//		if (device != null) {
//			Map<String, Object> extra = new HashMap<String, Object>();
//			extra.put("oid", oid);
//
//			JPushClient jpush = new JPushClient(PublicConfig.getJWaiterName(),
//					PublicConfig.getJWaiterPassword(),
//					PublicConfig.getJWaiterAppKey());
//			int sendNo = 1;
//			MessageResult msgResult = jpush.sendNotificationWithImei(sendNo,
//					device.getDeviceid(), title, message, 1, extra);
//			if (null != msgResult) {
//				if (msgResult.getErrcode() == ErrorCodeEnum.NOERROR.value()) {
//					System.out.println("发送成功， sendNo=" + msgResult.getSendno());
//				} else {
//					System.out.println("发送失败， 错误代码=" + msgResult.getErrcode()
//							+ ", 错误消息=" + msgResult.getErrmsg());
//				}
//			} else {
//				System.out.println("无法获取数据");
//			}
//		}
	}

	public void sendMessageToWaiter(String message, CodeNotificationType ntype,
			Device device) {
//		if (device != null) {
//			JPushClient jpush = new JPushClient(PublicConfig.getJWaiterName(),
//					PublicConfig.getJWaiterPassword(),
//					PublicConfig.getJWaiterAppKey());
//			int sendNo = 1;
//			MessageResult msgResult = jpush.sendCustomMessageWithImei(sendNo,
//					device.getDeviceid(), ntype.toString(), message);
//			if (null != msgResult) {
//				if (msgResult.getErrcode() == ErrorCodeEnum.NOERROR.value()) {
//					System.out.println("发送成功， sendNo=" + msgResult.getSendno());
//				} else {
//					System.out.println("发送失败， 错误代码=" + msgResult.getErrcode()
//							+ ", 错误消息=" + msgResult.getErrmsg());
//				}
//			} else {
//
//				System.out.println("无法获取数据");
//			}
//		}
	}

	public void sendNotifcationToUser(String message,
			CodeNotificationType ntype, Device device) {
//		if (device != null) {
//			JPushClient jpush = new JPushClient(PublicConfig.getJUserName(),
//					PublicConfig.getJUserPassword(),
//					PublicConfig.getJUserAppKey());
//			int sendNo = 1;
//			MessageResult msgResult = jpush.sendCustomMessageWithImei(sendNo,
//					device.getDeviceid(), ntype.toString(), message);
//			if (null != msgResult) {
//				if (msgResult.getErrcode() == ErrorCodeEnum.NOERROR.value()) {
//					System.out.println("发送成功， sendNo=" + msgResult.getSendno());
//				} else {
//					System.out.println("发送失败， 错误代码=" + msgResult.getErrcode()
//							+ ", 错误消息=" + msgResult.getErrmsg());
//				}
//			} else {
//				System.out.println("无法获取数据");
//			}
//		}
	}

}