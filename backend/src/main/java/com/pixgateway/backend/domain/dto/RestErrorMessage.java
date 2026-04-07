package com.pixgateway.backend.domain.dto;

import org.springframework.http.HttpStatus;

public record RestErrorMessage(HttpStatus status, String message) {
}