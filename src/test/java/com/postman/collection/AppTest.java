package com.postman.collection;

import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeNoException;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.File;
import org.junit.Test;
import com.networknt.schema.ValidationMessage;


/**
 * Unit test for simple App.
 */
public class AppTest 
{
    String filePath = new java.io.File("").getAbsolutePath();
    PostmanCollection pmcTest = null;

    @Test
    public void clearOutput() {
        File output = new File(filePath + "/test-output");
        File currentFile;
        if(!output.exists())
        {
            output.mkdir();
            return;
        }
        String[] files = output.list();
        for(String s: files) {
            currentFile = new File(output.getPath(),s);
            currentFile.delete();
        }
    }

    @Test
    public void shouldImportCollection()
    {
        filePath = new java.io.File("").getAbsolutePath();
        
        try {
            pmcTest  = PostmanCollection.PMCFactory(filePath + "/src/main/resources/com/postman/collection/example-catfact.postman_collection.json");
            assertTrue(pmcTest.validate());
            
        }
        catch(Exception e) {
            e.printStackTrace();
            assertTrue(false);
        }
        
    }
        
        @Test
        public void shouldCreateURLs() {
            
            String filePath = new java.io.File("").getAbsolutePath();
            
            
            List<PostmanUrl> liUrls  = new ArrayList<PostmanUrl>(Arrays.asList(new PostmanUrl[0]));
            
    
            
            liUrls.add(new PostmanUrl("http://foo.com/bar/bat.json"));
            liUrls.add(new PostmanUrl("//foo.com/bar/bat.json"));
            liUrls.add(new PostmanUrl("{{baseUrl}}/foo.com/bar/bat.json"));
            liUrls.add(new PostmanUrl("http://foo.com/bar/bat.json?foo=1&bar=2"));
            liUrls.add(new PostmanUrl("http://foo.com/bar/bat.json?foo=1&bar="));
            liUrls.add(new PostmanUrl("{{baseUrl}}/foo.com/bar/bat.json?foo=1&bar="));
            liUrls.add(new PostmanUrl("{{baseUrl}}/foo.com/bar/:path1/bat.json?foo=1&bar="));
            liUrls.add(new PostmanUrl("{{baseUrl}}foo.com:8080/bar/:path1/bat.json?foo=1&bar="));  
            liUrls.add(new PostmanUrl("{{baseUrl}}/foo.com:8080/bar/:path1/bat.json?foo=1&bar=")); 
            liUrls.add(new PostmanUrl("https://foo.com:8080/bar/:path1/bat.json?foo=1&bar="));
            liUrls.add(new PostmanUrl("https://foo.com/bar/:path1/bat.json?foo=1&bar="));
    
            pmcTest = PostmanCollection.PMCFactory();
            pmcTest.setName("URL Test");
            for(int i = 0; i<liUrls.size();i++)
            {
                try {
                    pmcTest.addRequest(new PostmanRequest(enumHTTPRequestMethod.GET,liUrls.get(i)),"URL " + (i + 1));
                    assertTrue(pmcTest.validate());
                }
                catch(Exception e) {
                    e.printStackTrace();
                    assertTrue(false);
                }
                
            } 
    
        /*
        requests[6].addVariable(new PostmanVariable("path1", "path-value"));
        requests[7].addVariable(new PostmanVariable("path1", "path-value"));
        requests[8].addVariable(new PostmanVariable("path1", "path-value"));
        */
        
        try {
            
        pmcTest.writeToFile(filePath +"/test-output/shouldCreateURLs.json");
        pmcTest = PostmanCollection.PMCFactory(filePath + "/test-output/shouldCreateURLs.json");
        assertTrue(pmcTest.validate());
        
        }
        catch(Exception e)
        {
            e.printStackTrace();
            assertTrue(false);
        }

        
        

        }
        @Test
        public void shouldProduceIdenticalCollection() {
            
            try {
                pmcTest = PostmanCollection.PMCFactory(filePath + "/src/main/resources/com/postman/collection/example-catfact.postman_collection.json");
                assertTrue(pmcTest.validate());
                pmcTest.writeToFile(filePath + "/test-output/example-catfact-compare.postman_collection.json");
            }
            catch(Exception e) {
                e.printStackTrace();
                assertTrue(false);
            }
            
            
        }

        @Test
        public void testBodyImportExport() {
            String filePath = new java.io.File("").getAbsolutePath();
            try {
                pmcTest = PostmanCollection.PMCFactory(filePath + "/src/main/resources/com/postman/collection/body-test.postman_collection.json");
                pmcTest.validate();
                for(int i = 0; i < pmcTest.getValidationMessages().length; i++)
                {
                    System.out.println("Validation [" + i+"]: " + pmcTest.getValidationMessages()[i]);
                }
                assertTrue(pmcTest.getValidationMessages().length == 0);
                pmcTest.writeToFile(filePath + "/test-output/body-test-compare.postman_collection.json");
            }
            catch(Exception e)
             {
                e.printStackTrace();
             }
             
            
        }

