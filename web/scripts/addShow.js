$(document).ready(function (){
    setTimeLimits();
    $('#buttonAddShow').on("click", addShow);
});

function addShow(){
    var invalidInput = 0;

    var showName = $('#showName').val();
    var showDate = $('#showDate').val();
    var showLocation = $('#showLocation').val();
    var showPrice = $('#showPrice').val();
    var pictureUrl = $('#pictureUrl').val();
    var numOfTickets = $('#numOfTickets').val();
    var showAbout = $('#showAbout').val();
    var actionType = "addShow";

    //noinspection JSAnnotator
    if (showName === "" || showDate === "" || showLocation === "" || pictureUrl === "" || numOfTickets === "") {
        openPopup("נא למלא את כל השדות");
        invalidInput = 1;
    }


    if(invalidInput === 0) {
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
            success: function (result) {
                var addShowRes = result[0];
                var show = result[1];
                showResponse(addShowRes, show);
            }
        });
    }
}


function setTimeLimits(){
    var today = new Date();
    var nextweek = new Date(today.getDate() + 7);
    $('#showDate').attr("min", today);
    $('#showDate').attr("max", nextweek);
}


function showResponse(i, show) {
    if (i === 0) {
        openPopup();
    }
    else if (i === 1) {

    }
    else if (i === 2) {
        var url = "mySellShow.html?id=" + show.m_ShowID;
        window.location.replace(url);
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

