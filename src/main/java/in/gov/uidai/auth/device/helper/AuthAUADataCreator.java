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
package in.gov.uidai.auth.device.helper;

import in.gov.uidai.auth.device.model.AuthDataFromDeviceToAUA;
import in.gov.uidai.auth.device.model.SessionKeyDetails;
import in.gov.uidai.auth.generic.helper.HashGenerator;
import in.gov.uidai.authentication.common.types._1.Meta;
import in.gov.uidai.authentication.uid_auth_request._1.DataType;
import in.gov.uidai.authentication.uid_auth_request_data._1.Pid;
import org.apache.commons.lang3.StringUtils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.StringWriter;
import java.security.SecureRandom;
import java.util.*;

/**
 * This class provides a method to collate all the data that needs to sent from Auth Client to AUA server.
 * 
 * @author UIDAI
 * 
 */
public class AuthAUADataCreator {

	private static final int AES_256_KEY_SIZE = 32;
	private Encrypter encrypter;
	private HashGenerator hashGenerator;

	private static final String RANDOM_ALGORITH_NAME = "SHA1PRNG";

	private Map<DataType, SynchronizedKey> skeyMap = new HashMap<DataType, SynchronizedKey>();
	
	private long expiryTime = 10 * 60 * 1000; //10 minutes for testing purpose.
	
	private SecureRandom secureSeedGenerator;
	private boolean useSSK = false;

