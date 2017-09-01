var result =[];

function runBravoCrawler()
{
    $.ajax({
        type: 'POST',
        url: "https://api.apifier.com/v1/M6RWTHNRAEf9LEjnM/crawlers/Bravo/execute?token=8nbdTgSeXCkfk5tqyTZQeJ3hK",
        async : false,
        success: function(data){
            var urlResults = data.resultsUrl;
            getData(urlResults);
        }
    });

}

function getData(urlResults) {
    var i = 0;
    $.ajax({
        type: 'GET',
        url: urlResults,
        datatype: "jsonp",
        async : false,
        success: function(data){
            while(data[i]!= null) {
                writeToDB(data[i].pageFunctionResult);
                i++;
            }
        }
    });

}

function writeToDB(myData){
    $.ajax({
        type: 'POST',
        url: "Crawlers",
        async : false,
        data: {
            "crawlerShows": JSON.stringify(myData),
            "ActionType": "crawlerBravoUpdate",
        },
        success: function () {
            var i = 0;
        }
    });
}

/*function runBravoCrawler() {
    // called on every page the crawler visits, use it to extract data from it
    var bravo = document.createElement('div');
    $.ajax({
        url: 'http://www.bravo.org.il/nextweek?p=1#l/',
        datatype: 'html',
        async: false,
        crossDomain : true,
        //origin:
        success: function (data) {
            $(bravo).html(data);
            getData(bravo)
            writeToDB();
        }
    });
}*/

/*
function getData(bravo) {
    var img, name, about, date, buyRef;

    ($(bravo).find(".list").find(".announce-list").find("li")).each(function(){
        buyRef = bravo + $(this).find(".list-description-block").find("a").attr("href");

        getSubPageData(buyRef, this);
    });
}

function getSubPageData(buyRef, page){
    var location, price, myData;

    myData = document.createElement('div');

    $.ajax({
        url: buyRef,
        dataType: 'html',
        async: false,
        success: function(data){
            $(myData).html(data);
            location = $(myData).find("table.tbl.tbl-seance").find("tr:eq(1)").find("td:eq(2)").find("a").text();
            price = $(myData).find(".seancePrice").find("span").first().text();

            pushToResult(page, price, location, buyRef);
        },
    });
}

function pushToResult(page, price, location, buyRef){
    var temp = [];
    temp = $(page).find(".list-img-block").find("img").attr("srcset").split(" ");
    result.push({
        m_ShowName: $(page).find(".list-description-block").find("a").first().text(),
        m_DateStr: $(page).find(".list-description-block").find("dl").find("dd").find("time").attr("datetime"),
        m_BuyRef: buyRef,
        m_Location: location,
        m_PictureUrl: temp[0],
        m_About: $(page).find(".list-description-block").find("p").text(),
        m_Price: price,
    });
}*/
