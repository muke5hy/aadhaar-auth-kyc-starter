package in.gov.uidai.core.model.xsd.kyc;

import org.apache.commons.lang3.StringUtils;

import java.util.Properties;

public class EKYCProperties {
	private static String lov;
	private static String idc;
	private static String publicIP;
	private static String lot;
	private static String terminalId;
	private static String sa;
	private static boolean usesBioFMR;
	private static boolean usesPa;
	private static String publicKeyFile;
	private static String auaCode;
	private static String lattitude;
	
	private static String fdc;
	
	private static String licenseKey;
	private static boolean usesBioIIR;
	
	private static String longitude;
	private static String pincode;
	private static String signaturePassword;
	private static String udc;
	private static boolean usesPin;
	
	private static String pidType;
	private static boolean usesBioFIR;
	private static String signKeyStore;
	private static boolean usesOtp;
	private static String publicKeyFileDSIG;
	private static String signatureAlias;
	private static boolean usesBio;
	private static String useSSK;
	private static boolean usesPfa;
	private static boolean usesPi;
	
	private static String proxyIP;
	private static String proxyHost;
	private static String proxyPort;
	private static String proxyPortNumber;
	private static String signKeyStoreFromDongle;
	private static String signaturePasswordFromDongle;
	
	private static String logPath;
	private static String ksaURL;
	//private static String KSA_IP;
	//private static String KSA_PORT;
	//private static String kycServerUrl;
	//private static String authServerUrl;
	//private static String otpServerUrl;
	//private static String asaLicenseKey;
	//private static String bfdServerUrl;
	public static String getKsaURL() {
		return ksaURL;
	}
	public static void setKsaURL(String ksaURL) {
		EKYCProperties.ksaURL = ksaURL;
	}
	public static String getLogPath() {
		return logPath;
	}
	public static void setLogPath(String logPath) {
		EKYCProperties.logPath = logPath;
	}
	/*
	public static String getKSA_IP() {
		return KSA_IP;
	}
	public static void setKSA_IP(String kSA_IP) {
		KSA_IP = kSA_IP;
	}
	public static String getKSA_PORT() {
		return KSA_PORT;
	}
	public static void setKSA_PORT(String kSA_PORT) {
		KSA_PORT = kSA_PORT;
	}
	*/
	
	
	public static String getProxyIP() {
		return proxyIP;
	}
	public static String getSignKeyStoreFromDongle() {
		return signKeyStoreFromDongle;
	}
	public static void setSignKeyStoreFromDongle(String signKeyStoreFromDongle) {
		EKYCProperties.signKeyStoreFromDongle = signKeyStoreFromDongle;
	}
	public static String getSignaturePasswordFromDongle() {
		return signaturePasswordFromDongle;
	}
	public static void setSignaturePasswordFromDongle(
			String signaturePasswordFromDongle) {
		EKYCProperties.signaturePasswordFromDongle = signaturePasswordFromDongle;
	}
	public static void setProxyIP(String proxyIP) {
		EKYCProperties.proxyIP = proxyIP;
	}
	public static String getProxyHost() {
		return proxyHost;
	}
	public static void setProxyHost(String proxyHost) {
		EKYCProperties.proxyHost = proxyHost;
	}
	public static String getProxyPort() {
		return proxyPort;
	}
	public static void setProxyPort(String proxyPort) {
		EKYCProperties.proxyPort = proxyPort;
	}
	public static String getProxyPortNumber() {
		return proxyPortNumber;
	}
	public static void setProxyPortNumber(String proxyPortNumber) {
		EKYCProperties.proxyPortNumber = proxyPortNumber;
	}
	/*
	public static String getKycServerUrl() {
		return kycServerUrl;
	}
	public static void setKycServerUrl(String kycServerUrl) {
		EKYCProperties.kycServerUrl = kycServerUrl;
	}
	*/
	public static String getLov() {
		return lov;
	}
	public static void setLov(String lov) {
		EKYCProperties.lov = lov;
	}
	public static String getIdc() {
		return idc;
	}
	public static void setIdc(String idc) {
		EKYCProperties.idc = idc;
	}
	public static String getPublicIP() {
		return publicIP;
	}
	public static void setPublicIP(String publicIP) {
		EKYCProperties.publicIP = publicIP;
	}
	public static String getLot() {
		return lot;
	}
	public static void setLot(String lot) {
		EKYCProperties.lot = lot;
	}
	public static String getTerminalId() {
		return terminalId;
	}
	public static void setTerminalId(String terminalId) {
		EKYCProperties.terminalId = terminalId;
	}
	public static String getSa() {
		return sa;
	}
	public static void setSa(String sa) {
		EKYCProperties.sa = sa;
	}
	public static boolean getUsesBioFMR() {
		return usesBioFMR;
	}
	public static void setUsesBioFMR(boolean usesBioFMR) {
		EKYCProperties.usesBioFMR = usesBioFMR;
	}
	public static boolean getUsesPa() {
		return usesPa;
	}
	public static void setUsesPa(boolean usesPa) {
		EKYCProperties.usesPa = usesPa;
	}
	public static String getPublicKeyFile() {
		return publicKeyFile;
	}
	public static void setPublicKeyFile(String publicKeyFile) {
		EKYCProperties.publicKeyFile = publicKeyFile;
	}
	public static String getAuaCode() {
		return auaCode;
	}
	public static void setAuaCode(String auaCode) {
		EKYCProperties.auaCode = auaCode;
	}
	public static String getLattitude() {
		return lattitude;
	}
	public static void setLattitude(String lattitude) {
		EKYCProperties.lattitude = lattitude;
	}
	/*
	public static String getAuthServerUrl() {
		return authServerUrl;
	}
	public static void setAuthServerUrl(String authServerUrl) {
		EKYCProperties.authServerUrl = authServerUrl;
	}
	*/
	public static String getFdc() {
		return fdc;
	}
	public static void setFdc(String fdc) {
		EKYCProperties.fdc = fdc;
	}
	/*
	public static String getOtpServerUrl() {
		return otpServerUrl;
	}
	public static void setOtpServerUrl(String otpServerUrl) {
		EKYCProperties.otpServerUrl = otpServerUrl;
	}
	*/
	public static String getLicenseKey() {
		return licenseKey;
	}
	public static void setLicenseKey(String licenseKey) {
		EKYCProperties.licenseKey = licenseKey;
	}
	public static boolean getUsesBioIIR() {
		return usesBioIIR;
	}
	public static void setUsesBioIIR(boolean usesBioIIR) {
		EKYCProperties.usesBioIIR = usesBioIIR;
	}
	/*
	public static String getAsaLicenseKey() {
		return asaLicenseKey;
	}
	public static void setAsaLicenseKey(String asaLicenseKey) {
		EKYCProperties.asaLicenseKey = asaLicenseKey;
	}
	*/
	public static String getLongitude() {
		return longitude;
	}
	public static void setLongitude(String longitude) {
		EKYCProperties.longitude = longitude;
	}
	public static String getPincode() {
		return pincode;
	}
	public static void setPincode(String pincode) {
		EKYCProperties.pincode = pincode;
	}
	public static String getSignaturePassword() {
		return signaturePassword;
	}
	public static void setSignaturePassword(String signaturePassword) {
		EKYCProperties.signaturePassword = signaturePassword;
	}
	public static String getUdc() {
		return udc;
	}
	public static void setUdc(String udc) {
		EKYCProperties.udc = udc;
	}
	public static boolean getUsesPin() {
		return usesPin;
	}
	public static void setUsesPin(boolean usesPin) {
		EKYCProperties.usesPin = usesPin;
	}
	/*
	public static String getBfdServerUrl() {
		return bfdServerUrl;
	}
	public static void setBfdServerUrl(String bfdServerUrl) {
		EKYCProperties.bfdServerUrl = bfdServerUrl;
	}
	*/
	public static String getPidType() {
		return pidType;
	}
	public static void setPidType(String pidType) {
		EKYCProperties.pidType = pidType;
	}
	public static boolean getUsesBioFIR() {
		return usesBioFIR;
	}
	public static void setUsesBioFIR(boolean usesBioFIR) {
		EKYCProperties.usesBioFIR = usesBioFIR;
	}
	public static String getSignKeyStore() {
		return signKeyStore;
	}
	public static void setSignKeyStore(String signKeyStore) {
		EKYCProperties.signKeyStore = signKeyStore;
	}
	public static boolean getUsesOtp() {
		return usesOtp;
	}
	public static void setUsesOtp(boolean usesOtp) {
		EKYCProperties.usesOtp = usesOtp;
	}
	public static String getPublicKeyFileDSIG() {
		return publicKeyFileDSIG;
	}
	public static void setPublicKeyFileDSIG(String publicKeyFileDSIG) {
		EKYCProperties.publicKeyFileDSIG = publicKeyFileDSIG;
	}
	public static String getSignatureAlias() {
		return signatureAlias;
	}
	public static void setSignatureAlias(String signatureAlias) {
		EKYCProperties.signatureAlias = signatureAlias;
	}
	public static boolean getUsesBio() {
		return usesBio;
	}
	public static void setUsesBio(boolean usesBio) {
		EKYCProperties.usesBio = usesBio;
	}
	public static String getUseSSK() {
		return useSSK;
	}
	public static void setUseSSK(String useSSK) {
		EKYCProperties.useSSK = useSSK;
	}
	public static boolean getUsesPfa() {
		return usesPfa;
	}
	public static void setUsesPfa(boolean usesPfa) {
		EKYCProperties.usesPfa = usesPfa;
	}
	public static boolean getUsesPi() {
		return usesPi;
	}
	public static void setUsesPi(boolean usesPi) {
		EKYCProperties.usesPi = usesPi;
	}
	public static void load(String str)
	{
	    Properties properties = new Properties();
	    try
	    {
	      
	    	//System.out.println("abc :"+EKYCProperties.class.getResourceAsStream("/kua.properties"));
	    	
	    	properties.load(EKYCProperties.class.getResourceAsStream("/kua.properties"));
	    	//properties.load(new FileInputStream(new File(str)));
	    	
	    	/*
	        if (properties.getProperty("authServerUrl") != null) {
	        	authServerUrl=properties.getProperty("authServerUrl").toString();
			}
			
			if (properties.getProperty("kycServerUrl") != null) {
				kycServerUrl=properties.getProperty("kycServerUrl").toString();
			}

			if (properties.getProperty("otpServerUrl") != null) {
				otpServerUrl=properties.getProperty("otpServerUrl").toString();
			}
			if (properties.getProperty("asaLicenseKey") != null) {
				asaLicenseKey=properties.getProperty("asaLicenseKey").toString();
			}
				if (properties.getProperty("bfdServerUrl") != null) {
				bfdServerUrl=properties.getProperty("bfdServerUrl").toString();
			}
			if (properties.getProperty("KSA_IP") != null) {
				KSA_IP=properties.getProperty("KSA_IP").toString();
			}
			if (properties.getProperty("KSA_PORT") != null) {
				KSA_PORT=properties.getProperty("KSA_PORT").toString();
			}
			*/
			if (properties.getProperty("auaCode") != null) {
				auaCode=properties.getProperty("auaCode").toString();
			}
			if (properties.getProperty("signKeyStore") != null) {
				signKeyStore=properties.getProperty("signKeyStore").toString();
			}

			if (properties.getProperty("sa") != null) {
				sa=properties.getProperty("sa").toString();
			}

			if (properties.getProperty("licenseKey") != null) {
				licenseKey=properties.getProperty("licenseKey").toString();
			}

			

			if (properties.getProperty("terminalId") != null) {
				terminalId=properties.getProperty("terminalId").toString();
			}

			if (properties.getProperty("publicKeyFile") != null) {
				publicKeyFile=properties.getProperty("publicKeyFile").toString();
			}

			if (properties.getProperty("publicKeyFileDSIG") != null && !StringUtils.isEmpty(properties.getProperty("publicKeyFileDSIG").toString())) {
				publicKeyFileDSIG=properties.getProperty("publicKeyFileDSIG").toString();
			} else {
				publicKeyFile=properties.getProperty("publicKeyFile").toString();
			}

			if (properties.getProperty("usesPi") != null) {
				usesPi=Boolean.valueOf(properties.getProperty("usesPi").toString());
			}

			if (properties.getProperty("usesPa") != null) {
				usesPa=Boolean.valueOf(properties.getProperty("usesPa").toString());
			}

			if (properties.getProperty("usesPfa") != null) {
				usesPfa=Boolean.valueOf(properties.getProperty("usesPfa").toString());
			}

			if (properties.getProperty("usesPin") != null) {
				usesPin=Boolean.valueOf(properties.getProperty("usesPin").toString());
			}

			if (properties.getProperty("usesOtp") != null) {
				usesOtp=Boolean.valueOf(properties.getProperty("usesOtp").toString());
			}

			if (properties.getProperty("usesBio") != null) {
				usesBio=Boolean.valueOf(properties.getProperty("usesBio").toString());
			}

			if (properties.getProperty("usesBioFMR") != null) {
				usesBioFMR=Boolean.valueOf(properties.getProperty("usesBioFMR").toString());
			}

			if (properties.getProperty("usesBioFIR") != null) {
				usesBioFIR=Boolean.valueOf(properties.getProperty("usesBioFIR").toString());
			}

			if (properties.getProperty("usesBioIIR") != null) {
				usesBioIIR=Boolean.valueOf(properties.getProperty("usesBioIIR").toString());
			}

			if (properties.getProperty("signatureAlias") != null) {
				signatureAlias=properties.getProperty("signatureAlias").toString();
			}

			if (properties.getProperty("signaturePassword") != null) {
				//NOTE properties.getProperty Only will give decrypted password , 
				//if we use properties.getProperty() this will give raw value(encrypted). 
				signaturePassword=properties.getProperty("signaturePassword").toString();
			}

			if (properties.getProperty("udc") != null) {
				udc=properties.getProperty("udc").toString();
			}

			if (properties.getProperty("fdc") != null) {
				fdc=properties.getProperty("fdc").toString();
			}

			if (properties.getProperty("idc") != null) {
				idc=properties.getProperty("idc").toString();
			}
			
			if (properties.getProperty("pincode") != null) {
				pincode=properties.getProperty("pincode").toString();
			}

			if (properties.getProperty("lot") != null) {
				lot=properties.getProperty("lot").toString();
			}
			
			if (properties.getProperty("lov") != null) {
				lov=properties.getProperty("lov").toString();
			}
			
			if (properties.getProperty("publicIP") != null) {
				publicIP=properties.getProperty("publicIP").toString();
			}
			
			if (properties.getProperty("useSSK") != null) {
				useSSK=properties.getProperty("useSSK").toString();
			}
			
			if (properties.getProperty("pidType") != null) {
				pidType=properties.getProperty("pidType").toString();
			}	
		
			if (properties.getProperty("PROXY_IP") != null) {
				proxyIP=properties.getProperty("PROXY_IP").toString();
			}
			if (properties.getProperty("PROXY_HOST") != null) {
				proxyHost=properties.getProperty("PROXY_HOST").toString();
			}
			if (properties.getProperty("PROXY_PORT") != null) {
				proxyPort=properties.getProperty("PROXY_PORT").toString();
			}
			if (properties.getProperty("PROXY_PORT_NUM") != null) {
				proxyPortNumber=properties.getProperty("PROXY_PORT_NUM").toString();
			}
			
			if (properties.getProperty("logPath") != null) {
				logPath=properties.getProperty("logPath").toString();
			}
			if (properties.getProperty("ksaUrl") != null) {
				ksaURL=properties.getProperty("ksaUrl").toString();
			}
			if (properties.getProperty("signKeyStoreFromDongle") != null) {
				signKeyStoreFromDongle=properties.getProperty("signKeyStoreFromDongle").toString();
			}
			if (properties.getProperty("signaturePasswordFromDongle") != null) {
				signaturePasswordFromDongle=properties.getProperty("signaturePasswordFromDongle").toString();
			}
			
	    }
	    catch (Exception e)
	    {
	    	System.out.println("Unable to load properties"+e);
	    }
	}
	  
}
