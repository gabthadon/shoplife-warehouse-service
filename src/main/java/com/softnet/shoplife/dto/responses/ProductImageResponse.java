package com.softnet.shoplife.dto.responses;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class ProductImageResponse {
    private Long id;
    private String imageUrl;
}
