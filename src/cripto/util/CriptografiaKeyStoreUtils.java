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
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.SignatureException;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.SecretKey;
import org.bouncycastle.jce.X509Principal;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.x509.X509V3CertificateGenerator;

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

    private static X509Certificate generateCertificate(KeyPair keyPair) {
        
//        <dependencies>
//        <!-- https://mvnrepository.com/artifact/org.bouncycastle/bcprov-ext-jdk16 -->
//        <dependency>
//            <groupId>org.bouncycastle</groupId>
//            <artifactId>bcprov-ext-jdk16</artifactId>
//            <version>1.46</version>
//        </dependency>
        
        X509Certificate generate = null;
        try {
            Security.addProvider(new BouncyCastleProvider());
            X509V3CertificateGenerator cert = new X509V3CertificateGenerator();
            cert.setSerialNumber(BigInteger.valueOf(1));   //or generate a random number  
            cert.setSubjectDN(new X509Principal("CN=localhost"));  //see examples to add O,OU etc  
            cert.setIssuerDN(new X509Principal("CN=localhost")); //same since it is self-signed  
            cert.setPublicKey(keyPair.getPublic());
            cert.setNotBefore(new Calendar.Builder().setDate(2010, 1, 1).build().getTime());
            cert.setNotAfter(new Calendar.Builder().setDate(2020, 1, 1).build().getTime());
            cert.setSignatureAlgorithm("SHA1WithRSAEncryption");
            PrivateKey signingKey = keyPair.getPrivate();
            generate = cert.generate(signingKey, "BC");
        } catch (CertificateEncodingException ex) {
            Logger.getLogger(CriptografiaKeyStoreUtils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalStateException ex) {
            Logger.getLogger(CriptografiaKeyStoreUtils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchProviderException ex) {
            Logger.getLogger(CriptografiaKeyStoreUtils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(CriptografiaKeyStoreUtils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SignatureException ex) {
            Logger.getLogger(CriptografiaKeyStoreUtils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(CriptografiaKeyStoreUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return generate;
    }

    public static KeyStore armazenarKeyPairNaKeyStore(KeyStore keyStore, KeyPair keyPair, String aliasPrivateKey, String aliasPublicKey, String senhaPrivateKey, String senhaPublicKey) {
        try {
            X509Certificate certificate = generateCertificate(keyPair);
            Certificate[] certChain = new Certificate[1];
            certChain[0] = certificate;
            keyStore.setKeyEntry(aliasPrivateKey, (Key) keyPair.getPrivate(), senhaPrivateKey.toCharArray(), certChain);
            keyStore.setKeyEntry(aliasPublicKey, (Key) keyPair.getPublic(), senhaPublicKey.toCharArray(), certChain);
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

    public static Key recuperarSecretKeyDakeyStore(KeyStore keyStore, String alias, String senhaSecretKey) {
        Key secretKey = null;
        try {
            secretKey = keyStore.getKey(alias, senhaSecretKey.toCharArray());
        } catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException ex) {
            Logger.getLogger(CriptografiaKeyStoreUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return secretKey;
    }

    public static PublicKey recuperarPublicKeyDakeyStore(KeyStore keyStore, String alias, String senhaSecretKey) {
        PublicKey secretKey = null;
        try {
            secretKey = (PublicKey) keyStore.getKey(alias, senhaSecretKey.toCharArray());

        } catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException ex) {
            Logger.getLogger(CriptografiaKeyStoreUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return secretKey;
    }

    public static PrivateKey recuperarPrivateKeyDakeyStore(KeyStore keyStore, String alias, String senhaSecretKey) {
        PrivateKey secretKey = null;
        try {
            secretKey = (PrivateKey) keyStore.getKey(alias, senhaSecretKey.toCharArray());

        } catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException ex) {
            Logger.getLogger(CriptografiaKeyStoreUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return secretKey;
    }
}
