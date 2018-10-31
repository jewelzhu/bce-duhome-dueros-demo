package com.baidubce.services.smarthome.util;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Security;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;

/**
 * Utils for security related operations
 */
public class SecurityUtils {

    static {
        try {
            Security.addProvider(new BouncyCastleProvider());
        } catch (Exception e) {
            System.out.println("Failed to add BouncyCastleProvider");
        }
    }

    private static final Logger logger = LoggerFactory.getLogger(SecurityUtils.class);

    private static final String SIGNATURE_ALGORITHM = "SHA256withRSA";


    public static String base64Encode(byte[] data) {
        return Base64.encodeBase64String(data);
    }

    public static byte[] base64Decode(String data) {
        return Base64.decodeBase64(data);
    }


    public static String sha256rsa(byte[] data, String privateKeyPem) {

        PrivateKey privateKey = getPrivateKey(privateKeyPem);
        try {
            Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
            signature.initSign(privateKey);
            signature.update(data);
            return base64Encode(signature.sign());
        } catch (Exception ex) {
            logger.error("failed to get sign", ex);
            throw new RuntimeException("failed to get sign");
        }
    }

    private static PrivateKey getPrivateKey(String privateKeyPem) {
        try {
            privateKeyPem = privateKeyPem
                    .replace("-----BEGIN RSA PRIVATE KEY-----", "")
                    .replace("-----END RSA PRIVATE KEY-----", "");
            byte[] privateKeyData = base64Decode(privateKeyPem);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyData);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePrivate(keySpec);
        } catch (Exception ex) {
            logger.error("failed to get private key", ex);
            throw new RuntimeException("failed to get private key");
        }
    }
}
