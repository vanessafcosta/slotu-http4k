package com.example.models

import org.http4k.template.ViewModel

data class HandlebarsViewModel(
    val firstName: String,
    val lastName: String,
) : ViewModel
