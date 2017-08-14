var id;

$(document).ready(function (){
    id = getURLParameter('id');
    getShow(id, 1);
    $('#ilovethis').on("click", gotoBuyPage);
});

function getURLParameter(name) {
    return decodeURIComponent((new RegExp('[?|&]' + name + '=' + '([^&;]+?)(&|#|;|$)').exec(location.search) || [null, ''])[1].replace(/\+/g, '%20')) || null;
}


function gotoBuyPage(){
    var id = $('#showID').attr("showNum");
    window.location.replace("buyPage.html?id=" + id);
}


