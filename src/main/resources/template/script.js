let pessoas = [];
const json = fetch("http://localhost:8080/athenas/")
    .then(dados => dados.json()).then(d => pessoas.push(d));

let table = document.getElementById("table");

json.then(d => pessoas.push(d));
console.log(json);
console.log(pessoas);