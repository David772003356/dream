package com.bigdream.dream.utils;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tk.mybatis.mapper.util.StringUtil;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class HttpClientUtil {

	// http url scheme
	private String HTTP_SCHEME = "http";

	// https url scheme
	private String HTTPS_SCHEME = "https";
	// 响应超时时间，超过此时间不再读取响应即数据传输过程中数据包之间间隔的最大时间
	private int socketTimeoutMillis = 10000;
	// 连接建立时间，三次握手完成时间
	private int connectTimeoutMillis = 10000;
	// http clilent中从connetcion pool中获得一个connection的超时时间
	private int connectionRequestTimeout = 10000;
	// 最大不要超过1000
	private int maxTotalConnections = 500;
	// 是单个路由连接的最大数 ,实际的单个连接池大小，如tps定为50，那就配置50
	private int maxPerRouteConnections = 50;
	// 连接的最大生存时间，0表示不限 ,负数表示 Long.MAX_VALUE
	private long timeToLive = -1;
	//The time unit for timeToLive.
	private TimeUnit timeUnit = TimeUnit.MILLISECONDS;
	private CloseableHttpClient httpClient = null;
	private static HttpClientUtil httpClientUtil = null;
	private static ReentrantLock lock = new ReentrantLock();

	private static final Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);

	public HttpClientUtil(int socketTimeoutMillis, int connectTimeoutMillis, int connectionRequestTimeout, int maxTotalConnections, int maxPerRouteConnections, long timeToLive) {
		this.socketTimeoutMillis = socketTimeoutMillis;
		this.connectTimeoutMillis = connectTimeoutMillis;
		this.connectionRequestTimeout = connectionRequestTimeout;
		this.maxTotalConnections = maxTotalConnections;
		this.maxPerRouteConnections = maxPerRouteConnections;
		this.timeToLive = timeToLive;
		initHttpClient();
	}

	/**
	 * initHttpClient
	 */
	public HttpClientUtil() {
		initHttpClient();
	}

	/**
	 * httpInstance
	 * 
	 * @return
	 */
	public static HttpClientUtil getInstance() {
		if (httpClientUtil == null) {
			try {
				lock.lock();
				if (httpClientUtil != null) {
					return httpClientUtil;
				}
				httpClientUtil = new HttpClientUtil();
			} finally {
				lock.unlock();
			}
		}
		return httpClientUtil;
	}

	/**
	 * post请求发送
	 * 
	 * @param url
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public String postFormSend(String url, Map<String, Object> params) throws Exception {
		if (StringUtil.isEmpty(url)) {
			logger.warn("-----------------发送的url为空！");
			return null;
		}
		if ((params == null) || (params.size() == 0)) {
			logger.warn("-----------------发送的content为空！");
			return null;
		}
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		Iterator<String> iterator = params.keySet().iterator();
		while (iterator.hasNext()) {
			String key = iterator.next();
			parameters.add(new BasicNameValuePair(key, String.valueOf(params.get(key))));
		}
		return executePost(url, new UrlEncodedFormEntity(parameters, "UTF-8"), null, null, "UTF-8");
	}

	/**
	 * post请求发送
	 * 
	 * @param url
	 * @param params
	 * @param contentType
	 * @param headers
	 * @param charset
	 * @return
	 * @throws Exception
	 */
	public String postFormSend(String url, Map<String, Object> params, String contentType, Map<String, String> headers, String charset) throws Exception {
		if (StringUtil.isEmpty(url)) {
			logger.warn("-----------------发送的url为空！");
			return null;
		}
		if ((params == null) || (params.size() == 0)) {
			logger.warn("-----------------发送的content为空！");
			return null;
		}
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		Iterator<String> iterator = params.keySet().iterator();
		while (iterator.hasNext()) {
			String key = iterator.next();
			parameters.add(new BasicNameValuePair(key, String.valueOf(params.get(key))));
		}
		return executePost(url, new UrlEncodedFormEntity(parameters, charset), contentType, headers, charset);
	}

	/**
	 * post请求发送
	 * 
	 * @param url
	 * @param data
	 * @return
	 */
	public String postSend(String url, String data) {
		if (StringUtil.isEmpty(url)) {
			logger.warn("-----------------发送的url为空！");
			return null;
		}
		if ((data == null) || (data.trim().length() == 0)) {
			logger.warn("-----------------发送的content为空！");
			return null;
		}
		try {
			return executePost(url, new StringEntity(data, "UTF-8"), null, null, "UTF-8");
		} catch (Exception e) {
			logger.error("************sendData:" + url + data, e);
		}
		return null;
	}

	/**
	 * post请求发送
	 * 
	 * @param url
	 * @param data
	 * @param contentType
	 * @return
	 * @throws Exception
	 */
	public String postSend(String url, String data, String contentType) throws Exception {
		return executePost(url, new StringEntity(data, "UTF-8"), contentType, null, "UTF-8");
	}

	/**
	 * post请求发送
	 * 
	 * @param url
	 * @param data
	 * @param contentType
	 * @param headers
	 * @param charset
	 * @return
	 * @throws Exception
	 */
	public String postSend(String url, String data, String contentType, Map<String, String> headers, String charset) throws Exception {
		return executePost(url, new StringEntity(data, charset), contentType, headers, charset);
	}
	
	/**
	 * put请求发送
	 * 
	 * @param url
	 * @param data
	 * @param contentType
	 * @param headers
	 * @param charset
	 * @return
	 * @throws Exception
	 */
	public String putSend(String url, String data, String contentType, Map<String, String> headers, String charset) throws Exception {
		return executePut(url, new StringEntity(data, charset), contentType, headers, charset);
	}
	
	/**
	 * get请求发送
	 * 
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public String getSend(String url) throws Exception {
		return executeGet(url, null, null);
	}

	 

	public String uploadImage(String url, HttpEntity reqEntity, String contentType, Map<String, String> headers) throws Exception {
		HttpPost httppost = null;
		CloseableHttpResponse response = null;
		BufferedReader bufferedReader = null;
		long s = System.currentTimeMillis();
		try {
			httppost = new HttpPost(url);
			if (StringUtil.isNotEmpty(contentType)) {
				httppost.addHeader("Content-Type", contentType);
			}

			if ((headers != null) && (headers.size() > 0)) {
				Iterator<String> iterator = headers.keySet().iterator();
				while (iterator.hasNext()) {
					String key = (String) iterator.next();
					httppost.addHeader(key, (String) headers.get(key));
				}
			}

			httppost.setEntity(reqEntity);
			response = this.httpClient.execute(httppost);
			HttpEntity resEntity = response.getEntity();
			if ((resEntity != null) && (response.getStatusLine().getStatusCode() == 200)) {
				HttpEntity resultEntity = response.getEntity();
				if (resultEntity != null) {
					StringBuffer resultBuffer = new StringBuffer();
					bufferedReader = new BufferedReader(new InputStreamReader(resultEntity.getContent(), "UTF-8"));
					String line = null;
					while ((line = bufferedReader.readLine()) != null) {
						resultBuffer.append(line);
					}
					logger.debug("-----------------发送成功,返回结果:" + resultBuffer + ",耗时:" + (System.currentTimeMillis() - s));
					return resultBuffer.toString();
				}
				logger.warn("=============发送失败，返回的Entity为空！url:" + url + ",耗时:" + (System.currentTimeMillis() - s));
			} else {
				logger.warn("=============发送失败,statusCode:" + response.getStatusLine().getStatusCode() + ",url:" + url + ",耗时:" + (System.currentTimeMillis() - s));
			}
		} catch (Exception e) {
			logger.error("*********uploadImage:url:" + url + "***sendData:" + ",耗时:" + (System.currentTimeMillis() - s), e);
			throw e;
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (httppost != null) {
				httppost.releaseConnection();
			}
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
 

 
 
 
	public String executeGet(String url, String contentType, Map<String, String> headers) throws Exception {
		long s = System.currentTimeMillis();
		HttpGet httpGet = null;
		CloseableHttpResponse resoponse = null;
		BufferedReader bufferedReader = null;
		try {
			if (StringUtil.isEmpty(url)) {
				logger.warn("-----------------发送的url为空！");
				return null;
			}
			httpGet = new HttpGet(url.trim());
			if (StringUtil.isNotEmpty(contentType)) {
				httpGet.addHeader("Content-Type", contentType);
			}

			if ((headers != null) && (headers.size() > 0)) {
				Iterator<String> iterator = headers.keySet().iterator();
				while (iterator.hasNext()) {
					String key = (String) iterator.next();
					httpGet.addHeader(key, (String) headers.get(key));
				}
			}
			resoponse = this.httpClient.execute(httpGet);
			if (resoponse.getStatusLine().getStatusCode() == 200) {
				HttpEntity resultEntity = resoponse.getEntity();
				if (resultEntity != null) {
					StringBuffer resultBuffer = new StringBuffer();
					bufferedReader = new BufferedReader(new InputStreamReader(resultEntity.getContent(), "UTF-8"));
					String line = null;
					while ((line = bufferedReader.readLine()) != null) {
						resultBuffer.append(line);
					}
					return resultBuffer.toString();
				}
				logger.warn("=============发送失败，返回的Entity为空！url:" + url + ",耗时:" + (System.currentTimeMillis() - s));

			} else {
				logger.warn("=============发送失败,statusCode:" + resoponse.getStatusLine().getStatusCode() + ",url:" + url + ",耗时:" + (System.currentTimeMillis() - s));
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (httpGet != null) {
				httpGet.releaseConnection();
			}
			if (resoponse != null) {
				try {
					resoponse.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	private String executePost(String url, HttpEntity sendEntity, String contentType, Map<String, String> headers, String charset) throws Exception {
		if (sendEntity == null) {
			logger.warn("-----------------发送的sendEntity为空！");
			return null;
		}
		if (StringUtil.isEmpty(url)) {
			logger.warn("-----------------发送的url为空！");
			return null;
		}
		long s = System.currentTimeMillis();
		HttpPost httpPost = null;
		CloseableHttpResponse resoponse = null;
		BufferedReader bufferedReader = null;
		try {
			httpPost = new HttpPost(url.trim());
			httpPost.setEntity(sendEntity);
			if (StringUtil.isNotEmpty(contentType)) {
				httpPost.addHeader("Content-Type", contentType);
			}
			if ((headers != null) && (headers.size() > 0)) {
				Iterator<String> iterator = headers.keySet().iterator();
				while (iterator.hasNext()) {
					String key = (String) iterator.next();
					httpPost.addHeader(key, (String) headers.get(key));
				}
			}
			resoponse = this.httpClient.execute(httpPost);
			if (resoponse.getStatusLine().getStatusCode() == 200) {
				HttpEntity resultEntity = resoponse.getEntity();
				if (resultEntity != null) {
					StringBuffer resultBuffer = new StringBuffer();
					bufferedReader = new BufferedReader(new InputStreamReader(resultEntity.getContent(), charset));
					String line = null;
					while ((line = bufferedReader.readLine()) != null) {
						resultBuffer.append(line);
					}
					return resultBuffer.toString();
				}
				logger.warn("=============发送失败，返回的Entity为空！url:" + url + ",耗时:" + (System.currentTimeMillis() - s));

			} else {
				HttpEntity resultEntity = resoponse.getEntity();
				StringBuffer resultBuffer = new StringBuffer();
				if (resultEntity != null) {
					bufferedReader = new BufferedReader(new InputStreamReader(resultEntity.getContent(), charset));
					String line = null;
					while ((line = bufferedReader.readLine()) != null) {
						resultBuffer.append(line);
					}
					 resultBuffer.toString();
				}
				logger.warn("=============发送失败,statusCode:" + resoponse.getStatusLine().getStatusCode() + ",url:" + url + ",耗时:" + (System.currentTimeMillis() - s)+",response:{}",resultBuffer);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (httpPost != null) {
				httpPost.releaseConnection();
			}
			if (resoponse != null) {
				try {
					resoponse.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	
	
	private String executePut(String url, HttpEntity sendEntity, String contentType, Map<String, String> headers, String charset) throws Exception {
		if (sendEntity == null) {
			logger.warn("-----------------发送的sendEntity为空！");
			return null;
		}
		if (StringUtil.isEmpty(url)) {
			logger.warn("-----------------发送的url为空！");
			return null;
		}
		long s = System.currentTimeMillis();
		HttpPut httpPut = null;
		CloseableHttpResponse resoponse = null;
		BufferedReader bufferedReader = null;
		try {
			httpPut = new HttpPut(url.trim());
			httpPut.setEntity(sendEntity);
			if (StringUtil.isNotEmpty(contentType)) {
				httpPut.addHeader("Content-Type", contentType);
			}
			if ((headers != null) && (headers.size() > 0)) {
				Iterator<String> iterator = headers.keySet().iterator();
				while (iterator.hasNext()) {
					String key = (String) iterator.next();
					httpPut.addHeader(key, (String) headers.get(key));
				}
			}
			resoponse = this.httpClient.execute(httpPut);
			if (resoponse.getStatusLine().getStatusCode() == 200) {
				HttpEntity resultEntity = resoponse.getEntity();
				if (resultEntity != null) {
					StringBuffer resultBuffer = new StringBuffer();
					bufferedReader = new BufferedReader(new InputStreamReader(resultEntity.getContent(), charset));
					String line = null;
					while ((line = bufferedReader.readLine()) != null) {
						resultBuffer.append(line);
					}
					return resultBuffer.toString();
				}
				logger.warn("=============发送失败，返回的Entity为空！url:" + url + ",耗时:" + (System.currentTimeMillis() - s));

			} else {
				HttpEntity resultEntity = resoponse.getEntity();
				StringBuffer resultBuffer = new StringBuffer();
				if (resultEntity != null) {
					bufferedReader = new BufferedReader(new InputStreamReader(resultEntity.getContent(), charset));
					String line = null;
					while ((line = bufferedReader.readLine()) != null) {
						resultBuffer.append(line);
					}
					 resultBuffer.toString();
				}
				logger.warn("=============发送失败,statusCode:" + resoponse.getStatusLine().getStatusCode() + ",url:" + url + ",耗时:" + (System.currentTimeMillis() - s)+",response:{}",resultBuffer);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (httpPut != null) {
				httpPut.releaseConnection();
			}
			if (resoponse != null) {
				try {
					resoponse.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	public void initHttpClient() {
		try {
			logger.debug("-----------------initHttpsClient!");

			this.httpClient = HttpClients.custom().disableContentCompression().useSystemProperties().setRetryHandler(new DefaultHttpRequestRetryHandler(0, false))
					.setConnectionManager(getConnectionManager()).setDefaultRequestConfig(getRequestConfig()).build();

		} catch (Exception e) {
			logger.error("***initHttpClient:", e);
		}
	}

	protected RequestConfig getRequestConfig() {
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(getSocketTimeoutMillis()).setConnectTimeout(getConnectTimeoutMillis())
				.setConnectionRequestTimeout(getConnectionRequestTimeout()).setCookieSpec(CookieSpecs.IGNORE_COOKIES).build();
		return requestConfig;
	}

	protected PoolingHttpClientConnectionManager getConnectionManager() {
		try {
			final SSLContext sslContext = SSLContext.getInstance("SSL");
			sslContext.init(null, new TrustManager[] { new X509TrustManager() {
				@Override
				public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
				}

				@Override
				public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
				}

				@Override
				public X509Certificate[] getAcceptedIssuers() {
					return null;
				}
			} }, new SecureRandom());

			RegistryBuilder<ConnectionSocketFactory> registryBuilder = RegistryBuilder.<ConnectionSocketFactory>create().register(HTTP_SCHEME,
					PlainConnectionSocketFactory.INSTANCE);

			registryBuilder.register(HTTPS_SCHEME, new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE));
			final Registry<ConnectionSocketFactory> registry = registryBuilder.build();

			PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(registry, null, null, null, getTimeToLive(), getTimeUnit());
			connectionManager.setMaxTotal(getMaxTotalConnections());
			connectionManager.setDefaultMaxPerRoute(getMaxPerRouteConnections());
			// HttpHost httpHost = new HttpHost(hostname, port);
			// // 将目标主机的最大连接数增加
			// cm.setMaxPerRoute(new HttpRoute(httpHost), maxRoute);

			// /**
			// * socket配置（默认配置 和 某个host的配置）
			// */
			// SocketConfig socketConfig = SocketConfig.custom()
			// .setTcpNoDelay(true) //是否立即发送数据，设置为true会关闭Socket缓冲，默认为false
			// .setSoReuseAddress(true) //是否可以在一个进程关闭Socket后，即使它还没有释放端口，其它进程还可以立即重用端口
			// .setSoTimeout(500) //接收数据的等待超时时间，单位ms
			// .setSoLinger(60) //关闭Socket时，要么发送完所有数据，要么等待60s后，就关闭连接，此时socket.close()是阻塞的
			// .setSoKeepAlive(true) //开启监视TCP连接是否有效
			// .build();
			// connManager.setDefaultSocketConfig(socketConfig);
			return connectionManager;
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	/**
	 * @Description 创建一个不进行正式验证的请求客户端对象 不用导入SSL证书
	 * @return HttpClient
	 */
	public static CloseableHttpClient wrapClient() {
		try {
			SSLContext ctx = SSLContext.getInstance("TLS");
			X509TrustManager tm = new X509TrustManager() {
				public X509Certificate[] getAcceptedIssuers() {
					return null;
				}

				public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
				}

				public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
				}
			};
			ctx.init(null, new TrustManager[] { tm }, null);
			SSLConnectionSocketFactory ssf = new SSLConnectionSocketFactory(ctx, NoopHostnameVerifier.INSTANCE);
			CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(ssf).build();
			return httpclient;
		} catch (Exception e) {
			return HttpClients.createDefault();
		}
	}

	public void closeHttpClient() {
		try {
			logger.debug("-----------------closeHttpClient!");

			if (this.httpClient != null)
				this.httpClient.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int getMaxTotalConnections() {
		return maxTotalConnections;
	}

	public void setMaxTotalConnections(int maxTotalConnections) {
		this.maxTotalConnections = maxTotalConnections;
	}

	public int getMaxPerRouteConnections() {
		return maxPerRouteConnections;
	}

	public void setMaxPerRouteConnections(int maxPerRouteConnections) {
		this.maxPerRouteConnections = maxPerRouteConnections;
	}

	public long getTimeToLive() {
		return timeToLive;
	}

	public void setTimeToLive(long timeToLive) {
		this.timeToLive = timeToLive;
	}

	public TimeUnit getTimeUnit() {
		return timeUnit;
	}

	public void setTimeUnit(TimeUnit timeUnit) {
		this.timeUnit = timeUnit;
	}

	public int getSocketTimeoutMillis() {
		return socketTimeoutMillis;
	}

	public void setSocketTimeoutMillis(int socketTimeoutMillis) {
		this.socketTimeoutMillis = socketTimeoutMillis;
	}

	public int getConnectTimeoutMillis() {
		return connectTimeoutMillis;
	}

	public void setConnectTimeoutMillis(int connectTimeoutMillis) {
		this.connectTimeoutMillis = connectTimeoutMillis;
	}

	public int getConnectionRequestTimeout() {
		return connectionRequestTimeout;
	}

	public void setConnectionRequestTimeout(int connectionRequestTimeout) {
		this.connectionRequestTimeout = connectionRequestTimeout;
	}
}