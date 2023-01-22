package com.postman.collection;

import java.util.regex.*;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Class to encapsulate the properties in the <code>url</code> object of a <a href="./PostmanRequest.html">PostmanRequst</a> object.  
 * The URL object properties contain the raw URL itself, the protocol, the port an array of strings for the individual host and path elements, arrays of key/value pairs for query parameters, as well as 
 * path variables.
 * <pre>
 * "url": {
                    "raw": "https://foo.com:8080/bar/:path1/bat.json?foo\u003d1\u0026bar\u003d",
                    "host": [
                        "foo",
                        "com"
                    ],
                    "path": [
                        "bar",
                        ":path1",
                        "bat.json"
                    ],
                    "query": [
                        {
                            "key": "foo",
                            "value": "1"
                        },
                        {
                            "key": "bar",
                            "value": ""
                        }
                    ],
                    "variable": [
                        {
                            "key": "path1"
                        }
                    ],
                    "protocol": "https",
                    "port": "8080"
                }   
                </pre>                 
 */

public class PostmanUrl extends PostmanCollectionElement {
    private String raw = "";
    private ArrayList<String> host;
    private ArrayList<String> path;
    private ArrayList<PostmanVariable> query;
    private ArrayList<PostmanVariable> variable = null;
    @SuppressWarnings("unused")
    private String protocol;
    private String port;

    
    /** 
     * 
     * Sets the raw URL.  The host, path, query,protocl, query and port properties are parsed from the raw url.  
     * Parsing does not always result in these properties being filled, as collection URLs are not always valid URLs in the java sense.  
     * For example, URLs containing variables may not produce discrete values, eg.
     * <pre> {{baseUrl}}/{{theHost}}/foo/{{filename}}</pre>
     * 
     * @param rawURL The raw URL as a String.  The URL is not validated
     * @throws Exception
     */
    public void setRaw(String rawURL)  {

        this.raw = rawURL;

        Pattern pnProtocol = Pattern.compile("^https?(:(/*)*)");
        Matcher maProtocol = pnProtocol.matcher(rawURL);
        if (maProtocol.find()) {
            this.setProtocol(maProtocol.group());
            rawURL = rawURL.replace(maProtocol.group(0), "");
        } else {
            this.setProtocol(null);
        }
        ;

        // Pattern pnHost = Pattern.compile("([^:^/]*)" );
        Pattern pnHost = Pattern.compile("^([^:^/]*)(:([0-9]+))?");
        Matcher maHost = pnHost.matcher(rawURL);
        if (maHost.find()) {
            this.setHost(maHost.group(1));
            if (maHost.groupCount() >= 3 && maHost.group(3) != null) {
                this.setPort(Integer.parseInt(maHost.group(3)));
                rawURL = rawURL.replace(maHost.group(2), "");
            }
            rawURL = rawURL.replace(maHost.group(1), "");
        } else if (!maHost.find()) {
            pnHost = Pattern.compile("([\\.]*)");
            maHost = pnHost.matcher(rawURL);
            if (maHost.find()) {
                this.setHost(maHost.group());
            }
        } else {
            this.setHost(null);
        }

        ArrayList<String> queryElements = new ArrayList<String>(Arrays.asList(rawURL.split("\\?")));
        if (queryElements != null && queryElements.size() == 1) {
            this.setPath(queryElements.get(0));
        } else if (queryElements != null && queryElements.size() == 2) {
            this.setPath(queryElements.get(0));
            this.setQuery(queryElements.get(1));
        } else {
            this.setPath(null);
            this.setQuery(null);
        }

    }

    
    /** 
     * @param key
     * @param value
     * @param description
     * @throws Exception
     */
    public void addVariable(String key, String value, String description) throws Exception {

        if (this.variable == null) {
            this.variable = new ArrayList<PostmanVariable>();
        }
        this.variable.add(new PostmanVariable(key, value, description));

    }

    
    /** 
     * @param rawPath
     * @throws Exception
     */
    public void setPath(String rawPath) throws Exception {

        ArrayList<String> pathElements = new ArrayList<String>();
        ArrayList<String> liPath;
        this.path = new ArrayList<String>();
        if (rawPath != null && rawPath.length() > 0) {
            pathElements = new ArrayList<String>(Arrays.asList(rawPath.split("/")));
            liPath = new ArrayList<String>(Arrays.asList(new String[0]));
            for (int i = 0; i < pathElements.size(); i++) {
                if (pathElements.get(i) != null && pathElements.get(i).length() > 0) {
                    liPath.add(pathElements.get(i));
                    if (pathElements.get(i).substring(0, 1).equals(":")) {
                        this.addVariable(pathElements.get(i).substring(1), null, null);
                    }
                }
            }

            this.path = liPath;
        }

    }

    
    /** 
     * @param rawQuery
     * @throws Exception
     */
    public void setQuery(String rawQuery) throws Exception {
        ArrayList<String> queryElements;
        if (rawQuery != null && rawQuery.length() > 0) {
            queryElements = new ArrayList<String>(Arrays.asList(rawQuery.split("&", 0)));

            for (int i = 0; i < queryElements.size(); i++) {
                this.addQuery(queryElements.get(i));
            }
        } else {
            this.query = null;
        }
    }

    
    /** 
     * @param rawHost
     */
    public void setHost(String rawHost) {

        if (rawHost == null || rawHost.length() < 1) {
            return;
        }

        this.host = new ArrayList<String>(Arrays.asList(rawHost.split("\\.", 0)));

    }

    
    /** 
     * @param rawProtocol
     */
    public void setProtocol(String rawProtocol) {

        if (rawProtocol == null || rawProtocol.length() < 1) {
            protocol = null;
        } else if (rawProtocol.contains("https")) {
            protocol = "https";
        } else if (rawProtocol.contains("http")) {
            protocol = "http";
        } else {
            protocol = null;
        }

    }

    
    /** 
     * @return String
     */
    public String getRaw() {
        // this.raw = this.host
        return raw;
    }

