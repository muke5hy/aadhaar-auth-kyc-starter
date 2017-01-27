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
package in.gov.uidai.auth.aua.httpclient;

import in.gov.uidai.auth.aua.helper.AuthRequestCreator;
import in.gov.uidai.auth.aua.helper.DigitalSigner;
import in.gov.uidai.auth.device.helper.AuthAUADataCreator;
import in.gov.uidai.auth.device.helper.Encrypter;
import in.gov.uidai.auth.device.helper.PidCreator;
import in.gov.uidai.auth.device.model.AuthDataFromDeviceToAUA;
import in.gov.uidai.auth.device.model.AuthResponseDetails;
import in.gov.uidai.auth.device.model.DeviceCollectedAuthData;
import in.gov.uidai.authentication.common.types._1.LocationType;
import in.gov.uidai.authentication.common.types._1.Meta;
import in.gov.uidai.authentication.uid_auth_request._1.Auth;
import in.gov.uidai.authentication.uid_auth_request._1.DataType;
import in.gov.uidai.authentication.uid_auth_request._1.Uses;
import in.gov.uidai.authentication.uid_auth_request._1.UsesFlag;
import in.gov.uidai.authentication.uid_auth_response._1.AuthRes;
import in.gov.uidai.authentication.uid_auth_response._1.AuthResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import javax.xml.transform.sax.SAXSource;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.InetAddress;
import java.net.URI;

/**
 * <code>AuthClient</code> class can be used for submitting an Authentication request to
 * UIDAI Auth Server, and to get the response back.  Given an <code>Auth</code> object, this
 * class (@see {@link AuthClient#authenticate}) will convert it to XML string, then,
 * digitally sign it, and submit it to UIDAI Auth Server using HTTP POST message.  After,
 * receiving the resonse, this class converts the response XML into authentication response
 *
 * @author UIDAI
 * @see AuthRes object
 */
public class AuthClient {
  private URI authServerURI = null;

  private String asaLicenseKey;
  private DigitalSigner digitalSignator;

  /**
   * Constructor
   *
   * @param authServerUri - URI of the authentication server
   */
  public AuthClient(URI authServerUri) {
    this.authServerURI = authServerUri;
  }

