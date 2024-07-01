document.addEventListener('DOMContentLoaded', function() {
    var form = document.getElementById('add-ad-form');

    form.addEventListener('submit', function(event) {
        event.preventDefault(); // Evita il comportamento di submit predefinito

        var titolo = document.getElementById('title').value;
        var tipoImmobile = document.getElementById('type').value;
        var prezzo = document.getElementById('price').value;
        var descrizione = document.getElementById('description').value;
        var indirizzo = document.getElementById('position').value;
        var images = document.getElementById('image').files;

        // Creazione di un oggetto FormData per l'invio dei dati
        var formData = new FormData();
        formData.append('titolo', titolo);
        formData.append('tipoDiImmobile', tipoImmobile);
        formData.append('prezzo', prezzo);
        formData.append('descrizione', descrizione);
        formData.append('position', indirizzo);

        for (var i = 0; i < images.length; i++) {
            formData.append('images', images[i]);
        }

        // Esempio di invio tramite AJAX con jQuery
        $.ajax({
            url: '/announcements/addAnnouncements', // Verifica che l'URL sia corretto
            type: 'POST',
            data: formData,
            processData: false,
            contentType: false,
            success: function(response) {
                console.log('Risposta dal server:', response);
                if (response === 'Annuncio aggiunto con successo') {
                    // Reindirizza alla pagina del profilo dopo l'aggiunta dell'annuncio
                    window.location.href = '/profile';
                } else {
                    console.error('Errore durante l\'aggiunta dell\'annuncio');
                    // Gestire il caso di errore in modo appropriato
                }
            },
            error: function(error) {
                console.error('Errore durante l\'invio dei dati:', error);
                // Gestire il caso di errore di invio in modo appropriato
            }
        });
    });
});
