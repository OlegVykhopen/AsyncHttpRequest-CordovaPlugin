cordova.define("cordova-plugin-asynchttprequest.AsyncHttpRequest", function(require, exports, module) {
    var exec = require('cordova/exec');

    exports.loadDataFromUrl = function(success,error,str) {
        if (loader && loader.length>0) loader.show();
        cordova.exec(success, error, "AsyncHttpRequest", "loadDataFromURL", [str]);
    };

});
