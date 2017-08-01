$(document).ready(function (){
    $('#profileSellButton').on("click", getToSellTickets);
    $('#profileBuyButton').on("click", getBuyedTickets);
});

function getToSellTickets(){
    window.location.replace("mySellShow.html");
}

function getBuyedTickets(){
}


function openPopup(msg) {
    $("#message").html(msg);
    $("#popup").show();
}

function closePopup() {
    $("#popup").hide();
}

