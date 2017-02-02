package com.aadhar.service;

import com.aadhar.resource.AuthRequestData;
import in.gov.uidai.core.aua.client.AuthClient;
import in.gov.uidai.core.device.model.DeviceCollectedAuthData;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

@Service
public class AuthService {

  private final AuthClient authClient;

  public AuthService() throws MalformedURLException, URISyntaxException {
    String authServerURL = "http://core.uidai.gov.in/1.6";
    authClient = new AuthClient(new URL(authServerURL).toURI());
  }

  public boolean authenticate(AuthRequestData authRequestData) {
    DeviceCollectedAuthData authData = new DeviceCollectedAuthData();
    authData.setUid(authRequestData.getUid());
    authData.setName(authRequestData.getName());
    authData.setNameMatchStrategy(authRequestData.getNameMatchStrategy());
    authData.setNameMatchValue(authRequestData.getNameMatchValue());
    authData.setGender(authRequestData.getGender());
    authData.setFullAddress(authRequestData.getFullAddress());
    return authClient.authenticateAuthData(authData);
  }

}
