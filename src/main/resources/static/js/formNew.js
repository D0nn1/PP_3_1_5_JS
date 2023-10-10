let formNew = document.forms["formNew"];

createNewUser();

function createNewUser() {
    formNew.addEventListener("submit", ev => {
        ev.preventDefault();

        let rolesForNewUser = [];
        for (let i = 0; i < formNew.roles.options.length; i++) {
            if (formNew.roles.options[i].selected)
                rolesForNewUser.push({
                    id: formNew.roles.options[i].value,
                    role: "ROLE_" + formNew.roles.options[i].text
                });
        }

        fetch("/api/admin/users/", {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                name: formNew.name.value,
                surname: formNew.surname.value,
                age: formNew.age.value,
                username: formNew.username.value,
                password: formNew.password.value,
                roles: rolesForNewUser
            })
        }).then(() => {
            formNew.reset();
            getAllUsers();
            $('#usersTable').click();

        });
    });
}

function loadRolesForNewUser() {
    let selectAdd = document.getElementById("create-roles");

    selectAdd.innerHTML = "";

    fetch("/api/admin/roles")
        .then(res => res.json())
        .then(data => {
            data.forEach(role => {
                let option = document.createElement("option");
                option.value = role.id;
                option.text = role.authority.replace('ROLE_', '');
                selectAdd.appendChild(option);
            });
        })
        .catch(error => console.error(error));
}

window.addEventListener("load", loadRolesForNewUser);

