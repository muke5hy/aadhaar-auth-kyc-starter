package in.gov.uidai.core.aua.client;

import in.gov.uidai.core.aua.helper.AadhaarConstants;
import in.gov.uidai.core.aua.helper.OtpRequestCreator;
import in.gov.uidai.core.device.model.OtpDataFromDeviceToAUA;
import in.gov.uidai.core.device.model.OtpResponseDetails;
import in.gov.uidai.core.model.xsd.common.types._1.LocationType;
import in.gov.uidai.core.model.xsd.common.types._1.Meta;
import in.gov.uidai.core.model.xsd.otp._1.Otp;
import in.gov.uidai.core.model.xsd.otp._1.OtpRes;
import org.junit.Test;

import java.net.URL;

import static org.junit.Assert.assertEquals;

public class OtpClientTest {

  @Test
  public void testOtp(){
    String authServerURL = "http://developer.uidai.gov.in/otp/1.5";
    try {
      OtpClient otpClient = new OtpClient(new URL(authServerURL).toURI());

      Meta deviceMetaData = new Meta();
      deviceMetaData.setFdc("NC");
      deviceMetaData.setIdc("NA");
      deviceMetaData.setLot(LocationType.P);
      deviceMetaData.setLov("560103");
      deviceMetaData.setPip("127.0.0.1");
      deviceMetaData.setUdc("UIDAI:SampleClient");

      OtpRequestCreator otpRequestCreator = new OtpRequestCreator();
      OtpDataFromDeviceToAUA auaData = new OtpDataFromDeviceToAUA("999999990019", "public", OtpDataFromDeviceToAUA.BOTH_EMAIL_SMS_CHANNEL);
      Otp otp = otpRequestCreator.createOtpRequest("public", "public", AadhaarConstants.get().asaLicenseKey, auaData);


      OtpResponseDetails otpResponseDetails = otpClient.generateOtp(otp);
      OtpRes otpRes = otpResponseDetails.getOtpRes();

      assertEquals(otpRes.getRet().value(), "y");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private String getFileFromResource(String fileName) {
    return this.getClass().getClassLoader().getResource(fileName).getFile();
  }


}