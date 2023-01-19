package com.postman.collection;

import com.google.gson.Gson;
import com.postman.collection.util.CollectionUtils;

import java.util.regex.*;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PostmanUrl implements IPostmanCollectionElement {
    private String raw = "";
    private String[] host;
    private String[] path;
    private PostmanVariable[] query;
    private PostmanVariable[] variable = null;
    private String protocol;
    private String port;
    
    public void setRaw(String rawURL) throws Exception {
       
        
        this.raw = rawURL;
        String testUrl = rawURL;
        Pattern pnProtocol = Pattern.compile("^https?(:(/*)*)");
        Matcher maProtocol = pnProtocol.matcher(rawURL);
        if(maProtocol.find()) {
            this.setProtocol(maProtocol.group());
            rawURL = rawURL.replace(maProtocol.group(0), "");
        }
        else {
            this.setProtocol(null);
        };

        //Pattern pnHost = Pattern.compile("([^:^/]*)" );
        Pattern pnHost = Pattern.compile("^([^:^/]*)(:([0-9]+))?");
        Matcher maHost = pnHost.matcher(rawURL);
        if(maHost.find()) {
            this.setHost(maHost.group(1));
            if(maHost.groupCount() >= 3 && maHost.group(3) != null)
            {
                this.setPort(Integer.parseInt(maHost.group(3)));
                rawURL = rawURL.replace(maHost.group(2),"");
            }
            rawURL = rawURL.replace(maHost.group(1),"");
        }
        else if (!maHost.find())
        {
            pnHost = Pattern.compile("([\\.]*)" );
            maHost = pnHost.matcher(rawURL);
            if(maHost.find()) {
                this.setHost(maHost.group());
            }
        }
        else {
            this.setHost(null);
        }
       /*
        Pattern pnPort = Pattern.compile(":([0-9]+)");
        Matcher maPort = pnPort.matcher(rawURL);
        if(maPort.find() == true)
        {
            this.setPort(Integer.parseInt(maPort.group(1)));
            rawURL = rawURL.replace(maPort.group(0), "");
        }
        */
        
        String [] queryElements = rawURL.split("\\?");
        if(queryElements != null && queryElements.length == 1)
        {
            this.setPath(queryElements[0]);
        }
        else if (queryElements != null && queryElements.length == 2)
        {
            this.setPath(queryElements[0]);
            this.setQuery(queryElements[1]);
        }
        else 
        {
            this.setPath(null);
            this.setQuery(null);
        }


        System.out.println("foo");

    }

    public void addVariable(String key, String value, String description) throws Exception {

        this.variable = CollectionUtils.insertInCopy((this.variable == null ? new PostmanVariable[0] : this.variable),new PostmanVariable(key, value,description));
       
    }

    public void setPath(String rawPath) throws Exception  {
        String pathElements[] = new String[0];
        List<String> liPath;
        this.path = new String[0];
        if(rawPath != null && rawPath.length() > 0)
        {
            pathElements = rawPath.split("/");
            liPath = new ArrayList<String>(Arrays.asList(new String[0]));
            for(int i = 0; i < pathElements.length; i++)
            {
                if(pathElements[i] != null && pathElements[i].length() > 0 ) {
                    liPath.add(pathElements[i]);
                    if(pathElements[i].substring(0,1).equals(":")) {
                        this.addVariable(pathElements[i].substring(1), null, null);
                    }
                }
            }

            this.path = liPath.toArray(this.path);
        }


    }

    public void setQuery(String rawQuery) throws Exception {
        String queryElements[];
        if(rawQuery != null && rawQuery.length() > 0)
        {
            queryElements = rawQuery.split("&");
            for(int i = 0; i < queryElements.length; i++) {
                this.addQuery(queryElements[i]);
            }
        }
        else {
            this.query = null;
        }
    }

    public void setHost(String rawHost) {
        

        if(rawHost == null || rawHost.length() < 1)
        {
            return;
        }

        this.host = rawHost.split("\\.",0);
        



    }

    public void setProtocol(String rawProtocol) {
        if(rawProtocol == null || rawProtocol.length() < 1)
        {
            this.protocol = null;
        }
        else if(rawProtocol.contains("https")) {
            this.protocol = "https";
        }
        else if(rawProtocol.contains("http")) {
            this.protocol = "http";
        }
        else {
            this.protocol = null;
        }
    }
    
    public String getRaw() {
        //this.raw = this.host
        return raw;
    }

    public PostmanUrl(String rawURL) throws Exception {
        this.setRaw(rawURL);
    }

    public PostmanUrl(String host, String path)
    {
        this.host = new String[1];
        this.host[0] = host;
        this.path = new String[1];
        this.path[0] = path;
    }

  
  

    public String[] getHosts() {
        return host;
    }

    public boolean isValid() {
        return true;
    }



    public void setHosts(String[] host) {
        this.host = host;
    }
    public String[] getPaths() {
        return path;
    }
    public void setPaths(String[] path) {
        this.path = path;
    }
    public PostmanVariable[] getQueries() {
        return query;
    }
    public void setQueries(PostmanVariable[] query) {
        this.query = query;
    }
    public PostmanVariable[] getVariables() {
        return variable;
    }
    public void setVariables(PostmanVariable[] variable) {
        this.variable = variable;
    }
    @Override
    public String getKey() {
        
        return null;
    }
 
    @Override
    public String toJson(boolean escaped, enumVariableResolution variableStrategy) {
        
        return new Gson().toJson(this);
    }

    public void addQuery(String key, String value) throws Exception {
        this.addQuery(key, value, null);
    }

    public void addQuery(String key, String value, String description) throws Exception {
        
        this.query = CollectionUtils.insertInCopy(this.query == null ? new PostmanVariable[0] : this.query, new PostmanVariable(key,value,description));
    
    }


    public void addQuery(String queryString) throws Exception {
        
        String[] elements = new String[2];

        if((queryString == null || queryString.length() < 1))
        {
            return;
        }

            elements = queryString.split("=", 0);
            if(elements.length == 1)
            {
                this.addQuery(elements[0], "");
                
            }
            if(elements.length == 2)
            {
                this.addQuery(elements[0], elements[1]);
            }

    }
    public String getPort() {
        return this.port;
    }

    public void setPort(int port) {
        try {
            this.port = Integer.toString(port);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        
    }
}
