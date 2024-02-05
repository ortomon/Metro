package org.javaacadmey.metro.metro.helpers;

import org.javaacadmey.metro.exceptions.ColorLineNotExistException;
import org.javaacadmey.metro.exceptions.DuplicateStationNameException;
import org.javaacadmey.metro.exceptions.LineIsNotEmptyException;
import org.javaacadmey.metro.metro.components.Station;

@FunctionalInterface
public interface CheckedSupplier<T extends Station> {
    T get() throws ColorLineNotExistException, DuplicateStationNameException, LineIsNotEmptyException;
}
