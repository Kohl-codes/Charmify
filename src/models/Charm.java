package models;

public class Charm {
    private String name;
    private String image;  // filename of PNG in assets/charms/
    private String description;
    
    public Charm(String name, String image, String description) {
        this.name = name;
        this.image = image;
        this.description = description;
    }

    public String getName() { return name; }
    public String getImage() { return image; }
    public String getDescription() { return description; }
}
