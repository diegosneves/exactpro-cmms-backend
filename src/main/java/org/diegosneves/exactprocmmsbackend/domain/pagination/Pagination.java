package org.diegosneves.exactprocmmsbackend.domain.pagination;

import java.util.List;

public record Pagination<T>(Integer currentPage, Integer perPage, Long total, List<T> items) {
}
