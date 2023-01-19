package com.postman.collection;

import static org.junit.Assert.assertTrue;


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
    public void shouldCreateRequestQueries() {
        try {
        
        pmcTest  = PostmanCollection.PMCFactory();
        PostmanRequest newReq = new PostmanRequest(enumHTTPRequestMethod.GET, "https://postman-echo.com/post");
        newReq.getUrl().addQuery("foo","bar");
        pmcTest.addRequest(newReq,"Get Foo Bar");
        pmcTest.setName("TEST Constructed Queries");
        pmcTest.writeToFile(filePath + "/test-output/TEST-constructed-queries.postman_collection.json");
        }
        catch(Exception e)
        {
            e.printStackTrace();
            assertTrue(false);
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
            try {
    
            
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
            pmcTest.setName("TEST Construct URLs");
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
        }
        catch(Exception e)
        {
            e.printStackTrace();
            assertTrue(false);
        }
    
        /*
        requests[6].addVariable(new PostmanVariable("path1", "path-value"));
        requests[7].addVariable(new PostmanVariable("path1", "path-value"));
        requests[8].addVariable(new PostmanVariable("path1", "path-value"));
        */
        
        try {
            
        pmcTest.writeToFile(filePath +"/test-output/TEST-construct-urls.postman_collection.json");
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
                pmcTest.setName("TEST Cat Fact");
                assertTrue(pmcTest.validate());
                pmcTest.writeToFile(filePath + "/test-output/TEST-example-catfact.postman_collection.json");
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
                    //System.out.println("Validation [" + i+"]: " + pmcTest.getValidationMessages()[i]);
                }
                assertTrue(pmcTest.getValidationMessages().length == 0);
                pmcTest.setName("TEST body-test-compare");
                pmcTest.writeToFile(filePath + "/test-output/TEST-body-test.postman_collection.json");
            }
            catch(Exception e)
             {
                e.printStackTrace();
             }
             
            
        }

        public void testBuildAuths() throws Exception {
            pmcTest.setName("TEST Auth");
            PostmanAuth auth;
            PostmanRequest req;
    
            auth = new PostmanAuth(enumAuthType.AKAMAI);
            auth.setAuthElement("headersToSign", "x-api-key");
            auth.setAuthElement("baseURL", "https://akamai-base.com");
            auth.setAuthElement("timestamp", "akamaiTimestamp");
            auth.setAuthElement("nonce", "akamaiNonce");
            auth.setAuthElement("clientSecret","akamaiClientSecret");
            auth.setAuthElement("clientToken", "akamaiClientToken");
            auth.setAuthElement("accessToken", "akamaiToken");
            
             req = new PostmanRequest(enumHTTPRequestMethod.GET, "https://postman-echo.com/post");
            req.setAuth(auth);
            pmcTest.addRequest(req, "AKAMAI request");
    
            
             auth = new PostmanAuth(enumAuthType.APIKEY);
            auth.setAuthElement("key", "API-KEY");
            auth.setAuthElement("value", "x-api-key");
            auth.setAuthElement("in","query");
             req = new PostmanRequest(enumHTTPRequestMethod.GET, "https://postman-echo.com/post");
            req.setAuth(auth);
            pmcTest.addRequest(req, "APIKEY request");
    
                  
            auth = new PostmanAuth(enumAuthType.AWS);
            auth.setAuthElement("sessionToken","awsSessiontoken");
            auth.setAuthElement("service","awsServiceName");
            auth.setAuthElement("secretKey","aswSecretKey");
            auth.setAuthElement("accessKey","awsAccessKey");
            auth.setAuthElement("addAuthDataToQuery","false");
             req = new PostmanRequest(enumHTTPRequestMethod.GET, "https://postman-echo.com/post");
            req.setAuth(auth);
            pmcTest.addRequest(req, "AWS request");
            
            auth = new PostmanAuth(enumAuthType.BEARER);
            auth.setAuthElement("key", "API-KEY");
            auth.setAuthElement("value", "x-api-key");
            req = new PostmanRequest(enumHTTPRequestMethod.GET, "https://postman-echo.com/post");
            req.setAuth(auth);
            pmcTest.addRequest(req, "BEARER request");
            System.out.println("Valid: " + pmcTest.validate());
            
    
            auth = new PostmanAuth(enumAuthType.BASIC);
            auth.setAuthElement("password","fakePassword");
            auth.setAuthElement("username","fakeusername");
            req = new PostmanRequest(enumHTTPRequestMethod.GET, "https://postman-echo.com/post");
            req.setAuth(auth);
            pmcTest.addRequest(req, "BASIC request");
            System.out.println("Valid: " + pmcTest.validate());
            
            
            auth = new PostmanAuth(enumAuthType.DIGEST);
            auth.setAuthElement("opaque","OpaqueString");
            auth.setAuthElement("clientNonce","2020202");
            auth.setAuthElement("nonceCount","1010101");
            auth.setAuthElement("qop","digest-qop");
            auth.setAuthElement("algorithim","SHA-256");
            auth.setAuthElement("nonce","digestNonce");
            auth.setAuthElement("realm","digest@test.com");
            auth.setAuthElement("password","digestPassword");
            req = new PostmanRequest(enumHTTPRequestMethod.GET, "https://postman-echo.com/post");
            req.setAuth(auth);
            pmcTest.addRequest(req, "DIGEST request");
    
            auth = new PostmanAuth(enumAuthType.HAWK);
            auth.setAuthElement("includePayloadHash","true");
            auth.setAuthElement("timestamp","hawkTimestamp");
            auth.setAuthElement("delegation","hawk-dlg");
            auth.setAuthElement("app","HawkApp");
            auth.setAuthElement("extraData","Hawk-ext");
            auth.setAuthElement("nonce","hawkNonce");
            auth.setAuthElement("user","HawkUser");
            auth.setAuthElement("authKey", "HawkAuthKey");
            auth.setAuthElement("algorithim", "sha256");
            req = new PostmanRequest(enumHTTPRequestMethod.GET, "https://postman-echo.com/post");
            req.setAuth(auth);
            pmcTest.addRequest(req, "HAWK request");
    
            auth = new PostmanAuth(enumAuthType.OAUTH1);
            auth.setAuthElement(new PostmanVariable("addEmptyParamsToSign","true", null, "boolean"));
            auth.setAuthElement(new PostmanVariable("includeBodyHash","true", null, "boolean"));
            auth.setAuthElement("realm","testoauth@test.com");
            auth.setAuthElement("nonce","oauthNonce");
            auth.setAuthElement("timestamp","oauthTimestamp");
            auth.setAuthElement("verifier","oauthVerifier");
            auth.setAuthElement("callback","https:/callback.url");
            auth.setAuthElement("tokenSecret","OAuthTokenSecret");
            auth.setAuthElement("token","oauthToken");
            auth.setAuthElement("consumerSecret","oauthConsumerSecret");
            auth.setAuthElement("consumerKey","oauthConsumerKey");
            auth.setAuthElement("signatureMethod","HMAC-SHA1");
            auth.setAuthElement("version","1.0");
            auth.setAuthElement(new PostmanVariable("addParamsToHeader","false",null,"boolean"));
            req = new PostmanRequest(enumHTTPRequestMethod.GET, "https://postman-echo.com/post");
            req.setAuth(auth);
            pmcTest.addRequest(req, "OAUTH1 request");
    
            auth = new PostmanAuth(enumAuthType.OAUTH2);
            auth.setAuthElement("grant_type","authorization_code");
            auth.setAuthElement("tokenName", "Oauth2TokenName");
            auth.setAuthElement("tokenType","");
            auth.setAuthElement("accessToken","oauth2AccessToken");
            auth.setAuthElement("addTokenTo","header");
            req = new PostmanRequest(enumHTTPRequestMethod.GET, "https://postman-echo.com/post");
            req.setAuth(auth);
            pmcTest.addRequest(req, "OAUTH2 request");
    
            auth = new PostmanAuth(enumAuthType.NTLM);
            auth.setAuthElement("workstation","NTMLWorkstation");
            auth.setAuthElement("domain","NTLMDomain");
            auth.setAuthElement("password","NTLMPassword");
            auth.setAuthElement("username","NTLMUsername");
            req = new PostmanRequest(enumHTTPRequestMethod.GET, "https://postman-echo.com/post");
            req.setAuth(auth);
            pmcTest.addRequest(req, "NTLM request");
            auth.getAuthTypeAsString();
           
            assertTrue(pmcTest.validate());
            pmcTest.writeToFile(filePath + "/test-output/TEST-auth.postman_collection.json");
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
                //System.out.println(msgs[i].getMessage());
            }
            pmcTest.setName("TEST Cat-Weather");
            pmcTest.writeToFile(filePath + "/test-output/TEST cat-weather.postman_collection.json");
            assertTrue(worked);
            //System.out.println("done");


        }

        @Test
        public void testConstructedBodies() throws Exception {
            
        PostmanCollection pmcTest = PostmanCollection.PMCFactory();
        pmcTest.setName("TEST Constructed Body with Responses");
        

        PostmanBody byUrlencoded = new PostmanBody(enumRequestBodyMode.URLENCODED);
        byUrlencoded.setFormdata("x-field-1", "value 1", "This is value 1");
        byUrlencoded.setFormdata("x-field-2", "value 2", "This is value 2");
        PostmanRequest rqUrlencoded = new PostmanRequest(enumHTTPRequestMethod.POST, "https://postman-echo.com/post");
        rqUrlencoded.setBody(byUrlencoded);
        PostmanResponse resp = new PostmanResponse("NORMAL Urlencoded", rqUrlencoded, "OK", 200, "this is the expected response body");
        pmcTest.addRequest(rqUrlencoded, "URLEncoded body", resp);
        

        PostmanBody byPlainText = new PostmanBody(enumRequestBodyMode.TEXT);
        byPlainText.setRaw("This is some plain text");
        PostmanRequest rqPlainText = new PostmanRequest(enumHTTPRequestMethod.POST, "https://postman-echo.com/post");
        rqPlainText.setBody(byPlainText);
        resp = new PostmanResponse("NORMAL Plaintext", rqPlainText, "OK", 200, "this is the expected response body");
        
        pmcTest.addRequest(rqPlainText, "Plaintext body", resp);
                

        PostmanBody byFormdata = new PostmanBody(enumRequestBodyMode.FORMDATA);
        byFormdata.setFormdata("field-1", "value 1", "This is value 1");
        byFormdata.setFormdata("field-2", "value 2", "This is value 2");
        PostmanRequest rqFormData = new PostmanRequest(enumHTTPRequestMethod.POST, "https://postman-echo.com/post");
        rqFormData.setBody(byFormdata);
        
        resp = new PostmanResponse("NORMAL Formdata", rqFormData, "OK", 200, "this is the expected response body");
                pmcTest.addRequest(rqFormData, "Formdata body", resp);
                

        PostmanBody byJsondata = new PostmanBody(enumRequestBodyMode.RAW, "{\"thing\":\"value\"}",enumRawBodyLanguage.JSON);
        PostmanRequest rqJsondata = new PostmanRequest(enumHTTPRequestMethod.POST, "https://postman-echo.com/post");
        rqJsondata.setBody(byJsondata);
        

        resp = new PostmanResponse("NORMAL JSON", rqJsondata , "OK", 200, "this is the expected response body");
        pmcTest.addRequest(rqJsondata, "JSON body",resp);
        


        PostmanBody byHTML = new PostmanBody(enumRequestBodyMode.RAW, "{<html><body><p>This is some html</p</body></html>}",enumRawBodyLanguage.HTML);
        PostmanRequest rqHTML = new PostmanRequest(enumHTTPRequestMethod.POST, "https://postman-echo.com/post");
        rqHTML.setBody(byHTML);
        resp = new PostmanResponse("NORMAL HTML", rqHTML, "OK", 200, "this is the expected response body");
        pmcTest.addRequest(rqHTML, "HTML body", resp);
        


        PostmanBody byXML = new PostmanBody(enumRequestBodyMode.RAW, "{<xml><body><p>This is some XML</p</body></xml>}",enumRawBodyLanguage.XML);
        PostmanRequest rqXML = new PostmanRequest(enumHTTPRequestMethod.POST, "https://postman-echo.com/post");
        rqXML.setBody(byXML);
        resp = new PostmanResponse("NORMAL XML", rqXML, "OK", 200, "this is the expected response body");
        pmcTest.addRequest(rqXML, "XML body", resp);
        


        String strGraphQL = "{ \n            launchesPast(limit: 10) {\n              mission_name\n              launch_date_local\n              launch_site {\n                site_name_long\n              }\n              links {\n                article_link\n                video_link\n              }\n              rocket {\n                rocket_name\n              }\n            }\n          }";
        String strVars = "{\"limit\":2}";
        PostmanBody byGraphQL = new PostmanBody(enumRequestBodyMode.GRAPHQL, strGraphQL,enumRawBodyLanguage.GRAPHQL);
        byGraphQL.setGraphql(strGraphQL, strVars);
        PostmanRequest rqGraphQL = new PostmanRequest(enumHTTPRequestMethod.POST, "https://postman-echo.com/post");
        rqGraphQL.setBody(byGraphQL);
        resp = new PostmanResponse("NORMAL GrapqhQL", rqGraphQL, "OK", 200, "this is the expected response body");
        pmcTest.addRequest(rqGraphQL, "GraphQL body", resp);
        
        try {
            pmcTest.writeToFile(filePath + "/test-output/TEST-bodies-with-responses.postman_collection.json");

            
        }
        catch(Exception e)
        {
            e.printStackTrace();
            assertTrue(false);
        }
        boolean valid = pmcTest.validate();
        if(!valid) {
            for(int i = 0; i < pmcTest.getValidationMessages().length;i++) {
                //System.out.println("Validation message [" + i + "]: " + pmcTest.getValidationMessages()[i].getMessage());
            }
        }
        assertTrue(valid);

    }
}

