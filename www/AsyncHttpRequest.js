var exec = require("cordova/exec");

function loadDataFromUrl(success,error,str) {
    if (loader && loader.length>0) loader.show();
    cordova.exec(success, error, "AsyncHttpRequest", "loadDataFromURL", [str]);
}

window.loadDataFromUrl = loadDataFromUrl;
module.exports = loadDataFromUrl;


