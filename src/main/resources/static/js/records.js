let books = [];

async function getBooks() {
    const data = await fetch('http://localhost:8080/api/books');
    books = await data.json();
    console.log(books);
}

getBooks();