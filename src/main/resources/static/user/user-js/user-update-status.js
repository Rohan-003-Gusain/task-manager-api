let taskId = null;

/* get taskId from URL */

const params = new URLSearchParams(window.location.search);
taskId = params.get("taskId");

/* page load */

window.onload = function () {

    if (!checkAccess("USER")) {
        return;
    }

    if (taskId) {
        loadTaskDetails();
    }
	
};

async function loadTaskDetails() {
	try {
		const response = await fetch(`${TASK_URL}/${taskId}`,{
		headers: getAuthHeaders()
		});

		if(!response.ok){
		alert("Task load failed");
		return;
		}

		const task = await response.json();

		document.getElementById("title").value = task.title;
		document.getElementById("description").value = task.description;

		selectStatus(task.status)

		}catch(err){

		console.error(err);
		alert("Server error");

		}
	} 

/* dropdown open/close */

function toggleDropdown(){

const list = document.getElementById("dropdownList");

if(list.style.display === "block"){
list.style.display = "none";
}else{
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
/* update status */

async function updateStatus(){

	const title = document.getElementById("title").value;
	const description = document.getElementById("description").value;
	const status = document.getElementById("selectedStatus").innerText;

try{

const response = await fetch(`${TASK_URL}/${taskId}`,{

method:"PUT",

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

alert("Status updated successfully");

window.location.href="user-view-tasks.html";

}else{

alert("Update failed");

}

}catch(err){

console.error(err);
alert("Server error");

}

}

/* back button */

function goBack(){
window.location.href="user-view-tasks.html";
}