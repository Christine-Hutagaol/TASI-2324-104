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
    public String getMedicinal(@PathVariable String id)
    {
        System.out.println("Key = " + id);
        return "detail_medicinal";

    }
}
