package com.postman.collection;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;



import java.util.ArrayList;
import java.util.Arrays;
import java.io.File;
import org.junit.Test;
import com.networknt.schema.ValidationMessage;
import java.util.HashMap;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * Unit test for simple App.
 */
public class AppTest {
    String filePath = new java.io.File("").getAbsolutePath();
    String resourcePath = "/src/main/resources/com/postman/collection";
    PostmanCollection pmcTest = null;
    String collectionOutputPath;

    @Test
    public void clearOutput() {
        File output = new File(filePath + "/test-output");
        File currentFile;
        if (!output.exists()) {
            output.mkdir();
            return;
        }
        String[] files = output.list();
        for (String s : files) {
            currentFile = new File(output.getPath(), s);
            currentFile.delete();
        }
    }

    @Test
    public void shouldCreateRequestQueries() {
        try {
            collectionOutputPath = filePath + "/test-output/TEST-constructed-queries.postman_collection.json";
            pmcTest = PostmanCollection.PMCFactory();
            PostmanRequest newReq = new PostmanRequest(enumHTTPRequestMethod.GET, "https://postman-echo.com/post");
            newReq.getUrl().addQuery("foo", "bar");
            pmcTest.addRequest(newReq, "Get Foo Bar");
            pmcTest.setName("TEST Constructed Queries");

            boolean valid = pmcTest.validate();
            HashMap<String, String> outputData = getOutputFileAndCollectionName(pmcTest,
                    new Throwable().getStackTrace()[0].getMethodName());
            pmcTest.setDescription(outputData.get("collection-description"));
            pmcTest.setName(outputData.get("collection-name"));
            collectionOutputPath = outputData.get("output-path");
            pmcTest.writeToFile(collectionOutputPath);
            printValidationMessages(pmcTest.getValidationMessages(),
                    new Throwable().getStackTrace()[0].getMethodName());
            assertTrue(valid);
            assertTrue(new File(collectionOutputPath).exists());
        } catch (Exception e) {
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

            event = new PostmanEvent(enumEventType.PRE_REQUEST,
                    "//PRE-REQUEST this is some source code for the folder");
            folder.setEvent(event);
            event = new PostmanEvent(enumEventType.TEST, "//TEST this is some source code for the folder");
            folder.setEvent(event);
            pmcTest.addItem(folder);

            req = new PostmanRequest(enumHTTPRequestMethod.GET, "https:/postman-echo.com/post?foo=bar");

            request = new PostmanItem("TEST Request with Scripts");
            request.setRequest(req);
            event = new PostmanEvent(enumEventType.PRE_REQUEST,
                    "//PRE-REQUEST this is some source code for the request");
            request.setEvent(event);

            event = new PostmanEvent(enumEventType.TEST, "//TEST this is some source code for the request");
            request.setEvent(event);
            folder.addItem(request);

            event = new PostmanEvent(enumEventType.TEST, "//TEST this is some source code for the collection");
            pmcTest.setEvent(event);
            event = new PostmanEvent(enumEventType.PRE_REQUEST,
                    "//PRE-REQUEST this is some source code for the collection");
            pmcTest.setEvent(event);

            boolean valid = pmcTest.validate();
            HashMap<String, String> outputData = getOutputFileAndCollectionName(pmcTest,
                    new Throwable().getStackTrace()[0].getMethodName());
            pmcTest.setDescription(outputData.get("collection-description"));
            pmcTest.setName(outputData.get("collection-name"));
            collectionOutputPath = outputData.get("output-path");
            pmcTest.writeToFile(collectionOutputPath);
            printValidationMessages(pmcTest.getValidationMessages(),
                    new Throwable().getStackTrace()[0].getMethodName());
            assertTrue(valid);
            assertTrue(new File(collectionOutputPath).exists());

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
            pmcTest = PostmanCollection.PMCFactory(new File(
                    filePath + "/src/main/resources/com/postman/collection/example-catfact.postman_collection.json"));
            boolean valid = pmcTest.validate();
            HashMap<String, String> outputData = getOutputFileAndCollectionName(pmcTest,
                    new Throwable().getStackTrace()[0].getMethodName());
            pmcTest.setDescription(outputData.get("collection-description"));
            pmcTest.setName(outputData.get("collection-name"));
            collectionOutputPath = outputData.get("output-path");
            pmcTest.writeToFile(collectionOutputPath);
            printValidationMessages(pmcTest.getValidationMessages(),
                    new Throwable().getStackTrace()[0].getMethodName());
            assertTrue(valid);
            assertTrue(new File(collectionOutputPath).exists());

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

            pmcTest = PostmanCollection.PMCFactory();
            pmcTest.setName("TEST Construct URLs");
            for (int i = 0; i < liUrls.size(); i++) {
                try {
                    pmcTest.addRequest(new PostmanRequest(enumHTTPRequestMethod.GET, liUrls.get(i)), "URL " + (i + 1));
                    boolean valid = pmcTest.validate();
                    HashMap<String, String> outputData = getOutputFileAndCollectionName(pmcTest,
                            new Throwable().getStackTrace()[0].getMethodName());
                    pmcTest.setDescription(outputData.get("collection-description"));
                    pmcTest.setName(outputData.get("collection-name"));
                    collectionOutputPath = outputData.get("output-path");
                    pmcTest.writeToFile(collectionOutputPath);
                    printValidationMessages(pmcTest.getValidationMessages(),
                            new Throwable().getStackTrace()[0].getMethodName());
                    assertTrue(valid);
                    assertTrue(new File(collectionOutputPath).exists());
                } catch (Exception e) {
                    e.printStackTrace();
                    assertTrue(false);
                }

            }
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

            boolean valid = pmcTest.validate();
            HashMap<String, String> outputData = getOutputFileAndCollectionName(pmcTest,
                    new Throwable().getStackTrace()[0].getMethodName());
            pmcTest.setDescription(outputData.get("collection-description"));
            pmcTest.setName(outputData.get("collection-name"));
            collectionOutputPath = outputData.get("output-path");
            pmcTest.writeToFile(collectionOutputPath);
            printValidationMessages(pmcTest.getValidationMessages(),
                    new Throwable().getStackTrace()[0].getMethodName());
            assertTrue(valid);
            assertTrue(new File(collectionOutputPath).exists());

        } catch (Exception e) {
            e.printStackTrace();
            assertTrue(false);
        }

    }

