package com.glorfindel.tehanu.infrastructure

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import java.io.IOException
import java.nio.charset.StandardCharsets
import java.security.InvalidAlgorithmParameterException
import java.security.InvalidKeyException
import java.security.KeyStore
import java.security.KeyStoreException
import java.security.NoSuchAlgorithmException
import java.security.NoSuchProviderException
import java.security.UnrecoverableEntryException
import java.security.cert.CertificateException
import javax.crypto.BadPaddingException
import javax.crypto.Cipher
import javax.crypto.IllegalBlockSizeException
import javax.crypto.KeyGenerator
import javax.crypto.NoSuchPaddingException
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec

class AndroidKeyStore {
    private val transformation = "${KeyProperties.KEY_ALGORITHM_AES}/${KeyProperties.BLOCK_MODE_CBC}/${KeyProperties.ENCRYPTION_PADDING_PKCS7}"
    private val androidKeyStore = "AndroidKeyStore"
    private val separator = "------,"
    private val alias = "alias"

    private var keyStore: KeyStore? = null
    private var secretKey: SecretKey? = null

    init {
        initKeystore()
        loadOrGenerateKey()
    }

    @Throws(
        NoSuchProviderException::class,
        NoSuchAlgorithmException::class,
        InvalidAlgorithmParameterException::class
    )
    private fun loadOrGenerateKey() {
        getKey()
        if (secretKey == null) generateKey()
    }

    @Throws(
        KeyStoreException::class,
        CertificateException::class,
        NoSuchAlgorithmException::class,
        IOException::class
    )
    private fun initKeystore() {
        keyStore =
            KeyStore.getInstance(androidKeyStore).apply {
                load(null)
            }
    }

    private fun getKey() {
        try {
            val secretKeyEntry = keyStore?.getEntry(alias, null)
            if (secretKeyEntry is KeyStore.SecretKeyEntry) {
                secretKey = secretKeyEntry.secretKey
            }
        } catch (e: KeyStoreException) {
            e.printStackTrace()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        } catch (e: UnrecoverableEntryException) {
            e.printStackTrace()
        }
    }

    @Throws(
        NoSuchProviderException::class,
        NoSuchAlgorithmException::class,
        InvalidAlgorithmParameterException::class
    )
    private fun generateKey() {
        val keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, androidKeyStore)
        val keyGenParameterSpec =
            KeyGenParameterSpec.Builder(alias, KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT)
                .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
                .build()
        keyGenerator.init(keyGenParameterSpec)
        secretKey = keyGenerator.generateKey()
    }

    @Throws(
        NoSuchPaddingException::class,
        NoSuchAlgorithmException::class,
        InvalidKeyException::class,
        BadPaddingException::class,
        IllegalBlockSizeException::class
    )
    fun encrypt(toEncrypt: String?): String {
        if (toEncrypt == null) return ""
        val cipher: Cipher = Cipher.getInstance(transformation)
        cipher.init(Cipher.ENCRYPT_MODE, secretKey)
        val iv: String = Base64.encodeToString(cipher.iv, Base64.DEFAULT)
        val encrypted: String =
            Base64.encodeToString(
                cipher.doFinal(toEncrypt.toByteArray(StandardCharsets.UTF_8)),
                Base64.DEFAULT
            )
        return encrypted + separator + iv
    }

    @Throws(
        NoSuchPaddingException::class,
        NoSuchAlgorithmException::class,
        InvalidAlgorithmParameterException::class,
        InvalidKeyException::class,
        BadPaddingException::class,
        IllegalBlockSizeException::class
    )
    fun decrypt(toDecrypt: String?): String {
        if (toDecrypt == null) return ""

        val parts = toDecrypt.split(separator).toTypedArray()
        if (parts.size != 2) throw AssertionError("String to decrypt must be of the form: 'BASE64_DATA" + separator + "BASE64_IV'")
        val encrypted: ByteArray = Base64.decode(parts[0], Base64.DEFAULT)
        val iv: ByteArray = Base64.decode(parts[1], Base64.DEFAULT)
        val cipher: Cipher = Cipher.getInstance(transformation)
        val spec = IvParameterSpec(iv)
        cipher.init(Cipher.DECRYPT_MODE, secretKey, spec)
        return String(cipher.doFinal(encrypted), StandardCharsets.UTF_8)
    }
}
