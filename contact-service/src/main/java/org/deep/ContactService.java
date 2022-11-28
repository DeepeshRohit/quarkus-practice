package org.deep;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.Document;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class ContactService {
    @Inject
    MongoClient mongoClient;

    public List<Contact> list(){
        List<Contact> list = new ArrayList<>();
        MongoCursor<Document> cursor = getCollection().find().iterator();

        try {
            while (cursor.hasNext()) {
                Document document = cursor.next();
                Contact  contact= new Contact();
                contact.setcId(document.getLong("cId"));
                contact.setEmail(document.getString("email"));
                contact.setContactName(document.getString("contactName"));
                contact.setUserId(document.getLong("userId"));
                list.add(contact);
            }
        } finally {
            cursor.close();
        }
        return list;
    }
    public void add(Contact contact){
        Document document= new Document()
                .append("cId",contact.getcId())
                .append("email", contact.getEmail())
                .append("contactName",contact.getContactName())
                .append("userId",contact.getUserId());
        getCollection().insertOne(document);
    }
    private MongoCollection getCollection(){
        return mongoClient.getDatabase("contact").getCollection("contact");
    }


}
