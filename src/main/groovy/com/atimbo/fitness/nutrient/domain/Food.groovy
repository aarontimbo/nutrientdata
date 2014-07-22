package com.atimbo.fitness.nutrient.domain

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

/**
 * Food entity providing data about a food.
 */
@Entity
@Table(name = 'food_description')
@ToString
@EqualsAndHashCode
class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = 'food_group_id')
    FoodGroup foodGroup

    @Column(name = 'short_description', nullable = false)
    String shortDescription

    @Column(name = 'long_description', nullable = false)
    String longDescription

}
