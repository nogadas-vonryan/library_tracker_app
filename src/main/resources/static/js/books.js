let data = [];

async function getBooks() {
    const response = await fetch('http://localhost:8080/api/books');
    data = await response.json();
    console.log(data);
}

getBooks(); 