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
    String resourcePath = "/src/main/resources/com/postman/collection";
    PostmanCollection pmcTest = null;
    String collectionOutputPath;

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
        collectionOutputPath = filePath + "/test-output/TEST-constructed-queries.postman_collection.json";
        pmcTest  = PostmanCollection.PMCFactory();
        PostmanRequest newReq = new PostmanRequest(enumHTTPRequestMethod.GET, "https://postman-echo.com/post");
        newReq.getUrl().addQuery("foo","bar");
        pmcTest.addRequest(newReq,"Get Foo Bar");
        pmcTest.setName("TEST Constructed Queries");
        boolean valid = pmcTest.validate();
        collectionOutputPath = filePath + "/test-output/TEST-" + pmcTest.getName();
        pmcTest.setDescription("TEST-" + new Throwable().getStackTrace()[0].getMethodName());
        pmcTest.writeToFile(collectionOutputPath);
        printValidationMessages(pmcTest.getValidationMessages(), new Throwable().getStackTrace()[0].getMethodName());
        assertTrue(valid);
        assertTrue(new File(collectionOutputPath).exists());
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
        
        PostmanEvent event;
        PostmanRequest req;
        

        try {
            pmcTest.setName("TEST Scripts");
            folder = new PostmanItem("Scripts");
            
            event = new PostmanEvent(enumEventType.PRE_REQUEST, "//PRE-REQUEST this is some source code for the folder");
            folder.setEvent(event);
            event = new PostmanEvent(enumEventType.TEST, "//TEST this is some source code for the folder");
            folder.setEvent(event);
            pmcTest.addItem(folder);

            req = new PostmanRequest(enumHTTPRequestMethod.GET, "https:/postman-echo.com/post?foo=bar");
            
            request = new PostmanItem("TEST Request with Scripts");
            request.setRequest(req);
            event = new PostmanEvent(enumEventType.PRE_REQUEST, "//PRE-REQUEST this is some source code for the request");
            request.setEvent(event);
            
            event = new PostmanEvent(enumEventType.TEST, "//TEST this is some source code for the request");
            request.setEvent(event);
            folder.addItem(request);

            
            event = new PostmanEvent(enumEventType.TEST, "//TEST this is some source code for the collection");
            pmcTest.setEvent(event);
            event = new PostmanEvent(enumEventType.PRE_REQUEST, "//PRE-REQUEST this is some source code for the collection");
            pmcTest.setEvent(event);

            boolean valid = pmcTest.validate();
            collectionOutputPath = filePath + "/test-output/TEST-" + pmcTest.getName();
            pmcTest.setDescription("TEST-" + new Throwable().getStackTrace()[0].getMethodName());
            pmcTest.writeToFile(collectionOutputPath);
            printValidationMessages(pmcTest.getValidationMessages(), new Throwable().getStackTrace()[0].getMethodName());
            assertTrue(valid);
            assertTrue(new File(collectionOutputPath).exists());
            
        }
        catch(Exception e)
        {
            e.printStackTrace();
            assertTrue(false);
        }


    }

    public void printValidationMessages(ArrayList<ValidationMessage> msgs, String methodName) {
        if(msgs != null && msgs.size() > 0)
        {
            for(ValidationMessage msg: msgs) {
                System.out.println("VALIDATION ERROR (" + methodName + "): " + msg.getMessage());
            }
        }
    }

    @Test
    public void shouldImportCollection()
    {
        filePath = new java.io.File("").getAbsolutePath();
        
        try {
            pmcTest  = PostmanCollection.PMCFactory(new File(filePath + "/src/main/resources/com/postman/collection/example-catfact.postman_collection.json"));
            boolean valid = pmcTest.validate();
        collectionOutputPath = filePath + "/test-output/TEST-" + pmcTest.getName();
        pmcTest.setDescription("TEST-" + new Throwable().getStackTrace()[0].getMethodName());
        pmcTest.writeToFile(collectionOutputPath);
        printValidationMessages(pmcTest.getValidationMessages(), new Throwable().getStackTrace()[0].getMethodName());
        assertTrue(valid);
        assertTrue(new File(collectionOutputPath).exists());
            
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
                    boolean valid = pmcTest.validate();
                    collectionOutputPath = filePath + "/test-output/TEST-" + pmcTest.getName();
                    pmcTest.setDescription("TEST-" + new Throwable().getStackTrace()[0].getMethodName());
                    pmcTest.writeToFile(collectionOutputPath);
                    printValidationMessages(pmcTest.getValidationMessages(), new Throwable().getStackTrace()[0].getMethodName());
                    assertTrue(valid);
                    assertTrue(new File(collectionOutputPath).exists());
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
            
        
        
        
        boolean valid = pmcTest.validate();
        collectionOutputPath = filePath + "/test-output/TEST-" + pmcTest.getName();
        pmcTest.setDescription("TEST-" + new Throwable().getStackTrace()[0].getMethodName());
        pmcTest.writeToFile(collectionOutputPath);
        printValidationMessages(pmcTest.getValidationMessages(), new Throwable().getStackTrace()[0].getMethodName());
        assertTrue(valid);
        assertTrue(new File(collectionOutputPath).exists());
        
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
                pmcTest = PostmanCollection.PMCFactory(new File( filePath + "/src/main/resources/com/postman/collection/example-catfact.postman_collection.json"));
                pmcTest.setName("TEST Cat Fact");
                boolean valid = pmcTest.validate();
                collectionOutputPath = filePath + "/test-output/TEST-" + pmcTest.getName();
                pmcTest.setDescription("TEST-" + new Throwable().getStackTrace()[0].getMethodName());
                pmcTest.writeToFile(collectionOutputPath);
                printValidationMessages(pmcTest.getValidationMessages(), new Throwable().getStackTrace()[0].getMethodName());
                assertTrue(valid);
                assertTrue(new File(collectionOutputPath).exists());
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
                pmcTest = PostmanCollection.PMCFactory(new File(filePath + "/src/main/resources/com/postman/collection/body-test.postman_collection.json"));
                boolean valid = pmcTest.validate();
        
        printValidationMessages(pmcTest.getValidationMessages(), new Throwable().getStackTrace()[0].getMethodName());
        
                pmcTest.setName("TEST body-test-compare");
                pmcTest.writeToFile(filePath + "/test-output/TEST-body-test.postman_collection.json");
                assertTrue(valid);        
            }
            catch(Exception e)
             {
                e.printStackTrace();
             }
             
            
        }
        @Test
        public void testAuthIngestion() throws Exception {
            pmcTest = PostmanCollection.PMCFactory(new File(filePath + resourcePath + "/auth.postman_collection.json"));
            boolean valid = pmcTest.validate();
            collectionOutputPath = filePath + "/test-output/TEST-" + pmcTest.getName();
            pmcTest.setDescription("TEST-" + new Throwable().getStackTrace()[0].getMethodName());
            pmcTest.writeToFile(collectionOutputPath);
            printValidationMessages(pmcTest.getValidationMessages(), new Throwable().getStackTrace()[0].getMethodName());
            assertTrue(valid);
            assertTrue(new File(collectionOutputPath).exists());
        }

        @Test
        public void testLargeBodyIngestion() throws Exception {
            pmcTest = PostmanCollection.PMCFactory(new File(filePath + resourcePath + "/test-collection.postman_collection.json"));
            pmcTest.setName("TEST large body");
            boolean valid = pmcTest.validate();
            collectionOutputPath = filePath + "/test-output/TEST-" + pmcTest.getName();
            pmcTest.setDescription("TEST-" + new Throwable().getStackTrace()[0].getMethodName());
            pmcTest.writeToFile(collectionOutputPath);
            printValidationMessages(pmcTest.getValidationMessages(), new Throwable().getStackTrace()[0].getMethodName());
            assertTrue(valid);
            assertTrue(new File(collectionOutputPath).exists());
        }

        @Test
        public void testBuildAuths() throws Exception {
            pmcTest = PostmanCollection.PMCFactory();
            pmcTest.setName("TEST Auth");
            PostmanAuth auth;
            PostmanRequest req;
            boolean valid;
    
            req = new PostmanRequest(enumHTTPRequestMethod.GET, "https://postman-echo.com/post");
            pmcTest.addRequest(req, "INHERIT request");
    

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
            
            
    
            auth = new PostmanAuth(enumAuthType.BASIC);
            auth.setAuthElement("password","fakePassword");
            auth.setAuthElement("username","fakeusername");
            req = new PostmanRequest(enumHTTPRequestMethod.GET, "https://postman-echo.com/post");
            req.setAuth(auth);
            pmcTest.addRequest(req, "BASIC request");
            
            
            
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

            pmcTest.setAuth(auth);
            
            pmcTest.writeToFile(filePath + "/test-output/TEST-auth.postman_collection.json");
            pmcTest.getAuth().getAuthElements().keySet().iterator().next();
            valid = pmcTest.validate();
            collectionOutputPath = filePath + "/test-output/TEST-" + pmcTest.getName();
            pmcTest.setDescription("TEST-" + new Throwable().getStackTrace()[0].getMethodName());
            pmcTest.writeToFile(collectionOutputPath);
            printValidationMessages(pmcTest.getValidationMessages(), new Throwable().getStackTrace()[0].getMethodName());
            assertTrue(valid);
            assertTrue(new File(collectionOutputPath).exists());
        }

        @Test 
        public void testAddVariables() throws Exception {
            pmcTest = PostmanCollection.PMCFactory();
        
            pmcTest.setName("TEST Constructed Variables");
            PostmanVariable var1 = new PostmanVariable("key 1", "value 1");
            PostmanVariable var2 = new PostmanVariable("key 2", "value 2");
            PostmanVariable var3 = new PostmanVariable("key 3", "value 3");
            pmcTest.addVariable(var1);
            pmcTest.addVariable(var2);
            pmcTest.addVariable(var3);
            boolean valid = pmcTest.validate();
            if(!valid)
            
            {
                ArrayList<ValidationMessage> msgs = pmcTest.getValidationMessages();
                
                //For some reason an empty collection, ie., one missing the 'item' element, doesn't validation, but still imports just fine
                //Not sure whether we want to include this exception in the actual implementation tho.  
                if(msgs.size() == 1 && msgs.get(0).getMessage().equals("$.item: is missing but it is required")) {
                    valid = true;
                }
                printValidationMessages(msgs, new Throwable().getStackTrace()[0].getMethodName());

            }
            collectionOutputPath = filePath + "/test-output/TEST-" + pmcTest.getName();
            pmcTest.setDescription("TEST-" + new Throwable().getStackTrace()[0].getMethodName());
            pmcTest.writeToFile(collectionOutputPath);
            printValidationMessages(pmcTest.getValidationMessages(), new Throwable().getStackTrace()[0].getMethodName());
            assertTrue(valid);
            assertTrue(new File(collectionOutputPath).exists());
            
        }
        @Test
        public void testIngestEvents() throws Exception {
            PostmanCollection pmcTest = PostmanCollection.PMCFactory(new File(filePath + resourcePath + "/example-cat-facts-with-tests.postman_collection.json"));
            boolean valid = pmcTest.validate();
            collectionOutputPath = filePath + "/test-output/TEST-" + pmcTest.getName();
            pmcTest.setDescription("TEST-" + new Throwable().getStackTrace()[0].getMethodName());
            pmcTest.writeToFile(collectionOutputPath);
            printValidationMessages(pmcTest.getValidationMessages(), new Throwable().getStackTrace()[0].getMethodName());
            assertTrue(valid);
            assertTrue(new File(collectionOutputPath).exists());
        }

        @Test
        public void testAddCollection() throws Exception {

            PostmanCollection pmcTest = PostmanCollection.PMCFactory(new File(filePath + "/src/main/resources/com/postman/collection/example-catfact.postman_collection.json"));
            PostmanCollection pmcTest2 = PostmanCollection.PMCFactory(new File(filePath + "/src/main/resources/com/postman/collection/example-weather.postman_collection.json"));
            pmcTest.addCollection(pmcTest2, true, true);
            pmcTest.setName("TEST Cat-Weather ");
            boolean valid = pmcTest.validate();
        
            collectionOutputPath = filePath + "/test-output/TEST-" + pmcTest.getName();
            pmcTest.setDescription("TEST-" + new Throwable().getStackTrace()[0].getMethodName());
            pmcTest.writeToFile(collectionOutputPath);
            printValidationMessages(pmcTest.getValidationMessages(), new Throwable().getStackTrace()[0].getMethodName());
            assertTrue(valid);
            assertTrue(new File(collectionOutputPath).exists());
            


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

        body = new PostmanBody(enumRequestBodyMode.FILE);
        body.setFile("8vhckkNqZ/jenkins-small.png");
        req = new PostmanRequest(enumHTTPRequestMethod.POST, "https://postman-echo.com/post");
        req.setBody(body);
        resp = new PostmanResponse("NORMAL Binary", req , "OK", 200, "this is the expected response body");
        pmcTest.addRequest(req, "Binary body",resp);
        


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
        collectionOutputPath = filePath + "/test-output/TEST-" + pmcTest.getName();
        pmcTest.setDescription("TEST-" + new Throwable().getStackTrace()[0].getMethodName());
        pmcTest.writeToFile(collectionOutputPath);
        printValidationMessages(pmcTest.getValidationMessages(), new Throwable().getStackTrace()[0].getMethodName());
        assertTrue(valid);
        assertTrue(new File(collectionOutputPath).exists());
        

    }
}
    
    


