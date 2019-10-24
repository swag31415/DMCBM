package DMCBM.StatBlocks.Builder;

public class Types {

    public enum Alignment {

        LG("Lawful Good"), NG("Neutral Good"), CG("Chaotic Good"), LN("Lawful Neutral"), NN("(True) Neutral"),
        CN("Chaotic Neutral"), LE("Lawful Evil"), NE("Neutral Evil"), CE("Chaotic Evil"), EN("Epic Neutral"),
        U("Unaligned");

        private String text;

        private Alignment(String text) {
            this.text = text;
        }

        public String get() {
            return this.text;
        }
    }

    public enum DamageTypes {
        Acid, Bludgeoning, Cold, Fire, Force, Lightning, Necrotic, Piercing, Poison, Psychic, Radiant, Slashing,
        Thunder;
    }

    public enum Conditions {
        Blinded, Charmed, Deafened, Exhaustion, Frightened, Grappled, Incapacitated, Invisible, Paralyzed, Petrified,
        Poisoned, Prone, Restrained, Stunned, Unconscious;
    }

    public enum Sizes {
        Tiny, Small, Medium, Large, Huge, Gargantuan;
    }

    public enum ArmorTypes {
        Natural(0), Light(1), Medium(0), Heavy(-2), Cloth(2);

        private int ac;

        private ArmorTypes(int ac) {
            this.ac = ac;
        }

        public int get() {
            return ac;
        }
    }

    public enum HitDie {
        d4(4), d6(6), d8(8), d10(10), d20(20), d100(100);

        private int die;

        private HitDie(int die) {
            this.die = die;
        }

        public int get() {
            return this.die;
        }
    }

    public enum Languages {
        Abyssal, Aquan, Auran, Celestial, Common, DeepSpeech, Draconic, Druidic, Dwarvish, Elvish, Giant, Gnomish,
        Goblin, Gnoll, Halfling, Ignan, Infernal, Orc, Primordial, Sylvan, Terran, Undercommon, Other;
    }

    public static final String[] Races = { "Dragonborn", "Dwarf", "Elf", "Gnome", "Half Elf", "Half Orc", "Halfling",
            "Human", "Tiefling", "Aarakocra", "Aasimar", "Bug Bear", "Firbolg", "Goblin", "Grung", "Hobgoblin", "Kenku",
            "Kobold", "Lizardfolk", "Orc", "Tabaxi", "Triton", "Yuan-Ti Pureblood", "Gith", "Changeling", "Eladrin",
            "Genasi", "Goliath", "Minotaur (Krynn)", "Shifter", "Warforged (Eberron)" };
}