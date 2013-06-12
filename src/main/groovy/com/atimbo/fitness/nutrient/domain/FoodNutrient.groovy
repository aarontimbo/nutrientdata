package com.atimbo.fitness.nutrient.domain

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

import javax.persistence.*

@Entity
@Table(name = 'food_nutrient')
@NamedQueries([
    @NamedQuery(
            name = 'com.atimbo.fitness.nutrient.domain.FoodNutrient.findAllByFood',
            query = 'select fn from FoodNutrient fn where fn.food = :food'
    )
])
@ToString
@EqualsAndHashCode
class FoodNutrient {

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = 'food_id')
    Food food

    @Id
    @Column(name = 'nutrient_id', nullable = false)
    Long nutrientId

    @Column(name = 'amount_per_100_grams', nullable = false)
    Float amountPer100Grams

}
