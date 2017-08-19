$(document).ready(function (){
    $('#buttonSingup').on("click", performSignup);
});

function performSignup(){
    var invalidInput = 0;

    var userFname = $('#userFname').val();
    var userLname = $('#userLname').val();
    var userPassword = $('#userPassword').val();
    var userEmail = $('#userEmail').val();
    var actionType = "signup";

    //noinspection JSAnnotator
    if (userFname === "" || userLname === "" || userPassword === "" || userEmail === "") {
        openPopup("נא למלא את כל השדות");
        invalidInput = 1;
    }


    if(invalidInput === 0) {
        $.ajax({
            type: "POST",
            url: "login",
            cache: false,
            data: {
                "ActionType" : actionType,
                "firstName": userFname,
                "lastName": userLname,
                "email": userEmail,
                "password": userPassword,
            },
            success: function (result) {
                var signupResult = result[0];
                var user = result[1];
                showResponse(signupResult, user);
            }
        });
    }
}

function showResponse(i, user) {
    if (i === 0){
        openPopup("לא קיים שם המשתמש במערכת");
    }
    else if(i === 1) {
        $("#loggedInUserName").text(user.m_fName);
        window.location.replace("indexSignedUser.html");
    }
    else if(i === 2) {
        openPopup("משתמש קיים במערכת");
    }
    else if (i === 3){
        $("#loggedInUserName").text(user.m_fName);
        window.location.replace("indexSignedUser.html");
    }
    else if (i == 4)
    {
        openPopup("דואר אלקטרוני לא תקין");
    }
    else {
        openPopup("סיסמא לא נכונה");
    }
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
    $('#username').reset();
    $('#userPassword').reset();
    document.getElementById('myModal').style.display = "none";
}

window.onclick = function(event) {
    var modal = document.getElementById('myModal');
    if (event.target == modal) {
        modal.style.display = "none";
    }
}