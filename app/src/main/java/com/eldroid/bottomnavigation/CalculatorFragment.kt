package com.eldroid.bottomnavigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment

class CalculatorFragment : Fragment() {
    private lateinit var inputTV: TextView
    private lateinit var outputTV: TextView

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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize TextViews
        inputTV = view.findViewById(R.id.inputTV)
        outputTV = view.findViewById(R.id.outputTV)

        // Initialize Buttons
        initializeButtons(view)

        // Set onClick listeners
        setNumberButtonListeners()
        setOperatorButtonListeners()
        setSpecialButtonListeners()
    }

    private fun initializeButtons(view: View) {
        equalsBTN = view.findViewById(R.id.equalsBTN)
        periodBTN = view.findViewById(R.id.periodBTN)
        zeroBTN = view.findViewById(R.id.zeroBTN)
        oneBTN = view.findViewById(R.id.oneBTN)
        twoBTN = view.findViewById(R.id.twoBTN)
        threeBTN = view.findViewById(R.id.threeBTN)
        fourBTN = view.findViewById(R.id.fourBTN)
        fiveBTN = view.findViewById(R.id.fiveBTN)
        sixBTN = view.findViewById(R.id.sixBTN)
        sevenBTN = view.findViewById(R.id.sevenBTN)
        eightBTN = view.findViewById(R.id.eightBTN)
        nineBTN = view.findViewById(R.id.nineBTN)
        plusBTN = view.findViewById(R.id.plusBTN)
        minusBTN = view.findViewById(R.id.minusBTN)
        timesBTN = view.findViewById(R.id.timesBTN)
        divideBTN = view.findViewById(R.id.divideBTN)
        moduloBTN = view.findViewById(R.id.moduloBTN)
        acBTN = view.findViewById(R.id.acBTN)
        backspaceBTN = view.findViewById(R.id.backspaceBTN)
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
            }  fun parseFactor(): Double {
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
