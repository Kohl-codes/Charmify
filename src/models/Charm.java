package models;

/**
 * Represents a Charm that can be displayed on a 3D bracelet.
 * This class stores visual metadata used by the GUI for 3D effects.
 */
public class Charm {

    // Core identity
    private String name;
    private String description;
    private String imagePath;

    // 3D visual properties
    private double depth;        // how far the charm hangs (z-depth illusion)
    private double scale;        // size multiplier for perspective
    private double shineLevel;   // how reflective it appears (0.0 – 1.0)

    public Charm(String name, String description, String imagePath) {
        this.name = name;
        this.description = description;
        this.imagePath = imagePath;

        // Default 3D values
        this.depth = 1.0;
        this.scale = 1.0;
        this.shineLevel = 0.5;
    }

    // Optional full constructor for advanced charms
    public Charm(String name, String description, String imagePath,
                 double depth, double scale, double shineLevel) {
        this.name = name;
        this.description = description;
        this.imagePath = imagePath;
        this.depth = depth;
        this.scale = scale;
        this.shineLevel = shineLevel;
    }

    // ===== Getters & Setters =====

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

    // ===== 3D Properties =====

    public double getDepth() {
        return depth;
    }

    public void setDepth(double depth) {
        this.depth = depth;
    }

    public double getScale() {
        return scale;
    }

    public void setScale(double scale) {
        this.scale = scale;
    }

    public double getShineLevel() {
        return shineLevel;
    }

    public void setShineLevel(double shineLevel) {
        this.shineLevel = shineLevel;
    }

    @Override
    public String toString() {
        return "Charm{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", imagePath='" + imagePath + '\'' +
                ", depth=" + depth +
                ", scale=" + scale +
                ", shineLevel=" + shineLevel +
                '}';
    }
}
