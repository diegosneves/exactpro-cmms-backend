package org.diegosneves.exactprocmmsbackend.domain.client;

public record ClientSearchQuery(Integer page, Integer perPage, String terms, String sort, String direction) {
}
