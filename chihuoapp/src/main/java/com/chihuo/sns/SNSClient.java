package com.chihuo.sns;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.log4j.Logger;
import org.json.JSONException;

public class SNSClient implements java.io.Serializable {

	private static final long serialVersionUID = -176092625883595547L;
	private static final int OK = 200; // OK: Success!
	private static final int NOT_MODIFIED = 304; // Not Modified: There was no
													// new data to return.
	private static final int BAD_REQUEST = 400; // Bad Request: The request was
												// invalid. An accompanying
												// error message will explain
												// why. This is the status code
												// will be returned during rate
												// limiting.
	private static final int NOT_AUTHORIZED = 401; // Not Authorized:
													// Authentication
													// credentials were missing
													// or incorrect.
	private static final int FORBIDDEN = 403; // Forbidden: The request is
												// understood, but it has been
												// refused. An accompanying
												// error message will explain
												// why.
	private static final int NOT_FOUND = 404; // Not Found: The URI requested is
												// invalid or the resource
												// requested, such as a user,
												// does not exists.
	private static final int NOT_ACCEPTABLE = 406; // Not Acceptable: Returned
													// by the Search API when an
													// invalid format is
													// specified in the request.
	private static final int INTERNAL_SERVER_ERROR = 500;// Internal Server
															// Error: Something
															// is broken. Please
															// post to the group
															// so the Weibo team
															// can investigate.
	private static final int BAD_GATEWAY = 502;// Bad Gateway: Weibo is down or
												// being upgraded.
	private static final int SERVICE_UNAVAILABLE = 503;// Service Unavailable:
														// The Weibo servers are
														// up, but overloaded
														// with requests. Try
														// again later. The
														// search and trend
														// methods use this to
														// indicate when you are
														// being rate limited.

	static Logger log = Logger.getLogger(SNSClient.class.getName());

	org.apache.commons.httpclient.HttpClient client = null;

	private MultiThreadedHttpConnectionManager connectionManager;

	public SNSClient() {
		this(150, 30000, 30000, 1024 * 1024);
	}

	public SNSClient(int maxConPerHost, int conTimeOutMs, int soTimeOutMs,
			int maxSize) {
		connectionManager = new MultiThreadedHttpConnectionManager();
		HttpConnectionManagerParams params = connectionManager.getParams();
		params.setDefaultMaxConnectionsPerHost(maxConPerHost);
		params.setConnectionTimeout(conTimeOutMs);
		params.setSoTimeout(soTimeOutMs);

		HttpClientParams clientParams = new HttpClientParams();
		// 忽略cookie 避免 Cookie rejected 警告
		clientParams.setCookiePolicy(CookiePolicy.IGNORE_COOKIES);
		client = new org.apache.commons.httpclient.HttpClient(clientParams,
				connectionManager);
		Protocol myhttps = new Protocol("https", new MySSLSocketFactory(), 443);
		Protocol.registerProtocol("https", myhttps);
	}

	private static void log(String message) {
		log.debug(message);
	}

	public void addAuthorizationHead(String auth) {
		List<Header> headers = new ArrayList<Header>();
		headers.add(new Header("Authorization", auth));
		client.getHostConfiguration().getParams()
				.setParameter("http.default-headers", headers);
	}

	public SNSResponse get(String url) throws SNSException {
		return get(url, new SNSParameter[0]);
	}

	public SNSResponse get(String url, SNSParameter[] params)
			throws SNSException {
		log("Request:");
		log("GET:" + url);
		if (null != params && params.length > 0) {
			String encodedParams = SNSClient.encodeParameters(params);
			if (-1 == url.indexOf("?")) {
				url += "?" + encodedParams;
			} else {
				url += "&" + encodedParams;
			}
		}
		GetMethod getmethod = new GetMethod(url);
		return httpRequest(getmethod);

	}

	public SNSResponse delete(String url, SNSParameter[] params)
			throws SNSException {
		if (0 != params.length) {
			String encodedParams = SNSClient.encodeParameters(params);
			if (-1 == url.indexOf("?")) {
				url += "?" + encodedParams;
			} else {
				url += "&" + encodedParams;
			}
		}
		DeleteMethod deleteMethod = new DeleteMethod(url);
		return httpRequest(deleteMethod);

	}

	public SNSResponse post(String url, SNSParameter[] params)
			throws SNSException {
		log("Request:");
		log("POST" + url);
		PostMethod postMethod = new PostMethod(url);
		for (int i = 0; i < params.length; i++) {
			postMethod.addParameter(params[i].getName(), params[i].getValue());
		}
		HttpMethodParams param = postMethod.getParams();
		param.setContentCharset("UTF-8");
		return httpRequest(postMethod);
	}

	public SNSResponse httpRequest(HttpMethod method) throws SNSException {
		int responseCode = -1;
		try {

			method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
					new DefaultHttpMethodRetryHandler(3, false));
			client.executeMethod(method);
			responseCode = method.getStatusCode();

			SNSResponse response = new SNSResponse();
			response.setResponseAsString(method.getResponseBodyAsString());

			if (responseCode != OK) {
				try {
					throw new SNSException(getCause(responseCode),
							response.asJSONObject(), method.getStatusCode());
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			return response;

		} catch (IOException ioe) {
			throw new SNSException(ioe.getMessage(), ioe, responseCode);
		} finally {
			method.releaseConnection();
		}

	}

	public static String encodeParameters(SNSParameter[] postParams) {
		StringBuffer buf = new StringBuffer();
		for (int j = 0; j < postParams.length; j++) {
			if (j != 0) {
				buf.append("&");
			}
			try {
				buf.append(URLEncoder.encode(postParams[j].getName(), "UTF-8"))
						.append("=")
						.append(URLEncoder.encode(postParams[j].getValue(),
								"UTF-8"));
			} catch (java.io.UnsupportedEncodingException neverHappen) {
			}
		}
		return buf.toString();
	}

	private static String getCause(int statusCode) {
		String cause = null;
		switch (statusCode) {
		case NOT_MODIFIED:
			break;
		case BAD_REQUEST:
			cause = "The request was invalid.  An accompanying error message will explain why. This is the status code will be returned during rate limiting.";
			break;
		case NOT_AUTHORIZED:
			cause = "Authentication credentials were missing or incorrect.";
			break;
		case FORBIDDEN:
			cause = "The request is understood, but it has been refused.  An accompanying error message will explain why.";
			break;
		case NOT_FOUND:
			cause = "The URI requested is invalid or the resource requested, such as a user, does not exists.";
			break;
		case NOT_ACCEPTABLE:
			cause = "Returned by the Search API when an invalid format is specified in the request.";
			break;
		case INTERNAL_SERVER_ERROR:
			cause = "Something is broken.  Please post to the group so the Weibo team can investigate.";
			break;
		case BAD_GATEWAY:
			cause = "Weibo is down or being upgraded.";
			break;
		case SERVICE_UNAVAILABLE:
			cause = "Service Unavailable: The Weibo servers are up, but overloaded with requests. Try again later. The search and trend methods use this to indicate when you are being rate limited.";
			break;
		default:
			cause = "";
		}
		return statusCode + ":" + cause;
	}

}
