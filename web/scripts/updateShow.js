$(document).ready(function (){
    var id = getURLParameter('id');
    $('#showId').attr("showNum", id);
    $('#showId').hide();
    getShow(id, 0);
    $('#buttonUpdateShow').on("click", updateShow);
});

function updateShow(){
    var invalidInput = 0;
    var formData = new FormData();
    var pictureUrl = $('#pictureUrl').val();
    var showPicFile = $('#showPic').val();
    var picType, showPic;

    if(pictureUrl != "" || showPicFile != "") {
        if (pictureUrl === "") {
            if (validFileExtension(showPicFile)) {
                formData.append('pictureUrl', document.getElementById('showPic').files[0]);
                formData.append('picType', 0);
            }
        }
        else {
            if (validFileExtension(pictureUrl)) {
                formData.append('pictureUrl', pictureUrl);
                formData.append('picType', 1);
            }
            else
                invalidInput = 1;
        }
    }
    else{
        formData.append('pictureUrl', "");
        formData.append('picType', 2);
    }

    formData.append("ActionType", "updateShow");
    formData.append("showID", $('#showId').attr("showNum"));
    formData.append("showName", $('#showName').val());
    formData.append("showDate", $('#showDate').val());
    formData.append("showLocation", $('#showLocation').val());
    formData.append("showPrice", $('#showPrice').val());
    formData.append("numOfTickets", $('#numOfTickets').val());
    formData.append("showAbout", $('#showAbout').val());

    if(!invalidInput) {
        $.ajax({
            type: "POST",
            url: "SellTicket",
            data: formData,
            processData: false,
            contentType: false,
            success: function (res) {
                gotoShowPage(res[1]);
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