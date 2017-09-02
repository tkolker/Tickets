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


function createShowPage(show, i){
    var a, h1, span, img, h2first, h2second, h2third, divMain, p1, div1, a1, h2four, h2five;
    a = document.createElement('a');
    h1 = document.createElement('h1');
    span = document.createElement('span');
    img = document.createElement('img');
    h2first = document.createElement('h4');
    h2second = document.createElement('h4');
    h2third = document.createElement('h4');
    h2four = document.createElement('h4');
    h2five = document.createElement('h4');
    div1 = document.createElement('div');
    p1 = document.createElement('p');
    a1 = document.createElement('a');
    divMain = $('#preamble');

    a.setAttribute("showNum", show.m_ShowID);
    $(divMain).append(a);
    h1.setAttribute("id", "showName");
    $(h1).text(show.m_ShowName);
    $(divMain).append(h1);

    h2first.setAttribute("id", "showDate");
    $(h2first).text(show.m_Date);
    span.appendChild(h2first);
    h2second.setAttribute("id", "showLocation");
    $(h2second).text(show.m_Location);
    span.appendChild(h2second);
    h2third.setAttribute("id", "price");
    $(h2third).text("מחיר כרטיס: " + show.m_Price + "₪");
    span.appendChild(h2third);
    h2four.setAttribute("id", "numOfTickets");
    $(h2four).text("מספר כרטיסים שנרכשו: " + show.m_NumOfTickets);
    span.appendChild(h2four);
    h2five.setAttribute("id", "total");
    $(h2five).text("סך הכל שולם: " + show.m_NumOfTickets * show.m_Price + "₪");
    span.appendChild(h2five);
    div1.setAttribute("class", "DivToScroll");
    $(p1).text(show.m_About);
    div1.appendChild(p1);
    span.appendChild(div1);
    $(divMain).append(span);

    $('#showPicture').attr("src", show.m_PictureUrl);
    $('#ilovethis span').text(show.m_Price + "₪");
    $('#ilovethis').attr("sellerType", show.m_Seller);
    $('#ilovethis').attr("buyRef", show.m_BuyRef);
}

function getShow(id, i){
    var actionType = "getShow";

    $.ajax({
        url: "SellTicket",
        data: {
            "showID" : id,
            "ActionType": actionType,
        },
        success: function (show) {
            createShowPage(show, i);
        }
    });
}

