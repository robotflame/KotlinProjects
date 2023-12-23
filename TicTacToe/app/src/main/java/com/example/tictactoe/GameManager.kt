package com.example.tictactoe

import android.content.Intent
import android.util.Log
import com.example.tictactoe.api.GameService
import com.example.tictactoe.api.data.Game
import com.example.tictactoe.api.data.GameState

object GameManager {
    val TAG:String = "GameManager"

    var player: String? = null
    var game: Game? = null
    var stateChanged:Boolean = false

    val StartingGameState: GameState = listOf(mutableListOf('0', '0', '0'), mutableListOf('0', '0', '0'), mutableListOf('0', '0', '0'))

    fun createGame(player: String) {
        GameService.createGame(player, StartingGameState) { game: Game?, err: Int? ->
            if (err != null) {
                Log.d(TAG, "Could not create a game")
            } else {
                this.game = game
                Log.d(TAG, "Created game with game id: ${this.game?.gameId}")

                // Create intent
                val intent = Intent(App.context, GameActivity::class.java)
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                // Send game object to intent
                intent.putExtra("GAME_OBJECT", game)
                App.context.startActivity(intent)
            }
        }
    }

    fun joinGame(player: String, gameId: String) {
        GameService.joinGame(player, gameId) { game: Game?, err: Int? ->
            if (err != null) {
                Log.d(TAG, "Could not join game")
            } else {
                this.game = game
                Log.d(TAG, "Joined game with game id: ${this.game?.gameId}")
                // Create intent
                val intent = Intent(App.context, GameActivity::class.java)
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                // Send game object to intent
                intent.putExtra("GAME_OBJECT", game)
                App.context.startActivity(intent)
            }
        }
    }

    fun pollGame():Boolean {
        var gameId = this.game?.gameId


        if (gameId == null) {
            Log.d(TAG, "game id is null, could not poll game.")
        } else {
            // gameId exists, poll game
            GameService.pollGame(gameId) { game: Game?, err: Int? ->
                if (err != null) {
                    Log.d( TAG, "Could not poll game")
                } else {
                    Log.d(TAG, this.game.toString())
                    this.stateChanged = false

                    if (this.game?.state != game?.state) {
                        // game state has changed, reinitialize game.
                        this.game = game
                        Log.d(TAG, "Polled game, new state: ${this.game?.state}")
                        this.stateChanged = true
                        Log.d(TAG, "statechanged? $stateChanged")
                    }
                }
            }
        }
        return stateChanged
    }

    fun updateGame(state: GameState) {
        var gameId = this.game?.gameId
        //var gameId = "tg4et"

        if (gameId == null) {
            Log.d(TAG, "game id is null, could not update game.")
        } else {
            // gameId exists, poll game
            GameService.updateGame(gameId, state) { game: Game?, err: Int? ->
                if (err != null) {
                    Log.d( TAG, "Could not update game")
                } else {
                    if (this.game?.state != game?.state) {
                        this.game?.state = game!!.state
                        Log.d(TAG, "Updated game, updated state: ${this.game}")
                    }
                }
            }
        }
    }

}