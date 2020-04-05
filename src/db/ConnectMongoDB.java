package db;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class ConnectMongoDB {

    public static MongoDatabase mongoDatabase = null;

    public static MongoDatabase connectToMongoDB(String dataBaseName) {
        MongoClient mongoClient = new MongoClient();
        mongoDatabase = mongoClient.getDatabase(dataBaseName);
        System.out.println("Database Connected");

        return mongoDatabase;
    }

    public static String insertIntoToMongoDB(User user,String dataBaseName){
        String profile = user.getStName();
        MongoDatabase mongoDatabase = connectToMongoDB(dataBaseName);
        MongoCollection<Document> collection = mongoDatabase.getCollection("profile");
        Document document = new Document().append("stName",user.getStName()).append("stID", user.getStID()).
                append("stDOB",user.getStDOB());
        collection.insertOne(document);
        return profile + " has been registered";
    }
    public static String insertEmpObjectIntoMongoDB(List<Employee> employee, String dataBaseName,String collectionName){
        MongoDatabase mongoDatabase = connectToMongoDB(dataBaseName);
        MongoCollection myCollection = mongoDatabase.getCollection(collectionName);
        boolean collectionExists = mongoDatabase.listCollectionNames()
                .into(new ArrayList<String>()).contains(collectionName);
        if(collectionExists) {
            myCollection.drop();
        }
        for(int i=0; i<employee.size(); i++){
            MongoCollection<Document> collection = mongoDatabase.getCollection(collectionName);
            Document document = new Document().append("empName", employee.get(i).getEmpName()).append("empID",
                    employee.get(i).getEmpID()).append("empDOB",employee.get(i).getEmpDOB());
            collection.insertOne(document);
        }
        return  "Employee has been registered";
    }
    public static String insertStudentObjIntoMongoDB(List<Student> student, String dataBaseName,String profileName){
        MongoDatabase mongoDatabase = connectToMongoDB(dataBaseName);
        MongoCollection myCollection = mongoDatabase.getCollection(profileName);
        boolean collectionExists = mongoDatabase.listCollectionNames()
                .into(new ArrayList<String>()).contains(profileName);
        if(collectionExists) {
            myCollection.drop();
        }
        for(int i=0; i<student.size(); i++){
            MongoCollection<Document> collection = mongoDatabase.getCollection(profileName);
            Document document = new Document().append("firstName", student.get(i).getFirstName()).append("lastName",
                    student.get(i).getLastName()).append("score",student.get(i).getScore()).append("id", student.get(i).getId());
            collection.insertOne(document);
        }
        return  "Student has been registered";
    }

    public static List<User> readUserProfileFromMongoDB(String dataBaseName){
        List<User> list = new ArrayList<User>();
        User user = new User();
        MongoDatabase mongoDatabase = connectToMongoDB(dataBaseName);
        MongoCollection<Document> collection = mongoDatabase.getCollection("profile");
        BasicDBObject basicDBObject = new BasicDBObject();
        FindIterable<Document> iterable = collection.find(basicDBObject);
        for(Document doc:iterable){
            String stName = (String)doc.get("stName");
            user.setStName(stName);
            String stID = (String)doc.get("stID");
            user.setStID(stID);
            String stDOB = (String)doc.get("stDOB");
            user.setStID(stDOB);
            user = new User(stName,stID,stDOB);
            list.add(user);
        }
        return list;
    }

    public static List<Employee> readEmpProfileFromMongoDB(String dataBaseName){
        List<Employee> list = new ArrayList<Employee>();
        Employee emp = new Employee();
        MongoDatabase mongoDatabase = connectToMongoDB(dataBaseName);
        MongoCollection<Document> collection = mongoDatabase.getCollection("employee");
        BasicDBObject basicDBObject = new BasicDBObject();
        FindIterable<Document> iterable = collection.find(basicDBObject);
        for(Document doc:iterable){
            String empName = (String)doc.get("empName");
            emp.setEmpName(empName);
            String empID = (String)doc.get("empID");
            emp.setEmpID(empID);
            String empDOB = (String)doc.get("empDOB");
            emp.setEmpID(empDOB);
            emp = new Employee(empName,empID,empDOB);
            list.add(emp);
        }
        return list;
    }

    public static List<Student> readStudentListFromMongoDB(String profileName,String dataBaseName){
        List<Student> list = new ArrayList<Student>();
        Student student = new Student();
        MongoDatabase mongoDatabase = connectToMongoDB(dataBaseName);
        MongoCollection<Document> collection = mongoDatabase.getCollection(profileName);
        BasicDBObject basicDBObject = new BasicDBObject();
        FindIterable<Document> iterable = collection.find(basicDBObject);
        for(Document doc:iterable){
            String firstName = (String)doc.get("firstName");
            student.setFirstName(firstName);
            String lastName = (String)doc.get("lastName");
            student.setLastName(lastName);
            String score = (String)doc.get("score");
            student.setScore(score);
            String id = (String) doc.get("id");
            student.setId(id);
            student = new Student(student.getFirstName(),student.getLastName(),student.getScore(),student.getId());
            list.add(student);
        }
        return list;
    }

    public static void main(String[] args){
        List<Employee> empList = new ArrayList<Employee>();
        empList.add(new Employee("Emdad Milon","902","09-13-1985"));
        empList.add(new Employee("Sumon Das","809","07-20-1999"));
        insertEmpObjectIntoMongoDB(empList,"deepcoding","employee");
        List<Employee> readEmpList = readEmpProfileFromMongoDB("deepcoding");
        for(Employee emp:readEmpList){
            System.out.println(emp.getEmpName()+ " "+ emp.getEmpID()+" "+emp.getEmpDOB());
        }
        //insertIntoToMongoDB(new User("Naomi Chan", "4493","07-1996"),"deepcoding");
        List<User> user = readUserProfileFromMongoDB("deepcoding");
        for(User person:user){
            System.out.println(person.getStName()+ " "+ person.getStID());
        }
    }
}

