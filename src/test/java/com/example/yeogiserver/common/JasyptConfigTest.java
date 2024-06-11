package com.example.yeogiserver.common;

import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class JasyptConfigTest {

//    @DisplayName("jasypt 암호화")
//    @Test
//    void jasyptEncryptor() {
//
//        String key = "";
//
//        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
//        encryptor.setPoolSize(8);   // 코어 수
//        encryptor.setPassword(key);
//        encryptor.setAlgorithm("PBEWithMD5AndTripleDES");
//
//        String str = "";
//        String encrypt = encryptor.encrypt(str);
//        String decrypt = encryptor.decrypt(encrypt);
//
//        System.out.println("encrypt = " + encrypt);
//        System.out.println("decrypt = " + decrypt);
//    }
}
