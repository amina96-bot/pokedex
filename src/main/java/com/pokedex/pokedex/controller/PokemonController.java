package com.pokedex.pokedex.controller;

import com.pokedex.pokedex.model.Pokemon;
import com.pokedex.pokedex.model.Type;
import com.pokedex.pokedex.repository.AttaqueRepository;
import com.pokedex.pokedex.repository.PokemonRepository;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.*;

@RestController
public class PokemonController {

    @Autowired
    private PokemonRepository pokemonRepository;

    @Autowired
    private AttaqueRepository attaqueRepository;

    @RequestMapping(method = RequestMethod.GET, value = "/pokemons")
    public List<Pokemon> getJsonListOfPokemons() {
        return pokemonRepository.findAll();
    }

    // l'API principale qui retourne la page index.html
    @GetMapping("/")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView showPokemonList() {

        //chercher la liste de tous les pokémons et l'ajouter au modelAndView pour pourvoir l'afficher dans la page destination qui est index.html
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.addObject("pokemons", pokemonRepository.findAll());

        //chercher la liste de tous les types et l'ajouter au modelAndView pour pourvoir l'afficher dans la page destination qui est index.html
        List<Type> typesList=new ArrayList<>();
        Collections.addAll(typesList, Type.values());
        modelAndView.addObject("types", typesList);

        //retourner la page index.html
        modelAndView.setViewName("index");

        return modelAndView;
    }

    @GetMapping(value="/login")
    public ModelAndView getLoginPage(Model model){

        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("login");

        return modelAndView;
    }

    @GetMapping(value="/logout-success")
    public ModelAndView getLogoutPage(Model model){
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("logout");

        return modelAndView;
    }


    //recherche par type, quand le bouton rechercher est clique, on mappe vers cette api qui cherche d'abord la liste despokemons correspondant au type saisi et l'associe au model pour qu'elle soie affichee dans une autre page qui est search.html
    @PostMapping("/search")
    public ModelAndView getPokemonsByOneType(Type type){
        List<Type> typesList=new ArrayList<>();
        typesList.add(type);

        ModelAndView modelAndView=new ModelAndView();
        modelAndView.addObject("pokemons", pokemonRepository.findByTypeInList(typesList));
        modelAndView.setViewName("search-pokemon");

        return modelAndView;
    }


    //preparer un objet pokemon vide, recuperer la liste des attaques, la liste de tous les types et la liste des pokemons (pour remplir la table des evolutions pokemons et pouvoir y selectionner)
    @GetMapping("/signup")
    public ModelAndView showSignUpForm() {

        ModelAndView modelAndView=new ModelAndView();

        Pokemon pokemon=new Pokemon();
        modelAndView.addObject("pokemon", pokemon);
        modelAndView.addObject("attaques", attaqueRepository.findAll());

        List<Type> typesList=new ArrayList<>();
        Collections.addAll(typesList, Type.values());

        modelAndView.addObject("types", typesList);
        modelAndView.addObject("pokemons", pokemonRepository.findAll());
        //retourner la page add-pokemon.html
        modelAndView.setViewName("add-pokemon");

        return modelAndView;
    }

    //quand le bouton Ajouter de la page add-pokemon est clique on mappe vers cette API
    @PostMapping("/addpokemon")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView addPokemon(@Valid Pokemon pokemon, BindingResult result) {
        ModelAndView modelAndView=new ModelAndView();

        //verifier si pas d'erreurs :champs non vides grace aux annotations @NoBlank dans l'entite Pokemon
        if (result.hasErrors()) {
            modelAndView.setViewName("add-pokemon");
            return modelAndView;
        }
        // ajouter pokemon a la bdd
        pokemonRepository.save(pokemon);
        //rediriger vers la page de l'annuaire
        modelAndView.setViewName("redirect:/");
        return modelAndView;
    }

    //chercher le pokemon dont le id est: id en parametres, pour afficher ses anciennes infos et preparer les listes des types, attaques et pokemons pour d'eventuelles modifications
    @GetMapping("/edit/{id}")
    public ModelAndView showUpdateForm(@PathVariable("id") long id) {

        ModelAndView modelAndView=new ModelAndView();

        Pokemon pokemon = pokemonRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid pokemon Id:" + id));

        modelAndView.addObject("pokemon", pokemon);
        modelAndView.addObject("attaques", attaqueRepository.findAll());

        List<Type> typesList = new ArrayList<>(Arrays.asList(Type.values()));

        modelAndView.addObject("types", typesList);
        modelAndView.addObject("pokemons", pokemonRepository.findAll());

        //afficher la page update-pokemon.html
        modelAndView.setViewName("update-pokemon");

        return modelAndView;
    }

    //chercher le pokemon dont le id est: id en parametres,
    @GetMapping("/show/{id}")
    public ModelAndView showDetailForm(@PathVariable("id") long id) {

        ModelAndView modelAndView=new ModelAndView();

        Pokemon pokemon = pokemonRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid pokemon Id:" + id));
        modelAndView.addObject("pokemon", pokemon);
        //retourner la page show-pokemon.html
        modelAndView.setViewName("show-pokemon");

        return modelAndView;
    }

    //chercher le pokemon dont le id est: id en parametres, et le modifier
    @PostMapping("/update/{id}")
    public ModelAndView updatePokemon(@PathVariable("id") long id, @Valid Pokemon pokemon,
                             BindingResult result) {
        ModelAndView modelAndView=new ModelAndView();
        if (result.hasErrors()) {
            pokemon.setId(id);
            modelAndView.setViewName("update-pokemon");
            return modelAndView;
        }
        pokemonRepository.save(pokemon);
        modelAndView.setViewName("redirect:/");
        return modelAndView;
    }

    //chercher le pokemon dont le id est: id en parametres, et le supprimer de la bdd
    @GetMapping("/delete/{id}")
    public ModelAndView deletePokemon(@PathVariable("id") long id) {

        Pokemon pokemon = pokemonRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid pokemon Id:" + id));
        pokemonRepository.delete(pokemon);

        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("redirect:/");

        return modelAndView;
    }
}
