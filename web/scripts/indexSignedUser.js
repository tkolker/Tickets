var counter = 0;

$(document).ready(function (){
    loadUserName();
    getShows();
    $('#buttonLogOutWindow').on("click",logout);
    $('#buttonSearchShow').on("click", gotoSearchShow);
    $('#loadMore').on("click", getShows);

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
            ++counter;
            buildIndex(numOfShows, showsArr);
        }
    });
}


function buildIndex(numOfShows, shows){
    var n = 15*counter;
    var i, li, a, img, div1, div2, summary, h2, h3, span;
    var ul = document.createElement('ul');
    var our_shows = $("#our-shows").empty();

    if(numOfShows < n){
        n = numOfShows;
        $('#loadMore').hide();
    }

    for(i = 0; i < n; i++){
        li  = document.createElement('li');
        a = document.createElement('a');
        img = document.createElement('img');
        div1 = document.createElement('div');
        div2 = document.createElement('div');
        summary = document.createElement('summary');
        h2 = document.createElement('h2');
        h3 = document.createElement('h3');
        span = document.createElement('span');

        $(a).attr("href", "showPageSignedUser.html?id=" + shows[i].m_ShowID);
        $(img).attr("src", shows[i].m_PictureUrl);
        $(img).attr("class", "indexPic");
        $(img).attr("alt", "מכרטסים");
        $(div1).attr("class", "overlay");
        $(h2).text(shows[i].m_ShowName);
        $(h3).text(shows[i].m_Date).append('<br>').append(shows[i].m_Location);
        $(div2).attr("class", "loves");
        $(span).text(shows[i].m_Price);

        $(summary).append(h2);
        $(summary).append(h3);
        $(div2).append(span);
        $(div1).append(summary);
        $(div1).append(div2);
        $(a).append(img);
        $(a).append(div1);
        $(li).append(a);

        $(ul).append(li);
    }

    $(our_shows).append(ul);
}

function logout(){
    var actionType = "logout";

    $.ajax({
        url: "login",
        type: "POST",
        data: {
            "ActionType": actionType,
        },
        success:
            window.location.replace("index.html")
    });
}

function gotoSearchShow() {
    var showNameToSearch = $('#searchBar').val();
    var actionType = "getShowExist";
    if (showNameToSearch == "")
    {
        openPopup("נא להקליד שם הופעה");
    }
    else {
        $.ajax({
            url: "SellTicket",
            data: {
                "ActionType": actionType,
                "showName": showNameToSearch,
            },
            success: function (isExist) {
                if (isExist[0]) {
                    window.location.replace("searchShowSignedUser.html?search=" + showNameToSearch);
                }
                else {
                    openPopup("לא נמצאה הופעה");
                }
            }
        });
    }
}


function openPopup(msg) {
    $("#message").html(msg);
    document.getElementById('myModal').style.display = "block";
}

function closePopup() {
    $('#username').html("");
    $('#userPassword').html("");
    document.getElementById('myModal').style.display = "none";
}
