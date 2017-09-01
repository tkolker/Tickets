var goto;

$(document).ready(function (){
    goto = getURLParameter('goto');
    runBravoCrawler();
    $('#buttonLogin').on("click", performLogin);
});

function getURLParameter(name) {
    return decodeURIComponent((new RegExp('[?|&]' + name + '=' + '([^&;]+?)(&|#|;|$)').exec(location.search) || [null, ''])[1].replace(/\+/g, '%20')) || null;
}

function performLogin(){
    var invalidInput = 0;

    var username = $('#username').val();
    var userPassword = $('#userPassword').val();
    var actionType = "login";

    if(username === "" || userPassword === ""){
        openPopup("נא למלא את כל השדות");
        invalidInput = 1;
    }

    if(invalidInput === 0) {
        $.ajax({
            url: "login",
            data: {
                "ActionType": actionType,
                "email": username,
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
        $('#loggedInUserName').val(user.m_fName);
        window.location.replace(goto);
    }
    else if(i === 2) {
        openPopup("משתמש קיים במערכת");
    }
    else if (i === 3){
        $("#loggedInUserName").val(user.m_fName);
        openPopup("הרשמה בוצעה בהצלחה");
        window.location.replace(goto);
    }
    else if (i == 4)
    {
        openPopup("דואר אלקטרוני לא תקין");
    }
    else {
        openPopup("סיסמא לא נכונה");
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

window.onclick = function(event) {
    var modal = document.getElementById('myModal');
    if (event.target == modal) {
        modal.style.display = "none";
    }
}
