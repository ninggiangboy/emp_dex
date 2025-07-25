package dev.ngb.empdex.shared.core.base;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public record Pageable(Integer pageNumber, Integer pageSize, String sortField, Direction sortDirection) {
    private final static int DEFAULT_PAGE_SIZE = 20;

    public Pageable {
        pageNumber = pageNumber == null ? 0 : pageNumber;
        pageSize = pageSize == null ? DEFAULT_PAGE_SIZE : pageSize;
        sortDirection = sortDirection == null ? Direction.ASC : sortDirection;

        if (pageNumber < 0 || pageSize <= 0) {
            throw new IllegalArgumentException("Invalid pagination parameters");
        }
    }

    // Factory methods for pagination only
    public static Pageable of(Integer number, Integer size) {
        return new Pageable(number, size, null, null);
    }

    public static Pageable firstPage() {
        return new Pageable(0, DEFAULT_PAGE_SIZE, null, null);
    }

    public static Pageable page(int number) {
        return new Pageable(number, DEFAULT_PAGE_SIZE, null, null);
    }

    // Factory methods with sorting
    public static Pageable of(Integer number, Integer size, String field, Direction direction) {
        return new Pageable(number, size, field, direction);
    }

    public static Pageable withSort(Integer number, Integer size, String field) {
        return new Pageable(number, size, field, Direction.ASC);
    }

    public static Pageable ascending(Integer number, Integer size, String field) {
        return new Pageable(number, size, field, Direction.ASC);
    }

    public static Pageable descending(Integer number, Integer size, String field) {
        return new Pageable(number, size, field, Direction.DESC);
    }

    // Convenience methods for first page with sorting
    public static Pageable firstPageSorted(String field, Direction direction) {
        return new Pageable(0, DEFAULT_PAGE_SIZE, field, direction);
    }

    public static Pageable firstPageAsc(String field) {
        return new Pageable(0, DEFAULT_PAGE_SIZE, field, Direction.ASC);
    }

    public static Pageable firstPageDesc(String field) {
        return new Pageable(0, DEFAULT_PAGE_SIZE, field, Direction.DESC);
    }

    // Utility methods
    public boolean hasSorting() {
        return sortField != null && !sortField.trim().isEmpty();
    }

    public boolean isAscending() {
        return sortDirection == Direction.ASC;
    }

    public boolean isDescending() {
        return sortDirection == Direction.DESC;
    }

    public int offset() {
        return pageNumber * pageSize;
    }

    @RequiredArgsConstructor
    public enum Direction {
        ASC("ASC"), DESC("DESC");

        @Getter
        private final String value;
    }
}