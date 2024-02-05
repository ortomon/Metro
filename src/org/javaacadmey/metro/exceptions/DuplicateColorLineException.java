package org.javaacadmey.metro.exceptions;

import org.javaacadmey.metro.metro.components.line.LineColor;

public class DuplicateColorLineException extends Exception {
    public DuplicateColorLineException(LineColor color) {
        super("Линия с цветом " + color + " уже существует.");
    }
}
