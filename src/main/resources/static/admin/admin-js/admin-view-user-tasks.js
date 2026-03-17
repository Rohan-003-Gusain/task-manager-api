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

        /* ✅ STEP-1 → check user exist */
        const userResponse = await fetch(`${ADMIN_URL}/user/${userId}`, {
            headers: getAuthHeaders()
        });

        if (userResponse.status === 401 || userResponse.status === 403) {
            logout();
            return;
        }

        if (userResponse.status === 404) {
            alert("User not found");
            goBack();
            return;
        }

        /* ✅ STEP-2 → now check tasks */
        const taskResponse = await fetch(`${ADMIN_URL}/user/${userId}/tasks`, {
            headers: getAuthHeaders()
        });

        if (taskResponse.status === 401 || taskResponse.status === 403) {
            logout();
            return;
        }

        const tasks = await taskResponse.json();

        taskBody.innerHTML = "";

        /* ✅ STEP-3 → tasks empty */
        if (!tasks || tasks.length === 0) {

            alert("Tasks not found for this user");

            taskBody.innerHTML =
                '<tr><td colspan="4" style="text-align:center;">No tasks for this user</td></tr>';

            return;
        }

        /* ✅ STEP-4 → show tasks */
        tasks.forEach(task => {

            const row = document.createElement("tr");

            row.innerHTML = `
                <td>${task.taskId}</td>
                <td>${task.title}</td>
                <td>${task.description}</td>
                <td>${task.status}</td>
            `;

            taskBody.appendChild(row);
        });

    } catch (err) {
        console.error(err);
        alert("Error loading data");
    }
}

function goBack(){
    window.location.href = "/admin/admin-dashboard.html";
}