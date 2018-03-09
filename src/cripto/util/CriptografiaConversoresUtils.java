/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cripto.util;

import java.util.Base64;

/**
 *
 * @author Fred William Torno Junior - www.fwtj.com.br - fredwilliam@gmail.com -
 * (22) 9-8136-5786
 */
public class CriptografiaConversoresUtils {
    
    /**
     *
     * @param bytes
     * @return
     */
    public static String converterBinarioEmStringBase64(byte[] bytes){
        String encodeToString = Base64.getEncoder().encodeToString(bytes);
        return encodeToString;
    }

    /**
     *
     * @param bytes
     * @return
     */
    public static String converterBinarioEmStringSimples(byte[] bytes) {
        String retorno = "";
        retorno = retorno.concat("{");
        for (byte b : bytes) {
            retorno = retorno.concat("" + b + ", ");
        }
        retorno = retorno.concat("}");
        return retorno;
    }

    /**
     *
     * @param bytes
     * @return
     */
    public static String converterBinarioParaHexa(byte[] bytes) {

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

    /**
     *
     * @param hexa
     * @return
     */
    public static byte[] converterHexaParaBinario(String hexa) {

        byte[] conversao = new byte[hexa.length() / 2];
        String numero = null;

        for (int i = 0, j = 0; i < hexa.length(); i = i + 2) {

            numero = hexa.substring(i, i + 2);
            conversao[j] = (byte) Integer.parseInt(numero, 16);
            j++;
        }

        return conversao;
    }

}
