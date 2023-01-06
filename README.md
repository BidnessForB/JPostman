# JPostman

A Java wrapper for Postman collection files.  

## Getting started

1. Download the JAR file from the releases tab
2. Import `com.postman.collection.PostmanCollection` in your source code.
3. Instantiate a new collection by passing the path to a Postman collection .json file to the `PostmanCollection.pmcFactory` method

```java
PostmanCollection pmcTest = PostmanCollection.PMCFactory("/path/to/your/exported/collection.json");
```

## What you can do

### Integrate with Java applications and platforms

Java is common in Enterprise software development environments.  JPostman enables you to create a complete Java object model of a Postman collection from an exported collection JSON file.  This provides a bridge for integrating 
with Java based applications and platforms such as JMeter.  

### Edit collections: add, remove, edit and move Collection elements

JPostman allows you to add new Folders and Requests to your Postman Collections.  You can also add variables, Pre-Request and Test Scripts, requests, and responses.  In fact, 
you can add, edit, or remove any element in a Postman collection.  All `add` operations allow you to specify a position in the array of items on the object, meaning 
that you preserve the order of Folders, Requests, etc, in the collection.  

For example, adding a new Folder as the third `item` in the collection:

```java
 // Add a new folder to the 3rd position beneath the root of the collection
  PostmanCollection pmcTest = PostmanCollection.PMCFactory("/path/to/your/exported/collection.json");
  PostmanItem newFolder = new PostmanItem("new Folder");
  pmcTest.addItem(newFolder, 2);
  
  ```
  ### Move collection elements
  
You can easily move elements from one parent to another.  For example, move a request to a new folder, or a folder to another folder or to the top level in the collection.

```java
  PostmanCollection pmcTest = PostmanCollection.PMCFactory("/path/to/your/exported/collection.json");
  PostmanItem newFolder1 = new PostmanItem("new Folder One");
  PostmanItem newFolder2 = new PostmanItem("new Folder Two");
  pmcTest.addItem(newFolder1);
  pmcTest.addItem(newFolder2);
  pmcTest.moveItem(newFolder2, newFolder1);
 ```


  
  ### Combining collections
  
  You can combine collections as well.  
  
  ```java
  // Combine collections, inserting the source collection as a Folder in the 2d position from the root of the target collection
  PostmanCollection pmcTarget = PostmanCollection.PMCFactory("/path/to/your/exported/collection.json");
  PostmanCollection pmcSource = PostmanCollection.PMCFactory("/path/to/another/collection.json");
  pmcTest.addItem(pmcSource, 2);
  ```
 You can add a collection in this way either to the top level Collection itself or to any folder in the collection.  
 
 All folders, requests, pre-request and test scripts are copied over.  Collection variables are appended to the target collections array of variables.
  
  **CAUTION** Currently there is no check for duplicate variables names between the two collections.
  
### Write your edited collections to a JSON file

JPostman allows you to generate JSON for your collections.  You can also write your collections to a file which can then be imported into Postman.

```java

  // Combine collections, inserting the source collection as a Folder in the 2d position from the root of the target collection
  // Then set the value of a String variable to the JSON for the new, combined collection.  
  PostmanCollection pmcTarget = PostmanCollection.PMCFactory("/path/to/your/exported/collection.json");
  PostmanCollection pmcSource = PostmanCollection.PMCFactory("/path/to/another/collection.json");
  pmcTest.addItem(pmcSource, 2);
  pmcTest.writeToFile("new-collection.json");
 ```
  


