$(document).ready(function() {
    loadRandomProperties(); // Chiamata iniziale per caricare annunci casuali all'avvio della pagina

    // Funzione per caricare annunci casuali
    function loadRandomProperties(address) {
        $.ajax({
            url: '/announcements/featured-properties',
            type: 'GET',
            data: { address: address }, // Passa la posizione come parametro
            success: function(data) {
                var featuredPropertiesList = $('.property-list');
                featuredPropertiesList.empty();

                if (data.length > 0) {
                    data.forEach(function(property) {
                        var propertyItem = `
                            <div class="property-item" onclick="openPropertyModal('${property.titolo}', '${property.descrizione}', '${property.prezzo}', '${property.tipoDiImmobile}', '${property.position}', ${JSON.stringify(property.images)})">
                                <img src="/css/images/${property.images[0]}" alt="${property.titolo}">
                                <h3>${property.titolo}</h3>
                                <p>${property.descrizione}</p>
                                <p>Prezzo: ${property.prezzo}</p>
                                <p>Tipo di immobile: ${property.tipoDiImmobile}</p>
                            </div>
                        `;
                        featuredPropertiesList.append(propertyItem);
                    });
                } else {
                    featuredPropertiesList.append('<p>Nessuna proprietà in evidenza al momento.</p>');
                }
            },
            error: function() {
                alert('Si è verificato un errore durante il caricamento degli annunci casuali.');
            }
        });
    }

    // Gestore per il submit del form di ricerca
    $('.search-form').submit(function(event) {
        event.preventDefault(); // Evita il comportamento di default del submit

        var address = $(this).find('input[type=text]').val(); // Recupera la posizione inserita dall'utente
        loadRandomProperties(address); // Carica gli annunci con la posizione specificata
    });

    // Funzione per aprire un modal con i dettagli dell'annuncio
    window.openPropertyModal = function(titolo, descrizione, prezzo, tipoDiImmobile, position, images) {
        alert('Dettagli dell\'annuncio:\n\nTitolo: ' + titolo + '\nDescrizione: ' + descrizione + '\nPrezzo: ' + prezzo + '\nTipo di immobile: ' + tipoDiImmobile + '\nPosizione: ' + position);
    };
});
