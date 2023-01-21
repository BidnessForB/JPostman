package com.postman.collection;

import java.util.ArrayList;

public class PostmanBody {
    private String mode;
    private PostmanBodyOptions options;
    private String raw;
    private PostmanGraphQL graphql;
    private ArrayList<PostmanVariable> formdata;
    private ArrayList<PostmanVariable> urlencoded;
    private PostmanBinaryFile file;

    
    /** 
     * @return PostmanBodyOptions
     */
    public PostmanBodyOptions getOptions() {
        return options;
    }

    
    /** 
     * @param options
     */
    public void setOptions(PostmanBodyOptions options) {
        this.options = options;
    }

    
    /** 
     * @return String
     */
    public String getRaw() {
        return raw;
    }

    
    /** 
     * @param raw
     */
    public void setRaw(String raw) {
        this.raw = raw;
    }

    
    /** 
     * @param lang
     */
    public void setRawLanguage(enumRawBodyLanguage lang) {
        if (this.getMode() != enumRequestBodyMode.RAW) {
            return;
        }
        this.getOptions().getRaw().setLanguage(lang);
    }

    
    /** 
     * @return enumRawBodyLanguage
     */
    public enumRawBodyLanguage getRawLanguage() {
        if (this.getMode() != enumRequestBodyMode.RAW) {
            return null;
        }
        return this.getOptions().getRaw().getLanguage();
    }

    
    /** 
     * @param raw
     * @param language
     */
    public void setRaw(String raw, enumRawBodyLanguage language) {

        if (this.getMode() == enumRequestBodyMode.TEXT) {
            this.raw = raw;
            this.options = null;
            return;
        }
        if (this.getMode() == enumRequestBodyMode.RAW) {
            this.options = new PostmanBodyOptions(new PostmanBodyRaw(language));
            this.raw = raw;
        }
        if (this.getMode() == enumRequestBodyMode.FORMDATA || this.getMode() == enumRequestBodyMode.URLENCODED) {
            this.options = null;
        }
        if (this.getMode() == enumRequestBodyMode.GRAPHQL) {
            this.options = null;
            this.graphql = new PostmanGraphQL(raw);
        }

    }

    
    /** 
     * @return PostmanGraphQL
     */
    public PostmanGraphQL getGraphql() {
        return graphql;
    }

    
    /** 
     * @param graphQL
     */
    public void setGraphql(String graphQL) {
        this.setGraphql(graphQL, null);
    }

    
    /** 
     * @param graphQL
     * @param variables
     */
    public void setGraphql(String graphQL, String variables) {
        this.graphql = (new PostmanGraphQL(graphQL, variables));
    }

    
    /** 
     * @return ArrayList<PostmanVariable>
     * @throws Exception
     */
    public ArrayList<PostmanVariable> getFormdata() throws Exception {
        return formdata;
    }

    
    /** 
     * @param position
     * @return PostmanVariable
     * @throws Exception
     */
    public PostmanVariable getFormdata(int position) throws Exception {
        switch (this.getMode()) {
            case URLENCODED: {
                return this.urlencoded.get(position);
            }
            case FORMDATA: {
                return this.formdata.get(position);
            }
            default: {
                throw new Exception(
                        "Body mode " + this.getMode() + ". Must be URLENCODED or FORMDATA to add form data");
            }
        }
    }

    
    /** 
     * @param formdata
     */
    public void setFormdata(ArrayList<PostmanVariable> formdata) {
        this.formdata = formdata;
    }

    
    /** 
     * @param key
     * @param value
     * @param description
     * @param position
     * @throws Exception
     */
    public void setFormdata(String key, String value, String description, int position) throws Exception {
        this.setFormdata(new PostmanVariable(key, value, description, "text"), position);
    }

    
    /** 
     * @param key
     * @param value
     * @param description
     * @throws Exception
     */
    public void setFormdata(String key, String value, String description) throws Exception {
        this.setFormdata(new PostmanVariable(key, value, description), 0);
    }

    
    /** 
     * @param data
     * @param position
     * @throws Exception
     */
    public void setFormdata(PostmanVariable data, int position) throws Exception {

        if (this.getMode() == enumRequestBodyMode.URLENCODED) {
            if (this.urlencoded == null) {
                this.urlencoded = new ArrayList<PostmanVariable>();
            }
            this.urlencoded.add(position, data);
        } else if (this.getMode() == enumRequestBodyMode.FORMDATA) {
            if (this.formdata == null) {
                this.formdata = new ArrayList<PostmanVariable>();
            }
            this.formdata.add(position, data);
        } else {
            throw new Exception("Cannot set Form Data.  Mode must be URLENCODED or FORMDATA");
        }
    }

