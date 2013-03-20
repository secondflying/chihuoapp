package com.chihuo.sns;

import org.json.JSONException;
import org.json.JSONObject;

public class SdkDouban {
	SNSClient client = new SNSClient();

	public AccessToken getAccessTokenByCode(String code) throws SNSException {
		JSONObject json = client.post(
				SNSConfig.getDoubanAccesstokenURI(),
				new SNSParameter[] {
						new SNSParameter("client_id", SNSConfig
								.getDoubanClientID()),
						new SNSParameter("client_secret", SNSConfig
								.getDoubanClientSercert()),
						new SNSParameter("redirect_uri", SNSConfig
								.getDoubanRedirectURI()),
						new SNSParameter("grant_type", "authorization_code"),
						new SNSParameter("code", code) }).asJSONObject();

		System.out.println("DOUBAN access token" + json);

		AccessToken accessToken = new AccessToken();
		try {
			accessToken.setAccessToken(json.getString("access_token"));
			accessToken.setExpireIn(json.getLong("expires_in"));
			accessToken.setRefreshToken(json.has("refresh_token") ? json
					.getString("refresh_token") : "");
			accessToken.setUid(json.getString("douban_user_id"));
		} catch (JSONException je) {
			throw new SNSException(je.getMessage() + ":" + json.toString(), je);
		}
		return accessToken;
	}

	public UserInfo showUserById(String uid, String token) throws SNSException {
		client.addAuthorizationHead("Bearer " + token);
		JSONObject json = client.get(SNSConfig.getDoubanBaseURI() + "user/~me")
				.asJSONObject();

		System.out.println("DOUBAN userinfo:" + json);

		UserInfo userInfo = new UserInfo();
		try {
			userInfo.setName(json.getString("name"));
			userInfo.setThumbnail(json.getString("avatar"));
		} catch (JSONException jsone) {
			throw new SNSException(jsone.getMessage() + ":" + json.toString(),
					jsone);
		}
		return userInfo;
	}

}
