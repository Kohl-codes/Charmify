package models;

public class Charm {
    private String name;
    private String description;
    private String imagePath;

    public Charm(String name, String description, String imagePath) {
        this.name = name;
        this.description = description;
        this.imagePath = imagePath;  // Path relative to project root (assets/charms/...)
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Override
    public String toString() {
        return "Charm{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", imagePath='" + imagePath + '\'' +
                '}';
    }
}
