$(document).ready(function (){
    loadUserName();
    getShows();
    $('#buttonLogOutWindow').on("click",gotoLogout);
});

function loadUserName(){
    var invalidInput = 0;

    var actionType = "getUserFromSession"

    if(invalidInput === 0) {
        $.ajax({
            url: "login",
            data: {
                "ActionType": actionType,
            },
            success: function (user){
                addUsername(user);
            }
        });
    }
}


function addUsername(name){
    $('#loggedInUserName').text(name);
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


        buildHref(ref, shows[i]);
        $('#' + img).attr("src", shows[i].m_PictureUrl);
        $('#' + show).text(shows[i].m_ShowName);
        $('#' + date).text(shows[i].m_Date + "\n" + shows[i].m_Location);
        $('#' + price).text(shows[i].m_Price);
    }
}

function buildHref(ref, show){
    $('#' + ref).attr("href", "showPageSignedUser.html?id=" + show.m_ShowID)
}

function gotoLogout(){
    var actionType = "logout"

    $.ajax({
        url: "login",
        type: "POST",
        data: {
            "ActionType": actionType,
        },
        success: function (user) {
            window.location.replace("index.html");
        }
    });
}


function openPopup(msg) {
    $("#message").html(msg);
    $("#popup").show();
}

function closePopup() {
    window.hide();
}
