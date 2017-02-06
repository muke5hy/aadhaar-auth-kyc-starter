/*******************************************************************************
 * DISCLAIMER: The sample code or utility or tool described herein
 *    is provided on an "as is" basis, without warranty of any kind.
 *    UIDAI does not warrant or guarantee the individual success
 *    developers may have in implementing the sample code on their
 *    environment. 
 *
 *    UIDAI does not warrant, guarantee or make any representations
 *    of any kind with respect to the sample code and does not make
 *    any representations or warranties regarding the use, results
 *    of use, accuracy, timeliness or completeness of any data or
 *    information relating to the sample code. UIDAI disclaims all
 *    warranties, express or implied, and in particular, disclaims
 *    all warranties of merchantability, fitness for a particular
 *    purpose, and warranties related to the code, or any service
 *    or software related thereto. 
 *
 *    UIDAI is not responsible for and shall not be liable directly
 *    or indirectly for any direct, indirect damages or costs of any
 *    type arising out of use or any action taken by you or others
 *    related to the sample code.
 *
 *    THIS IS NOT A SUPPORTED SOFTWARE.
 ******************************************************************************/
package in.gov.uidai.core.aua.client;

import in.gov.uidai.core.aua.helper.AadhaarConstants;
import in.gov.uidai.core.aua.helper.DigitalSigner;
import in.gov.uidai.core.device.helper.PidCreator;
import in.gov.uidai.core.model.xsd.auth.uid_auth_request._1.Auth;
import in.gov.uidai.core.model.xsd.auth.uid_auth_request._1.Uses;
import in.gov.uidai.core.model.xsd.common.types.YesNoType;
import in.gov.uidai.core.model.xsd.kyc.uid_kyc_request._1.Kyc;
import in.gov.uidai.core.model.xsd.kyc.uid_kyc_request._1.RaType;
import in.gov.uidai.core.model.xsd.kyc.uid_kyc_response._1.Resp;
import in.gov.uidai.core.util.DataDecryptor;
import in.gov.uidai.core.util.XMLUtilities;
import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
import java.io.StringWriter;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * <code>OtpClient</code> class can be used for submitting an OTP Generation
 * request to UIDAI OTP Server, and to get the response back
 *
 * @author UIDAI
 */
public class KYCClient {

  private URI kycServerURI = null;

  public static final String SLASH = "/";

  private String asaLicenseKey;
  private DigitalSigner digitalSignator;
  private DataDecryptor dataDecryptor;
  public static final String ISO_8601_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
  private static final String SIGNATURE_PASSWORD = "public";
  private String encrpytXml;

  public KYCClient(URI kycServerURI) {
    this.kycServerURI = kycServerURI;
    this.asaLicenseKey = AadhaarConstants.get().auaLicenseKey;
    this.digitalSignator = AadhaarConstants.get().digitalSigner;
    this.dataDecryptor = new DataDecryptor(getFileFromResource("Staging_Signature_PrivateKey.p12"), SIGNATURE_PASSWORD.toCharArray(), getFileFromResource("uidai_auth_stage.cer"));
  }

  // Changed for Mobile/Email ID consent and Local  Language required Consent
  String mecType = "N";
  String lrType = "N";
  String deType = "N";
  String pfr = "";     //new field added Pfr (Pdf form required)
  String ver = "";

  public String getEncrpytXml() {
    return encrpytXml;
  }

  public void setEncrpytXml(String encrpytXml) {
    this.encrpytXml = encrpytXml;
  }


