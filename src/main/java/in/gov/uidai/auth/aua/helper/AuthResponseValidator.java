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

import in.gov.uidai.auth.generic.helper.HashGenerator;
import in.gov.uidai.authentication.uid_auth_request._1.Auth;
import in.gov.uidai.authentication.uid_auth_request._1.DataType;
import in.gov.uidai.authentication.uid_auth_response._1.AuthRes;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.util.encoders.Hex;

import java.util.Date;

/**
 * Helper class to validate the authentication response by validating the following:
 * <ol>
 * 	<li>Hash of Aadhaar number from "info" attribute of authentication response</li>
 * 	<li>Hash of Demo XML from "info" attribute of authentication response</li>
 * 	<li>By interpreting the bit mask representing usage of various auth element in the "info" attribute of authentication response.</li>
 * </ol>
 * 
 * 
 * @author UIDAI
 *
 */
public class AuthResponseValidator {
	
	private static HashGenerator hashGenerator = new HashGenerator();
	
	private SignatureVerifier signatureVerifier = null;
	
	public AuthResponseValidator(SignatureVerifier signatureVerifier) {
		this.signatureVerifier = signatureVerifier;
	}

	/**
	 * Validates the auth response and returns ValidationResult
	 * @param auth Auth request object
	 * @param authRes Auth response object
	 * @param demoXML Demo XML string
	 * @return Validation result that contains details of validation.
	 */
	public ValidationResult validateAuthResponse(Auth auth, byte[] demoHash, AuthRes authRes, String responseXML) {
	
		ValidationResult v = new ValidationResult();
		
		String info = authRes.getInfo();
		v.setAadhaarNumber(auth.getUid());
		
		v.setAuthResult(authRes.getRet().toString());
		if (authRes.getErr() != null) {
			v.setAuthErrorCode(authRes.getErr().toString());
		}
		v.setAuthResponseCode(authRes.getCode());
		v.setResponseTime(authRes.getTs() != null ? authRes.getTs().toGregorianCalendar().getTime() : null);
		
		v.setPidType(auth.getData().getType());
		
		v.setInfoStructureVersion(info.substring(0, 2));
		v.setAadhaarHashInResponse(info.substring(2, 66));
		v.setDemoXmlHashInResponse(info.substring(66, 130));
		
		v.setComputedAadhaarHash(new String(Hex.encode(hashGenerator.generateSha256Hash(auth.getUid().getBytes()))));
		v.setComputedDemoXmlHash(new String(Hex.encode(demoHash)));
		
		v.setTxnInRequest(auth.getTxn());
		v.setTxnInResponse(authRes.getTxn());
		
		v.setEncodedUsageData(info.substring(130, 142));

		v.setDigitalSignatureVerified(this.signatureVerifier.verify(responseXML));
		
		return v;
		
	}

	/**
	 * Class to represent validation result
	 * 
	 * @author UIDAI
	 *
	 */
	public static class ValidationResult {
		String aadhaarNumber;
		String authResult;
		String authErrorCode;
		
		String infoStructureVersion;
	
		String aadhaarHashInResponse;
		String demoXmlHashInResponse;
		String txnInResponse;
		
		String computedAadhaarHash;
		String computedDemoXmlHash;
		String txnInRequest;
		
		String usageEncodingVersion;
		String encodedUsageData;
		
		boolean isPiNameUsed;
		boolean isPiLnameUsed;
		boolean isPiGenderUsed;
		boolean isPiDobUsed;

		private boolean isPiPhoneUsed;
		private boolean isPiEmailUsed;
		private boolean isPiAgeUsed;
		private boolean isPaCareOfUsed;

		private boolean isPaHouseUsed;
		private boolean isPaStreetUsed;
		private boolean isPaLandmarkUsed;
		private boolean isPaLocalityUsed;

		private boolean isPaVtcUsed;
		private boolean isPaDistUsed;
		private boolean isPaStateUsed;
		private boolean isPaPincodeUsed;

