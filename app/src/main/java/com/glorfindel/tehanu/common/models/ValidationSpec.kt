package com.glorfindel.tehanu.common.models

sealed class ValidationSpec {
    sealed class Int : ValidationSpec() {
        data class Null(val message: kotlin.String? = null) : Int()

        data class LessThan(val value: kotlin.Int, val message: kotlin.String? = null) : Int()

        data class GreaterThan(val value: kotlin.Int, val message: kotlin.String? = null) : Int()

        data class Odd(val message: kotlin.String? = null) : Int()

        data class Even(val message: kotlin.String? = null) : Int()
    }

    sealed class Long : ValidationSpec() {
        data class Null(val message: kotlin.String? = null) : Long()

        data class LessThan(val value: kotlin.Long, val message: kotlin.String? = null) : Long()

        data class GreaterThan(val value: kotlin.Long, val message: kotlin.String? = null) : Long()

        data class Odd(val message: kotlin.String? = null) : Long()

        data class Even(val message: kotlin.String? = null) : Long()
    }

    sealed class Float : ValidationSpec() {
        data class Null(val message: kotlin.String? = null) : Float()

        data class LessThan(val value: kotlin.Float, val message: kotlin.String? = null) : Float()

        data class GreaterThan(val value: kotlin.Float, val message: kotlin.String? = null) : Float()
    }

    sealed class Double : ValidationSpec() {
        data class Null(val message: kotlin.String? = null) : Double()

        data class LessThan(val value: kotlin.Double, val message: kotlin.String? = null) : Double()

        data class GreaterThan(val value: kotlin.Double, val message: kotlin.String? = null) : Double()
    }

    sealed class Boolean : ValidationSpec() {
        data class Null(val message: kotlin.String? = null) : Boolean()

        data class False(val value: kotlin.Boolean, val message: kotlin.String? = null) : Boolean()

        data class True(val value: kotlin.Boolean, val message: kotlin.String? = null) : Boolean()
    }

    sealed class String : ValidationSpec() {
        data class Null(val message: kotlin.String? = null) : String()

        data class Empty(val message: kotlin.String? = null) : String()

        data class LengthLessThan(val value: kotlin.Int, val message: kotlin.String? = null) : String()

        data class LengthGreaterThan(val value: kotlin.Int, val message: kotlin.String? = null) : String()

        data class WrongEmail(val message: kotlin.String? = null) : String()

        data class WrongGsm(val message: kotlin.String? = null) : String()
    }
}
