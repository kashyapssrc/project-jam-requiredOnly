var eventManager = {};
eventManager.subscribers = [];

eventManager.subscribe = function (event, eventListener) {
    eventManager.subscribers[event] = eventListener;
}

eventManager.broadcast = function(event, data) {
    var listen = eventManager.subscribers[event];
    listen(data);
}