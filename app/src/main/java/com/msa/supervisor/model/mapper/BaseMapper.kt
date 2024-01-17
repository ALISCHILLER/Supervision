package com.msa.supervisor.model.mapper

/**
 * Created by Ali Soleimani  on 10/03/2020.
 */

interface BaseMapper<E, D> {

    fun transformToDomain(type: E): D

    fun transformToDto(type: D): E
}
