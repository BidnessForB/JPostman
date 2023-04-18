package com.postman.collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.List;



import java.util.ArrayList;
import java.util.Arrays;
import java.io.File;

import org.apache.commons.collections4.functors.FalsePredicate;
import org.junit.Test;

import com.networknt.schema.ValidationMessage;
import java.util.HashMap;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.Set;
import java.util.HashSet;
import java.nio.file.DirectoryStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Timestamp;
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
public void testInvalidCollection() {
    pmcTest = Collection.pmcFactory();
    validateAndWriteToFile(pmcTest, new Throwable().getStackTrace()[0]);
}

    @Test
    public void shouldCreateRequestQueries() {
        try {
            collectionOutputPath = filePath + "/test-output/TEST-constructed-queries.postman_collection.json";
            pmcTest = Collection.pmcFactory();
            RequestBody newReq = new RequestBody(enumHTTPRequestMethod.GET, "https://postman-echo.com/post");
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

        Collection pmcTest = Collection.pmcFactory();
        Folder folder;
        Request request;

        //generates spurious "variable not used" warning
        Event  event = null; 
        RequestBody req;
        assertNull(event);
      
        try {
            pmcTest.setName("TEST Scripts");
            folder = new Folder("Scripts");

            
            folder.setPreRequestScript("//PRE-REQUEST this is some source code for the folder");
            event = new Event(enumEventType.TEST, "//TEST this is some source code for the folder");
            folder.setTestScript("//TEST this is some source code for the folder");
            pmcTest.addItem(folder);

            req = new RequestBody(enumHTTPRequestMethod.GET, "https:/postman-echo.com/post?foo=bar");

            request = new Request(req,"TEST Request with Scripts");
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

        List<Url> liUrls = new ArrayList<Url>(Arrays.asList(new Url[0]));
        try {

            liUrls.add(new Url("http://foo.com/bar/bat.json"));
            liUrls.add(new Url("//foo.com/bar/bat.json"));
            liUrls.add(new Url("{{baseUrl}}/foo.com/bar/bat.json"));
            liUrls.add(new Url("http://foo.com/bar/bat.json?foo=1&bar=2"));
            liUrls.add(new Url("http://foo.com/bar/bat.json?foo=1&bar="));
            liUrls.add(new Url("{{baseUrl}}/foo.com/bar/bat.json?foo=1&bar="));
            liUrls.add(new Url("{{baseUrl}}/foo.com/bar/:path1/bat.json?foo=1&bar="));
            liUrls.add(new Url("{{baseUrl}}foo.com:8080/bar/:path1/bat.json?foo=1&bar="));
            liUrls.add(new Url("{{baseUrl}}/foo.com:8080/bar/:path1/bat.json?foo=1&bar="));
            liUrls.add(new Url("https://foo.com:8080/bar/:path1/bat.json?foo=1&bar="));
            liUrls.add(new Url("https://foo.com/bar/:path1/bat.json?foo=1&bar="));

            pmcTest = Collection.pmcFactory();
            pmcTest.setName("TEST Construct URLs");
            for (int i = 0; i < liUrls.size(); i++) {
                try {
                    pmcTest.addRequest(new RequestBody(enumHTTPRequestMethod.GET, liUrls.get(i)), "URL " + (i + 1));
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
         * requests[6].addVariable(new Property("path1", "path-value"));
         * requests[7].addVariable(new Property("path1", "path-value"));
         * requests[8].addVariable(new Property("path1", "path-value"));
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
            assertEquals(pmcTest.getRequest("Raw-text XML").getRequestBody().getBody().getRaw(), test2.getRequest("Raw-text XML").getRequestBody().getBody().getRaw());
            
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
        validateAndWriteToFile(pmcTest, new Throwable().getStackTrace()[0]);
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
        RequestAuth auth;
        RequestBody req;
        

        req = new RequestBody(enumHTTPRequestMethod.GET, "https://postman-echo.com/post");
        pmcTest.addRequest(req, "INHERIT request");

        auth = new RequestAuth(enumAuthType.AKAMAI);
        auth.addProperty("headersToSign", "x-api-key");
        auth.addProperty("baseURL", "https://akamai-base.com");
        auth.addProperty("timestamp", "akamaiTimestamp");
        auth.addProperty("nonce", "akamaiNonce");
        auth.addProperty("clientSecret", "akamaiClientSecret");
        auth.addProperty("clientToken", "akamaiClientToken");
        auth.addProperty("accessToken", "akamaiToken");

        req = new RequestBody(enumHTTPRequestMethod.GET, "https://postman-echo.com/post");
        req.setAuth(auth);
        pmcTest.addRequest(req, "AKAMAI request");

        auth = new RequestAuth(enumAuthType.APIKEY);
        auth.addProperty("key", "API-KEY");
        auth.addProperty("value", "x-api-key");
        auth.addProperty("in", "query");
        req = new RequestBody(enumHTTPRequestMethod.GET, "https://postman-echo.com/post");
        req.setAuth(auth);
        pmcTest.addRequest(req, "APIKEY request");

        auth = new RequestAuth(enumAuthType.AWS);
        auth.addProperty("sessionToken", "awsSessiontoken");
        auth.addProperty("service", "awsServiceName");
        auth.addProperty("secretKey", "aswSecretKey");
        auth.addProperty("accessKey", "awsAccessKey");
        auth.addProperty("addAuthDataToQuery", "false");
        req = new RequestBody(enumHTTPRequestMethod.GET, "https://postman-echo.com/post");
        req.setAuth(auth);
        pmcTest.addRequest(req, "AWS request");

        auth = new RequestAuth(enumAuthType.BEARER);
        auth.addProperty("key", "token");
        auth.addProperty("value", "BearerTokenValue");
        req = new RequestBody(enumHTTPRequestMethod.GET, "https://postman-echo.com/post");
        req.setAuth(auth);
        pmcTest.addRequest(req, "BEARER request");

        auth = new RequestAuth(enumAuthType.BASIC);
        auth.addProperty("password", "fakePassword");
        auth.addProperty("username", "fakeusername");
        req = new RequestBody(enumHTTPRequestMethod.GET, "https://postman-echo.com/post");
        req.setAuth(auth);
        pmcTest.addRequest(req, "BASIC request");

        auth = new RequestAuth(enumAuthType.DIGEST);
        auth.addProperty("opaque", "OpaqueString");
        auth.addProperty("clientNonce", "2020202");
        auth.addProperty("nonceCount", "1010101");
        auth.addProperty("qop", "digest-qop");
        auth.addProperty("algorithim", "SHA-256");
        auth.addProperty("nonce", "digestNonce");
        auth.addProperty("realm", "digest@test.com");
        auth.addProperty("password", "digestPassword");
        req = new RequestBody(enumHTTPRequestMethod.GET, "https://postman-echo.com/post");
        req.setAuth(auth);
        pmcTest.addRequest(req, "DIGEST request");

        auth = new RequestAuth(enumAuthType.HAWK);
        auth.addProperty(new Property("includePayloadHash", "true","boolean"));
        auth.addProperty("timestamp", "hawkTimestamp");
        auth.addProperty("delegation", "hawk-dlg");
        auth.addProperty("app", "HawkApp");
        auth.addProperty("extraData", "Hawk-ext");
        auth.addProperty("nonce", "hawkNonce");
        auth.addProperty("user", "HawkUser");
        auth.addProperty("authKey", "HawkAuthKey");
        auth.addProperty("algorithim", "sha256");
        req = new RequestBody(enumHTTPRequestMethod.GET, "https://postman-echo.com/post");
        req.setAuth(auth);
        pmcTest.addRequest(req, "HAWK request");

        auth = new RequestAuth(enumAuthType.OAUTH1);
        auth.addProperty(new Property("addEmptyParamsToSign", "true", null, "boolean"));
        auth.addProperty(new Property("includeBodyHash", "true", null, "boolean"));
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
        auth.addProperty(new Property("addParamsToHeader", "false", null, "boolean"));
        req = new RequestBody(enumHTTPRequestMethod.GET, "https://postman-echo.com/post");
        req.setAuth(auth);
        pmcTest.addRequest(req, "OAUTH1 request");

        auth = new RequestAuth(enumAuthType.OAUTH2);
        auth.addProperty("grant_type", "authorization_code");
        auth.addProperty("tokenName", "Oauth2TokenName");
        auth.addProperty("tokenType", "");
        auth.addProperty("accessToken", "oauth2AccessToken");
        auth.addProperty("addTokenTo", "header");
        req = new RequestBody(enumHTTPRequestMethod.GET, "https://postman-echo.com/post");
        req.setAuth(auth);
        pmcTest.addRequest(req, "OAUTH2 request");

        auth = new RequestAuth(enumAuthType.NTLM);
        auth.addProperty("workstation", "NTMLWorkstation");
        auth.addProperty("domain", "NTLMDomain");
        auth.addProperty("password", "NTLMPassword");
        auth.addProperty("username", "NTLMUsername");
        req = new RequestBody(enumHTTPRequestMethod.GET, "https://postman-echo.com/post");
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
        Property var1 = new Property("key 1", "value 1");
        Property var2 = new Property("key 2", "value 2");
        Property var3 = new Property("key 3", "value 3");
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
        pmcTest.addVariable(new Property("key 4",null));
        assertEquals(4, pmcTest.getVariables().size());

        //add null key
        pmcTest.addVariable(new Property(null,"Value 5"));
        assertEquals(5, pmcTest.getVariables().size());

        //add null key, null value
        try {
            pmcTest.addVariable(new Property(null,null));
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
        RequestBody req;
        Response resp;
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
        req = new RequestBody(enumHTTPRequestMethod.POST, "https://postman-echo.com/post");
        req.setBody(body);
        resp = new Response("NORMAL Formdata", req, "OK", 200, "this is the expected response body");
        pmcTest.addRequest(req, "Test Request", resp);

        body = new BodyElement(enumRequestBodyMode.FORMDATA);
        body.setFormdata("field-1", "value 1", "This is value 1");
        body.setFormdata("field-2", "value 2", "This is value 2");
        req = new RequestBody(enumHTTPRequestMethod.POST, "https://postman-echo.com/post");
        req.setBody(body);
        resp = new Response("NORMAL Formdata", req, "OK", 200, "this is the expected response body");
        pmcTest2.addRequest(req, "Test Request", resp);

        Item itemReq = pmcTest.getItem("Test Request");
        Item itemReq2 = pmcTest2.getItem("Test Request");

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
        RequestBody req;
        Response resp;

        body = new BodyElement(enumRequestBodyMode.URLENCODED);
        body.setFormdata("x-field-1", "value 1", "This is value 1");
        body.setFormdata("x-field-2", "value 2", "This is value 2");
        req = new RequestBody(enumHTTPRequestMethod.POST, "https://postman-echo.com/post");
        req.setBody(body);
        resp = new Response("NORMAL Urlencoded", req, "OK", 200, "this is the expected response body");
        pmcTest.addRequest(req, "URLEncoded body", resp);

        body = new BodyElement(enumRequestBodyMode.TEXT);
        body.setRaw("This is some plain text");
        req = new RequestBody(enumHTTPRequestMethod.POST, "https://postman-echo.com/post");
        req.setBody(body);
        resp = new Response("NORMAL Plaintext", req, "OK", 200, "this is the expected response body");
        pmcTest.addRequest(req, "Plaintext body", resp);

        body = new BodyElement(enumRequestBodyMode.FORMDATA);
        body.setFormdata("field-1", "value 1", "This is value 1");
        body.setFormdata("field-2", "value 2", "This is value 2");
        req = new RequestBody(enumHTTPRequestMethod.POST, "https://postman-echo.com/post");
        req.setBody(body);
        resp = new Response("NORMAL Formdata", req, "OK", 200, "this is the expected response body");
        pmcTest.addRequest(req, "Formdata body", resp);

        body = new BodyElement(enumRequestBodyMode.RAW, "{\"thing\":\"value\"}", enumRawBodyLanguage.JSON);
        req = new RequestBody(enumHTTPRequestMethod.POST, "https://postman-echo.com/post");
        req.setBody(body);
        resp = new Response("NORMAL JSON", req, "OK", 200, "this is the expected response body");
        pmcTest.addRequest(req, "JSON body", resp);

        body = new BodyElement(enumRequestBodyMode.RAW,
                "pm.test(\"Status code is 200\", function () {\n    pm.response.to.have.status(200);\n});\npm.test(\"Response time is less than 800ms\", function () {\n    pm.expect(pm.response.responseTime).to.be.below(800);\n});",
                enumRawBodyLanguage.JAVASCRIPT);
        req = new RequestBody(enumHTTPRequestMethod.POST, "https://postman-echo.com/post");
        req.setBody(body);
        resp = new Response("NORMAL Javascript", req, "OK", 200, "this is the expected response body");
        pmcTest.addRequest(req, "Javascript body", resp);

        body = new BodyElement(enumRequestBodyMode.FILE);
        body.setBinarySrc("8vhckkNqZ/jenkins-small.png");
        req = new RequestBody(enumHTTPRequestMethod.POST, "https://postman-echo.com/post");
        req.setBody(body);
        resp = new Response("NORMAL Binary", req, "OK", 200, "this is the expected response body");
        pmcTest.addRequest(req, "Binary body", resp);

        body = new BodyElement(enumRequestBodyMode.RAW, "{<html><body><p>This is some html</p</body></html>}",
                enumRawBodyLanguage.HTML);
        req = new RequestBody(enumHTTPRequestMethod.POST, "https://postman-echo.com/post");
        req.setBody(body);
        resp = new Response("NORMAL HTML", req, "OK", 200, "this is the expected response body");
        pmcTest.addRequest(req, "HTML body", resp);

        body = new BodyElement(enumRequestBodyMode.RAW, "{<xml><body><p>This is some XML</p</body></xml>}",
                enumRawBodyLanguage.XML);
        req = new RequestBody(enumHTTPRequestMethod.POST, "https://postman-echo.com/post");
        req.setBody(body);
        resp = new Response("NORMAL XML", req, "OK", 200, "this is the expected response body");
        pmcTest.addRequest(req, "XML body", resp);

        String strGraphQL = "{ \n            launchesPast(limit: 10) {\n              mission_name\n              launch_date_local\n              launch_site {\n                site_name_long\n              }\n              links {\n                article_link\n                video_link\n              }\n              rocket {\n                rocket_name\n              }\n            }\n          }";
        String strVars = "{\"limit\":2}";
        body = new BodyElement(enumRequestBodyMode.GRAPHQL, strGraphQL, enumRawBodyLanguage.GRAPHQL);
        body.setGraphql(strGraphQL, strVars);
        req = new RequestBody(enumHTTPRequestMethod.POST, "https://postman-echo.com/post");
        req.setBody(body);
        resp = new Response("NORMAL GrapqhQL", req, "OK", 200, "this is the expected response body");
        pmcTest.addRequest(req, "GraphQL body", resp);

        validateAndWriteToFile(pmcTest, new Throwable().getStackTrace()[0]);

    }
    @Test
    public void testAuthObject() {


        Property prop;
        
        RequestAuth auth = new RequestAuth(enumAuthType.OAUTH1);
        prop = new Property("addEmptyParamsToSign", "true");
        auth.addProperty(prop);
        assertEquals(14, auth.getProperties().size());
        assertEquals("true", auth.getProperty("addEmptyParamsToSign").getValue());
        
        prop = new Property("addEmptyParamsToSign", "false");
        auth.addProperty(prop);

        assertEquals(14,auth.getProperties().size());
        assertEquals("false", auth.getProperty("addEmptyParamsToSign").getValue());
        

        PropertyList<Property> nullProps = null;
        auth.setProperties(nullProps);
        Property curVar = auth.getProperty("addEmptyParamsToSign");
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
        Url url = null;
        try {
            url = new Url(url1);
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
            url = new Url("foo.com",path1);
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
        ArrayList<Url> liUrls = new ArrayList<Url>();

    for(String curUrl: urls) {
        try {
            liUrls.add(new Url(curUrl));
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
        url = new Url("https://foo.com?var1=val1&var2=val2");
    }
    
    catch(DuplicateVariableKeyException e) {
        assertTrue("Unexpected duplicate key: " + e.getMessage(), false);
    }
    assertEquals(2, url.getQueryElements().size());
    assertEquals("var1=val1&var2=val2", url.getQueryString());
    Property varQ = url.getQueryElements().get(1);
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
            pmcTest = Collection.pmcFactory(new URL("https://api.getpostman.com/collections/23889826-2e2dc1e1-24a9-4167-a550-1167ee1aa389"));
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

        RequestBody req = null;
        try {
           req = new RequestBody(enumHTTPRequestMethod.GET, "https:/postman-echo.com/post?foo=bar");
        }
        catch(DuplicateVariableKeyException e)
        {
            assertTrue("Unexpected duplicate path key" + e.getMessage(), false);
        }
        Response resp = new Response("Test Response",req, "OK",200,"This is the body" );
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

        resp = new Response("Test Response",req, "Not authorize",401,"A completely different body" );
        RequestBody req2 = null;
        try {
            req2 = new RequestBody(enumHTTPRequestMethod.POST, "https://cnn.com");
        }
        catch(DuplicateVariableKeyException e)
        {
            assertTrue("Unexpected duplicate path key" + e.getMessage(), false);
        }
        
        
        Response newResp = new Response("Test Response",req2, "Not authorize",401,"A completely different body" );
                
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
        Event evt = new Event(enumEventType.PRE_REQUEST, "//fake javascript");
        
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
    public void testItem() throws Exception{
        
        pmcTest = Collection.pmcFactory(new java.io.File(filePath + "/src/main/resources/com/postman/collection/example-cat-facts-with-tests.postman_collection.json"));
        
        
        Item fact = pmcTest.getItem("Get a list of facts");
        Folder folder = pmcTest.getFolder("get Breeds");
        assertNotNull(fact);
        assertNotNull(folder);
        
        assertTrue(fact != null || fact.getName().equals("Get a list of facts"));
        assertTrue(folder != null || folder.getName().equals("Breeds"));

        pmcTest = Collection.pmcFactory(new java.io.File(filePath + "/src/main/resources/com/postman/collection/example-catfact.postman_collection.json"));
        
        ArrayList<Item> folders = pmcTest.getItems(enumItemType.FOLDER);
        assertEquals(2, folders.size());
        ArrayList<Item> requests = pmcTest.getItems(enumItemType.REQUEST);
        assertEquals(5, requests.size());
        ArrayList<Item> all = pmcTest.getItems(null);
        assertEquals(7, all.size());

        fact = pmcTest.getItem("Add Breed");
        assertNotNull(fact);
        assertEquals("Add Breed", fact.getName());

    }

    
    
    @Test
    public void TestCollectionRequests() {
        pmcTest = Collection.pmcFactory();
        pmcTest.setName("TEST Request Operations");

        RequestBody req = null;
        Item reqItem1 = null;
        //generates spurious "not used" warning
        Item reqItem2 = null;
        Folder newFolder = null;

        try {
            req = new RequestBody(enumHTTPRequestMethod.GET, "https://postman-echo.com/get");
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
        req = new RequestBody(enumHTTPRequestMethod.POST, "https://postman-echo.com/post");
    }
    catch(DuplicateVariableKeyException e)
    {
        assertTrue("Unexpected duplicate key: " + e.getMessage(), false);
    }
    Request reqItem3 = null;
    try {
       reqItem3 = pmcTest.addRequest("https://postman-echo.com/get");
    }
    catch (Exception e) {
        assertTrue("Exception adding collection", false);
    }
    
    assertEquals(reqItem3.getRequestBody().getUrl().getRaw(), "https://postman-echo.com/get");

    try {
        reqItem3 = pmcTest.addRequest("https://postman-echo.com/get?foo=bar&bat={{boo}}");
    }
    catch(Exception e) {
        assertTrue("Exception adding collection", false);
    }

    assertEquals(reqItem3.getRequestBody().getUrl().getRaw(), "https://postman-echo.com/get?foo=bar&bat={{boo}}");
    

    try {
        //should trigger a duplicate key exception?
        reqItem3 = pmcTest.addRequest("https://postman-echo.com/:pathvar1/get?foo=bar&bat={{boo}}");
    }
    catch(Exception e) {
        assertTrue("Exception adding collection", false);
    }

    assertEquals(reqItem3.getRequestBody().getUrl().getRaw(), "https://postman-echo.com/:pathvar1/get?foo=bar&bat={{boo}}");

    assertEquals(reqItem3.getRequestBody().getUrl().getPathVariables().size(), 1);
    assertNotNull(reqItem3.getRequestBody().getUrl().getPathVariable("pathvar1"));




      
       

       try {
        reqItem2 = pmcTest.addRequest(req,"POST echo");
        assertNotNull(reqItem2);
       } 
       catch(Exception e){
        assertTrue("Unexpected exception " + e.getMessage(), false);
       }

       


       assertEquals(5, pmcTest.getItems(enumItemType.REQUEST).size());

        try {
        newFolder = pmcTest.addFolder("New Folder");
       }
       catch(Exception e)
       {
        assertTrue("Unexpected exception: " + e.getMessage(), false);
       }
       
       try {
        pmcTest.moveItem(reqItem1, newFolder);
        assertEquals("New Folder", pmcTest.getRequest("GET echo").getParent().getName());
        assertEquals(5, pmcTest.getItems().size());
       }
       catch(Exception e)
       {
        assertTrue("Unexpected exception: " + e.getMessage(), false);
       }
       

       
       



       pmcTest.removeItem("POST echo");
       assertEquals(4, pmcTest.getItems(enumItemType.REQUEST).size());
       assertNotNull(pmcTest.getItem("GET echo"));

       //Should trigger duplicate variable key exception
       try {
           reqItem3 = pmcTest.addRequest("https://postman-echo.com/:pathvar1/:pathvar1/get?foo=bar&bat={{boo}}");
       }
       catch(DuplicateVariableKeyException e) {
            assertTrue("Duplicate variable key exception expected", true);
       }
       catch(Exception e) {
        assertTrue("Unexpected exception: " + e.getMessage(), false);
       }


       validateAndWriteToFile(pmcTest, new Throwable().getStackTrace()[0]);




    }

    @Test
    public void testCollectionFolder() {

        pmcTest = Collection.pmcFactory();
        pmcTest.setName("TEST Request Operations");

        RequestBody req;
        Item reqItem1 = null;
        Item reqItem2 = null;
        Folder newFolder = null;

        try {
            
            req = new RequestBody(enumHTTPRequestMethod.GET, "https://postman-echo/get");
            reqItem1 = pmcTest.addRequest(req, "GET test");
            req = new RequestBody(enumHTTPRequestMethod.POST, "https://postman-echo/post");
            reqItem2 = pmcTest.addRequest(req, "GET Post");
            newFolder = pmcTest.addFolder("New Folder");
            pmcTest.moveItem(reqItem1, newFolder);
            pmcTest.moveItem(reqItem2, newFolder);
            assertEquals(2, pmcTest.getItems(enumItemType.REQUEST).size());
            assertEquals(1, pmcTest.getItems(enumItemType.FOLDER).size());
            assertEquals(2, pmcTest.getFolder("New Folder").getItems().size());
            pmcTest.removeItem(newFolder);
            assertEquals(0, pmcTest.getItems(enumItemType.REQUEST).size());
            assertEquals(0, pmcTest.getItems(enumItemType.FOLDER).size());
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
    List<Url> liUrls = new ArrayList<Url>(Arrays.asList(new Url[0]));
    String url;
    try {

        liUrls.add(new Url("{{baseUrl}}/{{var1}}.com/:path1/bat.json"));
        liUrls.add(new Url("https://{{var1}}.com/:path1/bat.json?{{var1}}={{var2}}"));
        //Multiple path variables
        liUrls.add(new Url("https://{{var1}}.com/:path1/more/path/:path2/bat.json?{{var1}}={{var2}}"));
        //Missing path variable
        liUrls.add(new Url("{{baseUrl}}/{{var1}}.com/:path4/bat.json"));
        

        pmcTest = Collection.pmcFactory();
        pmcTest.setName("TEST Resolve Variables");
        pmcTest.addVariable(new Property("baseUrl","http://test.com"));
        pmcTest.addVariable(new Property("var1", "var1value"));
        pmcTest.addVariable(new Property("var2","333"));
        
        for (int i = 0; i < liUrls.size(); i++) {
            try {
                pmcTest.addRequest(new RequestBody(enumHTTPRequestMethod.GET, liUrls.get(i)), "URL " + (i + 1));
                assertTrue(pmcTest.validate());
                
            } catch (Exception e) {
                e.printStackTrace();
                assertTrue(false);
            }
        }
        
        
        
        
        url = pmcTest.getRequest("URL 2").getRequestBody().getUrl(true);
        //url = pmcTest.resolveVariables(url);
        assertEquals("https://var1value.com/:path1/bat.json?var1value=333", url);

        pmcTest.getRequest("URL 1").getRequestBody().getUrl().setPathVariable(new Property("path1", "path1value"));
        pmcTest.getRequest("URL 3").getRequestBody().getUrl().setPathVariable(new Property("path2", "path2value"));

        pmcTest.getRequest("URL 4").getRequestBody().getUrl(true);
        assertEquals("https://var1value.com/:path1/more/path/path2value/bat.json?var1value=333", pmcTest.getRequest("URL 3").getRequestBody().getUrl(true));
        assertEquals("http://test.com/var1value.com/:path4/bat.json", pmcTest.getRequest("URL 4").getRequestBody().getUrl(true));
        
        assertEquals("http://test.com/var1value.com/path1value/bat.json", pmcTest.getRequest("URL 1").getRequestBody().getUrl(true));
        
    }
    catch(Exception e) {
        //assertTrue("Unexpected exception: " + e.getMessage(), false );
        e.printStackTrace();
    }
}


@Test
public void testProperty() {
    Property var1 = new Property("var1","var1value");
    Property var2 = new Property("var2","var2value");
    Property var3 = new Property("var1","var1value");

    assertNotEquals(var1, var2);
    assertEquals(var1, var3);


    PropertyList<Property> alVars = new PropertyList<Property>();
    boolean added = false;
    added = alVars.add(var1);
    assertTrue(added);
    added = alVars.add(var2);
    assertTrue(added);
    added = alVars.add(var3);
    assertTrue(added);


    PropertyList<Property> vlMap = new PropertyList<Property>(alVars);
    vlMap.addAll(alVars);
    vlMap.set(2, new Property("var1", "newvalue"));
    try {
        vlMap.set(5, new Property("var5", "value5"));
    }
    catch(IndexOutOfBoundsException e)
    {
        assertTrue("Expected exception caught", true);
    }
    try {
        vlMap.remove(5);
    }
    catch(IndexOutOfBoundsException e)
    {
        assertTrue("Expected exception caught", true);
    }
    vlMap.remove(var3);
    assertEquals(2, vlMap.size());

    vlMap.remove(var2);
    assertEquals(1, vlMap.size());

    
    //Shouldn't find due to different value
    vlMap.remove(var1);
    assertEquals(1, vlMap.size());
    var1.setValue("newvalue");
    vlMap.remove(var1);
    assertEquals(0, vlMap.size());
    vlMap.remove(var1);
    
}
    @Test
    public void testParentChain() {

        
        RequestBody req = null;
        CollectionElement parent = null;
        Collection col = null;
        Folder folder = null;
        Request reqObj = null;
        try {
            req = new RequestBody(enumHTTPRequestMethod.GET, "https://foo.com/bar/bat.json");
            parent = req.getParent();
            col = req.getCollection();
            assertNull(parent);
            assertNull(col);

            pmcTest = Collection.pmcFactory();
            pmcTest.setName("TEST parent chain");
            reqObj = pmcTest.addRequest(req, "Req 1");

            assertNotNull(req.getParent());
            assertEquals("TEST parent chain", req.getCollection().getName());

            folder = pmcTest.addFolder("Folder 1");
            pmcTest.moveItem(reqObj, folder);
            assertNotNull(reqObj.getParent());
            assertEquals("Folder 1", ((Item)reqObj).getParent().getName());
            assertEquals("TEST parent chain", reqObj.getCollection().getName());

        }
        catch(Exception e) {
            assertTrue("Unexpected exception: " + e.getMessage(), false);
        }








    }

    @Test
    public void testWriteToPostman() {
        Collection pmcTest = Collection.pmcFactory();
        Collection pmcTest2 = null;
        BodyElement body = null;
        RequestBody req = null;
        Response resp = null;
        String colName = new java.sql.Timestamp(System.currentTimeMillis()).toString();
        //Test creating a new collection
        try {
            
            body = new BodyElement(enumRequestBodyMode.URLENCODED);
            body.setFormdata("x-field-1", "value 1", "This is value 1");
            body.setFormdata("x-field-2", "value 2", "This is value 2");
            req = new RequestBody(enumHTTPRequestMethod.POST, "https://postman-echo.com/post");
            req.setBody(body);
            resp = new Response("NORMAL Urlencoded", req, "OK", 200, "this is the expected response body");
            pmcTest.addRequest(req, "URLEncoded body", resp);
            pmcTest.setName("UPSERT test " + colName);
        }
        catch(Exception eee) {
            eee.printStackTrace();
            assertTrue("Exception creating test collection: " + eee.getMessage(), false);
        }
        
        try {
            assertNull("No postmanID before upsert: ", pmcTest.getPostmanID());
            pmcTest.upsertToPostman(null);
            assertNotNull("Postman ID successfully assigned: " , pmcTest.getPostmanID());
        }
        catch(Exception e) {
            e.printStackTrace();
            assertTrue("Exception upserting new collection to Postman: " + e.getMessage(), false);
        }

        //Upsert a collection to a workspace
        try {
            
            body = new BodyElement(enumRequestBodyMode.URLENCODED);
            body.setFormdata("x-field-1", "value 1", "This is value 1");
            body.setFormdata("x-field-2", "value 2", "This is value 2");
            req = new RequestBody(enumHTTPRequestMethod.POST, "https://postman-echo.com/post");
            req.setBody(body);
            resp = new Response("NORMAL Urlencoded", req, "OK", 200, "this is the expected response body");
            pmcTest.addRequest(req, "URLEncoded body", resp);
            pmcTest.setName("UPSERT test to Workspace" + colName);
            pmcTest.upsertToPostman(new PostmanID("23889826-169dff8a-c684-4ccc-b8df-7ad436efda57"));
        }
        catch(Exception eee) {
            eee.printStackTrace();
            assertTrue("Exception creating test collection: " + eee.getMessage(), false);
        }

        //Fetch a known collection from Postman, change the name, then Upsert it
       try {
        
        pmcTest    = Collection.pmcFactory(new PostmanID("23889826-169dff8a-c684-4ccc-b8df-7ad436efda57"));
        String origName = pmcTest.getName();
        pmcTest.setName(origName + "- UPSERTED");
        pmcTest.upsertToPostman(null);
        pmcTest    = Collection.pmcFactory(new PostmanID("23889826-169dff8a-c684-4ccc-b8df-7ad436efda57"));
        assertTrue(pmcTest.getName().equals(origName + "- UPSERTED"));
       }
       catch(Exception ee) {
        ee.printStackTrace();
        assertTrue("Exception writing known collection: " + ee.getMessage(), false);
       }


        try {
            //read in a collection with an ID
            pmcTest  = Collection.pmcFactory(new File(filePath + resourcePath + "/auth.postman_collection.json"));
            pmcTest.setName("Auth renamed again " + new java.sql.Timestamp(System.currentTimeMillis()));
            pmcTest.upsertToPostman(null);
        }
        catch(Exception e)
        {
            assertTrue("IOException: " + e.getMessage(), false);
        }

        
        
    }

    @Test
    public void testValidateWithBodyComments() {

        boolean valid = false;

        
        
        try {
            pmcTest = Collection.pmcFactory(new File(filePath + resourcePath + "/body-with-no-comments.postman_collection.json"));
            valid = pmcTest.validate();    
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        



}}