    public PostmanBody(enumRequestBodyMode mode, String content, enumRawBodyLanguage language) {

        this.setMode(mode);

        if (this.getMode() == enumRequestBodyMode.RAW || this.getMode() == enumRequestBodyMode.GRAPHQL) {
            this.setRaw(content, language);
        }
        if (this.getMode() == enumRequestBodyMode.FILE) {
            this.file.setSrc(content);
        }

    }

    
    /** 
     * @return ArrayList<PostmanVariable>
     */
    public ArrayList<PostmanVariable> getUrlencoded() {
        return urlencoded;
    }

    
    /** 
     * @param urlencoded
     */
    public void setUrlencoded(ArrayList<PostmanVariable> urlencoded) {
        this.urlencoded = urlencoded;
    }

    
    /** 
     * @return String
     */
    public String getFile() {
        if (this.getMode() != enumRequestBodyMode.FILE) {
            return null;
        }
        return this.file.getSrc();
    }

    
    /** 
     * @param file
     */
    public void setFile(String file) {
        if (this.getMode() != enumRequestBodyMode.FILE)
            this.file.setSrc(file);
    }

    
    /** 
     * @return enumRequestBodyMode
     */
    public enumRequestBodyMode getMode() {
        if (mode == null) {
            return null;
        }
        switch (mode) {
            case "file":
                return enumRequestBodyMode.FILE;
            case "formdata":
                return enumRequestBodyMode.FORMDATA;
            case "urlencoded":
                return enumRequestBodyMode.URLENCODED;
            case "graphql":
                return enumRequestBodyMode.GRAPHQL;
            case "raw":
                return enumRequestBodyMode.RAW;
        }
        return null;
    }

    
    /** 
     * @param newMode
     */
    public void setMode(enumRequestBodyMode newMode) {
        switch (newMode) {
            case FILE:
                this.mode = "file";
                this.file = new PostmanBinaryFile("");
                this.formdata = null;
                this.urlencoded = null;
                this.graphql = null;
                this.options = null;

                break;
            case FORMDATA:
                this.mode = "formdata";
                this.file = null;
                this.formdata = new ArrayList<PostmanVariable>();
                this.urlencoded = null;
                this.graphql = null;
                this.options = null;
                break;
            case URLENCODED:
                this.mode = "urlencoded";
                this.file = null;
                this.formdata = null;
                this.urlencoded = new ArrayList<PostmanVariable>();
                this.graphql = null;
                this.options = null;
                break;
            case GRAPHQL:
                this.mode = "graphql";
                this.file = null;
                this.formdata = null;
                this.urlencoded = null;
                this.graphql = null;
                this.options = null;
                break;
            case RAW:
                this.mode = "raw";
                this.file = null;
                this.formdata = null;
                this.urlencoded = null;
                this.graphql = null;
                this.options = null;
                break;
            case TEXT:
                this.mode = "raw";
                this.file = null;
                this.formdata = null;
                this.urlencoded = null;
                this.graphql = null;
                this.options = null;
        }
    }

    
    /** 
     * @param position
     */
    public void removeFormData(int position) {

    }

    
    /** 
     * @param data
     */
    public void removeFormDAta(PostmanVariable data) {

    }

    public PostmanBody(enumRequestBodyMode mode) {
        this.setMode(mode);
    }

    class PostmanBodyOptions {
        private PostmanBodyRaw raw;

        public PostmanBodyRaw getRaw() {
            return raw;
        }

        public void setRaw(PostmanBodyRaw raw) {
            this.raw = raw;
        }

        public PostmanBodyOptions(PostmanBodyRaw raw) {
            this.raw = raw;
        }

        public PostmanBodyOptions(enumRawBodyLanguage language) {
            this.raw = new PostmanBodyRaw(language);
        }
    }

    public class PostmanBodyRaw {
        private String language;

        public enumRawBodyLanguage getLanguage() {
            if (language == null) {
                return null;
            }
            switch (language) {
                case "javascript":
                    return enumRawBodyLanguage.JAVASCRIPT;
                case "json":
                    return enumRawBodyLanguage.JSON;
                case "html":
                    return enumRawBodyLanguage.HTML;
                case "xml":
                    return enumRawBodyLanguage.XML;
                case "graphql":
                    return enumRawBodyLanguage.GRAPHQL;
            }
            return null;
        }

        public void setLanguage(enumRawBodyLanguage newLanguage) {
            switch (newLanguage) {
                case JSON:
                    this.language = "json";
                    break;
                case JAVASCRIPT:
                    this.language = "javascript";
                    break;
                case HTML:
                    this.language = "html";
                    break;
                case XML:
                    this.language = "xml";
                    break;
                case GRAPHQL:
                    this.language = "graphql";
                    break;
                default:
                    this.language = null;
            }
        }

        public PostmanBodyRaw(enumRawBodyLanguage language) {
            this.setLanguage(language);
        }
    }

    public class PostmanBinaryFile {
        private String src;

        public String getSrc() {
            return src;
        }

        public void setSrc(String src) {
            this.src = src;
        }

        public PostmanBinaryFile(String src) {
            this.src = src;
        }
    }

    public class PostmanGraphQL {
        private String query;
        private String variables;

        public PostmanGraphQL(String query) {
            this(query, null);
        }

        public PostmanGraphQL(String query, String variables) {
            this.query = query;
            this.variables = variables;
        }

        public String getQuery() {
            return query;
        }

        public void setQuery(String query) {
            this.query = query;
        }

        public String getVariables() {
            return variables;
        }

        public void setVariables(String variables) {
            this.variables = variables;
        }
    }
}
