var id;
var num;
var price;

$(document).ready(function (){
    $('#paypal-button').on("click", performPurchase);
    id = getURLParameter('id');
    num = getURLParameter('quantity');
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
            "showID" : id,
            "ActionType": actionType,
            "numOfTicketsToBuy" : num,
        },
        success: window.location.replace("myBoughtTickets.html")

    });
}