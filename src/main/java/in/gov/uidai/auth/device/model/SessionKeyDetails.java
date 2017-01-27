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
package in.gov.uidai.auth.device.model;

/**
 * A class to encapsulate Skey details such that value of "ki" and "Skey" can be
 * derived without the caller having to know the context.
 * 
 * @author UIDAI
 *
 */
public class SessionKeyDetails {

	/**
	 * Flag indicating whether synchronized key being used.
	 */
	boolean isSynchronizedKeySchemeUsed;
	
	/**
	 * Flag indicating whether this session key represents initialize of synchronized key,
	 * in which case a seed key will be used along with key identifier.
	 */
	boolean isSynchronizedKeyBeingInitialized;

	/**
	 * Seed skey for synchronized key scheme.  It is a RSA2048 encrypted AES key, that is encrypted using UIDAI public key.
	 */
	byte[] seedSkeyForSynchronizedKey;
	
	/**
	 * Random number for synchronized key scheme
	 */
	byte[] randomNumberForSynchornizedKey;
	
	/**
	 * Key identifier for synchronized key scheme
	 */
	String keyIdentifier;
	
	/**
	 * Skey value when not using synchronized key.  It is a RSA2048 encrypted AES key, that is encrypted using UIDAI public key.
	 */
	byte[] normalSkey;
	
	private SessionKeyDetails() {

	}
	
	public static SessionKeyDetails createSkeyToInitializeSynchronizedKey(String ki, byte[] encyprtedSeedKey) {
		SessionKeyDetails d = new SessionKeyDetails();
		
		d.setSynchronizedKeySchemeUsed(true);
		d.setKeyIdentifier(ki);
		d.setSynchornizedKeyBeingInitialized(true);
		d.setSeedSkeyForSynchronizedKey(encyprtedSeedKey);
		
		return d;
		
	}
	
	public static SessionKeyDetails createSkeyToUsePreviouslyGeneratedSynchronizedKey(String ki, byte[] synchronizedKeyRandom) {
		SessionKeyDetails d = new SessionKeyDetails();
		d.setSynchronizedKeySchemeUsed(true);
		d.setKeyIdentifier(ki);
		d.setSynchornizedKeyBeingInitialized(false);
		d.setRandomNumberForSynchornizedKey(synchronizedKeyRandom);
		return d;
	}

	public static SessionKeyDetails createNormalSkey(byte[] encyprtedSeedKey) {
		SessionKeyDetails d = new SessionKeyDetails();
		d.setSynchronizedKeySchemeUsed(false);
		d.setNormalSkey(encyprtedSeedKey);		
		return d;
	}

	public String getKeyIdentifier() {
		if (isSynchronizedKeySchemeUsed) {
			return this.keyIdentifier;
		} else {
			return null;
		}
	}
	
	public byte[] getSkeyValue() {
		if (isSynchronizedKeySchemeUsed) {
			if (isSynchronizedKeyBeingInitialized) {
				return this.seedSkeyForSynchronizedKey;
			} else {
				return this.randomNumberForSynchornizedKey;
			}
		} else {
			return this.normalSkey;
		}
	}
	
	public void setKeyIdentifier(String ki) {
		this.keyIdentifier = ki;
	}
	
	public void setSeedSkeyForSynchronizedKey(byte[] seedSkey) {
		this.seedSkeyForSynchronizedKey = seedSkey;
	}
	
	public void setSynchronizedKeySchemeUsed(boolean isSSK) {
		this.isSynchronizedKeySchemeUsed = isSSK;
	}
	
	public void setSynchornizedKeyBeingInitialized(boolean sskInit) {
		this.isSynchronizedKeyBeingInitialized = sskInit;
	}
	
	public void setRandomNumberForSynchornizedKey(byte[] sskRandom) {
		this.randomNumberForSynchornizedKey = sskRandom;
	}
	
	public void setNormalSkey(byte[] normalSkey) {
		this.normalSkey = normalSkey;
	}
}
