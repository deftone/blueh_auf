package de.deftone.blueh_auf.biotopVernetzung;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AdminController {

    private final BluehService service;

    @GetMapping("/admin/getAllBluehEvents")
    public List<BluehEvent> getAllBluehEvents() {
        return service.getAllBlueEvents();
    }

    @PostMapping(path = "/admin/addBluehEventList", consumes = "application/json")
    public List<BluehEvent> addLocationList(@RequestBody List<BluehEvent> events) {
        return service.addEvents(events);
    }

}
