package ru.kbakaras.e2.core.model;

import org.dom4j.Element;
import ru.kbakaras.e2.message.E2;
import ru.kbakaras.e2.message.E2Request;
import ru.kbakaras.e2.message.E2Response;
import ru.kbakaras.e2.message.E2Update;
import ru.kbakaras.e2.message.Use;
import ru.kbakaras.sugar.utils.ExceptionUtils;

import java.util.UUID;

/**
 * Абстрактный класс, от которого должны наследоваться классы, отвечающие за
 * подключение систем, участвующих в обмене данными через e2.
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public abstract class SystemConnection {

    public final UUID systemId;
    public final String systemName;
    public final Class<? extends SystemType> systemType;


    public SystemConnection(UUID systemId, String systemName, Class<? extends SystemType> systemType) {
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


    public void sendUpdate(E2Update update) {
        sendUpdateMessage(update);
    }

    public void sendRepeat(E2Update update) {
        sendRepeatMessage(update);
    }

    public E2Response sendRequest(E2Request request) {

        try {

            return sendRequestMessage(request);

        } catch (Exception e) {

            return new E2Response(request.requestType())
                    .addSystemResponse(systemId.toString(), systemName)
                    .addSystemError().setText(ExceptionUtils.getMessage(e))
                    .parent.parent;

        }

    }

    protected abstract void sendUpdateMessage(E2Update update);

    protected void sendRepeatMessage(E2Update update) {
        throw new UnsupportedOperationException("Message repeat capability is not implemented for this system");
    }

    protected abstract E2Response sendRequestMessage(E2Request request);

    //protected abstract Element sendMessage(E2XmlProducer xmlProducer);


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