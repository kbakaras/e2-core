package ru.kbakaras.e2.core;

import ru.kbakaras.e2.core.model.SystemInstanceBase;

@FunctionalInterface
public interface RouteConfigurer4Request {
    void add(SystemInstanceBase from, SystemInstanceBase to, String... entities);
}