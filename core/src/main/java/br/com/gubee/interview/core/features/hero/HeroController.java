package br.com.gubee.interview.core.features.hero;

import br.com.gubee.interview.model.Hero;
import br.com.gubee.interview.model.enums.Race;
import br.com.gubee.interview.model.request.CreateHeroRequest;
import br.com.gubee.interview.model.request.HeroComparingDTO;
import br.com.gubee.interview.model.request.HeroDTO;
import br.com.gubee.interview.model.request.HeroForm;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.lang.String.format;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.ResponseEntity.created;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/heroes", produces = APPLICATION_JSON_VALUE)
public class HeroController {

    private final HeroService heroService;

    @GetMapping
    public List<HeroDTO> getHeros(){
        return heroService.getHeroes();
    }

    @GetMapping("/all")
    public List<Hero> getAll(){
        return heroService.getAll();
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<HeroDTO> getHero(@PathVariable String uuid){

        try {
            UUID id = UUID.fromString(uuid);
            Optional<HeroDTO> hero = heroService.getHero(id);
            if(!hero.isPresent()) System.out.println("NÃO EXISTE HERO");
            return hero.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        }catch (IllegalArgumentException  e){
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/filter_race")
    public ResponseEntity<List<HeroDTO>> getHeroesFilterRace(@RequestParam(required = true, name = "race") String race){
        try{
            Optional<List<HeroDTO>> heroesOptional = heroService.getHeroesByFilterRace(Race.valueOf(race));
            return heroesOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.ok().build());
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }

    }

    @GetMapping("/filter_name")
    public ResponseEntity<List<HeroDTO>> getHeroesFilterName(@RequestParam(required = true, name = "name") String name){
        try{
            Optional<List<HeroDTO>> heroesOptional = heroService.getHeroesByFilterName(name);
            return heroesOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.ok().build());
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }

    }


    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> create(@Validated
                                       @RequestBody CreateHeroRequest createHeroRequest) {
        final UUID id = heroService.create(createHeroRequest);

        return created(URI.create(format("/api/v1/heroes/%s", id))).build();
    }

    @PatchMapping("/update")
    public ResponseEntity<Hero> update(@Validated
                                              @RequestBody HeroForm heroForm) {
        try {
            int update = heroService.update(heroForm);
            if (update != 0) {
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.notFound().build();
        }catch (NullPointerException e){
            return ResponseEntity.notFound().build();
        }catch (Exception e){
            e.printStackTrace();
        }

        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/delete/{uuid}")
    public ResponseEntity<UUID> deleteHero(@PathVariable String uuid){
        Optional<UUID> delete = heroService.delete(uuid);
        if(delete.isPresent()){
            return ResponseEntity.ok(delete.get());
        }
        return  ResponseEntity.notFound().build();
    }

    @GetMapping("/comparing/heroMain/{uuid1}/comparedHero/{uuid2}")
    public ResponseEntity<List<HeroComparingDTO>> comparingHeros(@PathVariable String uuid1, @PathVariable String uuid2){
        try{
            Optional<List<HeroComparingDTO>> heroComparingDTOS = heroService.comparingHeros(uuid1, uuid2);
            if (heroComparingDTOS.isPresent()){
                return ResponseEntity.ok(heroComparingDTOS.get());
            }
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.notFound().build();
    }

}