$(document).ready(function() {
    $('#loginForm').on('submit', function(event) {
        event.preventDefault();

        let id = $('#eid').val();
        let password = $('#ePassword').val();

        if (!id.trim() || !password.trim()) {
        alert("Username and password cannot be empty.");
        return;
        }

        $.ajax({
            url: '/ajaxlogin',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({ id: id, password: password }),
                success: function(response) {
                    console.log(response)
                    if (response === "success") {
                        window.location.href = "/employee";
                    } else {
                        $('#error-message').text('Login failed.');
                    }
                },
                error: function(xhr) {
                    console.log(xhr)
                    let errorMsg = 'An error occurred during login';
                    $('#error-message').text(errorMsg);
                }
        });
    });
});