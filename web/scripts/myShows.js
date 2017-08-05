$(document).ready(function (){
    getMyShows();
});


function getMyShows(){
    var actionType = "getMySellShows";

    $.ajax({
        url: "SellTicket",
        data: {
            "ActionType": actionType,
        },
        success: function (shows) {
            var numOfShows = shows[0];
            var showsArr = shows[1];
            buildMyShows(numOfShows, showsArr);
        }
    });
}

function buildMyShows(n, shows){
    //TODO: build unknown number of shows dynamically

    var id;
    var name   ;
    var date;
    var loc;
    var pic;
    var i;

    for(i = 0; i < 1; i++){
        id = "showID" + (i+1);
        name = "showName" + (i+1);
        date = "showDate" + (i+1);
        loc = "showLocation" + (i+1);
        pic = "showPicture" + (i+1);


        $('#' + id).text(shows[i].m_ShowID);
        $('#' + id).hide();
        $('#' + name).text(shows[i].m_ShowName);
        $('#' + date).text(shows[i].m_Date);
        $('#' + loc).text(shows[i].m_Location);
        $('#' + pic).attr("src", shows[i].m_PictureUrl);
        $('#' + pic).on("click", {param: shows[i]}, redirect);
        $('#' + name).on("click", {param: shows[i]}, redirect);
    }
}

function redirect(event){
    var show = event.data.param;
    window.location.replace("mySellShow.html?id="+show.m_ShowID);
}
