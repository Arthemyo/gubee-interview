package br.com.gubee.interview.model.request;

import br.com.gubee.interview.model.Hero;
import br.com.gubee.interview.model.PowerStats;

import java.util.UUID;

public class HeroDTO {

    private UUID id;
    private String name;
    private String race;
    private UUID power_stats_id;
    private int strength;
    private int agility;
    private int dexterity;
    private int intelligence;

    public HeroDTO(Hero hero){
        this.name = hero.getName();
        this.race = hero.getRace().name();
    }
    public HeroDTO(){}

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public UUID getPower_stats_id() {
        return power_stats_id;
    }

    public void setPower_stats_id(UUID power_stats_id) {
        this.power_stats_id = power_stats_id;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getAgility() {
        return agility;
    }

    public void setAgility(int agility) {
        this.agility = agility;
    }

    public int getDexterity() {
        return dexterity;
    }

    public void setDexterity(int dexterity) {
        this.dexterity = dexterity;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
    }
}
