package br.com.gubee.interview.model.request;

import br.com.gubee.interview.model.Hero;
import br.com.gubee.interview.model.PowerStats;

import java.util.UUID;

public class HeroComparingDTO {
    private UUID id;
    private String name;
    private String race;
    private String strength;
    private String agility;
    private String dexterity;
    private String intelligence;

    public HeroComparingDTO(PowerStats mainHero, PowerStats comparedHero){
        this.strength = (mainHero.getStrength() - comparedHero.getStrength()) > 0 ? "+" + String.valueOf(mainHero.getStrength() - comparedHero.getStrength()) : String.valueOf(mainHero.getStrength() - comparedHero.getStrength());
        this.agility = (mainHero.getAgility() - comparedHero.getAgility()) > 0 ? "+" + String.valueOf(mainHero.getAgility() - comparedHero.getAgility()) : String.valueOf(mainHero.getAgility() - comparedHero.getAgility());
        this.dexterity = (mainHero.getDexterity() - comparedHero.getDexterity()) > 0 ? "+" + String.valueOf(mainHero.getDexterity() - comparedHero.getDexterity()) : String.valueOf(mainHero.getDexterity() - comparedHero.getDexterity());
        this.intelligence = (mainHero.getIntelligence() - comparedHero.getIntelligence()) > 0 ? "+" + String.valueOf(mainHero.getIntelligence() - comparedHero.getIntelligence()) : String.valueOf(mainHero.getIntelligence() - comparedHero.getIntelligence());
    }

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

    public String getStrength() {
        return strength;
    }

    public void setStrength(String strength) {
        this.strength = strength;
    }

    public String getAgility() {
        return agility;
    }

    public void setAgility(String agility) {
        this.agility = agility;
    }

    public String getDexterity() {
        return dexterity;
    }

    public void setDexterity(String dexterity) {
        this.dexterity = dexterity;
    }

    public String getIntelligence() {
        return intelligence;
    }

    public void setIntelligence(String intelligence) {
        this.intelligence = intelligence;
    }
}
