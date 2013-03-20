package com.chihuo.sns;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class SdkTqq {
	SNSClient client = new SNSClient();

	public AccessToken getAccessTokenByCode(String code) throws SNSException {
		String json = client.post(
				SNSConfig.getTqqAccesstokenURI(),
				new SNSParameter[] {
						new SNSParameter("client_id", SNSConfig
								.getTqqClientID()),
						new SNSParameter("client_secret", SNSConfig
								.getTqqClientSercert()),
						new SNSParameter("redirect_uri", SNSConfig
								.getTqqRedirectURI()),
						new SNSParameter("grant_type", "authorization_code"),
						new SNSParameter("code", code) }).asString();

		System.out.println("TQQ access token" + json);

		AccessToken accessToken = new AccessToken();

		Matcher m = Pattern.compile(
				"^access_token=(\\w+)&expires_in=(\\w+)&refresh_token=(\\w+)&openid=(\\w+)")
				.matcher(json);
		if (m.find()) {
			accessToken.setAccessToken(m.group(1));
			accessToken.setExpireIn(Long.parseLong(m.group(2)));
			accessToken.setRefreshToken(m.group(3));
			accessToken.setUid(m.group(4));
		}
		
		if (StringUtils.isEmpty(accessToken.getAccessToken())) {
			throw new SNSException("获取AccessToken出错");
		}

		return accessToken;
	}

	public UserInfo showUserById(String uid, String token) throws SNSException {
		JSONObject json = client.get(
				SNSConfig.getTqqBaseURI() + "user/info",
				new SNSParameter[] { new SNSParameter("openid", uid),
					new SNSParameter("oauth_consumer_key", SNSConfig.getTqqClientID()),
//					new SNSParameter("clientip", "192.168.1.1"),
					new SNSParameter("oauth_version", "2.a"),
						new SNSParameter("access_token", token) })
				.asJSONObject();
		
		System.out.println("TQQ userinfo:" + json);
		
		UserInfo userInfo = new UserInfo();
		try {
			JSONObject data = json.getJSONObject("data");
			userInfo.setName(data.getString("nick"));
			userInfo.setThumbnail(data.getString("head"));
		} catch (JSONException jsone) {
			throw new SNSException(jsone.getMessage() + ":" + json.toString(),
					jsone);
		}
		return userInfo;
	}

}
