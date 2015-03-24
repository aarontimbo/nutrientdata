package com.atimbo.fitness.nutrient.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.OneToOne
import javax.persistence.Table
import javax.persistence.Transient

/**
 * Food nutrient entity providing nutrient data for a {@link Food}
 * and {@link NutrientDefinition}.
 */
@Entity
@Table(name = 'food_nutrient')
@ToString
@EqualsAndHashCode
class FoodNutrient implements Serializable {

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = 'food_id')
    Food food

    @Id
    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = 'nutrient_id')
    NutrientDefinition definition

    @Column(name = 'amount_per_100_grams', nullable = false)
    Float amountPer100Grams

    @Transient
    String getFoodId() {
        return food.id
    }

}
