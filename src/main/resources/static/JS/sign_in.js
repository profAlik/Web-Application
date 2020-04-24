function isAuthenticated() {
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "/webapp/login");
    xhr.setRequestHeader("Content-type", "application/json");
    var username = document.getElementById("username").value;
    var password = document.getElementById("password").value;
    var params = {"username": username, "password": password};
    xhr.send(JSON.stringify(params));
    xhr.onload = (e) => {
        if (xhr.status !== 200) {
            alert('Неверный логин и/или пароль');
        }
    };
}
