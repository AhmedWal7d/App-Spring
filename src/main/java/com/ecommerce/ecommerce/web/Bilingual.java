package com.ecommerce.ecommerce.web;

import java.util.Enumeration;
import java.util.Locale;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;

/** Arabic first, then English, separated by {@value #SEP}. */
public final class Bilingual {

	public static final String SEP = " | ";

	/**
	 * Optional language from JSON body (set by controllers when query param {@code lang} is absent).
	 */
	public static final String REQUEST_LANG_ATTR = "com.ecommerce.ecommerce.requestLang";

	private Bilingual() {
	}

	public static String of(String ar, String en) {
		return ar + SEP + en;
	}

	public record Parts(String ar, String en) {
	}

	public static Parts parts(String combined) {
		if (combined == null) {
			return new Parts(null, null);
		}
		int idx = combined.indexOf(SEP);
		if (idx < 0) {
			return new Parts(combined.trim(), "");
		}
		return new Parts(combined.substring(0, idx).trim(), combined.substring(idx + SEP.length()).trim());
	}

	/**
	 * Resolves the client language for API messages. Order:
	 * <ol>
	 * <li>{@code ?lang=en} or {@code ?lang=ar} (also {@code language=})</li>
	 * <li>header {@code X-App-Language: en} (or {@code ar})</li>
	 * <li>first language-range in raw {@code Accept-Language} (some setups mis-report {@link HttpServletRequest#getLocale()})</li>
	 * <li>{@link HttpServletRequest#getLocales()} then {@link HttpServletRequest#getLocale()}</li>
	 * <li>default Arabic</li>
	 * </ol>
	 */
	public static Locale resolveLocale(HttpServletRequest request) {
		if (request == null) {
			return Locale.forLanguageTag("ar");
		}
		String qp = firstNonBlank(request.getParameter("lang"), request.getParameter("language"));
		if (qp != null) {
			return localeFromExplicitCode(qp);
		}
		Object bodyLang = request.getAttribute(REQUEST_LANG_ATTR);
		if (bodyLang instanceof String s && !s.isBlank()) {
			return localeFromExplicitCode(s.trim());
		}
		String headerLang = request.getHeader("X-App-Language");
		if (headerLang != null && !headerLang.isBlank()) {
			return localeFromExplicitCode(headerLang.trim());
		}
		Locale fromAccept = primaryLocaleFromAcceptLanguageHeader(request);
		if (fromAccept != null) {
			return fromAccept;
		}
		Enumeration<Locale> locales = request.getLocales();
		if (locales != null && locales.hasMoreElements()) {
			Locale l = locales.nextElement();
			if (l != null && l.getLanguage() != null && !l.getLanguage().isBlank()) {
				return l;
			}
		}
		Locale servlet = request.getLocale();
		if (servlet != null && servlet.getLanguage() != null && !servlet.getLanguage().isBlank()) {
			return servlet;
		}
		return Locale.forLanguageTag("ar");
	}

	private static String firstNonBlank(String a, String b) {
		if (a != null && !a.isBlank()) {
			return a;
		}
		if (b != null && !b.isBlank()) {
			return b;
		}
		return null;
	}

	private static Locale localeFromExplicitCode(String code) {
		String q = code.toLowerCase(Locale.ROOT);
		if (q.startsWith("en")) {
			return Locale.ENGLISH;
		}
		if (q.startsWith("ar")) {
			return Locale.forLanguageTag("ar");
		}
		return Locale.forLanguageTag(code.replace('_', '-'));
	}

	/**
	 * Parses the first language-range in {@code Accept-Language} (before {@code ,} and quality {@code ;q=}).
	 */
	private static Locale primaryLocaleFromAcceptLanguageHeader(HttpServletRequest request) {
		String h = request.getHeader("Accept-Language");
		if (h == null || h.isBlank()) {
			return null;
		}
		String first = h.split(",")[0].trim();
		int semi = first.indexOf(';');
		if (semi >= 0) {
			first = first.substring(0, semi).trim();
		}
		if (first.isEmpty()) {
			return null;
		}
		Locale l = Locale.forLanguageTag(first.replace('_', '-'));
		if (l.getLanguage().isBlank()) {
			return null;
		}
		return l;
	}

	/**
	 * Picks Arabic unless the locale language is English ({@code en*}).
	 */
	public static String pick(String combined, Locale locale) {
		if (combined == null) {
			return null;
		}
		Parts p = parts(combined);
		if (locale == null || locale.getLanguage() == null || locale.getLanguage().isBlank()) {
			return p.ar();
		}
		if (locale.getLanguage().toLowerCase(Locale.ROOT).startsWith("en")) {
			return p.en().isEmpty() ? p.ar() : p.en();
		}
		return p.ar();
	}

	public static String pickForRequest(String combined, HttpServletRequest request) {
		return pick(combined, resolveLocale(request));
	}

	/**
	 * Uses the current HTTP request when available; otherwise returns the full bilingual string.
	 */
	public static String resolveForRequest(String combined) {
		var attrs = RequestContextHolder.getRequestAttributes();
		if (attrs instanceof ServletRequestAttributes sra) {
			return pickForRequest(combined, sra.getRequest());
		}
		return combined;
	}
}