		private boolean isPfaAvUsed;
		private boolean isPfaLavUsed;
		private boolean isBioFMRUsed;
		private boolean isBioFIRUsed;

		private boolean isBioIIRUsed;
		private boolean isPvPinUsed;
		private boolean isPvOtpUsed;
		private boolean isTknUsed;

		private boolean isPaPoUsed;
		private boolean isPaSubdistUsed;
		private boolean isPiDobtUsed;

		private String authResponseCode;
		
		private Date responseTime;
		
		private boolean digitalSignatureVerified;

		private DataType dataType;
		
		public boolean isAadhaarHashCheckPassed() {
			return StringUtils.isNotBlank(computedAadhaarHash) && computedAadhaarHash.equals(aadhaarHashInResponse);
		}
		
		public DataType getPidType() {
			return dataType;
		}
		
		public void setPidType(DataType type) {
			this.dataType = type;
		}

		public boolean isDemoHashCheckPassed() {
			return StringUtils.isNotBlank(computedDemoXmlHash) && computedDemoXmlHash.equals(demoXmlHashInResponse);
		}
		
		public boolean isTransactionCodeCheckPassed() {
			return StringUtils.isBlank(txnInRequest) || txnInRequest.equals(txnInResponse);
		}
		
		public String getInfoStructureVersion() {
			return infoStructureVersion;
		}

		public void setInfoStructureVersion(String infoStructureVersion) {
			this.infoStructureVersion = infoStructureVersion;
		}

		public String getAadhaarHashInResponse() {
			return aadhaarHashInResponse;
		}

		public void setAadhaarHashInResponse(String aadhaarHashInResponse) {
			this.aadhaarHashInResponse = aadhaarHashInResponse;
		}

		public String getDemoXmlHashInResponse() {
			return demoXmlHashInResponse;
		}

		public void setDemoXmlHashInResponse(String demoXmlHashInResponse) {
			this.demoXmlHashInResponse = demoXmlHashInResponse;
		}

		public String getTxnInResponse() {
			return txnInResponse;
		}

		public void setTxnInResponse(String txnInResponse) {
			this.txnInResponse = txnInResponse;
		}

		public String getComputedAadhaarHash() {
			return computedAadhaarHash;
		}

		public void setComputedAadhaarHash(String computedAadhaarHash) {
			this.computedAadhaarHash = computedAadhaarHash;
		}

		public String getComputedDemoXmlHash() {
			return computedDemoXmlHash;
		}

		public void setComputedDemoXmlHash(String computedDemoXmlHash) {
			this.computedDemoXmlHash = computedDemoXmlHash;
		}

		public String getTxnInRequest() {
			return txnInRequest;
		}

		public void setTxnInRequest(String txnInRequest) {
			this.txnInRequest = txnInRequest;
		}

		public String getUsageEncodingVersion() {
			return usageEncodingVersion;
		}

		public void setUsageEncodingVersion(String usageEncodingVersion) {
			this.usageEncodingVersion = usageEncodingVersion;
		}
		
		public String getEncodedUsageData() {
			return encodedUsageData;
		}
		
		public void setEncodedUsageData(String encodedUsageData) {
			this.encodedUsageData = encodedUsageData;
			decode(this.encodedUsageData);
		}
		
		public String getAuthErrorCode() {
			return authErrorCode;
		}
		
		public void setAuthErrorCode(String authErrorCode) {
			this.authErrorCode = authErrorCode;
		}
		
		public String getAuthResult() {
			return authResult;
		}
		
		public void setAuthResult(String authResult) {
			this.authResult = authResult;
		}
		
