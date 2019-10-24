package DMCBM.StatBlocks.Builder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;

import DMCBM.StatBlocks.Builder.Types.Alignment;
import DMCBM.StatBlocks.Builder.Types.ArmorTypes;
import DMCBM.StatBlocks.Builder.Types.Conditions;
import DMCBM.StatBlocks.Builder.Types.DamageTypes;
import DMCBM.StatBlocks.Builder.Types.HitDie;
import DMCBM.StatBlocks.Builder.Types.Languages;
import DMCBM.StatBlocks.Builder.Types.Sizes;

import static j2html.TagCreator.*;
import static DMCBM.StatBlocks.Builder.BonusTags.*;
import j2html.tags.ContainerTag;

public class StatBuilder {

    private String name;
    private Sizes size; private String race; private Alignment alignment;
    private int armorClass; private ArmorTypes armorType;
    private int hitPoints; private int lvl; private HitDie hitDie;
    private int charisma; private int constitution; private int dexterity; private int intelligence; private int strength; private int wisdom;
    private int speed; private int burrowSpeed; private int climbSpeed; private int flySpeed; private int swimSpeed;
    private List<DamageTypes> damageImmunities;
    private List<Conditions> conditionImmunities;
    private int blindsight; private int darkvision; private int tremorsense; private int truesight;
    private List<Languages> languages;
    private double challengeRating; private int exp;
    private Map<String, String> passives;
    private Map<String, String> actions;

    public String render() {
        return stat_block(
            creature_heading(
                h1(this.name),
                h2(String.format("%s %s, %s", this.size.name(), this.race, this.alignment.get()))
            ),

            top_stats(
                property_line(
                    h4("Armor Class"),
                    p(String.format(" %d (%s armor)", this.armorClass, this.armorType.name()))
                ),
                property_line(
                    h4("Hit Points"),
                    p(String.format(" %d (%d%s + %d)", this.hitPoints, this.lvl, this.hitDie.name(), this.hitDie.get()))
                ),
                renderSpeed(),
                abilities_block()
                .attr("data-cha", this.charisma)
                .attr("data-con", this.constitution)
                .attr("data-dex", this.dexterity)
                .attr("data-int", this.intelligence)
                .attr("data-str", this.strength)
                .attr("data-wis", this.wisdom),
                property_line(
                    h4("Damage Immunities"),
                    (this.damageImmunities.size() != 0) ? p(" " + String.join(", ", this.damageImmunities.stream().map(d -> d.name()).toArray(String[]::new))) : p(" None")
                ),
                property_line(
                    h4("Conditon Immunities"),
                    (this.conditionImmunities.size() != 0) ? p(" " + String.join(", ", this.conditionImmunities.stream().map(c -> c.name()).toArray(String[]::new))) : p(" None")
                ),
                renderSenses(),
                property_line(
                    h4("Languages"),
                    (this.languages.size() != 0) ? p(" " + String.join(", ", this.languages.stream().map(l -> l.name()).toArray(String[]::new))) : p(" None")
                ),
                property_line(
                    h4("Challenge"),
                    p(String.format(" %.2f (%d XP)", this.challengeRating, this.exp))
                )
            ),

            each(this.passives.entrySet(), passive ->
                property_block(
                    h4(passive.getKey()),
                    p(" " + passive.getValue())
                )
            ),

            h3("Actions"),

            each(this.actions.entrySet(), action ->
                property_block(
                    h4(action.getKey()),
                    p(" " + action.getValue())
                )
            )
        ).render();
    }

    private ContainerTag renderSpeed() {
        Map<String, Integer> speeds = new HashMap<String, Integer>();
        speeds.put("Burrow Speed", burrowSpeed);
        speeds.put("Climb Speed", climbSpeed);
        speeds.put("Fly Speed", flySpeed);
        speeds.put("Swim Speed", swimSpeed);

        return property_line(
            property_line(
                h4("Speed"),
                p(String.format(" %d ft", this.speed))
            ),
            each(filter(speeds.entrySet(), speed -> 
                speed.getValue() != 0), speed -> 
                    property_line(
                        h4(speed.getKey()),
                        p(String.format(" %d ft", speed.getValue()))
                    )
            )
        );
    }

    private ContainerTag renderSenses() {
        Map<String, Integer> senses = new HashMap<String, Integer>();
        senses.put("Blindsight", blindsight);
        senses.put("Darkvision", darkvision);
        senses.put("Tremorsense", tremorsense);
        senses.put("Truesight", truesight);

        List<Entry<String, Integer>> fSenses = filter(senses.entrySet(), sense -> sense.getValue() != 0);

        return property_line(h4("Senses")).with(
            (fSenses.size() != 0) ? each(fSenses, sense ->
            property_line(p(String.format("%s %d ft", sense.getKey(), sense.getValue())))
            ) : p(" None")
        );
    }

