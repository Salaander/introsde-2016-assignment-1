package controller;

import java.io.File;
import java.io.FileReader;
import java.util.*;
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
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import model.HealthProfile;
import model.Person;
import dao.PeopleStore;
import model.generated.People;

public class HealthProfileReader {
	public PeopleStore people = new PeopleStore();

    private String PathToXML;
    private DocumentBuilder builder;
    private Document document;
    private XPath xpath;

    public HealthProfileReader(String PathToXML) throws Exception {
        this.PathToXML = PathToXML;

        // Setup for using XPATH
        DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
        domFactory.setNamespaceAware(true);
        this.builder = domFactory.newDocumentBuilder();
        this.document = this.builder.parse(PathToXML);
        XPathFactory factory = XPathFactory.newInstance();
        this.xpath = factory.newXPath();

        // Mapping the XML data into Objects with JAXB
        JAXBContext jc = JAXBContext.newInstance(PeopleStore.class);
        Unmarshaller um = jc.createUnmarshaller();
        this.people = (PeopleStore) um.unmarshal(new FileReader(PathToXML));
    }

    public PeopleStore getPeopleByUnmarshalling() throws Exception {
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

    /* Retrive a persons Weight by his ID using XPATH
     */
    public String getWeightByPersonID(String ID) throws XPathExpressionException {
        XPathExpression expr = this.xpath.compile("/people/person[@id=" + ID + "]/healthprofile/weight");
        Object result = expr.evaluate(this.document, XPathConstants.NODESET);
        NodeList nodes = (NodeList) result;
        return nodes.item(0).getChildNodes().item(0).getNodeValue();
    }

    /* Retrive a persons Height by his ID using XPATH
     */
    public String getHeightByPersonID(String ID) throws XPathExpressionException {
        XPathExpression expr = this.xpath.compile("/people/person[@id=" + ID + "]/healthprofile/height");
        Object result = expr.evaluate(this.document, XPathConstants.NODESET);
        NodeList nodes = (NodeList) result;
        return nodes.item(0).getChildNodes().item(0).getNodeValue();
    }

    public Person personFromXPATH(Element node) {
        String id = node.getAttributes().item(0).getTextContent();
        String firstname = node.getChildNodes().item(1).getTextContent();
        String lastname = node.getChildNodes().item(3).getTextContent();
        String birthdate = node.getChildNodes().item(5).getTextContent();
        String weight = node.getElementsByTagName("weight").item(0).getChildNodes().item(0).getNodeValue();
        String height = node.getElementsByTagName("height").item(0).getChildNodes().item(0).getNodeValue();
        HealthProfile hp = new HealthProfile(Double.parseDouble(weight),Double.parseDouble(height));

        return new Person(Long.parseLong(id), firstname, lastname, birthdate, hp);
    }

    /* Retrive all information from all persons using XPATH
     */
    public List<Person> getPeople() throws XPathExpressionException {
        List<Person> people = new ArrayList<Person>();
        XPathExpression expr = this.xpath.compile("/people/person");
        Object result = expr.evaluate(this.document, XPathConstants.NODESET);
        NodeList nodes = (NodeList) result;
        for (int i = 0; i < nodes.getLength(); i++) {
            people.add(personFromXPATH((Element) nodes.item(i)));
        }
        return people;
    }

    /* Retrive people with certain weight parameter using XPATH
     * Usaqe: ">90", "<80", "=70"
     * Pass it a string with an operator from this list: > < =
     * and an integer
     * and the function will return all personal data for those that the filter is true
     */
    public List<Person> getPeopleWithWeight(String operator, String weight) throws XPathExpressionException {
        List<Person> people = new ArrayList<Person>();
        XPathExpression expr = this.xpath.compile("/people/person[healthprofile/weight" + operator + weight + "]");
        Object result = expr.evaluate(this.document, XPathConstants.NODESET);
        NodeList nodes = (NodeList) result;
        for (int i = 0; i < nodes.getLength(); i++) {
            people.add(personFromXPATH((Element) nodes.item(i)));
        }
        return people;
    }

    public void printAllNames() throws XPathExpressionException {
        XPathFactory factory = XPathFactory.newInstance();
        XPath xpath = factory.newXPath();
        System.out.println("Reading list of titles...");
        System.out.println("(using xpath = //firstname)");
        XPathExpression expr = xpath.compile("//firstname/text()");

        Object result = expr.evaluate(this.document, XPathConstants.NODESET);
        NodeList nodes = (NodeList) result;
        for (int i = 0; i < nodes.getLength(); i++) {
            System.out.println(nodes.item(i).getNodeValue());
        }
    }
}
