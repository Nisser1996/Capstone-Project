<%@ page import="java.util.*" %>>
<%@ page import="com.classes.*" %>>


	<%	
	String userID = request.getParameter("id");
	String course = request.getParameter("course");
	
	course = course.replace('+', ' ');
	%>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>iAcademic | Student Portal</title>
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
          <li class="nav-item"><a class="nav-link" href="class_select.jsp?id=<%=userID%>&action=a">Add Student<span class="sr-only"></span></a></li>
          <li class="nav-item"><a class="nav-link" href="class_select.jsp?id=<%=userID%>&action=d">Drop Student<span class="sr-only"></span></a></li>
          <li class="nav-item"><a class="nav-link" href="class_select.jsp?id=<%=userID%>&action=g">Grade Submission<span class="sr-only"></span></a></li>
          <li class="nav-item"><a class="nav-link" href="/logout">Log Out<span class="sr-only"></span></a></li>
        </ul>
      </div>
    </nav>
  </section>
  <section id="features">
    <div style="margin-left: 1%;">
      <h2 class="display-4">Students in <%=course%></h2>
      <form action="/drop_student">
      <input type="hidden" name="id" value="<%=userID%>">
      <input type="hidden" name="course" value="<%=course%>">
      <table id="display" class= "table table-hover">
      <tr>
          <th>Student</th>
          <th>Drop?</th>
      </tr>
      
		<%
        DatabaseController dbc = new DatabaseController(true);
		Vector<User> students = dbc.getStudentsInCourse(course);
        for (int i = 0; i < students.size(); i++){ 
		%>
    	<tr>
    		  <td><%=students.elementAt(i).toString()%></td>
    		  <td><input id="checkBox" type="checkbox" name="selectedStudents" value="<%=students.elementAt(i).userID%>"></td>
    		  
		<%
    	  }
		%>
  	</table>
  		<button id="drop" class="btn btn-secondary" type="submit">Drop</button>
		</form>
    </div>
  </section>
</body>