package in.gov.uidai.core.aua.helper;

public final class AadhaarConstants {

  public final String asaLicenseKey = "MH4hSkrev2h_Feu0lBRC8NI-iqzT299_qPSSstOFbNFTwWrie29ThDo";

  public final String auaLicenseKey = "MBFWjkJHNF-fLidl8oOHtUwgL5p1ZjDbWrqsMEVEJLVEDpnlNj_CZTg";

  public DigitalSigner digitalSigner;

  private static AadhaarConstants aadhaarConstants;

  public static AadhaarConstants get() {
    if (aadhaarConstants == null) {
      aadhaarConstants = new AadhaarConstants();
    }
    return aadhaarConstants;
  }

  private AadhaarConstants() {
    digitalSigner = new DigitalSigner(getFileFromResource("Staging_Signature_PrivateKey.p12"), "public".toCharArray(), "public");
  }

  private String getFileFromResource(String fileName) {
    return this.getClass().getClassLoader().getResource(fileName).getFile();
  }

}
