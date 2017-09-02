var id,d,s,sh,m;

$(document).ready(function (){
    id = getURLParameter('id');
    d = getURLParameter('d');
    s = getURLParameter('s');
    sh = getURLParameter('sh');
    m = getURLParameter('m');
    buildMsg();
    $('#sendMsgButton').on("click", gotoSendMessage);
    $('#closeMsgButton').on("click", closeSendMsg);
});

function buildMsg(){
    $('#from').text(s);
    $('#date').text(d);
    $('#show').text(sh);
    $('#msg').text(m);
}

function getURLParameter(name) {
    return decodeURIComponent((new RegExp('[?|&]' + name + '=' + '([^&;]+?)(&|#|;|$)').exec(location.search) || [null, ''])[1].replace(/\+/g, '%20')) || null;
}

function gotoSendMessage(){
    window.location.replace("sendMessage.html?id=" + id);
}

function closeSendMsg(){
    window.close();
}