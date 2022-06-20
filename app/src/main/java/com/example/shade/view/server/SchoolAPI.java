package com.example.shade.view.server;

import com.example.shade.model.School;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

public class SchoolAPI{

    private static final String DEFAULT_SCHOOL_HOST = "https://open.neis.go.kr/hub/schoolInfo?KEY=b4186d1e9bdf427aaf7e64659a56739c&Type=json&SCHUL_NM=";

    private String schoolHost = DEFAULT_SCHOOL_HOST;
    ArrayList<School> response = new ArrayList<>();

    public SchoolAPI() {

    }

    public ArrayList<School> getSchoolByName(String name) throws ParseException, ParserConfigurationException, IOException, SAXException, XmlPullParserException {

        String enc_url = URLEncoder.encode( name, "UTF-8");
        final String urlSchool = schoolHost + enc_url;
        URL url = new URL(urlSchool);

        InputStream is = url.openStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader rd = new BufferedReader(isr);

        StringBuffer buffer = new StringBuffer();
        String line = rd.readLine();
        while(line != null){
            buffer.append(line + "\n");
           // System.out.println(line);

            line = rd.readLine();
        }

        String jsonData = buffer.toString();
        JSONArray schoolInfo = null;
        JSONObject row = null;

        try {
            JSONObject obj = new JSONObject(jsonData);

            schoolInfo = (JSONArray) obj.get("schoolInfo");
            JSONObject temp = schoolInfo.getJSONObject(1);

            JSONArray school_row = (JSONArray) temp.get("row");

            for(int i = 0; i < school_row.length(); i++){
                JSONObject school = school_row.getJSONObject(i);
                String sc_name = school.getString("SCHUL_NM");
                String sc_address = school.getString("ORG_RDNMA");

                response.add(new School(sc_name, sc_address));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return (response);

    }
}
