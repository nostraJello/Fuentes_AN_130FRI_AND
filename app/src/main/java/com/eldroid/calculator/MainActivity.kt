package com.eldroid.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    // Declare TextViews
    private lateinit var inputTV: TextView
    private lateinit var outputTV: TextView

    // Declare Buttons
    private lateinit var equalsBTN: Button
    private lateinit var periodBTN: Button
    private lateinit var zeroBTN: Button
    private lateinit var oneBTN: Button
    private lateinit var twoBTN: Button
    private lateinit var threeBTN: Button
    private lateinit var fourBTN: Button
    private lateinit var fiveBTN: Button
    private lateinit var sixBTN: Button
    private lateinit var sevenBTN: Button
    private lateinit var eightBTN: Button
    private lateinit var nineBTN: Button
    private lateinit var plusBTN: Button
    private lateinit var minusBTN: Button
    private lateinit var timesBTN: Button
    private lateinit var divideBTN: Button
    private lateinit var moduloBTN: Button
    private lateinit var acBTN: Button
    private lateinit var backspaceBTN: Button

    private var inputExpression: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize TextViews
        inputTV = findViewById(R.id.inputTV)
        outputTV = findViewById(R.id.outputTV)

        // Initialize Buttons
        initializeButtons()

        // Set onClick listeners
        setNumberButtonListeners()
        setOperatorButtonListeners()
        setSpecialButtonListeners()
    }

    private fun initializeButtons() {
        equalsBTN = findViewById(R.id.equalsBTN)
        periodBTN = findViewById(R.id.periodBTN)
        zeroBTN = findViewById(R.id.zeroBTN)
        oneBTN = findViewById(R.id.oneBTN)
        twoBTN = findViewById(R.id.twoBTN)
        threeBTN = findViewById(R.id.threeBTN)
        fourBTN = findViewById(R.id.fourBTN)
        fiveBTN = findViewById(R.id.fiveBTN)
        sixBTN = findViewById(R.id.sixBTN)
        sevenBTN = findViewById(R.id.sevenBTN)
        eightBTN = findViewById(R.id.eightBTN)
        nineBTN = findViewById(R.id.nineBTN)
        plusBTN = findViewById(R.id.plusBTN)
        minusBTN = findViewById(R.id.minusBTN)
        timesBTN = findViewById(R.id.timesBTN)
        divideBTN = findViewById(R.id.divideBTN)
        moduloBTN = findViewById(R.id.moduloBTN)
        acBTN = findViewById(R.id.acBTN)
        backspaceBTN = findViewById(R.id.backspaceBTN)
    }

    private fun setNumberButtonListeners() {
        val numberButtons = listOf(
            zeroBTN to "0",
            oneBTN to "1",
            twoBTN to "2",
            threeBTN to "3",
            fourBTN to "4",
            fiveBTN to "5",
            sixBTN to "6",
            sevenBTN to "7",
            eightBTN to "8",
            nineBTN to "9",
            periodBTN to "."
        )

        for ((button, value) in numberButtons) {
            button.setOnClickListener { appendToExpression(value) }
        }
    }

    private fun setOperatorButtonListeners() {
        val operatorButtons = listOf(
            plusBTN to "+",
            minusBTN to "-",
            timesBTN to "×",
            divideBTN to "÷",
            moduloBTN to "%"
        )

        for ((button, value) in operatorButtons) {
            button.setOnClickListener { appendToExpression(value) }
        }
    }

    private fun setSpecialButtonListeners() {
        acBTN.setOnClickListener { clearExpression() }
        backspaceBTN.setOnClickListener { removeLastCharacter() }
        equalsBTN.setOnClickListener { calculateResult() }
    }

    private fun appendToExpression(value: String) {
        inputExpression += value
        inputTV.text = inputExpression
    }

    private fun clearExpression() {
        inputExpression = ""
        inputTV.text = ""
        outputTV.text = ""
    }

    private fun removeLastCharacter() {
        if (inputExpression.isNotEmpty()) {
            inputExpression = inputExpression.dropLast(1)
            inputTV.text = inputExpression
        }
    }

    private fun calculateResult() {
        try {
            // Replace operator symbols with actual operators
            val expression = inputExpression
                .replace("×", "*")
                .replace("÷", "/")
                .replace("%", "/100")

            val result = eval(expression)
            outputTV.text = result.toString()
        } catch (e: Exception) {
            outputTV.text = "Error"
        }
    }

    // Simple evaluator for basic arithmetic expressions
    private fun eval(expression: String): Double {
        return object : Any() {
            var pos = -1
            var ch: Char = ' '

            fun nextChar() {
                pos++
                ch = if (pos < expression.length) expression[pos] else '\u0000'
            }

            fun eat(charToEat: Char): Boolean {
                while (ch == ' ') nextChar()
                if (ch == charToEat) {
                    nextChar()
                    return true
                }
                return false
            }

            fun parse(): Double {
                nextChar()
                val result = parseExpression()
                if (pos < expression.length) throw RuntimeException("Unexpected character: $ch")
                return result
            }

            fun parseExpression(): Double {
                var result = parseTerm()
                while (true) {
                    when {
                        eat('+') -> result += parseTerm()
                        eat('-') -> result -= parseTerm()
                        else -> return result
                    }
                }
            }

            fun parseTerm(): Double {
                var result = parseFactor()
                while (true) {
                    when {
                        eat('*') -> result *= parseFactor()
                        eat('/') -> result /= parseFactor()
                        else -> return result
                    }
                }
            }

            fun parseFactor(): Double {
                if (eat('+')) return parseFactor() // unary plus
                if (eat('-')) return -parseFactor() // unary minus

                var result: Double
                val startPos = pos

                if (eat('(')) {
                    result = parseExpression()
                    eat(')')
                } else if (ch.isDigit() || ch == '.') {
                    while (ch.isDigit() || ch == '.') nextChar()
                    result = expression.substring(startPos, pos).toDouble()
                } else {
                    throw RuntimeException("Unexpected character: $ch")
                }

                return result
            }
        }.parse()
    }
}
