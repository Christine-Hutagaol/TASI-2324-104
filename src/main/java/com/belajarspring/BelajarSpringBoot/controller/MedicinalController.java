package com.belajarspring.BelajarSpringBoot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

@Controller
public class MedicinalController {

    @RequestMapping(value = "/medicinal", method = RequestMethod.GET)
    public String getLoginForm(){
        return "medicinal";
    }

    @RequestMapping(value = "/medicinal/id/{id}", method = RequestMethod.GET)
    public String getMedicinal(@PathVariable String id) {
        System.out.println("Key = " + id);
        return "detail_medicinal";
    }

    @RequestMapping(value = "/class.html?class=", method = RequestMethod.GET)
    public String getClass(@RequestParam String genus) {
        // Handle request for genus page here
        System.out.println("Genus = " + genus);
        return "genus"; // Change this to the actual genus page name
    }

    @GetMapping("/class.html")
    public String getClassPage(@RequestParam("class") String className, Model model) {
        model.addAttribute("className", className);
        // Tambahkan logika lain jika diperlukan, misalnya mengambil data dari database
        return "class"; // Pastikan ada template class.html di folder templates
    }
    
    @RequestMapping(value = "/division.html?division=", method = RequestMethod.GET)
    public String getDivision(@RequestParam String genus) {
        // Handle request for genus page here
        System.out.println("Genus = " + genus);
        return "genus"; // Change this to the actual genus page name
    }

    @GetMapping("/division.html")
    public String getdivisionPage(@RequestParam("division") String divisionName, Model model) {
        model.addAttribute("divisionName", divisionName);
        // Tambahkan logika lain jika diperlukan, misalnya mengambil data dari database
        return "division"; // Pastikan ada template division.html di folder templates
    }

    @RequestMapping(value = "/order.html?order=", method = RequestMethod.GET)
    public String getOrder(@RequestParam String genus) {
        // Handle request for genus page here
        System.out.println("Genus = " + genus);
        return "genus"; // Change this to the actual genus page name
    }

    @GetMapping("/order.html")
    public String getOrderPage(@RequestParam("order") String orderName, Model model) {
        model.addAttribute("orderName", orderName);
        // Tambahkan logika lain jika diperlukan, misalnya mengambil data dari database
        return "order"; // Pastikan ada template order.html di folder templates
    }

    @RequestMapping(value = "/family.html?family=", method = RequestMethod.GET)
    public String getFamily(@RequestParam String genus) {
        // Handle request for genus page here
        System.out.println("Genus = " + genus);
        return "genus"; // Change this to the actual genus page name
    }

    @GetMapping("/family.html")
    public String getFamilyPage(@RequestParam("family") String familyName, Model model) {
        model.addAttribute("familyName", familyName);
        // Tambahkan logika lain jika diperlukan, misalnya mengambil data dari database
        return "family"; // Pastikan ada template family.html di folder templates
    }

    @RequestMapping(value = "/genus.html?genus=", method = RequestMethod.GET)
    public String getGenus(@RequestParam String genus) {
        // Handle request for genus page here
        System.out.println("Genus = " + genus);
        return "genus"; // Change this to the actual genus page name
    }

    @GetMapping("/genus.html")
    public String getGenusPage(@RequestParam("genus") String genusName, Model model) {
        model.addAttribute("genusName", genusName);
        // Tambahkan logika lain jika diperlukan, misalnya mengambil data dari database
        return "genus"; // Pastikan ada template genus.html di folder templates
    }

    @RequestMapping(value = "/species.html?species=", method = RequestMethod.GET)
    public String getSpecies(@RequestParam String species) {
        // Handle request for genus page here
        System.out.println("Species = " + species);
        return "species"; // Change this to the actual genus page name
    }

    @GetMapping("/species.html")
    public String getSpeciesPage(@RequestParam("species") String speciesName, Model model) {
        model.addAttribute("speciesName", speciesName);
        // Tambahkan logika lain jika diperlukan, misalnya mengambil data dari database
        return "species"; // Pastikan ada template species.html di folder templates
    }

    @RequestMapping(value = "/part.html?part=", method = RequestMethod.GET)
    public String getPart(@RequestParam String genus) {
        // Handle request for genus page here
        System.out.println("Genus = " + genus);
        return "genus"; // Change this to the actual genus page name
    }

    @RequestMapping(value = "/disease.html?disease=", method = RequestMethod.GET)
    public String getDisease(@RequestParam String genus) {
        // Handle request for genus page here
        System.out.println("Genus = " + genus);
        return "genus"; // Change this to the actual genus page name
    }

    @GetMapping("/disease.html")
    public String getDiseasePage(@RequestParam("disease") String diseaseName, Model model) {
        model.addAttribute("diseaseName", diseaseName);
        // Tambahkan logika lain jika diperlukan, misalnya mengambil data dari database
        return "disease"; // Pastikan ada template family.html di folder templates
    }

    @GetMapping("/part.html")
    public String getPartPage(@RequestParam("part") String partName, Model model) {
        model.addAttribute("partName", partName);
        // Tambahkan logika lain jika diperlukan, misalnya mengambil data dari database
        return "part"; // Pastikan ada template family.html di folder templates
    }

    @GetMapping("/method.html")
    public String getMethodPage(@RequestParam("method") String methodName, Model model) {
        model.addAttribute("methodName", methodName);
        // Tambahkan logika lain jika diperlukan, misalnya mengambil data dari database
        return "method"; // Pastikan ada template family.html di folder templates
    }

    @GetMapping("/compounds.html")
    public String getCompoundsPage(@RequestParam("compounds") String compoundsName, Model model) {
        model.addAttribute("compoundsName", compoundsName);
        // Tambahkan logika lain jika diperlukan, misalnya mengambil data dari database
        return "compounds"; // Pastikan ada template family.html di folder templates
    }
    
    @GetMapping("/use.html")
    public String getUsePage(@RequestParam("use") String useName, Model model) {
        model.addAttribute("useName", useName);
        // Tambahkan logika lain jika diperlukan, misalnya mengambil data dari database
        return "use"; // Pastikan ada template family.html di folder templates
    }

    
}