	/**
	 * Constructor
	 * @param encrypter For encryption of Pid
	 * @param useSynchronizedSesionKey Flag indicating whether synchronized sesssion key should be used.
	 */
	public AuthAUADataCreator(Encrypter encrypter, boolean useSynchronizedSesionKey) {
		this.hashGenerator = new HashGenerator();
		this.encrypter = encrypter;
		this.useSSK = useSynchronizedSesionKey;

		try {
			this.secureSeedGenerator = SecureRandom.getInstance(RANDOM_ALGORITH_NAME);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 * Creates an instance of {@link AuthDataFromDeviceToAUA} that represents all the data that an Auth client
	 * will send to AUA server.
	 * 
	 * @param uid
	 * @param terminalId
	 * @param meta
	 * @param pid
	 * @param dataType
	 * @return
	 */
	public AuthDataFromDeviceToAUA prepareAUAData(String uid, String terminalId, Meta meta, Object pid, DataType dataType) {

		try {
			byte[] rawPid;

			/*if (DataType.P == dataType) {
				rawPid = ((in.gov.uidai.authserver.protobuf.Auth.Pid) pid).toByteArray();
				System.out.println("Proto Pid in Hex: " + Hex.encodeHexString(rawPid));
			} else {
				rawPid = createPidXML((in.gov.uidai.authentication.uid_auth_request_data._1.Pid) pid).getBytes();
			}*/
			rawPid = createPidXML((in.gov.uidai.authentication.uid_auth_request_data._1.Pid) pid).getBytes();
			

			byte[] pidXmlBytes = rawPid;
			
			byte[] sessionKey = null;
			
			byte[] newRandom = new byte[20];
			
			SynchronizedKey synchronizedKey = null;
			byte[] encryptedSessionKey = null;
			
			SessionKeyDetails sessionKeyDetails;
			
			if (this.useSSK) {
				synchronizedKey = this.skeyMap.get(dataType);
				
				if (synchronizedKey == null || synchronizedKey.getSeedCreationDate().getTime()  - System.currentTimeMillis() > expiryTime) {
					synchronizedKey = new SynchronizedKey(this.encrypter.generateSessionKey(), UUID.randomUUID().toString(), new Date());
					this.skeyMap.put(dataType, synchronizedKey);
					
					sessionKey = synchronizedKey.getSeedSkey();
					encryptedSessionKey = this.encrypter.encryptUsingPublicKey(sessionKey);
					
					sessionKeyDetails = SessionKeyDetails.createSkeyToInitializeSynchronizedKey(synchronizedKey.getKeyIdentifier(), encryptedSessionKey);
				} else {
					byte[] seed = secureSeedGenerator.generateSeed(20);

					SecureRandom random = SecureRandom.getInstance(RANDOM_ALGORITH_NAME);
					random.setSeed(seed);
					random.nextBytes(newRandom);

					sessionKey = Arrays.copyOf(this.encrypter.encryptUsingSessionKey(synchronizedKey.getSeedSkey(), newRandom), AES_256_KEY_SIZE);
					encryptedSessionKey = newRandom;
					
					sessionKeyDetails = SessionKeyDetails.createSkeyToUsePreviouslyGeneratedSynchronizedKey(synchronizedKey.getKeyIdentifier(), encryptedSessionKey);
				}
			} else {
				this.skeyMap.clear();
				sessionKey = this.encrypter.generateSessionKey();
				encryptedSessionKey = this.encrypter.encryptUsingPublicKey(sessionKey);
				
				sessionKeyDetails = SessionKeyDetails.createNormalSkey(encryptedSessionKey);
			}

			byte[] encXMLPIDData = this.encrypter.encryptUsingSessionKey(sessionKey, pidXmlBytes);
			byte[] hmac = this.hashGenerator.generateSha256Hash(pidXmlBytes);
			byte[] encryptedHmacBytes = this.encrypter.encryptUsingSessionKey(sessionKey, hmac);

			String certificateIdentifier = this.encrypter.getCertificateIdentifier();

			byte[] demoBytes;
			/*if (DataType.P == dataType) {
				demoBytes = ((in.gov.uidai.authserver.protobuf.Auth.Pid) pid).getDemo().toByteArray();
			} else {
				demoBytes = getDemoXML((in.gov.uidai.authentication.uid_auth_request_data._1.Pid) pid).getBytes();
			}*/
			demoBytes = getDemoXML((in.gov.uidai.authentication.uid_auth_request_data._1.Pid) pid).getBytes();
			
			byte[] hashedDemoBytes = StringUtils.leftPad("0", 64, '0').getBytes();
			if (demoBytes != null && demoBytes.length > 0) {
				hashedDemoBytes = hashGenerator.generateSha256Hash(demoBytes);
			}

			AuthDataFromDeviceToAUA auaData = new AuthDataFromDeviceToAUA(uid, terminalId, sessionKeyDetails, encXMLPIDData, encryptedHmacBytes, hashedDemoBytes,
					certificateIdentifier, dataType, meta);
			
			return auaData;

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	/**
	 * This method returns Demo XML string for a given Pid. This method can be
	 * used for verifying Demo XML hash received in auth responses.
	 * 
	 * @param pid
	 *            Pid instance
	 * @return String containing XML representation of Demo element
	 */
	private String getDemoXML(Pid pid) {
		StringWriter sw = new StringWriter();

		try {
			JAXBContext.newInstance(Pid.class).createMarshaller().marshal(pid, sw);
		} catch (JAXBException e) {
			e.printStackTrace();
		}

		String pidXML = sw.toString();

		try {
			
			String demoStartStr = "<Demo";
			int startOfDemoElement = pidXML.lastIndexOf(demoStartStr);

			String demoEndStr = "</Demo";
			int beginningOfEndOfDemoElement = pidXML.indexOf(demoEndStr, startOfDemoElement + demoStartStr.length());

			int realEnd = pidXML.indexOf(">", beginningOfEndOfDemoElement + demoEndStr.length());

			String demoXML = pidXML.substring(startOfDemoElement, realEnd + 1);

			return demoXML;
		} catch (Exception e) {
			// In case of exception return blank string
			e.printStackTrace();
			return "";
		}

	}

	private String createPidXML(Pid pid) {
		StringWriter pidXML = new StringWriter();

		try {
			JAXBContext.newInstance(Pid.class).createMarshaller().marshal(pid, pidXML);
		} catch (JAXBException e) {
			e.printStackTrace();
		}

		System.out.println(pidXML.toString());

		return pidXML.toString();
	}

	public void restSkeyMap() {
		this.skeyMap.clear();
	}
	
	public static class SynchronizedKey {
		byte[] seedSkey;
		String keyIdentifier;
		Date seedCreationDate;
		
		public SynchronizedKey(byte[] seedSkey, String keyIdentifier, Date seedCreationDate) {
			super();
			this.seedSkey = seedSkey;
			this.keyIdentifier = keyIdentifier;
			this.seedCreationDate = seedCreationDate;
		}

		public String getKeyIdentifier() {
			return keyIdentifier;
		}
		
		public Date getSeedCreationDate() {
			return seedCreationDate;
		}
		
		public byte[] getSeedSkey() {
			return seedSkey;
		}
	}
}
