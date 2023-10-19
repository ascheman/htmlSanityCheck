/*
 * adapted from the original code from Ralph Allan Rice by Gernot Starke.
 *
 * Copyright (c) 2010 Ralph Allan Rice ralph.rice@gmail.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 *
 */

package org.aim42.htmlsanitycheck.suggest;


import net.ricecode.similarity.JaroWinklerStrategy;
import net.ricecode.similarity.SimilarityScore;
import net.ricecode.similarity.SimilarityStrategy;
import net.ricecode.similarity.StringSimilarityService;
import net.ricecode.similarity.StringSimilarityServiceImpl;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class StringSimilarityServiceImplTest {

    SimilarityStrategy strategy;
    StringSimilarityService service;
    List<String> features;

    @Before
    public void setup() {
        strategy = new JaroWinklerStrategy();
        service = new StringSimilarityServiceImpl(strategy);

        features = new ArrayList<String>();
    }

    @Test
    public void testFindBestTwo() {
        String target = "McDonalds";

        String c1 = "MacMahons";
        String c2 = "McPherson";
        String c3 = "McDonaldz";


        features.add(c1);
        features.add(c2);
        features.add(c3);

        List<SimilarityScore> bestTwo = service.findBestN(features, target, 2);


        assertEquals(2, bestTwo.size());

        assertEquals("McDonaldz is expected to be the best match", c3,
                bestTwo.get(0).getKey());

        assertEquals("McPherson is expected to be the second best match", c2,
                bestTwo.get(1).getKey());

    }

    @Test
    public void testFindBestZero() {
        assert features.size() == 0;

        List<SimilarityScore> best = service.findBestN(features, "none", 2);

        assertEquals("empty list of candidates shall yield empty result", 0, best.size());

    }

    @Test
    public void testFindBestThreeInTooShortList() {
        String target = "McDonalds";

        features = Arrays.asList("McDommy", "NicClumsy");

        List<SimilarityScore> bestThree = service.findBestN(features, target, 3);

        assertEquals("even if list is too short, valid result is returned", 2, bestThree.size());
    }



    @Test
    public void testFindTopInList() {
        String target = "McDonalds";

        String c1 = "MacMahons";
        String c2 = "McPherson";
        String c3 = "McDonaldz";


        features.add(c1);
        features.add(c2);
        features.add(c3);

        List<SimilarityScore> scores = service.scoreAll(features, target);

        assertEquals(3, scores.size());

        assertEquals( "McDonaldz is expected to be the best match", "McDonaldz",
                service.findTop(features, target).getKey());

    }




    @Test
    public void testFindSuggestionInLongList() {
        String target = "Bröncohr";
        String bestMatch = "Broncor";

        features = Arrays.asList(
                "Abomasnow", "Abra", "Absol", "Accelgor", "Aerodactyl", "Aggron",
                "Aipom", "Alakazam", "Alomomola", "Altaria", "Ambipom", "Amoonguss",
                "Ampharos", "Anorith", "Arbok", "Arcanine", "Arceus", "Archen", "Archeops",
                "Ariados", "Armaldo", "Aron", "Articuno", "Audino", "Axew", "Azelf",
                "Azumarill", "Azurill", "Bagon", "Baltoy", "Banette", "Barboach",
                "Basculin", "Bastiodon", "Bayleef", "Beartic", "Beautifly", "Beedrill",
                "Beheeyem", "Beldum", "Bellossom", "Bellsprout", "Bibarel", "Bidoof",
                "Bisharp", "Blastoise", "Blaziken", "Blissey", "Blitzle", "Boldore",
                "Bonsly", "Bouffalant", "Braviary", "Breloom", "Bronzong", bestMatch,
                "Budew", "Buizel", "Bulbasaur", "Buneary", "Burmy", "Butterfree", "Cacnea",
                "Cacturne", "Camerupt", "Carnivine", "Carracosta", "Carvanha", "Cascoon",
                "Castform", "Caterpie", "Celebi", "Chandelure", "Chansey", "Charizard",
                "Charmander", "Charmeleon", "Chatot", "Cherrim", "Cherubi", "Chikorita",
                "Chimchar", "Chimecho", "Chinchou", "Chingling", "Cinccino", "Clamperl",
                "Claydol", "Clefable", "Clefairy", "Cleffa", "Cloyster", "Cobalion",
                "Cofagrigus", "Combee", "Combusken", "Conkeldurr", "Corphish", "Corsola",
                "Cottonee", "Cradily", "Cranidos", "Crawdaunt", "Cresselia", "Croagunk",
                "Crobat", "Croconaw", "Crustle", "Cryogonal", "Cubchoo", "Cubone",
                "Cyndaquil", "Darkrai", "Darmanitan", "Darumaka", "Deerling", "Deino",
                "Delcatty", "Delibird", "Deoxys", "Dewgong", "Dewott", "Dialga", "Diglett",
                "Ditto", "Dodrio", "Doduo", "Donphan", "Dragonair", "Dragonite", "Drapion",
                "Dratini", "Drifblim", "Drifloon", "Drilbur", "Drowzee", "Druddigon",
                "Ducklett", "Dugtrio", "Dunsparce", "Duosion", "Durant", "Dusclops",
                "Dusknoir", "Duskull", "Dustox", "Dwebble", "Eelektrik", "Eelektross",
                "Eevee", "Ekans", "Electabuzz", "Electivire", "Electrike", "Electrode",
                "Elekid", "Elgyem", "Emboar", "Emolga", "Empoleon", "Entei", "Escavalier",
                "Espeon", "Excadrill", "Exeggcute", "Exeggutor", "Exploud", "Farfetch'd",
                "Fearow", "Feebas", "Feraligatr", "Ferroseed", "Ferrothorn", "Finneon",
                "Flaaffy", "Flareon", "Floatzel", "Flygon", "Foongus", "Forretress",
                "Fraxure", "Frillish", "Froslass", "Furret", "Gabite", "Gallade",
                "Galvantula", "Garbodor", "Garchomp", "Gardevoir", "Gastly", "Gastrodon",
                "Gengar", "Geodude", "Gible", "Gigalith", "Girafarig", "Giratina", "Glaceon",
                "Glalie", "Glameow", "Gligar", "Gliscor", "Gloom", "Golbat", "Goldeen",
                "Golduck", "Golem", "Golett", "Golurk", "Gorebyss", "Gothita", "Gothitelle",
                "Gothorita", "Granbull", "Graveler", "Grimer", "Grotle", "Groudon",
                "Grovyle", "Growlithe", "Grumpig", "Gulpin", "Gurdurr", "Gyarados",
                "Happiny", "Hariyama", "Haunter", "Haxorus", "Heatmor", "Heatran",
                "Heracross", "Herdier", "Hippopotas", "Hippowdon", "Hitmonchan",
                "Hitmonlee", "Hitmontop", "Ho-Oh", "Honchkrow", "Hoothoot", "Hoppip",
                "Horsea", "Houndoom", "Houndour", "Huntail", "Hydreigon", "Hypno",
                "Igglybuff", "Illumise", "Infernape", "Ivysaur", "Jellicent", "Jigglypuff",
                "Jirachi", "Jolteon", "Joltik", "Jumpluff", "Jynx", "Kabuto", "Kabutops",
                "Kadabra", "Kakuna", "Kangaskhan", "Karrablast", "Kecleon", "Kingdra"
        );

        List<SimilarityScore> scores = service.scoreAll(features, target);

        assertEquals(267, scores.size());

        assertEquals( bestMatch + " is expected to be the best match", bestMatch,
                service.findTop(features, target).getKey());

    }



}
