var row = 1;

var entry = document.getElementById("entry");
entry.addEventListener("click", displayDetails);

function displayDetails() {
    var ck = '<input id="checkBox" type="checkbox">';
    var subj = document.getElementById("subj").value;
    var classs = document.getElementById("class").value;
    var term = document.getElementById("selectTerm").value;
    var year = document.getElementById("inputYear").value;

    if(!subj || !classs){
        alert("Please fill the boxes to add a class");
        return;
    }
    var display = document.getElementById("display");

    var newRow = display.insertRow(row);

    var cell1 = newRow.insertCell(0);
    var cell2 = newRow.insertCell(1);
    var cell3 = newRow.insertCell(2);
    var cell4 = newRow.insertCell(3);
    var cell4 = newRow.insertCell(3);
    //cell4.appendChild(year);
    
    cell1.innerHTML = ck;
    cell2.innerHTML = subj;
    cell3.innerHTML = classs;
    cell4.innerHTML = term;
    cell5.innerHTML = year;
    
    row++;

    }