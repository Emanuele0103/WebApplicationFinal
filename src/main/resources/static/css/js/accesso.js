$(document).ready(function() {
    // Funzione per gestire il click sul tab di registrazione
    $('#register-tab').click(function() {
        $('#login-tab').removeClass('active');
        $('#register-tab').addClass('active');
        $('#login-form').removeClass('visible').addClass('hidden');
        $('#register-form').removeClass('hidden').addClass('visible');
    });

    // Funzione per gestire il click sul tab di login
    $('#login-tab').click(function() {
        $('#login-tab').addClass('active');
        $('#register-tab').removeClass('active');
        $('#login-form').removeClass('hidden').addClass('visible');
        $('#register-form').removeClass('visible').addClass('hidden');
    });

    // Gestione della registrazione
    $('#register-submit').click(function(event) {
        event.preventDefault(); // Evita il comportamento di default del form

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
                alert(response); // Mostra un messaggio di successo o errore

                $('#register-name').val('');
                $('#register-cognome').val('');
                $('#register-email').val('');
                $('#register-password').val('');
                $('#type1').val('');
                // Puoi aggiungere qui del codice per gestire la risposta, ad esempio reindirizzare l'utente
            },
            error: function(xhr, status, error) {
                // Gestisci gli errori
                console.error(error);
                alert('Errore durante la registrazione. Si prega di riprovare.');
            }
        });
    });

    // Funzione per gestire il login (opzionale, se implementato)
    $('#login-submit').click(function(event) {
        event.preventDefault(); // Evita il comportamento di default del form

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

                // Puoi aggiungere qui del codice per gestire la risposta, ad esempio reindirizzare l'utente
            },
            error: function(xhr, status, error) {
                // Gestisci gli errori
                console.error(error);
                alert('Errore durante il login. Si prega di riprovare.');
            }
        });
    });

    // Codice per simulare l'autenticazione e il controllo del ruolo dell'utente
    const userRole = 3; // Sostituisci con la logica reale per ottenere il ruolo dell'utente dal backend

    if (userRole !== null) {
        $('#user-button').text('Profilo').attr('href', 'profile'); // Aggiorna il link al profilo dell'utente
        const restrictedLinks = $('.restricted');
        restrictedLinks.each(function() {
            if (parseInt($(this).data('role')) === userRole) {
                $(this).show(); // Mostra i link riservati per il ruolo corrente
            } else {
                $(this).hide(); // Nasconde i link per gli altri ruoli
            }
        });
    } else {
        $('#user-dropdown').hide(); // Nasconde il menu utente se non autenticato
    }
});