package data;

import java.util.ArrayList;
import java.util.List;
import models.Charm;

public class CharmTemplates {

    public static List<Charm> getAllCharms() {
        List<Charm> charms = new ArrayList<>();

        charms.add(new Charm("The Bold Ember", "Energetic, Spontaneous, Fearless.", "assets/charms/Bold-Ember.png"));
        charms.add(new Charm("The Cozy Moss-Stone", "Grounded, Loyal, Comfy.", "assets/charms/Cozy-Moss-Stone.png"));
        charms.add(new Charm("The Twin Stars", "Curious, Witty, Social.", "assets/charms/Twin-Stars.png"));
        charms.add(new Charm("The Tidy Sprout", "Helpful, Precise, Observant.", "assets/charms/Tidy-Sprout.png"));
        charms.add(new Charm("The Midnight Potion", "Intense, Magnetic, Secretive.", "assets/charms/Midnight-Potion.png"));
        charms.add(new Charm("The Dreamy Bubble", "Imaginative, Kind, Ethereal.", "assets/charms/Dreamy-Bubble.png"));

        return charms;
    }
}
