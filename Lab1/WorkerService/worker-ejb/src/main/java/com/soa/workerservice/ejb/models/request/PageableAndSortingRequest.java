package com.soa.workerservice.ejb.models.request;

import com.soa.workerservice.ejb.models.SearchCriteria;
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
