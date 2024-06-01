package com.glorfindel.tehanu.infrastructure

import android.util.Base64
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

class Cryptography {
    private fun cipher(
        opmode: Int,
        secretKey: String
    ): Cipher {
        if (secretKey.length != 32) throw RuntimeException("SecretKey length is not 32 chars")

        val c = Cipher.getInstance("AES/CBC/PKCS7Padding")
        val sk = SecretKeySpec(secretKey.toByteArray(Charsets.UTF_8), "AES")
        val iv = IvParameterSpec(secretKey.substring(0, 16).toByteArray(Charsets.UTF_8))
        c.init(opmode, sk, iv)
        return c
    }

    fun encrypt(
        plainText: String,
        secretKey: String
    ): String {
        val encrypted = cipher(Cipher.ENCRYPT_MODE, secretKey).doFinal(plainText.toByteArray(Charsets.UTF_8))
        return String(Base64.encode(encrypted, Base64.NO_WRAP))
    }

    fun decrypt(
        cipherText: String,
        secretKey: String
    ): String {
        val byteStr = Base64.decode(cipherText.toByteArray(Charsets.UTF_8), Base64.NO_WRAP)
        return String(cipher(Cipher.DECRYPT_MODE, secretKey).doFinal(byteStr))
    }
}
