package in.gov.uidai.core.aua.client;

import in.gov.uidai.core.aua.helper.AadhaarConstants;
import in.gov.uidai.core.aua.helper.AuthRequestCreator;
import in.gov.uidai.core.device.helper.AuthAUADataCreator;
import in.gov.uidai.core.device.helper.Encrypter;
import in.gov.uidai.core.device.helper.PidCreator;
import in.gov.uidai.core.device.model.AuthDataFromDeviceToAUA;
import in.gov.uidai.core.device.model.DeviceCollectedAuthData;
import in.gov.uidai.core.model.xsd.auth.uid_auth_request._1.Auth;
import in.gov.uidai.core.model.xsd.auth.uid_auth_request._1.DataType;
import in.gov.uidai.core.model.xsd.auth.uid_auth_request._1.Uses;
import in.gov.uidai.core.model.xsd.auth.uid_auth_request._1.UsesFlag;
import in.gov.uidai.core.model.xsd.auth.uid_auth_request_data._1.MatchingStrategy;
import in.gov.uidai.core.model.xsd.auth.uid_auth_response._1.AuthResult;
import in.gov.uidai.core.model.xsd.common.types.LocationType;
import in.gov.uidai.core.model.xsd.common.types.Meta;
import in.gov.uidai.core.model.xsd.kyc.uid_kyc_response._1.KycRes;
import in.gov.uidai.core.model.xsd.kyc.uid_kyc_response._1.UidDataType;
import in.gov.uidai.core.util.CustomBase64;
import in.gov.uidai.core.util.XMLUtilities;
import org.junit.Ignore;
import org.junit.Test;

import javax.xml.bind.JAXBException;
import java.net.URL;

public class KYCClientTest {

  private KycRes kycResponse = new KycRes();

  private String responseXml = "";

  private String encryptXml;

  private String response = "";

  @Test
  @Ignore
  public void testKycClient() throws Exception {

    String kycServerURL = "http://developer.uidai.gov.in/kyc/1.0";

    try {
      KYCClient kycClient = new KYCClient(new URL(kycServerURL).toURI());

      DeviceCollectedAuthData authData = new DeviceCollectedAuthData();
      authData.setUid("999999990019");
      authData.setName("Shivshankar Choudhury");
      authData.setNameMatchStrategy(MatchingStrategy.E);
      authData.setNameMatchValue(100);

      authData.setGender("Male");
      authData.setFullAddress("");

      AuthAUADataCreator auaDataCreator = new AuthAUADataCreator(new Encrypter(getFileFromResource("uidai_auth_stage.cer")), true);
      Meta deviceMetaData = new Meta();
      deviceMetaData.setFdc("NC");
      deviceMetaData.setIdc("NA");
      deviceMetaData.setLot(LocationType.P);
      deviceMetaData.setLov("560103");
      deviceMetaData.setPip("127.0.0.1");
      deviceMetaData.setUdc("UIDAI:SampleClient");

      authData.setDeviceMetaData(deviceMetaData);

      AuthDataFromDeviceToAUA auaData = auaDataCreator.prepareAUAData(authData.getUid(), "public", authData.getDeviceMetaData(),
          PidCreator.createXmlPid(authData), DataType.X);

      AuthRequestCreator authRequestCreator = new AuthRequestCreator();

      Uses usesElement = new Uses();
      usesElement.setBio(UsesFlag.N);
      usesElement.setOtp(UsesFlag.N);
      usesElement.setPa(UsesFlag.N);
      usesElement.setPfa(UsesFlag.N);
      usesElement.setPi(UsesFlag.N);
      usesElement.setPin(UsesFlag.N);
      usesElement.setBt("FMR");

      String asaLicenseKey = AadhaarConstants.get().asaLicenseKey;

      Auth auth = authRequestCreator.createAuthRequest("public", "public", asaLicenseKey,
          usesElement, null, auaData, authData.getDeviceMetaData());

      String kua = "public";
      String ksaLicense = AadhaarConstants.get().auaLicenseKey;

      String customXML = "";

      String kycResponseXML = kycClient.kycTrans(auth, kua, false, ksaLicense, usesElement, customXML);

      setResponseXml(kycResponseXML);
      setEncryptXml(kycClient.getEncrpytXml());
      if (kycResponseXML.equalsIgnoreCase("E"))//added for handling E response by KSA
      {
        response = "E";
        encryptXml = "E";
      } else {
        displayAuthResults(kycResponseXML);
      }

    } catch (Exception ex) {
      ex.printStackTrace();
    }


  }

  private void displayAuthResults(String kycResponseXML) throws JAXBException {
    KycRes kycRes = (KycRes) XMLUtilities.parseXML(KycRes.class, kycResponseXML);
    System.out.println("KYC Response XML is " + kycRes);
    if (kycRes.getUidData() != null) {
      kycRes.getUidData().setPht(CustomBase64.encodeBase64(kycRes.getUidData().getPht()));
    }
    setKycResponse(kycRes);
    UidDataType uidData = kycRes.getUidData();
    System.out.println("Txn :" + kycRes.getTxn());
    System.out.println("Action Codes:" + kycRes.getActn());

    if (uidData != null) {
      System.out.println("Response UID :" + uidData.getUid());
    }

    if (kycRes.getRet().toString().equals(AuthResult.Y.toString())) {
      response = "Y";
      //System.out.println("Response :"+response);
      System.out.println("Response :" + response);
    } else {
      response = "N";
      //System.out.println("Response :"+response+" Error code: " + kycRes.getErr()) ;
      System.out.println("Response :" + response + " Error code: " + kycRes.getErr());
    }
  }

  private String getFileFromResource(String fileName) {
    return this.getClass().getClassLoader().getResource(fileName).getFile();
  }

  public String getEncryptXml() {
    return encryptXml;
  }

  public void setEncryptXml(String encrptXml) {
    this.encryptXml = encrptXml;
  }

  private void setKycResponse(KycRes kycResponse) {
    this.kycResponse = kycResponse;
  }

  private void setResponseXml(String responseXml) {
    this.responseXml = responseXml;
  }

}