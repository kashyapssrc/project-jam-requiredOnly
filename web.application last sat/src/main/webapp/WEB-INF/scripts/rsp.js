var rsp = {};
rsp.container;

rsp.createChildren = function () {
}

rsp.createView = function () {

    var requestReceived = 4;
    var statusReceived = 200;
    var xhttpRequest = new XMLHttpRequest();
    xhttpRequest.onreadystatechange = function () {
        if (this.readyState == requestReceived && this.status == statusReceived) {
            rsp.view = this.responseText;
        }
    };

    xhttpRequest.open('GET', 'rsp.html',false);
    xhttpRequest.send();
    rsp.container = document.getElementById('rsp');
}

rsp.prePopulate = function () {}

rsp.listenEvents = function () {
    eventManager.subscribe('eventSelected', rsp.onEventSelected);
}

rsp.setDefault = function () {
}

rsp.onEventSelected = function (data) {

    
    if (data === 'person') {
        personPanel.setView();
        document.getElementById('rsp').innerHTML = personPanel.view;
        personPanel.onInit();
    } else if (data === 'address') {
        addressPanel.onInit();
    }
}

rsp.onInit = function () {
    rsp.createChildren();
    rsp.createView();
    rsp.prePopulate();
    rsp.listenEvents();
    rsp.setDefault();
}
