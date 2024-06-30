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

        // Esempio di come puoi gestire i dati raccolti dal form
        var formData = new FormData();
        formData.append('titolo', titolo);
        formData.append('tipoDiImmobile', tipoImmobile);
        formData.append('prezzo', prezzo);
        formData.append('descrizione', descrizione);
        formData.append('position', indirizzo); // Assicurati che il nome sia 'position' come nel controller
        for (var i = 0; i < images.length; i++) {
            formData.append('images', images[i]);
        }

        // Esempio di invio tramite AJAX con jQuery
        $.ajax({
            url: '/annunci/aggiungi',
            type: 'POST',
            data: formData,
            processData: false,
            contentType: false,
            success: function(response) {
                console.log('Risposta dal server:', response);
                if (response === 'Annuncio aggiunto con successo') {
                    // Reindirizza alla pagina del profilo
                    window.location.href = '/profile';
                } else {
                    console.error('Errore durante l\'aggiunta dell\'annuncio');
                }
            },
            error: function(error) {
                console.error('Errore durante l\'invio dei dati:', error);
            }
        });
    });
});
