package com.android.quiztrivia.util.model

import androidx.annotation.DrawableRes
import com.android.quiztrivia.R
import kotlinx.serialization.Serializable

@Serializable
data class ResultArg(
    val item: Item,
    val correct: Int = 0,
    val incorrect: Int = 0,
    @DrawableRes val nightGif: Int = R.drawable.amazing_night,
    @DrawableRes val dayGif: Int = R.drawable.amazing_day
)
