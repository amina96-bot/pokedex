package com.pokedex.pokedex.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "attaque")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Attaque {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nom")
    private String nom;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private Type type;

    @Column(name = "puissance")
    private String puissance;

    @Column(name = "precision")
    private String precision;

    @Column(name = "cout")
    private String cout;

    @Override
    public String toString() {
        return this.nom;
    }

    @Override
    public int hashCode() {
        return 1;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Attaque other = (Attaque) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}
