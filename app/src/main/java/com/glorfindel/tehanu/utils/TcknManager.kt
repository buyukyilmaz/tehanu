package com.glorfindel.tehanu.utils

object TcknManager {
    fun isValid(tckn: String): Boolean {
        if (tckn.length != 11) return false

        val chars = tckn.toCharArray()
        val list = IntArray(11)
        for (i in 0 until 11) {
            list[i] = chars[i] - '0'
        }

        if (list[0] == 0) return false
        val odds = (list[0] + list[2] + list[4] + list[6] + list[8]) * 7
        val evens = list[1] + list[3] + list[5] + list[7]

        if ((odds - evens) % 10 != list[9]) return false

        val firstTenDigits = list[0] + list[1] + list[2] + list[3] + list[4] + list[5] + list[6] + list[7] + list[8] + list[9]

        return (firstTenDigits % 10) == list[10]
    }

    fun format(tckn: String) = Regexes.nonNumeric.replace(tckn, "").take(11)
}
