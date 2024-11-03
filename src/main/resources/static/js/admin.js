async function onload() {
    await getAllUsers()
    await getAllRoles()
    await getCurrentUserInfo()
}

async function showUserUpdateModal(id) {
    const user = await (await fetch(`/admin/users/${id}`)).json();
    const roles = await (await fetch(`/admin/roles`)).json();

    document.getElementById('modalUpdateId').value = user.id
    document.getElementById('modalUpdateName').value = user.name
    document.getElementById('modalUpdateLastName').value = user.lastName
    document.getElementById('modalUpdateAge').value = user.age
    document.getElementById('modalUpdateEmail').value = user.email
    document.getElementById('modalUpdatePassword').value = ''

    const rolesForm = document.getElementById('modalUpdateRoles')
    rolesForm.innerText = ''
    roles.forEach((role) => {
        const option = document.createElement('option')
        option.value = role.id
        option.textContent = role.roleName.substring(5, role.roleName.length)
        rolesForm.appendChild(option)
    })
}

async function showUserDeleteModal(id) {
    const user = await (await fetch(`/admin/users/${id}`)).json();

    document.getElementById('modalDeleteId').value = user.id
    document.getElementById('modalDeleteName').value = user.name
    document.getElementById('modalDeleteLastName').value = user.lastName
    document.getElementById('modalDeleteAge').value = user.age
    document.getElementById('modalDeleteEmail').value = user.email

    const element = document.getElementById('modalDeleteRoles')
    element.innerText = ''
    user.roles.forEach((role) => {
        const option = document.createElement('option')
        option.value = role.id
        option.textContent = role
        element.appendChild(option)
    })
}

async function hideModalErrors() {
    document.getElementById('modalUpdateName').setAttribute('class', 'form-control')
    document.getElementById('modalUpdateLastName').setAttribute('class', 'form-control')
    document.getElementById('modalUpdateAge').setAttribute('class', 'form-control')
    document.getElementById('modalUpdatePassword').setAttribute('class', 'form-control')
    document.getElementById('modalUpdateEmail').setAttribute('class', 'form-control')
    document.getElementById('newUserName').setAttribute('class', 'form-control')
    document.getElementById('newUserLastName').setAttribute('class', 'form-control')
    document.getElementById('newUserAge').setAttribute('class', 'form-control')
    document.getElementById('newUserPassword').setAttribute('class', 'form-control')
    document.getElementById('newUserEmail').setAttribute('class', 'form-control')
}

async function updateUser() {
    let body = {};
    body.id = document.getElementById("modalUpdateId").value;
    body.name = document.getElementById("modalUpdateName").value;
    body.lastName = document.getElementById("modalUpdateLastName").value;
    body.age = document.getElementById("modalUpdateAge").value;
    body.email = document.getElementById("modalUpdateEmail").value;
    body.password = document.getElementById("modalUpdatePassword").value;
    body.roles = document.getElementById('modalUpdateRoles').value

    const users = document.getElementById("modalUpdateRoles");
    body.roles = Array.from(users.options)
        .filter(option => option.selected)
        .map(option => option.text);

    try {
        let response = await fetch("/admin/users", {
            method: "PUT",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(body)
        });
        if (!response.ok) {
            if (!response.ok) {
                const json = await response.json();
                let jsonObject = JSON.parse(JSON.stringify(json))

                const {name, lastName, age, email, password} = jsonObject

                if (name != null && name.length > 0) {
                    console.log('name error')
                    const element = document.getElementById('modalUpdateName')
                    const errorText = document.getElementById('updateUserValidationNameText')
                    element.setAttribute('class', 'form-control is-invalid')
                    errorText.innerText = name
                } else {
                    const element = document.getElementById('modalUpdateName')
                    element.setAttribute('class', 'form-control')
                }
                if (lastName != null && lastName.length > 0) {
                    console.log('last_name error')
                    const element = document.getElementById('modalUpdateLastName')
                    const errorText = document.getElementById('updateUserValidationLastNameText')
                    element.setAttribute('class', 'form-control is-invalid')
                    errorText.innerText = lastName
                } else {
                    const element = document.getElementById('modalUpdateLastName')
                    element.setAttribute('class', 'form-control')
                }
                if (age != null && age.length > 0) {
                    console.log('age error')
                    const element = document.getElementById('modalUpdateAge')
                    const errorText = document.getElementById('updateUserValidationAgeText')
                    element.setAttribute('class', 'form-control is-invalid')
                    errorText.innerText = age
                } else {
                    const element = document.getElementById('modalUpdateAge')
                    element.setAttribute('class', 'form-control')
                }
                if (email != null && email.length > 0) {
                    console.log('email error')
                    const element = document.getElementById('modalUpdateEmail')
                    const errorText = document.getElementById('updateUserValidationEmailText')
                    element.setAttribute('class', 'form-control is-invalid')
                    errorText.innerText = email
                } else {
                    const element = document.getElementById('modalUpdateEmail')
                    element.setAttribute('class', 'form-control')
                }
                if (password != null && password.length > 0) {
                    console.log('password error')
                    const element = document.getElementById('modalUpdatePassword')
                    const errorText = document.getElementById('updateUserValidationPasswordText')
                    element.setAttribute('class', 'form-control is-invalid')
                    errorText.innerText = password
                } else {
                    const element = document.getElementById('modalUpdatePassword')
                    element.setAttribute('class', 'form-control')
                }
            }
        } else {
            document.getElementById('modalUpdatePassword').setAttribute('class', 'form-control')
            getAllUsers().then()
            $('#editUserModal').modal('hide')
        }
    } catch (error) {
        console.error("ERROR:", error);
    }
}

