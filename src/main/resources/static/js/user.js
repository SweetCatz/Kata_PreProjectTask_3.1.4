async function getCurrentUserInfo() {
    const currentUser = await (await fetch(`/user/current`)).json();
    document.getElementById('currentUserEmailNav').innerText = currentUser.email

    if (Array.isArray(currentUser.roles)) {
        const roleNames = currentUser.roles.map(role => role.roleName.substring(5, role.roleName.length));
        document.getElementById('currentUserRolesNav').innerText = roleNames.join(', ');
    }

    document.getElementById('currentUserID').innerText = currentUser.id
    document.getElementById('currentUserName').innerText = currentUser.name
    document.getElementById('currentUserLastName').innerText = currentUser.lastName
    document.getElementById('currentUserEmail').innerText = currentUser.email
    document.getElementById('currentUserAge').innerText = currentUser.age

    if (Array.isArray(currentUser.roles)) {
        const roleNames = currentUser.roles.map(role => role.roleName.substring(5, role.roleName.length));
        document.getElementById('currentUserRoles').innerText = roleNames.join(', ');
    }
}