		/**
		 * Decodes the encoded usage data of "info" attribute.
		 * @param encodedUsageData
		 */
		private void decode(String encodedUsageData) {
			//1st Hex digit
			this.usageEncodingVersion = encodedUsageData.substring(0, 1);
			
			//Below set of code takes each hexadecimal digit from encoded usage data,
			//converts it into a byte by pre-pending 0, and then uses the bit masking
			//to compute the value of each of usage flags.
			
			//2nd hex digit decoding
			byte hexDigit = Hex.decode("0" + encodedUsageData.substring(1, 2))[0];
			this.isPiNameUsed = (hexDigit & 0x8) > 0;
			this.isPiLnameUsed = (hexDigit & 0x4) > 0;
			this.isPiGenderUsed = (hexDigit & 0x2) > 0;
			this.isPiDobUsed = (hexDigit & 0x1) > 0;
			
			//Third hex digit decoding
			hexDigit = Hex.decode("0" + encodedUsageData.substring(2, 3))[0];
			this.isPiPhoneUsed = (hexDigit & 0x8) > 0;
			this.isPiEmailUsed = (hexDigit & 0x4) > 0;
			this.isPiAgeUsed = (hexDigit & 0x2) > 0;
			this.isPaCareOfUsed = (hexDigit & 0x1) > 0;
			
			//Fourth hex digit decoding
			hexDigit = Hex.decode("0" + encodedUsageData.substring(3, 4))[0];
			this.isPaHouseUsed = (hexDigit & 0x8) > 0;
			this.isPaStreetUsed = (hexDigit & 0x4) > 0;
			this.isPaLandmarkUsed = (hexDigit & 0x2) > 0;
			this.isPaLocalityUsed = (hexDigit & 0x1) > 0;
			
			//Fifth hex digit decoding
			hexDigit = Hex.decode("0" + encodedUsageData.substring(4, 5))[0];
			this.isPaVtcUsed = (hexDigit & 0x8) > 0;
			this.isPaDistUsed = (hexDigit & 0x4) > 0;
			this.isPaStateUsed = (hexDigit & 0x2) > 0;
			this.isPaPincodeUsed = (hexDigit & 0x1) > 0;
			
			//Sixth hex digit decoding
			hexDigit = Hex.decode("0" + encodedUsageData.substring(5, 6))[0];
			this.isPfaAvUsed = (hexDigit & 0x8) > 0;
			this.isPfaLavUsed = (hexDigit & 0x4) > 0;
			this.isBioFMRUsed = (hexDigit & 0x2) > 0;
			this.isBioFIRUsed = (hexDigit & 0x1) > 0;

			//Seventh hex digit decoding
			hexDigit = Hex.decode("0" + encodedUsageData.substring(6, 7))[0];
			this.isBioIIRUsed = (hexDigit & 0x8) > 0;
			this.isPvPinUsed = (hexDigit & 0x4) > 0;
			this.isPvOtpUsed = (hexDigit & 0x2) > 0;
			this.isTknUsed = (hexDigit & 0x1) > 0;
			
			//8th hex digit decoding
			hexDigit = Hex.decode("0" + encodedUsageData.substring(7, 8))[0];
			this.isPaPoUsed = (hexDigit & 0x8) > 0;
			this.isPaSubdistUsed = (hexDigit & 0x4) > 0;
			this.isPiDobtUsed = (hexDigit & 0x2) > 0;
			
		}

		public boolean isPiNameUsed() {
			return isPiNameUsed;
		}

		public boolean isPiLnameUsed() {
			return isPiLnameUsed;
		}

		public boolean isPiGenderUsed() {
			return isPiGenderUsed;
		}

		public boolean isPiDobUsed() {
			return isPiDobUsed;
		}

		public boolean isPiPhoneUsed() {
			return isPiPhoneUsed;
		}

		public boolean isPiEmailUsed() {
			return isPiEmailUsed;
		}

		public boolean isPiAgeUsed() {
			return isPiAgeUsed;
		}

		public boolean isPaCareOfUsed() {
			return isPaCareOfUsed;
		}

		public boolean isPaHouseUsed() {
			return isPaHouseUsed;
		}

		public boolean isPaStreetUsed() {
			return isPaStreetUsed;
		}

		public boolean isPaLandmarkUsed() {
			return isPaLandmarkUsed;
		}

		public boolean isPaLocalityUsed() {
			return isPaLocalityUsed;
		}

