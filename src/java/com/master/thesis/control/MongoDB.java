/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.master.thesis.control;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.util.JSON;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Luis Javier Surez Meza <luisjavier.suarezmeza at gmail.com>
 */
public class MongoDB {

    private MongoClient mongoClient;

    /**
     * MongoDB Constructor
     */
    public MongoDB() {
        try {
            this.mongoClient = new MongoClient();
        } catch (UnknownHostException ex) {
            Logger.getLogger(MongoDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * insertDocumentOnDB allows to insert a document in a specified collection
     * of some DB.
     *
     * @param dbName It is the DB name
     * @param collectionName It is collection's name
     * @param json It is the document in JSON format
     */
    public void insertDocumentIntoDB(String dbName, String collectionName, String json) {
        //System.out.println("Entró insertDocumentIntoDB: " + dbName + " - " + collectionName + " - " + json);
        DB db = this.mongoClient.getDB(dbName);
        DBCollection collection = db.getCollection(collectionName);
        DBObject dbObject = (DBObject) JSON.parse(json);
        collection.insert(dbObject);

        DBCursor cursorDocJSON = collection.find();
        while (cursorDocJSON.hasNext()) {
            DBObject dBObject = cursorDocJSON.next();
            //System.out.println("Result: " + dBObject);
        }
    }

    /**
     * insertDBObjectIntoDB allows to insert a BasicDBObject in a specified
     * collection of some DB.
     *
     * @param dbName It is the DB name.
     * @param collectionName It is collection's name.
     * @param dbObject It si a BasicDBObject which corresponds to the object to
     * be inserted.
     */
    public void insertDBObjectIntoDB(String dbName, String collectionName, BasicDBObject dbObject) {

        DB db = this.mongoClient.getDB(dbName);
        DBCollection collection = db.getCollection(collectionName);
        collection.insert(dbObject);

        DBCursor cursorObject = collection.find();
        while (cursorObject.hasNext()) {
            DBObject dBObject = cursorObject.next();
            //System.out.println("Result: " + dBObject);
        }
    }

    /**
     * getJsonFromDB allows to get a JSON document from a Query (DBObject).
     *
     * @param dbName
     * @param collectionName
     * @param query
     * @return
     */
    public String getJsonFromDB(String dbName, String collectionName, BasicDBObject query) {

        DB db = this.mongoClient.getDB(dbName);
        DBCollection collection = db.getCollection(collectionName);
        DBObject queryObject = collection.findOne(query);
        String result = String.valueOf(queryObject);
        System.out.println("Query Result: " + result);

        return result;
    }

    /**
     * Este método me permite obtener un arreglo de DBObject provenientes de
     * cualquier collecion que se especifíque.
     *
     * @param dbName
     * @param collName
     * @return
     */
    public DBCollection getDocumentsFromCollection(String dbName, String collName) {

        DB db = this.mongoClient.getDB(dbName);
        System.out.println("Result: " + db.getName());
        DBCollection collection = null;
        BasicDBObject query = new BasicDBObject();
        BasicDBObject fields = new BasicDBObject().append("_id", false).append("Timestamp", false);
        Set<String> colls = db.getCollectionNames();
        ArrayList<DBObject> array = new ArrayList<DBObject>();
        for (String c : colls) {
            if (c.equalsIgnoreCase(collName)) {
                collection = db.getCollection(c);
            }
        }
        return collection;
    }

    /**
     * getFieldFromDB allows to get a document's field from a Query (DBObject).
     *
     * @param dbName
     * @param collectionName
     * @param query
     * @return String
     */
    public String getFieldFromDB(String dbName, String collectionName, BasicDBObject query) {

        DB db = this.mongoClient.getDB(dbName);
        DBCollection collection = db.getCollection(collectionName);
        DBObject queryObject = collection.findOne(query);
        queryObject.get("_id");
        String field = String.valueOf(queryObject.get("_id"));
        //System.out.println("Resoult ... " + field);
        return field;
    }

    public void setGoalToCollection(String dbName, String collName, BasicDBObject dBObject) {
        
        DB db = this.mongoClient.getDB(dbName);
        DBCollection collection = db.getCollection(collName);
        collection.insert(dBObject);
    }
}
