package org.thej.foodorder.master.dto.template.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class PagedResponse<T> extends ApiResponse<T> {
    private int pageNumber;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean lastPage;
    private List<T> content;

    public PagedResponse(int pageNumber, long totalElements, int totalPages, boolean lastPage, List<T> content) {
        super();
        this.pageNumber = pageNumber;
        this.pageSize = content.size();
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.lastPage = lastPage;
        this.content = content;
    }
}
