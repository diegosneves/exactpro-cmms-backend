package org.diegosneves.exactprocmmsbackend.domain.exceptions;

public record DomainException(String message, int statusCode) {
}
