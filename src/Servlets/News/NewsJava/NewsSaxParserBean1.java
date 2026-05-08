package Servlets.News.NewsJava;

import java.io.FileReader;

import java.util.Vector;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

public class NewsSaxParserBean1 extends DefaultHandler implements java.io.Serializable {
    public NewsSaxParserBean1() {
    }
    private String text;
    private Vector vector = new Vector();
    private NewsElement current = null;


    public Vector parse(String filename) throws Exception {
        //System.out.println("comes into parser bean");
        SAXParserFactory spf = SAXParserFactory.newInstance();

        spf.setValidating(false);
        SAXParser saxParser = spf.newSAXParser();
        // create an XML reader
        XMLReader reader = saxParser.getXMLReader();
        System.out.println("file name is: " + filename);
        FileReader file = new FileReader(filename);
        // set handler
        reader.setContentHandler((ContentHandler)this);
        // call parse on an input source
        reader.parse(new InputSource(file));


        //for(int i=0;i<vector.size();i++)
        // System.out.println("aaaaaaaaaaaaaaaaaaaaa"+vector.elements());
        return vector;
    }

    // receive notification of the beginning of an    element

    public void startElement(String uri, String name, String qName,
                             Attributes atts) {

        // System.out.println("Start Element ...."+"  "+qName);
        current = new NewsElement(uri, name, qName, atts);
        vector.addElement(current);
        text = new String();

    }

    // receive notification of the end of an element

    public void endElement(String uri, String name, String qName) {
        if (current != null && text != null) {
            current.setValue(text.trim());
        }
        current = null;
        //  System.out.println("In End Element******");

    }

    // receive notification of character data

    public void characters(char[] ch, int start, int length) {
        if (current != null && text != null) {
            String value = new String(ch, start, length);
            text += value;
            System.out.println("text in characters is:" + text);
        }
    }
}
