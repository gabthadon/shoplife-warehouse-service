package com.softnet.shoplife.dto.responses;

import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class InFlowResponse {
    private Long totalCount;
    private String createdDate;
    private Long warehouseInventoryId;

}
