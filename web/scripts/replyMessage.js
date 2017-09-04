var id, receiver;

$(document).ready(function (){
    id = getURLParameter('id');
    receiver = getURLParameter('sender');
    $('#sendMsgButton').on("click", sendMessage);
    $('#closeMsgButton').on("click", closeSendMsg);
});

function getURLParameter(name) {
    return decodeURIComponent((new RegExp('[?|&]' + name + '=' + '([^&;]+?)(&|#|;|$)').exec(location.search) || [null, ''])[1].replace(/\+/g, '%20')) || null;
}

function sendMessage(){
    var msg = $('#message').val();
    var actionType = "replyMsg";

    $.ajax({
        url: "inbox",
        type: 'POST',
        data: {
            "ActionType": actionType,
            "message": msg,
            "showID": id,
            "msgReceiver": receiver,
        },
        success: function () {
            $('#message').val("הודעה נשלחה בהצלחה");
            $('#message').attr('readonly','readonly');
            $('#message').attr("class","disable");
            $('#sendMsgButton').prop("disabled",true);
        }
    });
}

function closeSendMsg(){
    window.close();
}