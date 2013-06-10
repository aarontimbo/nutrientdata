package com.atimbo.fitness.nutrient.domain

import com.fasterxml.jackson.annotation.JsonIdentityReference
import com.fasterxml.jackson.annotation.JsonIgnore
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

import javax.persistence.*

@Entity
@Table(name = 'food_description')
@ToString(excludes = ['nutrients'])
@EqualsAndHashCode(excludes = ['nutrients'])
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
