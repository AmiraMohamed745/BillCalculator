package com.example.billcalculator

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.text.NumberFormat

class BillCalculatorViewModel : ViewModel() {

    private val _billAmount = MutableStateFlow("")
    val billAmount = _billAmount.asStateFlow()

    private val _tipPercentage = MutableStateFlow("")
    val tipPercentage = _tipPercentage.asStateFlow()

    private val _numberOfPeople = MutableStateFlow("")
    val numberOfPeople = _numberOfPeople.asStateFlow()

    fun onBillAmountChanged(billAmount: String) {
        _billAmount.value = billAmount
    }

    fun onTipPercentageChanged(tipPercentage: String) {
        _tipPercentage.value = tipPercentage
    }

    fun onNumberOfPeopleChanged(numberOfPeople: String) {
        _numberOfPeople.value = numberOfPeople
    }

    private val _roundUp = MutableStateFlow(false)
    val roundUp = _roundUp.asStateFlow()

    private val _split = MutableStateFlow(false)
    val split = _split.asStateFlow()

    fun onRoundUpChanged(roundUp: Boolean) {
        _roundUp.value = roundUp
    }

    fun onSplitChanged(split: Boolean) {
        _split.value = split
        if (!split) {
            _numberOfPeople.value = ""
        }
    }

    fun calculateBill(
        billAmount: String,
        tipPercentage: String,
        numberOfPeople: String,
        roundUp: Boolean,
        split: Boolean
    ): String {
        val billAmountDouble: Double = billAmount.toDoubleOrNull() ?: 0.0
        val tipPercentageDouble: Double = tipPercentage.toDoubleOrNull() ?: 0.0
        val numberOfPeopleDouble: Int = numberOfPeople.toIntOrNull() ?: 0

        val tip = tipPercentageDouble / 100 * billAmountDouble
        var bill = billAmountDouble + tip
        if (roundUp) {
            bill = kotlin.math.ceil(bill)
        }
        if (split && numberOfPeopleDouble > 0) {
            bill = if (roundUp) {
                kotlin.math.ceil(bill / numberOfPeopleDouble)
            } else {
                bill / numberOfPeopleDouble
            }
        }
        return NumberFormat.getCurrencyInstance().format(bill)
    }
}

