package org.quantum_safe.client_quantum.Service;

import java.io.File;
import java.io.FileWriter;

public class KeyStorageUtil {

    public static void savePrivateKey(String email, String privateKey) throws Exception {

        File dir = new File("keys");
        if (!dir.exists()) {
            dir.mkdir();
        }

        FileWriter writer =
                new FileWriter("keys/" + email + "_private.key");

        writer.write(privateKey);
        writer.close();
    }
}