var result =[];



function runBravoCrawler()
{
        var request = new XMLHttpRequest();

        request.open('GET', 'https://api.apifier.com/v1/wgTzvTpaaZNctRxHQ/crawlers/Bravo/lastExec?token=jdx5QZkCk7PcZ2WtL4Y6TNhPX');

        request.onreadystatechange = function () {
            if (this.readyState === 4) {
                var responseURL = this.responseURL;

                $.ajax({
                    url: responseURL,
                    async: false,
                    success: function (data) {
                        var urlResults = data.resultsUrl;
                        getBravoData(urlResults);
                    }
                });
            }
        };

        request.send();
}

function getBravoData(urlResults) {
    var i = 0;
    $.ajax({
        type: 'GET',
        url: urlResults,
        datatype: "jsonp",
        async : false,
        success: function(data){
            while(data[i]!= null) {
                writeBravoToDB(data[i].pageFunctionResult);
                i++;
            }
        }
    });

}

function writeBravoToDB(myData){
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
