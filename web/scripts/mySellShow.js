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


function showResponse(i, show) {
    if (i === "0") {
        openPopup();
    }
    else if (i === "1") {

    }
}

function openPopup(msg) {
    $("#message").html(msg);
    document.getElementById('myModal').style.display = "block";
}

function closePopup() {
    $('#username').reset();
    $('#userPassword').reset();
    document.getElementById('myModal').style.display = "none";
}

window.onclick = function(event) {
    var modal = document.getElementById('myModal');
    if (event.target == modal) {
        modal.style.display = "none";
    }
}