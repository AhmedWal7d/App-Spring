package com.ecommerce.ecommerce.auth;

public record OtpSendResult(boolean success, OtpSendFailure failure, String smtpHint) {

	public static OtpSendResult ok() {
		return new OtpSendResult(true, null, null);
	}

	public static OtpSendResult fail(OtpSendFailure failure, String smtpHint) {
		return new OtpSendResult(false, failure, smtpHint);
	}
}
