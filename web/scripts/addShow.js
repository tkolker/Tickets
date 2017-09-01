$(document).ready(function (){
    setTimeLimits();
    $('#buttonAddShow').on("click", addShow);
});

function addShow(){
    var invalidInput = 0;
    var formData = new FormData();
    var pictureUrl = $('#pictureUrl').val();
    var showPicFile = $('#showPic').val();
    var picType, showPic;

    if ($('#showName').val() === "" || $('#showDate').val() === "" || $('#showLocation').val() === "" || (pictureUrl === "" && showPicFile === "") || $('#showPrice').val() === "" || $('#numOfTickets').val() === "" || $('#showAbout').val() === "") {
        openPopup("נא למלא את כל השדות");
        invalidInput = 1;
    }

    if(pictureUrl === "") {
        if (validFileExtension(showPicFile)) {
            formData.append('pictureUrl', document.getElementById('showPic').files[0]);
            formData.append('picType', 0);
        }
        else
            invalidInput = 1;
    }
    else {
        if (validFileExtension(pictureUrl)) {
            formData.append('pictureUrl', pictureUrl);
            formData.append('picType', 1);
        }
        else
            invalidInput = 1;
    }

    formData.append("ActionType", "addShow");
    formData.append("showName", $('#showName').val());
    formData.append("showDate", $('#showDate').val());
    formData.append("showLocation", $('#showLocation').val());
    formData.append("showPrice", $('#showPrice').val());
    formData.append("numOfTickets", $('#numOfTickets').val());
    formData.append("showAbout", $('#showAbout').val());

    if(invalidInput === 0) {
        $.ajax({
            type: "POST",
            url: "SellTicket",
            data: formData,
            processData: false,
            contentType: false,
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
        openPopup("המופע קיים במערכת");
    }
    else if (i === 2) {
        var url = "mySellShow.html?id=" + show.m_ShowID;
        window.location.replace(url);
    }
    else if (i == 8){
        openPopup("תאריך המופע צריך להיות לכל היותר שבוע מהיום");
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

