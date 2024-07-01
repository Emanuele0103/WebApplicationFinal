document.addEventListener('DOMContentLoaded', function() {
    var adsList = document.getElementById('ads-list');

    // Funzione per caricare gli annunci dal server
    function loadMyAds() {
        $.ajax({
            url: '/announcements/myAnnouncements', // Endpoint per recuperare gli annunci dell'utente loggato
            type: 'GET',
            success: function(response) {
                // Pulisce la lista degli annunci attuale
                adsList.innerHTML = '';

                // Costruisce gli elementi HTML per ogni annuncio ricevuto
                response.forEach(function(annuncio) {
                    var adItem = document.createElement('div');
                    adItem.classList.add('ad-item');
                    adItem.onclick = function() {
                        openModal('modal' + annuncio.id); // Definire la funzione openModal() appropriata
                    };

                    var img = document.createElement('img');
                    img.src = `/css/images/${annuncio.images[0]}` || '/css/images/default.jpeg'; // Usa un'immagine di default se non è disponibile
                    img.alt = 'Annuncio ' + annuncio.id;
                    adItem.appendChild(img);

                    var adDetails = document.createElement('div');
                    adDetails.classList.add('ad-details');

                    var h3 = document.createElement('h3');
                    h3.textContent = annuncio.titolo;
                    adDetails.appendChild(h3);

                    var p1 = document.createElement('p');
                    p1.textContent = annuncio.descrizione; // Utilizza descrizione al posto di caratteristiche
                    adDetails.appendChild(p1);

                    var p2 = document.createElement('p');
                    p2.textContent = `Prezzo: €${annuncio.prezzo}`;
                    adDetails.appendChild(p2);

                    adItem.appendChild(adDetails);
                    adsList.appendChild(adItem);

                    // Aggiungi modal per l'annuncio
                    var modal = document.createElement('div');
                    modal.id = 'modal' + annuncio.id;
                    modal.classList.add('modal');
                    modal.innerHTML = `
                        <div class="modal-content">
                            <span class="close" onclick="closeModal('modal${annuncio.id}')">&times;</span>
                            <h2>${annuncio.titolo}</h2>
                            <div class="carousel">
                                <button class="carousel-button prev" onclick="prevSlide(${annuncio.id})">&#10094;</button>
                                <img class="carousel-image" id="carousel-image${annuncio.id}" src="/css/images/${annuncio.images[0]}" alt="${annuncio.titolo}" data-images='${JSON.stringify(annuncio.images)}'>
                                <button class="carousel-button next" onclick="nextSlide(${annuncio.id})">&#10095;</button>
                            </div>
                            <p>${annuncio.descrizione}</p>
                            <p>Prezzo: €${annuncio.prezzo}</p>
                            <button class="delete-button" onclick="confirmDelete(${annuncio.id})">Elimina</button>
                        </div>
                    `;
                    document.body.appendChild(modal);
                });
            },
            error: function(error) {
                console.error('Errore durante il recupero degli annunci:', error);
            }
        });
    }

    // Carica gli annunci all'avvio della pagina
    loadMyAds();
});

// Funzione per aprire il modal
function openModal(modalId) {
    const modal = document.getElementById(modalId);
    if (modal) {
        modal.style.display = 'block';
    }
}

// Funzione per chiudere il modal
function closeModal(modalId) {
    const modal = document.getElementById(modalId);
    if (modal) {
        modal.style.display = 'none';
    }
}

// Funzione per confermare l'eliminazione di un annuncio
function confirmDelete(id) {
    if (confirm("Sei sicuro di voler eliminare questo annuncio?")) {
        deleteAnnuncio(id);
    }
}

// Funzione per eliminare un annuncio
function deleteAnnuncio(id) {
    $.ajax({
        url: '/announcements/delete/' + id,
        type: 'DELETE',
        success: function(response) {
            // Rimuovi il modal e ricarica gli annunci
            document.getElementById('modal' + id).remove();
            loadMyAds();
        },
        error: function(error) {
            console.error('Errore durante l\'eliminazione dell\'annuncio:', error);
        }
    });
}


// Funzione per navigare alla slide precedente nel carousel
function prevSlide(id) {
    var carouselImage = document.getElementById(`carousel-image${id}`);
    var images = JSON.parse(carouselImage.dataset.images);
    var currentImage = parseInt(carouselImage.dataset.currentImage) || 0;
    currentImage = (currentImage - 1 + images.length) % images.length;
    carouselImage.src = `/css/images/${images[currentImage]}`;
    carouselImage.dataset.currentImage = currentImage;
}

// Funzione per navigare alla slide successiva nel carousel
function nextSlide(id) {
    var carouselImage = document.getElementById(`carousel-image${id}`);
    var images = JSON.parse(carouselImage.dataset.images);
    var currentImage = parseInt(carouselImage.dataset.currentImage) || 0;
    currentImage = (currentImage + 1) % images.length;
    carouselImage.src = `/css/images/${images[currentImage]}`;
    carouselImage.dataset.currentImage = currentImage;
}