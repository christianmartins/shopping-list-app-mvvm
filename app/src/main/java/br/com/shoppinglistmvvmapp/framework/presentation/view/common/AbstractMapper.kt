package br.com.shoppinglistmvvmapp.framework.presentation.view.common

abstract class AbstractMapper<Entity, Presentation>{

    abstract fun convert(entity: Entity): Presentation

}