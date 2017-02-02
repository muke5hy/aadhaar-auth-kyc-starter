package in.gov.uidai.core.aua.client;


import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

public class HttpClientHelper {

  public static void acceptAllCertificates() {

// Create a trust manager that does not validate certificate chains
    TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
      public X509Certificate[] getAcceptedIssuers() {
        return null;
      }

      public void checkClientTrusted(X509Certificate[] certs, String authType) {
      }

      public void checkServerTrusted(X509Certificate[] certs, String authType) {
      }
    }};

// Install the all-trusting trust manager
    try {
      SSLContext sc = SSLContext.getInstance("SSL");
      sc.init(null, trustAllCerts, new SecureRandom());
      HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
      HttpsURLConnection.setDefaultHostnameVerifier((s, sslSession) -> true);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static String postAndGetResponse(String uriString, String signedXML) throws URISyntaxException {
    URI authServiceURI = new URI(uriString);

    RestTemplate restTemplate = new RestTemplate();

    restTemplate.getInterceptors().add((httpRequest, bytes, clientHttpRequestExecution) -> {
      httpRequest.getHeaders().clear();
      httpRequest.getHeaders().add("REMOTE_ADDR", "127.0.0.1");
      return clientHttpRequestExecution.execute(httpRequest, bytes);
    });

    ResponseEntity<String> responseEntity = restTemplate.postForEntity(authServiceURI, signedXML, String.class);

    return responseEntity.getBody();
  }

}
