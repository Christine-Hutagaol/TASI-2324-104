package com.belajarspring.BelajarSpringBoot.model;

import org.apache.jena.graph.NodeFactory;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.RDFNode;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class ontologyModel {

    private static final String ONTOLOGY_FILE_NAME = "Medicinal Plant Ontology.owl";

    public List<JSONObject> searchById(String key) {
        List<JSONObject> list = new ArrayList<>();
        try {
            File file = new File(ONTOLOGY_FILE_NAME);
            if (!file.exists()) {
                System.out.println("File ontologi tidak ditemukan: " + ONTOLOGY_FILE_NAME);
                return list;
            }
    
            FileReader reader = new FileReader(file);
            OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM);
            model.read(reader, null);
    
            // Pisahkan kata kunci menjadi beberapa kata
            String[] keywords = key.split("\\s+");
    
            StringBuilder regexBuilder = new StringBuilder();
            for (String keyword : keywords) {
                // Tambahkan ekspresi regex untuk setiap kata kunci
                regexBuilder.append("regex(str(?medicinalPlant), '").append(keyword).append("', 'i') || ");
                regexBuilder.append("regex(str(?treatDisease), '").append(keyword).append("', 'i') || ");
                regexBuilder.append("regex(str(?id), '").append(keyword).append("', 'i') || ");
                regexBuilder.append("regex(str(?hasBenefits), '").append(keyword).append("', 'i') || ");
                regexBuilder.append("regex(str(?hasEnglishName), '").append(keyword).append("', 'i') || ");
                regexBuilder.append("regex(str(?hasLocalName), '").append(keyword).append("', 'i') || ");
            }
            // Hapus karakter terakhir (||) dari ekspresi regex
            String regex = regexBuilder.substring(0, regexBuilder.length() - 4);
    
            String sparql = "PREFIX http: <http://www.w3.org/2011/http#>\n" +
                    "PREFIX dd: <http://example.org/dummydata/>\n" +
                    "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                    "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n" +
                    "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                    "PREFIX d: <http://www.semanticweb.org/hp/ontologies/2024/1/DataTanamanObat#>\n" +
                    "SELECT ?medicinalPlant ?treatDisease ?id ?hasBenefits ?hasEnglishName ?hasLocalName\n" +
                    "WHERE { ?medicinalPlant d:hasId ?id;\n" +
                    "                 d:treatDisease ?treatDisease;\n" +
                    "                 d:hasBenefits ?hasBenefits;\n" +
                    "                 d:hasEnglishName ?hasEnglishName;\n" +
                    "                 d:hasLocalName ?hasLocalName.\n" +
                    "    FILTER(" + regex + ")\n" +
                    "}\n" +
                    "ORDER BY ASC(?treatDisease)";
    
            System.out.println("Executing SPARQL query: \n" + sparql);
    
            Query query = QueryFactory.create(sparql);
            try (QueryExecution qe = QueryExecutionFactory.create(query, model)) {
                ResultSet resultSet = qe.execSelect();
                if (!resultSet.hasNext()) {
                    System.out.println("No results found for query: \n" + sparql);
                }
                while (resultSet.hasNext()) {
                    QuerySolution solution = resultSet.nextSolution();
                    JSONObject obj = new JSONObject();
                    obj.put("medicinalPlant", getStringValue(solution, "medicinalPlant"));
                    obj.put("treatDisease", getStringValue(solution, "treatDisease"));
                    obj.put("hasBenefits", getStringValue(solution, "hasBenefits"));
                    obj.put("hasEnglishName", getStringValue(solution, "hasEnglishName"));
                    obj.put("hasLocalName", getStringValue(solution, "hasLocalName"));
                    obj.put("id", getStringValue(solution, "id"));
                    list.add(obj);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public List<JSONObject> detailById(String key) {
        List<JSONObject> list = new ArrayList<>();
        try {
            File file = new File(ONTOLOGY_FILE_NAME);
            if (!file.exists()) {
                System.out.println("File ontologi tidak ditemukan: " + ONTOLOGY_FILE_NAME);
                return list;
            }

            FileReader reader = new FileReader(file);
            OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM);
            model.read(reader, null);

            String sparql = "PREFIX http: <http://www.w3.org/2011/http#>\n" +
                    "PREFIX dd: <http://example.org/dummydata/>\n" +
                    "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                    "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n" +
                    "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                    "PREFIX d: <http://www.semanticweb.org/hp/ontologies/2024/1/DataTanamanObat#>\n" +
                    "SELECT ?medicinalPlant ?treatDisease ?managedBy ?utilizingParts ?containsActiveCompounds ?usedByMeansOf ?hasSideEffect ?id ?hasBenefits ?hasEnglishName ?hasLocalName ?hasClass ?hasDivisions ?hasFamily ?hasGenus ?hasKingdom ?hasOrder ?hasSpecies ?hasImageURL\n" +
                    "WHERE { ?medicinalPlant d:hasId ?id;\n" +
                    "                 d:managedBy ?managedBy;\n" +
                    "                 d:treatDisease ?treatDisease;\n" +
                    "                 d:utilizingParts ?utilizingParts;\n" +
                    "                 d:containsActiveCompounds ?containsActiveCompounds;\n" +
                    "                 d:usedByMeansOf ?usedByMeansOf;\n" +
                    "                 d:hasBenefits ?hasBenefits;\n" +
                    "                 d:hasEnglishName ?hasEnglishName;\n" +
                    "                 d:hasLocalName ?hasLocalName;\n" +
                    "                 d:hasSideEffect ?hasSideEffect;\n" +
                    "                 d:hasClass ?hasClass;\n" +
                    "                 d:hasDivisions ?hasDivisions;\n" +
                    "                 d:hasFamily ?hasFamily;\n" +
                    "                 d:hasGenus ?hasGenus;\n" +
                    "                 d:hasKingdom ?hasKingdom;\n" +
                    "                 d:hasOrder ?hasOrder;\n" +
                    "                 d:hasSpecies ?hasSpecies;\n" +
                    "                 d:hasImageURL ?hasImageURL;\n" +
                    "    FILTER (?id = '" + key + "')\n" +
                    "}\n";

            System.out.println("Executing SPARQL query: \n" + sparql);

            Query query = QueryFactory.create(sparql);
            try (QueryExecution qe = QueryExecutionFactory.create(query, model)) {
                ResultSet resultSet = qe.execSelect();
                if (!resultSet.hasNext()) {
                    System.out.println("No results found for query: \n" + sparql);
                }
                while (resultSet.hasNext()) {
                    QuerySolution solution = resultSet.nextSolution();
                    JSONObject obj = new JSONObject();
                    obj.put("medicinalPlant", getStringValue(solution, "medicinalPlant"));
                    obj.put("managedBy", getStringValue(solution, "managedBy"));
                    obj.put("treatDisease", getStringValue(solution, "treatDisease"));
                    obj.put("utilizingParts", getStringValue(solution, "utilizingParts"));
                    obj.put("containsActiveCompounds", getStringValue(solution, "containsActiveCompounds"));
                    obj.put("usedByMeansOf", getStringValue(solution, "usedByMeansOf"));
                    obj.put("hasSideEffect", getStringArray(solution, "hasSideEffect"));
                    obj.put("hasClass", getStringValue(solution, "hasClass"));
                    obj.put("hasDivisions", getStringValue(solution, "hasDivisions"));
                    obj.put("hasFamily", getStringValue(solution, "hasFamily"));
                    obj.put("hasGenus", getStringValue(solution, "hasGenus"));
                    obj.put("hasKingdom", getStringValue(solution, "hasKingdom"));
                    obj.put("hasOrder", getStringValue(solution, "hasOrder"));
                    obj.put("hasSpecies", getStringValue(solution, "hasSpecies"));
                    obj.put("hasBenefits", getStringValue(solution, "hasBenefits"));
                    obj.put("hasEnglishName", getStringValue(solution, "hasEnglishName"));
                    obj.put("hasLocalName", getStringValue(solution, "hasLocalName"));
                    obj.put("hasImageURL", getStringValue(solution, "hasImageURL"));
                    obj.put("id", getStringValue(solution, "id"));
                    list.add(obj);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<JSONObject> searchByClassName(String className) {
        List<JSONObject> list = new ArrayList<>();
        try {
            File file = new File(ONTOLOGY_FILE_NAME);
            if (!file.exists()) {
                System.out.println("File ontologi tidak ditemukan: " + ONTOLOGY_FILE_NAME);
                return list;
            }
    
            FileReader reader = new FileReader(file);
            OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM);
            model.read(reader, null);
    
            String sparql = "PREFIX http: <http://www.w3.org/2011/http#>\n" +
                    "PREFIX dd: <http://example.org/dummydata/>\n" +
                    "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                    "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n" +
                    "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                    "PREFIX d: <http://www.semanticweb.org/hp/ontologies/2024/1/DataTanamanObat#>\n" +
                    "SELECT ?medicinalPlant ?treatDisease ?id ?hasBenefits ?hasEnglishName ?hasLocalName\n" +
                    "WHERE { ?medicinalPlant d:hasId ?id;\n" +
                    "                 d:treatDisease ?treatDisease;\n" +
                    "                 d:hasBenefits ?hasBenefits;\n" +
                    "                 d:hasEnglishName ?hasEnglishName;\n" +
                    "                 d:hasLocalName ?hasLocalName;\n" +
                    "                 d:hasClass d:" + className + ".\n" +
                    "}\n" +
                    "ORDER BY ASC(?treatDisease)";
    
            System.out.println("Executing SPARQL query: \n" + sparql);
    
            Query query = QueryFactory.create(sparql);
            try (QueryExecution qe = QueryExecutionFactory.create(query, model)) {
                ResultSet resultSet = qe.execSelect();
                if (!resultSet.hasNext()) {
                    System.out.println("No results found for query: \n" + sparql);
                }
                while (resultSet.hasNext()) {
                    QuerySolution solution = resultSet.nextSolution();
                    JSONObject obj = new JSONObject();
                    obj.put("medicinalPlant", getStringValue(solution, "medicinalPlant"));
                    obj.put("treatDisease", getStringValue(solution, "treatDisease"));
                    obj.put("id", getStringValue(solution, "id"));
                    obj.put("hasBenefits", getStringValue(solution, "hasBenefits"));
                    obj.put("hasEnglishName", getStringValue(solution, "hasEnglishName"));
                    obj.put("hasLocalName", getStringValue(solution, "hasLocalName"));
                    list.add(obj);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public List<JSONObject> searchByDivisionName(String divisionName) {
        List<JSONObject> list = new ArrayList<>();
        try {
            File file = new File(ONTOLOGY_FILE_NAME);
            if (!file.exists()) {
                System.out.println("File ontologi tidak ditemukan: " + ONTOLOGY_FILE_NAME);
                return list;
            }
    
            FileReader reader = new FileReader(file);
            OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM);
            model.read(reader, null);
    
            String sparql = "PREFIX http: <http://www.w3.org/2011/http#>\n" +
                    "PREFIX dd: <http://example.org/dummydata/>\n" +
                    "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                    "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n" +
                    "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                    "PREFIX d: <http://www.semanticweb.org/hp/ontologies/2024/1/DataTanamanObat#>\n" +
                    "SELECT ?medicinalPlant ?treatDisease ?id ?hasBenefits ?hasEnglishName ?hasLocalName\n" +
                    "WHERE { ?medicinalPlant d:hasId ?id;\n" +
                    "                 d:treatDisease ?treatDisease;\n" +
                    "                 d:hasBenefits ?hasBenefits;\n" +
                    "                 d:hasEnglishName ?hasEnglishName;\n" +
                    "                 d:hasLocalName ?hasLocalName;\n" +
                    "                 d:hasDivisions d:" + divisionName + ".\n" +
                    "}\n" +
                    "ORDER BY ASC(?treatDisease)";
    
            System.out.println("Executing SPARQL query: \n" + sparql);
    
            Query query = QueryFactory.create(sparql);
            try (QueryExecution qe = QueryExecutionFactory.create(query, model)) {
                ResultSet resultSet = qe.execSelect();
                if (!resultSet.hasNext()) {
                    System.out.println("No results found for query: \n" + sparql);
                }
                while (resultSet.hasNext()) {
                    QuerySolution solution = resultSet.nextSolution();
                    JSONObject obj = new JSONObject();
                    obj.put("medicinalPlant", getStringValue(solution, "medicinalPlant"));
                    obj.put("treatDisease", getStringValue(solution, "treatDisease"));
                    obj.put("id", getStringValue(solution, "id"));
                    obj.put("hasBenefits", getStringValue(solution, "hasBenefits"));
                    obj.put("hasEnglishName", getStringValue(solution, "hasEnglishName"));
                    obj.put("hasLocalName", getStringValue(solution, "hasLocalName"));
                    list.add(obj);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<JSONObject> searchByOrderName(String orderName) {
        List<JSONObject> list = new ArrayList<>();
        try {
            File file = new File(ONTOLOGY_FILE_NAME);
            if (!file.exists()) {
                System.out.println("File ontologi tidak ditemukan: " + ONTOLOGY_FILE_NAME);
                return list;
            }
    
            FileReader reader = new FileReader(file);
            OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM);
            model.read(reader, null);
    
            String sparql = "PREFIX http: <http://www.w3.org/2011/http#>\n" +
                    "PREFIX dd: <http://example.org/dummydata/>\n" +
                    "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                    "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n" +
                    "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                    "PREFIX d: <http://www.semanticweb.org/hp/ontologies/2024/1/DataTanamanObat#>\n" +
                    "SELECT ?medicinalPlant ?treatDisease ?id ?hasBenefits ?hasEnglishName ?hasLocalName\n" +
                    "WHERE { ?medicinalPlant d:hasId ?id;\n" +
                    "                 d:treatDisease ?treatDisease;\n" +
                    "                 d:hasBenefits ?hasBenefits;\n" +
                    "                 d:hasEnglishName ?hasEnglishName;\n" +
                    "                 d:hasLocalName ?hasLocalName;\n" +
                    "                 d:hasOrder d:" + orderName + ".\n" +
                    "}\n" +
                    "ORDER BY ASC(?treatDisease)";
    
            System.out.println("Executing SPARQL query: \n" + sparql);
    
            Query query = QueryFactory.create(sparql);
            try (QueryExecution qe = QueryExecutionFactory.create(query, model)) {
                ResultSet resultSet = qe.execSelect();
                if (!resultSet.hasNext()) {
                    System.out.println("No results found for query: \n" + sparql);
                }
                while (resultSet.hasNext()) {
                    QuerySolution solution = resultSet.nextSolution();
                    JSONObject obj = new JSONObject();
                    obj.put("medicinalPlant", getStringValue(solution, "medicinalPlant"));
                    obj.put("treatDisease", getStringValue(solution, "treatDisease"));
                    obj.put("id", getStringValue(solution, "id"));
                    obj.put("hasBenefits", getStringValue(solution, "hasBenefits"));
                    obj.put("hasEnglishName", getStringValue(solution, "hasEnglishName"));
                    obj.put("hasLocalName", getStringValue(solution, "hasLocalName"));
                    list.add(obj);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<JSONObject> searchByFamilyName(String familyName) {
        List<JSONObject> list = new ArrayList<>();
        try {
            File file = new File(ONTOLOGY_FILE_NAME);
            if (!file.exists()) {
                System.out.println("File ontologi tidak ditemukan: " + ONTOLOGY_FILE_NAME);
                return list;
            }
    
            FileReader reader = new FileReader(file);
            OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM);
            model.read(reader, null);
    
            String sparql = "PREFIX http: <http://www.w3.org/2011/http#>\n" +
                    "PREFIX dd: <http://example.org/dummydata/>\n" +
                    "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                    "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n" +
                    "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                    "PREFIX d: <http://www.semanticweb.org/hp/ontologies/2024/1/DataTanamanObat#>\n" +
                    "SELECT ?medicinalPlant ?treatDisease ?id ?hasBenefits ?hasEnglishName ?hasLocalName\n" +
                    "WHERE { ?medicinalPlant d:hasId ?id;\n" +
                    "                 d:treatDisease ?treatDisease;\n" +
                    "                 d:hasBenefits ?hasBenefits;\n" +
                    "                 d:hasEnglishName ?hasEnglishName;\n" +
                    "                 d:hasLocalName ?hasLocalName;\n" +
                    "                 d:hasFamily d:" + familyName + ".\n" +
                    "}\n" +
                    "ORDER BY ASC(?treatDisease)";
    
            System.out.println("Executing SPARQL query: \n" + sparql);
    
            Query query = QueryFactory.create(sparql);
            try (QueryExecution qe = QueryExecutionFactory.create(query, model)) {
                ResultSet resultSet = qe.execSelect();
                if (!resultSet.hasNext()) {
                    System.out.println("No results found for query: \n" + sparql);
                }
                while (resultSet.hasNext()) {
                    QuerySolution solution = resultSet.nextSolution();
                    JSONObject obj = new JSONObject();
                    obj.put("medicinalPlant", getStringValue(solution, "medicinalPlant"));
                    obj.put("treatDisease", getStringValue(solution, "treatDisease"));
                    obj.put("id", getStringValue(solution, "id"));
                    obj.put("hasBenefits", getStringValue(solution, "hasBenefits"));
                    obj.put("hasEnglishName", getStringValue(solution, "hasEnglishName"));
                    obj.put("hasLocalName", getStringValue(solution, "hasLocalName"));
                    list.add(obj);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public List<JSONObject> searchByGenusName(String genusName) {
        List<JSONObject> list = new ArrayList<>();
        try {
            File file = new File(ONTOLOGY_FILE_NAME);
            if (!file.exists()) {
                System.out.println("File ontologi tidak ditemukan: " + ONTOLOGY_FILE_NAME);
                return list;
            }
    
            FileReader reader = new FileReader(file);
            OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM);
            model.read(reader, null);
    
            String sparql = "PREFIX http: <http://www.w3.org/2011/http#>\n" +
                    "PREFIX dd: <http://example.org/dummydata/>\n" +
                    "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                    "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n" +
                    "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                    "PREFIX d: <http://www.semanticweb.org/hp/ontologies/2024/1/DataTanamanObat#>\n" +
                    "SELECT ?medicinalPlant ?treatDisease ?id ?hasBenefits ?hasEnglishName ?hasLocalName\n" +
                    "WHERE { ?medicinalPlant d:hasId ?id;\n" +
                    "                 d:treatDisease ?treatDisease;\n" +
                    "                 d:hasBenefits ?hasBenefits;\n" +
                    "                 d:hasEnglishName ?hasEnglishName;\n" +
                    "                 d:hasLocalName ?hasLocalName;\n" +
                    "                 d:hasGenus d:" + genusName + ".\n" +
                    "}\n" +
                    "ORDER BY ASC(?treatDisease)";
    
            System.out.println("Executing SPARQL query: \n" + sparql);
    
            Query query = QueryFactory.create(sparql);
            try (QueryExecution qe = QueryExecutionFactory.create(query, model)) {
                ResultSet resultSet = qe.execSelect();
                if (!resultSet.hasNext()) {
                    System.out.println("No results found for query: \n" + sparql);
                }
                while (resultSet.hasNext()) {
                    QuerySolution solution = resultSet.nextSolution();
                    JSONObject obj = new JSONObject();
                    obj.put("medicinalPlant", getStringValue(solution, "medicinalPlant"));
                    obj.put("treatDisease", getStringValue(solution, "treatDisease"));
                    obj.put("id", getStringValue(solution, "id"));
                    obj.put("hasBenefits", getStringValue(solution, "hasBenefits"));
                    obj.put("hasEnglishName", getStringValue(solution, "hasEnglishName"));
                    obj.put("hasLocalName", getStringValue(solution, "hasLocalName"));
                    list.add(obj);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<JSONObject> searchBySpeciesName(String speciesName) {
        List<JSONObject> list = new ArrayList<>();
        try {
            File file = new File(ONTOLOGY_FILE_NAME);
            if (!file.exists()) {
                System.out.println("File ontologi tidak ditemukan: " + ONTOLOGY_FILE_NAME);
                return list;
            }
    
            FileReader reader = new FileReader(file);
            OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM);
            model.read(reader, null);
    
            String sparql = "PREFIX http: <http://www.w3.org/2011/http#>\n" +
                    "PREFIX dd: <http://example.org/dummydata/>\n" +
                    "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                    "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n" +
                    "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                    "PREFIX d: <http://www.semanticweb.org/hp/ontologies/2024/1/DataTanamanObat#>\n" +
                    "SELECT ?medicinalPlant ?treatDisease ?id ?hasBenefits ?hasEnglishName ?hasLocalName\n" +
                    "WHERE { ?medicinalPlant d:hasId ?id;\n" +
                    "                 d:treatDisease ?treatDisease;\n" +
                    "                 d:hasBenefits ?hasBenefits;\n" +
                    "                 d:hasEnglishName ?hasEnglishName;\n" +
                    "                 d:hasLocalName ?hasLocalName;\n" +
                    "                 d:hasSpecies ?hasSpecies.\n" +
                    "         FILTER (?hasSpecies = '" + speciesName + "')\n" +
                    "}\n" +
                    "ORDER BY ASC(?treatDisease)";
    
            System.out.println("Executing SPARQL query: \n" + sparql);
    
            Query query = QueryFactory.create(sparql);
            try (QueryExecution qe = QueryExecutionFactory.create(query, model)) {
                ResultSet resultSet = qe.execSelect();
                if (!resultSet.hasNext()) {
                    System.out.println("No results found for query: \n" + sparql);
                }
                while (resultSet.hasNext()) {
                    QuerySolution solution = resultSet.nextSolution();
                    JSONObject obj = new JSONObject();
                    obj.put("medicinalPlant", getStringValue(solution, "medicinalPlant"));
                    obj.put("treatDisease", getStringValue(solution, "treatDisease"));
                    obj.put("id", getStringValue(solution, "id"));
                    obj.put("hasBenefits", getStringValue(solution, "hasBenefits"));
                    obj.put("hasEnglishName", getStringValue(solution, "hasEnglishName"));
                    obj.put("hasLocalName", getStringValue(solution, "hasLocalName"));
                    list.add(obj);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<JSONObject> searchByDiseaseName(String diseaseName) {
        List<JSONObject> list = new ArrayList<>();
        try {
            File file = new File(ONTOLOGY_FILE_NAME);
            if (!file.exists()) {
                System.out.println("File ontologi tidak ditemukan: " + ONTOLOGY_FILE_NAME);
                return list;
            }
    
            FileReader reader = new FileReader(file);
            OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM);
            model.read(reader, null);
    
            // Ubah kata kunci penyakit menjadi ekspresi regex untuk mencocokkan frasa penyakit utuh
            String regex = "regex(str(?disease), '.*" + diseaseName.replaceAll("\\s+", ".*") + ".*', 'i')";
    
            String sparql = "PREFIX http: <http://www.w3.org/2011/http#>\n" +
                    "PREFIX dd: <http://example.org/dummydata/>\n" +
                    "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                    "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n" +
                    "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                    "PREFIX d: <http://www.semanticweb.org/hp/ontologies/2024/1/DataTanamanObat#>\n" +
                    "SELECT ?medicinalPlant ?id ?hasBenefits ?hasEnglishName ?hasLocalName\n" +
                    "WHERE { ?medicinalPlant d:hasId ?id;\n" +
                    "                 d:hasBenefits ?hasBenefits;\n" +
                    "                 d:hasEnglishName ?hasEnglishName;\n" +
                    "                 d:hasLocalName ?hasLocalName;\n" +
                    "                 d:treatDisease ?disease.\n" +
                    "    FILTER(" + regex + ")\n" +
                    "}\n" +
                    "ORDER BY ASC(?hasBenefits)";
    
            System.out.println("Executing SPARQL query: \n" + sparql);
    
            Query query = QueryFactory.create(sparql);
            try (QueryExecution qe = QueryExecutionFactory.create(query, model)) {
                ResultSet resultSet = qe.execSelect();
                if (!resultSet.hasNext()) {
                    System.out.println("No results found for query: \n" + sparql);
                }
                while (resultSet.hasNext()) {
                    QuerySolution solution = resultSet.nextSolution();
                    JSONObject obj = new JSONObject();
                    obj.put("medicinalPlant", getStringValue(solution, "medicinalPlant"));
                    obj.put("id", getStringValue(solution, "id"));
                    obj.put("hasBenefits", getStringValue(solution, "hasBenefits"));
                    obj.put("hasEnglishName", getStringValue(solution, "hasEnglishName"));
                    obj.put("hasLocalName", getStringValue(solution, "hasLocalName"));
                    list.add(obj);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public List<JSONObject> searchByPartName(String partName) {
        List<JSONObject> list = new ArrayList<>();
        try {
            File file = new File(ONTOLOGY_FILE_NAME);
            if (!file.exists()) {
                System.out.println("File ontologi tidak ditemukan: " + ONTOLOGY_FILE_NAME);
                return list;
            }
    
            FileReader reader = new FileReader(file);
            OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM);
            model.read(reader, null);
    
            // Ubah nama bagian menjadi ekspresi regex
            String regexPartName = ".*" + partName.replaceAll("\\s+", ".*") + ".*";
    
            String sparql = "PREFIX http: <http://www.w3.org/2011/http#>\n" +
                    "PREFIX dd: <http://example.org/dummydata/>\n" +
                    "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                    "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n" +
                    "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                    "PREFIX d: <http://www.semanticweb.org/hp/ontologies/2024/1/DataTanamanObat#>\n" +
                    "SELECT ?medicinalPlant ?id ?hasBenefits ?hasEnglishName ?hasLocalName\n" +
                    "WHERE { ?medicinalPlant d:hasId ?id;\n" +
                    "                 d:hasBenefits ?hasBenefits;\n" +
                    "                 d:hasEnglishName ?hasEnglishName;\n" +
                    "                 d:hasLocalName ?hasLocalName;\n" +
                    "                 d:utilizingParts ?part.\n" +
                    "    FILTER(regex(str(?part), '" + regexPartName + "', 'i'))\n" +
                    "}\n" +
                    "ORDER BY ASC(?hasBenefits)";
    
            System.out.println("Executing SPARQL query: \n" + sparql);
    
            Query query = QueryFactory.create(sparql);
            try (QueryExecution qe = QueryExecutionFactory.create(query, model)) {
                ResultSet resultSet = qe.execSelect();
                if (!resultSet.hasNext()) {
                    System.out.println("No results found for query: \n" + sparql);
                }
                while (resultSet.hasNext()) {
                    QuerySolution solution = resultSet.nextSolution();
                    JSONObject obj = new JSONObject();
                    obj.put("medicinalPlant", getStringValue(solution, "medicinalPlant"));
                    obj.put("id", getStringValue(solution, "id"));
                    obj.put("hasBenefits", getStringValue(solution, "hasBenefits"));
                    obj.put("hasEnglishName", getStringValue(solution, "hasEnglishName"));
                    obj.put("hasLocalName", getStringValue(solution, "hasLocalName"));
                    list.add(obj);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    

    public List<JSONObject> searchByMethodName(String methodName) {
        List<JSONObject> list = new ArrayList<>();
        try {
            File file = new File(ONTOLOGY_FILE_NAME);
            if (!file.exists()) {
                System.out.println("File ontologi tidak ditemukan: " + ONTOLOGY_FILE_NAME);
                return list;
            }
    
            FileReader reader = new FileReader(file);
            OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM);
            model.read(reader, null);
    
            // Ubah nama metode menjadi ekspresi regex
            String regexMethodName = ".*" + methodName.replaceAll("\\s+", ".*") + ".*";
    
            String sparql = "PREFIX http: <http://www.w3.org/2011/http#>\n" +
                    "PREFIX dd: <http://example.org/dummydata/>\n" +
                    "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                    "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n" +
                    "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                    "PREFIX d: <http://www.semanticweb.org/hp/ontologies/2024/1/DataTanamanObat#>\n" +
                    "SELECT ?medicinalPlant ?id ?hasBenefits ?hasEnglishName ?hasLocalName\n" +
                    "WHERE { ?medicinalPlant d:hasId ?id;\n" +
                    "                 d:hasBenefits ?hasBenefits;\n" +
                    "                 d:hasEnglishName ?hasEnglishName;\n" +
                    "                 d:hasLocalName ?hasLocalName;\n" +
                    "                 d:managedBy ?method.\n" +
                    "    FILTER(regex(str(?method), '" + regexMethodName + "', 'i'))\n" +
                    "}\n" +
                    "ORDER BY ASC(?hasBenefits)";
    
            System.out.println("Executing SPARQL query: \n" + sparql);
    
            Query query = QueryFactory.create(sparql);
            try (QueryExecution qe = QueryExecutionFactory.create(query, model)) {
                ResultSet resultSet = qe.execSelect();
                if (!resultSet.hasNext()) {
                    System.out.println("No results found for query: \n" + sparql);
                }
                while (resultSet.hasNext()) {
                    QuerySolution solution = resultSet.nextSolution();
                    JSONObject obj = new JSONObject();
                    obj.put("medicinalPlant", getStringValue(solution, "medicinalPlant"));
                    obj.put("id", getStringValue(solution, "id"));
                    obj.put("hasBenefits", getStringValue(solution, "hasBenefits"));
                    obj.put("hasEnglishName", getStringValue(solution, "hasEnglishName"));
                    obj.put("hasLocalName", getStringValue(solution, "hasLocalName"));
                    list.add(obj);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public List<JSONObject> searchByCompoundsName(String compoundsName) {
        List<JSONObject> list = new ArrayList<>();
        try {
            File file = new File(ONTOLOGY_FILE_NAME);
            if (!file.exists()) {
                System.out.println("File ontologi tidak ditemukan: " + ONTOLOGY_FILE_NAME);
                return list;
            }
    
            FileReader reader = new FileReader(file);
            OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM);
            model.read(reader, null);
    
            // Ubah nama senyawa menjadi ekspresi regex
            String regexCompoundsName = ".*" + compoundsName.replaceAll("\\s+", ".*") + ".*";
    
            String sparql = "PREFIX http: <http://www.w3.org/2011/http#>\n" +
                    "PREFIX dd: <http://example.org/dummydata/>\n" +
                    "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                    "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n" +
                    "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                    "PREFIX d: <http://www.semanticweb.org/hp/ontologies/2024/1/DataTanamanObat#>\n" +
                    "SELECT ?medicinalPlant ?id ?hasBenefits ?hasEnglishName ?hasLocalName\n" +
                    "WHERE { ?medicinalPlant d:hasId ?id;\n" +
                    "                 d:hasBenefits ?hasBenefits;\n" +
                    "                 d:hasEnglishName ?hasEnglishName;\n" +
                    "                 d:hasLocalName ?hasLocalName;\n" +
                    "                 d:containsActiveCompounds ?compound.\n" +
                    "    FILTER(regex(str(?compound), '" + regexCompoundsName + "', 'i'))\n" +
                    "}\n" +
                    "ORDER BY ASC(?hasBenefits)";
    
            System.out.println("Executing SPARQL query: \n" + sparql);
    
            Query query = QueryFactory.create(sparql);
            try (QueryExecution qe = QueryExecutionFactory.create(query, model)) {
                ResultSet resultSet = qe.execSelect();
                if (!resultSet.hasNext()) {
                    System.out.println("No results found for query: \n" + sparql);
                }
                while (resultSet.hasNext()) {
                    QuerySolution solution = resultSet.nextSolution();
                    JSONObject obj = new JSONObject();
                    obj.put("medicinalPlant", getStringValue(solution, "medicinalPlant"));
                    obj.put("id", getStringValue(solution, "id"));
                    obj.put("hasBenefits", getStringValue(solution, "hasBenefits"));
                    obj.put("hasEnglishName", getStringValue(solution, "hasEnglishName"));
                    obj.put("hasLocalName", getStringValue(solution, "hasLocalName"));
                    list.add(obj);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public List<JSONObject> searchByUseName(String useName) {
        List<JSONObject> list = new ArrayList<>();
        try {
            File file = new File(ONTOLOGY_FILE_NAME);
            if (!file.exists()) {
                System.out.println("File ontologi tidak ditemukan: " + ONTOLOGY_FILE_NAME);
                return list;
            }
    
            FileReader reader = new FileReader(file);
            OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM);
            model.read(reader, null);
    
            // Ubah nama penggunaan menjadi ekspresi regex
            String regexUseName = ".*" + useName.replaceAll("\\s+", ".*") + ".*";
    
            String sparql = "PREFIX http: <http://www.w3.org/2011/http#>\n" +
                    "PREFIX dd: <http://example.org/dummydata/>\n" +
                    "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                    "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n" +
                    "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                    "PREFIX d: <http://www.semanticweb.org/hp/ontologies/2024/1/DataTanamanObat#>\n" +
                    "SELECT ?medicinalPlant ?id ?hasBenefits ?hasEnglishName ?hasLocalName\n" +
                    "WHERE { ?medicinalPlant d:hasId ?id;\n" +
                    "                 d:hasBenefits ?hasBenefits;\n" +
                    "                 d:hasEnglishName ?hasEnglishName;\n" +
                    "                 d:hasLocalName ?hasLocalName;\n" +
                    "                 d:usedByMeansOf ?use.\n" +
                    "    FILTER(regex(str(?use), '" + regexUseName + "', 'i'))\n" +
                    "}\n" +
                    "ORDER BY ASC(?hasBenefits)";
    
            System.out.println("Executing SPARQL query: \n" + sparql);
    
            Query query = QueryFactory.create(sparql);
            try (QueryExecution qe = QueryExecutionFactory.create(query, model)) {
                ResultSet resultSet = qe.execSelect();
                if (!resultSet.hasNext()) {
                    System.out.println("No results found for query: \n" + sparql);
                }
                while (resultSet.hasNext()) {
                    QuerySolution solution = resultSet.nextSolution();
                    JSONObject obj = new JSONObject();
                    obj.put("medicinalPlant", getStringValue(solution, "medicinalPlant"));
                    obj.put("id", getStringValue(solution, "id"));
                    obj.put("hasBenefits", getStringValue(solution, "hasBenefits"));
                    obj.put("hasEnglishName", getStringValue(solution, "hasEnglishName"));
                    obj.put("hasLocalName", getStringValue(solution, "hasLocalName"));
                    list.add(obj);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    

    private String getStringValue(QuerySolution solution, String variable) {
        RDFNode node = solution.get(variable);
        return node != null ? node.toString() : "Unknown";
    }

    private String[] getStringArray(QuerySolution solution, String variable) {
        if (solution.contains(variable)) {
            return solution.get(variable).toString().split(";");
        }
        return new String[]{"Unknown"};
    }

    private String getHighlightedText(String text, String keyword) {
        // Menyorot kata kunci dalam teks
        return text.replaceAll("(?i)(" + keyword + ")", "<span style='background-color: yellow'>$1</span>");
    }
    
    
}
