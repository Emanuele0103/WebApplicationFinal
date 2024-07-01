$(document).ready(function() {
    $('#add-announcement').click(function() {
        window.location.href = '/add_announcement';
    });

    $('#my-announcements').click(function() {
        window.location.href = '/my_announcements';
    });

    $('#logout').click(function() {
        $.ajax({
            url: '/auth/doLogout', // Chiamata all'endpoint di logout nel tuo controller Spring Boot
            method: 'GET',
            success: function(response) {
                alert('Logout effettuato con successo!');
                deleteSessionCookie(); // Rimuove il cookie di sessione dal frontend
                window.location.href = '/'; // Reindirizza alla homepage dopo il logout
            },
            error: function(xhr, status, error) {
                console.error('Errore durante il logout:', error);
                alert('Errore durante il logout. Si prega di riprovare.');
            }
        });
    });

// Funzione per eliminare il cookie di sessione dal frontend
    function deleteSessionCookie() {
        document.cookie = "sessionToken=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;";
    }
});
