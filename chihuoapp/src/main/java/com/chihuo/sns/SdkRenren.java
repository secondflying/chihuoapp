package com.chihuo.sns;

import org.json.JSONException;
import org.json.JSONObject;

public class SdkRenren {
	SNSClient client = new SNSClient();

	public AccessToken getAccessTokenByCode(String code) throws SNSException {
		JSONObject json = client.post(
				SNSConfig.getRenrenAccesstokenURI(),
				new SNSParameter[] {
						new SNSParameter("client_id", SNSConfig
								.getRenrenClientID()),
						new SNSParameter("client_secret", SNSConfig
								.getRenrenClientSercert()),
						new SNSParameter("redirect_uri", SNSConfig
								.getRenrenRedirectURI()),
						new SNSParameter("grant_type", "authorization_code"),
						new SNSParameter("code", code) }).asJSONObject();

		System.out.println("RENREN access token" + json);

		AccessToken accessToken = new AccessToken();
		try {
			accessToken.setAccessToken(json.getString("access_token"));
			accessToken.setExpireIn(json.getLong("expires_in"));
			accessToken.setRefreshToken(json.has("refresh_token") ? json
					.getString("refresh_token") : "");
		} catch (JSONException je) {
			throw new SNSException(je.getMessage() + ":" + json.toString(), je);
		}

		JSONObject jsonp = client.post(
				SNSConfig.getRenrenBaseURI(),
				new SNSParameter[] {
						new SNSParameter("v", "1.0"),
						new SNSParameter("format", "JSON"),
						new SNSParameter("access_token", accessToken
								.getAccessToken()),
						new SNSParameter("method", "users.getLoggedInUser") })
				.asJSONObject();

		System.out.println("RENREN Openid:" + jsonp);

		try {
			accessToken.setUid(jsonp.getString("uid"));
		} catch (JSONException je) {
			throw new SNSException(je.getMessage() + ":" + jsonp.toString(), je);
		}

		return accessToken;
	}

	public UserInfo showUserById(String uid, String token) throws SNSException {
		UserInfo userInfo = new UserInfo();
		try {
			JSONObject json = client.post(
					SNSConfig.getRenrenBaseURI(),
					new SNSParameter[] { new SNSParameter("v", "1.0"),
							new SNSParameter("format", "JSON"),
							new SNSParameter("access_token", token),
							new SNSParameter("method", "users.getInfo") })
					.asJSONArray().getJSONObject(0);

			System.out.println("RENREN userinfo:" + json);
			
			userInfo.setName(json.getString("name"));
			userInfo.setThumbnail(json.getString("tinyurl"));
		} catch (JSONException jsone) {
			throw new SNSException(jsone.getMessage());
		}
		return userInfo;
	}

}
