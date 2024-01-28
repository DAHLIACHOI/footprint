package com.study.footprint.common.util.aes;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.util.Arrays;
import java.util.Base64;

@Component
public class AesUtil {

    private static final int IV_LENGTH = 16;

    private static final int SALT_LENGTH = 20;

    @Value("${aes.secret-key}")
    private String secretKey; // 32byte

    private String iv = "";

    @Value("${aes.iteration-count}")
    private int iterationCount;

    @Value("${aes.digest-bit-length}")
    private int digestBitLength;

    private static final String AES_METHOD = "AES/CBC/PKCS5Padding";

    private static final String PBKDF_METHOD = "PBKDF2WithHmacSHA1";

    private static final String CIPHER_FINAL_ENCODING = "UTF-8";

    private static final String ENCODING_METHOD = "AES";


    public String encodeUnique(String targetEncodeString) {
        try {
            iv = secretKey.substring(0, IV_LENGTH);

            byte[] keyData = secretKey.getBytes();

            SecretKey secureKey = new SecretKeySpec(keyData, ENCODING_METHOD);

            Cipher c = Cipher.getInstance(AES_METHOD);
            c.init(Cipher.ENCRYPT_MODE, secureKey, new IvParameterSpec(iv.getBytes()));

            byte[] encrypted = c.doFinal(targetEncodeString.getBytes(CIPHER_FINAL_ENCODING));
            String enStr = new String(Base64.getEncoder().encode(encrypted));

            return enStr;

        } catch (Exception e) {

            throw new RuntimeException(e);
        }
    }

    public String decodeUnique(String targetDecodeString) {

        try {

            iv = secretKey.substring(0, IV_LENGTH);

            byte[] keyData = secretKey.getBytes();
            SecretKey secureKey = new SecretKeySpec(keyData, ENCODING_METHOD);
            Cipher c = Cipher.getInstance(AES_METHOD);
            c.init(Cipher.DECRYPT_MODE, secureKey, new IvParameterSpec(iv.getBytes(CIPHER_FINAL_ENCODING)));

            byte[] byteStr = Base64.getDecoder().decode(targetDecodeString.getBytes());

            return new String(c.doFinal(byteStr), CIPHER_FINAL_ENCODING);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public String encode(String targetEncodeString) {

        try {
            iv = secretKey.substring(0, IV_LENGTH);

            byte[] slatBytes = createSalt();

            SecretKeySpec secretKeySpec = passwordBasedKeyDerivation(slatBytes);

            AesEncDto encDto = encrypt(secretKeySpec, targetEncodeString);

            String saltAndIvAndEncryptedDataString = makeSaltAndIvAndEncryptedData(slatBytes, encDto);

            return saltAndIvAndEncryptedDataString;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public String decode(String targetDecodeString) {

        try {

            AesDecDto aesDecDto = parseEncryptedData(targetDecodeString);

            SecretKeySpec secretKeySpec = passwordBasedKeyDerivation(aesDecDto.getSaltBytes());

            String decryptedData = decrypt(secretKeySpec, aesDecDto);

            return decryptedData;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private AesDecDto parseEncryptedData(String targetDecodeString) {
        byte[] byteStr = Base64.getDecoder().decode(targetDecodeString.getBytes());

        byte[] saltBytes = Arrays.copyOfRange(byteStr, 0, SALT_LENGTH);
        byte[] originalIvBytes = Arrays.copyOfRange(byteStr, SALT_LENGTH, SALT_LENGTH + IV_LENGTH);
        byte[] originalEncryptedBytes = Arrays.copyOfRange(byteStr, SALT_LENGTH + IV_LENGTH, byteStr.length);

        AesDecDto aesDecDto = AesDecDto.builder()
                .saltBytes(saltBytes)
                .originalIvBytes(originalIvBytes)
                .originalEncryptedData(originalEncryptedBytes)
                .build();

        return aesDecDto;
    }

    private String makeSaltAndIvAndEncryptedData(byte[] slatBytes, AesEncDto encDto) throws InvalidParameterSpecException {

        AlgorithmParameters algorithmParameters = encDto.getCipher().getParameters();
        byte[] ivBytes = algorithmParameters.getParameterSpec(IvParameterSpec.class).getIV();

        byte[] saltAndIvAndEncrypted = new byte[SALT_LENGTH + IV_LENGTH + encDto.getEncryptedData().length];
        System.arraycopy(slatBytes, 0, saltAndIvAndEncrypted, 0, slatBytes.length);
        System.arraycopy(ivBytes, 0, saltAndIvAndEncrypted, slatBytes.length, IV_LENGTH);
        System.arraycopy(encDto.getEncryptedData(), 0, saltAndIvAndEncrypted, slatBytes.length + IV_LENGTH, encDto.getEncryptedData().length);

        return new String(Base64.getEncoder().encode(saltAndIvAndEncrypted));

    }

    private String decrypt(SecretKeySpec secretKeySpec, AesDecDto aesDecDto) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException {
        Cipher cipher = Cipher.getInstance(AES_METHOD);
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, new IvParameterSpec(aesDecDto.getOriginalIvBytes()));

        return new String(cipher.doFinal(aesDecDto.getOriginalEncryptedData()), CIPHER_FINAL_ENCODING);
    }

    private AesEncDto encrypt(SecretKeySpec secretKeySpec, String targetEncodeString) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException, BadPaddingException, IllegalBlockSizeException {

        Cipher cipher = Cipher.getInstance(AES_METHOD);
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);

        byte[] encryptedData = cipher.doFinal(targetEncodeString.getBytes(CIPHER_FINAL_ENCODING));

        AesEncDto encDto = AesEncDto.builder().cipher(cipher).encryptedData(encryptedData).build();

        return encDto;
    }

    private byte[] createSalt() throws NoSuchAlgorithmException {
        byte[] saltBytes = new byte[SALT_LENGTH];
        SecureRandom.getInstanceStrong().nextBytes(saltBytes);

        return saltBytes;
    }

    private SecretKeySpec passwordBasedKeyDerivation(byte[] saltBytes) throws NoSuchAlgorithmException, InvalidKeySpecException {

        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(PBKDF_METHOD);
        PBEKeySpec pbeKeySpec = new PBEKeySpec(secretKey.toCharArray(), saltBytes, iterationCount, digestBitLength);

        SecretKey secretKey = secretKeyFactory.generateSecret(pbeKeySpec);
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getEncoded(), ENCODING_METHOD);

        return secretKeySpec;
    }
}
