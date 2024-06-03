package com.swd391.bachhoasi.model.dto.response;

import java.util.List;

import org.springframework.data.domain.Page;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaginationResponse <T> {
    private List<T> content;
    private Integer pageNo;
    private Integer pageSize;
    private Long totalElement;
    private Integer totalPage;
    private Boolean isLastPage;
    private Boolean isFirstPage;


    public PaginationResponse(Page<T> page){
        this.content = page.getContent();
        this.pageNo = page.getNumber();
        this.pageSize = page.getSize();
        this.totalElement = page.getTotalElements();
        this.totalPage = page.getTotalPages();
        this.isFirstPage = page.isFirst();
        this.isLastPage = page.isLast();
    }
}
