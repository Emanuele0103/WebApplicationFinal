src="https://code.jquery.com/jquery-3.6.0.min.js"

    $(document).ready(function() {
    $('#register-submit').click(function() {
        // Ottieni i valori dai campi del modulo
        var nome = $('#register-name').val();
        var email = $('#register-email').val();
        var password = $('#register-password').val();
        var tipo = $('#type1').val();

        // Crea un oggetto con i dati da inviare al server
        var dataToSend = {
            nome: nome,
            email: email,
            password: password,
            tipo: tipo
        };

        // Effettua una richiesta AJAX al server
        $.ajax({
            url: '/doRegister', // Assicurati che l'URL sia corretto
            method: 'POST',
            data: dataToSend, // Dati da inviare al server
            success: function(response) {
                // Gestisci la risposta del server in caso di successo
                console.log(response);
                // Puoi aggiungere qui del codice per gestire la risposta
            },
            error: function(xhr, status, error) {
                // Gestisci gli errori
                console.error(error);
            }
        });
    });
});
