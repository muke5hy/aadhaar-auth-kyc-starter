package in.gov.uidai.core.util;

public class CustomBase64Utils {

	public static String decode(String encodedString) {
		return CustomStringUtils.newStringUtf8(new CustomBase64()
				.decode(encodedString));
	}
}
