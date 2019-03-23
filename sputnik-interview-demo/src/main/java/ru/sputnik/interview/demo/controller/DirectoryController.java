package ru.sputnik.interview.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.sputnik.interview.demo.model.Color;
import ru.sputnik.interview.demo.model.KindOfClothes;
import ru.sputnik.interview.demo.model.Place;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/directories")
public class DirectoryController {

    @ResponseBody
    @RequestMapping("/colors")
    public List<Color> getColors() {
        return Arrays.asList(Color.values());
    }

    @ResponseBody
    @RequestMapping("/kindsOfClothes")
    public List<KindOfClothes> getKindsOfClothes() {
        return Arrays.asList(KindOfClothes.values());
    }

    @ResponseBody
    @RequestMapping("/places")
    public List<Place> getPlaces() {
        return Arrays.asList(Place.values());
    }
}
