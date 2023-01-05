package com.postman.collection;

import com.google.gson.Gson;

public class PostmanEvent implements IPostmanCollectionElement {
    
private String listen = ""; // basically the name
private PostmanScript script = null;

public PostmanEvent(String listen, PostmanScript script) {
    this.listen = listen;
    this.script = script;
}

public String getListen() {
    return listen;
}

public void setListen(String listen) {
    this.listen = listen;
}
public PostmanScript getScript() {
    return script;
}
public void setScript(PostmanScript script) {
    this.script = script;
}

@Override
public String getKey() {
    // TODO Auto-generated method stub
    return listen;
}

@Override
public void setKey(String key) {
    // TODO Auto-generated method stub
    
}

@Override
public String toJson(boolean escaped, enumVariableResolution variableStrategy) {
    // TODO Auto-generated method stub
    return new Gson().toJson(this);
}





}
