package components;

public enum LineColor {
    RED("Красный"),
    BLUE("Синий");

    private String name;

    LineColor(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Color{" +
                "name='" + name + '\'' +
                '}';
    }
}
