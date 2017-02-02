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
package in.gov.uidai.core.biometrics;

import java.awt.Image;

/**
 * <code>CaptureDetails</code> class represents the biometric capture details.
 * 
 * @author UIDAI
 *
 */
public class CaptureDetails {
	
	int nfiq;
	Image image;
	byte[] isoFeatureSet;
	
	/**
	 * Constructor
	 * @param image Raw biometric image
	 * @param isoFeatureSet ISO compliant image or minutiae that is created from raw image.
	 */
	public CaptureDetails(Image image, byte[] isoFeatureSet, int nfiq) {
		this.image = image;
		this.isoFeatureSet = isoFeatureSet;
		this.nfiq = nfiq;
	}
	
	/**
	 * Returns biometric image
	 * @return Instance of Image class
	 */
	public Image getImage() {
		return image;
	}

	/**
	 * Returns ISO Minutiae or image (can be FMR/FIR/IIR based on implementation) 
	 * @return byte array representing ISO image or minutiae
	 */
	public byte[] getIsoFeatureSet() {
		return isoFeatureSet;
	}
	
	/**
	 * NFIQ of captured biometrics
	 * @return
	 */
	public int getNfiq() {
		return nfiq;
	}
}
