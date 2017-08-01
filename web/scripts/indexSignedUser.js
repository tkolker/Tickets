$(document).ready(function (){
    loadUserName();
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
    $("#popup").hide();
}
