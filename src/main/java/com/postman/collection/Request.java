package com.postman.collection;
import java.util.ArrayList;
public class Request extends ItemElement {
    private RequestElement request = null;
    private ArrayList<ResponseElement> response = null;
    
    public Request(RequestElement req, String name) {
        super(name);
        this.request = req;
        req.setParent(this);
    }

    public Request(String name) {
        super(name);
    }



    /** 
     * 
     * Return the object containing the values in the <code>request</code> property, or null if this item does not contain a request (e.g., is a folder);
     * 
     * 
     * @return RequestElement The request, or null if no request is defined.
     */
    public RequestElement getRequestElement() {
        return request;
    }

    
    /** 
     * 
     * Set the object containing the values in the <code>request</code> property
     * 
     * @param request
     */
    public void setRequestElement(RequestElement request) {
        this.request = request;
    }

    
    /** 
     * 
     * Return an ArrayList&#60;ResponseElement&#62; containing the values in the <code>response</code> property array, or null if none are defined.
     * 
     * 
     * @return ArrayList&#60;{@link com.postman.collection.ResponseElement ResponseElement}&#62;  The responses, or null if none are defined.
     */
    public ArrayList<ResponseElement> getResponseElements() {
        return response;
    }

    
    /** 
     * 
     * Set the ArrayList&#60;ResponseElement&#62;  containing the values in the <code>response</code> property array.  Passing null to this method removes the response array
     * 
     * @param response
     */
    public void setResponseElements(ArrayList<ResponseElement> response) {
        this.response = response;
    }

    /** 
     * 
     * Add a ResponseElement object to the <code>response</code> array
     * 
     * @param resp The new response
     * 
     */
    public void addResponseElement(ResponseElement resp)  {
        if (this.response == null) {
            this.response = new ArrayList<ResponseElement>();
        }
        this.response.add(resp);
    }
/** 
     * 
     * Add a response object to the request contained by this collection specified by <code>requestKey</code>
     * 
     * 
     * @param requestKey Key identifying the request to add the response to
     * @param response New response to add to the request
     * @throws InvalidCollectionActionException If the specifyed request is not contained by this collection
     */
    public void addResponse(String requestKey, ResponseElement response) throws InvalidCollectionActionException {
        RequestElement req = this.getRequestElement();
        if(req == null) {
            throw new InvalidCollectionActionException("Request with key [" + requestKey + "] not found");
        }
        this.addResponseElement(response);

    }
    

}
