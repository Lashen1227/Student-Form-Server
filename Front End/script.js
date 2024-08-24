// Save Button
document.getElementById("btnSave").onclick = function () {
    const myHeaders = new Headers();
    myHeaders.append("Content-Type", "application/json");

    const raw = JSON.stringify({
        "id": document.getElementById("txtStudentId").value,
        "name": document.getElementById("txtStudentName").value,
        "address": document.getElementById("txtStudentAddress").value,
        "gender": document.getElementById("txtStudentGender").value,
        "contactNumber": document.getElementById("txtContactNumber").value,
        "age": document.getElementById("txtStudentAge").value
    });

    const requestOptions = {
        method: "POST",
        headers: myHeaders,
        body: raw,
        redirect: "follow"
    };

    fetch("http://localhost:8080/students", requestOptions)
        .then((response) => response.text())
        .then((result) => console.log(result))
        .catch((error) => console.error(error));
    loadTableData();
}

// Fill fields when user press enter key and if student no found display alert
document.getElementById("txtStudentId").onkeyup = function (event) {
    if (event.keyCode === 13) {
        const requestOptions = {
            method: "GET",
            redirect: "follow"
        };
        let id = document.getElementById("txtStudentId").value;
        fetch(`http://localhost:8080/students?id=${id}`, requestOptions)
            .then((response) => {
                if(response.status === 200){
                    return response.json();
                }
                return response.text();
             })
            .then((result) => {
                console.log(result)
                if(result !== "Student Not Found"){
                    document.getElementById("txtStudentName").value = result.name;
                    document.getElementById("txtStudentAddress").value = result.address;
                    document.getElementById("txtStudentGender").value = result.gender;
                    document.getElementById("txtContactNumber").value = result.contactNumber;
                    document.getElementById("txtStudentAge").value = result.age;
                }else{
                    alert("Student Not Found");
                }
            })
            .catch((error) => console.error(error));
    }
}

// Clear Button
document.getElementById('btnClear').addEventListener('click', function() {
    document.getElementById('txtStudentId').value = '';
    document.getElementById('txtStudentName').value = '';
    document.getElementById('txtStudentAddress').value = '';
    document.getElementById('txtStudentGender').value = '';
    document.getElementById('txtContactNumber').value = '';
    document.getElementById('txtStudentAge').value = '';
});

// Update Button
document.getElementById('btnUpdate').addEventListener('click', function() {
    const myHeaders = new Headers();
    myHeaders.append("Content-Type", "application/json");

    const raw = JSON.stringify({
        "id": document.getElementById("txtStudentId").value,
        "name": document.getElementById("txtStudentName").value,
        "address": document.getElementById("txtStudentAddress").value,
        "gender": document.getElementById("txtStudentGender").value,
        "contactNumber": document.getElementById("txtContactNumber").value,
        "age": document.getElementById("txtStudentAge").value
    });

    const requestOptions = {
        method: "PUT",
        headers: myHeaders,
        body: raw,
        redirect: "follow"
    };

    fetch("http://localhost:8080/students", requestOptions)
        .then((response) => response.text())
        .then((result) => console.log(result))
        .catch((error) => console.error(error));
    loadTableData();
});

// Delete Button
document.getElementById('btnDelete').addEventListener('click', function() {
    const requestOptions = {
        method: "DELETE",
        redirect: "follow"
    };

    let id = document.getElementById("txtStudentId").value;
    fetch(`http://localhost:8080/students?id=${id}`, requestOptions)
        .then((response) => response.text())
        .then((result) => console.log(result))
        .catch((error) => console.error(error));
    loadTableData();
    clearFields();
});

// Load table data
function loadTableData(){
    const requestOptions = {
    method: "GET",
    redirect: "follow"
    };

    fetch("http://localhost:8080/students?option=all", requestOptions)
    .then((response) => response.json())
    .then((result) => {
        //access the table body
        let tbody = document.getElementById("tbody");
        //clear the table
        tbody.innerHTML='';

        for (let i = 0; i < result.length; i++) {
            //create a table row using dom
            let tr = document.createElement("tr");
            //create table data using result array
            tr.innerHTML = `
                <td>${result[i].id}</td>
                <td>${result[i].name}</td>
                <td>${result[i].address}</td>
                <td>${result[i].gender}</td>
                <td>${result[i].contactNumber}</td>
                <td>${result[i].age}</td>
            `;
            //append the newly created table row to table body
            tbody.appendChild(tr);
        }
    })
    .catch((error) => console.error(error));
}

// Load table data when page load
loadTableData();

// Function to clear fields
function clearFields(){
    document.getElementById("txtStudentId").value = "";
    document.getElementById("txtStudentName").value = "";
    document.getElementById("txtStudentAddress").value = "";
    document.getElementById("txtStudentGender").value = "";
    document.getElementById("txtContactNumber").value = "";
    document.getElementById("txtStudentAge").value = "";
}
