var addressListPanel = {};
addressListPanel.view;

addressListPanel.createChildren = function () {
}

addressListPanel.createView = function () {

    var request = new XMLHttpRequest();
    var READY_STATE_CODE = 4;
    var STATUS_CODE = 200;

    request.onreadystatechange = function() {
        if (this.readyState === READY_STATE_CODE && this.status === STATUS_CODE) {
                document.getElementById('app').innerHTML += this.responseText;
        }
    };

    request.open('GET', 'addressListPanel.html', false);
    request.send();
}

addressListPanel.prePopulate = function () {
    displayAddressDetails();
}

addressListPanel.listenEvents = function () {
    onClickAdd();
    onClickSubmit();
}

addressListPanel.setDefault = function () {
    onSelectAddressFirstRecord();
}

addressListPanel.onInit = function () {
    addressListPanel.createChildren();
    addressListPanel.createView();
    addressListPanel.prePopulate();
    addressListPanel.listenEvent();
    addressListPanel.setDefault();
}