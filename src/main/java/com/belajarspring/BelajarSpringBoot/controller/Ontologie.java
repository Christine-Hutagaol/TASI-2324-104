package com.belajarspring.BelajarSpringBoot.controller;

import com.belajarspring.BelajarSpringBoot.model.ontologyModel;
import org.apache.jena.ontology.*;
import org.apache.jena.query.*;
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

    public ontologyModel model;

    @RequestMapping(value = "/ontologies",method = RequestMethod.GET)
    public   List<JSONObject> getontologies() {
        List<JSONObject> list=new ArrayList();
        String fileName = "Medicinal Plant Ontology.owl";
        try {
            File file = new File(fileName);
            FileReader reader = new FileReader(file);
            OntModel model = ModelFactory
                    .createOntologyModel(OntModelSpec.OWL_DL_MEM);
            model.read(reader,null);
            Iterator ontologiesIter = model.listOntologies();
            while (ontologiesIter.hasNext()) {
                Ontology ontology = (Ontology) ontologiesIter.next();

                JSONObject obj = new JSONObject();
                obj.put("name",ontology.getLocalName());
                obj.put("uri",ontology.getURI());
                list.add(obj);

            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/classesList",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public   List<JSONObject> getClasses() {
        List<JSONObject> list=new ArrayList();
        String fileName = "Medicinal Plant Ontology.owl";
        try {
            File file = new File(fileName);
            FileReader reader = new FileReader(file);
            OntModel model = ModelFactory
                    .createOntologyModel(OntModelSpec.OWL_DL_MEM);
            model.read(reader,null);
            Iterator classIter = model.listClasses();
            while (classIter.hasNext()) {
                OntClass ontClass = (OntClass) classIter.next();
                JSONObject obj = new JSONObject();
                obj.put("name",ontClass.getLocalName());
                obj.put("uri",ontClass.getURI());
                list.add(obj);

            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/Individus",method = RequestMethod.GET)
    public   List<JSONObject> getIndividus() {
        List<JSONObject> list=new ArrayList();
        String fileName = "Medicinal Plant Ontology.owl";
        try {
            File file = new File(fileName);
            FileReader reader = new FileReader(file);
            OntModel model = ModelFactory
                    .createOntologyModel(OntModelSpec.OWL_DL_MEM);
            model.read(reader,null);
            Iterator individus = model.listIndividuals();
            while (individus.hasNext()) {
                Individual   sub = (Individual) individus.next();
                JSONObject obj = new JSONObject();
                obj.put("name",sub.getLocalName());
                obj.put("uri",sub.getURI());
                list.add(obj);

            }

            return list;


        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @RequestMapping(value = "/query/{key}",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public List<JSONObject> query(@PathVariable String key) {
        List<JSONObject> list=new ArrayList();
        String res = key;
        key = "";
        for (int i = 1 ; i < res.length(); i++) {
            key += res.charAt(i);
        }
        key.trim();
        if(key.length() == 0) return list;
        System.out.println(key);

        model = new ontologyModel();
        List<JSONObject> result = model.searchById(key);
        return result;

    }

    @RequestMapping(value = "/medicinal/byId/{key}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<JSONObject> getMedicinalById(@PathVariable String key){
        List<JSONObject> list=new ArrayList();
        System.out.println(key);

        model = new ontologyModel();
        List<JSONObject> result = model.detailById(key);
        return result;
    }

    @RequestMapping(value = "/medicinal/id/genus=", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<JSONObject> getGenus(@RequestParam String genus){
        List<JSONObject> list=new ArrayList();
        System.out.println(genus);

        model = new ontologyModel();
        List<JSONObject> result = model.detailById(genus);
        return result;
    }

}