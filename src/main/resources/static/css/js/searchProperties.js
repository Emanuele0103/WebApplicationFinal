// searchProperties.js

$(document).ready(function() {
    $('.search-form').on('submit', function(e) {
        e.preventDefault();
        const query = $('#search-input').val();
        searchProperties(query);
    });

    function searchProperties(query) {
        $.ajax({
            url: '/announcements/featured-properties',
            type: 'GET',
            data: { address: query },
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
                                <a href="/announcements/${property.id}">
                                    <img src="/css/images/${property.images[0]}" alt="${property.titolo}">
                                    <h3>${property.titolo}</h3>
                                </a>
                                <p>${property.descrizione}</p>
                                <p>Prezzo: €${property.prezzo}</p>
                                <p>Indirizzo: ${property.position}</p>
                            </div>
                        `;
                        propertiesList.append(propertyItem);
                    });
                    noPropertiesMessage.hide();
                } else {
                    propertyTitle.text('Nessun immobile trovato');
                    noPropertiesMessage.show();
                }
            },
            error: function(xhr) {
                if (xhr.status === 404) {
                    $('#property-title').text('Nessun immobile trovato');
                    $('#no-properties-message').show();
                    $('#featured-properties-list').empty();
                } else {
                    alert('Si è verificato un errore durante la ricerca.');
                }
            }
        });
    }
});
