package com.example.tictactoe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Button
import com.example.tictactoe.api.data.GameState
import com.example.tictactoe.databinding.ActivityGameBinding


class GameActivity : AppCompatActivity() {
    lateinit var binding: ActivityGameBinding
    val TAG:String = "GameActivity"

    var myTurn:Boolean = false

    //  Player with 'X' mark makes first move.
    var myMark: Char? = null
    var opponentMark: Char? = null

    var game = GameManager.game

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupGame()

        val handler = Handler(Looper.getMainLooper())
        // Pool game every 5 second.
        handler.post(object : Runnable {
            override fun run() {
                // if state has changed after polling the game
                if(GameManager.pollGame()) {
                    game = GameManager.game
                    Log.d(TAG, "polled game: $game")
                    setUpBoard()
                    myTurn = true
                    checkIfWinner()
                }

                handler.postDelayed(this, 5000)
            }
        })
    }

    fun setupGame() {
        binding.gameId.text = "Game ID: " + game?.gameId
        initializePlayers()
        setUpBoard()
    }


    fun setUpBoard() {
        initialzeCell(0, 0)
        initialzeCell(0, 1)
        initialzeCell(0, 2)
        initialzeCell(1, 0)
        initialzeCell(1, 1)
        initialzeCell(1, 2)
        initialzeCell(2, 0)
        initialzeCell(2, 1)
        initialzeCell(2, 2)
    }

    fun initialzeCell(row:Int, column:Int) {
        var btn:Button = findViewById(getResources().getIdentifier("_a$row$column", "id", packageName))

        if(game!!.state[row][column] != '0')
            btn.text = game!!.state[row][column].toString()

        btn.setOnClickListener {
            if(myTurn) {
                btn.text = myMark.toString()
                game!!.state[row][column] = myMark!!
                myTurn = false
                GameManager.updateGame(game!!.state)
                checkIfWinner()
            }
        }
    }

    fun initializePlayers() {
        if(game?.players?.size == 1) {
            myMark = 'X'
            opponentMark = 'O'
            myTurn = true
            binding.myPlayer.text = "You: " + myMark.toString()
            binding.opponent.text = "Opponent: " + opponentMark.toString()
        } else {
            myMark = 'O'
            opponentMark = 'X'
            binding.myPlayer.text = "You: " + myMark.toString()
            binding.opponent.text = "Opponent: " + opponentMark.toString()
        }
    }

    fun checkIfWinner() {
        var gameState: GameState = game!!.state

        // Check  horizontal wins for X
        if(gameState[0][0] == 'X' && gameState[0][1] == 'X' && gameState[0][2] == 'X') {
            myTurn = false
            binding.winner.text = "X WON"
        }
        else if (gameState[1][0] == 'X' && gameState[1][1] == 'X' && gameState[1][2] == 'X') {
            binding.winner.text = "X WON"
        }
        else if (gameState[2][0] == 'X' && gameState[2][1] == 'X' && gameState[2][2] == 'X') {
            myTurn = false
            binding.winner.text = "X WON"
        }

        // Check  vertical wins for X
        else if (gameState[0][0] == 'X' && gameState[1][0] == 'X' && gameState[2][0] == 'X') {
            myTurn = false
            binding.winner.text = "X WON"
        }
        else if (gameState[0][1] == 'X' && gameState[1][1] == 'X' && gameState[2][1] == 'X') {
            myTurn = false
            binding.winner.text = "X WON"
        }
        else if (gameState[0][2] == 'X' && gameState[1][2] == 'X' && gameState[2][2] == 'X') {
            myTurn = false
            binding.winner.text = "X WON"
        }

        // Check  diagonal wins for X
        else if(gameState[0][0] == 'X' && gameState[1][1] == 'X' && gameState[2][2] == 'X') {
            myTurn = false
            binding.winner.text = "X WON"
        }
        else if (gameState[2][0] == 'X' && gameState[1][1] == 'X' && gameState[0][2] == 'X') {
            myTurn = false
            binding.winner.text = "X WON"
        }

        // Check  horizontal wins for O
        else if(gameState[0][0] == 'O' && gameState[0][1] == 'O' && gameState[0][2] == 'O') {
            myTurn = false
            binding.winner.text = "O WON"
        }
        else if (gameState[1][0] == 'O' && gameState[1][1] == 'O' && gameState[1][2] == 'O') {
            myTurn = false
            binding.winner.text = "O WON"
        }
        else if (gameState[2][0] == 'O' && gameState[2][1] == 'O' && gameState[2][2] == 'O') {
            myTurn = false
            binding.winner.text = "O WON"
        }

        // Check  vertical wins for O
        else if(gameState[0][0] == 'O' && gameState[1][0] == 'O' && gameState[2][0] == 'O') {
            myTurn = false
            binding.winner.text = "O WON"
        }
        else if (gameState[0][1] == 'O' && gameState[1][1] == 'O' && gameState[2][1] == 'O') {
            myTurn = false
            binding.winner.text = "O WON"
        }
        else if (gameState[0][2] == 'O' && gameState[1][2] == 'O' && gameState[2][2] == 'O') {
            myTurn = false
            binding.winner.text = "O WON"
        }

        // Check  diagonal wins for O
        else if (gameState[0][0] == 'O' && gameState[1][1] == 'O' && gameState[2][2] == 'O') {
            myTurn = false
            binding.winner.text = "O WON"
        }
        else if (gameState[2][0] == 'O' && gameState[1][1] == 'X' && gameState[0][2] == 'O') {
            myTurn = false
            binding.winner.text = "O WON"
        }


        // Check for draw
        else if (gameState[0][0] != '0' && gameState[0][1] != '0' && gameState[0][2] != '0'
            && gameState[1][0] != '0' && gameState[1][1] != '0' && gameState[1][2] != '0'
            && gameState[2][0] != '0' && gameState[2][1] != '0' && gameState[2][2] != '0')
        {
            myTurn = false
            binding.winner.text = "DRAW"
        }

    }

}