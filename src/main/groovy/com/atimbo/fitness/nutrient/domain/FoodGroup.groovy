package com.atimbo.fitness.nutrient.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.OneToMany
import javax.persistence.Table

/**
 * Food group entity containing a set of {@link Food}s.
 */
@Entity
@Table(name = 'food_group')
@ToString(excludes = ['foods'])
@EqualsAndHashCode()
class FoodGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id

    @Column(name = 'description', nullable = false)
    String description

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = 'foodGroup')
    @JsonIgnore
    Collection<Food> foods = []

}
