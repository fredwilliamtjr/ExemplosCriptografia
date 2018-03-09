/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cripto.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.RSAKeyGenParameterSpec;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.KeyGenerator;

/**
 *
 * @author Fred William Torno Junior - www.fwtj.com.br - fredwilliam@gmail.com -
 * (22) 9-8136-5786
 */
public class CriptografiaKeysUtils {

    private static final int RSAKEYSIZE = 1024;
    private static final String METODO_ENCRIPTACAO = "RSA";
    private static final String METODO_ENCRIPTACAO2 = "AES";

    private static PrivateKey privateKey = null;
    private static PublicKey publicKey = null;

    public static PrivateKey getPrivateKey() {
        return privateKey;
    }

    public static PublicKey getPublicKey() {
        return publicKey;
    }

    /**
     *
     * @return
     */
    public static byte[] gerarChaveSimples() {
        byte[] retorno = null;
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance(METODO_ENCRIPTACAO2);
            //keyGen.init(128);
            retorno = keyGen.generateKey().getEncoded();
        } catch (NoSuchAlgorithmException e) {
            e.getMessage();
        }
        return retorno;
    }

    /**
     *
     * @param caminhoCompleto
     * @param chaveSimples
     * @throws Throwable
     */
    public static boolean gravarChaveSimplesEmArquivo(String caminhoCompleto, byte[] chaveSimples) throws Throwable {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(caminhoCompleto));
            oos.writeObject(chaveSimples);
            oos.close();
            
            return true;

        } catch (FileNotFoundException ex) {
            throw new Throwable("Erro ao gravar em disco chave simples " + ex.getMessage());
        } catch (IOException ex) {
            throw new Throwable("Erro ao gravar em disco chave simples " + ex.getMessage());
        }

    }

    /**
     *
     * @param caminhoCompleto
     * @return
     * @throws Throwable
     */
    public static byte[] lerChaveSimplesDoDisco(String caminhoCompleto) throws Throwable {

        byte[] retorno;

        try {
            ObjectInputStream objectInputStream1 = new ObjectInputStream(new FileInputStream(caminhoCompleto));
            Object readObject = objectInputStream1.readObject();

            retorno = (byte[]) readObject;

            return retorno;
        } catch (FileNotFoundException ex) {
            throw new Throwable("Erro ao gravar em disco par de chaves " + ex.getMessage());
        } catch (IOException | ClassNotFoundException ex) {
            throw new Throwable("Erro ao gravar em disco par de chaves " + ex.getMessage());
        }

    }

    /**
     * Apos gerar o par de cjaves usar os metodos get para buscar!
     *
     * @throws java.lang.Throwable
     */
    public static boolean gerarParChaves() throws Throwable {
        try {
            KeyPairGenerator kpg = KeyPairGenerator.getInstance(METODO_ENCRIPTACAO);
            kpg.initialize(new RSAKeyGenParameterSpec(RSAKEYSIZE, RSAKeyGenParameterSpec.F4));
            KeyPair kpr = kpg.generateKeyPair();
            privateKey = kpr.getPrivate();
            publicKey = kpr.getPublic();
            return true;
        } catch (NoSuchAlgorithmException | InvalidAlgorithmParameterException ex) {
            throw new Throwable("Erro ao gerar par de chaves " + ex.getMessage());
        }
    }

    /**
     *
     * @param caminhoCompletoPrivateKey
     * @param caminhoCompletoPublicKeyKey
     * @throws java.lang.Throwable
     */
    public static boolean gravarParChavesEmArquivo(String caminhoCompletoPrivateKey, String caminhoCompletoPublicKeyKey) throws Throwable {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(caminhoCompletoPublicKeyKey));
            oos.writeObject(publicKey);
            oos.close();

            oos = new ObjectOutputStream(new FileOutputStream(caminhoCompletoPrivateKey));
            oos.writeObject(privateKey);
            oos.close();

            return true;

        } catch (FileNotFoundException ex) {
            throw new Throwable("Erro ao gravar em disco par de chaves " + ex.getMessage());
        } catch (IOException ex) {
            throw new Throwable("Erro ao gravar em disco par de chaves " + ex.getMessage());
        }

    }

    /**
     * Apos ler do disco buscar via get!
     *
     * @param caminhoCompletoPrivateKey
     * @param caminhoCompletoPublicKeyKey
     * @return
     * @throws Throwable
     */
    public static boolean lerParChavesDoDisco(String caminhoCompletoPrivateKey, String caminhoCompletoPublicKeyKey) throws Throwable {

        privateKey = null;
        publicKey = null;

        try {
            ObjectInputStream objectInputStream1 = new ObjectInputStream(new FileInputStream(caminhoCompletoPrivateKey));
            Object readObject1 = objectInputStream1.readObject();
            privateKey = (PrivateKey) readObject1;

            ObjectInputStream objectInputStream2 = new ObjectInputStream(new FileInputStream(caminhoCompletoPublicKeyKey));
            Object readObject2 = objectInputStream2.readObject();
            publicKey = (PublicKey) readObject2;

            return true;
        } catch (FileNotFoundException ex) {
            throw new Throwable("Erro ao gravar em disco par de chaves " + ex.getMessage());
        } catch (IOException | ClassNotFoundException ex) {
            throw new Throwable("Erro ao gravar em disco par de chaves " + ex.getMessage());
        }

    }

}
