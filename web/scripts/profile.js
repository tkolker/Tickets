$(document).ready(function (){
    $('#profileSellButton').on("click", getToSellTickets);
    $('#profileBuyButton').on("click", getBuyedTickets);
    $('#profileFavsButton').on("click", getLikedTickets);
});

function getToSellTickets(){
    window.location.replace("mySellShow.html");
}

function getBuyedTickets(){
}

function getLikedTickets(){
}

