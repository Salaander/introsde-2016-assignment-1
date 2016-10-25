# Introduction to Service Design and Engineering - Assignment 1

...

## About the code

We are using two controllers to manage the data and the models we have:

**HealtProfileReader** - resposible to read the XML DB file (db/people.xml) and parse information from it using XPATH and/or (un)marshalling

**HealthProfileWriter** - responsible to read the XML file by unmarshalling it and marshalling it back to either XML or JSON format

## Tasks

1. **Make a function that prints all people in the list with detail**
The program reads the XML file and using XPATH parses all personal information and prints it. (_HealthProfileReader.getPeople()_)
2. **A function that accepts id as parameter and prints the HealthProfile of the person with that id**
The program reads the XML file and finds the person with ID == 5 and prints his/her health profile using the **getWeight()** and **getHeight()** functions we did for the Lab 3. instruction 1. (_HealthProfileReader.getHealthProfileByPersonID()_)
3. **A function which accepts a weight and an operator (=, > , <) as parameters and prints people that fulfill that condition (i.e., >80Kg, =75Kg, etc.).**
The program reads the XML file and finds all the people who fit the given filter, it our test case it is ">90". (_HealthProfileReader.getPeopleWithWeight()_)
4. **Write a java application that does the marshalling and un-marshalling using classes generated with JAXB XJC.**
We marshall all the people's information found during Task 4 and marhall them to the (db/new_people.xml). (_HealthProfileWriter.marshallToXML()_)
For this I created the XSD schema file for the provided XML file, and also added new entries up to 20 to the original XML file.
5. **Write a java application that does the marshalling and un-marshalling using classes generated with JAXB XJC.**
We unmarshall all the people's information from the XML storage and print all information using the object we mapped them into. (_HealthProfileReader.getPeopleByUnmarshalling()_)
6. **Make your java application to convert also JSON**
We marshall all the information we used in Task 4 into a JSON (db/new_people.json). (_HealthProfileWriter.marshallToJSON()_)


## How to run it

1) Clone the repository

``git clone git@github.com:Salaander/introsde-2016-assignment-1.git .``

2) Enter the folder created

``cd introsde-2016-assignment-1``

3) Run the evaluation script

``ant execute.evaluation``

I tested to check out the repo to a new folder and do the above mentioned process, the ant command run successfully and the program executed each task as described in the Tasks part.

----

_To Be Fixed:_

HealthProfileReader actually reads People with health profiles, so it should be renames

----

Created by Nagy Sándor Tibor, 2016.10.23