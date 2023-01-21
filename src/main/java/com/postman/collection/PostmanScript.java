package com.postman.collection;
import java.util.ArrayList;

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
