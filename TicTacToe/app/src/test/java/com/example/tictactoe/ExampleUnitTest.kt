package com.example.tictactoe

import com.example.tictactoe.api.GameService
import com.example.tictactoe.api.data.Game
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    var gameState: Game? = null
    val firstPlayer:String = "Player1"
    val secondPlayer:String = "Player2"
    val initState = listOf(mutableListOf('0', '0', '0'), mutableListOf('0', '0', '0'), mutableListOf('0', '0', '0'))

    @Test
    fun createGame(){
        GameService.createGame(firstPlayer,initState ){ state:Game?, err:Int? ->
            gameState = state
            assertNotNull(state)
            assertNotNull(state?.gameId)
            assertEquals(firstPlayer, state?.players?.get(0))
        }
    }


    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
}