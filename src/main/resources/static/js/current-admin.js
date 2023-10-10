const URLNavbarAdmin = '/api/admin/showAccount/';
const navbarBrandAdmin = document.getElementById('navbarBrandAdmin');
const tableUserAdmin = document.getElementById('tableAdmin');

function getCurrentAdmin() {
    fetch(URLNavbarAdmin)
        .then((res) => res.json())
        .then((userAdmin) => {

            let rolesStringAdmin = rolesToStringForAdmin(userAdmin.roles);
            let data = '';

            data += `<tr>
            <td>${userAdmin.id}</td>
            <td>${userAdmin.name}</td>
            <td>${userAdmin.surname}</td>
            <td>${userAdmin.age}</td>
            <td>${userAdmin.username}</td>
            <td>${rolesStringAdmin}</td>
            </tr>`;
            tableUserAdmin.innerHTML = data;
            navbarBrandAdmin.innerHTML = `<b><span>${userAdmin.username}</span></b>
                             <span>with roles:</span>
                             <span>${rolesStringAdmin}</span>`;
        });
}

getCurrentAdmin()

function rolesToStringForAdmin(roles) {
    let rolesString = '';

    for (const element of roles) {
        rolesString += (element.authority.replace('ROLE_', '') + ', ');
    }
    rolesString = rolesString.substring(0, rolesString.length - 2);
    return rolesString;
}

