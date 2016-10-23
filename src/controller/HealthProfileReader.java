package controller;

import java.io.File;
import java.io.FileReader;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;


import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import model.HealthProfile;
import model.Person;
import dao.PeopleStore;
import model.generated.People;

public class HealthProfileReader {
	public PeopleStore people = new PeopleStore();

    public HealthProfileReader(String PathToXML) throws Exception {

        // Setup for using XPATH
        DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
        domFactory.setNamespaceAware(true);
        DocumentBuilder builder = domFactory.newDocumentBuilder();

        // We read the XML data
        JAXBContext jc = JAXBContext.newInstance(PeopleStore.class);
        Unmarshaller um = jc.createUnmarshaller();
        this.people = (PeopleStore) um.unmarshal(new FileReader(PathToXML));
        /*List<Person> list = people.getData();
        for (Person person : list) {
          System.out.println("Person: " + person.getFirstname() + " born " + person.getBirthdate());
        }
        System.out.println("Done");*/
    }

    public PeopleStore getPeople() throws Exception {
        return this.people;
    }

    /* Method to find a Person with a given ID and return it's health profile (needed for task 2)
       We assume ID is a uniqe identifier so we simply return the first occurence
     */
    public HealthProfile getHealtProfileByPersonID(Long ID) throws Exception {
        for (Person person : people.getData()) {
            if (person.getPersonId() == (Long)ID) {
                return person.getHProfile();
            }
        }
        throw new Exception("No person found with ID: " + ID);
    }

    public void printAllNames() {
        System.out.println("Loading books.xml...");
        Document doc = builder.parse("books.xml");

        XPathFactory factory = XPathFactory.newInstance();
        XPath xpath = factory.newXPath();
        System.out.println("Reading list of titles...");
        System.out.println("(using xpath = /bookstore/book/title/text()");
        XPathExpression expr = xpath.compile("/bookstore/book/title/text()");

        Object result = expr.evaluate(doc, XPathConstants.NODESET);
        NodeList nodes = (NodeList) result;
        for (int i = 0; i < nodes.getLength(); i++) {
            System.out.println(nodes.item(i).getNodeValue());
        }
    }
}
