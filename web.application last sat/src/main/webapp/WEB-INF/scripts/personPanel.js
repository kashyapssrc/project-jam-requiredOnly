var personPanel = {};
personPanel.view;
personPanel.container;

personPanel.createChildren = function () {
    personListPanel.createChildren();
    personInformationPanel.createChildren();
}

personPanel.createView = function () {

    personListPanel.createView();
    document.getElementById('personPanel').innerHTML += personListPanel.view;
    personInformationPanel.createView();
    document.getElementById('personPanel').innerHTML += personInformationPanel.view;
}

personPanel.prePopulate = function () {
    personListPanel.prePopulate();
}

personPanel.listenEvents = function () {
    personListPanel.listenEvents();
    personInformationPanel.listenEvents();
}

personPanel.setDefault = function () {
    personListPanel.setDefault();
    personInformationPanel.setDefault();
}

personPanel.setView = function () {
    var requestReceived = 4;
    var statusReceived = 200;
    var xhttpRequest = new XMLHttpRequest();
    xhttpRequest.onreadystatechange = function () {
        if (this.readyState == requestReceived && this.status == statusReceived) {
            personPanel.view = this.responseText;
        }
    }

    xhttpRequest.open('GET', 'personPanel.html',false);
    xhttpRequest.send();
}
personPanel.onInit = function () {
    personPanel.createChildren();
    personPanel.createView();
    personPanel.prePopulate();
    personPanel.listenEvents();
    personPanel.setDefault();
}