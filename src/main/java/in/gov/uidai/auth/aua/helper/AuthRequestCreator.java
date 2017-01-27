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
package in.gov.uidai.auth.aua.helper;

import in.gov.uidai.auth.device.model.AuthDataFromDeviceToAUA;
import in.gov.uidai.authentication.common.types._1.Meta;
import in.gov.uidai.authentication.uid_auth_request._1.Auth;
import in.gov.uidai.authentication.uid_auth_request._1.Auth.Data;
import in.gov.uidai.authentication.uid_auth_request._1.Skey;
import in.gov.uidai.authentication.uid_auth_request._1.Tkn;
import in.gov.uidai.authentication.uid_auth_request._1.Uses;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <code>AuthRequestCreator</code> class provides a method to generate the <code>Auth</code> object
 * using information that has been received from authentication device and from the information that
 * is available with AUA.
 *  
 * @author UIDAI
 *
 */
public class AuthRequestCreator {

	/**
	 * Constructor
	 * @param aua AUA code
	 * @param saSub AUA code
	 * @param licenseKey License key
	 * @param uses Uses element
	 * @param token Token element, typically used for authentication requests received over mobile networks.
	 * @param auaData Data received from authentication device.
	 * @param metaData 
	 * @return Instance of {@link Auth}
	 */
	public static Auth createAuthRequest(String aua, String sa, String licenseKey, Uses uses,
			Tkn token, AuthDataFromDeviceToAUA auaData, Meta metaData) {

		try {
			Auth auth = new Auth();
			auth.setUid(auaData.getUid());

			auth.setVer("1.6");
			auth.setAc(aua);
			auth.setSa(sa);

			String txn = createTxn(aua);
			auth.setTxn(txn);

			auth.setLk(licenseKey);
			auth.setTid(auaData.getTerminalId());

			if (token != null) {
				auth.setTkn(token);
			}
			auth.setMeta(metaData);
			Skey skey = new Skey();
			skey.setCi(auaData.getCertificateIdentifier());
			
			skey.setValue(auaData.getSessionKeyDetails().getSkeyValue());
			skey.setKi(auaData.getSessionKeyDetails().getKeyIdentifier());
			
			auth.setSkey(skey);
			Data data = new Data();
			data.setType(auaData.getDataType());
			data.setValue(auaData.getEncryptedPid());
			auth.setData(data);
			auth.setHmac(auaData.getEncrytpedHmac());
			auth.setUses(uses);
			return auth;
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
	private static String createTxn(String aua) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddhhmmssSSS");
		String txn = "AuthDemoClient" + ":" + aua + ":" + dateFormat.format(new Date());
		return txn;
	}

}