function deleteUser() {
    let userId = document.getElementById('modalDeleteId').value
    fetch("/admin/users/" + userId, {
        method: "DELETE",
    }).then(onload)
    $('#deleteUserModal').modal('hide')
}

async function createUser() {
    let body = {};
    body.name = document.getElementById("newUserName").value;
    body.lastName = document.getElementById("newUserLastName").value;
    body.age = document.getElementById("newUserAge").value;
    body.email = document.getElementById("newUserEmail").value;
    body.password = document.getElementById("newUserPassword").value;

    const users = document.getElementById("newUserRoles");
    body.roles = Array.from(users.options)
        .filter(option => option.selected)
        .map(option => option.text)

    try {
        let response = await fetch("/admin/users", {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(body)
        });
        if (!response.ok) {
            const json = await response.json();
            let jsonObject = JSON.parse(JSON.stringify(json))

            const {name, lastName, age, email, password} = jsonObject

            if (name != null && name.length > 0) {
                const element = document.getElementById('newUserName')
                const errorText = document.getElementById('newUserValidationNameText')
                element.setAttribute('class', 'form-control is-invalid')
                errorText.innerText = name
            } else {
                const element = document.getElementById('newUserName')
                element.setAttribute('class', 'form-control')
            }
            if (lastName != null && lastName.length > 0) {
                const element = document.getElementById('newUserLastName')
                const errorText = document.getElementById('newUserValidationLastNameText')
                element.setAttribute('class', 'form-control is-invalid')
                errorText.innerText = lastName
            } else {
                const element = document.getElementById('newUserLastName')
                element.setAttribute('class', 'form-control')
            }
            if (age != null && age.length > 0) {
                const element = document.getElementById('newUserAge')
                const errorText = document.getElementById('newUserValidationAgeText')
                element.setAttribute('class', 'form-control is-invalid')
                errorText.innerText = age
            } else {
                const element = document.getElementById('newUserAge')
                element.setAttribute('class', 'form-control')
            }
            if (email != null && email.length > 0) {
                const element = document.getElementById('newUserEmail')
                const errorText = document.getElementById('newUserValidationEmailText')
                element.setAttribute('class', 'form-control is-invalid')
                errorText.innerText = email
            } else {
                const element = document.getElementById('newUserEmail')
                element.setAttribute('class', 'form-control')
            }
            if (password != null && password.length > 0) {
                const element = document.getElementById('newUserPassword')
                const errorText = document.getElementById('newUserValidationPasswordText')
                element.setAttribute('class', 'form-control is-invalid')
                errorText.innerText = password
            } else {
                const element = document.getElementById('newUserPassword')
                element.setAttribute('class', 'form-control')
            }
        } else {
            document.getElementById('newUserName').value = ''
            document.getElementById('newUserLastName').value = ''
            document.getElementById('newUserAge').value = ''
            document.getElementById('newUserEmail').value = ''
            document.getElementById('newUserPassword').value = ''
            getAllUsers().then()
            $('.nav-tabs a[href="#allUsers"]').tab('show')
        }
    } catch (error) {
        console.error("ERROR:", error);
    }
}

async function getAllRoles() {
    const roles = await (await fetch(`/admin/roles`)).json();
    const rolesForm = document.getElementById('newUserRoles')
    rolesForm.innerText = ''
    roles.forEach((role) => {
        const option = document.createElement('option')
        option.value = role.id
        option.textContent = role.roleName.substring(5, role.roleName.length)
        rolesForm.appendChild(option)
    })
}

async function getAllUsers() {
    const response = await fetch("/admin/users")
    const responseText = await response.json();
    let table = document.getElementById("usersTable")
    table.innerHTML = ''
    if (responseText.length > 0) {
        let temp = ''
        responseText.forEach((user) => {
            const userId = user.id;
            let tr = document.createElement("tr");
            let th = document.createElement("th");
            th.appendChild(document.createTextNode(userId));
            tr.appendChild(th);
            let td1 = document.createElement("td");
            td1.appendChild(document.createTextNode(user.name));
            tr.appendChild(td1);
            let td2 = document.createElement("td");
            td2.appendChild(document.createTextNode(user.lastName));
            tr.appendChild(td2);
            let td3 = document.createElement("td");
            td3.appendChild(document.createTextNode(user.age));
            tr.appendChild(td3);
            let td4 = document.createElement("td");
            td4.appendChild(document.createTextNode(user.email));
            tr.appendChild(td4);
            let td5 = document.createElement("td");
            let roles = user.roles;
            for (let i = 0; i < roles.length; i++) {
                td5.appendChild(document.createTextNode(roles[i].toString().concat(" ")));
            }

            tr.appendChild(td5);

            let editButton = document.createElement("button");
            editButton.setAttribute("type", "button");
            editButton.setAttribute("class", "btn btn-info btn-sm");
            editButton.setAttribute("data-toggle", "modal");
            editButton.setAttribute("data-target", "#editUserModal");
            editButton.appendChild(document.createTextNode("Edit"));
            editButton.addEventListener("click", function () {
                showUserUpdateModal(userId)
            });
            let dangerButton = document.createElement("button");
            dangerButton.setAttribute("type", "button");
            dangerButton.setAttribute("class", "btn btn-danger btn-sm");
            dangerButton.setAttribute("data-toggle", "modal");
            dangerButton.setAttribute("data-target", "#deleteUserModal");
            dangerButton.appendChild(document.createTextNode("Delete"));
            dangerButton.addEventListener("click", function () {
                showUserDeleteModal(userId)
            });

            let td6 = document.createElement("td");
            td6.appendChild(editButton);
            tr.appendChild(td6);
            let td7 = document.createElement("td");
            td7.appendChild(dangerButton);
            tr.appendChild(td7);
            table.appendChild(tr);
        })
    }
}