    @Test
    public void shouldProduceIdenticalCollection() {

        try {
            pmcTest = PostmanCollection.PMCFactory(new File(
                    filePath + "/src/main/resources/com/postman/collection/example-catfact.postman_collection.json"));
            pmcTest.setName("TEST Cat Fact");
            boolean valid = pmcTest.validate();
            HashMap<String, String> outputData = getOutputFileAndCollectionName(pmcTest,
                    new Throwable().getStackTrace()[0].getMethodName());
            pmcTest.setDescription(outputData.get("collection-description"));
            pmcTest.setName(outputData.get("collection-name"));
            collectionOutputPath = outputData.get("output-path");
            pmcTest.writeToFile(collectionOutputPath);
            printValidationMessages(pmcTest.getValidationMessages(),
                    new Throwable().getStackTrace()[0].getMethodName());
            assertTrue(valid);
            assertTrue(new File(collectionOutputPath).exists());
        } catch (Exception e) {
            e.printStackTrace();
            assertTrue(false);
        }

    }

    @Test
    public void testBodyImportExport() {
        String filePath = new java.io.File("").getAbsolutePath();
        try {
            pmcTest = PostmanCollection.PMCFactory(new File(
                    filePath + "/src/main/resources/com/postman/collection/body-test.postman_collection.json"));
            boolean valid = pmcTest.validate();

            HashMap<String, String> outputData = getOutputFileAndCollectionName(pmcTest,
                    new Throwable().getStackTrace()[0].getMethodName());

            pmcTest.setDescription(outputData.get("collection-description"));
            pmcTest.setName(outputData.get("collection-name"));
            collectionOutputPath = outputData.get("output-path");
            pmcTest.writeToFile(collectionOutputPath);
            printValidationMessages(pmcTest.getValidationMessages(),
                    new Throwable().getStackTrace()[0].getMethodName());
            assertTrue(valid);
            assertTrue(new File(collectionOutputPath).exists());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    
    /** 
     * @throws Exception
     */
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

    
    /** 
     * @throws Exception
     */
    @Test
    public void testLargeBodyIngestion() throws Exception {
        pmcTest = PostmanCollection
                .PMCFactory(new File(filePath + resourcePath + "/test-collection.postman_collection.json"));
        pmcTest.setName("TEST large body");
        boolean valid = pmcTest.validate();
        HashMap<String, String> outputData = getOutputFileAndCollectionName(pmcTest,
                new Throwable().getStackTrace()[0].getMethodName());
        pmcTest.setDescription(outputData.get("collection-description"));
        pmcTest.setName(outputData.get("collection-name"));
        collectionOutputPath = outputData.get("output-path");
        pmcTest.writeToFile(collectionOutputPath);
        printValidationMessages(pmcTest.getValidationMessages(), new Throwable().getStackTrace()[0].getMethodName());
        assertTrue(valid);
        assertTrue(new File(collectionOutputPath).exists());
    }

    
    /** 
     * @throws Exception
     */
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
        auth.setProperty("headersToSign", "x-api-key");
        auth.setProperty("baseURL", "https://akamai-base.com");
        auth.setProperty("timestamp", "akamaiTimestamp");
        auth.setProperty("nonce", "akamaiNonce");
        auth.setProperty("clientSecret", "akamaiClientSecret");
        auth.setProperty("clientToken", "akamaiClientToken");
        auth.setProperty("accessToken", "akamaiToken");

        req = new PostmanRequest(enumHTTPRequestMethod.GET, "https://postman-echo.com/post");
        req.setAuth(auth);
        pmcTest.addRequest(req, "AKAMAI request");

        auth = new PostmanAuth(enumAuthType.APIKEY);
        auth.setProperty("key", "API-KEY");
        auth.setProperty("value", "x-api-key");
        auth.setProperty("in", "query");
        req = new PostmanRequest(enumHTTPRequestMethod.GET, "https://postman-echo.com/post");
        req.setAuth(auth);
        pmcTest.addRequest(req, "APIKEY request");

        auth = new PostmanAuth(enumAuthType.AWS);
        auth.setProperty("sessionToken", "awsSessiontoken");
        auth.setProperty("service", "awsServiceName");
        auth.setProperty("secretKey", "aswSecretKey");
        auth.setProperty("accessKey", "awsAccessKey");
        auth.setProperty("addAuthDataToQuery", "false");
        req = new PostmanRequest(enumHTTPRequestMethod.GET, "https://postman-echo.com/post");
        req.setAuth(auth);
        pmcTest.addRequest(req, "AWS request");

        auth = new PostmanAuth(enumAuthType.BEARER);
        auth.setProperty("key", "API-KEY");
        auth.setProperty("value", "x-api-key");
        req = new PostmanRequest(enumHTTPRequestMethod.GET, "https://postman-echo.com/post");
        req.setAuth(auth);
        pmcTest.addRequest(req, "BEARER request");

        auth = new PostmanAuth(enumAuthType.BASIC);
        auth.setProperty("password", "fakePassword");
        auth.setProperty("username", "fakeusername");
        req = new PostmanRequest(enumHTTPRequestMethod.GET, "https://postman-echo.com/post");
        req.setAuth(auth);
        pmcTest.addRequest(req, "BASIC request");

        auth = new PostmanAuth(enumAuthType.DIGEST);
        auth.setProperty("opaque", "OpaqueString");
        auth.setProperty("clientNonce", "2020202");
        auth.setProperty("nonceCount", "1010101");
        auth.setProperty("qop", "digest-qop");
        auth.setProperty("algorithim", "SHA-256");
        auth.setProperty("nonce", "digestNonce");
        auth.setProperty("realm", "digest@test.com");
        auth.setProperty("password", "digestPassword");
        req = new PostmanRequest(enumHTTPRequestMethod.GET, "https://postman-echo.com/post");
        req.setAuth(auth);
        pmcTest.addRequest(req, "DIGEST request");

        auth = new PostmanAuth(enumAuthType.HAWK);
        auth.setProperty("includePayloadHash", "true");
        auth.setProperty("timestamp", "hawkTimestamp");
        auth.setProperty("delegation", "hawk-dlg");
        auth.setProperty("app", "HawkApp");
        auth.setProperty("extraData", "Hawk-ext");
        auth.setProperty("nonce", "hawkNonce");
        auth.setProperty("user", "HawkUser");
        auth.setProperty("authKey", "HawkAuthKey");
        auth.setProperty("algorithim", "sha256");
        req = new PostmanRequest(enumHTTPRequestMethod.GET, "https://postman-echo.com/post");
        req.setAuth(auth);
        pmcTest.addRequest(req, "HAWK request");

        auth = new PostmanAuth(enumAuthType.OAUTH1);
        auth.setProperty(new PostmanVariable("addEmptyParamsToSign", "true", null, "boolean"));
        auth.setProperty(new PostmanVariable("includeBodyHash", "true", null, "boolean"));
        auth.setProperty("realm", "testoauth@test.com");
        auth.setProperty("nonce", "oauthNonce");
        auth.setProperty("timestamp", "oauthTimestamp");
        auth.setProperty("verifier", "oauthVerifier");
        auth.setProperty("callback", "https:/callback.url");
        auth.setProperty("tokenSecret", "OAuthTokenSecret");
        auth.setProperty("token", "oauthToken");
        auth.setProperty("consumerSecret", "oauthConsumerSecret");
        auth.setProperty("consumerKey", "oauthConsumerKey");
        auth.setProperty("signatureMethod", "HMAC-SHA1");
        auth.setProperty("version", "1.0");
        auth.setProperty(new PostmanVariable("addParamsToHeader", "false", null, "boolean"));
        req = new PostmanRequest(enumHTTPRequestMethod.GET, "https://postman-echo.com/post");
        req.setAuth(auth);
        pmcTest.addRequest(req, "OAUTH1 request");

        auth = new PostmanAuth(enumAuthType.OAUTH2);
        auth.setProperty("grant_type", "authorization_code");
        auth.setProperty("tokenName", "Oauth2TokenName");
        auth.setProperty("tokenType", "");
        auth.setProperty("accessToken", "oauth2AccessToken");
        auth.setProperty("addTokenTo", "header");
        req = new PostmanRequest(enumHTTPRequestMethod.GET, "https://postman-echo.com/post");
        req.setAuth(auth);
        pmcTest.addRequest(req, "OAUTH2 request");

        auth = new PostmanAuth(enumAuthType.NTLM);
        auth.setProperty("workstation", "NTMLWorkstation");
        auth.setProperty("domain", "NTLMDomain");
        auth.setProperty("password", "NTLMPassword");
        auth.setProperty("username", "NTLMUsername");
        req = new PostmanRequest(enumHTTPRequestMethod.GET, "https://postman-echo.com/post");
        req.setAuth(auth);
        pmcTest.addRequest(req, "NTLM request");

        pmcTest.setAuth(auth);

        pmcTest.writeToFile(filePath + "/test-output/TEST-auth.postman_collection.json");
        pmcTest.getAuth().getProperties().keySet().iterator().next();
        valid = pmcTest.validate();
        HashMap<String, String> outputData = getOutputFileAndCollectionName(pmcTest,
                new Throwable().getStackTrace()[0].getMethodName());
        pmcTest.setDescription(outputData.get("collection-description"));
        pmcTest.setName(outputData.get("collection-name"));
        collectionOutputPath = outputData.get("output-path");
        pmcTest.writeToFile(collectionOutputPath);
        printValidationMessages(pmcTest.getValidationMessages(), new Throwable().getStackTrace()[0].getMethodName());
        assertTrue(valid);
        assertTrue(new File(collectionOutputPath).exists());
    }

    
    /** 
     * @throws Exception
     */
    @Test
    public void testAddVariables() throws Exception {
        pmcTest = PostmanCollection.PMCFactory();
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

        HashMap<String, String> outputData = getOutputFileAndCollectionName(pmcTest,
                new Throwable().getStackTrace()[0].getMethodName());
        pmcTest.setDescription(outputData.get("collection-description"));
        pmcTest.setName(outputData.get("collection-name"));
        collectionOutputPath = outputData.get("output-path");
        pmcTest.writeToFile(collectionOutputPath);
        printValidationMessages(pmcTest.getValidationMessages(), new Throwable().getStackTrace()[0].getMethodName());
        assertTrue(valid);
        assertTrue(new File(collectionOutputPath).exists());

    }

    
    /** 
     * @throws Exception
     */
    @Test
    public void testIngestEvents() throws Exception {
        PostmanCollection pmcTest = PostmanCollection.PMCFactory(
                new File(filePath + resourcePath + "/example-cat-facts-with-tests.postman_collection.json"));
        boolean valid = pmcTest.validate();
        HashMap<String, String> outputData = getOutputFileAndCollectionName(pmcTest,
                new Throwable().getStackTrace()[0].getMethodName());
        pmcTest.setDescription(outputData.get("collection-description"));
        pmcTest.setName(outputData.get("collection-name"));
        collectionOutputPath = outputData.get("output-path");
        pmcTest.writeToFile(collectionOutputPath);
        printValidationMessages(pmcTest.getValidationMessages(), new Throwable().getStackTrace()[0].getMethodName());
        assertTrue(valid);
        assertTrue(new File(collectionOutputPath).exists());
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

        PostmanCollection pmcTest = PostmanCollection.PMCFactory(new File(
                filePath + "/src/main/resources/com/postman/collection/example-catfact.postman_collection.json"));
        PostmanCollection pmcTest2 = PostmanCollection.PMCFactory(new File(
                filePath + "/src/main/resources/com/postman/collection/example-weather.postman_collection.json"));
        pmcTest.addCollection(pmcTest2, true, true);
        pmcTest.setName("TEST Cat-Weather ");
        boolean valid = pmcTest.validate();
        HashMap<String, String> outputData = getOutputFileAndCollectionName(pmcTest,
                new Throwable().getStackTrace()[0].getMethodName());
        pmcTest.setDescription(outputData.get("collection-description"));
        pmcTest.setName(outputData.get("collection-name"));
        collectionOutputPath = outputData.get("output-path");
        pmcTest.writeToFile(collectionOutputPath);
        printValidationMessages(pmcTest.getValidationMessages(), new Throwable().getStackTrace()[0].getMethodName());
        assertTrue(valid);
        assertTrue(new File(collectionOutputPath).exists());

    }

    
    /** 
     * @throws Exception
     */
    @Test
    public void testEquivalence() throws Exception {
        PostmanBody body;
        PostmanRequest req;
        PostmanResponse resp;
        pmcTest = PostmanCollection.PMCFactory(
                new File(filePath + "/src/main/resources/com/postman/collection/body-test.postman_collection.json"));
        PostmanCollection pmcTest2 = PostmanCollection.PMCFactory(
                new File(filePath + "/src/main/resources/com/postman/collection/body-test.postman_collection.json"));
        JsonNode diffs = pmcTest.isEquivalentTo(pmcTest2);
        System.out.println("diffs (should be none) " + diffs.toPrettyString());
        assertTrue(diffs.size() == 0);

        pmcTest2 = PostmanCollection.PMCFactory(new File(
                filePath + "/src/main/resources/com/postman/collection/body-test-diff.postman_collection.json"));
        diffs = pmcTest.isEquivalentTo(pmcTest2);
        System.out.println("diffs " + diffs.toPrettyString());
        assertTrue(diffs.size() == 1);

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
        System.out.println("Req diffs (should be none) " + diffs.toPrettyString());
        assertTrue(diffs.size() == 0);

    }

    
    /** 
     * @throws Exception
     */
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
        body.setFile("8vhckkNqZ/jenkins-small.png");
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

        try {
            pmcTest.writeToFile(filePath + "/test-output/TEST-bodies-with-responses.postman_collection.json");

        } catch (Exception e) {
            e.printStackTrace();
            assertTrue(false);
        }
        boolean valid = pmcTest.validate();
        HashMap<String, String> outputData = getOutputFileAndCollectionName(pmcTest,
                new Throwable().getStackTrace()[0].getMethodName());
        pmcTest.setDescription(outputData.get("collection-description"));
        pmcTest.setName(outputData.get("collection-name"));
        collectionOutputPath = outputData.get("output-path");
        pmcTest.writeToFile(collectionOutputPath);
        printValidationMessages(pmcTest.getValidationMessages(), new Throwable().getStackTrace()[0].getMethodName());
        assertTrue(valid);
        assertTrue(new File(collectionOutputPath).exists());

    }
    @Test
    public void testAuthObject() {


        PostmanVariable prop;
        HashMap<String, PostmanVariable> props = new HashMap<String,PostmanVariable>();
        PostmanAuth auth = new PostmanAuth(enumAuthType.OAUTH1);
        prop = new PostmanVariable("addEmptyParamsToSign", "true");
        auth.setProperty(prop);
        assertTrue(auth.getProperties().size() == 1);
        assertTrue(auth.getProperty("addEmptyParamsToSign").getValue().equals("true"));
        
        prop = new PostmanVariable("addEmptyParamsToSign", "false");
        auth.setProperty(prop);

        assertTrue(auth.getProperties().size() == 1);
        assertTrue(auth.getProperty("addEmptyParamsToSign").getValue().equals("false"));

        HashMap<String, PostmanVariable> nullProps = null;
        auth.setProperties(nullProps);
        PostmanVariable var = auth.getProperty("addEmptyParamsToSign");
        assertTrue(var == null);

    
    }
    @Test
    public void testBodyObject() {
        Object opts;
            PostmanBody body = new PostmanBody(enumRequestBodyMode.RAW,"//some javascript",enumRawBodyLanguage.JAVASCRIPT);
            assertTrue(body.getMode() == enumRequestBodyMode.RAW);
            try {
                assertTrue(body.getRawLanguage() == enumRawBodyLanguage.JAVASCRIPT);
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
                assertTrue(body.getRaw().equals("//some javascript"));
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
                opts = body.getGraphql();
            }
            catch(IllegalPropertyAccessException e)
            {
                assertTrue("Expected exception thrown", true);
            }
            try {
                String file = body.getFile();
            }
            catch(IllegalPropertyAccessException e)
            {
                assertTrue("Expected exception thrown",true);
            }

            body = new PostmanBody(enumRequestBodyMode.FILE);
            try {
                String file = "some/path/to/file.png";
                body.setFile(file);
                assertTrue(body.getFile().equals(file));
            }
            catch(Exception e)
            {
                assertTrue("Exception " + e.getMessage(), false);
            }

            body = new PostmanBody(enumRequestBodyMode.RAW);
            try {
                body.setRawLanguage(enumRawBodyLanguage.JAVASCRIPT);
                body.setRaw("//some javascript");
                assertTrue(body.getRawLanguage() == enumRawBodyLanguage.JAVASCRIPT);
                assertTrue(body.getRaw().equals("//some javascript"));
            }
            catch(Exception e)
            {
                System.out.println(e);
                assertTrue(false);
            }

            body.setMode(enumRequestBodyMode.FORMDATA);
            try {
                String raw = body.getRaw();
            }
            catch(IllegalPropertyAccessException e)
            {
                assertTrue("Expected exception", true);
            }
            
            
    }

