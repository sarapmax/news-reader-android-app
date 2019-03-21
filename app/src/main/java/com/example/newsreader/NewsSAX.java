package com.example.newsreader;

import android.util.Log;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.InputStream;
import java.net.URL;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class NewsSAX extends DefaultHandler {
    Boolean itemFound = false;
    Boolean descriptionFound = false;
    ParserListener parserListener;
    String element = "";

    NewsSAX(final URL url, ParserListener parserListener) {
        this.parserListener = parserListener;

        new Thread(new Runnable() {
            @Override
            public void run() {
                SAXParserFactory factory = SAXParserFactory.newInstance();

                try {
                    InputStream in = url.openStream();

                    SAXParser parser = factory.newSAXParser();

                    parser.parse(in, NewsSAX.this);

                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);

        if (localName.equals("item")) {
            itemFound = true;
        } else if (itemFound && localName.equals("description")) {
            descriptionFound = true;
        }

        element = "";
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);

        if (itemFound && localName.equals("title")) {
            parserListener.setTitle(element);
        } else if (descriptionFound && localName.equals("description")) {
            Log.i("Description", element);
            parserListener.setDescription(element);

            descriptionFound = false;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);

        element += new String(ch, start, length);
    }
}
