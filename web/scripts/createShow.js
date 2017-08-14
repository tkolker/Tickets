function createShowPage(show, i){
    $('#showID').attr("showNum", show.m_ShowID);
    $('#showName').text(show.m_ShowName);
    $('#showDate').text(show.m_Date);
    $('#showLocation').text(show.m_Location);
    $('#showAbout').text(show.m_About);
    $('#showPicture').attr("src", show.m_PictureUrl);
    $('#ilovethis').text(show.m_Price);

    if(i == 1){
        var combo = $('#comboBox');
        var i, option;
        for(i = 0; i < show.m_NumOfTickets; i++){
            option = document.createElement('option');
            $(option).attr("value", i+1);
            $(option).text("value", i+1);
        }
    }
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
