package br.com.gubee.interview.core.features.hero;

import br.com.gubee.interview.core.features.powerstats.PowerStatsService;
import br.com.gubee.interview.model.Hero;
import br.com.gubee.interview.model.PowerStats;
import br.com.gubee.interview.model.enums.Race;
import br.com.gubee.interview.model.request.HeroComparingDTO;
import br.com.gubee.interview.model.request.HeroDTO;
import br.com.gubee.interview.model.request.HeroForm;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.*;

@Repository
@RequiredArgsConstructor
public class HeroRepository {

    private final PowerStatsService powerStatsService;

    private static final String UPDATE_HERO_QUERY_ = "UPDATE Hero " +
            "SET name = :name, race = :race " +
            "WHERE Hero.id = :id; " +
            "UPDATE power_stats " +
            "SET strength = :strength, agility = :agility, " +
            "dexterity = :dexterity, intelligence = :intelligence " +
            "WHERE power_stats.id = :power_stats_id";
    private static final String DELETE_ROW = "DELETE FROM Hero USING power_stats " +
            "WHERE Hero.id = :id AND Hero.power_stats_id = power_stats.id RETURNING Hero.id";
    private static final String CREATE_HERO_QUERY = "INSERT INTO hero" +
        " (name, race, power_stats_id)" +
        " VALUES (:name, :race, :powerStatsId) RETURNING id";
    private static String GET_HERO_ = "SELECT * FROM Hero";
    private static String GET_HERO = "SELECT " +
            "* FROM Hero " +
            "INNER JOIN power_stats ON Hero.power_stats_id = power_stats.id";
    private static String GET_HERO_FOR_ID = "SELECT * FROM Hero " +
            "INNER JOIN power_stats ON Hero.power_stats_id = power_stats.id WHERE Hero.id = :id";
    private static String GET_HEROES_FOR_FILTER_RACE = "SELECT * FROM Hero INNER JOIN power_stats ON Hero.power_stats_id = power_stats.id WHERE  Hero.race = :race";

    private static String GET_HEROES_FOR_FILTER_NAME = "SELECT * FROM Hero INNER JOIN power_stats ON Hero.power_stats_id = power_stats.id WHERE  Hero.name = :name";

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<Hero> getHero(){
        return namedParameterJdbcTemplate.query(GET_HERO_, new HeroRowMapper());
    }

    public Optional<HeroDTO> getHero(UUID uuid){
        SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("id", uuid);

        try{
        return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject(GET_HERO_FOR_ID,
                namedParameters,
                new HeroDTORowMapper()));
        }catch (Exception e){
            e.printStackTrace();
            return  Optional.ofNullable(null);
        }

    }

    public Optional<List<HeroDTO>> getHeroesFilterRace(Race race) throws SQLException {
        try {
            SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("race", race.toString());
            return Optional.of(namedParameterJdbcTemplate.query(GET_HEROES_FOR_FILTER_RACE, namedParameters, new HeroDTORowMapper()));
        } catch (IllegalAccessError e){
            return  Optional.ofNullable(null);
        }

    }

    public Optional<List<HeroDTO>> getHeroesFilterName(String name) throws SQLException {
        try {
            SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("name", name);
            return Optional.of(namedParameterJdbcTemplate.query(GET_HEROES_FOR_FILTER_NAME, namedParameters, new HeroDTORowMapper()));
        } catch (IllegalAccessError e){
            return  Optional.ofNullable(null);
        }

    }

    public List<HeroDTO> getHeros(){
        return  namedParameterJdbcTemplate.query(GET_HERO, new HeroDTORowMapper());
    }

    UUID create(Hero hero) {
        final Map<String, Object> params = Map.of("name", hero.getName(),
            "race", hero.getRace().name(),
            "powerStatsId", hero.getPowerStatsId());

        return namedParameterJdbcTemplate.queryForObject(
            CREATE_HERO_QUERY,
            params,
            UUID.class);
    }

    public int update(HeroForm heroForm){
        UUID powerStatsId = null;
        Optional<HeroDTO> hero = this.getHero(UUID.fromString(heroForm.getId()));
        if(hero.isPresent()){
            powerStatsId = hero.get().getPower_stats_id();
        }

        Map<String, Object> params = Map.of("name", heroForm.getName(),
                "race", heroForm.getRace().name(),
                "strength", heroForm.getStrength(),
                "agility", heroForm.getAgility(),
                "dexterity", heroForm.getDexterity(),
                "intelligence", heroForm.getIntelligence(),
                "id", UUID.fromString(heroForm.getId()),
                "power_stats_id", powerStatsId);

        return namedParameterJdbcTemplate.update(UPDATE_HERO_QUERY_, params);
    }
    public Optional<UUID> delete(String id){
        try{
            Map<String, Object> params = Map.of("id", UUID.fromString(id));
            Optional<UUID> uuid = Optional.ofNullable(namedParameterJdbcTemplate.queryForObject(DELETE_ROW, params, UUID.class));
            return uuid;
        }catch (Exception e){
            return Optional.ofNullable(null);
        }
    }

    public Optional<List<HeroComparingDTO>> compraingHeros(String uuidHero1, String uuidHero2) {
        List<HeroComparingDTO> heroComparingDTOs = new ArrayList<>();
        try {
            heroComparingDTOs = new ArrayList<>();
            Optional<HeroDTO> hero1 = this.getHero(UUID.fromString(uuidHero1));
            Optional<HeroDTO> hero2 = this.getHero(UUID.fromString(uuidHero2));

            if(hero1.isPresent() && hero2.isPresent()){
                Optional<PowerStats> poweStatsHero1 = powerStatsService.getPoweStats(hero1.get().getPower_stats_id().toString());
                Optional<PowerStats> poweStatsHero2 = powerStatsService.getPoweStats(hero2.get().getPower_stats_id().toString());

                if(poweStatsHero1.isPresent() && poweStatsHero2.isPresent()){
                    HeroComparingDTO heroComparingDTO = new HeroComparingDTO(poweStatsHero1.get(), poweStatsHero2.get());
                    HeroComparingDTO heroComparingDTO1 = new HeroComparingDTO(poweStatsHero2.get(), poweStatsHero1.get());

                    heroComparingDTO.setId(hero1.get().getId());
                    heroComparingDTO.setName(hero1.get().getName());
                    heroComparingDTO.setRace(hero1.get().getRace());

                    heroComparingDTO1.setId(hero2.get().getId());
                    heroComparingDTO1.setName(hero2.get().getName());
                    heroComparingDTO1.setRace(hero2.get().getRace());

                    heroComparingDTOs.add(heroComparingDTO);
                    heroComparingDTOs.add(heroComparingDTO1);

                    return Optional.ofNullable(heroComparingDTOs);
                }
            }

        } catch (Exception e) {
            return Optional.ofNullable(null);
        }

        return Optional.ofNullable(null);
    }
}
