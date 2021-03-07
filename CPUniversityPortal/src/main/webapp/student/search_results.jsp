<!--Faculty Home-->
<%@ page import="java.util.*" %>
<%@ page import="com.classes.*" %>


<%	String userID = request.getParameter("id");
	String term = request.getParameter("term");
	String year = request.getParameter("year");
	String subject = request.getParameter("subj");
	String cName = request.getParameter("cName");
	String cID = request.getParameter("cID");
	
	subject.replace("+", " ");
	cName.replace("+", " ");
	cID.replace("+", " ");
	
	%>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>iAcademic | Faculty Portal</title>
    <link rel="icon" href="../images/iAcademic2.ico" type="image/icon type">
    <link rel="stylesheet" href="../CSS/styles.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css" integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ho+j7jyWK8fNQe+A12Hb8AhRq26LrZ/JpcUGGOn+Y7RsweNrtN/tE3MoK7ZeZDyx" crossorigin="anonymous"></script>
    
</head>
<body>
  <section id="nav">
    <nav class="navbar navbar-expand-lg navbar-light">
      <a class="navbar-brand" href="/logout"><img src="../images/iAcademic.ico" height="35" width="35"></a>
      <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNavDropdown" aria-controls="navbarNavDropdown" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse" id="navbarNavDropdown">
        <ul class="navbar-nav">
          <li class="nav-item"><a class="nav-link" href="home.jsp?id=<%=userID%>">Home <span class="sr-only">(current)</span></a></li>
          <li class="nav-item"><a class="nav-link" href="courseHistory.jsp?id=<%=userID%>">Course History <span class="sr-only"></span></a></li>
          <li class="nav-item"><a class="nav-link" href="/logout">Log Out<span class="sr-only"></span></a></li>
        </ul>
      </div>
    </nav>
  </section>
  <section id="features">
    <div style="margin-left: 1%;">
      <h2 class="display-4">Search Results</h2>
      <form action = "/enroll">
      <input type="hidden" name="id" value="<%=userID%>">
      <table id="display" class= "table table-hover">
      <tr>
          <th>Enroll?</th>
          <th>Course ID</th>
          <th>Title</th>
          <th>Instructor</th>
          <th>Start Time</th>
          <th>End Time</th>
          <th>Quarter</th>
          <th>Year</th>
      </tr>
      
<%
        DatabaseController dbc = new DatabaseController(true);
		User user = dbc.getStudent(userID);
        Vector<Course> results = dbc.getSearchResults(cID, cName, term, year, subject);
        for (int i = 0; i < results.size(); i++){ 
%>
    	<tr>
    		<td><input id="checkBox" type="checkbox" name="selectedCourses" value="<%=results.elementAt(i).courseID%>"></td>
    		  <td><%=results.elementAt(i).courseID%></td>
    		  <td><%=results.elementAt(i).title%></td>
    		  <td><%=results.elementAt(i).instructor%></td>
    		  <td><%=results.elementAt(i).startTime.toString()%></td>
    		  <td><%=results.elementAt(i).endTime.toString()%></td>
    		  <td><%=results.elementAt(i).quarterOffered%></td>
    		  <td><%=results.elementAt(i).yearOffered%></td>
    	</tr>
    		  
<%
    	  }
%>
  </table>
  <button id="drop" class="btn btn-secondary" type="submit">Add</button>
  </form>
    </div>
  </section>
</body>
</html>