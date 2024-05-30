package com.belajarspring.BelajarSpringBoot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(value = "/medicinal/id/Genus=", method = RequestMethod.GET)
    public String getGenus(@RequestParam String genus) {
        // Handle request for genus page here
        System.out.println("Genus = " + genus);
        return "genus"; // Change this to the actual genus page name
    }

    @RequestMapping(value = "/medicinal/id/part=", method = RequestMethod.GET)
    public String getPart(@RequestParam String part) {
        // Handle request for part page here
        System.out.println("part= " + part);
        return "part_detail"; // Change this to the actual part page name
    }

}
