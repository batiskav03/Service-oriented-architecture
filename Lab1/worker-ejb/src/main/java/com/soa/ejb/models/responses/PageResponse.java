package com.soa.ejb.models.responses;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder(builderMethodName = "listBuilder")
public class PageResponse<T> extends MessageResponse{
    private Page<T> page;

    public PageResponse(Integer code, Date date, String msg, Page<T> page) {
        super(code, date, msg);
        this.page = page;
    }
}
