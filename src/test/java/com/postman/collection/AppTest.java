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
        assertTrue(pmcTest.validate());
        }
        catch(Exception e)
        {
            e.printStackTrace();
            assertTrue(false);
        }

        
        




    }

    @Test
    public void shouldCreateScripts() {
        
        PostmanCollection pmcTest = PostmanCollection.PMCFactory();
        PostmanItem folder;
        PostmanItem request;
        PostmanScript script;
        PostmanEvent event;
        PostmanRequest req;
        PostmanBody body;

        try {
            pmcTest.setName("TEST Scripts");
            folder = new PostmanItem("Scripts");
            script = new PostmanScript("text/javascript","//PRE-REQUEST this is some source code for the folder");
            event = new PostmanEvent(enumEventType.PRE_REQUEST, script);
            folder.setEvent(event);
            script = new PostmanScript("text/javascript","//TEST this is some source code for the folder");
            event = new PostmanEvent(enumEventType.TEST, script);
            folder.setEvent(event);
            pmcTest.addItem(folder);

            req = new PostmanRequest(enumHTTPRequestMethod.GET, "https:/postman-echo.com/post?foo=bar");
            script = new PostmanScript("text/javascript","//PRE-REQUEST this is some source code for the request");
            request = new PostmanItem("TEST Request with Scripts");
            request.setRequest(req);
            event = new PostmanEvent(enumEventType.PRE_REQUEST, script);
            request.setEvent(event);
            script = new PostmanScript("text/javascript","//TEST this is some source code for the request");
            event = new PostmanEvent(enumEventType.TEST, script);
            request.setEvent(event);
            folder.addItem(request);

            script = new PostmanScript("text/javascript","//TEST this is some source code for the collection");
            event = new PostmanEvent(enumEventType.TEST, script);
            pmcTest.setEvent(event);
            script = new PostmanScript("text/javascript","//PRE-REQUEST this is some source code for the collection");
            event = new PostmanEvent(enumEventType.PRE_REQUEST, script);
            pmcTest.setEvent(event);
            
            

            

            //assertTrue(pmcTest.validate());
            pmcTest.writeToFile(filePath + "/test-output/TEST-constructed-scripts.postman_collection.json");
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
            pmcTest.setName("TEST Cat-Weather ");
            boolean worked = pmcTest.validate();
            ValidationMessage[] msgs = pmcTest.getValidationMessages();
            for(int i = 0; i < msgs.length; i++)
            {
                //System.out.println(msgs[i].getMessage());
            }
            pmcTest.setName("TEST Cat-Weather");
            pmcTest.writeToFile(filePath + "/test-output/TEST-cat-weather.postman_collection.json");
            assertTrue(worked);
            //System.out.println("done");


        }

        @Test
        public void testConstructedBodies() throws Exception {
            
        PostmanCollection pmcTest = PostmanCollection.PMCFactory();
        pmcTest.setName("TEST Constructed Body with Responses");
        PostmanBody body;
        PostmanRequest req;
        PostmanResponse resp;
        

        body = new PostmanBody(enumRequestBodyMode.URLENCODED);
        body.setFormdata("x-field-1", "value 1", "This is value 1");
        body.setFormdata("x-field-2", "value 2", "This is value 2");
        req = new PostmanRequest(enumHTTPRequestMethod.POST, "https://postman-echo.com/post");
        req.setBody(body);
        resp = new PostmanResponse("NORMAL Urlencoded", req, "OK", 200, "this is the expected response body");
        pmcTest.addRequest(req, "URLEncoded body", resp);
        

        body = new PostmanBody(enumRequestBodyMode.TEXT);
        body.setRaw("This is some plain text");
        req = new PostmanRequest(enumHTTPRequestMethod.POST, "https://postman-echo.com/post");
        req.setBody(body);
        resp = new PostmanResponse("NORMAL Plaintext", req, "OK", 200, "this is the expected response body");
        pmcTest.addRequest(req, "Plaintext body", resp);
                

        body = new PostmanBody(enumRequestBodyMode.FORMDATA);
        body.setFormdata("field-1", "value 1", "This is value 1");
        body.setFormdata("field-2", "value 2", "This is value 2");
        req = new PostmanRequest(enumHTTPRequestMethod.POST, "https://postman-echo.com/post");
        req.setBody(body);
        resp = new PostmanResponse("NORMAL Formdata", req, "OK", 200, "this is the expected response body");
        pmcTest.addRequest(req, "Formdata body", resp);
                

        body = new PostmanBody(enumRequestBodyMode.RAW, "{\"thing\":\"value\"}",enumRawBodyLanguage.JSON);
        req = new PostmanRequest(enumHTTPRequestMethod.POST, "https://postman-echo.com/post");
        req.setBody(body);
        resp = new PostmanResponse("NORMAL JSON", req , "OK", 200, "this is the expected response body");
        pmcTest.addRequest(req, "JSON body",resp);

        body = new PostmanBody(enumRequestBodyMode.RAW, "pm.test(\"Status code is 200\", function () {\n    pm.response.to.have.status(200);\n});\npm.test(\"Response time is less than 800ms\", function () {\n    pm.expect(pm.response.responseTime).to.be.below(800);\n});",enumRawBodyLanguage.JAVASCRIPT);
        req = new PostmanRequest(enumHTTPRequestMethod.POST, "https://postman-echo.com/post");
        req.setBody(body);
        resp = new PostmanResponse("NORMAL Javascript", req , "OK", 200, "this is the expected response body");
        pmcTest.addRequest(req, "Javascript body",resp);
        


        body = new PostmanBody(enumRequestBodyMode.RAW, "{<html><body><p>This is some html</p</body></html>}",enumRawBodyLanguage.HTML);
        req = new PostmanRequest(enumHTTPRequestMethod.POST, "https://postman-echo.com/post");
        req.setBody(body);
        resp = new PostmanResponse("NORMAL HTML", req, "OK", 200, "this is the expected response body");
        pmcTest.addRequest(req, "HTML body", resp);
        


        body = new PostmanBody(enumRequestBodyMode.RAW, "{<xml><body><p>This is some XML</p</body></xml>}",enumRawBodyLanguage.XML);
        req = new PostmanRequest(enumHTTPRequestMethod.POST, "https://postman-echo.com/post");
        req.setBody(body);
        resp = new PostmanResponse("NORMAL XML", req, "OK", 200, "this is the expected response body");
        pmcTest.addRequest(req, "XML body", resp);
        


        String strGraphQL = "{ \n            launchesPast(limit: 10) {\n              mission_name\n              launch_date_local\n              launch_site {\n                site_name_long\n              }\n              links {\n                article_link\n                video_link\n              }\n              rocket {\n                rocket_name\n              }\n            }\n          }";
        String strVars = "{\"limit\":2}";
        body = new PostmanBody(enumRequestBodyMode.GRAPHQL, strGraphQL,enumRawBodyLanguage.GRAPHQL);
        body.setGraphql(strGraphQL, strVars);
        req = new PostmanRequest(enumHTTPRequestMethod.POST, "https://postman-echo.com/post");
        req.setBody(body);
        resp = new PostmanResponse("NORMAL GrapqhQL", req, "OK", 200, "this is the expected response body");
        pmcTest.addRequest(req, "GraphQL body", resp);
        
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

