package com.android.quiztrivia.util.model

import androidx.annotation.DrawableRes
import kotlinx.serialization.Serializable

@Serializable
data class Item(
    val title: String,
    val apiNumber: Int,
    @DrawableRes val image: Int
)