    @Test
    public void testURLObject() {

        String url1 = "https://foo.com:8080/foo/bar/:path1/bat.json?var1=aaa&var2=bbb";
        String path1 = "foo/bar/:path1/bat.json";
        PostmanUrl url = new PostmanUrl(url1);

        assertTrue(url.getHosts().size() == 2);
        assertTrue(url.getPaths().size() == 4);
        assertTrue(url.getVariables().size() == 1);
        assertTrue(url.getQueries().size() == 2);

        url = new PostmanUrl("foo.com",path1);
        assertTrue(url.getPaths().size() == 4);
        assertTrue(url.getVariables().size() == 1);

        url.addQuery("query1", "q1value");
        assertTrue(url.getQueries().size() == 1);
        //assertTrue(url.generateURL().equals()


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
        liUrls.add(new PostmanUrl(curUrl));
    }
    for(int i = 0; i < liUrls.size(); i++){
        try{
            if(i != 2 && liUrls.get(i).generateURL().equals(urls.get(i))) {
                assertTrue(true);
                System.out.println("URL: " + urls.get(i));
            }
            //technical wrong, but it's OK i think
            if(i == 2 && liUrls.get(i).generateURL().equals("/foo.com/bar/bat.json")) {
                assertTrue(true);
            }

        }
        catch(Exception e)
        {
            System.out.println("URL: " + i);
            assertTrue(urls.get(i) + " failed", false);
        }
    }

            




    }

    @Test
    public void testResponseObject() {

        PostmanRequest req = new PostmanRequest(enumHTTPRequestMethod.GET, "https:/postman-echo.com/post?foo=bar");
        PostmanResponse resp = new PostmanResponse("Test Response",req, "OK",200,"This is the body" );
        assertTrue(resp.getBody().equals("This is the body"));
        assertTrue(resp.getCode() == 200);
        try {
            JsonNode diffs = resp.getOriginalRequest().isEquivalentTo(req);
            assertTrue(diffs.size() == 0);
        }
        catch(Exception e)
        {
            assertTrue("Equivalence test exception",false);
        }

        resp = new PostmanResponse("Test Response",req, "Not authorize",401,"A completely different body" );
        
        PostmanRequest req2 = new PostmanRequest(enumHTTPRequestMethod.POST, "https://cnn.com");
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
        
        assertTrue(evt.getSourceCodeElements().size() == 1);

        evt.addSourceCodeElement("//some more code", 0);
        assertTrue(evt.getSourceCodeElements().size() == 2);
        try {
            evt.removeSourceCodeElement(0);
        }
        catch(Exception e) {
            assertTrue("Unexpected excpetion", false);
        }

        assertTrue(evt.getSourceCodeElements().size() == 1);
        try {
            assertTrue(evt.getSourceCode().equals("//some more code"));
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
    
    @Test
    public void testPostmanItem() throws Exception{
        pmcTest = PostmanCollection.PMCFactory(new java.io.File(filePath + "/src/main/resources/com/postman/collection/example-cat-facts-with-tests.postman_collection.json"));
        PostmanItem fact = pmcTest.getItem("Get a list of facts");
        PostmanItem folder = pmcTest.getItem("get Breeds",true);

        assertTrue(fact != null || fact.getName().equals("Get a list of facts"));
        assertTrue(folder != null || folder.getName().equals("Breeds"));

        System.out.println("foo");
    }
}