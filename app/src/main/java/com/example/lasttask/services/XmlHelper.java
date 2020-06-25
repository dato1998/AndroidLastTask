package com.example.lasttask.services;

import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.ByteArrayInputStream;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class XmlHelper {
    public static Document getXmlDoc(String xmlRecords) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            ByteArrayInputStream encXML = new ByteArrayInputStream(xmlRecords.getBytes("UTF8"));
            return db.parse(encXML);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getCharacterDataFromElement(Element e) {
        Node child = e.getFirstChild();
        if (child instanceof CharacterData) {
            CharacterData cd = (CharacterData) child;
            return cd.getData();
        }
        return "?";
    }

    public static String getTextFromElement(Element element, String name) {
        NodeList nodeName = element.getElementsByTagName(name);
        Element line = (Element) nodeName.item(0);
        return getCharacterDataFromElement(line);
    }
}