		public boolean isPaVtcUsed() {
			return isPaVtcUsed;
		}

		public boolean isPaDistUsed() {
			return isPaDistUsed;
		}

		public boolean isPaStateUsed() {
			return isPaStateUsed;
		}

		public boolean isPaPincodeUsed() {
			return isPaPincodeUsed;
		}

		public boolean isPfaAvUsed() {
			return isPfaAvUsed;
		}

		public boolean isPfaLavUsed() {
			return isPfaLavUsed;
		}

		public boolean isBioFMRUsed() {
			return isBioFMRUsed;
		}

		public boolean isBioFIRUsed() {
			return isBioFIRUsed;
		}

		public boolean isBioIIRUsed() {
			return isBioIIRUsed;
		}

		public boolean isPvPinUsed() {
			return isPvPinUsed;
		}

		public boolean isPvOtpUsed() {
			return isPvOtpUsed;
		}

		public boolean isTknUsed() {
			return isTknUsed;
		}

		public boolean isPaPoUsed() {
			return isPaPoUsed;
		}

		public boolean isPaSubdistUsed() {
			return isPaSubdistUsed;
		}

		public boolean isPiDobtUsed() {
			return isPiDobtUsed;
		}
		
		public String getAuthResponseCode() {
			return authResponseCode;
		}
		
		public void setAuthResponseCode(String authResponseCode) {
			this.authResponseCode = authResponseCode;
		}
		
		public String getAadhaarNumber() {
			return aadhaarNumber;
		}
		
		public void setAadhaarNumber(String aadhaarNumber) {
			this.aadhaarNumber = aadhaarNumber;
		}
		
		public Date getResponseTime() {
			return responseTime;
		}
		
		public void setResponseTime(Date responseTime) {
			this.responseTime = responseTime;
		}
		
		public boolean isDigitalSignatureVerified() {
			return digitalSignatureVerified;
		}
		
		public void setDigitalSignatureVerified(boolean digitalSignatureVerified) {
			this.digitalSignatureVerified = digitalSignatureVerified;
		}
		
		/**
		 * String representation for displaying in the GUI or logs.
		 */
		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			
			String pidType = "Pid Type : " + (DataType.P.equals(this.getPidType()) ? "ProtoBuf": "XML");
			
			sb.append("*******************      Auth Response Validation Result (" + pidType + ")  ********************** ");
			sb.append("\n");
			sb.append("  AADHAAR Number                :  " + this.aadhaarNumber + " \n");
			sb.append("\n");
			
			sb.append("  Auth Result                   :  " + this.authResult + ( StringUtils.isNotBlank(this.authErrorCode) ? " (" + this.authErrorCode + ")" : "") + " \n");
			sb.append("\n");
			
