# JPostman

A Java wrapper for Postman collection files.  

[Docs](https://bidnessforb.github.io/JPostman)

[Postman Collection Overview](https://collectionformat.gatsbyjs.io/docs/getting-started/overview/)

[Postman Collection SDK (Node)](http://www.postmanlabs.com/postman-collection/index.html)

## Getting started

1. Download the JAR file from the releases tab
2. Import `com.postman.collection.Collection` in your source code.
3. Instantiate a new collection by passing the path to a Postman collection .json file to the `Collection.pmcFactory` method

```java
Collection pmcTest = Collection.pmcFactory("/path/to/your/exported/collection.json");
```

## What you can do

### Integrate with Java applications and platforms

Java is common in Enterprise software development environments.  JPostman enables you to create a complete Java object model of a Postman collection from an exported collection JSON file.  This provides a bridge for integrating 
with Java based applications and platforms such as JMeter.  

### Create collections from exported `postman_collection.json` files
```java
  File jsonFile = new File("/path/to/your/exported/collection.json");
  Collection pmcTest = Collection.pmcFactory(jsonFile);
```

You can experiment with example collections in the [Resources](https://github.com/BidnessForB/JPostman/tree/main/src/main/resources/com/postman/collection) folder.  

### Import a collection directly from Postman

Got a [Collection ID](https://support.postman.com/hc/en-us/articles/5063785095319-How-to-find-the-ID-of-an-element-in-Postman) and a [Postman API Key](https://learning.postman.com/docs/developer/intro-api/#generating-a-postman-api-key)?  Create Collection object directly from Postman: 

```java
Collection pmcTest = Collection.pmcFactory(new PostmanID("<your collection id>"));
```

NOTE: You must set an environment variable named `POSTMAN_API_KEY` with the value of your Postman API key.  

### Create Collections from scratch

You can create a new, empty collection as well

```java
Collection newColl = Collection.pmcFactory();
newColl.setName("My new collection");
```

then create and add elements to your new collection

### Add requests

You can add a new request just by providing a URL:

```java
  Collection newColl = Collection.pmcFactory();
  newColl.addRequest("https://postman-echo.com/get");
```

### Edit collections: add, remove, edit and move Collection elements

JPostman allows you to add new Folders and Requests to your Postman Collections.  You can also add variables, Pre-Request and Test Scripts, requests, and responses.  In fact, 
you can add, edit, or remove any element in a Postman collection.  All `add` operations allow you to specify a position in the array of items on the object, meaning 
that you preserve the order of Folders, Requests, etc, in the collection.  

For example, adding a new Folder as the third `item` in the collection:

```java
 // Add a new folder to the 3rd position beneath the root of the collection
  Collection pmcTest = Collection.pmcFactory("/path/to/your/exported/collection.json");
  Folder newFolder = new Folder("new Folder");
  pmcTest.addFolder(newFolder, 2);
  
  ```
### Move collection elements
  
You can easily move elements from one parent to another.  For example, move a request to a new folder, or a folder to another folder or to the top level in the collection.

```java
  Collection pmcTest = Collection.pmcFactory("/path/to/your/exported/collection.json");
  Item newFolder1 = new Folder("new Folder One");
  Item newFolder2 = new Folder("new Folder Two");
  pmcTest.addFolder(newFolder1);
  pmcTest.addFolder(newFolder2);
  pmcTest.moveItem(newFolder2, newFolder1);
 ```

  ### Combining collections
  
  You can combine collections as well.  
  
  ```java
  // Combine collections, inserting the source collection as a Folder in the 2d position from the root of the target collection
  Collection pmcTarget = Collection.pmcFactory("/path/to/your/exported/collection.json");
  Collection pmcSource = Collection.pmcFactory("/path/to/another/collection.json");
  pmcTest.addCollection(pmcSource);
  ```
 When a collection is added, a new Folder is created and all of the added collection's elements are linked to that folder.  The folder in turn is then linked to the target collection.  All folders, requests, pre-request and test scripts are copied over from the source to the target collection.  Collection variables are appended to the target collections array of variables.

There are options for copying (or not copying) the source collections scripts and variables as well.  You can also specify a specific parent to receive the source collection.

  
### Write your edited collections to a JSON file

JPostman allows you to generate JSON for your collections.  You can also write your collections to a file which can then be imported into Postman.
```java

  // Combine collections, inserting the source collection as a Folder in the 2d position from the root of the target collection
  // Then set the value of a String variable to the JSON for the new, combined collection.  
  Collection pmcTarget = Collection.pmcFactory("/path/to/your/exported/collection.json");
  Collection pmcSource = Collection.pmcFactory("/path/to/another/collection.json");
  pmcTest.addCollection(pmcSource);
  pmcTest.writeToFile("new-collection.json");

  ### Create or update a collection in Postman

  You can upsert (create or update) a collection in Postman, optionally adding it to a workspace.
   - If a collection does not have a collection ID, it will be created on Postman, and given the UUID returned by Postman
   - You can optionally include a workspace ID to link the newly created collection to that workspace
   - If a collection has a collection ID, it will be updated in Postman.
   
```
  pmcTest.upsertToPostman(new PostmanID(<POSTMAN_WORKSPACE_ID>"));
```

  ```

 ```
 
 ### Validate collections (and any collection element) against the Postman Collection Schema

 Use the `Collection.validate() method to ensure that the JSON emitted by your instance of Collection conforms with the Postman Collection schema:

 ```java
  boolean isValid = myCollection.validate();
 ```

JPostman uses the NetworkNT [json-schema-validator](https://github.com/networknt/json-schema-validator) to validate JSON against a JSON schema.  

 If the `validate()` method returns `false`, call `getValidationMessages()` for an array of [ValidationMessage](https://javadoc.io/doc/com.networknt/json-schema-validator/1.0.51/com/networknt/schema/ValidationMessage.html) objects describing differences between your collections generated JSON and the Postman schema.  

 #### Individual Elements
 Since collections and the elements they contain all extend the <code><a href="https://github.com/BidnessForB/JPostman/blob/main/src/main/java/com/postman/collection/CollectionElement.java">CollectionElement</a></code> class they have the potential to be individually validated as well.  Currently, element validation is supported for: 
  
  - Collection
  - Item
  - RequestAuth
  - BodyElement
 
 
 ## Implementation 
 
Java does not natively support JSON in the way that NodeJS does.  Object models based on JSON must be manually constructed.  Forutnately the brilliant folks at Google came up with [GSON](https://github.com/google/gson) a library that does this automatically.  Basically you create Java classes with member variables that match the keys in the JSON file.  GSON then parses the file and builds out the object model.  GSON also allows for an object model to be written out to JSON.  

GSON does not link parents to their child objects, so deriving parent objects is not straightforward.  In this implementation we recurse the object tree until we find the object which contains the target object, and return that as the parent.  

GSON also works with arrays by default rather than more convenient Java collections mechanisms like Collections, Maps, etc.  This has the advantage of simplicity but does make adding, removing, or re-ordering collection elements somewhat more complex and onerous.  

## Postman Collection File Structure

You can review the schema for the Postman Collection v2.1 format [here](https://schema.getpostman.com/json/collection/v2.1.0/collection.json). The Postman team have provided a comprehensive overview of the Collection format [here](https://collectionformat.gatsbyjs.io/docs/getting-started/overview/)

In a nutshell, a Postman collection file consists of the following elements
1. An `info` section containing collection metadata, including it's name, description, and Postman UID
2. Zero or more `item` elements, which can themselves contain an array if `item` elements.  There are two types of `item` objects
 - A *Request* is an item that contains a `request` key.  These are rendered in Postman as Requests.  Request items do not contain sub-items, they are always leaf elements. 
 - A *folder* is an item that contains zero or more sub-items, but which does NOT contain a `request` key.  
3. Pre-Request and Test scripts are stored in an `event` key.  Any item can have an `event` key.
4. Collection variables are stored under the `variables` key.  

Thus, a collection is `info`, `variables`, `events`, and an arry if `item`s, each of which can contain any number of sub-items.  

