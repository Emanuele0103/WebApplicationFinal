document.addEventListener('DOMContentLoaded', function() {
    fetch('getMyAds.php')
        .then(response => response.json())
        .then(data => {
            if (data.status === 'success') {
                const adsList = document.getElementById('ads-list');
                data.annunci.forEach(ad => {
                    const adElement = document.createElement('div');
                    adElement.classList.add('ad-item');

                    adElement.innerHTML = `
                        <h3>${ad.titolo}</h3>
                        <img src="${ad.percorso_immagine.split(',')[0]}" alt="${ad.titolo}">
                        <p>Tipo: ${ad.tipo}</p>
                        <p>Posizione: ${ad.posizione}</p>
                        <p>Descrizione: ${ad.descrizione}</p>
                        <p>Prezzo: €${ad.prezzo}</p>
                    `;

                    adsList.appendChild(adElement);
                });
            } else {
                console.error(data.message);
            }
        })
        .catch(error => console.error('Errore:', error));
});
