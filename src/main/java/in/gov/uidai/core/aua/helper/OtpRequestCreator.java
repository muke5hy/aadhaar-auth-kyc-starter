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
package in.gov.uidai.core.aua.helper;

import in.gov.uidai.core.model.xsd.otp._1.Opts;
import in.gov.uidai.core.model.xsd.otp._1.Otp;
import in.gov.uidai.core.model.xsd.uid_auth_request._1.Auth;
import in.gov.uidai.core.device.model.OtpDataFromDeviceToAUA;
import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This class provides a method to generate the Otp request object
 * using information that has been received from Otp client and using the information that
 * is available with AUA.
 *  
 * @author UIDAI
 *
 */
public class OtpRequestCreator {

	/**
	 * Constructor
	 * @param aua AUA code
	 * @param sa ASA code
	 * @param licenseKey License key
	 * @param auaData Data received from otp client.
	 * @return Instance of {@link Auth}
	 */
	public Otp createOtpRequest(String aua, String sa, String licenseKey, OtpDataFromDeviceToAUA auaData) {

		try {

			Otp otpReq = new Otp();
			otpReq.setUid(auaData.getUid());

			otpReq.setVer("1.6");
			otpReq.setAc(aua);
			otpReq.setSa(sa);

			String txn = createTxn(aua);
			otpReq.setTxn(txn);

			otpReq.setLk(licenseKey);
			otpReq.setTid(auaData.getTerminalId());
			
			
			if (StringUtils.isNotBlank(auaData.getChannel())) {
				Opts c = new Opts();
				c.setCh(auaData.getChannel());
				otpReq.setOpts(c);
			}
			
			return otpReq;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	/**
	 * Method to construct transaction code based on AUA code and current time.
	 * @param aua AUA code
	 * @return String representing transaction code.
	 */
	private String createTxn(String aua) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddhhmmssSSS");
		String txn = "AuthDemoClient" + ":" + aua + ":" + dateFormat.format(new Date());
		return txn;
	}

}
