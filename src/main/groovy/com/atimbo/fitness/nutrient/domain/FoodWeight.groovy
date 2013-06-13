package com.atimbo.fitness.nutrient.domain

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

import javax.persistence.*

@Entity
@Table(name = 'food_weight')
@NamedQueries([
@NamedQuery(
        name = 'com.atimbo.fitness.nutrient.domain.FoodWeight.findAllByFood',
        query = 'select fw from FoodWeight fw where fw.food = :food'
)
])
@ToString
@EqualsAndHashCode
class FoodWeight {

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = 'food_id')
    Food food

    @Id
    Long sequence

    @Column(nullable = false)
    Float amount

    @Column(name = 'gram_weight', nullable = false)
    Float gramWeight

    @Column(nullable = false)
    String description

}
