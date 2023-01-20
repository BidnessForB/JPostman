package com.postman.collection;

import com.google.gson.Gson;


import java.util.regex.*;



import java.util.ArrayList;
import java.util.Arrays;


public class PostmanUrl extends PostmanCollectionElement {
    private String raw = "";
    private ArrayList<String> host;
    private ArrayList<String> path;
    private ArrayList<PostmanVariable> query;
    private ArrayList<PostmanVariable> variable = null;
    private String protocol;
    private String port;
    
    public void setRaw(String rawURL) throws Exception {
       
        
        this.raw = rawURL;
        System.out.println(this.protocol);
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
       
        
        ArrayList<String> queryElements = new ArrayList<String>(Arrays.asList(rawURL.split("\\?")));
        if(queryElements != null && queryElements.size() == 1)
        {
            this.setPath(queryElements.get(0));
        }
        else if (queryElements != null && queryElements.size() == 2)
        {
            this.setPath(queryElements.get(0));
            this.setQuery(queryElements.get(1));
        }
        else 
        {
            this.setPath(null);
            this.setQuery(null);
        }

    }

    public void addVariable(String key, String value, String description) throws Exception {

        if(this.variable == null) {
            this.variable = new ArrayList<PostmanVariable>();
        }
        this.variable.add(new PostmanVariable(key,value,description));
       
    }

    public void setPath(String rawPath) throws Exception  {
        
        ArrayList<String> pathElements = new ArrayList<String>();
        ArrayList<String> liPath;
        this.path = new ArrayList<String>();
        if(rawPath != null && rawPath.length() > 0)
        {
            pathElements = new ArrayList<String>(Arrays.asList(rawPath.split("/")));
            liPath = new ArrayList<String>(Arrays.asList(new String[0]));
            for(int i = 0; i < pathElements.size(); i++)
            {
                if(pathElements.get(i) != null && pathElements.get(i).length() > 0 ) {
                    liPath.add(pathElements.get(i));
                    if(pathElements.get(i).substring(0,1).equals(":")) {
                        this.addVariable(pathElements.get(i).substring(1), null, null);
                    }
                }
            }

            this.path = liPath;
        }


    }

    public void setQuery(String rawQuery) throws Exception {
        ArrayList<String> queryElements;
        if(rawQuery != null && rawQuery.length() > 0)
        {
            queryElements = new ArrayList<String>(Arrays.asList(rawQuery.split("&",0)));
                           
            for(int i = 0; i < queryElements.size() ; i++) {
                this.addQuery(queryElements.get(i));
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

        this.host = new ArrayList<String>(Arrays.asList(rawHost.split("\\.",0)));
        



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
        this.host = new ArrayList<String>();
        this.host.add(host);
        this.path = new ArrayList<String>();
        this.path.add(path);
    }

  
  

    public ArrayList<String> getHosts() {
        return host;
    }




    public void setHosts(ArrayList<String> host) {
        this.host = host;
    }
    public ArrayList<String> getPaths() {
        return path;
    }
    public void setPaths(ArrayList<String> path) {
        this.path = path;
    }
    public ArrayList<PostmanVariable> getQueries() {
        return query;
    }
    public void setQueries(ArrayList<PostmanVariable> query) {
        this.query = query;
    }
    public ArrayList<PostmanVariable> getVariables() {
        return variable;
    }
    public void setVariables(ArrayList<PostmanVariable> variable) {
        this.variable = variable;
    }
    @Override
    public String getKey() {
        
        return null;
    }
 
  

    public void addQuery(String key, String value) throws Exception {
        this.addQuery(key, value, null);
    }

    public void addQuery(String key, String value, String description) throws Exception {
        
        if(this.query == null) {
            this.query = new ArrayList<PostmanVariable>();
        }
        this.query.add(new PostmanVariable(key,value,description));
    
    }


    public void addQuery(String queryString) throws Exception {
        
        ArrayList<String> elements = new ArrayList<String>();

        if((queryString == null || queryString.length() < 1))
        {
            return;
        }

            elements = new ArrayList<String>(Arrays.asList(queryString.split("=", 0)));
            if(elements.size() == 1)
            {
                this.addQuery(elements.get(0), "");
                
            }
            if(elements.size() == 2)
            {
                this.addQuery(elements.get(0), elements.get(1));
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
