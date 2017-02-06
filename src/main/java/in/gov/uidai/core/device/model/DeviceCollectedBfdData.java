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

import in.gov.uidai.core.model.xsd.common.types.FingerPosition;
import in.gov.uidai.core.model.xsd.common.types.Meta;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * This class is used for collecting various information from 
 * the GUI so that it can be used for creating Rbd and Bfd objects.
 * 
 * @author UIDAI
 *
 */
public class DeviceCollectedBfdData implements Serializable {

    /**
     * generated serial version id
     */
    private static final long serialVersionUID = -969857695481409943L;

    private String uid;
	private List<BiometricData> biometrics;
	private Meta deviceMetaData;
	
	
	public DeviceCollectedBfdData(String uid, List<BiometricData> biometrics,
			Meta deviceMetaData) {
		super();
		this.uid = uid;
		this.biometrics = biometrics;
		this.deviceMetaData = deviceMetaData;
	}


	public List<BiometricData> getBiometrics() {
		return biometrics;
	}
	
	public Meta getDeviceMetaData() {
		return deviceMetaData;
	}
	
	public String getUid() {
		return uid;
	}

	public static class BiometricData {
		
		FingerPosition position;
		byte[] biometricContent;
		int nfiq;
		
		public BiometricData(FingerPosition position,
				byte[] biometricContent, int nfiq) {
			super();
			this.position = position;
			this.biometricContent = biometricContent;
			this.nfiq = nfiq;
		}
		
		/**
		 * ISO FMR/FIR/IIR
		 * @return
		 */
		public byte[] getBiometricContent() {
			return biometricContent;
		}
		
		public FingerPosition getPosition() {
			return position;
		}
		
		public int getNfiq() {
			return nfiq;
		}
	}

}
