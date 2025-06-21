package org.thej.foodorder.webcommons.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
@AllArgsConstructor
public class PagedRequest extends BaseRequest {
    private int pageNumber;
    private int pageSize;
    private String sortBy;
    private String sortOrder;
    private Map<String, Object> filters;
}
