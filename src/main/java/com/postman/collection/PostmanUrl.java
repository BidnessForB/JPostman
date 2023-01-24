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
                    "raw": "https://foo.com:8080/bar/:path1/bat.json?foo=1&amp;bar=2",
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
                            "value": "2"
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
     * 
     * 
     * Add a variable to the array of query elements
     * 
     * @param key Value for the <code>key</key> property 
     * @param value Value for the <code>value</code> property
     * @param description Value for the <code>description<code> property
     * 
     */
    public void addVariable(String key, String value, String description) {

        if (this.variable == null) {
            this.variable = new ArrayList<PostmanVariable>();
        }
        this.variable.add(new PostmanVariable(key, value, description));

    }

    
    /** 
     * Set the path element using a String.  Elements of the <code>path</code> array are parsed out
     * 
     * @param rawPath the raw path, e.g., /foo/:path1/bat
     * @throws Exception
     */
    public void setPath(String rawPath) /*throws Exception*/ {

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
     * 
     * Set the elements of the <code>query</code> array with a string
     * 
     * @param rawQuery String with the query component, e.g., "foo=bar&amp;bat=big"
     * 
     */
    public void setQuery(String rawQuery) {
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
     * 
     * Set the host component of the URL with a string.  The elements of the <code>host</code> array are parsed out.
     * 
     * @param rawHost The host as a string, e.g., foo.com
     */
    public void setHost(String rawHost) {

        if (rawHost == null || rawHost.length() < 1) {
            return;
        }

        this.host = new ArrayList<String>(Arrays.asList(rawHost.split("\\.", 0)));

    }

    /**
     * Generate the raw URL from the component properties of this PostmanUrl.  If <code>raw</code> has been set, the output of this method
     * should equal the raw URL provided.  
     * 
     * @return The generated URL.  Note that this may not be a valid URL
     */
    //TO-DO: Add resolve argument to allow resolving variables to their values.
    public String generateURL() {
        String url = "";
        url = this.protocol == null ? url : url + this.protocol + "://";
        
        if(this.getHosts() != null) {
            for(String host: this.getHosts()) {
                url = url + host + ".";
            }
            if(url.substring(url.length() - 1).equals(".")) {
                url = url.substring(0,url.length() -1);
            }
        }

        
        if(this.getPort() != null) {
            if(this.getPort() != null && this.getPort().length() > 0) {
                url = url + ":" + this.getPort();
            }
        }
        
        if(this.getPaths() != null && this.getPaths().size() > 0) {
            for(String path: this.getPaths())
            {
                url = url + "/"+path;
            }
            if(url.substring(url.length() - 1).equals("/")) {
                url = url.substring(0,url.length() -1);
            }
        }
        
        if(this.getQueries() == null || this.getQueries().size() == 0) {
            return url;
        }
        
        url = url + "?";
        for(PostmanVariable query : this.getQueries()) {
            url = url + query.getKey() + "=" + query.getValue() + "&";
        }
        if(url.substring(url.length() - 1).equals("&")) {
            url = url.substring(0,url.length() -1);
        }
        

        return url;
    }

    
    /** 
     * Set the value of the <code>protocol</code> property with a string.  This method will attempt to resolve the provided value to either 'http' or 'https', or null 
     * if a reasonable guess results in anything else.  
     * 
     * @param rawProtocol The protocol, eg http
     */
    public void setProtocol(String rawProtocol) {

        
        if (rawProtocol == null || rawProtocol.length() < 1) {
            protocol = null;
        } else if (rawProtocol.toLowerCase().contains("https")) {
            protocol = "https";
        } else if (rawProtocol.toLowerCase().contains("http")) {
            protocol = "http";
        } else {
            protocol = null;
        }

    }

    
    /** 
     * 
     * Returns the value of the <code>raw</code> property, or null if it has not been set.  Note that the Postman application doesn't seem to mind ingesting 
     * URLs with no value in the <code>raw</code> property.
     * 
     * @return String
     */
    public String getRaw() {
        //Won't "magically" set this to the value of generated URL.  Postman won't care anway
        return raw;
    }

    /**
     * Create a PostmanURL with the specified URL
     * @param rawURL The raw URL
     */

    public PostmanUrl(String rawURL) /*throws Exception*/ {
        this.setRaw(rawURL);
    }

    /**
     * Create a PostmanURL with the specified host and path. 
     * @param host The host, e.g., "foo.com"
     * @param path The path, e.g., "/bar/bat"
     */
    
    public PostmanUrl(String host, String path) {
        this.host = new ArrayList<String>();
        this.setHost(host);
        this.path = new ArrayList<String>();
        this.setPath(path);
    }

    
    /** 
     * 
     * Return the array of elements in the <code>host</code> array.
     * 
     * @return ArrayList<String>
     */
    public ArrayList<String> getHosts() {
        return host;
    }

    
    /** 
     * Set the value of the elements in the <code>host</code> array with a pre-populated ArrayList&#60;String&#62;
     * 
     * @param host ArrayList&#60;String&#62; with host elements.
     */
    public void setHosts(ArrayList<String> host) {
        this.host = host;
    }

    
    /** 
     * 
     * Returns the contents of the <code>path</code> element array
     * 
     * @return ArrayList&#60;String&#62; containing the path elements
     */
    public ArrayList<String> getPaths() {
        return path;
    }

    
    /** 
     * 
     * Set the elements of the <code>path</code> element array
     * 
     * @param path  ArrayList&#60;String&#62; containing the path elements
     */
    public void setPaths(ArrayList<String> path) {
        this.path = path;
    }

    
    /** 
     * 
     * Return an ArrayList&#60;{@link com.postman.collection.PostmanVariable PostmanVariable}&#62; of key value pairs comprising the <code>query</code> array
     * 
     * @return ArrayList&#60;{@link com.postman.collection.PostmanVariable PostmanVariable}&#62; containing the key value paris
     */
    public ArrayList<PostmanVariable> getQueries() {
        return query;
    }
    /**
     * 
     * Get the raw query string for this URL.  
     * 
     * 
     * 
     * @return
     */
    public String getQueryString() {
        if (query == null)
        {
            return null;
        }
        String queryString = "";
        for(PostmanVariable curVar : query) {
            queryString = (queryString.length() > 0 ? "&" + curVar.getKey() : curVar.getKey()) + "=" + (curVar.getValue() + "");
        }

        return (queryString.length() > 0 ? "?" + queryString : queryString);
    }

    public void removeQueryElement(PostmanVariable queryElement) {
        this.query.remove(queryElement);
    }

    
    /** 
     * 
     * Set the contents of the <code>query</code> array with a pre-populated ArrayList&#60;{@link com.postman.collection.PostmanVariable PostmanVariable}&#62; containing the key value pairs
     * 
     * @param query
     */
    public void setQueries(ArrayList<PostmanVariable> query) {
        this.query = query;
    }

    
    /** 
     * 
     * Get an ArrayList&#60;{@link com.postman.collection.PostmanVariable PostmanVariable}&#62; containing the key-value pairs comprising the <code>variable</code> array, or null if none exit.
     * 
     * @return ArrayList&#60;{@link com.postman.collection.PostmanVariable PostmanVariable}&#62; The ArrayList containing the key-value pairs, or null if there are none.
     */
    public ArrayList<PostmanVariable> getVariables() {
        return variable;
    }

    
    /** 
     * 
     * Set the values of the <code>variable</code> array with a pre-populated ArrayList&#60;{@link com.postman.collection.PostmanVariable PostmanVariable}&#62; containing the key-value paris
     * 
     * @param variable the ArrayList&#60;{@link com.postman.collection.PostmanVariable PostmanVariable}&#62; containing the key-value paris
     */
    public void setVariables(ArrayList<PostmanVariable> variable) {
        this.variable = variable;
    }

    
    /** 
     * Currently unimplemented
     * 
     * @return String
     */
    @Override
    public String getKey() {

        return null;
    }

    
    /** 
     * 
     * Add an element to the <code>query</code> array using raw string values.
     * 
     * @param key  The key
     * @param value The value
     * 
     */
    public void addQuery(String key, String value)  {
        this.addQuery(key, value, null);
    }

    
    /** 
     * 
     * Add an element to the <code>query</code> array using raw String values, with a description property
     * 
     * @param key  The key
     * @param value The value
     * @param description The description
     * 
     */
    public void addQuery(String key, String value, String description)  {

        if (this.query == null) {
            this.query = new ArrayList<PostmanVariable>();
        }
        this.query.add(new PostmanVariable(key, value, description));

    }

    
    /** 
     * 
     * Populate the elements of the <code>query</code> array with an http query string.  Replaces any existing query string.
     * 
     * @param queryString  The query string, e.g., foo=bar&amp;bat=bing
     * @throws Exception
     */
    public void addQuery(String queryString)  {

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
     * 
     * Get the value of the <code>port</code> property 
     * 
     * @return String  The port
     */
    public String getPort() {
        return this.port;
    }

    
    /** 
     * Set the value of the <code>port</code> property
     * 
     * @param port The port
     */
    public void setPort(int port) {
        try {
            this.port = Integer.toString(port);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
