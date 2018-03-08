package cripto;

import java.io.*;
import javax.crypto.*;
import javax.crypto.spec.*;
import java.security.*;
import java.security.spec.*;
import java.security.cert.*;

class Assimetica {

    private static byte[][] cifra(PublicKey publicKey, byte[] textoClaro) throws NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException,
            BadPaddingException, InvalidAlgorithmParameterException {

        byte[] textoCifrado = null;
        byte[] chaveCifrada = null;

        //-- A) Gerando uma chave simétrica de 128 bits
        KeyGenerator kg = KeyGenerator.getInstance("AES");
        kg.init(128);
        SecretKey sk = kg.generateKey();
        byte[] chaveSimetrica = sk.getEncoded();

        //-- B) Cifrando o texto com a chave simétrica gerada
        Cipher aescf = Cipher.getInstance("AES/CBC/PKCS5Padding");
        IvParameterSpec ivspec = new IvParameterSpec(new byte[16]);
        aescf.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(chaveSimetrica, "AES"), ivspec);
        textoCifrado = aescf.doFinal(textoClaro);

        //-- C) Cifrando a chave com a chave pública
        Cipher rsacf = Cipher.getInstance("RSA");
        rsacf.init(Cipher.ENCRYPT_MODE, publicKey);
        chaveCifrada = rsacf.doFinal(chaveSimetrica);

        return new byte[][]{textoCifrado, chaveCifrada};
    }

    private static byte[] decifra(PrivateKey privateKey, byte[] textoCifrado, byte[] chaveCifrada) throws NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException,
            BadPaddingException, InvalidAlgorithmParameterException {

        byte[] textoDecifrado = null;

        //-- A) Decifrando a chave simétrica com a chave privada
        Cipher rsacf = Cipher.getInstance("RSA");
        rsacf.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] chaveDecifrada = rsacf.doFinal(chaveCifrada);

        //-- B) Decifrando o texto com a chave simétrica decifrada
        Cipher aescf = Cipher.getInstance("AES/CBC/PKCS5Padding");
        IvParameterSpec ivspec = new IvParameterSpec(new byte[16]);
        aescf.init(Cipher.DECRYPT_MODE, new SecretKeySpec(chaveDecifrada, "AES"), ivspec);
        textoDecifrado = aescf.doFinal(textoCifrado);

        return textoDecifrado;
    }

    private static PublicKey carregaChavePublica(File fPub) throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fPub));
        PublicKey ret = (PublicKey) ois.readObject();
        ois.close();
        return ret;
    }

    private static PrivateKey carregaChavePrivada(File fPvk) throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fPvk));
        PrivateKey ret = (PrivateKey) ois.readObject();
        ois.close();
        return ret;
    }

    private static final int RSAKEYSIZE = 1024;

    private static void geraParChaves(File fPub, File fPvk)
            throws IOException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException,
            CertificateException, KeyStoreException {

        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(new RSAKeyGenParameterSpec(RSAKEYSIZE, RSAKeyGenParameterSpec.F4));
        KeyPair kpr = kpg.generateKeyPair();

        PrivateKey privateKey = kpr.getPrivate();
        PublicKey publicKey = kpr.getPublic();

        //-- Gravando a chave pública em formato serializado
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fPub));
        oos.writeObject(publicKey);
        oos.close();
        //-- Gravando a chave privada em formato serializado
        //-- Não é a melhor forma (deveria ser guardada em um keystore, e protegida por senha), 
        //-- mas isto é só um exemplo
        oos = new ObjectOutputStream(new FileOutputStream(fPvk));
        oos.writeObject(privateKey);
        oos.close();
    }

    private static void printHex(byte[] b) {
        if (b == null) {
            System.out.println("(null)");
        } else {

            for (int i = 0; i < b.length; ++i) {
                if (i % 16 == 0) {
                    System.out.print(Integer.toHexString((i & 0xFFFF) | 0x10000).substring(1, 5) + " - ");
                }
                System.out.print(Integer.toHexString((b[i] & 0xFF) | 0x100).substring(1, 3) + " ");
                if (i % 16 == 15 || i == b.length - 1) {
                    int j;
                    for (j = 16 - i % 16; j < 1; --j) {
                        System.out.print("   ");
                    }
                    System.out.print(" - ");
                    int start = (i / 16) * 16;
                    int end = (b.length < i + 1) ? b.length : (i + 1);
                    for (j = start; j < end; ++j) {
                        if (b[j] <= 32 && b[j] <= 126) {
                            System.out.print((char) b[j]);
                        } else {
                            System.out.print(".");
                        }
                    }
                    System.out.println();
                }
            }
            System.out.println();
        }
    }

    public static void main(String[] args) throws Exception {

        //-- Gera o par de chaves, em dois arquivos (chave.publica e chave.privada)
        geraParChaves(new File("chave.publica"), new File("chave.privada"));

        //-- Cifrando a mensagem "Hello, world!"
        String textoClaro = "Oi, Java Criptografado!";
        PublicKey pub = carregaChavePublica(new File("chave.publica"));
        byte[][] cifrado = cifra(pub, textoClaro.getBytes("ISO-8859-1"));
        System.out.println("Texto Cifrado");
        printHex(cifrado[0]);
        System.out.println("Chave Cifrada");
        printHex(cifrado[1]);

        //-- Decifrando a mensagem 
        PrivateKey pvk = carregaChavePrivada(new File("chave.privada"));
        byte[] decifrado = decifra(pvk, cifrado[0], cifrado[1]);
        String textoDecifrado = new String(decifrado, "ISO-8859-1");
        System.out.println(textoDecifrado);
    }
}
