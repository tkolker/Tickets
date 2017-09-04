var removeShowRes;

$(document).ready(function (){
    var id = getURLParameter('id');
    getMySellShow(id, 0);

});

function passId(){
    var id = $('#showID').attr("showNum");
    window.location.replace("updateShow.html?id="+id);
}

function getURLParameter(name) {
    return decodeURIComponent((new RegExp('[?|&]' + name + '=' + '([^&;]+?)(&|#|;|$)').exec(location.search) || [null, ''])[1].replace(/\+/g, '%20')) || null;
}

function deleteShow(){
    var actionType = "deleteShow";
    var showID = $('#showID').attr("showNum");

    $.ajax({
        type: "POST",
        url: "SellTicket",
        data: {
            "ActionType": actionType,
            "showID": showID,
        },
        success: function (result) {
            removeShowRes = result[0];
            var show = result[1];
            showResponse(removeShowRes, show);
        }
    });
}


function showResponse(i, show) {
    if (i === "5") {
        openPopup("ההופעה של "+show+ " נמחקה בהצלחה");

    }
    else if (i === "4") {
        openPopup("אופס! קרתה תקלה. ההופעה לא נמחקה. נסה שוב");
    }
}

function openPopup(msg) {
    $("#message").html(msg);
    document.getElementById('myModal').style.display = "block";
}

function closePopup() {
    $('#username').reset();
    $('#userPassword').reset();
    document.getElementById('myModal').style.display = "none";
}

window.onclick = function(event) {
    var modal = document.getElementById('myModal');
    if(event.srcElement.getAttribute('id') != "buttonUpdateShow") {
        if (event.target == modal) {
            modal.style.display = "none";
        }
        if (removeShowRes != 4) {
            window.location.replace("myShows.html");
        }
    }
}

function getMySellShow(id, i){
    var actionType = "getShow";

    $.ajax({
        url: "SellTicket",
        data: {
            "showID" : id,
            "ActionType": actionType,
        },
        success: function (show) {
            createMySellShow(show, i);
        }
    });
}

function createMySellShow(show, i) {
    var p, h1, span1, span2, p2, p3, p4, divMain, p1, div1, button1, button2;
    p = document.createElement('p');
    h1 = document.createElement('h1');
    p1 = document.createElement('p');
    span1 = document.createElement('span');
    span2 = document.createElement('span');
    p2 = document.createElement('p');
    p3 = document.createElement('p');
    p4 = document.createElement('p');
    div1 = document.createElement('div');
    button1 = document.createElement('button');
    button2 = document.createElement('button');

    divMain = $('#preamble');

    p.setAttribute("id", "showID");
    p.setAttribute("showNum", show.m_ShowID);
    $(divMain).append(p);
    h1.setAttribute("id", "showName");
    $(h1).text(show.m_ShowName);
    $(divMain).append(h1);
    p2.setAttribute("id", "showDate");
    $(p2).text(show.m_Date);
    $(divMain).append(p2);
    p3.setAttribute("id", "showLocation");
    $(p3).text(show.m_Location);
    $(divMain).append(p3);
    $(divMain).append("<br>");
    div1.setAttribute("class", "DivToScroll");
    $(p4).text(show.m_About);
    div1.appendChild(p4);
    $(divMain).append(div1);
    $(divMain).append("<br><br>");
    button1.setAttribute("class","button");
    button1.setAttribute("id", "buttonUpdateShow");
    button1.setAttribute("style", "width: 110");
    $(button1).text("עדכון");
    span1.appendChild(button1);
    $(divMain).append(span1);
    button2.setAttribute("class","button");
    button2.setAttribute("id", "buttonDeleteShow");
    button2.setAttribute("style", "width: 110");
    $(button2).text("מחיקה");
    span2.appendChild(button2);
    $(divMain).append(span2);


    $(button2).on("click", deleteShow);
    $(button1).on("click", passId);
    $('#showPicture').attr("src", show.m_PictureUrl);
    $('#priceStatic span').text(show.m_Price + "₪");
    $('#priceStatic').attr("sellerType", show.m_Seller);
    $('#priceStatic').attr("buyRef", show.m_BuyRef);
}
