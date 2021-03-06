package com.example.csgs.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DistrictInfo {
    private String district;
    private long numHouses;
    private long numResidents;
    private long numParkingSpaces;
}
