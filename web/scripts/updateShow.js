var id;

$(document).ready(function (){
    id = getURLParameter('id');
    $('#buttonUpdateShow').on("click", updateShow);
    $('#buttonUpdateShow').on("click", updateShow);
});

function updateShow(){
    var actionType = "updateShow";
    var showName = $('#showName').val();
    var showDate = $('#showDate').val();
    var showLocation = $('#showLocation').val();
    var showPrice = $('#showPrice').val();
    var pictureUrl = $('#pictureUrl').val();
    var numOfTickets = $('#numOfTickets').val();
    var showAbout = $('#showAbout').val();

    $.ajax({
        type: "POST",
        url: "SellTicket",
        data: {
            "ActionType": actionType,
            "showName": showName,
            "showDate": showDate,
            "showLocation": showLocation,
            "pictureUrl": pictureUrl,
            "numOfTickets": numOfTickets,
            "showPrice" : showPrice,
            "showAbout": showAbout,
        },
        success: function (show) {
            createShowPage(show);
        }
    });
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