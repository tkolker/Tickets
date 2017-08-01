function createShowPage(show){
    $('#showID').text(show.m_ShowID);
    $('#showName').text(show.m_ShowName);
    $('#showPrice').text(show.m_Price);
    $('#showDate').text(show.m_Date);
    $('#showLocation').text(show.m_Location);
    $('#showAbout').text(show.m_About);
    $('#showPicture').attr("src", show.m_PictureUrl);
}

