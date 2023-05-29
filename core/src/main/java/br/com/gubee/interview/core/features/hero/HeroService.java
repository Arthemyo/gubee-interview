package br.com.gubee.interview.core.features.hero;

import br.com.gubee.interview.core.features.powerstats.PowerStatsService;
import br.com.gubee.interview.model.Hero;
import br.com.gubee.interview.model.PowerStats;
import br.com.gubee.interview.model.enums.Race;
import br.com.gubee.interview.model.request.CreateHeroRequest;
import br.com.gubee.interview.model.request.HeroComparingDTO;
import br.com.gubee.interview.model.request.HeroDTO;
import br.com.gubee.interview.model.request.HeroForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HeroService {

    private final HeroRepository heroRepository;

    private final PowerStatsService powerStatsService;

    public List<Hero> getAll(){
        return  heroRepository.getHero();
    }

    public Optional<List<HeroDTO>> getHeroesByFilterRace(Race race) throws SQLException {
        return heroRepository.getHeroesFilterRace(race);
    }

    public Optional<List<HeroDTO>> getHeroesByFilterName(String name) throws SQLException {
        return heroRepository.getHeroesFilterName(name);
    }

    public Optional<HeroDTO> getHero(UUID uuid){
        return  heroRepository.getHero(uuid);
    }

    public List<HeroDTO> getHeroes(){
        return  heroRepository.getHeros();
    }

    @Transactional
    public UUID create(CreateHeroRequest createHeroRequest) {
        UUID uuid = powerStatsService.create(new PowerStats(createHeroRequest));
        return heroRepository.create(new Hero(createHeroRequest, uuid));
    }
    @Transactional
    public int update(HeroForm heroForm){
        return heroRepository.update(heroForm);
    }
    @Transactional
    public Optional<UUID> delete(String id){
        return heroRepository.delete(id);
    }

    public Optional<List<HeroComparingDTO>> comparingHeros(String uuidHero1, String uuidHero2){
        return heroRepository.compraingHeros(uuidHero1, uuidHero2);
    }
}
