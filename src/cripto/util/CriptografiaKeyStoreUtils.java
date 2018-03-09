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
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.SecretKey;

/**
 *
 * @author Fred William Torno Junior - www.fwtj.com.br - fredwilliam@gmail.com -
 * (22) 9-8136-5786
 */
public class CriptografiaKeyStoreUtils {

    public static KeyStore criarKeyStore() {
        KeyStore keyStore = null;
        try {
            keyStore = KeyStore.getInstance("JCEKS");
            keyStore.load(null, null);
        } catch (KeyStoreException | IOException | NoSuchAlgorithmException | CertificateException ex) {
            Logger.getLogger(CriptografiaKeyStoreUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return keyStore;
    }

    public static KeyStore armazenarSecretKeyNaKeyStore(KeyStore keyStore, SecretKey secretKey, String alias, String senhaSecretKey) {
        KeyStore.SecretKeyEntry skEntry = new KeyStore.SecretKeyEntry(secretKey);
        try {
            keyStore.setEntry(alias, skEntry, new KeyStore.PasswordProtection(senhaSecretKey.toCharArray()));
        } catch (KeyStoreException ex) {
            Logger.getLogger(CriptografiaKeyStoreUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return keyStore;
    }

    public static boolean gravarKeyStoreEmDisco(KeyStore keyStore, String senhakeyStore, String caminhoCompleto) {
        try {
            FileOutputStream fos = fos = new FileOutputStream(caminhoCompleto);
            keyStore.store(fos, senhakeyStore.toCharArray());
            fos.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CriptografiaKeyStoreUtils.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (KeyStoreException | NoSuchAlgorithmException | CertificateException | IOException ex) {
            Logger.getLogger(CriptografiaKeyStoreUtils.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }

    public static KeyStore lerKeyStoreDoDisco(String caminhoCompleto, String senhakeyStore) {
        KeyStore keyStore = null;
        try {
            FileInputStream fis = new FileInputStream(caminhoCompleto);
            keyStore = KeyStore.getInstance("JCEKS");
            keyStore.load(fis, senhakeyStore.toCharArray());
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CriptografiaKeyStoreUtils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (KeyStoreException | NoSuchAlgorithmException | CertificateException | IOException ex) {
            Logger.getLogger(CriptografiaKeyStoreUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return keyStore;
    }
    
    public static Key recuperarSecretKeyDakeyStore(KeyStore keyStore, String alias, String senhaSecretKey){
        Key secretKey = null;
        try {
            secretKey = keyStore.getKey(alias, senhaSecretKey.toCharArray());
        } catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException ex) {
            Logger.getLogger(CriptografiaKeyStoreUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return secretKey;
    }
}
