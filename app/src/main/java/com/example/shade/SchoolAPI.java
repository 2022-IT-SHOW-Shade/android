package com.example.shade;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class SchoolAPI {

    private static final String DEFAULT_SCHOOL_HOST = "http://open.neis.go.kr/hub/schoolInfo?KEY=b4186d1e9bdf427aaf7e64659a56739c";

    private String schoolHost;

    public SchoolAPI(String schoolHost) {
        this.schoolHost = schoolHost;
    }

    //
    public List<School> getSchoolByName(String name) throws ParseException {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        Document doc;

        try{
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            name = URLEncoder.encode(name, String.valueOf(StandardCharsets.UTF_8));
            doc = dBuilder.parse(schoolHost + "&SCHUL_MN="  + name);

        }catch (ParserConfigurationException | SAXException | IOException e){
            throw new com.example.shade.Exception.ParseException();
        }

        doc.getDocumentElement().normalize();

        NodeList nList = doc.getElementsByTagName("row");

        List<School> response = new ArrayList<>();

        for(int i = 0, length = nList.getLength(); i < length; i++){
            Node node = nList.item(i);

            if(node.getNodeType() == Node.ELEMENT_NODE){
                Element element = (Element) node;
                response.add(new School(getTagValue("ATPT_OFCDC_SC_CODE", element), getTagValue("SD_SCHUL_CODE", element),
                        getTagValue("LCTN_SC_NM", element), getTagValue("SCHUL_NM", element)));
            }
        }

        return response;
    }

    private String getTagValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();

        Node nValue = nodeList.item(0);
        if(nValue == null)
            return "";

        return nValue.getNodeValue();
    }

    public static class Builder{
        private String schoolHost = DEFAULT_SCHOOL_HOST;

        public Builder setSchoolHost(String schoolHost){
            this.schoolHost = schoolHost;
            return this;
        }
    }
}
