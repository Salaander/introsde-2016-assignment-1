package controller;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;

import model.HealthProfile;
import model.Person;
import dao.PeopleStore;
import model.generated.People;

public class HealthProfileWriter {

    JAXBContext jaxb;
    Marshaller marshaller;

    public HealthProfileWriter() throws JAXBException {
        this.jaxb = JAXBContext.newInstance(PeopleStore.class);
        this.marshaller = this.jaxb.createMarshaller();
        this.marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
    }

    public void marshallToXML(PeopleStore people, String FileName) throws JAXBException {
        this.marshaller.marshal(people,new File(FileName)); // marshalling into a file
        this.marshaller.marshal(people, System.out);
    }

    public void marshallToJSON(PeopleStore people, String FileName) throws JAXBException, IOException {
        // Jackson Object Mapper
        ObjectMapper mapper = new ObjectMapper();

        // Adding the Jackson Module to process JAXB annotations
        JaxbAnnotationModule module = new JaxbAnnotationModule();

        // configure as necessary
        mapper.registerModule(module);
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        mapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);

        String result = mapper.writeValueAsString(people);
        System.out.println(result);
        mapper.writeValue(new File(FileName), people);
    }
}
