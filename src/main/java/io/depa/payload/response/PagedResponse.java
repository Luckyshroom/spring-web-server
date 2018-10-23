package io.depa.payload.response;

import java.util.List;

public class PagedResponse<T> {

    private List<T> content;
    private int page;
    private int size;
    private long totalElements;
    private long totalPages;
    private boolean isLast;

    public PagedResponse() {}

    public PagedResponse(List<T> content, int page, int size, long totalElements, long totalPages, boolean isLast) {
        this.content = content;
        this.page = page;
        this.size = size;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.isLast = isLast;
    }

    public List<T> getContent() {
        return content;
    }
    public int getPage() {
        return page;
    }
    public int getSize() {
        return size;
    }
    public long getTotalElements() {
        return totalElements;
    }
    public long getTotalPages() {
        return totalPages;
    }
    public boolean getIsLast() {
        return isLast;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }
    public void setPage(int page) {
        this.page = page;
    }
    public void setSize(int size) {
        this.size = size;
    }
    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }
    public void setTotalPages(long totalPages) {
        this.totalPages = totalPages;
    }
    public void setIsLast(boolean isLast) {
        this.isLast = isLast;
    }
}
