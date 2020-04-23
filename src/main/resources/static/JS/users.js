window.onload = function selectUser() {
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "api/select/first/user");
    xhr.setRequestHeader("Content-type", "application/json");
    xhr.send();
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

        var xhr3 = new XMLHttpRequest();
        xhr3.open("POST", "api/select/comment/by/name");
        xhr3.setRequestHeader("Content-type", "application/json");
        var name = document.getElementById("user_name").textContent;
        var params = {"name": name};
        xhr3.send(JSON.stringify(params));
        xhr3.onload = (e) => {
            var user = JSON.parse(e.target.response);
             var test = "";
            for (var i = 0; i < user.comments.length; i++) {
                test = test + user.comments[i] + "\n";
            }
            document.getElementById("comments_field").value = test;

        };
    };
};


function getUser(switching) {
        var xhr = new XMLHttpRequest();
        xhr.open("POST", "api/select/user/by/id");
        xhr.setRequestHeader("Content-type", "application/json");
        xhr.responseType = "text";

        var xhr2 = new XMLHttpRequest();
        xhr2.open("POST", "api/get/all/user/id");
        xhr2.setRequestHeader("Content-type", "application/json");
        xhr2.responseType = "text";
        xhr2.send();
        xhr2.onload = (e) => {
            var userData = JSON.parse(e.target.response);
            var temp;
            if (switching === 1) {
                var user_id = document.getElementById("user_id").textContent;
                do {
                    temp = userData.shift();
                } while (user_id >= temp);
            } else if (switching === 2) {
                var user_id = document.getElementById("user_id").textContent;
                do {
                    temp = userData.pop();
                } while (user_id <= temp);
            }
            user_id = temp;
            var params = {"id": user_id};
            xhr.send(JSON.stringify(params));
        };

        xhr.onload = (e) => {
            var userData = JSON.parse(e.target.response),
                userDataId = userData.id,
                userDataName = userData.name,
                userDataNumber = userData.numberPhone,
                userDataBirth = userData.birthday,
                userDataVk = userData.vk,
                userDataAbout = userData.about,
                userDataHobby = userData.hobby;
                if (userDataId == null){
                    alert('Конец списка пользователей.');
                } else {
                    document.getElementById("user_id").textContent = userDataId;
                    document.getElementById("user_name").textContent = userDataName;
                    document.getElementById("user_numberPhone").textContent = userDataNumber;
                    document.getElementById("user_birthday").textContent = userDataBirth;
                    document.getElementById("user_vk").href = userDataVk;
                    document.getElementById("user_about").value = userDataAbout;
                    document.getElementById("user_hobby").textContent = userDataHobby;
                    document.getElementById("user_marriage").textContent = userData.marriage;
                    document.getElementById("user_have_children").textContent = userData.haveChildren;
                    document.getElementById("user_gender").textContent = userData.gender;

                    var xhr3 = new XMLHttpRequest();
                    xhr3.open("POST", "api/select/comment/by/name");
                    xhr3.setRequestHeader("Content-type", "application/json");
                    var name = document.getElementById("user_name").textContent;
                    var params = {"name": name};
                    xhr3.send(JSON.stringify(params));
                    xhr3.onload = (e) => {
                        var user = JSON.parse(e.target.response);
                        var test = "";
                        for (var i = 0; i < user.comments.length; i++) {
                            test = test + user.comments[i] + "\n";
                        }
                        document.getElementById("comments_field").value = test;

                    };
            }
        };
}

function leaveComment() {
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "api/insert/new/comment");
    xhr.setRequestHeader("Content-type", "application/json");

    var nameReceiver = document.getElementById("user_name").textContent;
    var comment = document.getElementById("comment_input").value;
    var params = {"nameReceiver": nameReceiver, "comment": comment};
    xhr.send(JSON.stringify(params));
    var test = document.getElementById("comments_field").value;
    test = test + document.getElementById("comment_input").value;
    document.getElementById("comments_field").value = test;
    document.getElementById("comment_input").value = "";
}