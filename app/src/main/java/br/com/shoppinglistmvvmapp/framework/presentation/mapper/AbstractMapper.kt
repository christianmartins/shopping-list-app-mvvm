package br.com.shoppinglistmvvmapp.framework.presentation.mapper

abstract class AbstractMapper<Entity, Presentation>{

    abstract fun convert(entity: Entity): Presentation

}