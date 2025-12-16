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
        charms.add(new Charm("The Tidy Sprout", "Helpful, Precise, Observant.", "assets/charms/Tiny-Sprout.png"));
        charms.add(new Charm("The Midnight Potion", "Intense, Magnetic, Secretive.", "assets/charms/Midnight-Potion.png"));
        charms.add(new Charm("The Dreamy Bubble", "Imaginative, Kind, Ethereal.", "assets/charms/Dreamy-Bubble.png"));
        charms.add(new Charm("The Shy Seashell", "Nurturing, Intuitive, Sentimental.", "assets/charms/Shy-Seashell.png"));
        charms.add(new Charm("The Golden Sun-Lion", "Charismatic, Generous, Dramatic.", "assets/charms/Golden-Sun-Lion.png"));
        charms.add(new Charm("The Sweet Scales", "Aesthetic, Diplomatic, Romantic.", "assets/charms/Sweet Scales.png"));
        charms.add(new Charm("The Wandering Arrow", "Adventurous, Optimistic, Blunt.", "assets/charms/Wandering Arrow.png"));
        charms.add(new Charm("The Tiny Summit", "Ambitious, Disciplined, Dryly Funny.", "assets/charms/Tiny-Summit.png"));
        charms.add(new Charm("The Cosmic Cloud", "Eccentric, Brilliant, Free-spirited.", "assets/charms/Cosmic Cloud.png"));

        return charms;
    }
}
