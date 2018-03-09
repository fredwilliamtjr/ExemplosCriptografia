/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cripto.util;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author Fred William Torno Junior - www.fwtj.com.br - fredwilliam@gmail.com -
 * (22) 9-8136-5786
 */
public class CriptografiaSimetricaUtils {

    private static final String METODO_ENCRIPTACAO = "AES";

    public static byte[] criptografarTexto(byte[] key, String textoClaro) throws Throwable {
        byte[] encrypted = null;
        try {
            SecretKeySpec skeySpec = new SecretKeySpec(key, METODO_ENCRIPTACAO);
            Cipher cipher = Cipher.getInstance(METODO_ENCRIPTACAO);
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
            encrypted = cipher.doFinal(textoClaro.getBytes());
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException ex) {
            throw new Throwable("Erro ao criptografar informações " + ex.getMessage());
        }
        return encrypted;
    }

    public static String descriptografarTexto(byte[] key, byte[] textoCriptografado) throws Throwable {
        byte[] decrypted = null;
        try {
            SecretKeySpec skeySpec = new SecretKeySpec(key, METODO_ENCRIPTACAO);
            Cipher cipher = Cipher.getInstance(METODO_ENCRIPTACAO);
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            decrypted = cipher.doFinal(textoCriptografado);
        } catch (InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e) {
            throw new Throwable("Erro ao descriptografar informações " + e.getMessage());
        }
        return new String(decrypted);
    }

    

}
