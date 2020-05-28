package UrlChangeCheckService;
import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.MongoClientSettings;

import org.bson.Document;


public class Database {
    ConnectionString connString = new ConnectionString(
            "mongodb+srv://cloudhomework:cloud123@cluster-yaufg.mongodb.net/test?retryWrites=true&w=majority"
    );
    MongoDatabase database;
    public Database(){
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connString)
                .retryWrites(true)
                .build();
        MongoClient mongoClient = MongoClients.create(settings);
        database = mongoClient.getDatabase("test");
        MongoCollection<Document> collection = database.getCollection("Sites");
        System.out.println(collection);

    }


}
