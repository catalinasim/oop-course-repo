package oop.practice

data class Entity(
    val id: Int,
    val isHumanoid: Boolean? = null,
    val planet: Planet? = null,
    val age: Int? = null,
    val traits: List<Trait>? = null,
)