package ru.kbakaras.e2.core;

import ru.kbakaras.e2.core.model.SystemInstanceBase;

public interface RouteConfigurer4Update {
    void add(SystemInstanceBase from, SystemInstanceBase to, String...sourceEntities);
}