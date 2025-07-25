package dev.ngb.empdex.shared.core.base;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

public record Page<T>(Collection<T> content, int pageNumber, int pageSize, long totalElements) {
    public static <T> Page<T> of(Collection<T> content, int pageNumber, int pageSize, long totalElements) {
        Objects.requireNonNull(content, "content must not be null");
        if (pageNumber < 0 || pageSize <= 0 || totalElements < 0) {
            throw new IllegalArgumentException("Invalid pagination parameters");
        }
        return new Page<>(content, pageNumber, pageSize, totalElements);
    }

    public static <T> Page<T> empty() {
        return Page.of(Collections.emptyList(), 0, Integer.MAX_VALUE, 0L);
    }

    public static <T> Page<T> singlePage(Collection<T> content) {
        return Page.of(content, 0, content.size(), content.size());
    }

    public static <T> Page<T> singlePage(Collection<T> content, int pageSize) {
        return Page.of(content, 0, pageSize, content.size());
    }

    public int getTotalPages() {
        return (int) Math.ceil((double) totalElements / pageSize);
    }

    public boolean getHasNext() {
        return pageNumber < getTotalPages() - 1;
    }

    public boolean getHasPrevious() {
        return pageNumber > 0;
    }

    public long getNumberOfElements() {
        return content.size();
    }
}