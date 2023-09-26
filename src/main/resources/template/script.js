let Pessoas = { pessoas: [] }
let PesoIdeal = "";
let main = document.getElementById("main");
let linhasColunas = "";

const API = "http://localhost:8080/athenas/person"

async function consultaApi() {
    await fetch(API)
        .then(dados => dados.json()).then(d => Pessoas.pessoas = d);
}

async function incluir() {
    let pessoa = newPessoa();
    await fetch(API, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(pessoa)
    })
        .then((response) => response.json())
        .then((data) => {
            console.log("Success", data);
        })
        .catch((error) => {
            console.log(error);
        })
    consultaApi()
    openLista();
}

async function excluir(id) {
    await fetch(API + id, {
        method: "DELETE",
        headers: {
            "Content-Type": "application/json; charset=UTF-8",
        }
    })
        .then((data) => {
            console.log("Success", data);
        })
        .catch((error) => {
            console.log(error);
        })
    consultaApi()
    openLista();
}

async function alterar(id) {
    let pessoa = oldPessoa(id);
    await fetch(API + id, {
        method: "PUT",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(pessoa)
    })
        .then((response) => response.json())
        .then((data) => {
            console.log("Success", data);
        })
        .catch((error) => {
            console.log(error);
        })
    consultaApi()
    openLista();
}

async function calcPesoIdealApi(id) {
    await fetch(API + "/ideal/" + id)
        .then(dados => dados.text()).then(texto => PesoIdeal = texto);
}

function consulta() {
    consultaApi();
    let tbody = document.getElementById("tbody");
    linhasColunas = "";
    Pessoas.pessoas.forEach(p => {
        linhasColunas = linhasColunas +
            `<tr id="${p.id}">
                <td>${p.nome}</td>
                <td>${p.dataNasc}</td>
                <td>${p.sexo}</td>
                <td>${p.altura}</td>
                <td>${p.peso}</td>
            </tr>`
    });
    tbody.innerHTML = linhasColunas;
}

async function calcPesoIdeal(id) {
    let btns = document.getElementById("btns");
    btns.innerHTML = `<td>${PesoIdeal}</td>`;
}


async function pesquisar() {
    await consultaApi();
    let filtro = document.getElementById("filtro");
    linhasColunas = "";
    let value = filtro.value + "";
    value = value.toLowerCase()
    Pessoas.pessoas.forEach(p => {
        let nome = p.nome + "";
        let sexo = p.sexo + "";
        let id = p.id + "";
        if (value == id.toLowerCase() || value == nome.toLowerCase() || value == sexo.toLowerCase()) {
            linhasColunas = linhasColunas +
                `<tr id="tr">
                <td>${p.nome}</td>
                <td>${p.dataNasc}</td>
                <td>${p.sexo}</td>
                <td>${p.altura}</td>
                <td>${p.peso}</td>
                <td id="btns">
                    <button type="button" class="btn btn-outline-warning" onclick="returnPessoaForm(${p.id})">Alterar</button>
                    <button type="button" class="btn btn-outline-danger" onclick="excluir(${p.id})">Excluir</button>
                    <button type="button" class="btn btn-outline-info" onclick="calcPesoIdeal(${p.id})">Calcular</button>
                </td>
            </tr>`
            calcPesoIdealApi(p.id);
        }
    });
    tbody.innerHTML = linhasColunas;
    if (value == "") {
        await consulta();
    }
    console.log(Pessoas.pessoas);
}

function newPessoa() {
    let nome = document.getElementById("nome");
    let dataNasc = document.getElementById("dataNasc");
    let sexo = document.getElementById("sexo");
    let altura = document.getElementById("altura");
    let peso = document.getElementById("peso");
    let pessoa = {
        nome: nome.value,
        dataNasc: dataNasc.value,
        sexo: sexo.value,
        altura: altura.value,
        peso: peso.value
    }
    return pessoa;
}

function returnPessoaForm(id) {
    openForm(false, id);
    let nome = document.getElementById("nome");
    let dataNasc = document.getElementById("dataNasc");
    let sexo = document.getElementById("sexo");
    let altura = document.getElementById("altura");
    let peso = document.getElementById("peso");
    Pessoas.pessoas.forEach(pessoa => {
        if (pessoa.id == id) {
            nome.value = pessoa.nome;
            dataNasc.value = pessoa.dataNasc;
            sexo.value = pessoa.sexo;
            altura.value = pessoa.altura;
            peso.value = pessoa.peso;
        }
    });
}

function oldPessoa(id) {
    let pessoa = {
        id: id,
        nome: nome.value,
        dataNasc: dataNasc.value,
        sexo: sexo.value,
        altura: altura.value,
        peso: peso.value
    }
    return pessoa;
}


function openLista() {
    main.innerHTML = "";
    let lista =
        `<div class="input-group input-group-sm mb-3">
            <input type="text" id="filtro" class="form-control" aria-label="Sizing example input" aria-describedby="inputGroup-sizing-sm">
            <button class="input-group-text btn btn-outline-primary" onclick="pesquisar()">Pesquisa</button>
            <button class="input-group-text btn btn-outline-success" onclick="openForm(true, null)">Incluir</button>
        </div>
        <table class="table">
            <thead>
                <tr>
                    <th>Nome</th>
                    <th>Data Nascimento</th>
                    <th>Sexo</th>
                    <th>Altura</th>
                    <th>Peso</th>
                </tr>
            </thead>
            <tbody id="tbody"></tbody>
        </table>`;
    main.innerHTML = lista;
}

function openForm(incluir, id) {
    main.innerHTML = "";
    let btn = "";
    if (incluir) {
        btn = '<button class="btn btn-outline-success" type="submit" onclick="incluir()">Salvar</button>';
    } else {
        btn = `<button class="btn btn-outline-warning" type="submit" onclick="alterar(${id})">Alterar</button>`;
    }
    let form =
        `<div class="row">
            <div class="mb-3 col-6">
                <label class="form-label" for="nome">Nome</label>
                <input class="form-control" type="text" id="nome">
            </div>
            <div class="mb-3 col-6">
                <label class="form-label" for="dataNasc">Data de Nascimento</label>
                <input class="form-control" type="text" id="dataNasc">
            </div>
            <div class="mb-3 col-6">
                <label class="form-label" for="sexo">Sexo</label>
                <input class="form-control" type="text" id="sexo">
            </div>
            <div class="mb-3 col-6">
                <label class="form-label" for="altura">Altura</label>
                <input class="form-control" type="text" id="altura">
            </div>
            <div class="mb-3 col-6">
                <label class="form-label" for="peso">Peso</label>
                <input class="form-control" type="text" id="peso">
            </div>
            ${btn}
            <button class="btn btn-outline-info mt-2" onclick="openLista()" type="button">Voltar</button>
        </div>
            `
    main.innerHTML = form;
}

