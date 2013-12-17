package com.kodcu;

import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.pdf.PDFParser;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;

import static java.nio.file.StandardOpenOption.READ;

/**
 * Created by usta on 17.12.2013.
 */
public class App {

    public static void main(String[] args) throws Exception {

        String root = "./src/main/resources";

        Tika tika = new Tika();

        /// File nesnesinden tipi tespit eder.

        File file = new File(root + "/javaee7.jpg");

        String tip = tika.detect(file);

        System.out.println("Jpg İçeriği : " + tip);

        /// InputStream nesnesinden tipi tespit eder.

        FileInputStream is = new FileInputStream(root + "/index.php");

        System.out.println("Php içeriği : " + tika.detect(is));

        /// byte[] dizi nesnesi üzerinden tipi tespit eder.

        try (FileChannel kanal =
                     FileChannel.open(Paths.get(root + "/tika.png"), READ)) {

            ByteBuffer tampon = ByteBuffer.allocate((int) kanal.size());

            kanal.read(tampon);

            System.out.println("Png içeriği : " + tika.detect(tampon.array()));

        }

        /// PDFParser nesnesi üzerinden Metadata bilgilerini elde eder.

        Metadata metadata = new Metadata();
        ContentHandler handler = new DefaultHandler();
        Parser parser = new PDFParser();
        ParseContext context = new ParseContext();

        parser.parse(new FileInputStream(root + "/mypdf.pdf"), handler, metadata, context);

        String[] names = metadata.names();

        for (String name : names)
            System.out.printf("%s : %s %n", name, metadata.get(name));

    }
}
