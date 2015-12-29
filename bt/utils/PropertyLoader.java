package bt.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.StringTokenizer;

public class PropertyLoader {
    static Properties properties = new Properties();

    public static void load() throws FileNotFoundException, IOException {
        File f = new File("aktas.properties.txt");
        if (f.exists()) {
            properties.load(new FileInputStream("aktas.properties.txt"));
        } else { // read properties from classpath
            System.out.println("dosyayi bulamadi");
        }

        // Engine Version
        //String tmp1 = properties.getProperty("EngineVersion");

    }
    public static String getProperty(String propertyName) throws FileNotFoundException, IOException{
        load();
        return properties.getProperty(propertyName);
    }
    public static void setProperties(String PROPERTIES_FILENAME) {
        String classpath = System.getProperty("java.class.path");
        String ps = System.getProperty("path.separator");
        String fs = System.getProperty("file.separator");

        // System.err.println("**classpath "+classpath);

        StringTokenizer st = new StringTokenizer(classpath, ps);
        // boolean loaded = false;
        while (st.hasMoreTokens()) {
            String entry = st.nextToken();
            File f;
            if (entry.endsWith(".zip") || entry.endsWith(".jar")) {
                continue; // ignore .zip/.jar files
            }
            f = new File(entry, "");

            if (f.isDirectory()) {
                String path = f.getAbsolutePath();
                if (!path.endsWith(fs)) {
                    path += fs;
                }
                File pf = new File(path, PROPERTIES_FILENAME);
                if (pf.isFile()) {
                    try {
                        BufferedInputStream in = new BufferedInputStream(new FileInputStream(pf));
                        properties.load(in);
                        in.close();

                        return;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        System.err.println("Warning: failed to load the " + PROPERTIES_FILENAME + " file.\n"
                + "Make sure that the CLASSPATH entry is an absolute path.");
    }
    
}
