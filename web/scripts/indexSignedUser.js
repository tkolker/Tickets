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

}