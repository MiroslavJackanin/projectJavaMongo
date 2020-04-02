package sk.itsovy;

import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        MongoClient mongo = new MongoClient( "localhost" , 27017 );
        MongoCredential credential = MongoCredential.createCredential("sampleUser", "company", "".toCharArray());
        MongoDatabase database = mongo.getDatabase("company");
        MongoCollection<Document> collection = database.getCollection("users");

        System.out.println("INSERT ONE");
        Document document = new Document("name", "Peter Slabý")
                .append("age", 14)
                .append("status", "single");
        //collection.insertOne(document);

        document = new Document("name", "Ondrej Oleg")
                .append("age", 34)
                .append("status", "single");
        //collection.insertOne(document);

        System.out.println("INSERT MANY");
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

        //collection.insertMany(myList);

        System.out.println("QUERY DB");
        myList.clear();
        try (MongoCursor<Document> cursor = collection.find().iterator()) {
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                System.out.println(doc.toJson());
            }
        }

        System.out.println("DELETE ONE");
        BasicDBObject theQuery = new BasicDBObject();
        theQuery.put("name", "Peter Slabý");
        collection.deleteMany(theQuery);

        System.out.println("UPDATE ONE");
        Bson filter = new Document("name", "Ondrej Oleg");
        Bson newValue = new Document("name", "Oleg Ondrej");
        Bson updateOperationDocument = new Document("$set", newValue);
        collection.updateOne(filter, updateOperationDocument);

        try (MongoCursor<Document> cursor = collection.find().iterator()) {
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                System.out.println(doc.toJson());
            }
        }
    }
}
