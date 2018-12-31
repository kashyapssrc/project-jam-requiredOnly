var addressInformationPanel = {};
addressInformationPanel.view;

addressInformationPanel.createChildren = function () {
}

addressInformationPanel.createView = function () {

    var request = new XMLHttpRequest();
    var READY_STATE_CODE = 4;
    var STATUS_CODE = 200;

    request.onreadystatechange = function() {
        if (this.readyState === READY_STATE_CODE && this.status === STATUS_CODE) {
                document.getElementById('app').innerHTML += this.responseText;
        }
    };

    request.open('GET', 'addressInformationPanel.html', false);
    request.send();
}

addressInformationPanel.prePopulate = function () {
    displayAddressDetails();
}

addressInformationPanel.listenEvents = function () {
    onClickReset();
    onClickSubmit();
}

addressInformationPanel.setDefault = function () {
    onSelectAddressFirstRecord();
}

addressInformationPanel.onInit = function () {
    addressInformationPanel.createChildren();
    addressInformationPanel.createView();
    addressInformationPanel.prePopulate();
    addressInformationPanel.listenEvent();
    addressInformationPanel.setDefault();
}