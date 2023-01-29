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
    Collection pmcTest = null;
    String collectionOutputPath;

    
   
   /** 
    * @return Set<String>
    */
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
            pmcTest = Collection.pmcFactory();
            RequestElement newReq = new RequestElement(enumHTTPRequestMethod.GET, "https://postman-echo.com/post");
            newReq.getUrlElement().addQuery("foo", "bar");
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

        Collection pmcTest = Collection.pmcFactory();
        ItemElement folder;
        ItemElement request;

        //generates spurious "variable not used" warning
        EventElement  event = null; 
        RequestElement req;
        assertNull(event);
      
        try {
            pmcTest.setName("TEST Scripts");
            folder = new ItemElement("Scripts");

            
            folder.setPreRequestScript("//PRE-REQUEST this is some source code for the folder");
            event = new EventElement(enumEventType.TEST, "//TEST this is some source code for the folder");
            folder.setTestScript("//TEST this is some source code for the folder");
            pmcTest.addItem(folder);

            req = new RequestElement(enumHTTPRequestMethod.GET, "https:/postman-echo.com/post?foo=bar");

            request = new ItemElement("TEST Request with Scripts");
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
            pmcTest = Collection.pmcFactory(new File(
                    filePath + "/src/main/resources/com/postman/collection/example-catfact.postman_collection.json"));
            
validateAndWriteToFile(pmcTest, new Throwable().getStackTrace()[0]);

        } catch (Exception e) {
            e.printStackTrace();
            assertTrue(false);
        }

    }

    @Test
    public void shouldCreateURLs() {

        List<UrlElement> liUrls = new ArrayList<UrlElement>(Arrays.asList(new UrlElement[0]));
        try {

            liUrls.add(new UrlElement("http://foo.com/bar/bat.json"));
            liUrls.add(new UrlElement("//foo.com/bar/bat.json"));
            liUrls.add(new UrlElement("{{baseUrl}}/foo.com/bar/bat.json"));
            liUrls.add(new UrlElement("http://foo.com/bar/bat.json?foo=1&bar=2"));
            liUrls.add(new UrlElement("http://foo.com/bar/bat.json?foo=1&bar="));
            liUrls.add(new UrlElement("{{baseUrl}}/foo.com/bar/bat.json?foo=1&bar="));
            liUrls.add(new UrlElement("{{baseUrl}}/foo.com/bar/:path1/bat.json?foo=1&bar="));
            liUrls.add(new UrlElement("{{baseUrl}}foo.com:8080/bar/:path1/bat.json?foo=1&bar="));
            liUrls.add(new UrlElement("{{baseUrl}}/foo.com:8080/bar/:path1/bat.json?foo=1&bar="));
            liUrls.add(new UrlElement("https://foo.com:8080/bar/:path1/bat.json?foo=1&bar="));
            liUrls.add(new UrlElement("https://foo.com/bar/:path1/bat.json?foo=1&bar="));

            pmcTest = Collection.pmcFactory();
            pmcTest.setName("TEST Construct URLs");
            for (int i = 0; i < liUrls.size(); i++) {
                try {
                    pmcTest.addRequest(new RequestElement(enumHTTPRequestMethod.GET, liUrls.get(i)), "URL " + (i + 1));
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
        
        Collection pmcTest2;
        
        
        
        for(String curPath : collectionFiles) {
            try {
                pmcTest = Collection.pmcFactory(new File(filePath + resourcePath + "/" + curPath));
            }
            catch(Exception e) {
                System.out.println("Error reading collection file: " + curPath);
                break;
            }
            try {
                pmcTest.writeToFile(new File(filePath + "/test-output/compare-src.postman_collection.json"));
                pmcTest2 = Collection.pmcFactory(new File(filePath + "/test-output/compare-src.postman_collection.json"));
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
            pmcTest = Collection.pmcFactory(new File(
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
            pmcTest = Collection.pmcFactory(new File(
                    filePath + "/src/main/resources/com/postman/collection/body-test.postman_collection.json"));
            
            validateAndWriteToFile(pmcTest, new Throwable().getStackTrace()[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Collection test2;
        try {
            test2 =Collection.pmcFactory(new File(filePath + "/test-output/TEST-testBodyImportExport.postman_collection.json"));
            assertTrue(pmcTest.getItem("Raw-text XML").getRequest().getBody().getRaw().equals(test2.getItem("Raw-text XML").getRequest().getBody().getRaw()));
            
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
        pmcTest = Collection.pmcFactory(new File(filePath + resourcePath + "/auth.postman_collection.json"));
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
        pmcTest = Collection
                .pmcFactory(new File(filePath + resourcePath + "/test-collection.postman_collection.json"));
        pmcTest.setName("TEST large body");
        validateAndWriteToFile(pmcTest, new Throwable().getStackTrace()[0]);
    }

    
    /** 
     * @throws Exception
     */
    @Test
    public void testBuildAuths() throws Exception {
        pmcTest = Collection.pmcFactory();
        pmcTest.setName("TEST Auth");
        AuthElement auth;
        RequestElement req;
        

        req = new RequestElement(enumHTTPRequestMethod.GET, "https://postman-echo.com/post");
        pmcTest.addRequest(req, "INHERIT request");

        auth = new AuthElement(enumAuthType.AKAMAI);
        auth.addProperty("headersToSign", "x-api-key");
        auth.addProperty("baseURL", "https://akamai-base.com");
        auth.addProperty("timestamp", "akamaiTimestamp");
        auth.addProperty("nonce", "akamaiNonce");
        auth.addProperty("clientSecret", "akamaiClientSecret");
        auth.addProperty("clientToken", "akamaiClientToken");
        auth.addProperty("accessToken", "akamaiToken");

        req = new RequestElement(enumHTTPRequestMethod.GET, "https://postman-echo.com/post");
        req.setAuth(auth);
        pmcTest.addRequest(req, "AKAMAI request");

        auth = new AuthElement(enumAuthType.APIKEY);
        auth.addProperty("key", "API-KEY");
        auth.addProperty("value", "x-api-key");
        auth.addProperty("in", "query");
        req = new RequestElement(enumHTTPRequestMethod.GET, "https://postman-echo.com/post");
        req.setAuth(auth);
        pmcTest.addRequest(req, "APIKEY request");

        auth = new AuthElement(enumAuthType.AWS);
        auth.addProperty("sessionToken", "awsSessiontoken");
        auth.addProperty("service", "awsServiceName");
        auth.addProperty("secretKey", "aswSecretKey");
        auth.addProperty("accessKey", "awsAccessKey");
        auth.addProperty("addAuthDataToQuery", "false");
        req = new RequestElement(enumHTTPRequestMethod.GET, "https://postman-echo.com/post");
        req.setAuth(auth);
        pmcTest.addRequest(req, "AWS request");

        auth = new AuthElement(enumAuthType.BEARER);
        auth.addProperty("key", "API-KEY");
        auth.addProperty("value", "x-api-key");
        req = new RequestElement(enumHTTPRequestMethod.GET, "https://postman-echo.com/post");
        req.setAuth(auth);
        pmcTest.addRequest(req, "BEARER request");

        auth = new AuthElement(enumAuthType.BASIC);
        auth.addProperty("password", "fakePassword");
        auth.addProperty("username", "fakeusername");
        req = new RequestElement(enumHTTPRequestMethod.GET, "https://postman-echo.com/post");
        req.setAuth(auth);
        pmcTest.addRequest(req, "BASIC request");

        auth = new AuthElement(enumAuthType.DIGEST);
        auth.addProperty("opaque", "OpaqueString");
        auth.addProperty("clientNonce", "2020202");
        auth.addProperty("nonceCount", "1010101");
        auth.addProperty("qop", "digest-qop");
        auth.addProperty("algorithim", "SHA-256");
        auth.addProperty("nonce", "digestNonce");
        auth.addProperty("realm", "digest@test.com");
        auth.addProperty("password", "digestPassword");
        req = new RequestElement(enumHTTPRequestMethod.GET, "https://postman-echo.com/post");
        req.setAuth(auth);
        pmcTest.addRequest(req, "DIGEST request");

        auth = new AuthElement(enumAuthType.HAWK);
        auth.addProperty("includePayloadHash", "true");
        auth.addProperty("timestamp", "hawkTimestamp");
        auth.addProperty("delegation", "hawk-dlg");
        auth.addProperty("app", "HawkApp");
        auth.addProperty("extraData", "Hawk-ext");
        auth.addProperty("nonce", "hawkNonce");
        auth.addProperty("user", "HawkUser");
        auth.addProperty("authKey", "HawkAuthKey");
        auth.addProperty("algorithim", "sha256");
        req = new RequestElement(enumHTTPRequestMethod.GET, "https://postman-echo.com/post");
        req.setAuth(auth);
        pmcTest.addRequest(req, "HAWK request");

        auth = new AuthElement(enumAuthType.OAUTH1);
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
        req = new RequestElement(enumHTTPRequestMethod.GET, "https://postman-echo.com/post");
        req.setAuth(auth);
        pmcTest.addRequest(req, "OAUTH1 request");

        auth = new AuthElement(enumAuthType.OAUTH2);
        auth.addProperty("grant_type", "authorization_code");
        auth.addProperty("tokenName", "Oauth2TokenName");
        auth.addProperty("tokenType", "");
        auth.addProperty("accessToken", "oauth2AccessToken");
        auth.addProperty("addTokenTo", "header");
        req = new RequestElement(enumHTTPRequestMethod.GET, "https://postman-echo.com/post");
        req.setAuth(auth);
        pmcTest.addRequest(req, "OAUTH2 request");

        auth = new AuthElement(enumAuthType.NTLM);
        auth.addProperty("workstation", "NTMLWorkstation");
        auth.addProperty("domain", "NTLMDomain");
        auth.addProperty("password", "NTLMPassword");
        auth.addProperty("username", "NTLMUsername");
        req = new RequestElement(enumHTTPRequestMethod.GET, "https://postman-echo.com/post");
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
        pmcTest = Collection.pmcFactory();
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
        Collection pmcTest = Collection.pmcFactory(
                new File(filePath + resourcePath + "/example-cat-facts-with-tests.postman_collection.json"));
        validateAndWriteToFile(pmcTest, new Throwable().getStackTrace()[0]);
    }

    
    /** 
     * @param pmcTest
     * @param methodName
     * @return HashMap<String, String>
     */
    public HashMap<String, String> getOutputFileAndCollectionName(Collection pmcTest, String methodName) {
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

        Collection pmcTest = Collection.pmcFactory(new File(
                filePath + "/src/main/resources/com/postman/collection/example-catfact.postman_collection.json"));
        Collection pmcTest2 = Collection.pmcFactory(new File(
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
        BodyElement body;
        RequestElement req;
        ResponseElement resp;
        pmcTest = Collection.pmcFactory(
                new File(filePath + "/src/main/resources/com/postman/collection/body-test.postman_collection.json"));
        Collection pmcTest2 = Collection.pmcFactory(
                new File(filePath + "/src/main/resources/com/postman/collection/body-test.postman_collection.json"));
        JsonNode diffs = pmcTest.isEquivalentTo(pmcTest2);
        
        assertEquals(0, diffs.size());

        pmcTest2 = Collection.pmcFactory(new File(
                filePath + "/src/main/resources/com/postman/collection/body-test-diff.postman_collection.json"));
        diffs = pmcTest.isEquivalentTo(pmcTest2);
        
        

        body = new BodyElement(enumRequestBodyMode.FORMDATA);
        body.setFormdata("field-1", "value 1", "This is value 1");
        body.setFormdata("field-2", "value 2", "This is value 2");
        req = new RequestElement(enumHTTPRequestMethod.POST, "https://postman-echo.com/post");
        req.setBody(body);
        resp = new ResponseElement("NORMAL Formdata", req, "OK", 200, "this is the expected response body");
        pmcTest.addRequest(req, "Test Request", resp);

        body = new BodyElement(enumRequestBodyMode.FORMDATA);
        body.setFormdata("field-1", "value 1", "This is value 1");
        body.setFormdata("field-2", "value 2", "This is value 2");
        req = new RequestElement(enumHTTPRequestMethod.POST, "https://postman-echo.com/post");
        req.setBody(body);
        resp = new ResponseElement("NORMAL Formdata", req, "OK", 200, "this is the expected response body");
        pmcTest2.addRequest(req, "Test Request", resp);

        ItemElement itemReq = pmcTest.getItem("Test Request");
        ItemElement itemReq2 = pmcTest2.getItem("Test Request");

        diffs = itemReq.isEquivalentTo(itemReq2);
        
        assertEquals(0, diffs.size());

    }

    
    /** 
     * @throws Exception
     */
    @Test
    public void testConstructedBodies() throws Exception {

        Collection pmcTest = Collection.pmcFactory();
        pmcTest.setName("TEST Constructed Body with Responses");
        BodyElement body;
        RequestElement req;
        ResponseElement resp;

        body = new BodyElement(enumRequestBodyMode.URLENCODED);
        body.setFormdata("x-field-1", "value 1", "This is value 1");
        body.setFormdata("x-field-2", "value 2", "This is value 2");
        req = new RequestElement(enumHTTPRequestMethod.POST, "https://postman-echo.com/post");
        req.setBody(body);
        resp = new ResponseElement("NORMAL Urlencoded", req, "OK", 200, "this is the expected response body");
        pmcTest.addRequest(req, "URLEncoded body", resp);

        body = new BodyElement(enumRequestBodyMode.TEXT);
        body.setRaw("This is some plain text");
        req = new RequestElement(enumHTTPRequestMethod.POST, "https://postman-echo.com/post");
        req.setBody(body);
        resp = new ResponseElement("NORMAL Plaintext", req, "OK", 200, "this is the expected response body");
        pmcTest.addRequest(req, "Plaintext body", resp);

        body = new BodyElement(enumRequestBodyMode.FORMDATA);
        body.setFormdata("field-1", "value 1", "This is value 1");
        body.setFormdata("field-2", "value 2", "This is value 2");
        req = new RequestElement(enumHTTPRequestMethod.POST, "https://postman-echo.com/post");
        req.setBody(body);
        resp = new ResponseElement("NORMAL Formdata", req, "OK", 200, "this is the expected response body");
        pmcTest.addRequest(req, "Formdata body", resp);

        body = new BodyElement(enumRequestBodyMode.RAW, "{\"thing\":\"value\"}", enumRawBodyLanguage.JSON);
        req = new RequestElement(enumHTTPRequestMethod.POST, "https://postman-echo.com/post");
        req.setBody(body);
        resp = new ResponseElement("NORMAL JSON", req, "OK", 200, "this is the expected response body");
        pmcTest.addRequest(req, "JSON body", resp);

        body = new BodyElement(enumRequestBodyMode.RAW,
                "pm.test(\"Status code is 200\", function () {\n    pm.response.to.have.status(200);\n});\npm.test(\"Response time is less than 800ms\", function () {\n    pm.expect(pm.response.responseTime).to.be.below(800);\n});",
                enumRawBodyLanguage.JAVASCRIPT);
        req = new RequestElement(enumHTTPRequestMethod.POST, "https://postman-echo.com/post");
        req.setBody(body);
        resp = new ResponseElement("NORMAL Javascript", req, "OK", 200, "this is the expected response body");
        pmcTest.addRequest(req, "Javascript body", resp);

        body = new BodyElement(enumRequestBodyMode.FILE);
        body.setBinarySrc("8vhckkNqZ/jenkins-small.png");
        req = new RequestElement(enumHTTPRequestMethod.POST, "https://postman-echo.com/post");
        req.setBody(body);
        resp = new ResponseElement("NORMAL Binary", req, "OK", 200, "this is the expected response body");
        pmcTest.addRequest(req, "Binary body", resp);

        body = new BodyElement(enumRequestBodyMode.RAW, "{<html><body><p>This is some html</p</body></html>}",
                enumRawBodyLanguage.HTML);
        req = new RequestElement(enumHTTPRequestMethod.POST, "https://postman-echo.com/post");
        req.setBody(body);
        resp = new ResponseElement("NORMAL HTML", req, "OK", 200, "this is the expected response body");
        pmcTest.addRequest(req, "HTML body", resp);

        body = new BodyElement(enumRequestBodyMode.RAW, "{<xml><body><p>This is some XML</p</body></xml>}",
                enumRawBodyLanguage.XML);
        req = new RequestElement(enumHTTPRequestMethod.POST, "https://postman-echo.com/post");
        req.setBody(body);
        resp = new ResponseElement("NORMAL XML", req, "OK", 200, "this is the expected response body");
        pmcTest.addRequest(req, "XML body", resp);

        String strGraphQL = "{ \n            launchesPast(limit: 10) {\n              mission_name\n              launch_date_local\n              launch_site {\n                site_name_long\n              }\n              links {\n                article_link\n                video_link\n              }\n              rocket {\n                rocket_name\n              }\n            }\n          }";
        String strVars = "{\"limit\":2}";
        body = new BodyElement(enumRequestBodyMode.GRAPHQL, strGraphQL, enumRawBodyLanguage.GRAPHQL);
        body.setGraphql(strGraphQL, strVars);
        req = new RequestElement(enumHTTPRequestMethod.POST, "https://postman-echo.com/post");
        req.setBody(body);
        resp = new ResponseElement("NORMAL GrapqhQL", req, "OK", 200, "this is the expected response body");
        pmcTest.addRequest(req, "GraphQL body", resp);

        validateAndWriteToFile(pmcTest, new Throwable().getStackTrace()[0]);

    }
    @Test
    public void testAuthObject() {


        PostmanVariable prop;
        
        AuthElement auth = new AuthElement(enumAuthType.OAUTH1);
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
        
            BodyElement body = new BodyElement(enumRequestBodyMode.RAW,"//some javascript",enumRawBodyLanguage.JAVASCRIPT);
            assertSame(enumRequestBodyMode.RAW,body.getMode());

            
            
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

            body = new BodyElement(enumRequestBodyMode.FORMDATA);
            
            try {
                assertNull(body.getRawLanguage());
            }
            catch (IllegalPropertyAccessException e)
            {
                assertTrue("Expected exception thrown", true);
            }

            body = new BodyElement(enumRequestBodyMode.RAW);
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

            body = new BodyElement(enumRequestBodyMode.GRAPHQL);
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


            body = new BodyElement(enumRequestBodyMode.FILE);
            try {
                String file = "some/path/to/file.png";
                body.setBinarySrc(file);
                assertEquals(file, body.getFile());
            }
            catch(Exception e)
            {
                assertTrue("Exception " + e.getMessage(), false);
            }

            body = new BodyElement(enumRequestBodyMode.RAW);
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
        UrlElement url = null;
        try {
            url = new UrlElement(url1);
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
            url = new UrlElement("foo.com",path1);
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
        ArrayList<UrlElement> liUrls = new ArrayList<UrlElement>();

    for(String curUrl: urls) {
        try {
            liUrls.add(new UrlElement(curUrl));
        }
        catch(DuplicateVariableKeyException e) {
            assertTrue("Unexpected duplicate key: " + e.getMessage(), false);
        }
    }
    for(int i = 0; i < liUrls.size(); i++){
        try{
            if(i != 2 && liUrls.get(i).generateURL().equals(urls.get(i))) {
                assertTrue(true);
                
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
        url = new UrlElement("https://foo.com?var1=val1&var2=val2");
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
    
    /** 
     * @throws MalformedURLException
     */
    @Test
    public void testIngestFromUrl() throws MalformedURLException {
        //Good URL
        try {
            pmcTest = Collection.pmcFactory(new URL("https://api.getpostman.com/collections/23889826-a0a8f60c-36c9-4221-9c99-3aa90eb46abe"));
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

        RequestElement req = null;
        try {
           req = new RequestElement(enumHTTPRequestMethod.GET, "https:/postman-echo.com/post?foo=bar");
        }
        catch(DuplicateVariableKeyException e)
        {
            assertTrue("Unexpected duplicate path key" + e.getMessage(), false);
        }
        ResponseElement resp = new ResponseElement("Test Response",req, "OK",200,"This is the body" );
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

        resp = new ResponseElement("Test Response",req, "Not authorize",401,"A completely different body" );
        RequestElement req2 = null;
        try {
            req2 = new RequestElement(enumHTTPRequestMethod.POST, "https://cnn.com");
        }
        catch(DuplicateVariableKeyException e)
        {
            assertTrue("Unexpected duplicate path key" + e.getMessage(), false);
        }
        
        
        ResponseElement newResp = new ResponseElement("Test Response",req2, "Not authorize",401,"A completely different body" );
                
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
        EventElement evt = new EventElement(enumEventType.PRE_REQUEST, "//fake javascript");
        
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
    
    
    
    /** 
     * @throws Exception
     */
    public void testItemElement() throws Exception{
        
        pmcTest = Collection.pmcFactory(new java.io.File(filePath + "/src/main/resources/com/postman/collection/example-cat-facts-with-tests.postman_collection.json"));
        
        
        ItemElement fact = pmcTest.getItem("Get a list of facts");
        ItemElement folder = pmcTest.getItem("get Breeds",true);
        assertNotNull(fact);
        assertNotNull(folder);
        
        assertTrue(fact != null || fact.getName().equals("Get a list of facts"));
        assertTrue(folder != null || folder.getName().equals("Breeds"));

        pmcTest = Collection.pmcFactory(new java.io.File(filePath + "/src/main/resources/com/postman/collection/example-catfact.postman_collection.json"));
        
        ArrayList<ItemElement> folders = pmcTest.getItems(enumItemElementType.FOLDER);
        assertEquals(2, folders.size());
        ArrayList<ItemElement> requests = pmcTest.getItems(enumItemElementType.REQUEST);
        assertEquals(5, requests.size());
        ArrayList<ItemElement> all = pmcTest.getItems(null);
        assertEquals(7, all.size());

        fact = pmcTest.getItem("Add Breed");
        assertNotNull(fact);
        assertEquals("Add Breed", fact.getName());

    }

    
    
    @Test
    public void TestCollectionRequests() {
        pmcTest = Collection.pmcFactory();
        pmcTest.setName("TEST Request Operations");

        RequestElement req = null;
        ItemElement reqItem1 = null;
        //generates spurious "not used" warning
        ItemElement reqItem2 = null;
        ItemElement newFolder = null;

        try {
            req = new RequestElement(enumHTTPRequestMethod.GET, "https://postman-echo.com/get");
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
        req = new RequestElement(enumHTTPRequestMethod.POST, "https://postman-echo.com/post");
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

       


       assertEquals(2, pmcTest.getItems(enumItemElementType.REQUEST).size());

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
       assertEquals(1, pmcTest.getItems(enumItemElementType.REQUEST).size());
       assertNotNull(pmcTest.getItem("GET echo"));


    }

    @Test
    public void testCollectionFolder() {

        pmcTest = Collection.pmcFactory();
        pmcTest.setName("TEST Request Operations");

        RequestElement req;
        ItemElement reqItem1 = null;
        ItemElement reqItem2 = null;
        ItemElement newFolder = null;

        try {
            
            req = new RequestElement(enumHTTPRequestMethod.GET, "https://postman-echo/get");
            reqItem1 = pmcTest.addRequest(req, "GET test");
            req = new RequestElement(enumHTTPRequestMethod.POST, "https://postman-echo/post");
            reqItem2 = pmcTest.addRequest(req, "GET Post");
            newFolder = pmcTest.addFolder("New Folder");
            pmcTest.moveItem(reqItem1, newFolder);
            pmcTest.moveItem(reqItem2, newFolder);
            assertEquals(2, pmcTest.getItems(enumItemElementType.REQUEST).size());
            assertEquals(1, pmcTest.getItems(enumItemElementType.FOLDER).size());
            assertEquals(2, pmcTest.getItem("New Folder").getItems().size());
            pmcTest.removeItem(newFolder);
            assertEquals(0, pmcTest.getItems(enumItemElementType.REQUEST).size());
            assertEquals(0, pmcTest.getItems(enumItemElementType.FOLDER).size());
        }
        catch(Exception e) {
            assertTrue("Unexpected exception: " + e.getMessage(), false);
        }
        

    }

    @Test
    public void testBooleanIntegerVariables() {
        try {
            pmcTest = Collection.pmcFactory(new File(filePath + "/" + resourcePath + "/test-variable-types.postman_collection.json"));
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
            pmcTest = Collection.pmcFactory(new PostmanID("23889826-a0a8f60c-36c9-4221-9c99-3aa90eb46abe"));
        }
        catch(Exception e) {
            assertTrue("Unexpected exception: " + e.getMessage(), false);
        }
        validateAndWriteToFile(pmcTest, new Throwable().getStackTrace()[0]);
        
    }



/** 
 * @param pmcColl
 * @param testMethodInfo
 * @return boolean
 */
public boolean validateAndWriteToFile(Collection pmcColl, StackTraceElement testMethodInfo) {
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



/** 
 * @param directoryToBeDeleted
 * @return boolean
 */
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
    List<UrlElement> liUrls = new ArrayList<UrlElement>(Arrays.asList(new UrlElement[0]));
    String url;
    try {

        liUrls.add(new UrlElement("{{baseUrl}}/{{var1}}.com/:path1/bat.json"));
        liUrls.add(new UrlElement("https://{{var1}}.com/:path1/bat.json?{{var1}}={{var2}}"));

        

        pmcTest = Collection.pmcFactory();
        pmcTest.setName("TEST Resolve Variables");
        pmcTest.addVariable(new PostmanVariable("baseUrl","http://test.com"));
        pmcTest.addVariable(new PostmanVariable("var1", "var1value"));
        pmcTest.addVariable(new PostmanVariable("var2","333"));
        for (int i = 0; i < liUrls.size(); i++) {
            try {
                pmcTest.addRequest(new RequestElement(enumHTTPRequestMethod.GET, liUrls.get(i)), "URL " + (i + 1));
                assertTrue(pmcTest.validate());
                
            } catch (Exception e) {
                e.printStackTrace();
                assertTrue(false);
            }
        }
        
        
        
        
        url = pmcTest.getItem("URL 2").getRequest().getUrl(true);
        //url = pmcTest.resolveVariables(url);
        assertTrue(url.equals("https://var1value.com/:path1/bat.json?var1value=333"));

        pmcTest.getItem("URL 1").getRequest().getUrlElement().setPathVariable(new PostmanVariable("path1", "path1value"));
        assertTrue(pmcTest.getItem("URL 1").getRequest().getUrl(true).equals("http://test.com/var1value.com/path1value/bat.json"));
    }
    catch(Exception e) {
        //assertTrue("Unexpected exception: " + e.getMessage(), false );
        e.printStackTrace();
    }
}

@Test
public void testPostmanVariable() {
    PostmanVariable var1 = new PostmanVariable("var1","var1value");
    PostmanVariable var2 = new PostmanVariable("var2","var2value");
    PostmanVariable var3 = new PostmanVariable("var1","var1value");

    assertFalse(var1.equals(var2));
    assertTrue(var1.equals(var3));


    VariableListMap<PostmanVariable> alVars = new VariableListMap<PostmanVariable>();
    boolean added = false;
    added = alVars.add(var1);
    assertTrue(added);
    added = alVars.add(var2);
    assertTrue(added);
    added = alVars.add(var3);
    assertFalse(added);


    VariableListMap<PostmanVariable> vlMap = new VariableListMap<PostmanVariable>(alVars);
    vlMap.addAll(alVars);
    
    
    
}
    @Test
    public void testParentChain() {

        
        RequestElement req = null;
        CollectionElement parent = null;
        Collection col = null;
        ItemElement folder = null;
        ItemElement reqObj = null;
        try {
            req = new RequestElement(enumHTTPRequestMethod.GET, "https://foo.com/bar/bat.json");
            parent = req.getParent();
            col = req.getCollection();
            assertTrue(parent == null);
            assertTrue(col == null);

            pmcTest = Collection.pmcFactory();
            pmcTest.setName("TEST parent chain");
            reqObj = pmcTest.addRequest(req, "Req 1");

            assertTrue(req.getParent() != null);
            assertTrue(req.getCollection().getName().equals("TEST parent chain"));

            folder = pmcTest.addFolder("Folder 1");
            pmcTest.moveItem(reqObj, folder);
            assertTrue(reqObj.getParent() != null);
            assertTrue(((ItemElement)reqObj).getParent().getName().equals("Folder 1"));
            assertTrue(reqObj.getCollection().getName().equals("TEST parent chain"));

        }
        catch(Exception e) {
            assertTrue("Unexpected exception: " + e.getMessage(), false);
        }








    }


}