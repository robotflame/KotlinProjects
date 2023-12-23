package com.example.tictactoe.api.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

typealias GameState = List<MutableList<Char>>

@Parcelize
data class Game(val players: MutableList<String>, val gameId: String, var state: GameState) :
    Parcelable
