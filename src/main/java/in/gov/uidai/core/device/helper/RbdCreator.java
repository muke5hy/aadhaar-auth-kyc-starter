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
package in.gov.uidai.core.device.helper;

import com.google.protobuf.ByteString;
import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;
import in.gov.uidai.core.device.model.DeviceCollectedBfdData;
import in.gov.uidai.core.model.protobuf.bfd.Bfd;
import in.gov.uidai.core.model.xsd.uid_bfd_request_data._1.BfdBio;
import in.gov.uidai.core.model.xsd.uid_bfd_request_data._1.BfdBios;
import in.gov.uidai.core.model.xsd.uid_bfd_request_data._1.Rbd;
import in.gov.uidai.core.model.protobuf.bfd.Bfd.FingerPosition;
import in.gov.uidai.core.model.protobuf.bfd.Bfd.Rbd.Builder;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * This class provides utility method to create Rbd object using data that was collected by the BFD Device
 * in the BFD Client GUI.
 * 
 * @author UIDAI
 * 
 */
public class RbdCreator {

	public static Rbd createXmlRbd(DeviceCollectedBfdData data) {

		Rbd rbd = new Rbd();
		rbd.setVer("1.0");
		
		if (data != null) {
			Calendar calendar = GregorianCalendar.getInstance();
			rbd.setTs(XMLGregorianCalendarImpl.createDateTime(
					calendar.get(Calendar.YEAR),
					calendar.get(Calendar.MONTH) + 1,
					calendar.get(Calendar.DAY_OF_MONTH),
					calendar.get(Calendar.HOUR_OF_DAY),
					calendar.get(Calendar.MINUTE),
					calendar.get(Calendar.SECOND)));

			if (data.getBiometrics() != null
					&& data.getBiometrics().size() > 0) {
				
				BfdBios bios = new BfdBios();
				rbd.setBios(bios);

				for (in.gov.uidai.core.device.model.DeviceCollectedBfdData.BiometricData p : data.getBiometrics()) {
					BfdBio bio = new BfdBio();
					bio.setNa(1);
					bio.setNfiq(p.getNfiq());
					bio.setPos(p.getPosition());
					bio.setValue(p.getBiometricContent());

					rbd.getBios().getBio().add(bio);
				}
			}
		}

		return rbd;
	}


	public static Bfd.Rbd createProtoRbd(DeviceCollectedBfdData data) {

		Builder rbdBuilder = Bfd.Rbd.newBuilder();		
		
		if (data != null) {
			
			Calendar calendar = GregorianCalendar.getInstance();
			rbdBuilder.setTs(XMLGregorianCalendarImpl.createDateTime(
					calendar.get(Calendar.YEAR),
					calendar.get(Calendar.MONTH) + 1,
					calendar.get(Calendar.DAY_OF_MONTH),
					calendar.get(Calendar.HOUR_OF_DAY),
					calendar.get(Calendar.MINUTE),
					calendar.get(Calendar.SECOND)).toString());
			rbdBuilder.setVer("1.0");
			
			
			if (data.getBiometrics() != null
					&& data.getBiometrics().size() > 0) {
				
				Bfd.BfdBios.Builder bios = Bfd.BfdBios.newBuilder();
				
				for (in.gov.uidai.core.device.model.DeviceCollectedBfdData.BiometricData p : data.getBiometrics()) {
					Bfd.BfdBio.Builder bio = Bfd.BfdBio.newBuilder();
					bio.setNa(1);
					bio.setNfiq(p.getNfiq());
					bio.setPos(FingerPosition.valueOf(p.getPosition().name()));
					bio.setContent(ByteString.copyFrom(p.getBiometricContent()));
					
					bios.addBio(bio);
				}
				
				rbdBuilder.setBios(bios);
			}
			
		}
		
		System.out.println(rbdBuilder.build().toString() + " -- " + rbdBuilder.isInitialized());

		return rbdBuilder.build();

	}
}
