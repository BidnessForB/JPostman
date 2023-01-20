package com.postman.collection;

import com.google.gson.Gson;

public class PostmanEvent extends PostmanCollectionElement {
    
private String listen = ""; // basically the name
private PostmanScript script = null;

public PostmanEvent(enumEventType evtType, PostmanScript script) throws Exception {
    this.setEventType(evtType);
    this.script = script;
}

public static PostmanEvent pmcEventFactory(String eventCode) {
    PostmanEvent retVal = new Gson().fromJson(eventCode,PostmanEvent.class);
    return retVal;
}

public static PostmanEvent pmcEventFactory() {
    PostmanEvent retVal = null;
    PostmanScript script = new PostmanScript("text/javascript", "//Your code here");
    
    try {
        retVal = new PostmanEvent(enumEventType.TEST, script);
    }
    catch(Exception e)
    {
        //System.out.println("This should not happen here.");
    }
    
    
    retVal.setScript(new PostmanScript("text/javascript","//Your code here" ));
    return retVal;
}



public enumEventType getEventType() {
    if(this.getListen().equals("test"))
    {
        return enumEventType.TEST;
    }
    else if (this.getListen().equals("prerequest"))
    {
        return enumEventType.PRE_REQUEST;
    }
    else {
        return null;
    }
}

public void setEventType(enumEventType eventType) throws Exception {
    if(eventType == enumEventType.PRE_REQUEST )
    {
        this.setListen("prerequest");
    }
    else if (eventType == enumEventType.TEST) {
        this.setListen("test");
    }
    else {
        throw new Exception("Only Pre-Request and Test Scripts supported at this time");
    }
}

private String getListen() {
    return listen;
}

private void setListen(String listen) {
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
    
    return listen;
}








}
