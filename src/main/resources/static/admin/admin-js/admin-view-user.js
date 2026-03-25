window.onload = function () {

    if (!checkAccess("ADMIN")) {
        return;
    }

    loadUsers();
};

async function loadUsers() {

    const userBody = document.getElementById("userBody");

    userBody.innerHTML =
        '<tr><td colspan="4" style="text-align:center;">Loading users...</td></tr>';

    try {

        const response = await fetch(`${ADMIN_URL}/users`, {
            method: "GET",
            headers: getAuthHeaders()
        });

        if (response.status === 401 || response.status === 403) {
            logout();
            return;
        }

        const users = await response.json();

        if (!users || users.length === 0) {
            userBody.innerHTML =
                '<tr><td colspan="4" style="text-align:center;">No users found</td></tr>';
            return;
        }

        userBody.innerHTML = "";

        users.forEach(user => {

            const row = document.createElement("tr");

            row.innerHTML = `
                <td>${user.id}</td>
                <td>${user.username}</td>
                <td>${user.email}</td>
                <td>${user.role}</td>
            `;

            userBody.appendChild(row);

        });

    } catch (err) {

        userBody.innerHTML =
            '<tr><td colspan="4" style="text-align:center;color:red;">Server error</td></tr>';

    }
}