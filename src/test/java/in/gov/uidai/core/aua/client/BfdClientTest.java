package in.gov.uidai.core.aua.client;

import in.gov.uidai.core.aua.helper.AadhaarConstants;
import in.gov.uidai.core.aua.helper.BfdRequestCreator;
import in.gov.uidai.core.device.helper.BfdAUADataCreator;
import in.gov.uidai.core.device.helper.Encrypter;
import in.gov.uidai.core.device.helper.RbdCreator;
import in.gov.uidai.core.device.model.BFDDataFromDeviceToAUA;
import in.gov.uidai.core.device.model.BfdResponseDetails;
import in.gov.uidai.core.device.model.DeviceCollectedBfdData;
import in.gov.uidai.core.model.xsd.common.types._1.FingerPosition;
import in.gov.uidai.core.model.xsd.common.types._1.LocationType;
import in.gov.uidai.core.model.xsd.common.types._1.Meta;
import in.gov.uidai.core.model.xsd.uid_bfd_request._1.Bfd;
import in.gov.uidai.core.model.xsd.uid_bfd_request._1.DataType;
import in.gov.uidai.core.model.xsd.uid_bfd_response._1.BfdRes;
import org.bouncycastle.util.encoders.Base64;
import org.junit.Ignore;
import org.junit.Test;

import java.net.URL;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

public class BfdClientTest {

  @Test
  @Ignore
  public void testBfd(){
    String authServerURL = "http://developer.uidai.gov.in/bfd/1.6";
    try {
      BfdClient bfdClient = new BfdClient(new URL(authServerURL).toURI());

      Meta deviceMetaData = new Meta();
      deviceMetaData.setFdc("NC");
      deviceMetaData.setIdc("NA");
      deviceMetaData.setLot(LocationType.P);
      deviceMetaData.setLov("560103");
      deviceMetaData.setPip("127.0.0.1");
      deviceMetaData.setUdc("UIDAI:SampleClient");

      String biometricContent = "Rk1SACAyMAAAAADkAAgAyQFnAMUAxQEAAAARIQBqAGsgPgCIAG0fRwC2AG2dSQBVAIUjPABuALShMgCxAL0jMAByAM6lPgCmAN2kQQBwAN8qNAB1AN8mPADJAOcgOQA8AOorNABoAOomOQC+AO2fMQDFAPqlSgCvAP8lRQB8AQuhPABwAQ4fMgB7ASqcRADAAS4iNwCkATMeMwCFATYeNwBLATYwMQBWATcoMQCkATecMQBEATwyMgBJAUciQQCkAU8cNQB9AVQWNgCEAVUVRACoAVgYOgBBAV69NgCsAWeYNwAA";

      DeviceCollectedBfdData.BiometricData biometric = new DeviceCollectedBfdData.BiometricData(FingerPosition.LEFT_THUMB, Base64.decode(biometricContent.getBytes()), 1);

      DeviceCollectedBfdData bfdData = new DeviceCollectedBfdData("999999990019", asList(biometric), deviceMetaData);

      BfdAUADataCreator bfdAUADataCreator = new BfdAUADataCreator(new Encrypter(getFileFromResource("uidai_auth_stage.cer")), true);

      BFDDataFromDeviceToAUA bfdDataFromDeviceToAUA = bfdAUADataCreator.prepareAUAData(bfdData.getUid(), "public", bfdData.getDeviceMetaData(),
          RbdCreator.createXmlRbd(bfdData), DataType.X);

      BfdRequestCreator requestCreator = new BfdRequestCreator();
      Bfd bfdRequest = requestCreator.createBfdRequest("public", "public", AadhaarConstants.get().asaLicenseKey,
          bfdDataFromDeviceToAUA, bfdData.getDeviceMetaData());

      BfdResponseDetails bfdResponseDetails = bfdClient.performBfd(bfdRequest);
      BfdRes bfdRes = bfdResponseDetails.getBfdRes();

      assertEquals(bfdRes.getErr(), "error");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private String getFileFromResource(String fileName) {
    return this.getClass().getClassLoader().getResource(fileName).getFile();
  }


}