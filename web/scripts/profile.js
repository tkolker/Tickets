$(document).ready(function (){
    $('#profileSellButton').on("click", getToSellTickets);
    $('#profileBuyButton').on("click", getBuyedTickets);
});

function getToSellTickets(){
    window.location.replace("myShows.html");
}

function getBuyedTickets(){
    window.location.replace("myBoughtTickets.html");
}


function openPopup(msg) {
    $("#message").html(msg);
    $("#popup").show();
}

function closePopup() {
    window.hide();
}
