$(document).ready(function (){
    loadUserName();
    getShows();
    $('#buttonLogOutWindow').on("click",logout);
    $('#buttonSearchShow').on("click", gotoSearchShow);

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


        $('#' + ref).attr("href", "showPageSignedUser.html?id=" + shows[i].m_ShowID);
        $('#' + img).attr("src", shows[i].m_PictureUrl);
        $('#' + show).text(shows[i].m_ShowName);
        $('#' + date).text(shows[i].m_Date + "\n" + shows[i].m_Location);
        $('#' + price).text(shows[i].m_Price);
    }
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
