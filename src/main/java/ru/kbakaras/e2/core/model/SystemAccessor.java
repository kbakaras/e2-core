package ru.kbakaras.e2.core.model;

import org.dom4j.Element;
import ru.kbakaras.e2.message.E2;
import ru.kbakaras.e2.message.E2Response;
import ru.kbakaras.e2.message.Use;
import ru.kbakaras.sugar.utils.ExceptionUtils;

import java.util.UUID;

public abstract class SystemAccessor {
    public final UUID systemId;
    public final String systemName;
    public final Class<? extends SystemType> systemType;

    public SystemAccessor(UUID systemId, String systemName, Class<? extends SystemType> systemType) {
        this.systemId   = systemId;
        this.systemName = systemName;
        this.systemType = systemType;
    }


    /**
     * Отправляет запрос к системе в понятном для неё формате, возвращает ответ в формате системы.
     * Этот функционал вынесен в отдельный метод для того, чтобы его можно было запускать в отдельном потоке.
     * @param request Запрос в формате донора.
     * @return Ответ от донора в формате донора.
     */
    public Element request(Element request) {
        try {
            return doRequest(request);
        } catch (Exception e) {
            Element error = Use.createRoot(E2.ERROR, E2.NS);
            error.setText(ExceptionUtils.getMessage(e));
            return error;
        }
    }

    public void update(Element update) {
        doRequest(update);
    }

    abstract protected Element doRequest(Element donorRequest);


    public Element convertRequest(Element request) {
        return request;
    }
    public E2Response convertResponse(Element response) {
        return new E2Response(response);
    }


    /**
     * Метод для упрощения доступа к идентификатору системы
     */
    public UUID getId() {
        return systemId;
    }

    /**
     * Метод для упрощения доступа к наименованию системы
     */
    public String getName() {
        return systemName;
    }


    @Override
    public String toString() {
        return systemName;
    }
}