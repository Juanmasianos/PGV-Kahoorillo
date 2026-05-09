package net.salesianos.kahorillo.utils;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class SecureManager {

  public static final String DEFAULT_KEY = "salesianos2026key";

  private Cipher cipher;
  private Cipher decipher;

  public SecureManager() {
    this(DEFAULT_KEY);
  }

  public SecureManager(String secretText) {
    SecretKey secretKey = createSecretKey(secretText);
    this.initializeCiphers(secretKey);
  }

  private SecretKey createSecretKey(String secretText) {
    byte[] keyBytes = secretText.getBytes();
    if (keyBytes.length < 16) {
      byte[] temp = new byte[16];
      System.arraycopy(keyBytes, 0, temp, 0, keyBytes.length);
      keyBytes = temp;
    } else if (keyBytes.length > 16) {
      byte[] temp = new byte[16];
      System.arraycopy(keyBytes, 0, temp, 0, 16);
      keyBytes = temp;
    }
    return new SecretKeySpec(keyBytes, "AES");
  }

  private void initializeCiphers(SecretKey secretKey) {
    try {
      this.cipher = Cipher.getInstance("AES");
      this.decipher = Cipher.getInstance("AES");
    } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
      e.printStackTrace();
    }

    try {
      this.cipher.init(Cipher.ENCRYPT_MODE, secretKey);
      this.decipher.init(Cipher.DECRYPT_MODE, secretKey);
    } catch (InvalidKeyException e) {
      e.printStackTrace();
    }
  }

  public void printMessage(byte[] encryptedMessage) {
    System.out.println("Mensaje encriptado: " + Base64.getEncoder().encodeToString(encryptedMessage));
  }

  public byte[] encript(String newText) {
    try {
      return cipher.doFinal(newText.getBytes());
    } catch (IllegalBlockSizeException | BadPaddingException e) {
      System.out.println("¡Error al encriptar mensaje!");
      return null;
    }
  }

  public String decript(byte[] encryptedMessage) {
    try {
      byte[] decryptedMessage = decipher.doFinal(encryptedMessage);
      return new String(decryptedMessage);
    } catch (IllegalBlockSizeException | BadPaddingException e) {
      System.out.println("¡Error al desencriptar mensaje!");
      return null;
    }
  }

  public String encriptToBase64(String text) {
    byte[] encrypted = encript(text);
    if (encrypted == null)
      return null;
    return Base64.getEncoder().encodeToString(encrypted);
  }

  public String decriptFromBase64(String base64) {
    try {
      byte[] encrypted = Base64.getDecoder().decode(base64);
      return decript(encrypted);
    } catch (Exception e) {
      System.out.println("¡Error al desencriptar desde Base64!");
      return null;
    }
  }
}