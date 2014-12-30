package torontourism.android.dps.torontourism;

/**
 * Created by Kabeer on 12/24/2014.
 */


import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by Kabeer on 11/6/2014.
 */
public class TorontourismParser {

    static final String FesEventURL = "http://wx.toronto.ca/festevents.nsf/tpaview?readviewentries";

    //constructor
    public TorontourismParser()
    {

    }

    //open a HTTP Connection

    public String getXMLfromURL(String urlString)
    {
        String xmlfile = null;
        try {
            DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(urlString);

            HttpResponse httpResponse = defaultHttpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            xmlfile = EntityUtils.toString(httpEntity);

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return xmlfile;
    }

    public Document getElements(String xml)
    {
        Document document = null;
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

            InputSource inputSource = new InputSource();
            inputSource.setCharacterStream(new StringReader(xml));
            document = documentBuilder.parse(inputSource);
            document.getDocumentElement().normalize();


        } catch (Exception e) {
            Log.e("Error in Parser file", e.getMessage());
            e.printStackTrace();
        }
        return document;
    }


    /**
     * creates a list of entrydata atributes, then takes the name value of the Entry Data
     * goes through each entrydata looking for the matching name, once found, creates a list
     * of text atributes (should be only one), takes the text attribute element and gets the
     * child text from it.
     * @param element - elements to be searched for data
     * @param string - name value of the element the data would need to be taken from
     * @return string - text value of the data from the found element
     */
    public String getValue(Element element, String string)
    {

        NodeList nodeList = element.getElementsByTagName("entrydata");
        String returnValue = "----------";
        for(int i = 0; i < nodeList.getLength(); i++){
            Element elementEntry = (Element)nodeList.item(i);

            if(string.equals(elementEntry.getAttributes().getNamedItem("name").getNodeValue())){

                NodeList textNodes = elementEntry.getElementsByTagName("text");
                Node textNode = textNodes.item(0);
                returnValue = textNode.getFirstChild().getTextContent();

                return returnValue;
            }
        }

        return returnValue;
    }


}