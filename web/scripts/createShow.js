function createShowPage(show){
    $('#showID').text(show.m_ShowID);
    $('#showName').text(show.m_ShowName);
    $('#showPrice').text(show.m_Price);
    $('#showDate').text(show.m_Date);
    $('#showLocation').text(show.m_Location);
    $('#showAbout').text(show.m_About);
    $('#showPicture').attr("src", show.m_PictureUrl);
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

function getShows(){
    var actionType = "getShows";

    $.ajax({
        url: "SellTicket",
        data: {
            "ActionType": actionType,
        },
        success: function (shows) {
            var numOfShows = shows[0];
            var showsArr = shows[1];
            buildIndex(numOfShows, showsArr);
        }
    });
}

function buildIndex(numOfShows, shows){
    var n = 15;
    var ref;
    var img;
    var show;
    var date;
    var price;
    var i;

    if(numOfShows < n){ n = numOfShows; }

    for(i = 0; i < n; i++){
        ref = "ref" + (i+1);
        img = "img" + (i+1);
        show = "show" + (i+1);
        date = "date" + (i+1);
        price = "price" + (i+1);

        $('#' + ref).attr("href", "showPage.html?id=" + shows[i].m_ShowID);
        $('#' + img).attr("src", shows[i].m_PictureUrl);
        $('#' + show).text(shows[i].m_ShowName);
        $('#' + date).text(shows[i].m_Date + "<br>" + shows[i].m_Location);
        $('#' + price).text(shows[i].m_Price);
    }
}