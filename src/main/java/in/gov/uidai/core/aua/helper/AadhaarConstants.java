package in.gov.uidai.core.aua.helper;

public final class AadhaarConstants {

  public static final String asaLicenseKey = "MBFWjkJHNF-fLidl8oOHtUwgL5p1ZjDbWrqsMEVEJLVEDpnlNj_CZTg";

  public static DigitalSigner digitalSigner;

  public AadhaarConstants() {
    digitalSigner = new DigitalSigner(getFileFromResource("Staging_Signature_PrivateKey.p12"), "public".toCharArray(), "public");
  }

  private String getFileFromResource(String fileName) {
    return this.getClass().getClassLoader().getResource(fileName).getFile();
  }

}
