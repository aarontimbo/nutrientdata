package com.atimbo.fitness.nutrient.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

import javax.persistence.*

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
