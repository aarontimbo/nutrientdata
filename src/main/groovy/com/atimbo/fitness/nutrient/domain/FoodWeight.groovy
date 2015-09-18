package com.atimbo.fitness.nutrient.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table
import javax.persistence.Transient

/**
 * Food weight providing weight in grams for a
 * given amount of {@link Food}.
 */
@Entity
@Table(name = 'food_weight')
@ToString
@EqualsAndHashCode
class FoodWeight {

    @JsonIgnore
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

    @Transient
    String getFoodId() {
        return food.id
    }

}
