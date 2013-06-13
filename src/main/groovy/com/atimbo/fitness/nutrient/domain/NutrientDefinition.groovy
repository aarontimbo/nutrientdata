package com.atimbo.fitness.nutrient.domain

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

import javax.persistence.*

@Entity
@Table(name = 'nutrient_definition')
@ToString
@EqualsAndHashCode
class NutrientDefinition {

    @Id
    Long id

    @Column(name = 'unit_of_measure')
    String unit

    @Column
    String description

}
