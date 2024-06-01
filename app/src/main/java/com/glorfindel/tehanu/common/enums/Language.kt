package com.glorfindel.tehanu.common.enums

import java.util.Locale

enum class Language(private val language: String, private val country: String) {
    TURKISH("tr", "TR"),
    UK("en", "GB"),
    US("en", "US"),
    AUSTRALIA("en", "AU"),
    IRELAND("en", "IE"),
    CANADA("en", "CA"),
    CANADA_FRENCH("fr", "CA"),
    FRANCE("fr", "FR"),
    GERMANY("de", "DE"),
    AUSTRIA("de", "AT"),
    ITALY("it", "IT"),
    JAPAN("ja", "JP"),
    KOREA("ko", "KR"),
    SIMPLIFIED_CHINESE("zh", "CN"),
    TRADITIONAL_CHINESE("zh", "TW"),
    TAIWAN("zh", "TW"),
    ARABIC_UAE("ar", "AE"),
    ARABIC_BAHRAIN("ar", "BH"),
    ARABIC_ALGERIA("ar", "DZ"),
    ARABIC_EGYPT("ar", "EG"),
    ARABIC_IRAQ("ar", "IQ"),
    ARABIC_JORDAN("ar", "JO"),
    ARABIC_KUWAIT("ar", "KW"),
    ARABIC_LEBANON("ar", "LB"),
    ARABIC_LIBYA("ar", "LY"),
    ARABIC_MOROCCO("ar", "MA"),
    ARABIC_OMAN("ar", "OM"),
    ARABIC_SAUDI_ARABIA("ar", "SA"),
    ARABIC_SYRIA("ar", "SY"),
    ARABIC_TUNISIA("ar", "TN"),
    ARABIC_YEMEN("ar", "YE"),
    SOUTH_AFRICA("af", "ZA"),
    AZERBAIJAN("az", "AZ"),
    CATALAN("ca", "ES"),
    CZECH("cs", "CZ"),
    WELSH("cy", "GB"),
    DANISH("da", "DK"),
    GREEK("el", "GR"),
    ARGENTINA("es", "AR"),
    COLOMBIA("es", "CO"),
    SPAIN("es", "ES"),
    MEXICO("es", "MX"),
    URUGUAY("es", "UY"),
    IRAN("fa", "IR"),
    FINNISH("fi", "FI"),
    INDIA("hi", "IN"),
    BELGIUM("nl", "BE"),
    NETHERLANDS("nl", "NL"),
    POLISH("pl", "PL"),
    PORTUGAL("pt", "PT"),
    BRAZIL("pt", "BR"),
    RUSSIA("ru", "RU"),
    SWEDEN("sv", "SE"),
    UKRAINE("uk", "UA"),
    UNDETERMINED_LANGUAGE("", "");

    fun getLocale() = Locale(language, country)

    fun getLanguage() = language

    fun getCountry() = country

    fun getLocaleString() = "$language-$country"

    companion object {
        fun getLangFromLocaleString(localeString: String) = entries.find { it.getLocaleString() == localeString } ?: UNDETERMINED_LANGUAGE
    }
}
