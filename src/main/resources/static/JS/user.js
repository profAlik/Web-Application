window.onload = function selectUser() {
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "api/select/user/by/name");
    xhr.setRequestHeader("Content-type", "application/json");
    var name = decodeURI(location.search.substr(1).split('&')[0].split('=')[1]);
    var params = {"name": name};
    xhr.send(JSON.stringify(params));
    xhr.onload = (e) => {
        var user = JSON.parse(e.target.response);
        document.getElementById("user_id").textContent = user.id;
        document.getElementById("user_name").textContent = user.name;
        document.getElementById("user_numberPhone").textContent = user.numberPhone;
        document.getElementById("user_birthday").textContent = user.birthday;
        document.getElementById("user_vk").href = user.vk;
        document.getElementById("user_about").textContent = user.about;
        document.getElementById("user_hobby").textContent = user.hobby;
        document.getElementById("user_marriage").textContent = user.marriage;
        document.getElementById("user_have_children").textContent = user.haveChildren;
        document.getElementById("user_gender").textContent = user.gender;

    };
};

function updateUserInfo() {
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "api/update/user/info");
    xhr.setRequestHeader("Content-type", "application/json");

    var user_name = document.getElementById("user_name").textContent;
    var user_info = document.getElementById("user_about").value;
    var params = {"name": user_name, "info": user_info};
    xhr.send(JSON.stringify(params));
    xhr.onload = (e) => {
            var user = JSON.parse(e.target.response);
            if (xhr.status === 200) {
                alert("Данные успешно изменены!");
            } else if (xhr.status === 400) {
                alert("Ошибка с заполнеными данными.");
                document.getElementById("input_name").value = "";
            } else if (xhr.status === 500) {
                alert("Ошибка на сервере");
            } else {
                alert("Что-то пошло не так. Повторите попытку позже.");
            }
        };

}