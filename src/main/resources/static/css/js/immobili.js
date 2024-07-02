$(document).ready(function() {
    // Funzione per caricare tutti gli annunci
    function loadProperties() {
        $.getJSON('/api/immobili', function(data) {
            console.log('Risposta ricevuta:', data);

            // Pulisce le liste di proprietà attuali
            $('.property-list').empty();

            // Itera attraverso le categorie di immobili
            $.each(data, function(category, properties) {
                var listId;
                switch (category) {
                    case 'appartamento':
                        listId = '#appartamenti .property-list';
                        break;
                    case 'villa':
                        listId = '#ville .property-list';
                        break;
                    case 'box_auto':
                        listId = '#box_auto .property-list';
                        break;
                    case 'terreno_edificabile':
                        listId = '#terreno_edificabile .property-list';
                        break;
                    default:
                        return;
                }

                var listElement = $(listId);

                // Aggiungi le proprietà alla lista HTML
                properties.forEach(function(property) {
                    var propertyHtml = `
                    <div class="property-item" data-id="${property.id}">
                        <img src="/css/images/${property.images[0] || 'default.jpeg'}" alt="${property.titolo}">
                        <h3>${property.titolo}</h3>
                        <p>${property.descrizione}</p>
                        <p>${property.prezzo}€</p>
                    </div>
                    `;
                    listElement.append(propertyHtml);
                });
            });

            // Gestione click sulle proprietà per aprire il modal
            $(document).on('click', '.property-item', function() {
                var annuncioId = $(this).data('id');
                $.getJSON(`/api/immobili/${annuncioId}`, function(annuncio) {
                    $('#modal-title').text(annuncio.titolo);
                    $('#carousel-image').attr('src', `/css/images/${annuncio.images[0] || 'default.jpeg'}`);
                    $('#carousel-image').data('images', annuncio.images); // Imposta l'attributo data-images con le immagini dell'annuncio
                    $('#modal-description').text(annuncio.descrizione);
                    $('#modal-price').text(`Prezzo: ${annuncio.prezzo}€`);
                    openModal('modal-details');
                });
            });
        }).fail(function(jqxhr, textStatus, error) {
            console.error('Request Failed: ' + textStatus + ', ' + error);
        });
    }

    // Carica gli annunci all'avvio della pagina
    loadProperties();
});

// Variabile per tenere traccia dell'indice dell'immagine corrente nel carousel
var currentImageIndex = 0;

// Funzione per aprire il modal
function openModal(modalId) {
    $('#' + modalId).css('display', 'block');
}

// Funzione per chiudere il modal
function closeModal(modalId) {
    $('#' + modalId).css('display', 'none');
}

// Funzione per navigare alla slide precedente nel carousel
function prevSlide() {
    var images = $('#carousel-image').data('images');
    if (images && images.length > 0) {
        currentImageIndex = (currentImageIndex - 1 + images.length) % images.length;
        $('#carousel-image').attr('src', '/css/images/' + images[currentImageIndex]);
    }
}

// Funzione per navigare alla slide successiva nel carousel
function nextSlide() {
    var images = $('#carousel-image').data('images');
    if (images && images.length > 0) {
        currentImageIndex = (currentImageIndex + 1) % images.length;
        $('#carousel-image').attr('src', '/css/images/' + images[currentImageIndex]);
    }
}
