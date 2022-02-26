<%@page import="edu.ucr.cs242.project.web.QueryUtil"%><%

    String query = ""+request.getParameter("query");
    
    // @todo - process query value
    
    String queryProcessed = QueryUtil.process(query);

%>
<html>
    
    <head>
        <title>searchFinish.jsp</title>
    </head>
    
    <body>
        
        <p><b>searchFinish.jsp</b></p>
        
        <p>Query Value: <%= queryProcessed %></p>
        
        <p><a href="searchStart.jsp">Back</a></p>
        
    </body>
    
</html>