var showId,date,sender,show,msg, senderId;

$(document).ready(function (){
    showId = getURLParameter('id');
    senderId = getURLParameter('sid');
    date = getURLParameter('d');
    sender = getURLParameter('s');
    show = getURLParameter('sh');
    msg = getURLParameter('m');
    buildMsg();
    $('#sendMsgButton').on("click", gotoSendMessage);
    $('#closeMsgButton').on("click", closeSendMsg);
});

function buildMsg(){
    $('#from').text(sender);
    $('#date').text(date);
    $('#show').text(show);
    $('#msg').text(msg);
}

function getURLParameter(name) {
    return decodeURIComponent((new RegExp('[?|&]' + name + '=' + '([^&;]+?)(&|#|;|$)').exec(location.search) || [null, ''])[1].replace(/\+/g, '%20')) || null;
}

function gotoSendMessage(){
    window.location.replace("replyMessage.html?id=" + showId + "&sender=" + senderId);
}

function closeSendMsg(){
    window.close();
}

window.onresize = function()
{
    window.resizeTo(450,300);
}