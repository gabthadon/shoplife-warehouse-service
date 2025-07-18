package com.softnet.shoplife.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class WarehouseLocation {
    private String country;
    private String state;
    private String lga;
    private String cityOrTown;
    private String contactAddress;
}
