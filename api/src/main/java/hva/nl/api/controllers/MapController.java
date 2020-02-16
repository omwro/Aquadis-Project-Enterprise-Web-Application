package hva.nl.api.controllers;

import hva.nl.api.models.Map;
import hva.nl.api.repositories.MapRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(path = "/api")
public class MapController {

    @Autowired
    private MapRepository mapRepository;

    @GetMapping(path = "/map")
    @ResponseBody
    public List<Map> getAll(){
        return mapRepository.getMaps();
    }

    @GetMapping(path = "/map/{key}")
    @ResponseBody
    public Map getAll(@PathVariable String key){
        return mapRepository.getMapByKey(key);
    }

    @GetMapping(path = "/map/update/{key}/{value}")
    @ResponseBody
    public Map updateMap(@PathVariable String key, @PathVariable int value){
        Map map = mapRepository.getMapByKey(key);
        map.setValue(value);
        return mapRepository.setMap(map);
    }

    @GetMapping(path = "/map/create/{key}/{value}")
    @ResponseBody
    public Map setMap(@PathVariable String key, @PathVariable int value){
        return mapRepository.setMap(key,value);
    }
}
