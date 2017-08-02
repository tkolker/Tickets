$(document).ready(function (){
        $('#buttonLoginWindow').on("click",gotoLogin);
        $('#buttonSingupWindow').on("click", gotoSignup);
});



//TODO: make it a popup
function gotoLogin(){
    window.location.replace("login.html")
}

function gotoSignup(){
    window.location.replace("signup.html")
}

function openPopup(msg) {
    $("#message").html(msg);
    $("#popup").show();
}

function closePopup() {
    window.hide();
}







