package com.postman.collection;

import static org.junit.Assert.assertTrue;


import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    String filePath = new java.io.File("").getAbsolutePath();

    @Test
    public void shouldImportCollection()
    {
        filePath = new java.io.File("").getAbsolutePath();
        PostmanCollection pmcTest;
        try {
            pmcTest  = PostmanCollection.PMCFactory(filePath + "/src/main/resources/com/postman/collection/example-catfact.postman_collection.json");
        }
        catch(Exception e) {
            e.printStackTrace();
            assertTrue(false);
        }
    }
        
        @Test
        public void shouldCreateURLs() {
            
        PostmanCollection pmcTest = new PostmanCollection("URL Test Constructed");
        PostmanUrl[] urls = new PostmanUrl[9];
        PostmanRequest[] requests = new PostmanRequest[9];
        
        urls[0] = new PostmanUrl("http://foo.com/bar/bat.json");
        urls[1] = new PostmanUrl("//foo.com/bar/bat.json");
        urls[2] = new PostmanUrl("{{baseUrl}}/foo.com/bar/bat.json");
        urls[3] = new PostmanUrl("http://foo.com/bar/bat.json?foo=1&bar=2");
        urls[4] = new PostmanUrl("http://foo.com/bar/bat.json?foo=1&bar=");
        urls[5] = new PostmanUrl("{{baseUrl}}/foo.com/bar/bat.json?foo=1&bar=");
        urls[6] = new PostmanUrl("{{baseUrl}}foo.com/bar/:path1/bat.json?foo=1&bar=");
        urls[7] = new PostmanUrl("{{baseUrl}}foo.com:8080/bar/:path1/bat.json?foo=1&bar=");
        urls[8] = new PostmanUrl("{{baseUrl}}/foo.com:8080/bar/:path1/bat.json?foo=1&bar=");
        
        for(int i = 0; i<urls.length; i++)
        {
            requests[i] = new PostmanRequest(enumHTTPRequestMethod.GET,urls[i]);
            try {
                
            pmcTest.addRequest(requests[i],"Test Constructed URL " + i);
            }
            catch(Exception e)
            {
                assertTrue(false);
            }
        } 
        requests[6].addVariable(new PostmanVariable("path1", "path-value"));
        requests[7].addVariable(new PostmanVariable("path1", "path-value"));
        requests[8].addVariable(new PostmanVariable("path1", "path-value"));
        try {
            
        pmcTest.writeToFile(filePath +"/test-output/shouldCreateUrRLs.json");
        pmcTest = PostmanCollection.PMCFactory(filePath + "/test-output/shouldCreateUrRLs.json");
        
        
        }
        catch(Exception e)
        {
            assertTrue(false);
        }

        assert(true);
        

        }
    }

