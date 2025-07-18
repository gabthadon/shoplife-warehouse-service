package com.softnet.shoplife.dto.responses;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class LocationResponse {
    private Long id;
    private String country;
    private String state;
    private String lga;
    private String cityOrTown;
    private String contactAddress;
}
