package org.javaacadmey.metro.components;

public enum LineColor {
    RED("Красная"),
    BLUE("Синяя");

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
