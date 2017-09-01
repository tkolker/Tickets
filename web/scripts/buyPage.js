var id;
var num;
var price;

$(document).ready(function (){
    $('#paypal-button').on("click", performPurchase);
    id = getURLParameter('id');
    num = getURLParameter('quantity');
    //$('#prev').attr('href', 'showPageSignedUser.html?id=' + id);
    getShow(id, 0);
});


function getURLParameter(name) {
    return decodeURIComponent((new RegExp('[?|&]' + name + '=' + '([^&;]+?)(&|#|;|$)').exec(location.search) || [null, ''])[1].replace(/\+/g, '%20')) || null;
}

function createBuyShowPage(show, i){
    price = show.m_Price;
    $('#showID').attr("showNum", show.m_ShowID);
    $('#showName').text(show.m_ShowName);
    $('#showDate').text(show.m_Date);
    $('#showLocation').text(show.m_Location);
    $('#price').text("מחיר: " + show.m_Price + "ש\"ח");
    $('#quantity').text("מספר כרטיסים: " + num);
    $('#total').text("סך הכל לתשלום: " + show.m_Price*num + "ש\"ח");
    $('#total').attr("totalAmount", show.m_Price*num);
    $('#showPicture').attr("src", show.m_PictureUrl);
    $('#ilovethis').text(show.m_Price);
    $('#ilovethis').attr("sellerType", show.m_Seller);
    $('#ilovethis').attr("buyRef", show.m_BuyRef);

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
            createBuyShowPage(show, i);
        }
    });
}

function performPurchase(){
    var actionType = "buyTicket";

    $.ajax({
        url: "SellTicket",
        type: 'POST',
        data: {
            "numOfTicketsToBuy": num,
            "showID" : id,
            "ActionType": actionType,
            "numOfTicketsToBuy" : num,
        },
        success: function (data) {
            var requestStatus = data[0];
            var  messageRequestStatus = data[1];
            showResponse(requestStatus, messageRequestStatus);

        }
    });
}

function showResponse(requestStatus, messageRequestStatus) {
    if (requestStatus == 6)
    {
        if (messageRequestStatus == 1)
        {
            openPopup("הרכישה התבצעה בהצלחה. מייל נשלח למוכר");
        }
        else if(messageRequestStatus == 2)
        {
            openPopup("הרכישה התבצעה בהצלחה. אך המייל לא נשלח למוכר");
        }
    }
    else if (requestStatus == 7)
    {
        openPopup("לא ניתן לבצע את הרכישה, אנא נסה מאוחר יותר");
    }
}

function openPopup(msg) {
    $("#message").html(msg);
    document.getElementById('myModal').style.display = "block";
}

function closePopup() {
    document.getElementById('myModal').style.display = "none";
}

window.onclick = function(event) {
    var modal = document.getElementById('myModal');
    if (event.target == modal) {
        modal.style.display = "none";
    }
    window.location.replace("myBoughtTickets.html");
}
