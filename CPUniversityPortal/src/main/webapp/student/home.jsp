<!--Student Home-->
<!DOCTYPE html>
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
          <li class="nav-item"><a class="nav-link" href="home.html">Home <span class="sr-only">(current)</span></a></li>
          <li class="nav-item"><a class="nav-link" href="#">Course History <span class="sr-only"></span></a></li>
          <li class="nav-item"><a class="nav-link" href="/logout">Log Out<span class="sr-only"></span></a></li>
        </ul>
      </div>
    </nav>
  </section>
  <section id="features">
    <h1 style="margin-left: 2%" class="display-4">Class Enrollment</h1><br>

    <div id="main" class="row">
      <div style="margin-left: 1%" id="left" class="col-3">
        <div class="form-group col-md-8">
          <label for="exampleFormControlSelect1">Choose Term</label>
          <select class="form-control" id="selectTerm">
          <option>Fall</option>
          <option>Winter</option>
          <option>Spring</option>
          <option>Summer</option>
        </select>
        </div>
        <div class="form-group col-md-8">
          <label for="inputZip">Year</label>
          <input type="text" class="form-control" id="inputYear">
        </div>
        <div class="form-group col-md-8">
          <label for="inputZip">Subject</label>
          <input type="text" class="form-control" id="subj">
        </div>
        <div class="form-group col-md-8">
          <label for="inputZip">Course</label>
          <input type="text" class="form-control" id="class">
        </div>
        <button style="margin-left: 3%;" id="entry" class="btn btn-secondary" type="submit">Add To Cart</button>
        </div>
        <div id="right" class="col-7">
          <h3>Shopping Cart</h3>
          <table id="display" class= "table table-hover">
            <tr>
                <th>Select</th>
                <th>Department</th>
                <th>Course ID</th>
                <th>Term</th>
                <!--<th>Course Description</th>
                <th>Instructor</th>
                <th>Time</th>
                <th>Location</th>-->
            </tr>
        </table>
        <button style="margin-left: 1%;" id="enroll" class="btn btn-secondary" type="submit">Enroll</button>
        </div>
    </div>
    <br>
    <div style="margin-left: 1%;">
      <h3>Class Schedule</h3>
      <table id="display" class= "table table-hover">
      <tr>
          <th>Select</th>
          <th>Department</th>
          <th>Course ID</th>
          <th>Term</th>
          <th>Course Description</th>
          <th>Instructor</th>
          <th>Time</th>
          <th>Location</th>
      </tr>
  </table>
  <button id="enroll" class="btn btn-secondary" type="submit">Drop</button>
    </div>
  </section>
  <script src="../JS/script.js"></script>
</body>
</html>