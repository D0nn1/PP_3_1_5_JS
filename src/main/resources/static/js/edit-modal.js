let formEdit = document.forms["formEdit"];
editUser();

const URLEdit = "/api/admin/users/";

async function editModal(id) {
    const modalEdit = new bootstrap.Modal(document.querySelector('#editModal'));
    await open_fill_modal(formEdit, modalEdit, id);
    loadRolesForEdit();
}

async function editUser() {
    formEdit.addEventListener("submit", async (ev) => {
        ev.preventDefault();

        let rolesForEdit = [];
        for (let i = 0; i < formEdit.roles.options.length; i++) {
            if (formEdit.roles.options[i].selected)
                rolesForEdit.push({
                    id: formEdit.roles.options[i].value,
                    role: "ROLE_" + formEdit.roles.options[i].text,
                });
        }

        const headers = new Headers({
            'Content-Type': 'application/json',
        });

        try {
            const response = await fetch(URLEdit + formEdit.id.value, {
                method: 'PATCH',
                headers: headers,
                body: JSON.stringify({
                    id: formEdit.id.value,
                    name: formEdit.name.value,
                    surname: formEdit.surname.value,
                    age: formEdit.age.value,
                    username: formEdit.username.value,
                    password: formEdit.password.value,
                    roles: rolesForEdit,
                }),
            });

            if (response.ok) {
                $('#editClose').click();
                getAllUsers();
            } else {
                // Обработка ошибки
                console.error('Ошибка при редактировании пользователя');
            }
        } catch (error) {
            console.error(error);
        }
    });
}


function loadRolesForEdit() {
    let selectEdit = document.getElementById("edit-roles");
    selectEdit.innerHTML = "";

    fetch("/api/admin/roles")
        .then(res => res.json())
        .then(data => {
            data.forEach(role => {
                let option = document.createElement("option");
                option.value = role.id;
                option.text = role.authority.replace('ROLE_', '');
                selectEdit.appendChild(option);
            });
        })
        .catch(error => console.error(error));
}

window.addEventListener("load", loadRolesForEdit);

