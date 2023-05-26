import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.Charset;

import com.google.gson.Gson;

import person.Person;
import person.Phones;

public class GsonParser {
    public static void main(String[] args) {
        String personJsonFile = "src/main/resources/Person.json";
        String charset = "UTF-8";

        Person deserializedPerson = (Person)deserializeFromFile(personJsonFile, charset, Person.class);

        serializeToString(deserializedPerson);

        getPersonFriendsInfo(deserializedPerson);
    }

    private static void serializeToString(Object object) {
        Gson gson = new Gson();
        String json = gson.toJson(object);
        System.out.println(json);
    }

    private static Object deserializeFromFile(String fileName, String charset, Class classToDeserialize) {
        Object obj = null;

        try (Reader reader = new FileReader(fileName, Charset.forName(charset))) {
            Gson gson = new Gson();
            obj = gson.fromJson(reader, classToDeserialize);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return obj;
    }

    private static void getPersonFriendsInfo(Person deserializedPerson) {
        for (Person friend : deserializedPerson.friends) {
            System.out.println(friend.lastName);
            for (Phones phone : friend.phoneNumbers) {
                System.out.println(" - phone type: " + phone.type + ", phone number : " + phone.number);
            }
            if (friend.friends != null) {
                for (Person friendOfFriend : friend.friends) {
                    System.out.println(" - friend: " + friendOfFriend.lastName);
                }
            }
        }
    }
}
