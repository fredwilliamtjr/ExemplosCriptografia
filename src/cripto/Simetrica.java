/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cripto;

import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author Fred William Torno Junior - www.fwtj.com.br - fredwilliam@gmail.com -
 * (22) 9-8136-5786
 */
public class Simetrica {

    private static final String METODO_ENCRIPTACAO = "AES";
    public static final byte[] CHAVE = geradorDeChaveCripto();

    public static String encriptar(byte[] key, String value) throws Throwable {
        try {
            SecretKeySpec skeySpec = new SecretKeySpec(key, METODO_ENCRIPTACAO);
            Cipher cipher = Cipher.getInstance(METODO_ENCRIPTACAO);
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
            byte[] encrypted = cipher.doFinal(value.getBytes());
            String encodeToString = Base64.getEncoder().encodeToString(encrypted);
            String converteParaHexa = converteParaHexa(encodeToString.getBytes());
            return converteParaHexa;
        } catch (Exception e) {
            throw new Throwable("Erro ao criptografar informações " + e.getMessage());
        }
    }

    public static String decriptar(byte[] key, String encrypted) throws Throwable {
        byte[] decrypted = null;
        try {
            SecretKeySpec skeySpec = new SecretKeySpec(key, METODO_ENCRIPTACAO);
            byte[] converteHexaParaBinario = converteHexaParaBinario(encrypted);
            String string = new String(converteHexaParaBinario);
            byte[] decode = Base64.getDecoder().decode(string);
            Cipher cipher = Cipher.getInstance(METODO_ENCRIPTACAO);
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            decrypted = cipher.doFinal(decode);
        } catch (Exception e) {
            throw new Throwable("Erro ao descriptografar informações " + e.getMessage());
        }
        return new String(decrypted);
    }

    public static byte[] geradorDeChaveCripto() {
        byte[] retorno = null;
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance(METODO_ENCRIPTACAO);
            //keyGen.init(256);
            retorno = keyGen.generateKey().getEncoded();
        } catch (Exception e) {
            e.getMessage();
        }
        return retorno;
    }

    public static String conversorByteEmString(byte[] bytes) {
        String retorno = "";
        retorno = retorno.concat("{");
        for (byte b : bytes) {
            retorno = retorno.concat("" + b + ", ");
        }
        retorno = retorno.concat("}");
        return retorno;
    }

    public static String converteParaHexa(byte[] bytes) {

        if (bytes == null) {
            return null;
        }

        String hexDigits = "0123456789ABCDEF";
        StringBuilder sbuffer = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            int j = ((int) bytes[i]) & 0xFF;
            sbuffer.append(hexDigits.charAt(j / 16));
            sbuffer.append(hexDigits.charAt(j % 16));
        }
        return sbuffer.toString();
    }

    public static byte[] converteHexaParaBinario(String hexa) {

        byte[] conversao = new byte[hexa.length() / 2];
        String numero = null;

        for (int i = 0, j = 0; i < hexa.length(); i = i + 2) {

            numero = hexa.substring(i, i + 2);
            conversao[j] = (byte) Integer.parseInt(numero, 16);
            j++;
        }

        return conversao;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Throwable {
        System.out.println("Chave AES : " + conversorByteEmString(CHAVE));
        String informacao = "Oi, Java Criptografado!";
        String informacaoCriptografada = null;
        String informacaoDescriptografada = null;
        try {
            System.out.println("Informação Pura            : " + informacao);
            informacaoCriptografada = encriptar(CHAVE, informacao);
            System.out.println("Informação criptografada   : " + informacaoCriptografada);
            informacaoDescriptografada = Simetrica.decriptar(Simetrica.CHAVE, informacaoCriptografada);
            System.out.println("Informação descriptografada: " + informacaoDescriptografada);
        } catch (Throwable e) {
            e.getMessage();
        }
    }
}
