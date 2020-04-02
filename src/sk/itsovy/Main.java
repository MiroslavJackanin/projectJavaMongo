package sk.itsovy;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        MongoClient mongo = new MongoClient( "localhost" , 27017 );

        MongoCredential credential = MongoCredential.createCredential("sampleUser", "company", "".toCharArray());
        System.out.println("Connected to the database successfully");

        MongoDatabase database = mongo.getDatabase("company");

        MongoCollection<Document> collection = database.getCollection("users");
        System.out.println("Collection selected successfully");
        Document document = new Document("name", "Peter Slabý")
                .append("age", 14)
                .append("status", "single");

        collection.insertOne(document);

        document = new Document("name", "Ondrej Oleg")
                .append("age", 34)
                .append("status", "single");

        collection.insertOne(document);

        List<Document> myList = new ArrayList<>();

        document = new Document("name", "Peter Svar")
                .append("age", 21)
                .append("status", "married");

        myList.add(document);

        document = new Document("name", "Juraj Drevo")
                .append("age", 89)
                .append("status", "married");

        myList.add(document);

        document = new Document("name", "Juraj Druhy")
                .append("age", 88)
                .append("status", "divorced");

        myList.add(document);

        collection.insertMany(myList);

        System.out.println("Document inserted successfully");
    }
}
