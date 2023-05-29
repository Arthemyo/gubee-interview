package br.com.gubee.interview.core.features.powerstats;

import br.com.gubee.interview.model.PowerStats;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/powerstats", produces = APPLICATION_JSON_VALUE)
public class PowerStatsController {

    private final PowerStatsService powerStatsService;

    @GetMapping("/{uuid}")
    public ResponseEntity<PowerStats> getPowerStatsById(@PathVariable String uuid){

        Optional<PowerStats> powerStats = powerStatsService.getPoweStats(uuid);
        if(powerStats.isPresent()){
            return ResponseEntity.ok(powerStats.get());
        }
        return ResponseEntity.notFound().build();
    }
}
