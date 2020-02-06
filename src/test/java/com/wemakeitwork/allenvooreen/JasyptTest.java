package com.wemakeitwork.allenvooreen;

import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JasyptTest {
    @Autowired
    StringEncryptor jasyptStringEncryptor;

    //@Value("${allenvooreen.sleutel}")
    //private String sleutel;

    @Test
    public void encryptieMetJasypt() {
        // Stap 1
        // Geef deze variabele een waarde mee die encrypted moet worden.
        String password = "Sleutel";

        // Stap 2
        // Run deze test, 'encryptieMetJasypt'.
        String encryptedPassword = jasyptStringEncryptor.encrypt(password);

        // Stap 3
        System.out.println("Kopieer de onderstaande regel vanuit de terminal.");
        System.out.println(encryptedPassword);

        // Stap 4
        // Haal de opgegeven waarde weer weg, voordat je commit.
    }

    @Test
    public void werktJasypt() {
        String verwacht = "Sleutel";
        //Assert.assertEquals(verwacht, sleutel);
    }}