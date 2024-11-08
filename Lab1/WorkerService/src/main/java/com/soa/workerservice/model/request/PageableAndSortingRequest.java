package com.soa.workerservice.model.request;

import com.soa.workerservice.model.SearchCriteria;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PageableAndSortingRequest {
    private String sorting;
    private SearchCriteria filter;
    private Integer page;
    private Integer pageSize;
}
