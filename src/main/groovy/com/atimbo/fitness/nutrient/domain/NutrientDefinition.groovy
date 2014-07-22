package com.atimbo.fitness.nutrient.domain

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.NamedQueries
import javax.persistence.NamedQuery
import javax.persistence.Table

/**
 * Nutrient definition entity providing a unit of measure
 * by nutrient type description for a {@link Food}.
 */
@Entity
@Table(name = 'nutrient_definition')
@NamedQueries([
@NamedQuery(
        name = 'com.atimbo.fitness.nutrient.domain.NutrientDefinition.findAll',
        query = 'select nd from NutrientDefinition nd'
    )
])
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
