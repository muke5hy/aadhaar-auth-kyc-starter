/**
 * DISCLAIMER: The sample code or utility or tool described herein
 * is provided on an "as is" basis, without warranty of any kind.
 * UIDAI does not warrant or guarantee the individual success
 * developers may have in implementing the sample code on their
 * environment. 
 * 
 * UIDAI does not warrant, guarantee or make any representations
 * of any kind with respect to the sample code and does not make
 * any representations or warranties regarding the use, results
 * of use, accuracy, timeliness or completeness of any data or
 * information relating to the sample code. UIDAI disclaims all
 * warranties, express or implied, and in particular, disclaims
 * all warranties of merchantability, fitness for a particular
 * purpose, and warranties related to the code, or any service
 * or software related thereto. 
 * 
 * UIDAI is not responsible for and shall not be liable directly
 * or indirectly for any direct, indirect damages or costs of any
 * type arising out of use or any action taken by you or others
 * related to the sample code.
 * 
 * THIS IS NOT A SUPPORTED SOFTWARE.
 * 
 */
package in.gov.uidai.core.util;

import in.gov.uidai.core.aua.client.NamespaceFilter;
import in.gov.uidai.core.model.xsd.kyc.uid_kyc_response._1.KycRes;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import javax.xml.bind.*;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringReader;
import java.io.StringWriter;

public class XMLUtilities {

	private XMLUtilities() {
	}

	public static Object parseXML(Class clazz, String xmlToParse)
			throws JAXBException {
		// Create an XMLReader to use with our filter
		try {
			// Prepare JAXB objects
			JAXBContext jc = JAXBContext.newInstance(clazz);
			Unmarshaller u = jc.createUnmarshaller();

			XMLReader reader;
			reader = XMLReaderFactory.createXMLReader();

			// Create the filter (to add namespace) and set the xmlReader as its
			// parent.
			NamespaceFilter inFilter = new NamespaceFilter(
					"http://www.uidai.gov.in/kyc/uid-kyc-response/1.0", true);
			inFilter.setParent(reader);

			// Prepare the input, in this case a java.io.File (output)
			InputSource is = new InputSource(new StringReader(xmlToParse));

			// Create a SAXSource specifying the filter
			SAXSource source = new SAXSource(inFilter, is);

			// Do unmarshalling
			Object res = u.unmarshal(source, clazz).getValue();
			return res;
		} catch (SAXException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static String getXML(KycRes input, boolean format) {
		StringWriter kycResp = new StringWriter();
		JAXBElement kycRespElement = new JAXBElement(new QName(
				"http://www.uidai.gov.in/kyc/uid-kyc-response/1.0", "KycRes"),
				KycRes.class, input);
		try {
			Marshaller marshaller = JAXBContext.newInstance(KycRes.class)
					.createMarshaller();
			if (format) {
				marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
						Boolean.TRUE);
			}
			marshaller.marshal(kycRespElement, kycResp);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return kycResp.toString();
	}

	public static Document getDomObject(String string) throws Exception {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		DocumentBuilder builder = dbf.newDocumentBuilder();
		return builder.parse(new InputSource(new StringReader(string)));
	}

	public static String getString(Document dom) {
		TransformerFactory tf = TransformerFactory.newInstance();
		try {
			Transformer transformer = tf.newTransformer();
			StringWriter xml = new StringWriter();
			transformer.transform(new DOMSource(dom), new StreamResult(xml));
			return xml.toString();
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
		return "";
	}

	public static Document addRarNode(Document kycDOM, byte[] codedAuthXML) {
		Element rad = kycDOM.createElement("Rad");
		try {
			rad.setTextContent(DatatypeConverter
					.printBase64Binary(codedAuthXML));
		} catch (DOMException e) {
			e.printStackTrace();
		}
		kycDOM.getDocumentElement().appendChild(rad);
		return null;
	}

}
