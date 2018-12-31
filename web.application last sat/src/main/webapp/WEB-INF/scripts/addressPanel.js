var addressPanel = {};
addressPanel.view;

addressPanel.createChildren = function () {
    addressListPanel.createChildren();
    addressInformationPanel.createChildren();
}

addressPanel.createView = function () {
    addressListPanel.createView();
    addressInformationPanel.createView();
}

addressPanel.prePopulate = function () {
    addressListPanel.prePopulate();
    addressInformationPanel.prePopulate();
}

addressPanel.listenEvents = function () {
    addressListPanel.listenEvents();
    addressInformationPanel.listenEvents();
}

addressPanel.setDefault = function () {
    addressListPanel.setDefault();
    addressInformationPanel.setDefault();
}

addressPanel.onInit = function () {
    addressPanel.createChildren();
    addressPanel.createView();
    addressPanel.prePopulate();
    addressPanel.listenEvent();
    addressPanel.setDefault();
}