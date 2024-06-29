$(document).ready(function() {
    $('#add-announcement').click(function() {
        window.location.href = '/add_announcement';
    });

    $('#my-announcements').click(function() {
        window.location.href = '/my_announcements';
    });

    $('#logout').click(function() {
        $.ajax({
            url: '/auth/doLogout',
            method: 'GET',
            success: function(response) {
                alert('Logout effettuato con successo!');
                window.location.href = '/'; // Reindirizza alla homepage dopo il logout
            },
            error: function(xhr, status, error) {
                console.error('Errore durante il logout:', error);
                alert('Errore durante il logout. Si prega di riprovare.');
            }
        });
    });
});
