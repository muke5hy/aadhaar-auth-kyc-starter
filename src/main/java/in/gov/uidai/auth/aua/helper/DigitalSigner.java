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

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

import javax.xml.crypto.dsig.*;
import javax.xml.crypto.dsig.dom.DOMSignContext;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.keyinfo.KeyInfoFactory;
import javax.xml.crypto.dsig.keyinfo.X509Data;
import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;
import javax.xml.crypto.dsig.spec.TransformParameterSpec;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.security.KeyStore;
import java.security.Security;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * <code>DigitalSigner</code> class provides a utility method to digitally sign XML document.
 * This implementation uses .p12 file as a source of signer's digital certificate.  
 * In production environments, a hardware security module (HSM) should be used for digitally signing.
 * 
 * @author UIDAI
 *
 */
public class DigitalSigner {

	private static final String MEC_TYPE = "DOM";
	private static final String WHOLE_DOC_URI = "";
	private static final String KEY_STORE_TYPE = "PKCS12";
	
	private KeyStore.PrivateKeyEntry keyEntry;
	
	/**
	 * Constructor
	 * @param keyStoreFile - Location of .p12 file
	 * @param keyStorePassword - Password of .p12 file
	 * @param alias - Alias of the certificate in .p12 file
	 */
	public DigitalSigner(String keyStoreFile, char[] keyStorePassword, String alias) {
		this.keyEntry = getKeyFromKeyStore(keyStoreFile, keyStorePassword, alias);
		
		
		if (keyEntry == null) {
			throw new RuntimeException("Key could not be read for digital signature. Please check value of signature "
					+ "alias and signature password, and restart the Auth Client");
		}
	}

	/**
	 * Method to digitally sign an XML document.
	 * @param xmlDocument - Input XML Document.
	 * @return Signed XML document
	 */
	public String signXML(String xmlDocument, boolean includeKeyInfo) {
		Security.addProvider(new BouncyCastleProvider());
		try {
			// Parse the input XML
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			dbf.setNamespaceAware(true);
			Document inputDocument = dbf.newDocumentBuilder().parse(new InputSource(new StringReader(xmlDocument)));

			// Sign the input XML's DOM document
			Document signedDocument = sign(inputDocument, includeKeyInfo);
			
			// Convert the signedDocument to XML String
			StringWriter stringWriter = new StringWriter();
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer trans = tf.newTransformer();
			trans.transform(new DOMSource(signedDocument), new StreamResult(stringWriter));

			return stringWriter.getBuffer().toString();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Error while digitally signing the XML document", e);
		}
	}

	private Document sign(Document xmlDoc, boolean includeKeyInfo) throws Exception {
		
		if (System.getenv("SKIP_DIGITAL_SIGNATURE") != null) {
			return xmlDoc;
		}

		// Creating the XMLSignature factory.
		XMLSignatureFactory fac = XMLSignatureFactory.getInstance(MEC_TYPE);
		// Creating the reference object, reading the whole document for
		// signing.
		Reference ref = fac.newReference(WHOLE_DOC_URI, fac.newDigestMethod(DigestMethod.SHA1, null),
				Collections.singletonList(fac.newTransform(Transform.ENVELOPED, (TransformParameterSpec) null)), null,
				null);

		// Create the SignedInfo.
		SignedInfo sInfo = fac.newSignedInfo(
				fac.newCanonicalizationMethod(CanonicalizationMethod.INCLUSIVE, (C14NMethodParameterSpec) null),
				fac.newSignatureMethod(SignatureMethod.RSA_SHA1, null), Collections.singletonList(ref));

		if (keyEntry == null) {
			throw new RuntimeException(
					"Key could not be read for digital signature. Please check value of signature alias and signature password, and restart the Auth Client");
		}

		X509Certificate x509Cert = (X509Certificate) keyEntry.getCertificate();

		KeyInfo kInfo = getKeyInfo(x509Cert, fac);
		DOMSignContext dsc = new DOMSignContext(this.keyEntry.getPrivateKey(), xmlDoc.getDocumentElement());
		XMLSignature signature = fac.newXMLSignature(sInfo, includeKeyInfo ? kInfo : null);
		signature.sign(dsc);

		Node node = dsc.getParent();
		return node.getOwnerDocument();

	}
	
	@SuppressWarnings("unchecked")
	private KeyInfo getKeyInfo(X509Certificate cert, XMLSignatureFactory fac) {
		// Create the KeyInfo containing the X509Data.
		KeyInfoFactory kif = fac.getKeyInfoFactory();
		List x509Content = new ArrayList();
		x509Content.add(cert.getSubjectX500Principal().getName());
		x509Content.add(cert);
		X509Data xd = kif.newX509Data(x509Content);
		return kif.newKeyInfo(Collections.singletonList(xd));
	}

	private KeyStore.PrivateKeyEntry getKeyFromKeyStore(String keyStoreFile, char[] keyStorePassword, String alias) {
		// Load the KeyStore and get the signing key and certificate.
		FileInputStream keyFileStream = null;
		try {
			KeyStore ks = KeyStore.getInstance(KEY_STORE_TYPE);
			keyFileStream = new FileInputStream(keyStoreFile);
			ks.load(keyFileStream, keyStorePassword);

			KeyStore.PrivateKeyEntry entry = (KeyStore.PrivateKeyEntry) ks.getEntry(alias,
					new KeyStore.PasswordProtection(keyStorePassword));
			return entry;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (keyFileStream != null) {
				try {
					keyFileStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

}
