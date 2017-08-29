var id;

$(document).ready(function (){
    id = getURLParameter('id');
    $('#ilovethis').on("click", redirect);
    getShow(id, 0);
});

function getURLParameter(name) {
    return decodeURIComponent((new RegExp('[?|&]' + name + '=' + '([^&;]+?)(&|#|;|$)').exec(location.search) || [null, ''])[1].replace(/\+/g, '%20')) || null;
}

function redirect(){
    window.location.replace("login.html?goto=showPageSignedUser.html?id=" + id);
}

