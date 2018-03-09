/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cripto.referencias;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

/**
 *
 * @author Fred William Torno Junior - www.fwtj.com.br - fredwilliam@gmail.com -
 * (22) 9-8136-5786
 */
public class ExemploKeyStore {

    public static String conversorByteEmString(byte[] bytes) {
        String retorno = "";
        retorno = retorno.concat("{");
        for (byte b : bytes) {
            retorno = retorno.concat("" + b + ", ");
        }
        retorno = retorno.concat("}");
        return retorno;
    }

    /**
     * @param args the command line arguments
     * @throws java.security.KeyStoreException
     * @throws java.io.IOException
     * @throws java.security.NoSuchAlgorithmException
     * @throws java.security.cert.CertificateException
     * @throws java.security.UnrecoverableEntryException
     */
    public static void main(String[] args) throws KeyStoreException, IOException, NoSuchAlgorithmException, CertificateException, UnrecoverableEntryException {

        SecretKey key = KeyGenerator.getInstance("AES").generateKey();
        System.out.println(conversorByteEmString(key.getEncoded()));
        KeyStore.SecretKeyEntry skEntry = new KeyStore.SecretKeyEntry(key);

        KeyStore ks = KeyStore.getInstance("JCEKS");
        ks.load(null, null);
        ks.setEntry("MinhaKey", skEntry, new KeyStore.PasswordProtection("SenhaMinhaKey".toCharArray()));

        FileOutputStream fos = new FileOutputStream("meu.keystore");
        ks.store(fos, "SenhaMeuKeyStore".toCharArray());
        fos.close();

        FileInputStream fis = new FileInputStream("meu.keystore");

        KeyStore ks2 = KeyStore.getInstance("JCEKS");
        ks2.load(fis, "SenhaMeuKeyStore".toCharArray());
        Key key2 = ks2.getKey("MinhaKey", "SenhaMinhaKey".toCharArray());
        System.out.println(conversorByteEmString(key2.getEncoded()));

    }

}
