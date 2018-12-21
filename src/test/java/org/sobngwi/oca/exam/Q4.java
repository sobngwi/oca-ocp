package org.sobngwi.oca.exam;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Q4 {

    public static void main(String[] args) {
        // missing offset parameter
        final byte[] buffer = new byte[100];
        int lengthRead;
        OutputStream out = null;
        InputStream in = null;
        try {
            while ((lengthRead = in.read(buffer)) > 0) {
                out.flush();
                out.write(buffer, lengthRead, 0 /* The offset Param */);
                out.flush();
            }
        } catch (Exception e) { }

    }
}
