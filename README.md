# e2-core

Модуль e2-core содержит абстракции, которые должна реализовывать
специализированная конфигурация.

## Конфигурирование e2

Конфигурирование e2 происходит в форме сборки специального jar-архива.
Этот архив содержит класссы, отражающие экземпляры интегрируемых систем,
классы для типов систем, классы для конверсий.

Каждый такой jar-файл должен содержать один специальный bootstrap-класс,
в котором определяются маршруты. Этот класс является наследником
`RouteConfigurer`, он должен реализовывать метод `setupRoutes()`,
который вызывается при инициализации конфигурации. В этот метод
передаются специальные объекты для регистрации в них маршрутов.
Bootstrap-класс должен быть указан в манифесте jar-архива.

Упоминаемые при инициализации маршрутов системы автоматически
регистрируются и записываются в базу данных e2. Автоматического удаления
систем из базы не предусмотрено. При этом маршруты живут только в
памяти, в базу не записываются, поэтому они легко могут быть
переконфигурированы.

Jar-файл конфигурации деплоится на сервер e2. Сервер записывает его в
базу данных и заново создаёт реестры маршрутов и конверсий в памяти
запуская bootstrap-класс, указанный в манифесте. Для создания реестра
конверсий применяется сканирование классов. Сканируется только пакет,
который таже указан в манифесте jar-архива конфигурации.

Выделение конфигурации в динамически загружаемый jar-файл позволяет:

* отделить сервер e2 от конфигурации;
* обновлять конфигурацию без остановки сервера; текущие процессы должны
  доработать со старой конфигурацией, а следующие в это время уже
  переключаются на новую;
* выполнять тестирование специализированных конверсий отдельно от
  сервера e2;
* поставлять сервер e2 в форме docker-имиджа.