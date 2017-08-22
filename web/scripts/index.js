$(document).ready(function (){
    getShows();
    $('#buttonLoginWindow').on("click",gotoLogin);
    $('#buttonSingupWindow').on("click", gotoSignup);
    $('#buttonSearchShow').on("click", gotoSearchShow);
});


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
        $('#' + date).text(shows[i].m_Date + "\n" + shows[i].m_Location);
        $('#' + price).text(shows[i].m_Price);
    }
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
                    window.location.replace("searchShow.html?search=" + showNameToSearch);
                }
                else {
                    openPopup("לא נמצאה הופעה");
                }
                }
            });
    }
}

function buildHref(ref, show){
    $('#' + ref).attr("href", "showPage.html?id=" + show.m_ShowID)
}

function gotoLogin(){
    window.location.replace("login.html")
}

function gotoSignup(){
    window.location.replace("signup.html")
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







