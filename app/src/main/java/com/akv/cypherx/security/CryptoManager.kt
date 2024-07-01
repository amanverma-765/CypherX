package com.akv.cypherx.security

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec
import android.util.Base64
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CryptoManager {

    private val keyStore = KeyStore.getInstance("AndroidKeyStore").apply {
        load(null)
    }

    private suspend fun getEncryptCipher(): Cipher = withContext(Dispatchers.IO) {
        Cipher.getInstance(TRANSFORMATION).apply {
            init(Cipher.ENCRYPT_MODE, getKey())
        }
    }

    private suspend fun getDecryptCipherForIv(iv: ByteArray): Cipher = withContext(Dispatchers.IO) {
        Cipher.getInstance(TRANSFORMATION).apply {
            init(Cipher.DECRYPT_MODE, getKey(), IvParameterSpec(iv))
        }
    }

    private suspend fun getKey(): SecretKey = withContext(Dispatchers.IO) {
        val existingKey = keyStore.getEntry("secret", null) as? KeyStore.SecretKeyEntry
        existingKey?.secretKey ?: createKey()
    }

    private suspend fun createKey(): SecretKey = withContext(Dispatchers.IO) {
        KeyGenerator.getInstance(ALGORITHM).apply {
            init(
                KeyGenParameterSpec.Builder(
                    "secret",
                    KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
                )
                    .setBlockModes(BLOCK_MODE)
                    .setEncryptionPaddings(PADDING)
                    .setUserAuthenticationRequired(false)
                    .setRandomizedEncryptionRequired(true)
                    .build()
            )
        }.generateKey()
    }

    suspend fun encrypt(input: String): String = withContext(Dispatchers.IO) {
        val bytes = input.toByteArray()
        val encryptCipher = getEncryptCipher()
        val encryptedBytes = encryptCipher.doFinal(bytes)
        val iv = encryptCipher.iv
        val result = ByteArray(iv.size + encryptedBytes.size)
        System.arraycopy(iv, 0, result, 0, iv.size)
        System.arraycopy(encryptedBytes, 0, result, iv.size, encryptedBytes.size)
        Base64.encodeToString(result, Base64.DEFAULT)
    }

    suspend fun decrypt(encryptedInput: String): String = withContext(Dispatchers.IO) {
        val encryptedBytesWithIv = Base64.decode(encryptedInput, Base64.DEFAULT)
        val ivSize = 16
        val iv = ByteArray(ivSize)
        val encryptedBytes = ByteArray(encryptedBytesWithIv.size - ivSize)
        System.arraycopy(encryptedBytesWithIv, 0, iv, 0, iv.size)
        System.arraycopy(encryptedBytesWithIv, iv.size, encryptedBytes, 0, encryptedBytes.size)
        val decryptCipher = getDecryptCipherForIv(iv)
        val decryptedBytes = decryptCipher.doFinal(encryptedBytes)
        String(decryptedBytes)
    }

    companion object {
        private const val ALGORITHM = KeyProperties.KEY_ALGORITHM_AES
        private const val BLOCK_MODE = KeyProperties.BLOCK_MODE_CBC
        private const val PADDING = KeyProperties.ENCRYPTION_PADDING_PKCS7
        private const val TRANSFORMATION = "$ALGORITHM/$BLOCK_MODE/$PADDING"
    }
}
