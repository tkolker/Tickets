function createShowPage(show, i){
    var a, h1, span, img, h2first, h2second, h2third, divMain, p1, div1, a1, comboBox, p2;
    a = document.createElement('a');
    h1 = document.createElement('h1');
    span = document.createElement('span');
    img = document.createElement('img');
    h2first = document.createElement('h2');
    h2second = document.createElement('h2');
    div1 = document.createElement('div');
    p1 = document.createElement('p');
    a1 = document.createElement('a');
    p2 = document.createElement('p');
    comboBox = document.createElement('select');
    divMain = $('#preamble');

    a.setAttribute("id","showID");
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
    $(p1).text(show.m_About);
    //div1.setAttribute("class", "DivWithScroll");
    div1.setAttribute("class", "DivToScroll");
    div1.appendChild(p1);
    span.appendChild(div1);
    $(divMain).append(span);
    p2.setAttribute("id", "comboBoxText");
    $(p2).text("בחר כמות כרטיסים")
    comboBox.setAttribute("id", "comboBox")


    $('#showPicture').attr("src", show.m_PictureUrl);
    $('#ilovethis span').text(show.m_Price + "₪");
    $('#ilovethis').attr("sellerType", show.m_Seller);
    $('#ilovethis').attr("buyRef", show.m_BuyRef);

    var seller = show.m_Seller;

    if(seller == 0) {
        i =0;
        $(p2).hide();
        $(comboBox).hide();
    }

    if(i == 1){
        var i, option;
        for(i = 0; i < show.m_NumOfTickets; i++){
            option = document.createElement('option');
            $(option).attr("value", i+1);
            $(option).text(i+1);
            $(comboBox).append(option);
        }
        $(divMain).append(p2);
        $(divMain).append(comboBox);
    }
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
