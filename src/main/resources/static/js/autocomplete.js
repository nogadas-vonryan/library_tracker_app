const searchbar = document.querySelector('.autocomplete-searchbar');
const suggestions = document.querySelector('.autocomplete-suggestion');

searchbar.addEventListener('input', function () {
    const query = searchbar.value.toLowerCase();

    suggestions.innerHTML = '';

    if (query === '') return;

    const filteredData = data.filter(item => item.title.toLowerCase().includes(query));
	console.log(data);
	
    if (filteredData.length > 0) {
        filteredData.forEach(item => {
            const suggestionItem = document.createElement('div');
            suggestionItem.classList.add(
				'bg-white',
                'border',
                'border-t-0',
                'p-1',
                'cursor-pointer',
                'hover:bg-primary',
                'hover:text-white');
            suggestionItem.textContent = item.title;

            suggestionItem.addEventListener('click', function () {
                location.href = `/user/books/${item.id}`;
            });

            suggestions.appendChild(suggestionItem);
        });
    }
});

document.addEventListener('click', function (event) {
    if (!event.target.closest('.autocomplete-container')) {
        suggestions.innerHTML = '';
    }
});