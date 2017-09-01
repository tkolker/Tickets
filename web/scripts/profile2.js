$(document).ready(function (){
    $('#locFavButton').on("click", addFavoriteLocation);
    $('#showFavButton').on("click", addFavoriteShow);
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
            var div = $('#locationFavorites');
            buildFav(showsStr, div);
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
            "favoriteLocation": input,
        },
        success: function (showsStr) {
            var div = $('#showFavorites');
            buildFav(showsStr, div);
        }
    });
}

//TODO: debug adding favorites and showing them in the profile
function buildFav(shows, div){
    var favs = [];
    favs = shows.split(",");
    var span = document.createElement("span");
    var t = document.createElement("h5");

    for(var i = 0; i < favs.length; i++){
        var t = document.createElement("a");
        $(t).text(favs[i]);
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
            var boughtDiv = $('#myBoughtShow');
            buildShows(numOfShows, showsArr, boughtDiv, 1);
            $("[name='seeMoreButton']").attr("id", "seeMoreBought");
            $('#seeMoreSell').attr("onclick", "window.location.replace(\"myBoughtShows.html\")");
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
            var sellDiv = $('#mySellShow');
            var showsArr = shows[1];
            buildShows(numOfShows, showsArr, sellDiv, 0);
            $("[name='seeMoreButton']").attr("id", "seeMoreSell");
            $('#seeMoreSell').attr("onclick", "window.location.replace(\"myShows.html\")");
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
            var favDiv = $('#recommended');
            buildShows(numOfShows, showsArr, favDiv, 2);
            $("[name='seeMoreButton']").attr("id", "seeMoreBought");
            $('#seeMoreSell').attr("onclick", "window.location.replace(\"myFavoriteShows.html\")");
        }
    });
}

function buildShows(numOfShows, shows, div, bought){

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
    $(button).attr("name", "seeMoreButton");
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