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

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.crypto.dsig.XMLSignature;
import javax.xml.crypto.dsig.XMLSignatureFactory;
import javax.xml.crypto.dsig.dom.DOMValidateContext;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.security.GeneralSecurityException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;


/**
 * Class that verifies digital signature of a signed XML document.
 * 
 * @author UIDAI
 *
 */
public class SignatureVerifier {

	private String publicKeyFile = "";

	/**
	 * Constructor
	 * @param publicKeyFile File name of signer's public key file (.cer)
	 */
	public SignatureVerifier(String publicKeyFile) {
		this.publicKeyFile = publicKeyFile;
	}

	public boolean verify(String signedXml) {

		boolean verificationResult = false;

		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			dbf.setNamespaceAware(true);
			Document signedDocument = dbf.newDocumentBuilder().parse(new InputSource(new StringReader(signedXml)));

			NodeList nl = signedDocument.getElementsByTagNameNS(XMLSignature.XMLNS, "Signature");
			if (nl.getLength() == 0) {
				throw new IllegalArgumentException("Cannot find Signature element");
			}

			XMLSignatureFactory fac = XMLSignatureFactory.getInstance("DOM");

			DOMValidateContext valContext = new DOMValidateContext(getCertificateFromFile(publicKeyFile).getPublicKey(), nl.item(0));
			XMLSignature signature = fac.unmarshalXMLSignature(valContext);
			
			verificationResult = signature.validate(valContext);
			
		} catch (Exception e) {
			System.out.println("Error while verifying digital siganature" + e.getMessage());
			e.printStackTrace();
		}

		return verificationResult;
	}

	private X509Certificate getCertificateFromFile(String certificateFile) throws GeneralSecurityException, IOException {
		FileInputStream fis = null;
		try {
			CertificateFactory certFactory = CertificateFactory.getInstance("X.509", "BC");
			fis = new FileInputStream(certificateFile);
			return (X509Certificate) certFactory.generateCertificate(fis);
		} finally {
			if (fis != null) {
				fis.close();
			}
		}

	}

}
