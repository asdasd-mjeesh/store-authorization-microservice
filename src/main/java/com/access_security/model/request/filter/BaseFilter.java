package com.access_security.model.request.filter;

import lombok.Data;

@Data
public abstract class BaseFilter {
    private int limit;
    private int offset;
    private String sortField;
    private SortingOrder sortingOrder;

    protected BaseFilter() {  }

    protected BaseFilter(int limit, int offset, String sortField, SortingOrder sortingOrder) {
        this.limit = limit;
        this.offset = offset;
        this.sortField = sortField;
        this.sortingOrder = sortingOrder;
    }
}
