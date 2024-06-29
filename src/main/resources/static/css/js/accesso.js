$(document).ready(function() {
    // Funzione per gestire il click sul tab di registrazione
    $('#register-tab').click(function(event) {
        event.preventDefault(); // Evita il comportamento predefinito del link

        $('#login-tab').removeClass('active');
        $('#register-tab').addClass('active');
        $('#login-form').removeClass('visible').addClass('hidden');
        $('#register-form').removeClass('hidden').addClass('visible');
    });

    // Funzione per gestire il click sul tab di login
    $('#login-tab').click(function(event) {
        event.preventDefault(); // Evita il comportamento predefinito del link

        $('#login-tab').addClass('active');
        $('#register-tab').removeClass('active');
        $('#login-form').removeClass('hidden').addClass('visible');
        $('#register-form').removeClass('visible').addClass('hidden');
    });

    // Gestione della registrazione
    $('#register-submit').click(function(event) {
        event.preventDefault(); // Evita il comportamento predefinito del form

        // Ottieni i valori dai campi del modulo di registrazione
        var nome = $('#register-name').val();
        var cognome = $('#register-cognome').val();
        var email = $('#register-email').val();
        var password = $('#register-password').val();
        var tipo = $('#type1').val();

        // Crea un oggetto con i dati da inviare al server
        var dataToSend = {
            nome: nome,
            cognome: cognome,
            email: email,
            password: password,
            tipo: tipo
        };

        // Effettua una richiesta AJAX al server per la registrazione
        $.ajax({
            url: 'auth/doRegister', // URL del tuo endpoint per la registrazione
            method: 'POST',
            data: dataToSend, // Dati da inviare al server
            success: function(response) {
                // Gestisci la risposta del server in caso di successo
                console.log(response);
                if (response.success) {
                    alert('Registrazione avvenuta con successo!');
                    $('#register-name').val('');
                    $('#register-cognome').val('');
                    $('#register-email').val('');
                    $('#register-password').val('');
                    $('#type1').val('');
                    $('#login-tab').click(); // Passa al tab di login
                } else {
                    alert('Errore nella registrazione: ' + response.message);
                }
            },
            error: function(xhr, status, error) {
                // Gestisci gli errori
                console.error(error);
                alert('Errore durante la registrazione. Si prega di riprovare.');
            }
        });
    });

    $('#login-submit').click(function(event) {
        event.preventDefault(); // Evita il comportamento predefinito del form

        // Ottieni i valori dai campi del modulo di login
        var email = $('#login-email').val();
        var password = $('#login-password').val();

        // Crea un oggetto con i dati da inviare al server per il login
        var dataToSend = {
            email: email,
            password: password
        };

        // Effettua una richiesta AJAX al server per il login
        $.ajax({
            url: 'auth/doLogin', // URL del tuo endpoint per il login
            method: 'POST',
            data: dataToSend, // Dati da inviare al server
            success: function(response) {
                // Gestisci la risposta del server in caso di successo
                console.log(response);
                alert('Login effettuato con successo!'); // Mostra un messaggio di successo o errore

                $('#login-email').val('');
                $('#login-password').val('');
                updateUIAfterLogin(); // Aggiorna l'interfaccia dopo il login
            },
            error: function(xhr, status, error) {
                // Gestisci gli errori
                console.error(error);
                alert('Errore durante il login. Si prega di riprovare.');
            }
        });
    });

    // Funzione per aggiornare l'interfaccia utente dopo il login
    function updateUIAfterLogin() {
        $('#user-button').text('Profilo').removeAttr('href'); // Cambia il testo del bottone "Accedi" in "Profilo"
        $('#user-button').click(function(event) {
            event.preventDefault();
            $('#user-dropdown').toggle(); // Mostra o nasconde il menu utente
            $('#profile-page').toggle(); // Mostra o nasconde la sezione del profilo
        });
    }

    // Funzione per verificare e aggiornare lo stato di login
    function checkAndUpdateLoginStatus() {
        // Verifica lo stato di login
        $.ajax({
            url: 'auth/checkLogin', // Assicurati che corrisponda al percorso definito nel controller
            method: 'GET',
            success: function(response) {
                console.log('Risposta del server per checkLogin:', response);
                if (response.loggedIn) {
                    // Se l'utente è loggato, aggiorna l'interfaccia utente
                    updateUIAfterLogin();
                }
            },
            error: function(xhr, status, error) {
                console.error('Errore durante la verifica dello stato di login:', error);
            }
        });
    }

    // Verifica lo stato di login quando la pagina è caricata
    checkAndUpdateLoginStatus();
});
