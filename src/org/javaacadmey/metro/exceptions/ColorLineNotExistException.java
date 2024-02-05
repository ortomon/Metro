package org.javaacadmey.metro.exceptions;

import org.javaacadmey.metro.components.LineColor;

public class ColorLineNotExistException extends Exception{
    public ColorLineNotExistException(LineColor color) {
        super("Линия с цветом " + color + " не существует.");
    }
}
