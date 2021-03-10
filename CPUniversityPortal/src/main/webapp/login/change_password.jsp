  <!--Faculty Login-->
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>iAcademic | Login</title>
    <link rel="icon" href="../images/iAcademic2.ico" type="image/icon type">
    <link rel="stylesheet" href="../CSS/styles.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css" integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ho+j7jyWK8fNQe+A12Hb8AhRq26LrZ/JpcUGGOn+Y7RsweNrtN/tE3MoK7ZeZDyx" crossorigin="anonymous"></script>
    
</head>
<body class="text-center">
  <!--Navigation Bar-->
  <section id="nav">
    <nav class="navbar navbar-expand-lg navbar-light">
      <a class="navbar-brand" href="../index.html"><img src="../images/iAcademic.ico" height="35" width="35"></a>
      <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNavDropdown" aria-controls="navbarNavDropdown" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse" id="navbarNavDropdown">
        <ul class="navbar-nav">
          <li class="nav-item"><a class="nav-link" href="../index.html">Home <span class="sr-only">(current)</span></a></li>
          <li class="nav-item dropdown"><a class="nav-link dropdown-toggle" href="#" id="navbarDropdownMenuLink" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Choose platform</a>
            <div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
              <a class="dropdown-item" href="student.html">Student</a>
              <a class="dropdown-item" href="faculty.html">Faculty</a>
            </div>
          </li>
        </ul>
      </div>
    </nav>
    <form action = "/change_password">
      <div style="margin-left: 1%" id="left" class="col-3">
        <div class="form-group col-md-8">
          <label for="inputZip">User ID</label>
          <input type="text" class="form-control" id="inputID" name="id">
        </div>
        <div class="form-group col-md-8">
          <label for="inputZip">Old Password</label>
          <input type="password" id="password" name = old class="form-control" placeholder="Password" required autofocus><br>
        </div>
        <div class="form-group col-md-8">
          <label for="inputZip">New Password</label>
          <input type="password" id="password" name = new class="form-control" placeholder="Password" required autofocus><br>
        </div>
        </div>
        </form>
        </section>
        </body>