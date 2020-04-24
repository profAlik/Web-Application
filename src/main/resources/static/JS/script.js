function createUser() {
    if (checkNonEmptyData() && checkInputNumber() && checkInputBirth() && checkInputVk()) {
        var xhr = new XMLHttpRequest();
        xhr.open("POST", "api/create/new/user");
        xhr.setRequestHeader("Content-type", "application/json");
        var user_name = document.getElementById("input_name").value;
        var user_numberPhone = document.getElementById("input_numberPhone").value;
        var user_birthday = document.getElementById("input_birthday").value;
        var user_vk = document.getElementById("input_vk").value;
        var user_about = document.getElementById("input_about").value;
        var user_hobby = document.getElementById("input_hobby").value;
        if (document.getElementById("checkbox_marriage").checked) {
            var user_marriage = document.getElementById("checkbox_marriage").value;
        } else {
            var user_marriage = "Не состоит в браке";
        }
        if (document.getElementById("checkbox_have_children").checked) {
            var user_have_children = document.getElementById("checkbox_have_children").value;
        } else {
            var user_have_children = "Нет детей";
        }
        var selectedIndex = document.getElementById("input_user_gender").selectedIndex;
        var user_gender = document.getElementById("input_user_gender").options[selectedIndex].value;
        var user_password = document.getElementById("input_user_password").value;
        var user_role = "user";
        var params = {
            "name" : user_name,
            "numberPhone" : user_numberPhone,
            "birthday" : user_birthday,
            "vk" : user_vk,
            "about" : user_about,
            "hobby" : user_hobby,
            "marriage" : user_marriage,
            "haveChildren" : user_have_children,
            "gender" : user_gender,
            "password" : user_password,
            "role" : user_role
        };
        xhr.send(JSON.stringify(params));
        xhr.onload = (e) => {
            if (xhr.status === 200) {
                alert ('Регестрация прошла успешно!');
                document.getElementById("input_numberPhone").value = "";
                document.getElementById("input_birthday").value = "";
                document.getElementById("input_vk").value = "";
                document.getElementById("input_name").value = "";
                document.getElementById("input_about").value = "";
                document.getElementById("input_hobby").value = "";
                document.getElementById("input_user_password").value = "";
                window.location='login';
            } else if (xhr.status === 400) {
                alert("Неверные данные. (Имя пользователя должно быть уникальное)");
                document.getElementById("input_name").value = "";
            } else if (xhr.status === 500) {
                alert("Ошибка на сервере");
            } else {
                alert("Что-то пошло не так. Повторите попытку позже.");
            }
        }
    } else {
        if (!checkNonEmptyData()) {
            alert("Не все поля заполнены.")
        }
        if (!checkInputNumber()) {
            alert("Поле для ввода телефона не должно содержать букв и быть пустым.")
            document.getElementById("input_numberPhone").value = "";
        }
        if (!checkInputBirth()) {
            alert("Неккоректная дата рождения.")
            document.getElementById("input_birthday").value = "";
        }
        if (!checkInputVk()) {
            alert("Ссылка должна быть следующего вида: https://vk.com/user_id")
            document.getElementById("input_vk").value = "";
        }
    }

}

function checkInputNumber() {
    var numberRegex = new RegExp("^[-+()0-9]*[^A-Za-zА-ЯА-я]$");
    var user_number = document.getElementById("input_numberPhone").value;
    if (numberRegex.test(user_number)) {
        return true;
    }
}

function checkInputBirth() {
    var input_birth = document.getElementById("input_birthday").value.split('-');
    var input_date = new Date(input_birth[0], input_birth[1] - 1, input_birth[2]);
    var current_date = new Date();
    var min_date = new Date();
    min_date.setFullYear(min_date.getFullYear() - 150);
    if (input_date < current_date && input_date > min_date) {
        return true;
    }
}

function checkInputVk() {
    var vkRegex = new RegExp("^https:\/\/vk\.com\/.*");
    var user_vk = document.getElementById("input_vk").value;
    if (vkRegex.test(user_vk)) {
        return true;
    }
}

function checkNonEmptyData() {
    if (document.getElementById("input_name").value.trim() !== "" &&
        document.getElementById("input_about").value.trim() !== "" &&
        document.getElementById("input_hobby").value.trim() !== "" &&
        document.getElementById("input_user_password").value.trim() !== "") {
        return true;
    }
}

function checkUserAndPassword () {
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "api/is/user/with/password");
    xhr.setRequestHeader("Content-type", "application/json");

    var user_name = document.getElementById("input_user_name").value;
    var user_password = document.getElementById("input_user_password").value;
    var params = {
        "name": user_name,
        "password": user_password
    };
    xhr.send(JSON.stringify(params));

    xhr.onload = (e) => {
        if (xhr.status === 200) {
            document.location.href = "usersOverview.html?name=" + user_name;
        } else if (xhr.status === 400) {
            alert("Неверное имя пользователя или/и пароль");
        } else if (xhr.status === 500) {
            alert("Ошибка на сервере");
        } else {
           alert("Что-то пошло не так. Повторите попытку позже.");
        }
    }
}