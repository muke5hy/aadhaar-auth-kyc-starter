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

import in.gov.uidai.authentication.common.types._1.Meta;
import in.gov.uidai.authentication.uid_auth_request._1.DataType;
import in.gov.uidai.authentication.uid_auth_request_data._1.BioMetricType;
import in.gov.uidai.authentication.uid_auth_request_data._1.BiometricPosition;
import in.gov.uidai.authentication.uid_auth_request_data._1.MatchingStrategy;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * This class is used for collecting various information from the GUI so that it
 * can be used for creating Pid and Auth objects.
 * 
 * @author UIDAI
 * 
 */
public class DeviceCollectedAuthData implements Serializable {

	/**
	 * generated serial version id
	 */
	private static final long serialVersionUID = -969857695481409943L;

	private String language;

	private String uid;
	private String name;
	private String lname;
	private MatchingStrategy nameMatchStrategy;
	private int nameMatchValue;
	private int localNameMatchValue;

	private String gender;
	private String dob;
	private String dobType;
	private String phoneNo;
	private String email;

	private String age;

	private String pinCode;
	private String careOf;
	private String building;
	private String street;
	private String landmark;
	private String locality;
	private String village;
	private String poName;
	private String subdistrict;
	private String district;
	private String state;
	private MatchingStrategy addressMatchStrategy;

	private String fullAddress;
	private String localFullAddress;
	private MatchingStrategy fullAddressMatchStrategy;
	private int fullAddressMatchValue;
	private int localFullAddressMatchValue;

	private String staticPin;
	private String dynamicPin;

	private String biometricPosition;
	private String biometricType;

	private DataType pidType;

	private List<BiometricData> biometrics;

	private Meta deviceMetaData;

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getName() {
		return name;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPinCode() {
		return pinCode;
	}

	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}

	public String getCareOf() {
		return careOf;
	}

	public void setCareOf(String careOf) {
		this.careOf = careOf;
	}

	public String getBuilding() {
		return building;
	}

	public void setBuilding(String building) {
		this.building = building;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getLandmark() {
		return landmark;
	}

	public void setLandmark(String landmark) {
		this.landmark = landmark;
	}

	public String getLocality() {
		return locality;
	}

	public void setLocality(String locality) {
		this.locality = locality;
	}

	public String getVillage() {
		return village;
	}

	public void setVillage(String village) {
		this.village = village;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getStaticPin() {
		return staticPin;
	}

	public void setStaticPin(String staticPin) {
		this.staticPin = staticPin;
	}

	public String getDynamicPin() {
		return dynamicPin;
	}

	public void setDynamicPin(String dynamicPin) {
		this.dynamicPin = dynamicPin;
	}

	public List<BiometricData> getBiometrics() {
		return biometrics;
	}

	public void setBiometrics(List<BiometricData> biometrics) {
		this.biometrics = biometrics;
	}

	public MatchingStrategy getAddressMatchStrategy() {
		return addressMatchStrategy;
	}

	public void setAddressMatchStrategy(MatchingStrategy addressMatchStrategy) {
		this.addressMatchStrategy = addressMatchStrategy;
	}

	public MatchingStrategy getNameMatchStrategy() {
		return nameMatchStrategy;
	}

	public void setNameMatchStrategy(MatchingStrategy nameMatchStrategy) {
		this.nameMatchStrategy = nameMatchStrategy;
	}

	public String getFullAddress() {
		return fullAddress;
	}

	public void setFullAddress(String fullAddress) {
		this.fullAddress = fullAddress;
	}

	public MatchingStrategy getFullAddressMatchStrategy() {
		return fullAddressMatchStrategy;
	}

	public void setFullAddressMatchStrategy(
			MatchingStrategy fullAddressMatchStrategy) {
		this.fullAddressMatchStrategy = fullAddressMatchStrategy;
	}

	public int getFullAddressMatchValue() {
		return fullAddressMatchValue;
	}

	public void setFullAddressMatchValue(int fullAddressMatchValue) {
		this.fullAddressMatchValue = fullAddressMatchValue;
	}

	public int getNameMatchValue() {
		return nameMatchValue;
	}

	public void setNameMatchValue(int nameMatchValue) {
		this.nameMatchValue = nameMatchValue;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	String getBiometricPosition() {
		return this.biometricPosition;
	}

	public void setBiometricPosition(String biometricPosition) {
		this.biometricPosition = biometricPosition;
	}

	public String getLname() {
		return lname;
	}

	public String getLocalFullAddress() {
		return localFullAddress;
	}

	public int getLocalFullAddressMatchValue() {
		return localFullAddressMatchValue;
	}

	public int getLocalNameMatchValue() {
		return localNameMatchValue;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public void setLocalFullAddress(String localFullAddress) {
		this.localFullAddress = localFullAddress;
	}

	public void setLocalFullAddressMatchValue(int localFullAddressMatchValue) {
		this.localFullAddressMatchValue = localFullAddressMatchValue;
	}

	public void setLocalNameMatchValue(int localNameMatchValue) {
		this.localNameMatchValue = localNameMatchValue;
	}

	public String getBiometricType() {
		return biometricType;
	}

	public void setBiometricType(String biometricType) {
		this.biometricType = biometricType;
	}

	public String getPoName() {
		return poName;
	}

	public void setPoName(String poName) {
		this.poName = poName;
	}

	public String getSubdistrict() {
		return subdistrict;
	}

	public void setSubdistrict(String subdistrict) {
		this.subdistrict = subdistrict;
	}

	public String getDobType() {
		return dobType;
	}

	public void setDobType(String dobType) {
		this.dobType = dobType;
	}

	public void setPidType(DataType pidType) {
		this.pidType = pidType;
	}

	public DataType getPidType() {
		return pidType;
	}

	public Meta getDeviceMetaData() {
		return deviceMetaData;
	}

	public void setDeviceMetaData(Meta deviceMetaData) {
		this.deviceMetaData = deviceMetaData;
	}

	public static class BiometricData {

		BiometricPosition position;
		BioMetricType type;
		byte[] biometricContent;

		public BiometricData(BiometricPosition position, BioMetricType type,
				byte[] biometricContent) {
			super();
			this.position = position;
			this.type = type;
			this.biometricContent = biometricContent;
		}

		/**
		 * ISO FMR/FIR/IIR
		 * 
		 * @return
		 */
		public byte[] getBiometricContent() {
			return biometricContent;
		}

		public BiometricPosition getPosition() {
			return position;
		}

		public BioMetricType getType() {
			return type;
		}

		@Override
		public String toString() {
			return position.toString();
		}
	}
}
