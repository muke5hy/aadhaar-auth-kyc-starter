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
package in.gov.uidai.core.device.model;

/**
 * This class represents the information that typically needs to be
 * transferred by an Otp Client to AUA server.
 *
 * @author UIDAI
 */
public class OtpDataFromDeviceToAUA {

  public static final String SMS_CHANNEL = "01";
  public static final String EMAIL_CHANNEL = "02";
  public static final String BOTH_EMAIL_SMS_CHANNEL = "00";


  String uid;
  String terminalId;
  String channel;

  public OtpDataFromDeviceToAUA(String uid, String terminalId, String channel) {
    this.uid = uid;
    this.terminalId = terminalId;
    this.channel = channel;
  }

  public String getChannel() {
    return channel;
  }

  public String getTerminalId() {
    return terminalId;
  }

  public String getUid() {
    return uid;
  }

}
