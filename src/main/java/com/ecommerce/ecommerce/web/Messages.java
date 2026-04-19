package com.ecommerce.ecommerce.web;

public final class Messages {

	private Messages() {
	}

	public static final String REGISTER_OK = Bilingual.of("تم إنشاء الحساب بنجاح", "Account created successfully");
	public static final String LOGIN_OK = Bilingual.of("تم تسجيل الدخول بنجاح", "Signed in successfully");
	public static final String PROFILE_OK = Bilingual.of("تم جلب بيانات الملف الشخصي", "Profile loaded successfully");

	public static final String LOGIN_FAIL = Bilingual.of("البريد أو كلمة المرور غير صحيحة", "Invalid email or password");
	public static final String EMAIL_TAKEN = Bilingual.of("هذا البريد مسجل مسبقاً", "This email is already registered");
	public static final String SKU_TAKEN = Bilingual.of("رمز المنتج (SKU) مستخدم بالفعل", "This product SKU is already in use");
	public static final String OTP_INVALID = Bilingual.of(
			"رمز التحقق غير صحيح أو منتهي. استخدم آخر رمز وصلك على نفس البريد خلال المدة المحددة.",
			"Invalid or expired OTP. Use the latest 6-digit code for the same email before it expires.");
	public static final String UNAUTHORIZED = Bilingual.of("يجب تسجيل الدخول أو التوكن غير صالح", "You must sign in or the token is invalid");
	public static final String FORBIDDEN = Bilingual.of("ليس لديك صلاحية لهذا الطلب", "You are not allowed to access this resource");
	public static final String VALIDATION = Bilingual.of("بيانات الطلب غير صالحة", "Request validation failed");
	public static final String BAD_JSON = Bilingual.of("صيغة JSON غير صحيحة أو غير مكتملة", "Malformed or unreadable JSON body");
	public static final String UNSUPPORTED_MEDIA = Bilingual.of(
			"Content-Type غير مدعوم. استخدم application/json",
			"Unsupported Content-Type. Use application/json for the request body");
	public static final String METHOD_NOT_ALLOWED = Bilingual.of("طريقة HTTP غير مسموحة لهذا المسار", "HTTP method is not allowed for this path");
	public static final String NOT_FOUND = Bilingual.of("المسار غير موجود", "Resource or path not found");
	public static final String MISSING_PARAM = Bilingual.of("معامل مفقود في الطلب", "A required request parameter is missing");
	public static final String DATABASE = Bilingual.of("خطأ في قاعدة البيانات", "Database error");
	public static final String INTERNAL = Bilingual.of("خطأ داخلي في الخادم", "Internal server error");

	public static final String PRODUCT_NOT_FOUND = Bilingual.of("المنتج غير موجود أو غير متاح", "Product not found or inactive");
	public static final String CART_NOT_FOUND = Bilingual.of("لا توجد سلة لهذا المستخدم", "No shopping cart for this user");
	public static final String CART_ITEM_NOT_FOUND = Bilingual.of("هذا الصنف غير موجود في السلة", "This item is not in your cart");
	public static final String MAX_MID_BANNERS = Bilingual.of(
			"لا يمكن تفعيل أكثر من إعلانين في منتصف الموقع",
			"At most two active mid-page banners are allowed");
	public static final String ADMIN_RESOURCE_NOT_FOUND = Bilingual.of("المحتوى غير موجود", "Content not found");
	public static final String CATEGORY_NOT_FOUND = Bilingual.of("التصنيف غير موجود", "Category not found");
	public static final String CATEGORY_SLUG_TAKEN = Bilingual.of("رابط التصنيف (slug) مستخدم بالفعل", "This category slug is already in use");
	public static final String CATEGORY_IN_USE = Bilingual.of(
			"لا يمكن حذف التصنيف لأنه مرتبط بمنتجات",
			"Cannot delete this category because products are assigned to it");
	public static final String CART_EMPTY_CHECKOUT = Bilingual.of("السلة فارغة — لا يمكن الدفع", "Cart is empty — cannot checkout");
	public static final String STRIPE_NOT_CONFIGURED = Bilingual.of(
			"الدفع غير مهيأ: اضبط stripe.secret-key وstripe.publishable-key",
			"Payments not configured: set stripe.secret-key and stripe.publishable-key");
	public static final String STRIPE_ERROR = Bilingual.of("فشل الاتصال بمزود الدفع", "Payment provider request failed");

	public static final String FORGOT_GENERIC = Bilingual.of(
			"إذا كان هناك حساب بهذا البريد، تم إصدار تعليمات إعادة التعيين.",
			"If an account exists for this email, password reset instructions have been issued.");
	public static final String RESET_OK = Bilingual.of("تم تغيير كلمة المرور. يمكنك تسجيل الدخول الآن.", "Password has been reset. You can sign in now.");

	public static final String MAIL_NO_SENDER = Bilingual.of(
			"إعداد البريد ناقص: يجب ضبط spring.mail.host والمنفذ حتى يعمل الإرسال.",
			"Mail is not configured: set spring.mail.host and port so the server can send email.");
	public static final String MAIL_NO_CREDS = Bilingual.of(
			"بيانات اعتماد SMTP ناقصة. اضبط MAIL_USERNAME وMAIL_PASSWORD أو spring.mail.username وspring.mail.password (استخدم App Password لـ Gmail).",
			"SMTP credentials missing. Set MAIL_USERNAME / MAIL_PASSWORD or spring.mail.* (use a Gmail App Password).");
	public static final String MAIL_SMTP_FAIL = Bilingual.of(
			"فشل إرسال البريد عبر SMTP. تحقق من كلمة مرور التطبيق والإعدادات أو سجل الخادم.",
			"SMTP rejected sending. Check the app password, mail settings, and server logs.");

	public static String forgotCodeSent(String email) {
		return Bilingual.of(
				"تم إرسال رمز من 6 أرقام إلى " + email + ". تحقق من الوارد والبريد غير المرغوب.",
				"A 6-digit code was sent to " + email + ". Check inbox and spam.");
	}
}
