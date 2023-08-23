requestPath = window.location.pathname;
baseUrl = window.location.origin + "/" + requestPath.split('/')[1] + "/";
shortBaseUrl = "/" + requestPath.split('/')[1] + "/";
//console.log("file : custom-loader.js || base url : " + baseUrl);

loaderIcon = "<img src='" + baseUrl + "resources/dist/img/loader.gif' width='40' height='40' alt='loading...'>";
marginTop = marginBottom = "15px";

function showLoader(reference) {
    var instance = $(".loader");
    if (reference) {
        instance = $(reference).find(".loader");
    }
    instance.html(loaderIcon).css({
        "margin-top": marginTop,
        "margin-bottom": marginBottom,
        "text-align": 'center',
        "color": '#2874F0'
    }).show();
}

function hideLoader(reference) {
    var instance = $(".loader");
    if (reference) {
        instance = $(reference).find(".loader");
    }
    instance.html("").hide();
}

function showButtonLoader(instance, text) {
    $(instance).attr("val", $(instance).html());
    $(instance).html("<i class=\"fa fa-spinner fa-spin\"></i>" + (text ? " " + text + "..." : ""));
    $(instance).prop("disabled", true);
}

function hideButtonLoader(instance) {
    $(instance).html($(instance).attr("val"));
    $(instance).prop("disabled", false);
}