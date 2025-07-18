package com.softnet.shoplife.dto.responses;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class InFlowOutFlowResponse {
    private List<InFlowResponse> inFlowCount;
    private List<OutFlowResponse> outFlowCount;
}
