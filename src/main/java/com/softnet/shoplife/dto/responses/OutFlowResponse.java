package com.softnet.shoplife.dto.responses;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class OutFlowResponse {
    private Long totalCount;
    private LocalDateTime createdDate;
    private Long warehouseInventoryId;
}
