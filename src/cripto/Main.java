package cripto;

import cripto.util.CriptografiaAssimetricaUtils;
import cripto.util.CriptografiaConversoresUtils;
import cripto.util.CriptografiaKeysUtils;
import cripto.util.CriptografiaSimetricaUtils;
import java.security.PrivateKey;
import java.security.PublicKey;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Fred William Torno Junior - www.fwtj.com.br - fredwilliam@gmail.com -
 * (22) 9-8136-5786
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Throwable  {
        testarCriptografiaAssimetrica();
    }

    private static void testarCriptografiaSimetrica() throws Throwable {
        // Testar Criptografia Simetrica
        String textoClaro = "Oi, me esconda!";
        System.out.println("Texto Claro  : " + textoClaro);

        byte[] chaveSimples = CriptografiaKeysUtils.gerarChaveSimples();
        System.out.println("Chave Gerada : " + CriptografiaConversoresUtils.converterBinarioEmStringSimples(chaveSimples));
        System.out.println("Chave Gerada : " + CriptografiaConversoresUtils.converterBinarioEmStringBase64(chaveSimples));

        byte[] criptografarTexto = CriptografiaSimetricaUtils.criptografarTexto(chaveSimples, textoClaro);
        System.out.println("Texto Cripto : " + CriptografiaConversoresUtils.converterBinarioEmStringSimples(criptografarTexto));
        System.out.println("Texto Cripto : " + CriptografiaConversoresUtils.converterBinarioEmStringBase64(criptografarTexto));

        String descriptografarTexto = CriptografiaSimetricaUtils.descriptografarTexto(chaveSimples, criptografarTexto);
        System.out.println("Texto Descr  : " + descriptografarTexto);
    }
    
    public static void testarCriptografiaAssimetrica() throws Throwable{
        // Testar Criptografia Simetrica
        String textoClaro = "Oi, me esconda!";
        System.out.println("Texto Claro  : " + textoClaro);
        
        CriptografiaKeysUtils.gerarParChaves();
        
        PublicKey publicKey = CriptografiaKeysUtils.getPublicKey();
        System.out.println("publicKey Gerada  : " + CriptografiaConversoresUtils.converterBinarioEmStringSimples(publicKey.getEncoded()));
        System.out.println("publicKey Gerada  : " + CriptografiaConversoresUtils.converterBinarioEmStringBase64(publicKey.getEncoded()));
        
        PrivateKey privateKey = CriptografiaKeysUtils.getPrivateKey();
        System.out.println("privateKey Gerada : " + CriptografiaConversoresUtils.converterBinarioEmStringSimples(privateKey.getEncoded()));
        System.out.println("privateKey Gerada : " + CriptografiaConversoresUtils.converterBinarioEmStringBase64(privateKey.getEncoded()));
        
        byte[] criptografarTexto = CriptografiaAssimetricaUtils.criptografarTexto(publicKey, textoClaro);
        System.out.println("Texto Cripto      : " + CriptografiaConversoresUtils.converterBinarioEmStringSimples(criptografarTexto));
        System.out.println("Texto Cripto      : " + CriptografiaConversoresUtils.converterBinarioEmStringBase64(criptografarTexto));
        
        String descriptografarTexto = CriptografiaAssimetricaUtils.descriptografarTexto(privateKey, criptografarTexto);
        System.out.println("Texto Descr       : " + descriptografarTexto);
        
        
        
    }

}
