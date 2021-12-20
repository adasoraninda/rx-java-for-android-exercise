package com.adasoraninda.rxjavaforandroidexercise.ex3

import java.util.regex.Pattern

enum class CardType(val cvcLength: Int) {
    UNKNOWN(-1),
    VISA(3),
    MASTER_CARD(3),
    AMERICAN_EXPRESS(4);

    companion object {
        fun fromString(number: String): CardType {
            return when {
                regVisa.matcher(number).matches() -> VISA
                regMasterCard.matcher(number).matches() -> MASTER_CARD
                regAmericanExpress.matcher(number).matches() -> AMERICAN_EXPRESS
                else -> UNKNOWN
            }
        }

        private val regVisa: Pattern = Pattern.compile("^4[0-9]{12}(?:[0-9]{3})?\$")
        private val regMasterCard: Pattern = Pattern.compile("^5[1-5][0-9]{14}\$")
        private val regAmericanExpress: Pattern = Pattern.compile("^3[47][0-9]{13}\$")
    }

}