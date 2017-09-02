var id;

$(document).ready(function (){
    id = getURLParameter('id');
    getShow(id, 1);
    $('#ilovethis').on("click", gotoBuyPage);
    $('#contactSeller').on("click", gotoSendMsg);
});

function gotoSendMsg() {
    window.open("sendMessage.html?id=" + id, "Contact seller", 'width=450, height=300');
}


function getURLParameter(name) {
    return decodeURIComponent((new RegExp('[?|&]' + name + '=' + '([^&;]+?)(&|#|;|$)').exec(location.search) || [null, ''])[1].replace(/\+/g, '%20')) || null;
}


function gotoBuyPage(){
    var id = $('#showID').attr("showNum");
    var buyRef = $('#ilovethis').attr("buyRef");
    var sellerType = parseInt($('#ilovethis').attr("sellerType"));

    if(sellerType) {
        var num = $('#comboBox').val();
        window.location.replace(buyRef + "&quantity=" + num);
    }
    else{
        window.open(buyRef);
    }
}


