package cripto.util;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * Autor: Fred William Torno Junior
 * E-Mail: fredwilliam@gmail.com / fredwilliam@outlook.com
 * Site: www.fwtj.com.br
 * Telefone: (22) 9-8136-5786
 * Data: 19/04/2018
 * Hora: 14:33
 * Copyright©Fwtj Sistemas. Todos os direitos reservados.
 */
public class CriptografiaHashUtils {


    public static String md5(String texto){
        return DigestUtils.md5Hex(texto);
    }

    public static String sha256(String texto){
        return DigestUtils.sha256Hex(texto);
    }

    public static String sha384(String texto){
        return DigestUtils.sha384Hex(texto);
    }

    public static String sha512(String texto){
        return DigestUtils.sha512Hex(texto);
    }

    public static boolean comparacaoMd5(String textoNormal, String textoMd5){
        String md5 = md5(textoNormal);
        return textoMd5.equals(md5);
    }

    public static boolean comparacaoSha256(String textoNormal, String textoSha256){
        String sha256 = sha256(textoNormal);
        return textoSha256.equals(sha256);
    }

    public static boolean comparacaoSha384(String textoNormal, String textoSha384){
        String sha384 = sha384(textoNormal);
        return textoSha384.equals(sha384);
    }

    public static boolean comparacaoSha512(String textoNormal, String textoSha512){
        String sha512 = sha512(textoNormal);
        return textoSha512.equals(sha512);
    }

}
