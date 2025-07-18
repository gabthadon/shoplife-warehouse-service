package com.softnet.shoplife.dto.request;

import com.softnet.shoplife.entity.BaseEntityWithoutTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LocationRequest{
    private String country;
    private String state;
    private String lga;
    private String cityOrTown;
    private String contactAddress;
}
