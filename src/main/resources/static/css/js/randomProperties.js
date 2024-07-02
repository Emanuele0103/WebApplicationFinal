$(document).ready(function() {

    // Funzione per ottenere e visualizzare gli annunci in base all'indirizzo
    function getFeaturedProperties(address) {
        $.get("/announcements/featured-properties", { address: address }, function(data) {
            // 'data' conterrà la lista di annunci che corrispondono all'indirizzo
            displayProperties(data);
        });
    }

    // Funzione per visualizzare gli annunci nella sezione
    function displayProperties(properties) {
        var featuredPropertiesList = $('#featured-properties-list');
        featuredPropertiesList.empty(); // Svuota la lista precedente, se presente

        properties.forEach(function(property) {
            var propertyItem = `
                <div class="property-item">
                    <img src="/css/images/${property.images[0]}" alt="${property.titolo}">
                    <h3>${property.titolo}</h3>
                    <p>${property.descrizione}</p>
                    <p>Prezzo: ${property.prezzo}</p>
                    <p>Tipo di immobile: ${property.tipoDiImmobile}</p>
                </div>
            `;
            featuredPropertiesList.append(propertyItem);
        });
    }

    // Gestione dell'evento di submit del form di ricerca
    $('.search-form').submit(function(event) {
        event.preventDefault(); // Impedisci il comportamento predefinito del form
        var address = $(this).find('input[type="text"]').val(); // Ottieni l'indirizzo inserito

        // Chiamata per ottenere gli annunci in base all'indirizzo
        getFeaturedProperties(address);
    });

    // Chiamata iniziale per ottenere e visualizzare gli annunci in evidenza all'avvio della pagina
    getFeaturedProperties('');
});

$(document).ready(function() {
    $('.search-form').on('submit', function(e) {
        e.preventDefault();
        const query = $('#search-input').val();
        searchProperties(query);
    });

    function searchProperties(query) {
        $.ajax({
            url: '/search',
            type: 'GET',
            data: { position: query },
            success: function(data) {
                const propertiesList = $('#featured-properties-list');
                const propertyTitle = $('#property-title');
                const noPropertiesMessage = $('#no-properties-message');

                propertiesList.empty();

                if (data.length > 0) {
                    propertyTitle.text('Proprietà trovate');
                    data.forEach(property => {
                        const propertyItem = `
                            <div class="property-item">
                                <img src="${property.images[0]}" alt="${property.titolo}">
                                <h3>${property.titolo}</h3>
                                <p>${property.descrizione}</p>
                                <p>Prezzo: €${property.prezzo}</p>
                                <p>Indirizzo: ${property.position}</p>
                            </div>
                        `;
                        propertiesList.append(propertyItem);
                    });
                    noPropertiesMessage.hide();
                } else {
                    propertyTitle.text('Proprietà trovate');
                    noPropertiesMessage.show();
                }
            },
            error: function() {
                alert('Si è verificato un errore durante la ricerca.');
            }
        });
    }
});

function displayProperties(properties) {
    var featuredPropertiesList = $('#featured-properties-list');
    featuredPropertiesList.empty(); // Svuota la lista precedente, se presente

    properties.forEach(function(property) {
        var propertyItem = `
            <div class="property-item" onclick="openPropertyModal('${property.titolo}', '${property.descrizione}', '${property.prezzo}', '${property.tipoDiImmobile}', '${property.position}', '${property.images}')">
                <img src="/css/images/${property.images[0]}" alt="${property.titolo}">
                <h3>${property.titolo}</h3>
                <p>${property.descrizione}</p>
                <p>Prezzo: ${property.prezzo}</p>
                <p>Tipo di immobile: ${property.tipoDiImmobile}</p>
            </div>
        `;
        featuredPropertiesList.append(propertyItem);
    });
}

// Funzione per aprire il modal dell'annuncio
function openPropertyModal(titolo, descrizione, prezzo, tipoDiImmobile, position, images) {
    var modal = $('#property-modal');
    var modalTitle = $('#modal-title');
    var modalImage = $('#modal-image');
    var modalDescription = $('#modal-description');
    var modalPrice = $('#modal-price');
    var modalType = $('#modal-type');
    var modalAddress = $('#modal-address');

    modalTitle.text(titolo);
    modalImage.attr('src', `/css/images/${images[0]}`);
    modalImage.attr('alt', titolo);
    modalDescription.text(descrizione);
    modalPrice.text(`Prezzo: €${prezzo}`);
    modalType.text(`Tipo di immobile: ${tipoDiImmobile}`);
    modalAddress.text(`Indirizzo: ${position}`);

    modal.css('display', 'block');

    // Funzione per chiudere il modal cliccando sullo 'x'
    var closeModal = $('.close');
    closeModal.on('click', function() {
        modal.css('display', 'none');
    });

    // Funzioni per la navigazione delle immagini nel carousel
    var currentIndex = 0;
    var imagesArray = images;
    var carouselImage = $('#modal-image');

}
function prevSlide() {
    currentIndex = (currentIndex - 1 + imagesArray.length) % imagesArray.length;
    carouselImage.attr('src', `/css/images/${imagesArray[currentIndex]}`);
}

function nextSlide() {
    currentIndex = (currentIndex + 1) % imagesArray.length;
    carouselImage.attr('src', `/css/images/${imagesArray[currentIndex]}`);
}


