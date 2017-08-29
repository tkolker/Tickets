var result =[];

function writeToDB(){
    $.ajax({
        type: 'POST',
        url: "SellTicket",
        async : false,
        data: {
            "crawlerShows": JSON.stringify(result),
            "ActionType": "crawlerUpdate",
        },
        success: function () {
            var i = 0;
        }
    });
}

function runCrawler() {
    var zappa = document.createElement("div");

    $.ajax({
        url: "https://www.zappa-club.co.il/",
        datatype: 'html',
        async: false,
        success : function(data){
            $(zappa).html(data);
            getData(zappa)
            writeToDB();
        }
    });
}


function getData(zappa){

    var url;


    ($(zappa).find(".event.griditem.show_info_icon")).each(function(){
        url = $(this).find(".eventAction").find(".buyTicket").attr("href");

        getSubPageData(url, this);
    });

}

function getSubPageData(url, page){
    var about, myData, img, price;
    var temp = [];

    //var url
    myData = document.createElement('div');

    $.ajax({
        url: url,
        dataType: 'html',
        async: false,
        success: function(data){
            $(myData).html(data);
            about = $(myData).find(".details_txt").find("p").text();
            img = $(myData).find('.cover').css("background-image");
            temp = img.split('(');
            temp = temp[1].split(')');
            img = temp[0];

            price = 0;
            ($(myData).find('.detals_table').find("div")).each(function(){
                if($(this).find(".label").text().indexOf("מחיר")!=-1){
                    price = parseInt($(this).find(".detail").text());
                }
                if($(this).find(".label").text().indexOf("מכירה")!=-1){
                    price = parseInt($(this).find(".detail").text());
                }
            });

            pushToResult(page, img, price, about, url);
        },
    });
}


function pushToResult( page, img, price, about, url){
    result.push({
        m_ShowName: $(page).find(".eventAction").find("h2").text(),
        m_DateStr: $(page).find(".eventBox").find(".event_data").find(".row").find("meta").attr("content"),
        m_BuyRef: url,
        m_Location: $(page).find(".eventBox").find(".event_data").find("div").find("meta").attr("content"),
        m_PictureUrl: img,
        m_About: about,
        m_Price: price,
    });
}