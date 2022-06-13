package ru.greatstep.spring.boot_security.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class JSController {

    @GetMapping("js/app.js")
    public String jSTwo(){
        return "js/app.js";
    }


}




