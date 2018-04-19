package cripto;

import cripto.util.*;

import java.security.KeyPair;
import java.security.KeyStore;
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
    public static void main(String[] args) throws Throwable {
//        testarCriptografiaSimetrica();
//        testarCriptografiaAssimetrica();

        String md5 = CriptografiaHashUtils.md5("admin");
        System.out.println(md5);
        boolean comparacaoMd5 = CriptografiaHashUtils.comparacaoMd5("admin1", md5);
        System.out.println(comparacaoMd5);

    }

    private static void testarCriptografiaSimetrica() throws Throwable {
        // Testar Criptografia Simetrica
        String textoClaro = "Oi, me esconda Simetrica!";
        System.out.println("Texto Claro            : " + textoClaro);

        byte[] chaveSimplesMemoria = CriptografiaKeysUtils.gerarChaveSimples();
        // Não é obrigado gravar em disco, as duas linhas abaixo é so para mostrar como fazer!
        CriptografiaKeysUtils.gravarChaveSimplesEmArquivo("chaveSimples.key", chaveSimplesMemoria);
        byte[] chaveSimplesDoDisco = CriptografiaKeysUtils.lerChaveSimplesDoDisco("chaveSimples.key");

        System.out.println("Chave Gerada (Binario) : " + CriptografiaConversoresUtils.converterBinarioEmStringSimples(chaveSimplesDoDisco));
        System.out.println("Chave Gerada (Base64)  : " + CriptografiaConversoresUtils.converterBinarioEmStringBase64(chaveSimplesDoDisco));

        byte[] criptografarTexto = CriptografiaSimetricaUtils.criptografarTexto(chaveSimplesDoDisco, textoClaro);
        System.out.println("Texto Cripto (Binario) : " + CriptografiaConversoresUtils.converterBinarioEmStringSimples(criptografarTexto));
        System.out.println("Texto Cripto (Base64)  : " + CriptografiaConversoresUtils.converterBinarioEmStringBase64(criptografarTexto));

        String descriptografarTexto = CriptografiaSimetricaUtils.descriptografarTexto(chaveSimplesDoDisco, criptografarTexto);
        System.out.println("Texto Descr            : " + descriptografarTexto);

        System.out.println("-------------------------------------------------------------------------------------------------");
    }

    public static void testarCriptografiaAssimetrica() throws Throwable {
        // Testar Criptografia Simetrica
        String textoClaro = "Oi, me esconda Assimetrica!";
        System.out.println("Texto Claro           : " + textoClaro);

        CriptografiaKeysUtils.gerarParChaves();
        
        // Tambem é possivel gravar cada chave em um arquivo distinto e recuperar as chaves dos mesmos, como demostrado nas 2 linhas abaixo
        //CriptografiaKeysUtils.gravarParChavesEmArquivo("private.key", "public.key");
        //CriptografiaKeysUtils.lerParChavesDoDisco("private.key", "public.key");

        PublicKey publicKey = CriptografiaKeysUtils.getPublicKey();
        System.out.println("publicKey Gerada      : " + CriptografiaConversoresUtils.converterBinarioEmStringSimples(publicKey.getEncoded()));

        PrivateKey privateKey = CriptografiaKeysUtils.getPrivateKey();
        System.out.println("privateKey Gerada     : " + CriptografiaConversoresUtils.converterBinarioEmStringSimples(privateKey.getEncoded()));
        System.out.println("-------------------------------------------------------------------------------------------------");
        
        // Tambem é possivel gravar em uma KeyStore com Senha e recuperar as chaves dela, como demostrado nas 7 linhas abaixo
        //KeyStore keyStore = CriptografiaKeyStoreUtils.criarKeyStore();
        //KeyPair keyPair = CriptografiaKeysUtils.getKeyPair();
        //keyStore = CriptografiaKeyStoreUtils.armazenarKeyPairNaKeyStore(keyStore, keyPair, "private", "public", "123", "123");
        //CriptografiaKeyStoreUtils.gravarKeyStoreEmDisco(keyStore, "123", "nosso.keystore");
        //KeyStore keyStoreDoDisco = CriptografiaKeyStoreUtils.lerKeyStoreDoDisco("nosso.keystore", "123");
        //publicKey = CriptografiaKeyStoreUtils.recuperarPublicKeyDakeyStore(keyStoreDoDisco, "public", "123");
        //System.out.println("publicKey Recuparada  : " + CriptografiaConversoresUtils.converterBinarioEmStringSimples(publicKey.getEncoded()));
        //privateKey = CriptografiaKeyStoreUtils.recuperarPrivateKeyDakeyStore(keyStoreDoDisco, "private", "123");
        //System.out.println("privateKey Recuparada : " + CriptografiaConversoresUtils.converterBinarioEmStringSimples(privateKey.getEncoded()));
        //System.out.println("-------------------------------------------------------------------------------------------------");

        byte[] criptografarTexto = CriptografiaAssimetricaUtils.criptografarTexto(publicKey, textoClaro);
        System.out.println("Texto Cripto          : " + CriptografiaConversoresUtils.converterBinarioEmStringSimples(criptografarTexto));

        String descriptografarTexto = CriptografiaAssimetricaUtils.descriptografarTexto(privateKey, criptografarTexto);
        System.out.println("Texto Descr           : " + descriptografarTexto);

        System.out.println("-------------------------------------------------------------------------------------------------");

    }

}
