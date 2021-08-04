package com.easycruise.recipes_compose.domain.util

interface DomainMapper <T, DomainModel> {

    fun mapToDomainModel(entity: T): DomainModel

    fun mapFromDomainModel(domainModel: DomainModel): T
}