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

        const res = await fetch(
            `${ADMIN_URL}/user/${userId}/tasks`,
            { headers: getAuthHeaders() }
        );

        /* auth problem */
        if (res.status === 401 || res.status === 403) {
            logout();
            return;
        }

        /* user not exist */
        if (res.status === 404) {
            alert("User not found");
            goBack();
            return;
        }

        const tasks = await res.json();

        taskBody.innerHTML = "";

        /* no tasks */
        if (!tasks || tasks.length === 0) {
            taskBody.innerHTML =
                '<tr><td colspan="4" style="text-align:center;">No tasks assigned</td></tr>';
            return;
        }

        /* render tasks */
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