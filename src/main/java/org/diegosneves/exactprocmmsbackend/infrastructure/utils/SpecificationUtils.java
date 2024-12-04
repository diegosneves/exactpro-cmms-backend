package org.diegosneves.exactprocmmsbackend.infrastructure.utils;

import org.springframework.data.jpa.domain.Specification;

public final class SpecificationUtils {

    private static final String LIKE_WILDCARD = "%";

    private SpecificationUtils() {}

    public static <T> Specification<T> like(final String property, final String term) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.upper(root.get(property)), LIKE_WILDCARD + term.toUpperCase() + LIKE_WILDCARD);
    }
}
