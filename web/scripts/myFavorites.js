$(document).ready(function (){
    getMyFavorites();
    loadUserName();
    $('#buttonLogOutWindow').on("click",logout);
});


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
            buildMyShows(numOfShows, showsArr);
        }
    });
}

function buildMyShows(n, shows){

    var id;
    var name;
    var date;
    var loc;
    var pic;
    var listDiv = $('#myShowList');
    var list = document.createElement('ul');
    $(list).attr("id", "showList");
    $(list).attr("style", "list-style-type: none;padding-right:15px;");
    var i;
    var li, div1, div2, img, h2, p1, p2, p3;

    for(i = 0; i < n; i++){
        li = document.createElement('li');
        div1 = document.createElement('div');
        div2 = document.createElement('div');
        img = document.createElement('img');
        h2 = document.createElement('h5');
        p1 = document.createElement('h5');
        p2 = document.createElement('h5');
        p3 = document.createElement('h5');

        div1.setAttribute("style", "display: inline-block");
        img.setAttribute("name", "showPicture");
        img.setAttribute("src", shows[i].m_PictureUrl);
        img.setAttribute("class", "showListPicListPage");
        li.setAttribute("class", "showBoxListPage");
        if(i%2 == 0 && n != 1){
            li.setAttribute("style", "float:left");
        }
        $(img).on("click", {param: shows[i]}, redirect);


        div1.appendChild(img);
        li.appendChild(div1);

        div2.setAttribute("style", "display: inline-block;");
        h2.setAttribute("name", "showName");
        $(h2).attr("style", "overflow-wrap: break-word;");
        $(h2).text(shows[i].m_ShowName);

        $(h2).on("click", {param: shows[i]}, redirect);

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

        $(list).append(li);
    }
    $(listDiv).append(list);
}

function redirect(event){
    var show = event.data.param;
    window.location.replace("showPageSignedUser.html?id="+show.m_ShowID);
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
                $('#loggedInUserName').text(user);
            }
        });
    }
}
