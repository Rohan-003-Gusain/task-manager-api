window.onload = function() {
    checkAccess("ADMIN");
};

function openUsers() {
    window.location.href = "admin-view-users.html";
}

async function goToViewTasks() {

    const userId = document.getElementById("userIdInput").value;

    if (!userId) {
        alert("Please enter a User ID");
        return;
    }

    try {

        const response = await fetch(`${ADMIN_URL}/user/${userId}/tasks`, {
            headers: getAuthHeaders()
        });

        if (response.status === 404) {
            alert("No user found with this ID");
            return;
        }

        if (response.status === 401 || response.status === 403) {
            logout();
            return;
        }

        window.location.href =
            `admin-view-user-tasks.html?userId=${encodeURIComponent(userId)}`;

    } 
    catch (err) {

        console.error(err);
        alert("Server error");

    }
}