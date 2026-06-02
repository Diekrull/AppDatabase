package com.appdatabase.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class ProgramDto(
    val id: String? = null,
    val name: String,
    val goal: String,
    val durationWeeks: Int
)