package com.mukeshyadav.aadhar.resource;

import in.gov.uidai.core.model.xsd.auth.uid_auth_request_data._1.MatchingStrategy;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class AuthRequestData {

  private String uid;
  private String name;
  private MatchingStrategy nameMatchStrategy;
  private int nameMatchValue;
  private String gender;
  private String fullAddress;

  public String getUid() {
    return uid;
  }

  public void setUid(String uid) {
    this.uid = uid;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public MatchingStrategy getNameMatchStrategy() {
    return nameMatchStrategy;
  }

  public void setNameMatchStrategy(MatchingStrategy nameMatchStrategy) {
    this.nameMatchStrategy = nameMatchStrategy;
  }

  public int getNameMatchValue() {
    return nameMatchValue;
  }

  public void setNameMatchValue(int nameMatchValue) {
    this.nameMatchValue = nameMatchValue;
  }

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public String getFullAddress() {
    return fullAddress;
  }

  public void setFullAddress(String fullAddress) {
    this.fullAddress = fullAddress;
  }
}