        @Test
        public void testAddCollection() throws Exception {

            PostmanCollection pmcTest = PostmanCollection.PMCFactory(filePath + "/src/main/resources/com/postman/collection/example-catfact.postman_collection.json");
            PostmanCollection pmcTest2 = PostmanCollection.PMCFactory(filePath + "/src/main/resources/com/postman/collection/example-weather.postman_collection.json");
            pmcTest.addCollection(pmcTest2, true, true);
            boolean worked = pmcTest.validate();
            ValidationMessage[] msgs = pmcTest.getValidationMessages();
            for(int i = 0; i < msgs.length; i++)
            {
                System.out.println(msgs[i].getMessage());
            }
            pmcTest.writeToFile(filePath + "/test-output/cat-weather.postman_collection.json");
            assertTrue(worked);
            System.out.println("done");


        }
/*
        @Test
        public void testConstructedBodies() throws Exception {
            
            try
            {
            pmcTest = new PostmanCollection("Constructed Bodies");
        
            PostmanBody byUrlencoded = new PostmanBody(enumRequestBodyMode.URLENCODED);
        byUrlencoded.setFormdata("x-field-1", "value 1", "This is value 1");
        byUrlencoded.setFormdata("x-field-2", "value 2", "This is value 2");
        PostmanRequest rqUrlencoded = new PostmanRequest(enumHTTPRequestMethod.POST, "https://postman-echo.com/post");
        rqUrlencoded.setBody(byUrlencoded);
        PostmanResponse resp = new PostmanResponse("NORMAL Urlencoded", rqUrlencoded, "OK", 200, "this is the expected response body");
        pmcTest.addRequest(rqUrlencoded, "URLEncoded body");
        pmcTest.addResponse(reqKey, resp);

        PostmanBody byPlainText = new PostmanBody(enumRequestBodyMode.TEXT);
        byPlainText.setRaw("This is some plain text");
        PostmanRequest rqPlainText = new PostmanRequest(enumHTTPRequestMethod.POST, "https://postman-echo.com/post");
        rqPlainText.setBody(byPlainText);
        resp = new PostmanResponse("NORMAL Plaintext", rqPlainText, "OK", 200, "this is the expected response body");
        
                reqKey = pmcTest.addRequest(rqPlainText, "Plaintext body");
                pmcTest.addResponse(reqKey, resp);

        PostmanBody byFormdata = new PostmanBody(enumRequestBodyMode.FORMDATA);
        byFormdata.setFormdata("field-1", "value 1", "This is value 1");
        byFormdata.setFormdata("field-2", "value 2", "This is value 2");
        PostmanRequest rqFormData = new PostmanRequest(enumHTTPRequestMethod.POST, "https://postman-echo.com/post");
        rqFormData.setBody(byFormdata);
        
        resp = new PostmanResponse("NORMAL Formdata", rqFormData, "OK", 200, "this is the expected response body");
                pmcTest.addRequest(rqFormData, "Formdata body");
                pmcTest.addResponse(reqKey, resp);

        PostmanBody byJsondata = new PostmanBody(enumRequestBodyMode.RAW, "{\"thing\":\"value\"}",enumRawBodyLanguage.JSON);
        PostmanRequest rqJsondata = new PostmanRequest(enumHTTPRequestMethod.POST, "https://postman-echo.com/post");
        rqJsondata.setBody(byJsondata);
        

        resp = new PostmanResponse("NORMAL JSON", rqJsondata , "OK", 200, "this is the expected response body");
        reqKey = pmcTest.addRequest(rqJsondata, "JSON body");
        pmcTest.addResponse(reqKey, resp);


        PostmanBody byHTML = new PostmanBody(enumRequestBodyMode.RAW, "{<html><body><p>This is some html</p</body></html>}",enumRawBodyLanguage.HTML);
        PostmanRequest rqHTML = new PostmanRequest(enumHTTPRequestMethod.POST, "https://postman-echo.com/post");
        rqHTML.setBody(byHTML);
        resp = new PostmanResponse("NORMAL HTML", rqHTML, "OK", 200, "this is the expected response body");
        reqKey = pmcTest.addRequest(rqHTML, "HTML body");
        pmcTest.addResponse(reqKey, resp);


        PostmanBody byXML = new PostmanBody(enumRequestBodyMode.RAW, "{<xml><body><p>This is some XML</p</body></xml>}",enumRawBodyLanguage.XML);
        PostmanRequest rqXML = new PostmanRequest(enumHTTPRequestMethod.POST, "https://postman-echo.com/post");
        rqXML.setBody(byXML);
        resp = new PostmanResponse("NORMAL XML", rqXML, "OK", 200, "this is the expected response body");
        reqKey = pmcTest.addRequest(rqXML, "XML body");
        pmcTest.addResponse(reqKey, resp);


        String strGraphQL = "{ \n            launchesPast(limit: 10) {\n              mission_name\n              launch_date_local\n              launch_site {\n                site_name_long\n              }\n              links {\n                article_link\n                video_link\n              }\n              rocket {\n                rocket_name\n              }\n            }\n          }";

        PostmanBody byGraphQL = new PostmanBody(enumRequestBodyMode.GRAPHQL, strGraphQL,enumRawBodyLanguage.GRAPHQL);
        
        PostmanRequest rqGraphQL = new PostmanRequest(enumHTTPRequestMethod.POST, "https://postman-echo.com/post");
        rqGraphQL.setBody(byGraphQL);
        resp = new PostmanResponse("NORMAL GrapqhQL", rqGraphQL, "OK", 200, "this is the expected response body");
        reqKey = pmcTest.addRequest(rqGraphQL, "GraphQL body");
        pmcTest.addResponse(reqKey, resp);
        try {
            pmcTest.writeToFile(filePath + "/test-output/bodies-with-responses.postman_collection.json");
            
        }
        catch(Exception e)
        {
            e.printStackTrace();
            assertTrue(false);
        }
        

        assertTrue(pmcTest.validate());
            }
            catch(Exception e) {
                e.printStackTrace();
                assertTrue(false);
            }
        }
        */
    }

