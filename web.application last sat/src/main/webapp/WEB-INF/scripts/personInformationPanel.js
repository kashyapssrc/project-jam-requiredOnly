var personInformationPanel = {};

personInformationPanel.view;
personInformationPanel.fields = ['personIdDetail', 'personFirstNameDetail', 'personLastNameDetail', 'personMailDetail', 'personBirthDateDetail'];
personInformationPanel.info;
personInformationPanel.rowDetails;

personInformationPanel.createChildren = function () {
}

personInformationPanel.createView = function () {

    var requestReceived = 4;
    var statusReceived = 200;
    var xhttpRequest = new XMLHttpRequest();
    xhttpRequest.onreadystatechange = function () {
        if (this.readyState === requestReceived && this.status === statusReceived) {
            personInformationPanel.view = this.responseText;
        }
    };

    xhttpRequest.open('GET', 'personInformationPanel.html',false);
    xhttpRequest.send();
}

personInformationPanel.listenEvents = function () {
    var fields = personInformationPanel.fields;

    document.getElementById('reset')
            .addEventListener('click', function () { onReset();});

    document.getElementById('submit')
            .addEventListener('click', function () { personSubmitted(); });

    eventManager.subscribe('selectRow', onSelectRow);
    eventManager.subscribe('addRow', onAddRow)
}

var onSelectRow = function (details) {
    var headers = personListPanel.columns;
    var fields = personInformationPanel.fields;
    for ( var i = 0; i < fields.length; i++) {
        document.getElementById(fields[i]).value = details[headers[i]];
    }
    personInformationPanel.info = details;
    document.getElementById('personId').style.display = 'none';
    document.getElementById('personIdDetail').style.display = 'none';
}

var onReset = function () {
    var details = personInformationPanel.info;
    var label = personListPanel.columns;
    var headers = personInformationPanel.fields;
    var newId = document.getElementById('personIdDetail').value;
    if (newId == "") {
        for ( var i = 1; i < headers.length; i++) {
            document.getElementById(headers[i]).value = '';
        }
    } else {
        for ( var i = 0; i < headers.length; i++) {
            document.getElementById(headers[i]).value = details[label[i]];
        }
    }
}

var onAddRow = function () {
    var fields = personInformationPanel.fields;
    for ( var i = 0; i < fields.length; i++) {
        document.getElementById(fields[i]).value = "";
    }
    document.getElementById('personId').style.display = 'none';
    document.getElementById('personIdDetail').style.display = 'none';
}

personInformationPanel.setDefault = function () {}

var personSubmitted = function () {
    var fields = personInformationPanel.fields;
    var details = {};
    for ( var i = 0; i < fields.length; i++) {
        details[fields[i]] = document.getElementById(fields[i]).value;
    }
    eventManager.broadcast('submitPerson', details);
}