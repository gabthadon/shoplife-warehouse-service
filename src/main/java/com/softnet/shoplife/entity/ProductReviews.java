package com.softnet.shoplife.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@NamedEntityGraph(name = "ProductReviews.productOnly", attributeNodes = {})
@Builder
@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductReviews {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;
    private String username;
    private String productName;
    private String productPrimaryImage;

    private String comment;


    @ManyToOne
    private ProcessedProducts processedProducts;
}
