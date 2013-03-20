package com.chihuo.sns;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class SdkQzone {
	SNSClient client = new SNSClient();

	public AccessToken getAccessTokenByCode(String code) throws SNSException {
		String json = client.post(
				SNSConfig.getQzoneAccesstokenURI(),
				new SNSParameter[] {
						new SNSParameter("client_id", SNSConfig
								.getQzoneClientID()),
						new SNSParameter("client_secret", SNSConfig
								.getQzoneClientSercert()),
						new SNSParameter("redirect_uri", SNSConfig
								.getQzoneRedirectURI()),
						new SNSParameter("grant_type", "authorization_code"),
						new SNSParameter("code", code) }).asString();

		System.out.println("QZONE access token" + json);

		AccessToken accessToken = new AccessToken();

		Matcher m = Pattern.compile(
				"^access_token=(\\w+)&expires_in=(\\w+)&refresh_token=(\\w+)$")
				.matcher(json);
		if (m.find()) {
			accessToken.setAccessToken(m.group(1));
			accessToken.setExpireIn(Long.parseLong(m.group(2)));
			accessToken.setRefreshToken(m.group(3));
		} else {
			Matcher m2 = Pattern.compile(
					"^access_token=(\\w+)&expires_in=(\\w+)$").matcher(json);
			if (m2.find()) {
				accessToken.setAccessToken(m2.group(1));
				accessToken.setExpireIn(Long.parseLong(m2.group(2)));
				accessToken.setRefreshToken("");
			}
		}
		
		if (StringUtils.isEmpty(accessToken.getAccessToken())) {
			throw new SNSException("获取AccessToken出错");
		}

		String jsonp = client.get(
				SNSConfig.getQzoneBaseURI() + "oauth2.0/me",
				new SNSParameter[] { new SNSParameter("access_token",
						accessToken.getAccessToken()) }).asString();

		System.out.println("QZONE Openid:" + jsonp);

		Matcher m3 = Pattern.compile("\"openid\"\\s*:\\s*\"(\\w+)\"").matcher(
				jsonp);
		if (m3.find())
			accessToken.setUid(m3.group(1));
		else {
			throw new SNSException("获取OpenID出错");
		}

		return accessToken;
	}

	public UserInfo showUserById(String uid, String token) throws SNSException {
		JSONObject json = client.get(
				SNSConfig.getQzoneBaseURI() + "user/get_user_info",
				new SNSParameter[] { new SNSParameter("openid", uid),
					new SNSParameter("oauth_consumer_key", SNSConfig.getQzoneClientID()),
						new SNSParameter("access_token", token) })
				.asJSONObject();
		
		System.out.println("QZONE userinfo:" + json);
		
		UserInfo userInfo = new UserInfo();
		try {
			userInfo.setName(json.getString("nickname"));
			userInfo.setThumbnail(json.getString("figureurl_1"));
		} catch (JSONException jsone) {
			throw new SNSException(jsone.getMessage() + ":" + json.toString(),
					jsone);
		}
		return userInfo;
	}

}
