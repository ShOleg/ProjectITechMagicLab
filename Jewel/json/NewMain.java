/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Jewel.json;

import java.io.Writer;

/**
 *
 * @author ShOleg
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        //XML.toJSONObject(string);
        System.out.println(XML.toJSONObject("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                + "<note>\n"
                + "  <from>Jani</from>\n"
                + "  <to>Tove</to>\n"
                + "  <message>Remember me this weekend</message>\n"
                + "</note>").toString());

        /*String text = "{\n"
                + "     \"firstName\": \"John\", \"lastName\": \"Smith\", \"age\": 25,\n"
                + "     \"address\" : {\n"
                + "         \"streetAddress\": \"21 2nd Street\",\n"
                + "         \"city\": \"New York\",\n"
                + "         \"state\": \"NY\",\n"
                + "         \"postalCode\": \"10021\"\n"
                + "     },\n"
                + "     \"phoneNumber\": [\n"
                + "         { \"type\": \"home\", \"number\": \"212 555-1234\" },\n"
                + "         { \"type\": \"fax\", \"number\": \"646 555-4567\" }\n"
                + "     ]\n"
                + " }";

        Jewel.json.JSONObject obj = new Jewel.json.JSONObject(text);
        System.out.println(Jewel.json.XML.toString(obj));
        */
    }

}
