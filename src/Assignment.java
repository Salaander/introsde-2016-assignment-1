import controller.HealthProfileReader;
import controller.HealthProfileWriter;
import model.HealthProfile;
import model.Person;
import model.generated.People;
import dao.PeopleStore;

import java.util.ArrayList;
import java.util.List;
import java.lang.String;

public class Assignment {

    private static void printPerson(Person p) {
        System.out.println();
        System.out.println("ID: " + p.getPersonId().toString());
        System.out.println("Full name: " + p.getFirstname() + " " + p.getLastname());
        System.out.println("Date of birth: " + p.getBirthdate());
        System.out.println("Health profile: " + p.getHProfile().toString());
    }

    public static void main(String[] args) throws Exception {
        HealthProfileReader healthProfileReader = new HealthProfileReader("db/people.xml");
        PeopleStore people = healthProfileReader.getPeople();

        // Task 1: runs instruction 2 based on Lab 3
        List<Person> list = people.getData();
        for (Person person : list) {
            Assignment.printPerson(person);
        }

        // Task 2: runs instruction 3 based on Lab 3 with id = 5 (make sure you have a person with such an id)
        System.out.println();
        Long PersonID = 15L;
        HealthProfile healthProfile = healthProfileReader.getHealtProfileByPersonID(PersonID);
        System.out.println("Found person with ID " + PersonID + ", showing health profile:");
        System.out.println(healthProfile.toString());
        System.out.println();

        // Task 3: runs instruction 4 based on Lab 3 with weight > 90
        System.out.println();

        // Task 4: runs instruction 2 based on Lab 4 (marshaling to XML - create 3 persons using java and marshal them to XML) - please print the content and save to .xml file

        // Task 5: runs instruction 2 based on Lab 4 (un-marshaling from XML)

        // Task 6: runs instruction 3 based on Lab 4 (marshaling to JSON - create 3 persons using java and marshal them to JSON) - please print the content and save to .json file

    }

    //public fu
}
