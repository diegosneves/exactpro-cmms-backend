package org.diegosneves.domain.client;

public record ClientSearchQuery(Integer page, Integer perPage, String terms, String sort, String direction) {
}