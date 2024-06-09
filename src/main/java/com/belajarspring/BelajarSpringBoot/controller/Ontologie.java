package com.belajarspring.BelajarSpringBoot.controller;

import com.belajarspring.BelajarSpringBoot.model.ontologyModel;
import org.apache.jena.ontology.*;
import org.apache.jena.rdf.model.ModelFactory;
import org.json.simple.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:63342")
public class Ontologie {

    private ontologyModel model;

    @RequestMapping(value = "/ontologies", method = RequestMethod.GET)
    public List<JSONObject> getOntologies() {
        List<JSONObject> list = new ArrayList<>();
        String fileName = "Medicinal Plant Ontology.owl";
        try {
            File file = new File(fileName);
            FileReader reader = new FileReader(file);
            OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM);
            model.read(reader, null);
            Iterator ontologiesIter = model.listOntologies();
            while (ontologiesIter.hasNext()) {
                Ontology ontology = (Ontology) ontologiesIter.next();

                JSONObject obj = new JSONObject();
                obj.put("name", ontology.getLocalName());
                obj.put("uri", ontology.getURI());
                list.add(obj);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/classesList", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<JSONObject> getClasses() {
        List<JSONObject> list = new ArrayList<>();
        String fileName = "Medicinal Plant Ontology.owl";
        try {
            File file = new File(fileName);
            FileReader reader = new FileReader(file);
            OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM);
            model.read(reader, null);
            Iterator classIter = model.listClasses();
            while (classIter.hasNext()) {
                OntClass ontClass = (OntClass) classIter.next();
                JSONObject obj = new JSONObject();
                obj.put("name", ontClass.getLocalName());
                obj.put("uri", ontClass.getURI());
                list.add(obj);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/Individus", method = RequestMethod.GET)
    public List<JSONObject> getIndividus() {
        List<JSONObject> list = new ArrayList<>();
        String fileName = "Medicinal Plant Ontology.owl";
        try {
            File file = new File(fileName);
            FileReader reader = new FileReader(file);
            OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM);
            model.read(reader, null);
            Iterator individus = model.listIndividuals();
            while (individus.hasNext()) {
                Individual sub = (Individual) individus.next();
                JSONObject obj = new JSONObject();
                obj.put("name", sub.getLocalName());
                obj.put("uri", sub.getURI());
                list.add(obj);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/query/{key}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<JSONObject> query(@PathVariable String key) {
        List<JSONObject> list = new ArrayList<>();
        String res = key;
        key = "";
        for (int i = 1; i < res.length(); i++) {
            key += res.charAt(i);
        }
        key = key.trim();
        if (key.length() == 0) return list;
        System.out.println(key);

        model = new ontologyModel();
        List<JSONObject> result = model.searchById(key);
        return result;
    }

    @RequestMapping(value = "/medicinal/byId/{key}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<JSONObject> getMedicinalById(@PathVariable String key) {
        List<JSONObject> list = new ArrayList<>();
        System.out.println(key);

        model = new ontologyModel();
        List<JSONObject> result = model.detailById(key);
        return result;
    }

    @RequestMapping(value = "/query/class/{className}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<JSONObject> getProductsByClass(@PathVariable String className) {
        List<JSONObject> list = new ArrayList<>();
        System.out.println("Class name: " + className);
    
        model = new ontologyModel();
        List<JSONObject> result = model.searchByClassName(className); // Use searchByClassName for filtering
        return result;
    }
    
    @RequestMapping(value = "/query/division/{divisionName}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<JSONObject> getProductsByDivision(@PathVariable String divisionName) {
        List<JSONObject> list = new ArrayList<>();
        System.out.println("Division name: " + divisionName);
    
        model = new ontologyModel();
        List<JSONObject> result = model.searchByDivisionName(divisionName); // Use searchByDivisionName for filtering
        return result;
    }

    @RequestMapping(value = "/query/order/{orderName}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<JSONObject> getProductsByOrder(@PathVariable String orderName) {
        List<JSONObject> list = new ArrayList<>();
        System.out.println("Order name: " + orderName);
    
        model = new ontologyModel();
        List<JSONObject> result = model.searchByOrderName(orderName); // Use searchByOrderName for filtering
        return result;
    }

    @RequestMapping(value = "/query/family/{familyName}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<JSONObject> getProductsByFamily(@PathVariable String familyName) {
        List<JSONObject> list = new ArrayList<>();
        System.out.println("Family name: " + familyName);
    
        model = new ontologyModel();
        List<JSONObject> result = model.searchByFamilyName(familyName); // Use searchByFamilyName for filtering
        return result;
    }

    @RequestMapping(value = "/query/genus/{genusName}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<JSONObject> getProductsByGenus(@PathVariable String genusName) {
        List<JSONObject> list = new ArrayList<>();
        System.out.println("Genus name: " + genusName);
    
        model = new ontologyModel();
        List<JSONObject> result = model.searchByGenusName(genusName); // Use searchByGenusName for filtering
        return result;
    }

    @RequestMapping(value = "/query/species/{speciesName}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<JSONObject> getProductsBySpecies(@PathVariable String speciesName) {
        List<JSONObject> list = new ArrayList<>();
        System.out.println("Species name: " + speciesName);
    
        model = new ontologyModel();
        List<JSONObject> result = model.searchBySpeciesName(speciesName); // Use searchBySpeciesName for filtering
        return result;
    }


    @RequestMapping(value = "/query/disease/{diseaseName}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<JSONObject> getProductsByDisease(@PathVariable String diseaseName) {
        List<JSONObject> list = new ArrayList<>();
        System.out.println("Disease name: " + diseaseName);
    
        model = new ontologyModel();
        List<JSONObject> result = model.searchByDiseaseName(diseaseName); // Use searchByFamilyName for filtering
        return result;
    }

    @RequestMapping(value = "/query/part/{partName}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<JSONObject> getProductsByPart(@PathVariable String partName) {
        List<JSONObject> list = new ArrayList<>();
        System.out.println("Part name: " + partName);
    
        model = new ontologyModel();
        List<JSONObject> result = model.searchByPartName(partName); // Use searchByFamilyName for filtering
        return result;
    }

    @RequestMapping(value = "/query/method/{methodName}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<JSONObject> getProductsByMethod(@PathVariable String methodName) {
        List<JSONObject> list = new ArrayList<>();
        System.out.println("Method name: " + methodName);
    
        model = new ontologyModel();
        List<JSONObject> result = model.searchByMethodName(methodName); // Use searchByFamilyName for filtering
        return result;
    }

    @RequestMapping(value = "/query/compounds/{compoundsName}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<JSONObject> getProductsByCompounds(@PathVariable String compoundsName) {
        List<JSONObject> list = new ArrayList<>();
        System.out.println("Compounds name: " + compoundsName);
    
        model = new ontologyModel();
        List<JSONObject> result = model.searchByCompoundsName(compoundsName); // Use searchByFamilyName for filtering
        return result;
    }
    
    @RequestMapping(value = "/query/use/{useName}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<JSONObject> getProductsByUse(@PathVariable String useName) {
        List<JSONObject> list = new ArrayList<>();
        System.out.println("Use name: " + useName);
    
        model = new ontologyModel();
        List<JSONObject> result = model.searchByUseName(useName); // Use searchByFamilyName for filtering
        return result;
    }
}
