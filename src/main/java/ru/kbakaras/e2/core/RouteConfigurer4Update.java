package ru.kbakaras.e2.core;

import ru.kbakaras.e2.core.model.SystemConnection;

@FunctionalInterface
public interface RouteConfigurer4Update {
    void add(SystemConnection from, SystemConnection to, String...entities);
}