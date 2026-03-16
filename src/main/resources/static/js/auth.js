const BASE_URL = "https://task-manager-api-production-9871.up.railway.app/api/v1";
const AUTH_URL = `${BASE_URL}/auth`;
const TASK_URL = `${BASE_URL}/tasks`;
const ADMIN_URL = `${BASE_URL}/admin`;

function getToken() {
    return localStorage.getItem("token");
}

function getAuthHeaders() {
    return {
        "Content-Type": "application/json",
        "Authorization": `Bearer ${getToken()}`
    };
}

function checkAccess(role) {
    const token = getToken();

    if (!token) {
        handleAuthFailure("Please login first");
        return false;
    }

    try {
        const base64Url = token.split('.')[1];
        const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
        const payload = JSON.parse(window.atob(base64));

        if (payload.exp && Date.now() >= payload.exp * 1000) {
            handleAuthFailure("Session expired. Please login again.");
            return false;
        }

        if (role && payload.role !== role) {
            handleAuthFailure("Access denied: Insufficient permissions.");
            return false;
        }
		return true;
    } catch (e) {
        handleAuthFailure();
    }
}

function handleAuthFailure(message) {
    if (message) alert(message);
    localStorage.removeItem("token");
    window.location.href = "../login.html";
}

function logout() {
    localStorage.removeItem("token");
    window.location.href = "/login.html";
}