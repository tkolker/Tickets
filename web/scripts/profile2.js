$(document).ready(function (){
    $('#locFavButton').on("click", addFavoriteLocation);
    $('#showFavButton').on("click", addFavoriteShow);
    getFavoritesShows();
    getFavoritesLocations();
    getMySellShows();
    getMyBoughtShows();
    getMyFavorites();
});

function addFavoriteLocation(){
    var actionType = "addFavoriteLocation";
    var input = $('#locationFavoritesInput').val();

    $.ajax({
        url: "SellTicket",
        type: 'POST',
        data: {
            "ActionType": actionType,
            "favoriteLocation": input,
        },
        success: function (showsStr) {
            var div = $('#locationFavorites').empty();
            buildFav(showsStr, div, "loc");
            getMyFavorites();
        }
    });
}


function addFavoriteShow(){
    var actionType = "addFavoriteShow";
    var input = $('#showFavoritesInput').val();

    $.ajax({
        url: "SellTicket",
        type: 'POST',
        data: {
            "ActionType": actionType,
            "favoriteShow": input,
        },
        success: function (showsStr) {
            var div = $('#showFavorites').empty();
            buildFav(showsStr, div, "show");
            getMyFavorites();
        }
    });
}


function buildFav(shows, div, type){
    var favs = [];
    favs = shows.split(",");
    var span = document.createElement("span");

    for(var i = 0; i < favs.length - 1; i++){
        var t = document.createElement("h4");
        var x = document.createElement("button");
        $(x).text("x");
        $(x).attr("class", "tinyButton");
        $(x).on("click", {param1:favs[i], param2:type}, removeKey)
        $(t).text(favs[i]);
        $(t).attr("style", "padding:5px;");
        $(span).append(x);
        $(span).append(t);
    }

    $(div).append(span);
}


function getMyBoughtShows(){
    var actionType = "getMyBoughtShows";

    $.ajax({
        url: "SellTicket",
        data: {
            "ActionType": actionType,
        },
        success: function (shows) {
            var numOfShows = shows[0];
            var showsArr = shows[1];
            var boughtDiv = $('#myBoughtShow').empty();
            var header = document.createElement('h4');
            $(header).text("כרטיסים שרכשתי");
            $(boughtDiv).append(header).append('<br>');
            var goto = "window.location.replace(\"myBoughtTickets.html\")";
            buildShows(numOfShows, showsArr, boughtDiv, 1, goto);
        }
    });
}

function getMySellShows(){
    var actionType = "getMySellShows";

    $.ajax({
        url: "SellTicket",
        data: {
            "ActionType": actionType,
        },
        success: function (shows) {
            var numOfShows = shows[0];
            var sellDiv = $('#mySellShow').empty();
            var showsArr = shows[1];
            var header = document.createElement('h4');
            $(header).text("כרטיסים שאני מוכר");
            $(sellDiv).append(header).append('<br>');
            var goto = "window.location.replace(\"myShows.html\")";
            buildShows(numOfShows, showsArr, sellDiv, 0, goto);
        }
    });
}

function getFavoritesShows(){
    var actionType = "getFavoritesShows";

    $.ajax({
        url: "SellTicket",
        data: {
            "ActionType": actionType,
        },
        success: function (shows) {
            var showDiv = $('#showFavorites').empty();
            buildFav(shows, showDiv, "show");
        }
    });
}

function getFavoritesLocations(){
    var actionType = "getFavoritesLocations";

    $.ajax({
        url: "SellTicket",
        data: {
            "ActionType": actionType,
        },
        success: function (locations) {
            var locDiv = $('#locationFavorites').empty();
            buildFav(locations, locDiv, "loc");
        }
    });
}

function getMyFavorites(){
    var actionType = "getMyFavorites";

    $.ajax({
        url: "SellTicket",
        data: {
            "ActionType": actionType,
        },
        success: function (shows) {
            var numOfShows = shows[0];
            var showsArr = shows[1];
            var header = document.createElement('h4');
            $(header).text("כרטיסים מומלצים");
            var favDiv = $('#recommended').empty();
            $(favDiv).append(header).append('<br>');
            var goto = "window.location.replace(\"myFavorites.html\")";
            buildShows(numOfShows, showsArr, favDiv, 2, goto);
        }
    });
}

function buildShows(numOfShows, shows, div, bought, goto){

    var n = 3;
    var id;
    var name;
    var date;
    var loc;
    var pic;
    var i;
    var li, div1, div2, img, h2, p1, p2, p3, button;


    if(numOfShows < n) { n = numOfShows; }

    for(i = 0; i < n; i++){
        li = document.createElement('li');
        div1 = document.createElement('div');
        div2 = document.createElement('div');
        img = document.createElement('img');
        h2 = document.createElement('h2');
        p1 = document.createElement('p');
        p2 = document.createElement('p');
        p3 = document.createElement('p');

        div1.setAttribute("style", "display: inline-block");
        img.setAttribute("name", "showPicture");
        img.setAttribute("src", shows[i].m_PictureUrl);
        img.setAttribute("class", "showProfilePic");

        redirectToPage(img, shows[i], bought);

        div1.appendChild(img);
        li.appendChild(div1);

        div2.setAttribute("style", "display: inline-block");
        h2.setAttribute("name", "showName");
        $(h2).text(shows[i].m_ShowName);

        redirectToPage(h2, shows[i], bought);

        p1.setAttribute("name", "showDate");
        $(p1).text(shows[i].m_Date);

        p2.setAttribute("name", "showLocation");
        $(p2).text(shows[i].m_Location);


        p3.setAttribute("name", "showID");

        div2.appendChild(h2);
        div2.appendChild(p1);
        div2.appendChild(p2);
        div2.appendChild(p3);
        li.appendChild(div2);

        $(div).append(li);
    }

    button = document.createElement('button');
    $(button).attr("onclick", goto);
    $(button).attr("class", "seeAllButton");
    $(button).text("ראה הכל");

    $(div).append(button);
}

function redirectToPage(div, show, i){
    if(i == 0){
        $(div).on("click", {param: "mySellShow.html?id="+show.m_ShowID}, redirect);
    }
    else if(i == 1){
        $(div).on("click", {param: "showPageStatic.html?id="+show.m_ShowID}, redirect);
    }
    else{
        $(div).on("click", {param: "showPageSignedUser.html?id="+show.m_ShowID}, redirect);
    }
}


function redirect(event){
    var p = event.data.param;
    window.location.replace(p);
}

function removeKey(event){
    var keyWord = event.data.param1;
    var type = event.data.param2;

    if(type == "loc"){
        removeLocationKeyword(keyWord);
    }
    else{
        removeShowKeyword(keyWord);
    }
}

function removeLocationKeyword(keyword){
    var actionType = "removeLocFav";

    $.ajax({
        url: "SellTicket",
        type: "POST",
        data: {
            "ActionType": actionType,
            "locKeyword": keyword,
        },
        success: function (locations) {
            var locDiv = $('#locationFavorites').empty();
            buildFav(locations, locDiv, "loc");
            getMyFavorites();
        }
    });
}

function removeShowKeyword(keyword){
    var actionType = "removeShowFav";

    $.ajax({
        url: "SellTicket",
        type: "POST",
        data: {
            "ActionType": actionType,
            "showKeyword": keyword,
        },
        success: function (shows) {
            var showDiv = $('#showFavorites').empty();
            buildFav(shows, showDiv, "show");
            getMyFavorites();
        }
    });
}