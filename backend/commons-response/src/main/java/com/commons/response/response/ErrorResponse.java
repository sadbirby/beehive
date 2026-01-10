package com.commons.response.response;

public record ErrorResponse(
        ErrorDetail error
) {
    public record ErrorDetail(
            String code,
            String message,
            String details
    ) {
    }
}
