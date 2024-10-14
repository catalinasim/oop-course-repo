package oop.practice

import com.fasterxml.jackson.databind.JsonNode

data class Universe(
    val name: String,
    val individuals: MutableList<JsonNode>
)