  public String kycTrans(Auth auth, String kua, boolean isRcReceived,
                         String ksaLicense, Uses usesElement, String customXML
  ) {
    setAsaLicenseKey(ksaLicense);

    String raType = "";
    if (usesElement.getBt().contains("FIR")
        || usesElement.getBt().contains("FMR")) {
      raType += "F";
    }
    if (usesElement.getBt().contains("IIR")) {
      raType += "I";
    }
    if (usesElement.getOtp().toString().contains("Y")) {
      raType += "O";
    }
    if (raType.isEmpty())
      raType = "F";

    String rcType = "N";
    if (isRcReceived) {
      rcType = "Y";
    }


    try {
      String signedXML = generateSignedAuthXML(auth);
      byte[] codedAuthXML = signedXML
          .getBytes();
      Kyc kyc = new Kyc();
      kyc.setRa(RaType.valueOf(raType));
      kyc.setRc(YesNoType.valueOf(rcType));
      kyc.setMec(YesNoType.valueOf(mecType));
      kyc.setLr(YesNoType.valueOf(lrType));
      kyc.setDe(YesNoType.valueOf(deType));

//			kyc.setVer("1.0");
      kyc.setVer(ver);

      if (ver.equals("2.0")) {
        kyc.setPfr(YesNoType.valueOf(pfr));

      }
      XMLGregorianCalendar calendar = DatatypeFactory
          .newInstance()
          .newXMLGregorianCalendar(
              (GregorianCalendar) GregorianCalendar.getInstance());
      //kyc.setTs(PidCreator.pidTs.getTs());
      //System.out.println("KYC"+PidCreator.pidTs.getTs());
      //if(PidCreator.pidTs != null ){
      if (PidCreator.threadLocalPidTs.get() != null) {
        //kyc.setTs(PidCreator.pidTs.getTs());
        kyc.setTs(PidCreator.threadLocalPidTs.get().getTs());
      } else {
        SimpleDateFormat dateFormat = new SimpleDateFormat(ISO_8601_DATE_FORMAT);
        //Date convertedDate = dateFormat.parse(PidCreator.pidBuilderTs.getTs());
        Date convertedDate = dateFormat.parse(PidCreator.threadLocalPidBuilderTs.get().getTs());
        //System.out.println("convertedDate.toString()== "+convertedDate.toString());
        GregorianCalendar gc = (GregorianCalendar) GregorianCalendar.getInstance();
        gc.setTime(convertedDate);
        kyc.setTs(DatatypeFactory.newInstance().newXMLGregorianCalendar(gc));
      }
      kyc.setRad(codedAuthXML);

      String kycSignedXML;
      if (StringUtils.isBlank(System.getenv("USE_CUSTOM_KYC_XML"))) {
        kycSignedXML = generateSignedKycXML(kyc);
        System.out.println(kycSignedXML);
      } else {
        String customKYCXML = customXML;
        Document kycDOM = XMLUtilities.getDomObject(customKYCXML);
        XMLUtilities.addRarNode(kycDOM, codedAuthXML);
        String updatedCustomKYCXML = XMLUtilities.getString(kycDOM);
        System.out.println(updatedCustomKYCXML);
        kycSignedXML = generateSignedKycXML(updatedCustomKYCXML);
      }

      String uriString = kycServerURI.toString() + (kycServerURI.toString().endsWith("/") ? "" : "/")
          + kua + "/" + auth.getUid().charAt(0) + "/" + auth.getUid().charAt(1) + SLASH + ksaLicense;

      String responseXML = HttpClientHelper.postAndGetResponse(uriString, kycSignedXML);

      System.out.println("\nresp \n" + responseXML);

      Resp resp1 = (Resp) XMLUtilities.parseXML(Resp.class, responseXML);
      if (resp1.getStatus().equalsIgnoreCase("-1")) {
        if (resp1.getKycRes().length == 0) {
          throw new Exception(
              "KYC response xml retured a status of -1, no content found.");
        }
      }
      byte[] kycRes = resp1.getKycRes();
      String xml = "";
      if (resp1.getStatus().equalsIgnoreCase("0")) {
        xml = new String(dataDecryptor.decrypt(kycRes));
//				xml = new String(kycRes); // if private key not present 
      } else {
        xml = new String(kycRes);
      }
      System.out.println(" \n xml \n  " + xml);
      if (StringUtils.isBlank(System.getenv("SKIP_RESP_SIG_VERIFY"))) {
        if (dataDecryptor.verify(xml)) {
          return xml;
        } else {
          throw new Exception(
              "KYC response xml signature verification failed.");
        }
      } else {
        return xml;
      }

    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException("Exception during KYC transaction "
          + e.getMessage(), e);
    }
  }


  private String generateSignedKycXML(Kyc kyc) throws JAXBException,
      Exception {
    StringWriter kycXML = new StringWriter();

    JAXBElement kycElement = new JAXBElement(new QName(
        "http://www.uidai.gov.in/kyc/uid-kyc-request/1.0", "Kyc"),
        Kyc.class, kyc);

    JAXBContext.newInstance(Kyc.class).createMarshaller().marshal(
        kycElement, kycXML);
    boolean includeKeyInfo = true;
    return this.digitalSignator.signXML(kycXML.toString(), includeKeyInfo);

  }

  private String generateSignedKycXML(String kycXML) throws JAXBException,
      Exception {
    boolean includeKeyInfo = true;
    return this.digitalSignator.signXML(kycXML.toString(), includeKeyInfo);

  }

  private String generateSignedAuthXML(Auth auth) throws JAXBException,
      Exception {
    StringWriter authXML = new StringWriter();

    JAXBElement authElement = new JAXBElement(new QName(
        "http://www.uidai.gov.in/auth/uid-auth-request/1.0", "Auth"), Auth.class, auth);

    JAXBContext.newInstance(Auth.class).createMarshaller().marshal(
        authElement, authXML);
    boolean includeKeyInfo = true;

    if (System.getenv().get("SKIP_DIGITAL_SIGNATURE_AUTH_ONLY") != null) {
      return authXML.toString();
    } else {
      return this.digitalSignator.signXML(authXML.toString(),
          includeKeyInfo);
    }
  }

  /**
   * Method to inject an instance of <code>DigitalSigner</code> class.
   *
   * @param digitalSignator
   */
  public void setDigitalSignator(DigitalSigner digitalSignator) {
    this.digitalSignator = digitalSignator;
  }

  public void setAsaLicenseKey(String asaLicenseKey) {
    this.asaLicenseKey = asaLicenseKey;
  }

  /**
   * @param dataDecryptor the dataDecryptor to set
   */
  public void setDataDecryptor(DataDecryptor dataDecryptor) {
    this.dataDecryptor = dataDecryptor;
  }

  //ADDED for mec and lr requirement
  public void setMecLr(boolean isMecRecieved, boolean isLrRecieved) {
    mecType = "N";
    lrType = "N";
    if (isMecRecieved) {
      mecType = "Y";
    }
    if (isLrRecieved) {
      lrType = "Y";
    }

  }

  public void setDe(boolean isDeRecieved) {
    deType = "N";
    if (isDeRecieved) {
      deType = "Y";
    }
  }

  public void setVer(String version) {
    ver = version;
  }

  public void setPfr(String Pfr) {
    pfr = Pfr;
  }

  private String getFileFromResource(String fileName) {
    return this.getClass().getClassLoader().getResource(fileName).getFile();
  }

}