    // ------------------------- BELOW HERE ARE BUILDER METHODS ------------------------- //

    public StatBuilder() {
        passives = new HashMap<String, String>();
        actions = new HashMap<String, String>();
    }

    public StatBuilder name(String name) {
        this.name = name;
        return this;
    }

    public StatBuilder addPassive(String name, String desc) {
        passives.put(name, desc);
        return this;
    }

    public StatBuilder addAction(String name, String desc) {
        actions.put(name, desc);
        return this;
    }
    
    public StatBuilder challengeRating(double challengeRating, int exp) {
        this.challengeRating = challengeRating;
        this.exp = exp;
        return this;
    }

    // ------------------------- BELOW HERE IS AUTOMATED STUFF ------------------------- //

    private int bRand(double bias, int seed, int start, int end) {
        double weight = 1;
        double r_gaus = (new Random(seed).nextGaussian() + 1) / 2;
        return (int) Math.max(Math.min((r_gaus * (end - start)) + (weight * bias), end), start);
    }

    public StatBuilder max_random() {
        Random rand = new Random();
        double cr = (rand.nextInt(20) + 1) * 0.25;
        int seed = rand.nextInt(10000);
        this.challengeRating(cr, (int) (cr * 200));
        return this.max_random(cr, seed);
    }

    public StatBuilder max_random(double cr, int seed) {
        this.r_alignment(cr, seed);
        this.r_conditionImmunities(cr, seed);
        this.r_damageImmunities(cr, seed);
        this.r_hitPoints(cr, seed);
        this.r_languages(cr, seed);
        this.r_race(cr, seed);
        this.r_sight(cr, seed);
        this.r_size(cr, seed);
        this.r_speed(cr, seed);
        this.r_speed(cr, seed);
        this.r_stats(cr, seed);
        return this;
    }

    public StatBuilder size(Sizes size) {
        this.size = size;
        return this;
    }

    public StatBuilder r_size(double cr, int seed) {
        Sizes[] sizes = Sizes.values();
        this.size = sizes[this.bRand(cr, seed, 0, sizes.length - 1)];
        return this;
    }

    public StatBuilder race(String race) {
        this.race = race;
        return this;
    }

    public StatBuilder r_race(double cr, int seed) {
        this.race = Types.Races[this.bRand(cr, seed, 0, Types.Races.length - 1)];
        return this;
    }

    public StatBuilder alignment(Alignment alignment) {
        this.alignment = alignment;
        return this;
    }

    public StatBuilder r_alignment(double cr, int seed) {
        Alignment[] alignments = Alignment.values();
        this.alignment = alignments[this.bRand(cr, seed, 0, alignments.length - 1)];
        return this;
    }

    public StatBuilder hitPoints(int hitPoints, int lvl, HitDie hitDie) {
        this.hitPoints = hitPoints;
        this.lvl = lvl;
        this.hitDie = hitDie;
        return this;
    }

    public StatBuilder r_hitPoints(double cr, int seed) {
        HitDie[] dies = HitDie.values();

        if (this.lvl == 0) {
            this.lvl = this.bRand(cr, seed, 1, 20);
        }
        this.hitDie = dies[this.bRand(cr, seed, 0, dies.length - 1)];
        this.hitPoints = this.hitDie.get();
        this.hitPoints += new Random(seed).nextInt(lvl * this.hitDie.get()) + this.lvl;
        return this;
    }

    public StatBuilder stats(int charisma, int constitution, int dexterity, int intelligence, int strength, int wisdom) {
        this.charisma = charisma;
        this.constitution = constitution;
        this.dexterity = dexterity;
        this.intelligence = intelligence;
        this.strength = strength;
        this.wisdom = wisdom;

        calcArmorClass(new Random().nextInt());
        return this;
    }

    public StatBuilder r_stats(double cr, int seed) {
        Random rand = new Random(seed);

        if (this.lvl == 0) {
            this.lvl = this.bRand(cr, seed, 1, 20);
        }

        this.charisma = 3 + rand.nextInt(6 * 3) + rand.nextInt(this.lvl);
        this.constitution = 3 + rand.nextInt(6 * 3) + rand.nextInt(this.lvl);
        this.dexterity = 3 + rand.nextInt(6 * 3) + rand.nextInt(this.lvl);
        this.intelligence = 3 + rand.nextInt(6 * 3) + rand.nextInt(this.lvl);
        this.strength = 3 + rand.nextInt(6 * 3) + rand.nextInt(this.lvl);
        this.wisdom = 3 + rand.nextInt(6 * 3) + rand.nextInt(this.lvl);

        calcArmorClass(seed);
        return this;
    }

