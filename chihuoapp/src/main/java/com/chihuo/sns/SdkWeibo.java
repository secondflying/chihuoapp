package com.chihuo.sns;

import org.json.JSONException;
import org.json.JSONObject;

public class SdkWeibo {
	SNSClient client = new SNSClient();

	public AccessToken getAccessTokenByCode(String code) throws SNSException {
		JSONObject json = client.post(
				SNSConfig.getWeiboAccesstokenURI(),
				new SNSParameter[] {
						new SNSParameter("client_id", SNSConfig
								.getWeiboClientID()),
						new SNSParameter("client_secret", SNSConfig
								.getWeiboClientSercert()),
						new SNSParameter("redirect_uri", SNSConfig
								.getWeiboRedirectURI()),
						new SNSParameter("grant_type", "authorization_code"),
						new SNSParameter("code", code) }).asJSONObject();

		System.out.println("WEIBO access token:" + json);
		
		AccessToken accessToken = new AccessToken();
		try {
			accessToken.setAccessToken(json.getString("access_token"));
			accessToken.setExpireIn(json.getLong("expires_in"));
			accessToken.setRefreshToken(json.has("refresh_token") ? json
					.getString("refresh_token") : "");
			accessToken.setUid(json.getString("uid"));
		} catch (JSONException je) {
			throw new SNSException(je.getMessage() + ":" + json.toString(), je);
		}
		return accessToken;
	}

	public UserInfo showUserById(String uid, String token) throws SNSException {
		JSONObject json = client.get(
				SNSConfig.getWeiboBaseURI() + "users/show.json",
				new SNSParameter[] { new SNSParameter("uid", uid),
						new SNSParameter("access_token", token) })
				.asJSONObject();
		
		System.out.println("WEIBO userinfo:" + json);
		
		UserInfo userInfo = new UserInfo();
		try {
			userInfo.setName(json.getString("name"));
			userInfo.setThumbnail(json.getString("profile_image_url"));
		} catch (JSONException jsone) {
			throw new SNSException(jsone.getMessage() + ":" + json.toString(),
					jsone);
		}
		return userInfo;
	}

}
