package org.thej.foodorder.master.dto.template.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

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
