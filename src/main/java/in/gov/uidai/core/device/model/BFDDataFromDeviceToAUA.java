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

import in.gov.uidai.core.model.xsd.common.types._1.Meta;
import in.gov.uidai.core.model.xsd.uid_bfd_request._1.DataType;


/**
 * This class represents the information that typically needs to be 
 * transferred by a BFD device to AUA server. 
 * 
 * @author UIDAI
 *
 */
public class BFDDataFromDeviceToAUA {
	
	String uid;
	String terminalId;
	byte[] encryptedRbd;
	byte[] encrytpedHmac;
	String certificateIdentifier;
	
	DataType dataType;
	
	Meta meta;

	SessionKeyDetails sessionKeyDetails;

	public BFDDataFromDeviceToAUA(String uid, String terminalId, SessionKeyDetails sessionKeyDetails, byte[] encryptedRbd, byte[] encrytpedHmac,
                                String certificateIdentifier, DataType dataType, Meta meta) {
		this.uid = uid;
		this.terminalId = terminalId;
		this.encryptedRbd = encryptedRbd;
		this.encrytpedHmac = encrytpedHmac;
		this.certificateIdentifier = certificateIdentifier;
		this.dataType = dataType;
		
		this.meta = meta;
		
		this.sessionKeyDetails = sessionKeyDetails;
	}
	
	public String getUid() {
		return uid;
	};

	public String getTerminalId() {
		return terminalId;
	}
	
	public byte[] getEncryptedRbd() {
		return encryptedRbd;
	}

	public byte[] getEncrytpedHmac() {
		return encrytpedHmac;
	}

	public String getCertificateIdentifier() {
		return certificateIdentifier;
	}

	public DataType getDataType() {
		return dataType;
	}


	public Meta getMeta() {
		return meta;
	}

	public SessionKeyDetails getSessionKeyDetails() {
		return sessionKeyDetails;
	}
}
