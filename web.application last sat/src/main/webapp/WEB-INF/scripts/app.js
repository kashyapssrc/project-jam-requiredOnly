var app = {};
app.view;

app.createChildren = function () {
    lsp.createChildren();
    rsp.createChildren();
}

app.createView = function () {
    app.view = document.getElementById('app');
    lsp.createView();
    app.view.innerHTML += lsp.view;
    rsp.createView();
    app.view.innerHTML += rsp.view;
}

app.prePopulate = function () {
    lsp.prePopulate();
    rsp.prePopulate();
}

app.listenEvents = function () {
    lsp.listenEvents();
    rsp.listenEvents();
}

app.setDefault = function () {
    lsp.setDefault();
    rsp.setDefault();
}

app.onInit = function () {
    app.createChildren();
    app.createView();
    app.prePopulate();
    app.listenEvents();
    app.setDefault();
}