			sb.append("  Signature Verification        :  " + (this.digitalSignatureVerified ? "SUCCESS" : "FAILED") + " \n");
			sb.append("\n");
			sb.append("  Authentication Response Code  :  " + this.authResponseCode + " \n");
			sb.append("  Time of Response Generation   :  " + this.responseTime + " \n");
			sb.append("  Transaction Code              :  " + this.txnInRequest + " \n");
			sb.append("  Aadhaar Hash in Response      :  " + this.aadhaarHashInResponse + " \n");
			sb.append("  Demo element Hash in Response :  " + this.demoXmlHashInResponse + " \n");
			sb.append("\n");
			sb.append("  Aadhaar Hash Validation       :  " + (this.isAadhaarHashCheckPassed() ? "PASS" : "FAIL") + " \n");
			sb.append("  Demo Xml Hash Validation      :  " + 
					(isDemoUsed() ? (this.isDemoHashCheckPassed() ? "PASS" : "FAIL") + " \n" : "Not applicable\n"));
			sb.append("  Transaction code check        :  " + (this.isTransactionCodeCheckPassed() ? "PASS" : "FAIL") + "\n");
			sb.append("\n");
			sb.append("  Encoded Usage Data: " + this.encodedUsageData + " \n");
			sb.append("\n");
			sb.append("  Was Pi->name used?            : " + (this.isPiNameUsed ? "YES" : "NO") + " \n");
			sb.append("  Was Pi->lname used?           : " + (this.isPiLnameUsed ? "YES" : "NO") + " \n");
			sb.append("  Was Pi->gender used?          : " + (this.isPiGenderUsed ? "YES" : "NO") + " \n");
			sb.append("  Was Pi->dob used?             : " + (this.isPiDobUsed ? "YES" : "NO") + " \n");
			sb.append("\n");
			sb.append("  Was Pi->phone used?           : " + (this.isPiPhoneUsed ? "YES" : "NO") + " \n");
			sb.append("  Was Pi->email used?           : " + (this.isPiEmailUsed ? "YES" : "NO") + " \n");
			sb.append("  Was Pi->age used?             : " + (this.isPiAgeUsed ? "YES" : "NO") + " \n");
			sb.append("  Was Pa->co used?              : " + (this.isPaCareOfUsed ? "YES" : "NO") + " \n");
			sb.append("\n");
			sb.append("  Was Pa->house used?           : " + (this.isPaHouseUsed ? "YES" : "NO") + " \n");
			sb.append("  Was Pa->street used?          : " + (this.isPaStreetUsed ? "YES" : "NO") + " \n");
			sb.append("  Was Pa->lm used?              : " + (this.isPaLandmarkUsed ? "YES" : "NO") + " \n");
			sb.append("  Was Pa->loc used?             : " + (this.isPaLocalityUsed ? "YES" : "NO") + " \n");
			sb.append("\n");
			sb.append("  Was Pa->vtc used?             : " + (this.isPaVtcUsed ? "YES" : "NO") + " \n");
			sb.append("  Was Pa->dist used?            : " + (this.isPaDistUsed ? "YES" : "NO") + " \n");
			sb.append("  Was Pa->state used?           : " + (this.isPaStateUsed ? "YES" : "NO") + " \n");
			sb.append("  Was Pa->pc used?              : " + (this.isPaPincodeUsed ? "YES" : "NO") + " \n");
			sb.append("\n");
			sb.append("  Was Pfa->av used?             : " + (this.isPfaAvUsed ? "YES" : "NO") + " \n");
			sb.append("  Was Pfa->lav used?            : " + (this.isPfaLavUsed ? "YES" : "NO") + " \n");
			sb.append("  Was Bio->FMR used?            : " + (this.isBioFMRUsed ? "YES" : "NO") + " \n");
			sb.append("  Was Bio->FIR used?            : " + (this.isBioFIRUsed ? "YES" : "NO") + " \n");
			sb.append("\n");
			sb.append("  Was Bio->IIR used?            : " + (this.isBioIIRUsed ? "YES" : "NO") + " \n");
			sb.append("  Was Pv->Pin used?             : " + (this.isPvPinUsed ? "YES" : "NO") + " \n");
			sb.append("  Was Pv->Otp used?             : " + (this.isPvOtpUsed ? "YES" : "NO") + " \n");
			sb.append("  Was Tkn used?                 : " + (this.isTknUsed ? "YES" : "NO") + " \n");
			sb.append("\n");
			sb.append("  Was Pa->po used?              : " + (this.isPaPoUsed ? "YES" : "NO") + " \n");
			sb.append("  Was Pa->subdist used?         : " + (this.isPaSubdistUsed ? "YES" : "NO") + " \n");
			sb.append("  Was Pi->dobt used?            : " + (this.isPiDobtUsed ? "YES" : "NO") + " \n");
			sb.append("\n");
			sb.append("\n");
			  
			return sb.toString();
		}

		private boolean isDemoUsed() {
			// If server has is all 0s then we can assume that demo was not used in auth request.
			if (StringUtils.leftPad("", 64, '0').equals(this.demoXmlHashInResponse)) {
				return false;
			} else {
				return true;
			}
		}


	}
	
}
