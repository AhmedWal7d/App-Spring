package com.ecommerce.ecommerce.security;

import java.io.IOException;

import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private static final String AUTH_HEADER = "Authorization";

	private final JwtService jwtService;
	private final CustomUserDetailsService userDetailsService;

	public JwtAuthenticationFilter(JwtService jwtService, CustomUserDetailsService userDetailsService) {
		this.jwtService = jwtService;
		this.userDetailsService = userDetailsService;
	}

	@Override
	protected void doFilterInternal(
			@NonNull HttpServletRequest request,
			@NonNull HttpServletResponse response,
			@NonNull FilterChain filterChain) throws ServletException, IOException {
		String header = request.getHeader(AUTH_HEADER);
		String token = extractBearerToken(header);
		if (token == null || token.isEmpty()) {
			filterChain.doFilter(request, response);
			return;
		}
		try {
			String email = jwtService.extractEmail(token);
			if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				UserDetails userDetails = userDetailsService.loadUserByUsername(email);
				if (jwtService.isTokenValid(token, userDetails.getUsername())) {
					var auth = new UsernamePasswordAuthenticationToken(
							userDetails, null, userDetails.getAuthorities());
					auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(auth);
				}
			}
		}
		catch (Exception ignored) {
			SecurityContextHolder.clearContext();
		}
		filterChain.doFilter(request, response);
	}

	/**
	 * Accepts {@code Authorization: Bearer &lt;jwt&gt;} with any casing on {@code Bearer}
	 * (some clients send {@code bearer}), per common HTTP client behavior.
	 */
	private static String extractBearerToken(String header) {
		if (header == null) {
			return null;
		}
		String h = header.trim();
		// Prefix must be "Bearer" + single space (RFC 6750-style)
		if (h.length() <= 7 || !h.regionMatches(true, 0, "Bearer ", 0, 7)) {
			return null;
		}
		return h.substring(7).trim();
	}
}
