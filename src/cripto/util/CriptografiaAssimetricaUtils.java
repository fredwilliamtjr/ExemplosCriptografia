/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cripto.util;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 *
 * @author Fred William Torno Junior - www.fwtj.com.br - fredwilliam@gmail.com -
 * (22) 9-8136-5786
 */
public class CriptografiaAssimetricaUtils {

    private static final String METODO_ENCRIPTACAO = "RSA";

    /**
     *
     * @param publicKey
     * @param textoClaro
     * @return
     * @throws Throwable
     */
    public static byte[] criptografarTexto(PublicKey publicKey, String textoClaro) throws Throwable {

        byte[] textoCriptografado = null;

        try {
            Cipher rsacf = Cipher.getInstance(METODO_ENCRIPTACAO);
            rsacf.init(Cipher.ENCRYPT_MODE, publicKey);
            textoCriptografado = rsacf.doFinal(textoClaro.getBytes());
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException ex) {
            throw new Throwable("Erro ao criptografar informações " + ex.getMessage());
        }

        return textoCriptografado;
    }

    /**
     *
     * @param privateKey
     * @param textoCriptografado
     * @return
     * @throws Throwable
     */
    public static String descriptografarTexto(PrivateKey privateKey, byte[] textoCriptografado) throws Throwable {

        byte[] textoDescriptogradado = null;

        try {
            Cipher rsacf = Cipher.getInstance(METODO_ENCRIPTACAO);
            rsacf.init(Cipher.DECRYPT_MODE, privateKey);
            textoDescriptogradado = rsacf.doFinal(textoCriptografado);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException ex) {
            throw new Throwable("Erro ao descriptografar informações " + ex.getMessage());
        }

        return new String(textoDescriptogradado);
    }
    
    

}
