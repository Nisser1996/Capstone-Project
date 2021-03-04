<!--Student Home-->
<%@ page import="java.util.*" %>
<%@ page import="com.classes.*" %>


<%String userID = request.getParameter("id");
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
          <li class="nav-item"><a class="nav-link" href="courseHistory.jsp?id=<%=userID%>">Course History <span class="sr-only"></span></a></li>
          <li class="nav-item"><a class="nav-link" href="/logout">Log Out<span class="sr-only"></span></a></li>
        </ul>
      </div>
    </nav>
  </section>
  <section id="features">

    <div style="margin-left: 1%;">
      <h3>Course History</h3>
      <table id="display" class= "table table-hover">
      <tr>
          <th>Course ID</th>
          <th>Title</th>
          <th>Quarter Taken</th>
          <th>Year Taken</th>
          <th>Grade</th>

      </tr>
      
<%
        DatabaseController dbc = new DatabaseController(true);
        Vector<Course> schedule = dbc.getStudentHistory(userID);
        float gpa = 0;
        int count = 0;
        for (int i = 0; i < schedule.size(); i++){ 
%>
    	<tr>
    		  <td><%=schedule.elementAt(i).courseID%></td>
    		  <td><%=schedule.elementAt(i).title%></td>
    		  <td><%=schedule.elementAt(i).quarterOffered%></td>
    		  <td><%=schedule.elementAt(i).yearOffered%></td>
    		  <td><%=schedule.elementAt(i).grade%></td>
    	</tr>
    		  
<%
			switch (schedule.elementAt(i).grade){
			case "A+":
				gpa += 4;
				count++;
				break;
			case "A":
				gpa += 4;
				count++;
				break;
			case "A-":
				gpa += 3.7;
				count++;
				break;
			case "B+":
				gpa += 3.3;
				count++;
				break;
			case "B":
				gpa += 3;
				count++;
				break;
			case "B-":
				gpa += 2.7;
				count++;
				break;
			case "C+":
				gpa += 2.3;
				count++;	
				break;
			case "C":
				gpa += 2.0;
				count++;
				break;
			case "C-":
				gpa += 1.7;
				count++;
				break;
			case "D+":
				gpa += 1.3;
				count++;
				break;
			case "D":
				gpa += 1;
				count++;
				break;
			case "D-":
				gpa += .7;
				count++;
				break;
			case "F":
				count++;
				break;
			default:
			}
    	  }
        
        gpa = gpa/count;
%>
  </table>
  
  <p>Your GPA is <%=gpa%>.</p>
  </div>
  
  </section>
  <script src="../JS/script.js"></script>
</body>
</html>