    public StatBuilder speed(int speed, int burrowSpeed, int climbSpeed, int flySpeed, int swimSpeed) {
        this.speed = speed;
        this.burrowSpeed = burrowSpeed;
        this.climbSpeed = climbSpeed;
        this.flySpeed = flySpeed;
        this.swimSpeed = swimSpeed;
        return this;
    }

    public StatBuilder r_speed(double cr, int seed) {
        Random rand = new Random(seed);

        this.speed = 5 * (rand.nextInt(11) + 2);
        this.burrowSpeed = (rand.nextInt(10) == 0) ? (5 * (rand.nextInt(11) + 2)) : 0;
        this.climbSpeed = (rand.nextInt(10) == 0) ? (5 * (rand.nextInt(11) + 2)) : 0;
        this.flySpeed = (rand.nextInt(10) == 0) ? (5 * (rand.nextInt(11) + 2)) : 0;
        this.swimSpeed = (rand.nextInt(10) == 0) ? (5 * (rand.nextInt(11) + 2)) : 0;
        return this;
    }

    public StatBuilder sight(int blindsight, int darkvision, int tremorsense, int truesight) {
        this.blindsight = blindsight;
        this.darkvision = darkvision;
        this.tremorsense = tremorsense;
        this.truesight = truesight;
        return this;
    }

    public StatBuilder r_sight(double cr, int seed) {
        Random rand = new Random(seed);
        this.blindsight = (rand.nextInt(10) == 0) ? (5 * (rand.nextInt(5) + 2)) : 0;
        this.darkvision = (rand.nextInt(10) == 0) ? (5 * (rand.nextInt(5) + 2)) : 0;;
        this.tremorsense = (rand.nextInt(10) == 0) ? (5 * (rand.nextInt(5) + 2)) : 0;
        this.truesight = (rand.nextInt(10) == 0) ? (5 * (rand.nextInt(5) + 2)) : 0;
        return this;
    }

    public StatBuilder damageImmunities(DamageTypes... damageImmunities) {
        this.damageImmunities = Arrays.asList(damageImmunities);
        return this;
    }

    public StatBuilder r_damageImmunities(double cr, int seed) {
        Random rand = new Random(seed);
        DamageTypes[] damageTypes = DamageTypes.values();
        this.damageImmunities = new ArrayList<DamageTypes>();

        int iterations = rand.nextInt((int) (cr * 2) + 3);
        for (int i = 0; i < iterations; i++) {
            DamageTypes type = damageTypes[rand.nextInt(damageTypes.length - 1)];
            if (!this.damageImmunities.contains(type)) {
                this.damageImmunities.add(type);
            }
        }

        return this;
    }

    public StatBuilder conditionImmunities(Conditions... conditionImmunities) {
        this.conditionImmunities = Arrays.asList(conditionImmunities);
        return this;
    }

    public StatBuilder r_conditionImmunities(double cr, int seed) {
        Random rand = new Random(seed);
        Conditions[] conditions = Conditions.values();
        this.conditionImmunities = new ArrayList<Conditions>();

        int iterations = rand.nextInt((int) (cr * 2) + 3);
        for (int i = 0; i < iterations; i++) {
            Conditions condition = conditions[rand.nextInt(conditions.length - 1)];
            if (!this.conditionImmunities.contains(condition)) {
                this.conditionImmunities.add(condition);
            }
        }
        
        return this;
    }

    public StatBuilder languages(Languages... languages) {
        this.languages = Arrays.asList(languages);
        return this;
    }

    public StatBuilder r_languages(double cr, int seed) {
        Random rand = new Random(seed);
        Languages[] langs = Languages.values();
        this.languages = new ArrayList<Languages>();

        this.languages.add(Languages.Common);

        int iterations = rand.nextInt((int) (cr * 2) + 1);
        for (int i = 0; i < iterations; i++) {
            Languages lang = langs[rand.nextInt(langs.length - 1)];
            if (!this.languages.contains(lang)) {
                this.languages.add(lang);
            }
        }

        return this;
    }

    private void calcArmorClass(int seed) {
        ArmorTypes[] armorTypes = ArmorTypes.values();
        this.armorType = armorTypes[new Random(seed).nextInt(armorTypes.length - 1)];
        this.armorClass = this.dexterity + this.armorType.get();
    }
}