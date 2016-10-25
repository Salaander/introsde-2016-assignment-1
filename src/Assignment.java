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
        HealthProfileWriter healthProfileWriter = new HealthProfileWriter();

        // Task -1: Testing the new getWeight and getHeight functions
        // Use xpath to implement methods like getWeight and getHeight (getWeight(personID) returns weight of a given person, the same for getHeight)

        //System.out.println();
        //healthProfileReader.getHeightByPersonID("0019");
        //healthProfileReader.getWeightByPersonID("0019");
        //System.out.println();

        // Task 1: runs instruction 2 based on Lab 3
        // Make a function that prints all people in the list with detail
        System.out.println("Task 1: runs instruction 2 based on Lab 3");
        List<Person> list = healthProfileReader.getPeople();
        for (Person person : list) {
            Assignment.printPerson(person);
        }

        // Task 2: runs instruction 3 based on Lab 3 with id = 5 (make sure you have a person with such an id)
        // A function that accepts id as parameter and prints the HealthProfile of the person with that id
        System.out.println();
        String PersonID = "0005";
        System.out.println("Task 2: runs instruction 3 based on Lab 3 with id = 5");
        HealthProfile healthProfile = healthProfileReader.getHealthProfileByPersonID(PersonID);
        System.out.println("Found person with ID " + PersonID + ", showing health profile:");
        System.out.println(healthProfile.toString());
        System.out.println();

        // Task 3: runs instruction 4 based on Lab 3 with weight > 90
        // A function which accepts a weight and an operator (=, > , <) as parameters and prints people that fulfill that condition (i.e., >80Kg, =75Kg, etc.).
        System.out.println();
        System.out.println("Task 3: runs instruction 4 based on Lab 3 with weight >90");
        List<Person> list2 = healthProfileReader.getPeopleWithWeight(">", "90");
        for (Person person : list2) {
            Assignment.printPerson(person);
        }

        PeopleStore ps = new PeopleStore();
        ps.setData(list2);

        // Task 4: runs instruction 2 based on Lab 4 (marshaling to XML - create 3 persons using java and marshal them to XML) - please print the content and save to .xml file
        System.out.println();
        System.out.println("Task 4: runs instruction 2 based on Lab 4 ");
        healthProfileWriter.marshallToXML(ps, "db/new_people.xml");

        // Task 5: runs instruction 2 based on Lab 4 (un-marshaling from XML)
        System.out.println();
        System.out.println("Task 5: runs instruction 2 based on Lab 4");
        PeopleStore p = healthProfileReader.getPeopleByUnmarshalling();
        for (Person person : p.getData()) {
            Assignment.printPerson(person);
        }

        // Task 6: runs instruction 3 based on Lab 4 (marshaling to JSON - create 3 persons using java and marshal them to JSON) - please print the content and save to .json file
        System.out.println();
        System.out.println("Task 6: runs instruction 3 based on Lab 4");
        healthProfileWriter.marshallToJSON(ps, "db/new_people.json");
    }

    //public fu
}
