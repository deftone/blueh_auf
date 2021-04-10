package de.deftone.blueh_auf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
//https://wiki.openstreetmap.org/wiki/OpenLayers_Simple_Example
    //https://josm.openstreetmap.de/wiki/DevelopersGuide/CompilingUsingIntelliJ
    @GetMapping("/")
    public String home(){
        return "home";
    }

    @GetMapping("/map")
    public String map(){
        return "map";
    }
}
