package com.atimbo.fitness.nutrient.domain

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

import javax.persistence.*

@Entity
@Table(name = 'food_description')
@NamedQueries([
@NamedQuery(
        name = 'com.atimbo.fitness.nutrient.domain.Food.findAll',
        query = 'select f from Food f'
),
@NamedQuery(
        name = 'com.atimbo.fitness.nutrient.domain.Food.findAllByDescription',
        query = 'select f from Food f where f.longDescription like :description'
)
])
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
