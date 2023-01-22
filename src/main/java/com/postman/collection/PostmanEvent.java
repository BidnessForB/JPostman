package com.postman.collection;

import java.util.ArrayList;
/**
 * 
 * Encapsulates members of the <code>event</code> array object.  Events contain pre-request and test scripts for Folders, Requests, and Collections
 * 
 * 
 * <pre>
 * "event": [
        {
            "listen": "test",
            "script": {
                "exec": [
                    "pm.test(\"Status code is 200\", function () {",
                    "    pm.response.to.have.status(200);",
                    "});",
                    "",
                    "var latencyTestName = \"Response time is less than \" + pm.collectionVariables.get(\"latencyLimit\") + \" ms\";",
                    "",
                    "pm.test(latencyTestName, function () {",
                    "    pm.expect(pm.response.responseTime).to.be.below(parseInt(pm.collectionVariables.get(\"latencyLimit\")));",
                    "});",
                    "",
                    "pm.test(\"Response contains fact\", function () {",
                    "    var jsonData = pm.response.json();",
                    "    pm.expect(pm.response.json().length).to.be.greaterThan(1);",
                    "});"
                ],
                "type": "text/javascript"
            }
        },
        {
            "listen": "prerequest",
            "script": {
                "exec": [
                    "console.log(\"last fact: \" + pm.collectionVariables.get(\"curFact\"));"
                ],
                "type": "text/javascript"
            }
        }
    ]

                    </pre>
 * 
 * 
 */
public class PostmanEvent extends PostmanCollectionElement {

    private String listen = ""; // basically the name
    private PostmanScript script = null;

    /**
     * 
     * Create a new PostmanEvent object with the specified EventType (eg., pre-request or test) and source code.  The <code>type</code> property is excluded because it is always 'text/javascript', 
     * although the schema does theoretically allow for different values
     * 
     * 
     * @param evtType  Enumerated value of the event type, eg. pre-request or test
     * @param scriptCode Source code for the script
     * @param type Content type of the script, always "text/javascript"
     * 
     */
    public PostmanEvent(enumEventType evtType, String sourceCode)  {
        this.setEventType(evtType);
        this.setScript(new PostmanScript(this.getScriptType(), sourceCode));
    }

    
    /** 
     * 
     * Unimplemented because type is always text/javascript
    /*
    public void setScriptType(String scriptType) {
        this.script.setType(scriptType);
    }
    */

    
    /** 
     * @return String
     */
    public String getScriptType() {
        //return this.script.getType();
        return "text/javascript";
    }

    
    /** 
     * 
     * Returns the type of this script, eg., pre-request or test, as an enumerated value.
     * 
     * @return enumEventType The event type
     */
    public enumEventType getEventType() {
        if (this.getListen().equals("test")) {
            return enumEventType.TEST;
        } else if (this.getListen().equals("prerequest")) {
            return enumEventType.PRE_REQUEST;
        } else {
            return null;
        }
    }

    
    /** 
     * 
     * Sets the type of this event, eg. pre-request or test, as an enumerated value
     * 
     * @param eventType The type of the event
     * 
     */
    public void setEventType(enumEventType eventType)   {
        if (eventType == enumEventType.PRE_REQUEST) {
            this.setListen("prerequest");
        } else if (eventType == enumEventType.TEST) {
            this.setListen("test");
        }
    }

    
    /** 
     * @return String
     */
    private String getListen() {
        return listen;
    }

    
    /** 
     * @param listen
     */
    private void setListen(String listen) {
        this.listen = listen;
    }

    /**
     * 
     * Get complete source for this script, or null if none is set.  Concatenates all the elements of the <code>exec</code> array.  
     * 
     * @return
     */
    public String getSourceCode() {
        String srcCode = "";
        if(this.getScript() == null || this.getScript().getSourceCode() == null) {
            return null;
        }
        for(String chunk: this.getScript().getSourceCode()) {
            srcCode = srcCode + "\n" + chunk;
        }

        return srcCode;
    }


    /**
     * 
     * Set the source code for this script
     * 
     * @param srcCode The source code (javascript)
     */
    public void setSourceCode(String srcCode) {
        this.setScript(new PostmanScript("text/javascript", srcCode));
    }



    /** 
     * @return PostmanScript
     */
    private PostmanScript getScript() {
        return script;
    }

    
    /** 
     * @param script
     */
    private void setScript(PostmanScript script) {
        this.script = script;
    }

    
    /** 
     * @return String
     */
    @Override
    public String getKey() {

        return listen;
    }

    
    /** 
     * 
     * Pre-pend source code to the end of the <code>exec</code> property array.  
     * 
     * @param code
     */
    public void addSourceCodeElement(String code, boolean append) {
        if(this.getScript() == null) {
            this.setScript(new PostmanScript("text/javascript",code));
            return;
        }
        this.getScript().setSourceCodeElement(code, append);
        
    }

    public void removeSourceCodeElement(int position) {

    }

    public class PostmanScript extends PostmanCollectionElement {
        private String type = "";
        private ArrayList<String> exec;

        public PostmanScript(String scriptType, ArrayList<String> Exec) {
            this.type = scriptType;
            exec = Exec;
        }

        public PostmanScript(String scriptType, String srcCode) {
            this.exec = new ArrayList<String>();
            this.exec.add(srcCode);
            this.type = scriptType;

        }

        public void setSourceCodeElement(String code, boolean append) {
            if(append) {
                this.exec.add(0,code);
            }
            else {
                this.exec.add(code);
            }
        }

        public void removeSourceCodeElement(int position) throws InvalidPropertyException {
            
            if(this.exec == null || (position < 0 || position > this.exec.size())) {
                throw new InvalidPropertyException("Postion " + position + "out of bounds" );
            }
        }

        

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public ArrayList<String> getSourceCode() {
            return exec;
        }

        public void setSourceCode(ArrayList<String> exec) {
            this.exec = exec;
        }

        @Override
        public String getKey() {

            return null;
        }

    }

}
