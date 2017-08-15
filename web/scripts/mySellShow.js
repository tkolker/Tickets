$(document).ready(function (){
    var id = getURLParameter('id');
    getShow(id, 0);
    $('#buttonDeleteShow').on("click", deleteShow);
    $('#buttonGoToUpdate').on("click", passId);
});

function passId(){
    var id = $('#showID').attr("showNum");
    window.location.replace("updateShow.html?id="+id);
}

function getURLParameter(name) {
    return decodeURIComponent((new RegExp('[?|&]' + name + '=' + '([^&;]+?)(&|#|;|$)').exec(location.search) || [null, ''])[1].replace(/\+/g, '%20')) || null;
}

function deleteShow(){
    var actionType = "deleteShow";
    var showID = $('#showID').attr("showNum");

    $.ajax({
        type: "POST",
        url: "SellTicket",
        data: {
            "ActionType": actionType,
            "showID": showID,
        },
        success: function (result) {
            var removeShowRes = result[0];
            var show = result[1];
            showResponse(removeShowRes, show);
        }
    });
}


function showResponse(i, show) {
    if (i === "5") {
        openPopup("ההופעה של "+show+ " נמחקה בהצלחה");
        window.location.replace("myShows.html");
    }
    else if (i === "4") {
        openPopup("אופס! קרתה תקלה. ההופעה לא נמחקה. נסה שוב");
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