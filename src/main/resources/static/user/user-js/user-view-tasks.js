let selectedTaskId = null;

window.onload = function () {

    if (!checkAccess("USER")) {
        return;
    }

    loadTasks();

    document.getElementById("taskTableBody").addEventListener("click", function(e){

        const row = e.target.closest("tr");
        if(!row) return;

        document.querySelectorAll("#taskTableBody tr")
        .forEach(r => r.classList.remove("selected"));

        row.classList.add("selected");

        selectedTaskId = row.cells[0].innerText.trim();
        console.log("Selected ID:", selectedTaskId);

    });

};


async function loadTasks(){

try{

const response = await fetch(`${TASK_URL}/all`,{
headers: getAuthHeaders()
});

const tasks = await response.json();

const tbody = document.getElementById("taskTableBody");
tbody.innerHTML = "";

if(tasks.length === 0){

tbody.innerHTML = `
<tr>
<td colspan="4" style="text-align:center;">
No tasks found
</td>
</tr>
`;

return;
}

tasks.forEach(task => {

const row = document.createElement("tr");

row.innerHTML = `
<td>${task.taskId}</td>
<td>${task.title}</td>
<td>${task.description}</td>
<td>${task.status}</td>
`;
tbody.appendChild(row);

});

}catch(err){

console.error(err);
alert("Error loading tasks");

}

}

function updateTask(){
	
	const tbody = document.getElementById("taskTableBody");
	
	if(tbody.rows.length === 0){
	alert("List is empty, can not update");
	return;
	}

if(!selectedTaskId){
alert("Please select a task first");
return;
}

window.location.href = "user-update-status.html?taskId=" + selectedTaskId;

}

/* delete task */

async function deleteTask(){
	
	const tbody = document.getElementById("taskTableBody")
	
	if(tbody.rows.length === 0){
	alert("List is empty, can not delete");
	return;
	}

if(!selectedTaskId){
alert("Pehle task select karo");
return;
}

if(!confirm("Delete this task?")) return;

try{

const response = await fetch(`${TASK_URL}/${selectedTaskId}`,{
method:"DELETE",
headers:getAuthHeaders()
});

if(response.ok){

loadTasks();
selectedTaskId = null;

}else{
alert("Delete failed");
}

}catch(err){

console.error(err);
alert("Error deleting task");

}

}

const tableWrapper = document.querySelector(".table-wrapper");

document.getElementById("taskTableBody").addEventListener("wheel", function(e){

const cell = e.target.closest("td");
if(!cell) return;

let delta = e.deltaY + e.deltaX;
delta = Math.sign(delta) * Math.min(Math.abs(delta),40);

const canScroll = cell.scrollWidth > cell.clientWidth;

if(!canScroll) return;

const atStart = cell.scrollLeft <= 0;
const atEnd = cell.scrollLeft + cell.clientWidth >= cell.scrollWidth - 1;

if((delta < 0 && !atStart) || (delta > 0 && !atEnd)){

cell.scrollLeft += delta;
e.preventDefault();

}else{

tableWrapper.scrollTop += delta;
e.preventDefault();

}

},{passive:false});

function goBack(){
window.location.href = "user-dashboard.html";
}