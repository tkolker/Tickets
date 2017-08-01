$(document).ready(function (){
    var id = getURLParameter('id');
    getShow(id);
    $('#buttonDeleteShow').on("click", deleteShow);
    $('#buttonGoToUpdate').on("click", passId);
});

function passId(){
    window.location.replace("updateShow.html");
}

function getURLParameter(name) {
    return decodeURIComponent((new RegExp('[?|&]' + name + '=' + '([^&;]+?)(&|#|;|$)').exec(location.search) || [null, ''])[1].replace(/\+/g, '%20')) || null;
}

function deleteShow(){
    var actionType = "deleteShow";
    var showID = document.getElementById("showID");

    $.ajax({
        type: "POST",
        url: "SellTicket",
        data: {
            "ActionType": actionType,
            "showID": showID,
        },
        success: function (result) {
            var addShowRes = result[0];
            var show = result[1];
            showResponse(addShowRes, show);
        }
    });
}

function getShow(id){
    var actionType = "getShow";

    $.ajax({
        url: "SellTicket",
        data: {
            "showID" : id,
            "ActionType": actionType,
        },
        success: function (show) {
            createShowPage(show);
        }
    });
}

function showResponse(i, show) {
    if (i === "0") {
        openPopup();
    }
    else if (i === "1") {

    }
}