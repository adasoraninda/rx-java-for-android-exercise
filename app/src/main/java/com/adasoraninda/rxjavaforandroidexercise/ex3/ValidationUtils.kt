package com.adasoraninda.rxjavaforandroidexercise.ex3

import io.reactivex.Observable

object ValidationUtils {

    fun checkExpirationDate(candidate: String): Boolean {
        return candidate.matches("\\d\\d/\\d\\d".toRegex())
    }

    fun and(vararg a: Observable<Boolean>): Observable<Boolean> {
        return Observable.combineLatest(a) { arr ->
            var data = arr[0] as Boolean
            arr.drop(1).forEach {
                data = data && (it as Boolean)
            }
            data
        }
    }

    fun equals(
        a: Observable<Int>,
        b: Observable<Int>
    ): Observable<Boolean> {
        return Observable.combineLatest(a, b) { valueA, valueB -> valueA == valueB }
    }

    fun checkCardChecksum(number: String): Boolean {
        val digits = IntArray(number.length)
        for (i in number.indices) {
            digits[i] = Integer.valueOf(number.substring(i, i + 1))
        }
        return checkCardChecksum(digits)
    }

    fun isValidCvc(cardType: CardType, cvc: String): Boolean {
        return cvc.length == cardType.cvcLength
    }

    fun isValidCardType(cardType: CardType): Boolean {
        return cardType != CardType.UNKNOWN
    }

    private fun checkCardChecksum(digits: IntArray): Boolean {
        var sum = 0
        val length = digits.size
        for (i in 0 until length) {

            // Get digits in reverse order
            var digit = digits[length - i - 1]

            // Every 2nd number multiply with 2
            if (i % 2 == 1) {
                digit *= 2
            }
            sum += if (digit > 9) digit - 9 else digit
        }
        return sum % 10 == 0
    }

}