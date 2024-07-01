$(document).ready(function() {
    // Funzione per gestire il click sul tab di registrazione
    $('#register-tab').click(function(event) {
        event.preventDefault();
        $('#login-tab').removeClass('active');
        $('#register-tab').addClass('active');
        $('#login-form').removeClass('visible').addClass('hidden');
        $('#register-form').removeClass('hidden').addClass('visible');
    });

    // Funzione per gestire il click sul tab di login
    $('#login-tab').click(function(event) {
        event.preventDefault();
        $('#login-tab').addClass('active');
        $('#register-tab').removeClass('active');
        $('#login-form').removeClass('hidden').addClass('visible');
        $('#register-form').removeClass('visible').addClass('hidden');
    });

    // Funzione per gestire il submit del form di registrazione
    $('#register-submit').click(function(event) {
        event.preventDefault();

        var nome = $('#register-name').val();
        var cognome = $('#register-cognome').val();
        var email = $('#register-email').val();
        var password = $('#register-password').val();
        var tipo = $('#type1').val();

        var dataToSend = {
            nome: nome,
            cognome: cognome,
            email: email,
            password: password,
            tipo: tipo
        };

        $.ajax({
            url: 'auth/doRegister',
            method: 'POST',
            data: dataToSend,
            success: function(response) {
                alert('Registrazione avvenuta con successo!');
                $('#register-name, #register-cognome, #register-email, #register-password').val('');
                $('#type1').val('');
                $('#login-tab').click(); // Passa al tab di login dopo la registrazione
            },
            error: function(xhr, status, error) {
                console.error('Errore durante la registrazione:', error);
                alert('Errore durante la registrazione. Si prega di riprovare.');
            }
        });
    });

    // Funzione per gestire il submit del form di login
    $('#login-submit').click(function(event) {
        event.preventDefault();

        var email = $('#login-email').val();
        var password = $('#login-password').val();

        var dataToSend = {
            email: email,
            password: password
        };

        $.ajax({
            url: 'auth/doLogin',
            method: 'POST',
            data: dataToSend,
            success: function(response) {
                console.log(response);
                localStorage.setItem('sessionToken', response); // Salva il token di sessione nel localStorage
                window.location.href = '/profile'; // Reindirizza alla pagina di profilo dopo il login
            },
            error: function(xhr, status, error) {
                console.error('Errore durante il login:', error);
                alert('Errore durante il login. Si prega di riprovare.');
            }
        });
    });

    $(document).ready(function() {
        // Funzione per verificare e aggiornare lo stato di login all'avvio della pagina
        function checkAndUpdateLoginStatus() {
            var sessionToken = localStorage.getItem('sessionToken');
            if (sessionToken) {
                $.ajax({
                    url: '/auth/checkLogin',
                    method: 'GET',
                    success: function(response) {
                        if (response) {
                            updateUIAfterLogin();
                        } else {
                            localStorage.removeItem('sessionToken'); // Rimuove il token scaduto dal localStorage
                        }
                    },
                    error: function(xhr, status, error) {
                        console.error('Errore durante la verifica dello stato di login:', error);
                    }
                });
            }
        }

        // Funzione per aggiornare l'interfaccia utente dopo il login
        function updateUIAfterLogin() {
            $('#user-button').text('Profilo').off('click').click(function(event) {
                event.preventDefault();
                $('#user-dropdown').toggle(); // Mostra o nasconde il dropdown
                // Eventuale altro codice per gestire il click sul profilo
            });
        }

        // Chiamata per verificare lo stato di login quando la pagina è caricata
        checkAndUpdateLoginStatus();
    });
});