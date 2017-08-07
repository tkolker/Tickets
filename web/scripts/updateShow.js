$(document).ready(function (){
    var id = getURLParameter('id');
    $('#showId').attr("showNum", id);
    $('#showId').hide();
    getShow(id);
    $('#buttonUpdateShow').on("click", updateShow);
});

function updateShow(){
    var actionType = "updateShow";
    var id = $('#showId').attr("showNum");
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
            "showID" : id,
            "showName": showName,
            "showDate": showDate,
            "showLocation": showLocation,
            "pictureUrl": pictureUrl,
            "numOfTickets": numOfTickets,
            "showPrice" : showPrice,
            "showAbout": showAbout,
        },
        success: function (res) {
            gotoShowPage(res[1]);
        }
    });
}

function gotoShowPage(show){
    window.location.replace("mySellShow.html?id="+show.m_ShowID)
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

function getURLParameter(name) {
    return decodeURIComponent((new RegExp('[?|&]' + name + '=' + '([^&;]+?)(&|#|;|$)').exec(location.search) || [null, ''])[1].replace(/\+/g, '%20')) || null;
}