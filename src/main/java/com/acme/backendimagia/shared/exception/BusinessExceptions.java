package com.acme.backendimagia.shared.exception;

import com.acme.backendimagia.interfaces.persistence.exception.CustomException;
import org.springframework.http.HttpStatus;

public class BusinessExceptions {

    public static abstract class BusinessException extends CustomException {
        public BusinessException(String message, HttpStatus status) {
            super(message, status);
        }
    }

    public static class BadRequestException extends BusinessException {
        public BadRequestException(String message) {
            super(message, HttpStatus.BAD_REQUEST);
        }
    }

    public static class UnauthorizedException extends BusinessException {
        public UnauthorizedException(String message) {
            super(message, HttpStatus.UNAUTHORIZED);
        }
    }

    public static class ForbiddenException extends BusinessException {
        public ForbiddenException(String message) {
            super(message, HttpStatus.FORBIDDEN);
        }
    }

    public static class ResourceNotFoundException extends BusinessException {
        public ResourceNotFoundException(String message) {
            super(message, HttpStatus.NOT_FOUND);
        }
    }

    public static class ValidationException extends BusinessException {
        public ValidationException(String message) {
            super(message, HttpStatus.BAD_REQUEST);
        }
    }

    public static class ConflictException extends BusinessException {
        public ConflictException(String message) {
            super(message, HttpStatus.CONFLICT);
        }
    }
}