package swsec;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import javax.ws.rs.core.Response;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import swsec.api.Config;

public class RestApiITest {

	private static String EXAMPLE_MESSAGE = "Bienvenidos a Fans de las Aves Chilenas. Soy el administrador.";
	private String jwtToken;
	
	public void setUp() throws ClientProtocolException, IOException {
		jwtToken = Config.USE_JWT_AUTH ? getJWTToken() : null;
	}
	
	private String getJWTToken() throws ClientProtocolException, IOException {
		HttpPost request = new HttpPost("http://127.0.0.1:8080/api/auth/login");
		StringEntity rawData = new StringEntity("{ \"username\": \"" + TestConfig.DEFAULT_USER + "\", \"password\": \"" + TestConfig.DEFAULT_PASSWORD + "\"}");
		request.addHeader("content-type", "application/json");
		request.setEntity(rawData);
		HttpResponse response = HttpClientBuilder.create().build().execute(request);
		String token = response.getFirstHeader("Authorization").getValue();
		return token;
	}

	@Test
	public void checkUser() throws ClientProtocolException, IOException {
		HttpGet request = new HttpGet("http://127.0.0.1:8080/api/users/get/1");
		request.setHeader("Authorization", jwtToken);
		HttpResponse response = HttpClientBuilder.create().build().execute(request);
		String responseString = EntityUtils.toString(response.getEntity(), "UTF-8");
		assertTrue(responseString.contains("jperez"));
	}
	
	@Test
	public void testPost() throws ClientProtocolException, IOException {
		HttpPost request = new HttpPost("http://127.0.0.1:8080/api/posts/add");
		request.setHeader("Authorization", jwtToken);
		StringEntity rawData = new StringEntity("{ \"message\": \"" + EXAMPLE_MESSAGE+ "\" }");
		request.addHeader("content-type", "application/json");
		request.setEntity(rawData);
		HttpResponse response = HttpClientBuilder.create().build().execute(request);
		assertTrue(response.getStatusLine().getStatusCode() == Response.Status.OK.getStatusCode());
	}
	
	@Test
	public void testIsPostCreated() throws ClientProtocolException, IOException {
		HttpGet request = new HttpGet("http://127.0.0.1:8080/api/posts/get");
		request.setHeader("Authorization", jwtToken);
		HttpResponse response = HttpClientBuilder.create().build().execute(request);
		String responseString = EntityUtils.toString(response.getEntity(), "UTF-8");
		assertTrue(responseString.contains(EXAMPLE_MESSAGE));
	}

}
