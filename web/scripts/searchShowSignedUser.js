$(document).ready(function (){
    var searchNameShow = getURLParameter('search');
    searchShow(searchNameShow);
});


function getURLParameter(name) {
    return decodeURIComponent((new RegExp('[?|&]' + name + '=' + '([^&;]+?)(&|#|;|$)').exec(location.search) || [null, ''])[1].replace(/\+/g, '%20')) || null;
}

function searchShow(showNameToSearch) {
    var actionType = "getSearchShow";
    $.ajax({
        url: "SellTicket",
        data: {
            "ActionType": actionType,
            "showName": showNameToSearch,
        },
        success: function (shows) {
            var numOfShows = shows[0];
            var showsArr = shows[1];
            buildSearchShows(numOfShows, showsArr);
        }
    });
}

function buildSearchShows(n, shows){
    var id;
    var name;
    var date;
    var loc;
    var pic;
    var list = $('#myShowsList');
    var i;
    var li, div1, div2, img, h2, p1, p2, p3;

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
        img.setAttribute("width", "220");
        img.setAttribute("height", "160");
        $(img).on("click", {param: shows[i]}, redirect);


        div1.appendChild(img);
        li.appendChild(div1);

        div2.setAttribute("style", "display: inline-block");
        h2.setAttribute("name", "showName");
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
}

function redirect(event){
    var show = event.data.param;
    window.location.replace("showPageSignedUser.html?id="+show.m_ShowID);
}

function openPopup(msg) {
    $("#message").html(msg);
    document.getElementById('myModal').style.display = "block";
}

function closePopup() {
    $('#searchBar').html("");
    document.getElementById('myModal').style.display = "none";
}