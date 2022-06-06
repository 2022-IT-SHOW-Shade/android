package com.example.shade;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class SchoolAPI{

    private static final String DEFAULT_SCHOOL_HOST = "https://open.neis.go.kr/hub/schoolInfo?KEY=b4186d1e9bdf427aaf7e64659a56739c&Type=json&SCHUL_NM=";

    private String schoolHost = DEFAULT_SCHOOL_HOST;

    public SchoolAPI(String schoolHost) {
        this.schoolHost = schoolHost;
    }

    public SchoolAPI() {

    }


    public List<School> getSchoolByName(String name) throws ParseException, ParserConfigurationException, IOException, SAXException, XmlPullParserException {

        String enc_url = URLEncoder.encode( name, "UTF-8");
        final String urlSchool = schoolHost + enc_url;
        URL url = new URL(urlSchool);

        List<School> response = new ArrayList<>();

        InputStream is = url.openStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader rd = new BufferedReader(isr);

        StringBuffer buffer = new StringBuffer();
        String line = rd.readLine();
        while(line != null){
            buffer.append(line + "\n");
            System.out.println(line);

            line = rd.readLine();
        }


/*
        for(int i = 0, length = nList.getLength(); i < length; i++){
            Node node = nList.item(i);

            if(node.getNodeType() == Node.ELEMENT_NODE){
                Element element = (Element) node;
                response.add(new School(getTagValue("ATPT_OFCDC_SC_CODE", element), getTagValue("SD_SCHUL_CODE", element),
                        getTagValue("LCTN_SC_NM", element), getTagValue("SCHUL_NM", element)));
                System.out.println(response.get(0).getName());
            }
        }*/

        //System.out.println(response.get(0).getName());

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
