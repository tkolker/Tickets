var id;

$(document).ready(function (){
    id = getURLParameter('id');
    $('#ilovethis').on("click", redirect);
    getShow(id, 0);
});

function getURLParameter(name) {
    return decodeURIComponent((new RegExp('[?|&]' + name + '=' + '([^&;]+?)(&|#|;|$)').exec(location.search) || [null, ''])[1].replace(/\+/g, '%20')) || null;
}

//TODO: rmemeber where we were, after login return to same page (add param for that. need to check if is  a problam when getting empty param)
function redirect(){
    window.location.replace("login.html");
}

