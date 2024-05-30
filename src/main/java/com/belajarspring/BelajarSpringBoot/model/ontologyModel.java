package com.belajarspring.BelajarSpringBoot.model;

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
    public List<JSONObject> searchById(String key) {
        List<JSONObject> list = new ArrayList<>();
        String fileName = "Medicinal Plant Ontology.owl";
        try {
            File file = new File(fileName);
            FileReader reader = new FileReader(file);
            OntModel model = ModelFactory
                    .createOntologyModel(OntModelSpec.OWL_DL_MEM);
            model.read(reader, null);

            String sprql = "PREFIX http: <http://www.w3.org/2011/http#>\n" +
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
                    "    FILTER(regex(str(?medicinalPlant), '" + key + "', 'i') || " +
                    "           regex(str(?treatDisease), '" + key + "', 'i') || " +
                    "           regex(str(?id), '" + key + "', 'i') || " +
                    "           regex(str(?hasBenefits), '" + key + "', 'i') || " +
                    "           regex(str(?hasEnglishName), '" + key + "', 'i') || " +
                    "           regex(str(?hasLocalName), '" + key + "', 'i'))" +
                    "}\n" +
                    "ORDER BY ASC(?treatDisease)";
//            FILTER (?medicinalPlant = 'd_a')
            Query query = QueryFactory.create(sprql);
            QueryExecution qe = QueryExecutionFactory.create(query, model);
            ResultSet resultSet = qe.execSelect();
            int x = 0;
            while (resultSet.hasNext()) {
                x++;
                JSONObject obj = new JSONObject();
                QuerySolution solution = resultSet.nextSolution();

                RDFNode medicinalPlantNode = solution.get("medicinalPlant");
                if (medicinalPlantNode != null) {
                    System.out.println(medicinalPlantNode.toString());
                    obj.put("medicinalPlant", medicinalPlantNode.toString());
                } else {
                    // Handle the case when medicinalPlantNode is null
                    // For example, you can skip adding this entry to the list or add a placeholder value
                    obj.put("medicinalPlant", "Unknown");
                }

                // Check and handle treatDisease
                RDFNode treatDiseaseNode = solution.get("treatDisease");
                if (treatDiseaseNode != null) {
                    obj.put("treatDisease", treatDiseaseNode.toString());
                } else {
                    obj.put("treatDisease", "Unknown");
                }

                // Check and handle hasBenefits
                RDFNode hasBenefitsNode = solution.get("hasBenefits");
                if (hasBenefitsNode != null) {
                    obj.put("hasBenefits", hasBenefitsNode.toString());
                } else {
                    obj.put("hasBenefits", "Unknown");
                }

                // Check and handle hasEnglishName
                RDFNode hasEnglishNameNode = solution.get("hasEnglishName");
                if (hasEnglishNameNode != null) {
                    obj.put("hasEnglishName", hasEnglishNameNode.toString());
                } else {
                    obj.put("hasEnglishName", "Unknown");
                }

                // Check and handle hasLocalName
                RDFNode hasLocalNameNode = solution.get("hasLocalName");
                if (hasLocalNameNode != null) {
                    obj.put("hasLocalName", hasLocalNameNode.toString());
                } else {
                    obj.put("hasLocalName", "Unknown");
                }

                RDFNode idNode = solution.get("id");
                if (idNode != null) {
                    obj.put("id", idNode.toString());
                } else {
                    obj.put("id", "Unknown");
                }

                list.add(obj);
            }
            System.out.println(x);
            return list;


        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    public List<JSONObject> detailById(String key) {
        List<JSONObject> list = new ArrayList<>();
        String fileName = "Medicinal Plant Ontology.owl";
        try {
            File file = new File(fileName);
            FileReader reader = new FileReader(file);
            OntModel model = ModelFactory
                    .createOntologyModel(OntModelSpec.OWL_DL_MEM);
            model.read(reader, null);

            String sprql = "PREFIX http: <http://www.w3.org/2011/http#>\n" +
                    "PREFIX dd: <http://example.org/dummydata/>\n" +
                    "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                    "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n" +
                    "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                    "PREFIX d: <http://www.semanticweb.org/hp/ontologies/2024/1/DataTanamanObat#>\n" +
                    "\n" +
                    "SELECT ?medicinalPlant ?treatDisease ?managedBy ?utilizingParts ?containsActiveCompounds ?usedByMeansOf ?hasSideEffect ?id ?hasBenefits ?hasEnglishName ?hasLocalName ?hasClass ?hasDivisions ?hasFamily ?hasGenus ?hasKingdom ?hasOrder ?hasSpecies ?hasImageURL\n" +
                    "WHERE\n" +
                    "  {\n" +
                    "?medicinalPlant d:hasId ?id;\n" +
                    "d:managedBy ?managedBy;" +
                    "d:treatDisease ?treatDisease;" +
                    "d:utilizingParts ?utilizingParts;" +
                    "d:containsActiveCompounds ?containsActiveCompounds;" +
                    "d:usedByMeansOf ?usedByMeansOf;" +
                    "d:hasBenefits ?hasBenefits;" +
                    "d:hasEnglishName ?hasEnglishName;" +
                    "d:hasLocalName ?hasLocalName;" +
                    "d:hasSideEffect ?hasSideEffect;" +
                    "d:hasClass ?hasClass;" +
                    "d:hasDivisions ?hasDivisions;" +
                    "d:hasFamily ?hasFamily;" +
                    "d:hasGenus ?hasGenus;" +
                    "d:hasKingdom ?hasKingdom;" +
                    "d:hasOrder ?hasOrder;" +
                    "d:hasSpecies ?hasSpecies;" +
                    "d:hasImageURL ?hasImageURL;" +
                    "    FILTER (?id = '" + key + "')\n" +
                    "  }\n";
//            FILTER (?medicinalPlant = 'd_a')
            Query query = QueryFactory.create(sprql);
            QueryExecution qe = QueryExecutionFactory.create(query, model);
            ResultSet resultSet = qe.execSelect();

            int x = 0;
            while (resultSet.hasNext()) {
                x++;
                JSONObject obj = new JSONObject();
                QuerySolution solution = resultSet.next();
                // Print out the values for debugging
                RDFNode medicinalPlantNode = solution.get("medicinalPlant");
                System.out.println(medicinalPlantNode != null ? medicinalPlantNode.toString() : "Unknown");

                RDFNode managedByNode = solution.get("managedBy");
                System.out.println(managedByNode != null ? managedByNode.toString() : "Unknown");

                RDFNode treatDiseaseNode = solution.get("treatDisease");
                System.out.println(treatDiseaseNode != null ? treatDiseaseNode.toString() : "Unknown");

                RDFNode utilizingPartsNode = solution.get("utilizingParts");
                System.out.println(utilizingPartsNode != null ? utilizingPartsNode.toString() : "Unknown");

                RDFNode containsActiveCompoundsNode = solution.get("containsActiveCompounds");
                System.out.println(containsActiveCompoundsNode != null ? containsActiveCompoundsNode.toString() : "Unknown");

                RDFNode usedByMeansOfNode = solution.get("usedByMeansOf");
                System.out.println(usedByMeansOfNode != null ? usedByMeansOfNode.toString() : "Unknown");

                RDFNode hasClassNode = solution.get("hasClass");
                System.out.println(hasClassNode != null ? hasClassNode.toString() : "Unknown");

                RDFNode hasDivisionsNode = solution.get("hasDivisions");
                System.out.println(hasDivisionsNode != null ? hasDivisionsNode.toString() : "Unknown");

                RDFNode hasFamilyNode = solution.get("hasFamily");
                System.out.println(hasFamilyNode != null ? hasFamilyNode.toString() : "Unknown");

                RDFNode hasGenusNode = solution.get("hasGenus");
                System.out.println(hasGenusNode != null ? hasGenusNode.toString() : "Unknown");

                RDFNode hasKingdomNode = solution.get("hasKingdom");
                System.out.println(hasKingdomNode != null ? hasKingdomNode.toString() : "Unknown");

                RDFNode hasOrderNode = solution.get("hasOrder");
                System.out.println(hasOrderNode != null ? hasOrderNode.toString() : "Unknown");

                RDFNode hasSpeciesNode = solution.get("hasSpecies");
                System.out.println(hasSpeciesNode != null ? hasSpeciesNode.toString() : "Unknown");

                RDFNode hasImageURLNode = solution.get("hasImageURL");
                System.out.println(hasImageURLNode != null ? hasImageURLNode.toString() : "Unknown");

                // Now, you can continue with putting the values into JSON object
                // Make sure to handle the null values appropriately

                obj.put("medicinalPlant", medicinalPlantNode != null ? medicinalPlantNode.toString() : "Unknown");
                obj.put("managedBy", managedByNode != null ? managedByNode.toString() : "Unknown");
                obj.put("treatDisease", treatDiseaseNode != null ? treatDiseaseNode.toString() : "Unknown");
                obj.put("utilizingParts", utilizingPartsNode != null ? utilizingPartsNode.toString() : "Unknown");
                obj.put("containsActiveCompounds", containsActiveCompoundsNode != null ? containsActiveCompoundsNode.toString() : "Unknown");
                obj.put("usedByMeansOf", usedByMeansOfNode != null ? usedByMeansOfNode.toString() : "Unknown");
                obj.put("hasClass", hasClassNode != null ? hasClassNode.toString() : "Unknown");
                obj.put("hasDivisions", hasDivisionsNode != null ? hasDivisionsNode.toString() : "Unknown");
                obj.put("hasFamily", hasFamilyNode != null ? hasFamilyNode.toString() : "Unknown");
                obj.put("hasGenus", hasGenusNode != null ? hasGenusNode.toString() : "Unknown");
                obj.put("hasKingdom", hasKingdomNode != null ? hasKingdomNode.toString() : "Unknown");
                obj.put("hasOrder", hasOrderNode != null ? hasOrderNode.toString() : "Unknown");
                obj.put("hasSpecies", hasSpeciesNode != null ? hasSpeciesNode.toString() : "Unknown");
                obj.put("hasImageURL", hasImageURLNode != null ? hasImageURLNode.toString() : "Unknown");

                if (solution.contains("hasEnglishName")) {
                    String[] hasEnglishName = solution.get("hasEnglishName").toString().split(";");
                    obj.put("hasEnglishName", hasEnglishName);
                } else {
                    obj.put("hasEnglishName", new String[]{"Unknown"});
                }

                if (solution.contains("hasLocalName")) {
                    String[] hasLocalName = solution.get("hasLocalName").toString().split(";");
                    obj.put("hasLocalName", hasLocalName);
                } else {
                    obj.put("hasLocalName", new String[]{"Unknown"});
                }

                if (solution.contains("hasBenefits")) {
                    String[] hasBenefits = solution.get("hasBenefits").toString().split(";");
                    obj.put("hasBenefits", hasBenefits);
                } else {
                    obj.put("hasBenefits", new String[]{"Unknown"});
                }

                if (solution.contains("hasSideEffect")) {
                    String[] hasSideEffect = solution.get("hasSideEffect").toString().split(";");
                    obj.put("hasSideEffect", hasSideEffect);
                } else {
                    obj.put("hasSideEffect", new String[]{"Unknown"});
                }

                if (solution.contains("hasImageURL")) {
                    String[] hasImageURL = solution.get("hasImageURL").toString().split(";");
                    obj.put("hasImageURL", hasImageURL);
                } else {
                    obj.put("hasImageURL", new String[]{"Unknown"});
                }

                list.add(obj);
            }
            System.out.println(x);
            return list;


        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getHighlightedText(String text, String keyword) {
        // Menyorot kata kunci dalam teks
        String highlightedText = text.replaceAll("(?i)(" + keyword + ")", "<span style='background-color: yellow'>$1</span>");
        return highlightedText;
    }
}