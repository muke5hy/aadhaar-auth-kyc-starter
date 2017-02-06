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

import in.gov.uidai.core.device.model.BFDDataFromDeviceToAUA;
import in.gov.uidai.core.model.xsd.common.types.Meta;
import in.gov.uidai.core.model.xsd.bfd.uid_bfd_request._1.Bfd;
import in.gov.uidai.core.model.xsd.bfd.uid_bfd_request._1.Bfd.Data;
import in.gov.uidai.core.model.xsd.bfd.uid_bfd_request._1.Skey;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <code>AuthRequestCreator</code> class provides a method to generate the <code>Auth</code> object
 * using information that has been received from auth device and from the information that
 * is available with AUA.
 *  
 * @author UIDAI
 *
 */
public class BfdRequestCreator {

	
	public static Bfd createBfdRequest(String aua, String sa, String licenseKey,
			BFDDataFromDeviceToAUA auaData, Meta metaData) {

		try {
			Bfd bfd = new Bfd();
			bfd.setUid(auaData.getUid());

			bfd.setVer("1.6");
			bfd.setAc(aua);
			bfd.setSa(sa);

			String txn = createTxn(aua);
			bfd.setTxn(txn);

			bfd.setLk(licenseKey);
			bfd.setTid(auaData.getTerminalId());

			bfd.setMeta(metaData);

			Skey skey = new Skey();
			skey.setCi(auaData.getCertificateIdentifier());

			skey.setKi(auaData.getSessionKeyDetails().getKeyIdentifier());
			skey.setValue(auaData.getSessionKeyDetails().getSkeyValue());
			
			bfd.setSkey(skey);
			
			Data data = new Data();
			data.setType(auaData.getDataType());
			data.setValue(auaData.getEncryptedRbd());
			bfd.setData(data);
			bfd.setHmac(auaData.getEncrytpedHmac());
			
			return bfd;
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