  /**
   * Method to perform authentication
   *
   * @param auth Authentication request
   * @return Authentication response
   */
  public AuthResponseDetails authenticate(Auth auth) {

    HttpClientHelper.acceptAllCertificates();

    try {
      String signedXML = generateSignedAuthXML(auth);
      System.out.println(signedXML);


      String uriString = authServerURI.toString() + (authServerURI.toString().endsWith("/") ? "" : "/")
          + auth.getAc() + "/" + auth.getUid().charAt(0) + "/" + auth.getUid().charAt(1);

      if (StringUtils.isNotBlank(asaLicenseKey)) {
        uriString = uriString + "/" + asaLicenseKey;
      }

      URI authServiceURI = new URI(uriString);

      RestTemplate restTemplate = new RestTemplate();

      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.APPLICATION_XML);
      headers.add("REMOTE_ADDR", InetAddress.getLocalHost().getHostAddress());

      HttpEntity<String> request = new HttpEntity<>(signedXML, headers);

      ResponseEntity<String> responseEntity = restTemplate.postForEntity(authServiceURI, request, String.class);

      String responseXML = responseEntity.getBody();

      System.out.println(responseXML);

      return new AuthResponseDetails(responseXML, parseAuthResponseXML(responseXML));

    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException("Exception during authentication " + e.getMessage(), e);
    }
  }


  private String generateSignedAuthXML(Auth auth) throws JAXBException, Exception {
    StringWriter authXML = new StringWriter();

    JAXBElement authElement = new JAXBElement(new QName(
        "http://www.uidai.gov.in/authentication/uid-auth-request/1.0", "Auth"), Auth.class, auth);

    JAXBContext.newInstance(Auth.class).createMarshaller().marshal(authElement, authXML);
    boolean includeKeyInfo = true;

    if (System.getenv().get("SKIP_DIGITAL_SIGNATURE") != null) {
      return authXML.toString();
    } else {
      return this.digitalSignator.signXML(authXML.toString(), includeKeyInfo);
    }
  }

  private AuthRes parseAuthResponseXML(String xmlToParse) throws JAXBException {

    //Create an XMLReader to use with our filter
    try {
      //Prepare JAXB objects
      JAXBContext jc = JAXBContext.newInstance(AuthRes.class);
      Unmarshaller u = jc.createUnmarshaller();

      XMLReader reader;
      reader = XMLReaderFactory.createXMLReader();

      //Create the filter (to add namespace) and set the xmlReader as its parent.
      NamespaceFilter inFilter = new NamespaceFilter("http://www.uidai.gov.in/authentication/uid-auth-response/1.0", true);
      inFilter.setParent(reader);

      //Prepare the input, in this case a java.io.File (output)
      InputSource is = new InputSource(new StringReader(xmlToParse));

      //Create a SAXSource specifying the filter
      SAXSource source = new SAXSource(inFilter, is);

      //Do unmarshalling
      AuthRes res = u.unmarshal(source, AuthRes.class).getValue();
      return res;
    } catch (SAXException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    return null;
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

  public boolean authenticateAuthData(DeviceCollectedAuthData authData) {
    boolean result = false;

    try {
      //String authServerURL = "http://auth.uidai.gov.in/1.6";
      String asaLicenseKey = "MBFWjkJHNF-fLidl8oOHtUwgL5p1ZjDbWrqsMEVEJLVEDpnlNj_CZTg";
//			String asaLicenseKey = "MH4hSkrev2h_Feu0lBRC8NI-iqzT299_qPSSstOFbNFTwWrie29ThDo";
      DigitalSigner ds = new DigitalSigner("/Users/sriramviswanathan/everychildinschool/childportalparent/AadharHelper/Staging_Signature_PrivateKey.p12", "public".toCharArray(), "public");

      //AuthClient authClient = new AuthClient(new URL(authServerURL).toURI());
      this.setAsaLicenseKey(asaLicenseKey);
      this.setDigitalSignator(ds);

      AuthAUADataCreator auaDataCreator = new AuthAUADataCreator(new Encrypter("/Users/sriramviswanathan/everychildinschool/childportalparent/AadharHelper/uidai_auth_stage.cer"), true);
      Meta deviceMetaData = new Meta();
      deviceMetaData.setFdc("NC");
      deviceMetaData.setIdc("NA");
      deviceMetaData.setLot(LocationType.P);
      deviceMetaData.setLov("560103");
      deviceMetaData.setPip("127.0.0.1");
      deviceMetaData.setUdc("UIDAI:SampleClient");

      authData.setDeviceMetaData(deviceMetaData);

      AuthDataFromDeviceToAUA auaData = auaDataCreator.prepareAUAData(authData.getUid(), "public", authData.getDeviceMetaData(),
          (Object) PidCreator.createXmlPid(authData), DataType.X);

      AuthRequestCreator authRequestCreator = new AuthRequestCreator();
      Uses usesElement = new Uses();
      usesElement.setBio(UsesFlag.N);
      usesElement.setOtp(UsesFlag.N);
      usesElement.setPa(UsesFlag.N);
      usesElement.setPfa(UsesFlag.N);
      usesElement.setPi(UsesFlag.N);
      usesElement.setPin(UsesFlag.N);

      System.out.println("usesElement : " + usesElement.getBt() + "|" + usesElement.getBio() + "|"
          + usesElement.getOtp() + "|" + usesElement.getPa() + "|"
          + usesElement.getPfa() + "|" + usesElement.getPi() + "|" + usesElement.getPin());
      //System.out.println("token : " + token.toString() + "|" + token.getType() + "|" + token.getValue());
      System.out.println("auaData : " + auaData.getCertificateIdentifier() + "|" + auaData.getTerminalId() + "|"
          + auaData.getUid() + "|" + auaData.getDataType() + "|"
          + auaData.getMeta() + "|" + auaData.getMeta().getFdc() + "|" + auaData.getMeta().getIdc() + "|" + auaData.getMeta().getUdc() + "|"
          + auaData.getSessionKeyDetails() + "|" + auaData.getSessionKeyDetails().getKeyIdentifier() + "|" + auaData.getSessionKeyDetails().getSkeyValue());
      System.out.println("authData : " + authData.getName() + "|" + authData.getBiometricType() + "|" + authData.getNameMatchValue() + "|" + authData.getNameMatchStrategy()
          + authData.getDob() + "|" + authData.getDobType() + "|" + authData.getGender());
      System.out.println("authData.getDeviceMetaData() : " + authData.getDeviceMetaData() + "|" + authData.getDeviceMetaData().getFdc() + "|"
          + authData.getDeviceMetaData().getIdc() + "|" + authData.getDeviceMetaData().getLov() + "|"
          + authData.getDeviceMetaData().getPip() + "|" + authData.getDeviceMetaData().getUdc() + "|" + authData.getDeviceMetaData().getLot());


      Auth auth = authRequestCreator.createAuthRequest("public", "public", asaLicenseKey,
          usesElement, null, auaData, authData.getDeviceMetaData());

      AuthResponseDetails data = this.authenticate(auth);
      AuthRes authResult = data.getAuthRes();

      System.out.println(authResult.getCode() + "|" + authResult.getErr() + "|" + authResult.getInfo() + "|" + authResult.getTxn() + "|" + authResult.getTs());

      result = authResult.getRet().equals(AuthResult.Y);

      System.out.println("Result: " + result);

    } catch (Exception e) {
      e.printStackTrace();
    }

    return result;
  }

}
