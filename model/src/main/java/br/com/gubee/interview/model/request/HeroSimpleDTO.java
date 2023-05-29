package br.com.gubee.interview.model.request;

import br.com.gubee.interview.model.Hero;

import java.util.Optional;
import java.util.UUID;

public class HeroSimpleDTO {
    private UUID id;
    private String name;
    private String race;
    private int strength;
    private int agility;
    private int dexterity;
    private int intelligence;

    public HeroSimpleDTO(HeroDTO hero){
        this.id = hero.getId();
        this.name = hero.getName();
        this.race = hero.getRace();
        this.strength = hero.getStrength();
        this.agility = hero.getAgility();
        this.dexterity = hero.getDexterity();
        this.intelligence = hero.getDexterity();
    }

    public HeroSimpleDTO(){
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
