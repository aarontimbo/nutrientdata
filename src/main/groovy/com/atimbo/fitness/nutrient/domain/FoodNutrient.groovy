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
    ),
    @NamedQuery(
            name = 'com.atimbo.fitness.nutrient.domain.FoodNutrient.findByFoodAndDefinition',
            query = 'select fn from FoodNutrient fn where fn.food = :food and fn.definition = :definition'
    )
])
@ToString
@EqualsAndHashCode
class FoodNutrient implements Serializable {

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = 'food_id')
    Food food

    @Id
    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = 'nutrient_id')
    NutrientDefinition definition

    @Column(name = 'amount_per_100_grams', nullable = false)
    Float amountPer100Grams

}
