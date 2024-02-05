package org.javaacadmey.metro.metro.helpers;

import org.javaacadmey.metro.exceptions.DuplicateColorLineException;

@FunctionalInterface
public interface CheckedConsumer {
    void accept() throws DuplicateColorLineException;
}
