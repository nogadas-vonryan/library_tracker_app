let data = [];

async function getRecords() {
    const response = await fetch('http://localhost:8080/api/books');
    data = await response.json();
    console.log(data);
}

getRecords(); 