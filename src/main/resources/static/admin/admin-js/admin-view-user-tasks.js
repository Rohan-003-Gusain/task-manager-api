window.onload = function () {

    const params = new URLSearchParams(window.location.search);
    const userId = params.get("userId");
	
	if(!userId){
	    alert("User ID missing");
	    goBack();
	    return;
	}

    if (!checkAccess("ADMIN")) {
        return;
    }

    loadUserTasks(userId);
};

async function loadUserTasks(userId) {

    const taskBody = document.getElementById("taskBody");

    try {

        const response = await fetch(`${ADMIN_URL}/user/${userId}/tasks`, {
            headers: getAuthHeaders()
        });

        if (response.status === 401 || response.status === 403) {
            logout();
            return;
        }

        if (response.status === 404) {
            alert("No user found with this ID");
            return;
        }

        const tasks = await response.json();

        taskBody.innerHTML = "";

        if (tasks.length === 0) {

            taskBody.innerHTML =
            '<tr><td colspan="4" style="text-align:center;">No tasks for this user</td></tr>';

            return;
        }

        tasks.forEach(task => {

            const row = document.createElement("tr");

            row.innerHTML =
            `<td>${task.taskId}</td>
             <td>${task.title}</td>
             <td>${task.description}</td>
             <td>${task.status}</td>`;

            taskBody.appendChild(row);

        });

    } catch (err) {

        console.error(err);
        alert("Error loading tasks");

    }
}

function goBack(){
    window.location.href = "/admin/admin-dashboard.html";
}