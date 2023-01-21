package com.postman.collection;

import com.google.gson.Gson;
import java.util.ArrayList;
public class PostmanEvent extends PostmanCollectionElement {
    
private String listen = ""; // basically the name
private PostmanScript script = null;

public PostmanEvent(enumEventType evtType, String scriptCode, String type) throws Exception {
    this.setEventType(evtType);
    this.script = new PostmanScript(type, scriptCode);
}

public PostmanEvent(enumEventType evtType, String scriptCode) throws Exception {
    this(evtType, scriptCode, "text/javascript");
}

public void setScriptType(String scriptType) {
    this.script.setType(scriptType);
}

public String getScriptType() {
    return this.script.getType();
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

public void setExecElement(String code) {
    this.script.getExec().set(1, code);
}



public class PostmanScript extends PostmanCollectionElement {
    private String type = "";
    private ArrayList<String> exec;

    
    public PostmanScript(String scriptType, ArrayList<String> Exec) {
            this.type = scriptType;
            exec = Exec;
    }

    public PostmanScript(String scriptType, String srcCode)  {
        this.exec = new ArrayList<String>();
        this.exec.add(srcCode);
        this.type = scriptType;

    }



    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public ArrayList<String> getExec() {
        return exec;
    }


    public void setExec(ArrayList<String> exec) {
        this.exec = exec;
    }


    @Override
    public String getKey() {
        
        return null;
    }


 


 

    

}





}
