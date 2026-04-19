package com.ecommerce.ecommerce.web.dto;

import java.math.BigDecimal;
import java.util.List;

public record CartResponse(long cartId, List<CartLineResponse> lines, BigDecimal grandTotal) {
}
