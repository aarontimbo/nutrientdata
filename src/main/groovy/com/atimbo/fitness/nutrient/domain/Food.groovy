package com.atimbo.fitness.nutrient.domain

import groovy.transform.ToString

import javax.persistence.*

@Entity
@Table(name = 'food_description')
@ToString
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id

    @Column(name = 'long_description', nullable = false)
    String longDescription

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = 'food_group_id')
    FoodGroup foodGroup

}
