window.onload = function () {

    if (!checkAccess("USER")) {
        return;
    }

};

function openAddTask() {
    window.location.href = "user-add-task.html";
}

function openTasks() {
    window.location.href = "user-view-tasks.html";
}