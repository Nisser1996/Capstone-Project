<%@ page import="java.util.*" %>>
<%@ page import="com.classes.*" %>>


	<%	
	String userID = request.getParameter("id");
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
                    <li class="nav-item"><a class="nav-link" href="generate_login.jsp?id=<%=userID%>">Student Password Override<span class="sr-only"></span></a></li>
          <li class="nav-item"><a class="nav-link" href="/logout">Log Out<span class="sr-only"></span></a></li>
        </ul>
      </div>
    </nav>
  </section>
  <section id="features">
  <form action= "/override_password">
        <div style="margin-left: 1%" id="left" class="col-3">
        <div class="form-group col-md-8">
          <label for="inputStudent">Student ID</label>
          <input type="text" class="form-control" id="inputStudent" name="sID">
          <label for="inputPassword">New Password</label>
          <input type="text" class="form-control" id="inputPassword" name="sPassword">
          <input type="hidden" value="<%=userID%>" name="id">
        </div>
        <button style="margin-left: 3%;" id="entry" class="btn btn-secondary" type="submit">Change Student Password</button>
        </div>
        </form>
  </section>
</body>