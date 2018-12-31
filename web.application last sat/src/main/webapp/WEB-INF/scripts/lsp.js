var lsp = {};
lsp.view;

lsp.createChildren = function () {
}

lsp.createView = function () {
    var requestReceived = 4;
    var statusReceived = 200;
    var xhttpRequest = new XMLHttpRequest();
    xhttpRequest.onreadystatechange = function () {
        if (this.readyState === requestReceived && this.status === statusReceived) {
            lsp.view = this.responseText;
        }
    };

    xhttpRequest.open('GET', 'lsp.html',false);
    xhttpRequest.send();
}

lsp.prePopulate = function () {
}

lsp.listenEvents = function () {

    document.getElementById('personDetail').addEventListener('click', function () {
        eventManager.broadcast('eventSelected', 'person');
    });

    document.getElementById('addressDetail').addEventListener('click', function () {
        eventManager.broadcast('eventSelected', 'address');
    });
}

lsp.setDefault = function () {
    eventManager.broadcast('eventSelected', 'person');
}

lsp.onInit = function () {
    lsp.createChildren();
    lsp.createView();
    lsp.prePopulate();
    lsp.listenEvent();
    lsp.setDefault();
}
