package com.kesi.planit.core;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

public final class PageRequestFactory {
    public static final int MAX_SIZE = 50;

    public static PageRequest of(int pageNumber, int pageSize, Sort sort) {
        pageSize = Math.min(pageSize, MAX_SIZE);
        return PageRequest.of(pageNumber, pageSize, sort);
    }

    public static PageRequest of(int pageNumber, int pageSize) {
        return of(pageNumber, pageSize, Sort.unsorted());
    }

    public static PageRequest of(int pageNumber, int pageSize, Sort.Direction direction, String... properties) {
        return of(pageNumber, pageSize, Sort.by(direction, properties));
    }
}
