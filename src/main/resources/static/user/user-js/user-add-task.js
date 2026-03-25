console.log("JS file successfully load ho gayi!");

/* page load */

window.onload = function () {

    if (!checkAccess("USER")) {
        return;
    }

    setStatusColor();
};

/* ================= DROPDOWN ================= */

function toggleDropdown() {

    let list = document.getElementById("dropdownList");

    if (list.style.display === "block") {
        list.style.display = "none";
    } else {
        list.style.display = "block";
    }
}

/* select status */

function selectStatus(status) {

    let btn = document.querySelector(".dropdown-btn");
    let text = document.getElementById("selectedStatus");

    text.innerText = status;

    btn.classList.remove("pending", "progress", "done");

    if (status === "PENDING") {
        btn.classList.add("pending");
    } else if (status === "IN_PROGRESS") {
        btn.classList.add("progress");
    } else {
        btn.classList.add("done");
    }

    document.getElementById("dropdownList").style.display = "none";
}

/* set color on page load */

function setStatusColor() {

    let status = document.getElementById("selectedStatus").innerText;
    let btn = document.querySelector(".dropdown-btn");

    btn.classList.remove("pending", "progress", "done");

    if (status === "PENDING") {
        btn.classList.add("pending");
    } else if (status === "IN_PROGRESS") {
        btn.classList.add("progress");
    } else {
        btn.classList.add("done");
    }
}

/* ================= SAVE TASK ================= */

async function saveTask(){

const title = document.getElementById("title").value.trim();
const description = document.getElementById("description").value.trim();
const status = document.getElementById("selectedStatus").innerText;

/* validation */

if(!title || !description){
alert("Please fill all fields");
return;
}

try{

const response = await fetch(`${TASK_URL}`,{

method:"POST",

headers:{
...getAuthHeaders(),
"Content-Type":"application/json"
},

body:JSON.stringify({
title:title,
description:description,
status:status
})

});

if(response.ok){

alert("Task created successfully!");

window.location.href="user-dashboard.html";

}else{

alert("Task create failed");

}

}catch(err){

console.error(err);
alert("Server error");

}

}

/* ================= BACK ================= */

function goBack() {
    window.location.href = "/user/user-dashboard.html";
}