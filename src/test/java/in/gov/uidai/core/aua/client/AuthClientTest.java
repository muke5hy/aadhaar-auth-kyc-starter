package in.gov.uidai.core.aua.client;

import in.gov.uidai.core.device.model.DeviceCollectedAuthData;
import in.gov.uidai.core.model.xsd.uid_auth_request_data._1.MatchingStrategy;
import org.junit.Test;

import java.net.URL;

import static org.junit.Assert.assertEquals;

public class AuthClientTest {

  @Test
  public void testAuthenticationName(){
    String authServerURL = "http://core.uidai.gov.in/1.6";
    try {
      AuthClient authClient = new AuthClient(new URL(authServerURL).toURI());

      DeviceCollectedAuthData authData = new DeviceCollectedAuthData();
      authData.setUid("999999990019");
      authData.setName("Shivshankar Choudhury");
      authData.setNameMatchStrategy(MatchingStrategy.E);
      authData.setNameMatchValue(100);

      authData.setGender("Male");
      authData.setFullAddress("");

      boolean result = authClient.authenticateAuthData(authData);
      System.out.println("Result from authenticateAuthData : " + result);

      assertEquals(true, result);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testAuthenticationNameNegative(){
    String authServerURL = "http://core.uidai.gov.in/1.6";
    try {
      AuthClient authClient = new AuthClient(new URL(authServerURL).toURI());

      DeviceCollectedAuthData authData = new DeviceCollectedAuthData();
      authData.setUid("999999990019");
      authData.setName("Wrong Name");
      authData.setNameMatchStrategy(MatchingStrategy.E);
      authData.setNameMatchValue(100);

      authData.setGender("Male");
      authData.setFullAddress("");

      boolean result = authClient.authenticateAuthData(authData);
      System.out.println("Result from authenticateAuthData : " + result);

      assertEquals(false, result);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testAuthenticationDoB(){
    String authServerURL = "http://core.uidai.gov.in/1.6";
    try {
      AuthClient authClient = new AuthClient(new URL(authServerURL).toURI());

      DeviceCollectedAuthData authData = new DeviceCollectedAuthData();
      authData.setUid("999999990019");
      authData.setDob("1968-05-13");
      authData.setDobType("V");

      authData.setNameMatchStrategy(MatchingStrategy.E);
      authData.setNameMatchValue(100);

      authData.setGender("Male");
      authData.setFullAddress("");

      boolean result = authClient.authenticateAuthData(authData);
      System.out.println("Result from authenticateAuthData : " + result);

      assertEquals(true, result);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testAuthenticationEmail(){
    String authServerURL = "http://core.uidai.gov.in/1.6";
    try {
      AuthClient authClient = new AuthClient(new URL(authServerURL).toURI());

      DeviceCollectedAuthData authData = new DeviceCollectedAuthData();
      authData.setUid("999999990026");
      authData.setEmail("kma@mailserver.com");

      authData.setNameMatchStrategy(MatchingStrategy.E);
      authData.setNameMatchValue(100);

      authData.setGender("Male");
      authData.setFullAddress("");

      boolean result = authClient.authenticateAuthData(authData);
      System.out.println("Result from authenticateAuthData : " + result);

      assertEquals(true, result);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testAuthenticationPincode(){
    String authServerURL = "http://core.uidai.gov.in/1.6";
    try {
      AuthClient authClient = new AuthClient(new URL(authServerURL).toURI());

      DeviceCollectedAuthData authData = new DeviceCollectedAuthData();
      authData.setUid("999999990026");
      authData.setPinCode("313001");

      authData.setNameMatchStrategy(MatchingStrategy.E);
      authData.setNameMatchValue(100);

      authData.setGender("Male");
      authData.setFullAddress("");

      boolean result = authClient.authenticateAuthData(authData);
      System.out.println("Result from authenticateAuthData : " + result);

      assertEquals(true, result);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testAuthenticationAddress1(){
    String authServerURL = "http://core.uidai.gov.in/1.6";
    try {
      AuthClient authClient = new AuthClient(new URL(authServerURL).toURI());

      DeviceCollectedAuthData authData = new DeviceCollectedAuthData();
      authData.setUid("999999990026");
      authData.setState("Rajasthan");
      authData.setDistrict("Udaipur");
      authData.setVillage("Udaipur");

      authData.setNameMatchStrategy(MatchingStrategy.E);
      authData.setNameMatchValue(100);

      authData.setGender("Male");
      authData.setFullAddress("");

      boolean result = authClient.authenticateAuthData(authData);
      System.out.println("Result from authenticateAuthData : " + result);

      assertEquals(true, result);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}