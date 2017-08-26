function runCrawler(){
    $.ajax({
        type: 'POST',
        url: "https://api.apifier.com/v1/wgTzvTpaaZNctRxHQ/crawlers/Zappa-Tickets/execute?token=LxjPfmD8wpfNbbxGfNvn8PKst",
        async : false,
        success: function (data) {
            writeToDB(data.resultsUrl);
        }
    });
}


function writeToDB(data){
    $.ajax({
        type: 'POST',
        url: "SellTicket",
        async : false,
        data: {
            "crawlerShows": data,
            "ActionType": "crawlerUpdate",
        },
        success: function () {
            var i = 0;
        }
    });
}