    public PostmanUrl(String rawURL) throws Exception {
        this.setRaw(rawURL);
    }

    public PostmanUrl(String host, String path) {
        this.host = new ArrayList<String>();
        this.host.add(host);
        this.path = new ArrayList<String>();
        this.path.add(path);
    }

    
    /** 
     * @return ArrayList<String>
     */
    public ArrayList<String> getHosts() {
        return host;
    }

    
    /** 
     * @param host
     */
    public void setHosts(ArrayList<String> host) {
        this.host = host;
    }

    
    /** 
     * @return ArrayList<String>
     */
    public ArrayList<String> getPaths() {
        return path;
    }

    
    /** 
     * @param path
     */
    public void setPaths(ArrayList<String> path) {
        this.path = path;
    }

    
    /** 
     * @return ArrayList<PostmanVariable>
     */
    public ArrayList<PostmanVariable> getQueries() {
        return query;
    }

    
    /** 
     * @param query
     */
    public void setQueries(ArrayList<PostmanVariable> query) {
        this.query = query;
    }

    
    /** 
     * @return ArrayList<PostmanVariable>
     */
    public ArrayList<PostmanVariable> getVariables() {
        return variable;
    }

    
    /** 
     * @param variable
     */
    public void setVariables(ArrayList<PostmanVariable> variable) {
        this.variable = variable;
    }

    
    /** 
     * @return String
     */
    @Override
    public String getKey() {

        return null;
    }

    
    /** 
     * @param key
     * @param value
     * @throws Exception
     */
    public void addQuery(String key, String value) throws Exception {
        this.addQuery(key, value, null);
    }

    
    /** 
     * @param key
     * @param value
     * @param description
     * @throws Exception
     */
    public void addQuery(String key, String value, String description) throws Exception {

        if (this.query == null) {
            this.query = new ArrayList<PostmanVariable>();
        }
        this.query.add(new PostmanVariable(key, value, description));

    }

    
    /** 
     * @param queryString
     * @throws Exception
     */
    public void addQuery(String queryString) throws Exception {

        ArrayList<String> elements = new ArrayList<String>();

        if ((queryString == null || queryString.length() < 1)) {
            return;
        }

        elements = new ArrayList<String>(Arrays.asList(queryString.split("=", 0)));
        if (elements.size() == 1) {
            this.addQuery(elements.get(0), "");

        }
        if (elements.size() == 2) {
            this.addQuery(elements.get(0), elements.get(1));
        }

    }

    
    /** 
     * @return String
     */
    public String getPort() {
        return this.port;
    }

    
    /** 
     * @param port
     */
    public void setPort(int port) {
        try {
            this.port = Integer.toString(port);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
