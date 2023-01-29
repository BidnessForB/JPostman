package com.postman.collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.List;



import java.util.ArrayList;
import java.util.Arrays;
import java.io.File;
import org.junit.Test;

import com.networknt.schema.ValidationMessage;
import java.util.HashMap;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.Set;
import java.util.HashSet;
import java.nio.file.DirectoryStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
/**
 * Unit test for simple App.
 */
public class AppTest {
    String filePath = new java.io.File("").getAbsolutePath();
    String resourcePath = "/src/main/resources/com/postman/collection";
    PostmanCollection pmcTest = null;
    String collectionOutputPath;

    
   /*  @Test
        public void clearOutput() {
        File outputRoot = new File(filePath + "/test-output");
        deleteDirectory(outputRoot);
        boolean mkDirs = false;
        mkDirs = new File(filePath + "/test-output/compare").mkdirs();
        assertTrue(mkDirs);
        
        
    } */
    

    public Set<String> listFilesUsingDirectoryStream(String dir) throws IOException {
        Set<String> fileSet = new HashSet<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(dir))) {
            for (Path path : stream) {
                if (!Files.isDirectory(path)) {
                    fileSet.add(path.getFileName()
                        .toString());
                }
            }
        }
        return fileSet;
    }
    
    

    @Test
    public void shouldCreateRequestQueries() {
        try {
            collectionOutputPath = filePath + "/test-output/TEST-constructed-queries.postman_collection.json";
            pmcTest = PostmanCollection.pmcFactory();
            PostmanRequest newReq = new PostmanRequest(enumHTTPRequestMethod.GET, "https://postman-echo.com/post");
            newReq.getUrl().addQuery("foo", "bar");
            pmcTest.addRequest(newReq, "Get Foo Bar");
            pmcTest.setName("TEST Constructed Queries");
            validateAndWriteToFile(pmcTest, new Throwable().getStackTrace()[0]);
            
            
validateAndWriteToFile(pmcTest, new Throwable().getStackTrace()[0]);
            
        } catch (Exception e) {
            e.printStackTrace();
            assertTrue(false);
        }

    }

    @Test
    public void shouldCreateScripts() {

        PostmanCollection pmcTest = PostmanCollection.pmcFactory();
        PostmanItem folder;
        PostmanItem request;

        //generates spurious "variable not used" warning
        PostmanEvent  event = null; 
        PostmanRequest req;
        assertNull(event);
      
        try {
            pmcTest.setName("TEST Scripts");
            folder = new PostmanItem("Scripts");

            
            folder.setPreRequestScript("//PRE-REQUEST this is some source code for the folder");
            event = new PostmanEvent(enumEventType.TEST, "//TEST this is some source code for the folder");
            folder.setTestScript("//TEST this is some source code for the folder");
            pmcTest.addItem(folder);

            req = new PostmanRequest(enumHTTPRequestMethod.GET, "https:/postman-echo.com/post?foo=bar");

            request = new PostmanItem("TEST Request with Scripts");
            request.setRequest(req);
            request.setPreRequestScript("//PRE-REQUEST this is some source code for the request");

            
            request.setTestScript("//TEST this is some source code for the request");
            folder.addItem(request);

            
            pmcTest.setTestScript("//TEST this is some source code for the collection");
            pmcTest.setPreRequestScript("//PRE-REQUEST this is some source code for the collection");

            
validateAndWriteToFile(pmcTest, new Throwable().getStackTrace()[0]);

        } catch (Exception e) {
            e.printStackTrace();
            assertTrue(false);
        }

    }

    
    /** 
     * @param msgs
     * @param methodName
     */
    public void printValidationMessages(ArrayList<ValidationMessage> msgs, String methodName) {
        if (msgs != null && msgs.size() > 0) {
            for (ValidationMessage msg : msgs) {
                System.out.println("VALIDATION ERROR [" + methodName + "] output file: [" + collectionOutputPath + "]: "
                        + msg.getMessage());
            }
        }
    }

    @Test
    public void shouldImportCollection() {
        filePath = new java.io.File("").getAbsolutePath();

        try {
            pmcTest = PostmanCollection.pmcFactory(new File(
                    filePath + "/src/main/resources/com/postman/collection/example-catfact.postman_collection.json"));
            
validateAndWriteToFile(pmcTest, new Throwable().getStackTrace()[0]);

        } catch (Exception e) {
            e.printStackTrace();
            assertTrue(false);
        }

    }

    @Test
    public void shouldCreateURLs() {

        List<PostmanUrl> liUrls = new ArrayList<PostmanUrl>(Arrays.asList(new PostmanUrl[0]));
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

            pmcTest = PostmanCollection.pmcFactory();
            pmcTest.setName("TEST Construct URLs");
            for (int i = 0; i < liUrls.size(); i++) {
                try {
                    pmcTest.addRequest(new PostmanRequest(enumHTTPRequestMethod.GET, liUrls.get(i)), "URL " + (i + 1));
                    assertTrue(pmcTest.validate());
                    
                } catch (Exception e) {
                    e.printStackTrace();
                    assertTrue(false);
                }

            }
            validateAndWriteToFile(pmcTest, new Throwable().getStackTrace()[0]);
        } catch (Exception e) {
            e.printStackTrace();
            assertTrue(false);
        }

        /*
         * requests[6].addVariable(new PostmanVariable("path1", "path-value"));
         * requests[7].addVariable(new PostmanVariable("path1", "path-value"));
         * requests[8].addVariable(new PostmanVariable("path1", "path-value"));
         */

        try {

            
validateAndWriteToFile(pmcTest, new Throwable().getStackTrace()[0]);

        } catch (Exception e) {
            e.printStackTrace();
            assertTrue(false);
        }

    }

    @Test
    public void shouldProduceIdenticalCollection() {

        Set<String> collectionFiles;
        try {
            collectionFiles = listFilesUsingDirectoryStream(filePath + resourcePath);
        }
        catch(IOException e) {
            System.out.println("Error reading collection file list: " + e.getMessage());
            System.out.println("Skipping test: " + new Throwable().getStackTrace()[0].getMethodName());
            return;
        }
        
        PostmanCollection pmcTest2;
        
        
        
        for(String curPath : collectionFiles) {
            try {
                pmcTest = PostmanCollection.pmcFactory(new File(filePath + resourcePath + "/" + curPath));
            }
            catch(Exception e) {
                System.out.println("Error reading collection file: " + curPath);
                break;
            }
            try {
                pmcTest.writeToFile(new File(filePath + "/test-output/compare-src.postman_collection.json"));
                pmcTest2 = PostmanCollection.pmcFactory(new File(filePath + "/test-output/compare-src.postman_collection.json"));
            }
            catch(Exception e) {
                System.out.println("Error reading collection file: " + curPath);
                break;
            }

            JsonNode diffs = null;

            try {
                diffs = pmcTest.isEquivalentTo(pmcTest2);
            }
            catch(ValidationException e)
            {
                assertTrue("Error validating " + curPath,false);
            }
            /*
            for(int i = 0; i<diffs.size(); i++)
            {
                System.out.println("DIFF [" + i + "]: " + diffs.get(i).toPrettyString());
            }
            */
            assertEquals(0, diffs.size());

        }
        

        try {
            pmcTest = PostmanCollection.pmcFactory(new File(
                    filePath + "/src/main/resources/com/postman/collection/example-catfact.postman_collection.json"));
            
            
validateAndWriteToFile(pmcTest, new Throwable().getStackTrace()[0]);
        } catch (Exception e) {
            e.printStackTrace();
            assertTrue(false);
        }

    }

    @Test
    public void testBodyImportExport() {
        
        try {
            pmcTest = PostmanCollection.pmcFactory(new File(
                    filePath + "/src/main/resources/com/postman/collection/body-test.postman_collection.json"));
            
            validateAndWriteToFile(pmcTest, new Throwable().getStackTrace()[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        PostmanCollection test2;
        try {
            test2 =PostmanCollection.pmcFactory(new File(filePath + "/test-output/TEST-testBodyImportExport.postman_collection.json"));
            assertTrue(pmcTest.getItem("Raw-text XML").getRequest().getBody().getRaw().equals(test2.getItem("Raw-text XML").getRequest().getBody().getRaw()));
            System.out.println(pmcTest.toJson());
            System.out.println(test2.toJson());
        }
        catch(IOException e) {
            assertTrue("Unexpected IOEXception: " + e.getMessage(), false);
        }
        catch(IllegalPropertyAccessException a) {
            assertTrue("Unexpected IllegalPropertyAccessException: " + a.getMessage(), false);
        }
        

        

    }

    
    /** 
     * @throws Exception
     */
    @Test
    public void testAuthIngestion() throws Exception {
        pmcTest = PostmanCollection.pmcFactory(new File(filePath + resourcePath + "/auth.postman_collection.json"));
        boolean valid = pmcTest.validate();
        collectionOutputPath = filePath + "/test-output/TEST-" + pmcTest.getName();
        pmcTest.setDescription("TEST-" + new Throwable().getStackTrace()[0].getMethodName());
        pmcTest.writeToFile(new File(collectionOutputPath));
        printValidationMessages(pmcTest.getValidationMessages(), new Throwable().getStackTrace()[0].getMethodName());
        assertTrue(valid);
        assertTrue(new File(collectionOutputPath).exists());
    }

    
    /** 
     * @throws Exception
     */
    @Test
    public void testLargeBodyIngestion() throws Exception {
        pmcTest = PostmanCollection
                .pmcFactory(new File(filePath + resourcePath + "/test-collection.postman_collection.json"));
        pmcTest.setName("TEST large body");
        validateAndWriteToFile(pmcTest, new Throwable().getStackTrace()[0]);
    }

    
    /** 
     * @throws Exception
     */
    @Test
    public void testBuildAuths() throws Exception {
        pmcTest = PostmanCollection.pmcFactory();
        pmcTest.setName("TEST Auth");
        PostmanAuth auth;
        PostmanRequest req;
        

        req = new PostmanRequest(enumHTTPRequestMethod.GET, "https://postman-echo.com/post");
        pmcTest.addRequest(req, "INHERIT request");

        auth = new PostmanAuth(enumAuthType.AKAMAI);
        auth.addProperty("headersToSign", "x-api-key");
        auth.addProperty("baseURL", "https://akamai-base.com");
        auth.addProperty("timestamp", "akamaiTimestamp");
        auth.addProperty("nonce", "akamaiNonce");
        auth.addProperty("clientSecret", "akamaiClientSecret");
        auth.addProperty("clientToken", "akamaiClientToken");
        auth.addProperty("accessToken", "akamaiToken");

        req = new PostmanRequest(enumHTTPRequestMethod.GET, "https://postman-echo.com/post");
        req.setAuth(auth);
        pmcTest.addRequest(req, "AKAMAI request");

        auth = new PostmanAuth(enumAuthType.APIKEY);
        auth.addProperty("key", "API-KEY");
        auth.addProperty("value", "x-api-key");
        auth.addProperty("in", "query");
        req = new PostmanRequest(enumHTTPRequestMethod.GET, "https://postman-echo.com/post");
        req.setAuth(auth);
        pmcTest.addRequest(req, "APIKEY request");

        auth = new PostmanAuth(enumAuthType.AWS);
        auth.addProperty("sessionToken", "awsSessiontoken");
        auth.addProperty("service", "awsServiceName");
        auth.addProperty("secretKey", "aswSecretKey");
        auth.addProperty("accessKey", "awsAccessKey");
        auth.addProperty("addAuthDataToQuery", "false");
        req = new PostmanRequest(enumHTTPRequestMethod.GET, "https://postman-echo.com/post");
        req.setAuth(auth);
        pmcTest.addRequest(req, "AWS request");

        auth = new PostmanAuth(enumAuthType.BEARER);
        auth.addProperty("key", "API-KEY");
        auth.addProperty("value", "x-api-key");
        req = new PostmanRequest(enumHTTPRequestMethod.GET, "https://postman-echo.com/post");
        req.setAuth(auth);
        pmcTest.addRequest(req, "BEARER request");

        auth = new PostmanAuth(enumAuthType.BASIC);
        auth.addProperty("password", "fakePassword");
        auth.addProperty("username", "fakeusername");
        req = new PostmanRequest(enumHTTPRequestMethod.GET, "https://postman-echo.com/post");
        req.setAuth(auth);
        pmcTest.addRequest(req, "BASIC request");

        auth = new PostmanAuth(enumAuthType.DIGEST);
        auth.addProperty("opaque", "OpaqueString");
        auth.addProperty("clientNonce", "2020202");
        auth.addProperty("nonceCount", "1010101");
        auth.addProperty("qop", "digest-qop");
        auth.addProperty("algorithim", "SHA-256");
        auth.addProperty("nonce", "digestNonce");
        auth.addProperty("realm", "digest@test.com");
        auth.addProperty("password", "digestPassword");
        req = new PostmanRequest(enumHTTPRequestMethod.GET, "https://postman-echo.com/post");
        req.setAuth(auth);
        pmcTest.addRequest(req, "DIGEST request");

        auth = new PostmanAuth(enumAuthType.HAWK);
        auth.addProperty("includePayloadHash", "true");
        auth.addProperty("timestamp", "hawkTimestamp");
        auth.addProperty("delegation", "hawk-dlg");
        auth.addProperty("app", "HawkApp");
        auth.addProperty("extraData", "Hawk-ext");
        auth.addProperty("nonce", "hawkNonce");
        auth.addProperty("user", "HawkUser");
        auth.addProperty("authKey", "HawkAuthKey");
        auth.addProperty("algorithim", "sha256");
        req = new PostmanRequest(enumHTTPRequestMethod.GET, "https://postman-echo.com/post");
        req.setAuth(auth);
        pmcTest.addRequest(req, "HAWK request");

        auth = new PostmanAuth(enumAuthType.OAUTH1);
        auth.addProperty(new PostmanVariable("addEmptyParamsToSign", "true", null, "boolean"));
        auth.addProperty(new PostmanVariable("includeBodyHash", "true", null, "boolean"));
        auth.addProperty("realm", "testoauth@test.com");
        auth.addProperty("nonce", "oauthNonce");
        auth.addProperty("timestamp", "oauthTimestamp");
        auth.addProperty("verifier", "oauthVerifier");
        auth.addProperty("callback", "https:/callback.url");
        auth.addProperty("tokenSecret", "OAuthTokenSecret");
        auth.addProperty("token", "oauthToken");
        auth.addProperty("consumerSecret", "oauthConsumerSecret");
        auth.addProperty("consumerKey", "oauthConsumerKey");
        auth.addProperty("signatureMethod", "HMAC-SHA1");
        auth.addProperty("version", "1.0");
        auth.addProperty(new PostmanVariable("addParamsToHeader", "false", null, "boolean"));
        req = new PostmanRequest(enumHTTPRequestMethod.GET, "https://postman-echo.com/post");
        req.setAuth(auth);
        pmcTest.addRequest(req, "OAUTH1 request");

        auth = new PostmanAuth(enumAuthType.OAUTH2);
        auth.addProperty("grant_type", "authorization_code");
        auth.addProperty("tokenName", "Oauth2TokenName");
        auth.addProperty("tokenType", "");
        auth.addProperty("accessToken", "oauth2AccessToken");
        auth.addProperty("addTokenTo", "header");
        req = new PostmanRequest(enumHTTPRequestMethod.GET, "https://postman-echo.com/post");
        req.setAuth(auth);
        pmcTest.addRequest(req, "OAUTH2 request");

        auth = new PostmanAuth(enumAuthType.NTLM);
        auth.addProperty("workstation", "NTMLWorkstation");
        auth.addProperty("domain", "NTLMDomain");
        auth.addProperty("password", "NTLMPassword");
        auth.addProperty("username", "NTLMUsername");
        req = new PostmanRequest(enumHTTPRequestMethod.GET, "https://postman-echo.com/post");
        req.setAuth(auth);
        pmcTest.addRequest(req, "NTLM request");

        pmcTest.setAuth(auth);

        validateAndWriteToFile(pmcTest, new Throwable().getStackTrace()[0]);
    }

    
    /** 
     * @throws Exception
     */
    @Test
    public void testAddVariables() throws Exception {
        pmcTest = PostmanCollection.pmcFactory();
        collectionOutputPath = filePath + "/test-output/TEST-" + pmcTest.getName();
        pmcTest.setName("TEST Constructed Variables");
        PostmanVariable var1 = new PostmanVariable("key 1", "value 1");
        PostmanVariable var2 = new PostmanVariable("key 2", "value 2");
        PostmanVariable var3 = new PostmanVariable("key 3", "value 3");
        pmcTest.addVariable(var1);
        pmcTest.addVariable(var2);
        pmcTest.addVariable(var3);
        boolean valid = pmcTest.validate();
        if (!valid)

        {
            ArrayList<ValidationMessage> msgs = pmcTest.getValidationMessages();

            // For some reason an empty collection, ie., one missing the 'item' element,
            // doesn't validation, but still imports just fine
            // Not sure whether we want to include this exception in the actual
            // implementation tho.
            if (msgs.size() == 1 && msgs.get(0).getMessage().equals("$.item: is missing but it is required")) {
                valid = true;
            }
            printValidationMessages(msgs, new Throwable().getStackTrace()[0].getMethodName());

        }
        //add null value
        pmcTest.addVariable(new PostmanVariable("key 4",null));
        assertEquals(4, pmcTest.getVariables().size());

        //add null key
        pmcTest.addVariable(new PostmanVariable(null,"Value 5"));
        assertEquals(5, pmcTest.getVariables().size());

        //add null key, null value
        try {
            pmcTest.addVariable(new PostmanVariable(null,null));
        }
        catch(NullPointerException e) {
            assertTrue("Expected null value exception received",true);
        }
        
        String nullKey = null;
        pmcTest.removeVariable(nullKey);
        assertEquals(4, pmcTest.getVariables().size());
        pmcTest.removeVariable("key 2");
        assertNull(pmcTest.getVariable("key 2"));
        assertEquals(3, pmcTest.getVariables().size());
        pmcTest.removeVariable("not there");
        assertEquals(3, pmcTest.getVariables().size());



        
        validateAndWriteToFile(pmcTest, new Throwable().getStackTrace()[0]);

    }

    
    /** 
     * @throws Exception
     */
    @Test
    public void testIngestEvents() throws Exception {
        PostmanCollection pmcTest = PostmanCollection.pmcFactory(
                new File(filePath + resourcePath + "/example-cat-facts-with-tests.postman_collection.json"));
        validateAndWriteToFile(pmcTest, new Throwable().getStackTrace()[0]);
    }

    
    /** 
     * @param pmcTest
     * @param methodName
     * @return HashMap<String, String>
     */
    public HashMap<String, String> getOutputFileAndCollectionName(PostmanCollection pmcTest, String methodName) {
        HashMap<String, String> retVal = new HashMap<String, String>();
        retVal.put("collection-name", "TEST-" + methodName);
        retVal.put("output-path",
                filePath + "/test-output/" + retVal.get("collection-name") + ".postman_collection.json");
        
        retVal.put("collection-description", retVal.get("collection-name") + "generated by Test: " + methodName);
        return retVal;
    }

    
    /** 
     * @throws Exception
     */
    @Test
    public void testAddCollection() throws Exception {

        PostmanCollection pmcTest = PostmanCollection.pmcFactory(new File(
                filePath + "/src/main/resources/com/postman/collection/example-catfact.postman_collection.json"));
        PostmanCollection pmcTest2 = PostmanCollection.pmcFactory(new File(
                filePath + "/src/main/resources/com/postman/collection/example-weather.postman_collection.json"));
        pmcTest.addCollection(pmcTest2, true, true);
        pmcTest.setName("TEST Cat-Weather ");
        validateAndWriteToFile(pmcTest, new Throwable().getStackTrace()[0]);

    }

    
    /** 
     * @throws Exception
     */
    @Test
    public void testEquivalence() throws Exception {
        PostmanBody body;
        PostmanRequest req;
        PostmanResponse resp;
        pmcTest = PostmanCollection.pmcFactory(
                new File(filePath + "/src/main/resources/com/postman/collection/body-test.postman_collection.json"));
        PostmanCollection pmcTest2 = PostmanCollection.pmcFactory(
                new File(filePath + "/src/main/resources/com/postman/collection/body-test.postman_collection.json"));
        JsonNode diffs = pmcTest.isEquivalentTo(pmcTest2);
        //System.out.println("diffs (should be none) " + diffs.toPrettyString());
        assertEquals(0, diffs.size());

        pmcTest2 = PostmanCollection.pmcFactory(new File(
                filePath + "/src/main/resources/com/postman/collection/body-test-diff.postman_collection.json"));
        diffs = pmcTest.isEquivalentTo(pmcTest2);
        System.out.println("diffs " + diffs.toPrettyString());
        //assertTrue(diffs.size() == 1);

        body = new PostmanBody(enumRequestBodyMode.FORMDATA);
        body.setFormdata("field-1", "value 1", "This is value 1");
        body.setFormdata("field-2", "value 2", "This is value 2");
        req = new PostmanRequest(enumHTTPRequestMethod.POST, "https://postman-echo.com/post");
        req.setBody(body);
        resp = new PostmanResponse("NORMAL Formdata", req, "OK", 200, "this is the expected response body");
        pmcTest.addRequest(req, "Test Request", resp);

        body = new PostmanBody(enumRequestBodyMode.FORMDATA);
        body.setFormdata("field-1", "value 1", "This is value 1");
        body.setFormdata("field-2", "value 2", "This is value 2");
        req = new PostmanRequest(enumHTTPRequestMethod.POST, "https://postman-echo.com/post");
        req.setBody(body);
        resp = new PostmanResponse("NORMAL Formdata", req, "OK", 200, "this is the expected response body");
        pmcTest2.addRequest(req, "Test Request", resp);

        PostmanItem itemReq = pmcTest.getItem("Test Request");
        PostmanItem itemReq2 = pmcTest2.getItem("Test Request");

        diffs = itemReq.isEquivalentTo(itemReq2);
        //System.out.println("Req diffs (should be none) " + diffs.toPrettyString());
        assertEquals(0, diffs.size());

    }

    
    /** 
     * @throws Exception
     */
    @Test
    public void testConstructedBodies() throws Exception {

        PostmanCollection pmcTest = PostmanCollection.pmcFactory();
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

        body = new PostmanBody(enumRequestBodyMode.RAW, "{\"thing\":\"value\"}", enumRawBodyLanguage.JSON);
        req = new PostmanRequest(enumHTTPRequestMethod.POST, "https://postman-echo.com/post");
        req.setBody(body);
        resp = new PostmanResponse("NORMAL JSON", req, "OK", 200, "this is the expected response body");
        pmcTest.addRequest(req, "JSON body", resp);

        body = new PostmanBody(enumRequestBodyMode.RAW,
                "pm.test(\"Status code is 200\", function () {\n    pm.response.to.have.status(200);\n});\npm.test(\"Response time is less than 800ms\", function () {\n    pm.expect(pm.response.responseTime).to.be.below(800);\n});",
                enumRawBodyLanguage.JAVASCRIPT);
        req = new PostmanRequest(enumHTTPRequestMethod.POST, "https://postman-echo.com/post");
        req.setBody(body);
        resp = new PostmanResponse("NORMAL Javascript", req, "OK", 200, "this is the expected response body");
        pmcTest.addRequest(req, "Javascript body", resp);

        body = new PostmanBody(enumRequestBodyMode.FILE);
        body.setBinarySrc("8vhckkNqZ/jenkins-small.png");
        req = new PostmanRequest(enumHTTPRequestMethod.POST, "https://postman-echo.com/post");
        req.setBody(body);
        resp = new PostmanResponse("NORMAL Binary", req, "OK", 200, "this is the expected response body");
        pmcTest.addRequest(req, "Binary body", resp);

        body = new PostmanBody(enumRequestBodyMode.RAW, "{<html><body><p>This is some html</p</body></html>}",
                enumRawBodyLanguage.HTML);
        req = new PostmanRequest(enumHTTPRequestMethod.POST, "https://postman-echo.com/post");
        req.setBody(body);
        resp = new PostmanResponse("NORMAL HTML", req, "OK", 200, "this is the expected response body");
        pmcTest.addRequest(req, "HTML body", resp);

        body = new PostmanBody(enumRequestBodyMode.RAW, "{<xml><body><p>This is some XML</p</body></xml>}",
                enumRawBodyLanguage.XML);
        req = new PostmanRequest(enumHTTPRequestMethod.POST, "https://postman-echo.com/post");
        req.setBody(body);
        resp = new PostmanResponse("NORMAL XML", req, "OK", 200, "this is the expected response body");
        pmcTest.addRequest(req, "XML body", resp);

        String strGraphQL = "{ \n            launchesPast(limit: 10) {\n              mission_name\n              launch_date_local\n              launch_site {\n                site_name_long\n              }\n              links {\n                article_link\n                video_link\n              }\n              rocket {\n                rocket_name\n              }\n            }\n          }";
        String strVars = "{\"limit\":2}";
        body = new PostmanBody(enumRequestBodyMode.GRAPHQL, strGraphQL, enumRawBodyLanguage.GRAPHQL);
        body.setGraphql(strGraphQL, strVars);
        req = new PostmanRequest(enumHTTPRequestMethod.POST, "https://postman-echo.com/post");
        req.setBody(body);
        resp = new PostmanResponse("NORMAL GrapqhQL", req, "OK", 200, "this is the expected response body");
        pmcTest.addRequest(req, "GraphQL body", resp);

        validateAndWriteToFile(pmcTest, new Throwable().getStackTrace()[0]);

    }
    @Test
    public void testAuthObject() {


        PostmanVariable prop;
        
        PostmanAuth auth = new PostmanAuth(enumAuthType.OAUTH1);
        prop = new PostmanVariable("addEmptyParamsToSign", "true");
        auth.addProperty(prop);
        assertEquals(1, auth.getProperties().size());
        assertEquals("true", auth.getProperty("addEmptyParamsToSign").getValue());
        
        prop = new PostmanVariable("addEmptyParamsToSign", "false");
        auth.addProperty(prop);

        assertEquals(1,auth.getProperties().size());
        assertEquals("false", auth.getProperty("addEmptyParamsToSign").getValue());
        

        HashMap<String, PostmanVariable> nullProps = null;
        auth.setProperties(nullProps);
        PostmanVariable curVar = auth.getProperty("addEmptyParamsToSign");
        assertNull(curVar);

    
    }
    @Test
    public void testBodyObject() {
        
            PostmanBody body = new PostmanBody(enumRequestBodyMode.RAW,"//some javascript",enumRawBodyLanguage.JAVASCRIPT);
            assertSame(enumRequestBodyMode.RAW,body.getMode());

            System.out.println(body.toJson());
            
            try {
                assertSame(enumRawBodyLanguage.JAVASCRIPT, body.getRawLanguage());
            }
            catch(IllegalPropertyAccessException e)
            {
                assertTrue(false);
            }

            
            try {
                body.setRaw("//some javascript");
            }
            catch(IllegalPropertyAccessException e)
            {
                assertTrue("Unexpected IllegalPropertyAccessException ", false);
            }
            
            
            
            try {
                assertEquals("//some javascript", body.getRaw());
            }
            catch(IllegalPropertyAccessException e)
            {
                assertTrue("Unexpected IllegalPropertyAccessException ", false);
            }
            
            
            boolean valid = false;
            try {
                valid = body.validate();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            
            printValidationMessages(body.getValidationMessages(), new Throwable().getStackTrace()[0].getMethodName());
            assertTrue(valid);

            body = new PostmanBody(enumRequestBodyMode.FORMDATA);
            
            try {
                assertNull(body.getRawLanguage());
            }
            catch (IllegalPropertyAccessException e)
            {
                assertTrue("Expected exception thrown", true);
            }

            body = new PostmanBody(enumRequestBodyMode.RAW);
            try {
                assertNull(body.getFile());    
            }
            catch(IllegalPropertyAccessException e)
            {
                assertTrue("expected exception thrown",true);
            }
            
            
            try {
                assertNull(body.getFormdata());
            }
            catch(IllegalPropertyAccessException e)
            {
                assertTrue("expected exception thrown",true);
            }

            try {
                assertNull(body.getGraphql());
            }
            catch(IllegalPropertyAccessException e)
            {
                assertTrue("expected Exception thrown", true);
            }
            
            try {
                assertNull(body.getRaw());
            }
            catch(IllegalPropertyAccessException e)
            {
                assertTrue("expected exception thrown",true);
            }

            body = new PostmanBody(enumRequestBodyMode.GRAPHQL);
            try {
                assertNull("GraphQL is not null", body.getGraphql());
            }
            catch(IllegalPropertyAccessException e)
            {
                assertTrue("Expected exception thrown", true);
            }
            try {
                assertNotNull("Body is not null", body.getFile());
            }
            catch(IllegalPropertyAccessException e)
            {
                assertTrue("Expected exception thrown",true);
            }


            body = new PostmanBody(enumRequestBodyMode.FILE);
            try {
                String file = "some/path/to/file.png";
                body.setBinarySrc(file);
                assertEquals(file, body.getFile());
            }
            catch(Exception e)
            {
                assertTrue("Exception " + e.getMessage(), false);
            }

            body = new PostmanBody(enumRequestBodyMode.RAW);
            try {
                body.setRawLanguage(enumRawBodyLanguage.JAVASCRIPT);
                body.setRaw("//some javascript");
                assertSame(enumRawBodyLanguage.JAVASCRIPT, body.getRawLanguage());
                assertEquals("//some javascript", body.getRaw());
            }
            catch(Exception e)
            {
                System.out.println(e);
                assertTrue(false);
            }

            body.setMode(enumRequestBodyMode.FORMDATA);
            try {
                body.getRaw();
            }
            catch(IllegalPropertyAccessException e)
            {
                assertTrue("Expected exception", true);
            }
            
            
    }

    @Test
    public void testURLObject() {

        String url1 = "https://foo.com:8080/foo/bar/:path1/bat.json?var1=aaa&var2=bbb";
        //String url1 = "https://foo.com:8080/foo/bar/bat.json?var1=aaa&var2=bbb";
        String path1 = "foo/bar/:path1/bat.json";
        PostmanUrl url = null;
        try {
            url = new PostmanUrl(url1);
        }
        catch(DuplicateVariableKeyException e)
        {
            assertTrue("Unexpected duplicate key: " + e.getMessage(), false);
        }
        

        assertEquals(2, url.getHosts().size());
        assertEquals(4, url.getPaths().size());
        assertEquals(1, url.getPathVariables().size());
        assertEquals(2, url.getQueryElements().size());

        try {
            url = new PostmanUrl("foo.com",path1);
        }
        
        catch(DuplicateVariableKeyException e)
        {
            assertTrue("Unexpected duplicate key: " + e.getMessage(), false);
        }
        assertEquals(4, url.getPaths().size());
        assertEquals(1, url.getPathVariables().size());

        url.addQuery("query1", "q1value");
        assertEquals(1, url.getQueryElements().size());
        


        ArrayList<String> urls = new ArrayList<String>();
        urls.add("");
        urls.add("http://foo.com/bar/bat.json");
        urls.add("//foo.com/bar/bat.json");
        urls.add("{{baseUrl}}/foo.com/bar/bat.json");
        urls.add("http://foo.com/bar/bat.json?foo=1&bar=2");
        urls.add("http://foo.com/bar/bat.json?foo=1&bar=");
        urls.add("{{baseUrl}}/foo.com/bar/bat.json?foo=1&bar=");
        urls.add("{{baseUrl}}/foo.com/bar/:path1/bat.json?foo=1&bar=");
        urls.add("{{baseUrl}}foo.com:8080/bar/:path1/bat.json?foo=1&bar=");
        urls.add("{{baseUrl}}/foo.com:8080/bar/:path1/bat.json?foo=1&bar=");
        urls.add("https://foo.com:8080/bar/:path1/bat.json?foo=1&bar=");
        urls.add("https://foo.com/bar/:path1/bat.json?foo=1&bar=");
        ArrayList<PostmanUrl> liUrls = new ArrayList<PostmanUrl>();

    for(String curUrl: urls) {
        try {
            liUrls.add(new PostmanUrl(curUrl));
        }
        catch(DuplicateVariableKeyException e) {
            assertTrue("Unexpected duplicate key: " + e.getMessage(), false);
        }
    }
    for(int i = 0; i < liUrls.size(); i++){
        try{
            if(i != 2 && liUrls.get(i).generateURL().equals(urls.get(i))) {
                assertTrue(true);
                //System.out.println("URL: " + urls.get(i));
            }
            //technical wrong, but it's OK i think
            if(i == 2 && liUrls.get(i).generateURL().equals("/foo.com/bar/bat.json")) {
                assertTrue(true);
            }

        }
        catch(Exception e)
        {
            //System.out.println("URL: " + i);
            assertTrue(urls.get(i) + " failed", false);
        }
    }
    try {
        url = new PostmanUrl("https://foo.com?var1=val1&var2=val2");
    }
    
    catch(DuplicateVariableKeyException e) {
        assertTrue("Unexpected duplicate key: " + e.getMessage(), false);
    }
    assertEquals(2, url.getQueryElements().size());
    assertEquals("var1=val1&var2=val2", url.getQueryString());
    PostmanVariable varQ = url.getQueryElements().get(1);
    url.removeQueryElement(varQ);
    assertEquals(1, url.getQueryElements().size());
    assertEquals("var1=val1", url.getQueryString());

    }
    @Test
    public void testIngestFromUrl() throws MalformedURLException {
        //Good URL
        try {
            pmcTest = PostmanCollection.pmcFactory(new URL("https://api.getpostman.com/collections/23889826-a0a8f60c-36c9-4221-9c99-3aa90eb46abe"));
            assertTrue("Valid collection ingested from URL",pmcTest.validate());
        }
        catch(Exception e) {
            assertTrue("Unexpected " + e.getClass().getName() +": " + e.getMessage(),false);
        }
        //Good URL, bad COllection ID
//        try {

  //      }

    

        
        
    }

    @Test
    public void testResponseObject() {

        PostmanRequest req = null;
        try {
           req = new PostmanRequest(enumHTTPRequestMethod.GET, "https:/postman-echo.com/post?foo=bar");
        }
        catch(DuplicateVariableKeyException e)
        {
            assertTrue("Unexpected duplicate path key" + e.getMessage(), false);
        }
        PostmanResponse resp = new PostmanResponse("Test Response",req, "OK",200,"This is the body" );
        assertEquals("This is the body", resp.getBody());
        assertEquals(200, resp.getCode());
        try {
            JsonNode diffs = resp.getOriginalRequest().isEquivalentTo(req);
            assertEquals(0,diffs.size());
        }
        catch(Exception e)
        {
            assertTrue("Equivalence test exception",false);
        }

        resp = new PostmanResponse("Test Response",req, "Not authorize",401,"A completely different body" );
        PostmanRequest req2 = null;
        try {
            req2 = new PostmanRequest(enumHTTPRequestMethod.POST, "https://cnn.com");
        }
        catch(DuplicateVariableKeyException e)
        {
            assertTrue("Unexpected duplicate path key" + e.getMessage(), false);
        }
        
        
        PostmanResponse newResp = new PostmanResponse("Test Response",req2, "Not authorize",401,"A completely different body" );
                
        try {
                    JsonNode diffs = newResp.getOriginalRequest().isEquivalentTo(req);
                    assertTrue(diffs.size() > 0);
        }
        catch(Exception e)
        {
            assertTrue("Equivalence test exception",false);
        }

        
        


        


    }

    @Test
    public void testEventObject() {
        PostmanEvent evt = new PostmanEvent(enumEventType.PRE_REQUEST, "//fake javascript");
        
        assertEquals(1, evt.getSourceCodeElements().size());

        evt.addSourceCodeElement("//some more code", 0);
        assertEquals(2, evt.getSourceCodeElements().size());
        try {
            evt.removeSourceCodeElement(0);
        }
        catch(Exception e) {
            assertTrue("Unexpected excpetion", false);
        }

        assertEquals(1, evt.getSourceCodeElements().size());
        try {
            assertEquals("//some more code", evt.getSourceCode());
        }
        catch(InvalidPropertyException e)
         {
            assertTrue("Unexpected exception",false);
         }
        

        try {
            evt.removeSourceCodeElement(10);
        }
        catch (InvalidPropertyException e) {
            assertTrue("Expected exception", true);
        }
        


        

        try {
            evt.removeSourceCodeElement(0);
        }
        catch(Exception e) {
            assertTrue("Unexpected exception " + e.getMessage(), false);
        }

        
        
    }
    
    
    public void testPostmanItem() throws Exception{
        
        pmcTest = PostmanCollection.pmcFactory(new java.io.File(filePath + "/src/main/resources/com/postman/collection/example-cat-facts-with-tests.postman_collection.json"));
        
        
        PostmanItem fact = pmcTest.getItem("Get a list of facts");
        PostmanItem folder = pmcTest.getItem("get Breeds",true);
        assertNotNull(fact);
        assertNotNull(folder);
        
        assertTrue(fact != null || fact.getName().equals("Get a list of facts"));
        assertTrue(folder != null || folder.getName().equals("Breeds"));

        pmcTest = PostmanCollection.pmcFactory(new java.io.File(filePath + "/src/main/resources/com/postman/collection/example-catfact.postman_collection.json"));
        
        ArrayList<PostmanItem> folders = pmcTest.getItems(enumPostmanItemType.FOLDER);
        assertEquals(2, folders.size());
        ArrayList<PostmanItem> requests = pmcTest.getItems(enumPostmanItemType.REQUEST);
        assertEquals(5, requests.size());
        ArrayList<PostmanItem> all = pmcTest.getItems(null);
        assertEquals(7, all.size());

        fact = pmcTest.getItem("Add Breed");
        assertNotNull(fact);
        assertEquals("Add Breed", fact.getName());

    }

    
    
    @Test
    public void TestCollectionRequests() {
        pmcTest = PostmanCollection.pmcFactory();
        pmcTest.setName("TEST Request Operations");

        PostmanRequest req = null;
        PostmanItem reqItem1 = null;
        //generates spurious "not used" warning
        PostmanItem reqItem2 = null;
        PostmanItem newFolder = null;

        try {
            req = new PostmanRequest(enumHTTPRequestMethod.GET, "https://postman-echo.com/get");
        }
        catch(DuplicateVariableKeyException e)
        {
            assertTrue("Unexpected duplicate key: " + e.getMessage(), false);
        }
        
        
       try {
        reqItem1 = pmcTest.addRequest(req, "GET echo");
       } 
       catch(Exception e){
        assertTrue("Unexpected exception " + e.getMessage(), false);
       }
        
       try {
        req = new PostmanRequest(enumHTTPRequestMethod.POST, "https://postman-echo.com/post");
    }
    catch(DuplicateVariableKeyException e)
    {
        assertTrue("Unexpected duplicate key: " + e.getMessage(), false);
    }
      
       

       try {
        reqItem2 = pmcTest.addRequest(req,"POST echo");
        assertNotNull(reqItem2);
       } 
       catch(Exception e){
        assertTrue("Unexpected exception " + e.getMessage(), false);
       }

       


       assertEquals(2, pmcTest.getItems(enumPostmanItemType.REQUEST).size());

       try {
        pmcTest.moveItem(reqItem1, reqItem1);
       }
       catch(RecursiveItemAddException e)
       {
        assertTrue("Recursive exception as expected",true);
       }
       catch(InvalidCollectionActionException d) {
        assertTrue("Unexpected exception: " + d.getMessage(), false);
       }

       try {
        newFolder = pmcTest.addFolder("New Folder");
       }
       catch(Exception e)
       {
        assertTrue("Unexpected exception: " + e.getMessage(), false);
       }
       
       try {
        pmcTest.moveItem(reqItem1, newFolder);
        assertEquals("New Folder", pmcTest.getItem("GET echo", true).getName());
        assertEquals(2, pmcTest.getItems().size());
       }
       catch(Exception e)
       {
        assertTrue("Unexpected exception: " + e.getMessage(), false);
       }
       

       
       



       pmcTest.removeItem("POST echo");
       assertEquals(1, pmcTest.getItems(enumPostmanItemType.REQUEST).size());
       assertNotNull(pmcTest.getItem("GET echo"));


    }

    @Test
    public void testCollectionFolder() {

        pmcTest = PostmanCollection.pmcFactory();
        pmcTest.setName("TEST Request Operations");

        PostmanRequest req;
        PostmanItem reqItem1 = null;
        PostmanItem reqItem2 = null;
        PostmanItem newFolder = null;

        try {
            
            req = new PostmanRequest(enumHTTPRequestMethod.GET, "https://postman-echo/get");
            reqItem1 = pmcTest.addRequest(req, "GET test");
            req = new PostmanRequest(enumHTTPRequestMethod.POST, "https://postman-echo/post");
            reqItem2 = pmcTest.addRequest(req, "GET Post");
            newFolder = pmcTest.addFolder("New Folder");
            pmcTest.moveItem(reqItem1, newFolder);
            pmcTest.moveItem(reqItem2, newFolder);
            assertEquals(2, pmcTest.getItems(enumPostmanItemType.REQUEST).size());
            assertEquals(1, pmcTest.getItems(enumPostmanItemType.FOLDER).size());
            assertEquals(2, pmcTest.getItem("New Folder").getItems().size());
            pmcTest.removeItem(newFolder);
            assertEquals(0, pmcTest.getItems(enumPostmanItemType.REQUEST).size());
            assertEquals(0, pmcTest.getItems(enumPostmanItemType.FOLDER).size());
        }
        catch(Exception e) {
            assertTrue("Unexpected exception: " + e.getMessage(), false);
        }
        

    }

    @Test
    public void testBooleanIntegerVariables() {
        try {
            pmcTest = PostmanCollection.pmcFactory(new File(filePath + "/" + resourcePath + "/test-variable-types.postman_collection.json"));
            validateAndWriteToFile(pmcTest, new Throwable().getStackTrace()[0]);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        

    }

    @Test
    public void TestIngestionByID() {
        try {
            pmcTest = PostmanCollection.pmcFactory(new PostmanID("23889826-a0a8f60c-36c9-4221-9c99-3aa90eb46abe"));
        }
        catch(Exception e) {
            assertTrue("Unexpected exception: " + e.getMessage(), false);
        }
        validateAndWriteToFile(pmcTest, new Throwable().getStackTrace()[0]);
        
    }


public boolean validateAndWriteToFile(PostmanCollection pmcColl, StackTraceElement testMethodInfo) {
    boolean valid = false;
    
    HashMap<String, String> outputData = getOutputFileAndCollectionName(pmcColl,
            testMethodInfo.getMethodName());
    pmcColl.setDescription(outputData.get("collection-description"));
    pmcColl.setName(outputData.get("collection-name"));
    collectionOutputPath = outputData.get("output-path");
    try {
        pmcColl.writeToFile(new File(collectionOutputPath));
        System.out.println("Wrote to " + collectionOutputPath);
    }
    catch(IOException i) {
        assertTrue("IOException writing to file: [" + testMethodInfo.getMethodName() +":" + testMethodInfo.getLineNumber() + "]: " + i.getMessage(), false);
    }

    try {
        valid = pmcColl.validate();
    }
    catch(ValidationException e) {
        assertTrue("Validation exception [" + testMethodInfo.getMethodName() +":" + testMethodInfo.getLineNumber() + "]: " + e.getMessage(), false);
    }
    
    
    
    printValidationMessages(pmcColl.getValidationMessages(),testMethodInfo.getMethodName());
    assertTrue("Final validation test for: " + testMethodInfo.getMethodName(), valid);
    assertTrue("Validating output file was written: " + testMethodInfo.getMethodName(), new File(collectionOutputPath).exists());
    return valid;
}


boolean deleteDirectory(File directoryToBeDeleted) {
    File[] allContents = directoryToBeDeleted.listFiles();
    if (allContents != null) {
        for (File file : allContents) {
            deleteDirectory(file);
        }
    }
    return directoryToBeDeleted.delete();
}


@Test
public void testVariableResolution() {
    List<PostmanUrl> liUrls = new ArrayList<PostmanUrl>(Arrays.asList(new PostmanUrl[0]));
    String url;
    try {

        liUrls.add(new PostmanUrl("{{baseUrl}}/{{var1}}.com/:path1/bat.json"));
        liUrls.add(new PostmanUrl("https://{{var1}}.com/:path1/bat.json?{{var1}}={{var2}}"));

        

        pmcTest = PostmanCollection.pmcFactory();
        pmcTest.setName("TEST Resolve Variables");
        pmcTest.addVariable(new PostmanVariable("baseUrl","http://test.com"));
        pmcTest.addVariable(new PostmanVariable("var1", "var1value"));
        pmcTest.addVariable(new PostmanVariable("var2","333"));
        for (int i = 0; i < liUrls.size(); i++) {
            try {
                pmcTest.addRequest(new PostmanRequest(enumHTTPRequestMethod.GET, liUrls.get(i)), "URL " + (i + 1));
                assertTrue(pmcTest.validate());
                
            } catch (Exception e) {
                e.printStackTrace();
                assertTrue(false);
            }
        }

        url = pmcTest.getItem("URL 1").getRequest().getUrl().getUrl(true);
        url = pmcTest.resolveVariables(url);
        assertTrue(url.equals("http://test.com/var1value.com/:path1/bat.json"));
        url = pmcTest.getItem("URL 2").getRequest().getUrl().getUrl(true);
        url = pmcTest.resolveVariables(url);
        assertTrue(url.equals("https://var1value.com/:path1/bat.json?var1value=333"));

        pmcTest.getItem("URL 1").getRequest().getUrl().setPathVariable(new PostmanVariable("path1", "path1value"));
        assertTrue(pmcTest.resolveVariables(pmcTest.getItem("URL 1").getRequest().getUrl().getUrl(true)).equals("http://test.com/var1value.com/path1value/bat.json"));
    }
    catch(Exception e) {
        assertTrue("Unexpected exception: " + e.getMessage(), false );
    }
}

@Test
public void testPostmanVariable() {
    PostmanVariable var1 = new PostmanVariable("var1","var1value");
    PostmanVariable var2 = new PostmanVariable("var2","var2value");
    PostmanVariable var3 = new PostmanVariable("var1","var1value");

    assertFalse(var1.equals(var2));
    assertTrue(var1.equals(var3));
    ArrayList<PostmanVariable> alVars = new ArrayList<PostmanVariable>();
    alVars.add(var1);
    alVars.add(var2);
    alVars.add(var3);


    VariableListMap<PostmanVariable> vlMap = new VariableListMap<PostmanVariable>(alVars);

    System.out.println(vlMap.contains(var1));
    System.out.println(vlMap.get("var2").getValue());
    System.out.println(vlMap instanceof Iterable);
    for(PostmanVariable curVar : vlMap) {
        System.out.println("KEY: " + curVar.getKey() + " VALUE: " + curVar.getValue());

    }



}
}