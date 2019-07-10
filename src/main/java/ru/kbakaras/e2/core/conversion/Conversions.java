package ru.kbakaras.e2.core.conversion;

import ru.kbakaras.sugar.lazy.MapCache;

import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;

public class Conversions extends MapCache<String, Conversion> {

    private Map<String, Class<? extends Conversion>> conversionMap;

    public Conversions(Map<String, Class<? extends Conversion>> conversionMap) {
        this(conversionMap, null);
    }

    public Conversions(Map<String, Class<? extends Conversion>> conversionMap, Supplier<UUID> uidSupplier) {

        super(inputName -> {
            Class<? extends Conversion> clazz = conversionMap.get(inputName);
            if (clazz != null) {
                try {

                    Conversion conversion = clazz.newInstance();

                    if (uidSupplier != null) {
                        conversion.setUidSupplier(uidSupplier);
                    }

                    return conversion;

                } catch (InstantiationException | IllegalAccessException e) {
                    throw new RuntimeException(e);
                }

            } else {
                return new ConversionCopy(inputName);
            }
        });
        this.conversionMap = conversionMap;

    }


}