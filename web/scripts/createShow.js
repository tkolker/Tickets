function createShowPage(show){
    $('#showID').attr("showNum", show.m_ShowID);
    $('#showName').text(show.m_ShowName);
    //$('#showPrice').text(show.m_Price);
    $('#showDate').text(show.m_Date);
    $('#showLocation').text(show.m_Location);
    $('#showAbout').text(show.m_About);
    $('#showPicture').attr("src", show.m_PictureUrl);
    $('#ilovethis').text(show.m_Price);
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
