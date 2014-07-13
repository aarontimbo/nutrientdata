package com.atimbo.fitness.nutrient.domain

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.NamedQueries
import javax.persistence.NamedQuery
import javax.persistence.OneToOne
import javax.persistence.Table

/**
 * Food nutrient entity providing nutrient data for a {@link Food}
 * and {@link NutrientDefinition}.
 */
@Entity
@Table(name = 'food_nutrient')
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
