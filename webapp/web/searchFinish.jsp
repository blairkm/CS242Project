<%@page import="edu.ucr.cs242.project.web.QueryUtil,edu.ucr.cs242.project.test.GeoTest,edu.ucr.cs242.project.util.GeocodingUtil"%><%

    String textQuery = ""+request.getParameter("query");
    String locationQuery = ""+request.getParameter("query2");
    String radiusQuery = ""+request.getParameter("query3");
    
    String displayResult = "";
    
    String queryProcessed = "";
    if (!textQuery.isEmpty()) {
        queryProcessed = textQuery;
        displayResult = QueryUtil.process(textQuery);
    } else if (!locationQuery.isEmpty() && !radiusQuery.isEmpty()) {
        queryProcessed = locationQuery + " (" + radiusQuery + " miles)";
        String latitude = "";
        String longitude = "";
        String geocodingResponse = GeocodingUtil.request(locationQuery.trim());
        latitude = GeocodingUtil.getLatitude(geocodingResponse);
        longitude = GeocodingUtil.getLongitude(geocodingResponse);
        displayResult = GeoTest.performQuery(latitude, longitude, Float.parseFloat(""+radiusQuery.trim()));
    }

%>
<html>
    
    <head>
        <title>searchFinish.jsp</title>
    </head>
    
    <body>
        
        <p><b>searchFinish.jsp</b></p>
        
        <p>Query Value: <%= queryProcessed %></p>
        
        <%= displayResult %>
        
        <p><a href="searchStart.jsp">Back</a></p>
        
    </body>
    
</html>