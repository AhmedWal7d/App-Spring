package com.ecommerce.ecommerce.auth;

final class OtpNormalizer {

	private OtpNormalizer() {
	}

	/**
	 * Keeps ASCII digits and maps Arabic-Indic / Eastern-Arabic digits to ASCII, then takes the first 6 digits
	 * (handles pasted OTP with labels or invisible characters from email clients).
	 */
	static String toSixDigitAscii(String raw) {
		if (raw == null) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < raw.length(); i++) {
			char c = raw.charAt(i);
			if (c >= '0' && c <= '9') {
				sb.append(c);
			}
			else if (c >= '\u0660' && c <= '\u0669') {
				sb.append((char) ('0' + (c - '\u0660')));
			}
			else if (c >= '\u06F0' && c <= '\u06F9') {
				sb.append((char) ('0' + (c - '\u06F0')));
			}
		}
		String digits = sb.toString();
		if (digits.length() >= 6) {
			return digits.substring(0, 6);
		}
		return digits;
	}
}
