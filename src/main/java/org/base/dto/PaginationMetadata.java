package org.base.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PaginationMetadata {
    private int pageNumber;
    private int pageSize;
    private int totalPages;
    private long totalItems;
}
