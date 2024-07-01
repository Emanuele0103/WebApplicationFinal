$(document).ready(function() {
    $('#add-announcement').click(function() {
        window.location.href = '/add_announcement';
    });

    $('#my-announcements').click(function() {
        window.location.href = '/my_announcements';
    });

    $(document).ready(function() {
        // Funzione per verificare lo stato di login e aggiornare il pulsante
        function checkAndUpdateLoginStatus() {
            var sessionToken = localStorage.getItem('sessionToken');
            if (sessionToken) {
                $.ajax({
                    url: '/auth/checkLogin',
                    method: 'GET',
                    headers: { 'Authorization': 'Bearer ' + sessionToken },
                    success: function(response) {
                        if (response) {
                            $('#user-button').text('Profilo').attr('href', '/profile');
                        } else {
                            localStorage.removeItem('sessionToken');
                            $('#user-button').text('Accedi').attr('href', 'accesso');
                        }
                    },
                    error: function(xhr, status, error) {
                        console.error('Errore durante la verifica dello stato di login:', error);
                    }
                });
            } else {
                $('#user-button').text('Accedi').attr('href', 'accesso');
            }
        }

        // Chiamata per verificare lo stato di login quando la pagina è caricata
        checkAndUpdateLoginStatus();

        // Evento click sul pulsante di logout
        $('#logout').click(function(event) {
            event.preventDefault();
            $.ajax({
                url: '/auth/doLogout',
                method: 'GET',
                success: function(response) {
                    console.log(response);
                    localStorage.removeItem('sessionToken');
                    $('#user-button').text('Accedi').attr('href', 'accesso');
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
});
