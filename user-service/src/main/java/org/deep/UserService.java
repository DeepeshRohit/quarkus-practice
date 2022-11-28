package org.deep;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.Document;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class UserService {
    @Inject
    MongoClient mongoClient;
   @RestClient
   ContactProxy cp;
    public List<User> list(){
        List<User> list = new ArrayList<>();
        MongoCursor<Document> cursor = getCollection().find().iterator();

        try {
            while (cursor.hasNext()) {
                Document document = cursor.next();
                User user = new User();
                user.setUserId(document.getLong("userId"));
                user.setName(document.getString("name"));
                user.setPhone(document.getString("phone"));
                list.add(user);
            }
        } finally {
            cursor.close();
        }
        return list;
    }
    public void add(User user){
        Document document= new Document()
                .append("userId",user.getUserId())
                .append("name", user.getName())
                .append("phone",user.getPhone());
        getCollection().insertOne(document);
    }
    private MongoCollection getCollection(){
        return mongoClient.getDatabase("user").getCollection("user");
    }
}
