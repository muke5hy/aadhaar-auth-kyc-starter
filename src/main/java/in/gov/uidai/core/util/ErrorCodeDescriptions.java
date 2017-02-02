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
package in.gov.uidai.core.util;

import java.util.HashMap;
import java.util.Map;

public class ErrorCodeDescriptions {
	
	private static Map<String, String> codeToDescriptionMap = new HashMap<String, String>();
	
	static {
		codeToDescriptionMap.put("100", "Identity data mismatch");
		codeToDescriptionMap.put("110", "Email not verified");
		codeToDescriptionMap.put("111", "Mobile not verified");
		codeToDescriptionMap.put("112", "Neither email nor mobile are available");
		
		codeToDescriptionMap.put("200", "Address mismatch");
		
		codeToDescriptionMap.put("300", "Biometric mismatch");
		
		codeToDescriptionMap.put("310", "Duplicate fingers used");
		codeToDescriptionMap.put("311", "Duplicate irises used");
		codeToDescriptionMap.put("312", "Both FMR and FIR used in same request");
		codeToDescriptionMap.put("313", "FIR has more than one finger");
		codeToDescriptionMap.put("314", "More than 10 fingers used");
		codeToDescriptionMap.put("315", "More than 2 iris used");
		
		codeToDescriptionMap.put("400", "OTP mismatch");
		codeToDescriptionMap.put("401", "Token mismatch");
		
		codeToDescriptionMap.put("500", "Invalid Skey encryption");
		codeToDescriptionMap.put("501", "Invalid Certificate identifer in Skey");
		codeToDescriptionMap.put("502", "Invalid Pid encryption");
		codeToDescriptionMap.put("503", "Invalid Hmac encryption");
		codeToDescriptionMap.put("504", "Syncronized Skey Expired");
		codeToDescriptionMap.put("505", "Syncronized Skey not allowed");
		
		codeToDescriptionMap.put("510", "Invalid XML");
		codeToDescriptionMap.put("511", "Invalid Pid XML");
		codeToDescriptionMap.put("520", "Invalid device");
		codeToDescriptionMap.put("521", "Invalid Finger device");
		codeToDescriptionMap.put("522", "Invalid Iris device");
		codeToDescriptionMap.put("530", "Invalid AUA");
		codeToDescriptionMap.put("540", "Invalid API Version");
		codeToDescriptionMap.put("541", "Invalid PID XML Version");
		codeToDescriptionMap.put("542", "AUA not authorized for ASA");
		
		
		codeToDescriptionMap.put("550", "Invalid uses element attribute");
		codeToDescriptionMap.put("561", "Expired request");
		codeToDescriptionMap.put("562", "Future request");
		codeToDescriptionMap.put("563", "Duplicate request");
		codeToDescriptionMap.put("564", "HMAC Validation failed");
		codeToDescriptionMap.put("565", "Expired license");
		codeToDescriptionMap.put("566", "Invalid license");
		codeToDescriptionMap.put("567", "Invalid input");
		codeToDescriptionMap.put("568", "Unsupported language");
		codeToDescriptionMap.put("569", "Digital signature verification failed");
		codeToDescriptionMap.put("570", "Invalid digital certificate");
		codeToDescriptionMap.put("571", "PIN requires reset");
		codeToDescriptionMap.put("572", "Invalid biometric position");
		
		codeToDescriptionMap.put("573", "Pi not allowed");
		codeToDescriptionMap.put("574", "Pa not allowed");
		codeToDescriptionMap.put("575", "Pfa not allowed");
		codeToDescriptionMap.put("576", "FMR not allowed");
		codeToDescriptionMap.put("577", "FIR not allowed");
		codeToDescriptionMap.put("578", "IIR not allowed");
		codeToDescriptionMap.put("579", "OTP not allowed");
		codeToDescriptionMap.put("580", "PIN not allowed");
		codeToDescriptionMap.put("581", "Fuzzy match strategy not allowed");
		codeToDescriptionMap.put("582", "Usage of local language not allowed");
		
		codeToDescriptionMap.put("583", "BFD not allowed");
		codeToDescriptionMap.put("584", "Invalid pin code in Meta");
		codeToDescriptionMap.put("585", "Invalid geo code in Meta");
		
		codeToDescriptionMap.put("700", "Invalid demographic data");
		codeToDescriptionMap.put("710", "Missing Pi data");
		codeToDescriptionMap.put("720", "Missing Pa data");
		codeToDescriptionMap.put("721", "Missing Pfa data");
		codeToDescriptionMap.put("730", "Missing PIN data");
		codeToDescriptionMap.put("740", "Missing OTP data");
		
		codeToDescriptionMap.put("800", "Invalid biometrics");
		codeToDescriptionMap.put("810", "Missing biometrics in Auth request");
		codeToDescriptionMap.put("811", "Missing biometrics in CIDR");
		codeToDescriptionMap.put("812", "Best Finger Detection(BFD) not done.");
		
		
		codeToDescriptionMap.put("820", "Missing biometrics type in uses element");
		codeToDescriptionMap.put("821", "Invalid biometrics type in uses element");
		
		codeToDescriptionMap.put("901", "No auth factors in request");
		codeToDescriptionMap.put("902", "Invalid DOB");
		
		codeToDescriptionMap.put("910", "Invalid Pi Mv value");
		codeToDescriptionMap.put("911", "Invalid Pfa Mv value");
		codeToDescriptionMap.put("912", "Invalid Pa Mv value");
		codeToDescriptionMap.put("913", "Both Pa and Pfa present");

		codeToDescriptionMap.put("930", "Biometric SDK error");
		codeToDescriptionMap.put("931", "Auth server error");
		codeToDescriptionMap.put("932", "Audit error");
		codeToDescriptionMap.put("933", "BI Related Technical Error");
		codeToDescriptionMap.put("934", "OTP store error");
		codeToDescriptionMap.put("935", "Skey store error");
		codeToDescriptionMap.put("936", "PIN store error");
		codeToDescriptionMap.put("937", "Digital signature error");
		codeToDescriptionMap.put("938", "License store error");
		codeToDescriptionMap.put("939", "Onboarding error");
	
		codeToDescriptionMap.put("940", "Unauthorized ASA Channel");
		codeToDescriptionMap.put("941", "Unspecified ASA Channel");
		codeToDescriptionMap.put("950", "Could not generate and send OTP");
		
		codeToDescriptionMap.put("980", "Unsupported option");
	
		codeToDescriptionMap.put("999", "Unknown error");
	}
	
	public static String getDescription(String code) {
		String description = codeToDescriptionMap.get(code);
		if (description == null) {
			description = "Not sure. Pls. check spec.";
		}
		return description;
	}
	
}

