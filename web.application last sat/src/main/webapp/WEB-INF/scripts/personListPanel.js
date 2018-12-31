var personListPanel = {};
var person = {};
var row = {};
personListPanel.view;
personListPanel.personsList;
personListPanel.columns;
personListPanel.row;
var check = false;

personListPanel.createChildren = function () {
}

personListPanel.createView = function () {

    var requestReceived = 4;
    var statusReceived = 200;
    var xhttpRequest = new XMLHttpRequest();
    xhttpRequest.onreadystatechange = function () {
        if (this.readyState == requestReceived && this.status == statusReceived) {
            personListPanel.view = this.responseText;
        }
    };

    xhttpRequest.open('GET', 'personListPanel.html',false);
    xhttpRequest.send();
}

personListPanel.prePopulate = function () {
    person = getPerson();
    constructTable(person);
    personListPanel.personsList = person;
}

var getPerson = function () {

    // create xmlHttpRequest object
    requestObject = new XMLHttpRequest();

    // once request is received, do the needed operation
    requestObject.onreadystatechange = function() {
       if (requestObject.readyState == XMLHttpRequest.DONE) {
            if (requestObject.status == 200) {  
                result = requestObject.responseText;
            }
       }
    }

    // specify the request type and url
    requestObject.open('GET', 'http://localhost:8080/ws/person.html', false);

    // send request to server
    requestObject.send();

    return JSON.parse(requestObject.responseText);
}


var constructTable = function (person) {

    var headers = [];
    for (key in person[0]) {
        if (headers.indexOf(key) === -1) {
            headers.push(key);
        }
    }
    personListPanel.columns = headers;
    var xhttpRequest = new XMLHttpRequest();
    var requestReceived = 4;
    var statusReceived = 200;
    xhttpRequest.onreadystatechange = function () {
        if (this.readyState == requestReceived && this.status == statusReceived) {
            row = this.responseText;
            personListPanel.row = row;
            populateTable(row, person, headers);
        }
    };

    xhttpRequest.open('GET', '../html/template.html', false);
    xhttpRequest.send();
}

var populateTable = function (row, person, headers) {

    var table = document.getElementById('personTable');
    for (var i = 0; i < person.length; i++) {
        var newRecord = row.replace('%Id%', person[i][headers[0]])
                           .replace('Firstname', person[i][headers[1]])
                           .replace('Lastname', person[i][headers[2]])
                           .replace('E-mail', person[i][headers[3]])
                           .replace('Birthdate', person[i][headers[4]])
                           .replace('deleteId', 'delete' + person[i][headers[0]])
                           .replace('rowId', person[i][headers[1]] 
                                                   + person[i][headers[2]]);

        table.innerHTML += newRecord;
    }
}

personListPanel.listenEvents = function () {
    var headers = personListPanel.columns;
    var personsList = personListPanel.personsList;
    var column = personListPanel.columns;
    var personTable = document.getElementById('personTable');

    for (var i = 0; i < personsList.length; i++) {

        var rowId = person[i][headers[1]] + person[i][headers[2]];
        var deleteId = 'delete' + (i + 1);

        document.getElementById(rowId).addEventListener('click',
            function() {
                personListPanel.fetchRecord(this);
            });
        document.getElementById(deleteId).addEventListener('click',
            function() {
                personListPanel.onDelete(this);
            });

        var deleteRecord = document.getElementsByClassName('person-delete');

        document.getElementById('add').addEventListener('click',
            function() {
                eventManager.broadcast('addRow');
            });
    }
    eventManager.subscribe('submitPerson', onSubmitPerson);
}

personListPanel.setDefault = function () {
    var data = [];
    var column = personListPanel.columns;
    var personTable = document.getElementById('personTable');
    for (var j = 0; j < column.length - 1; j++) {
        data[column[j]] = personTable.rows[1].cells[j].innerHTML;
        console.log(personTable.rows[1].cells[j].innerHTML);
        }
    eventManager.broadcast('selectRow', data);
}

var onSubmitPerson = function (details) {
    var record = personListPanel.row;
    var persons = personListPanel.personsList;
    var headers = personListPanel.columns;
    var fields = personInformationPanel.fields;
    var personTable = document.getElementById('personTable');
    var personId = details.personIdDetail;
    if (personId === '') {
        personId = personTable.rows.length;
    }

    var invalidDetails = personListPanel.validate(details);
    if (invalidDetails) {
        return;
    } else if (invalidDetails != false) {
        for (var i = 0; i < persons.length; i++) {
            for (var j = 1; j < headers.length; j++) {
                if (persons[i][headers[0]] == personId) {
                    personTable.rows[i + 1].cells[j].innerHTML = details[fields[j]];
                }
            }
        }

        if (personId > persons.length) {
            var record = personListPanel.row;
            var personTable = document.getElementById('personTable');
            var newRecord = record.replace('%Id%', personId)
                                  .replace('Firstname', details[fields[1]])
                                  .replace('Lastname', details[fields[2]])
                                  .replace('E-mail', details[fields[3]])
                                  .replace('Birthdate', details[fields[4]])

            personTable.innerHTML += newRecord;
        }
    }
    personListPanel.listenEvents();
}

personListPanel.fetchRecord = function (selectedRecord) {
    var data = {};
    var header = personListPanel.columns;
    for (var j = 0; j < header.length; j++) {
        data[header[j]] = selectedRecord.cells[j].innerHTML;
    }
    eventManager.broadcast('selectRow', data);
}

personListPanel.validate = function (details) {
    var headers = personListPanel.columns;
    for (var j = 1; j < headers.length; j++) {
        if (details[headers[j]] === "") {
            window.alert('enter all the details');
            return false;
        }
    }
}

personListPanel.onDelete = function (selectedRecord) {
    var deleteRecord = selectedRecord.parentNode.parentNode;
    var parentRecord = deleteRecord.parentNode;
    parentRecord.removeChild(deleteRecord);

    // create xmlHttpRequest object
    requestObject = new XMLHttpRequest();

    requestObject.onreadystatechange = function() {
       
       if (requestObject.readyState == XMLHttpRequest.DONE) {
            
            if (requestObject.status == 200) {  
                 result = requestObject.responseText;
            }
       }
    }
    // specify the request type and url
    requestObject.open('DELETE', 'http://localhost:8080/ws/person.html', false);

    // send request to server
    requestObject.send(parentRecord.innerHTML);

    return JSON.parse(requestObject.responseText);
}
