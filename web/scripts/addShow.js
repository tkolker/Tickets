$(document).ready(function (){
    setTimeLimits();
    $('#buttonAddShow').on("click", addShow);
});

function addShow(){
    var invalidInput = 0;
    var formData;

    var showName = $('#showName').val();
    var showDate = $('#showDate').val();
    var showLocation = $('#showLocation').val();
    var showPrice = $('#showPrice').val();
    var pictureUrl = $('#pictureUrl').val();
    var showPicFile = $("#showPic").val();
    var numOfTickets = $('#numOfTickets').val();
    var showAbout = $('#showAbout').val();
    var actionType = "addShow";
    var showPic = pictureUrl;

    if (showName === "" || showDate === "" || showLocation === "" || (pictureUrl === "" && showPicFile === "") || numOfTickets === "") {
        openPopup("נא למלא את כל השדות");
        invalidInput = 1;
    }

    /*
    if(pictureUrl === "") {
        showPic = showPicFile;
        if (validFileExtension(showPic)) {
            formData = new FormData();
            formData.append('fileName', $('#showPic')[0].files[0]);
        }
    }
*/

    if(invalidInput === 0) {
        $.ajax({
            type: "POST",
            url: "SellTicket",
            data: {
                "ActionType": actionType,
                "showName": showName,
                "showDate": showDate,
                "showLocation": showLocation,
                "pictureUrl":showPic,
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

function validFileExtension(file) {

    var ext = file.split(".");
    ext = ext[ext.length-1].toLocaleLowerCase();
    var arrayExtensions = ["jpg", "png"];

    if(arrayExtensions.lastIndexOf(ext) == -1){
        openPopup("קובץ מסוג זה לא נתמך. הכנס קובץ מסוג jpg או png." +
            "או קישור לתמונה מאתר");
        return false;
    }
    return true;
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

