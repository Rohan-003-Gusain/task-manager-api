/* ================= LOGIN ================= */

async function login() {

    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;

    if(!username || !password){
        alert("Please enter username and password");
        return;
    }

    try {

        const response = await fetch(`${AUTH_URL}/login`, {

            method: "POST",

            headers: {
                "Content-Type": "application/json"
            },

            body: JSON.stringify({
                username,
                password
            })

        });

        /* ❌ wrong credentials */

        if(response.status === 401){
            alert("Invalid username or password");
            return;
        }

        /* ❌ other error */

        if(!response.ok){
            alert("Login failed");
            return;
        }

        const data = await response.json();

        /* store JWT */

        localStorage.setItem("token", data.token);

        const payload = JSON.parse(atob(data.token.split(".")[1]));
        localStorage.setItem("role", payload.role);
        localStorage.setItem("username", payload.sub);

        /* redirect */

        if(payload.role === "ADMIN"){
            window.location.href = "/admin/admin-dashboard.html";
        }
        else{
            window.location.href = "/user/user-dashboard.html";
        }

    } 
    catch (err) {

        console.error("Login Error:", err);
        alert("Server connection failed!");

    }

}