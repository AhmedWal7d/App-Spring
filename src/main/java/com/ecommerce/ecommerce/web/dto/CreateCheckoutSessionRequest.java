package com.ecommerce.ecommerce.web.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * اختياري — روابط العودة بعد الدفع أو الإلغاء. إن لم تُرسل، تُستخدم عناوين افتراضية من الخادم.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record CreateCheckoutSessionRequest(String successUrl, String cancelUrl) {
}
