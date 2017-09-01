var id;

$(document).ready(function (){
    id = getURLParameter('id');
    $('#ilovethis').on("click", redirect);
    getShow(id, 0);
});

function getURLParameter(name) {
    return decodeURIComponent((new RegExp('[?|&]' + name + '=' + '([^&;]+?)(&|#|;|$)').exec(location.search) || [null, ''])[1].replace(/\+/g, '%20')) || null;
}

function redirect(){
    window.location.replace("login.html?goto=showPageSignedUser.html?id=" + id);
}


function createShowPage(show, i){
    $('#showID').attr("showNum", show.m_ShowID);
    $('#showName').text(show.m_ShowName);
    $('#showDate').text(show.m_Date);
    $('#showLocation').text(show.m_Location);
    $('#about').text(show.m_About);
    $('#showPicture').attr("src", show.m_PictureUrl);
    $('#numOfTickets').text("מספר כרטיסים שנרכשו: " + show.m_NumOfTickets);
    $('#total').text("סך הכל שולם: " + show.m_NumOfTickets * show.m_Price + "₪");
    $('#price').text("מחיר כרטיס: " + show.m_Price + "₪");

    var seller = show.m_Seller;

    if(!seller) {
        i =0;
        $('#comboBox').hide();
    }

    if(i == 1){
        var combo = $('#comboBox');
        var i, option;
        for(i = 0; i < show.m_NumOfTickets; i++){
            option = document.createElement('option');
            $(option).attr("value", i+1);
            $(option).text(i+1);
            $(combo).append(option);
        }
    }
}

function getShow(id, i){
    var actionType = "getShow";

    $.ajax({
        url: "SellTicket",
        data: {
            "showID" : id,
            "ActionType": actionType,
        },
        success: function (show) {
            createShowPage(show, i);
        }
    });
}

