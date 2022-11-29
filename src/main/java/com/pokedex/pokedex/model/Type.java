package com.pokedex.pokedex.model;

import java.util.stream.Stream;

public enum Type {

    ACIER("Acier"),
    COMBAT("Combat"),
    DRAGON("Dragon"),
    EAU("Eau"),
    ELECTRIK("Électrik"),
    FEE("Fée"),
    FEU("Feu"),
    GLACE("Glace"),
    INSECTE("Insecte"),
    NORMAL("Normal"),
    POISON("Poison"),
    PLANTE("Plante"),
    PSY("Psy"),
    ROCHE("Roche"),
    SOL("Sol"),
    SPECTRE("Spectre"),
    TENEBRES("Ténèbres"),
    VOL("Vol");

    private String value;

    Type(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static Type of(String value) {
        return Stream.of(Type.values())
                .filter(p -> p.getValue().equals(value))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
