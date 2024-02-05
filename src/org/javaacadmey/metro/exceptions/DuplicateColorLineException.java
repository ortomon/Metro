package org.javaacadmey.metro.exceptions;

import org.javaacadmey.metro.components.LineColor;

public class DuplicateColorLineException extends Exception {
    public DuplicateColorLineException(LineColor color) {
        super("Линия с цветом " + color + " уже существует.");
    }
}
