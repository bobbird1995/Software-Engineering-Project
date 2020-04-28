package com.example.csgs.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "of_grid")
public class OfGridEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    String district;                //所属区

    @Column(nullable = false)
    String community;               //所属小区

    @Column(nullable = false)       //小区住在数量
    Long numHouses;

    @Column(nullable = false)       //小区居民数量
    Long numResidents;

    @Column(nullable = false)       //小区停车位数量
    Long numParkingSpaces;
}