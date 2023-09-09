package com.android.quiztrivia.util

import com.android.quiztrivia.R
import com.android.quiztrivia.util.model.Item
import com.android.quiztrivia.util.model.ResultArg

val AVAILABLE_QUIZ: List<ResultArg> = listOf(
    ResultArg(item = Item("Entertainment Books",10, R.drawable.books)),
    ResultArg(item = Item("General Knowledge",9, R.drawable.knowledge)),
    ResultArg(item = Item("Sports",21, R.drawable.sport)),
    ResultArg(item = Item("Animals",27, R.drawable.animal)),
    ResultArg(item = Item("Nature",17, R.drawable.nature)),
    ResultArg(item = Item("Mythology",20, R.drawable.mythology)),
    ResultArg(item = Item("History",23, R.drawable.history)),
    ResultArg(item = Item("Science Gadgets",30, R.drawable.gadgets))
//    Item("Mathematics",19, R.drawable.mathematics),
//    Item("Geography",22, R.drawable.geography)
)