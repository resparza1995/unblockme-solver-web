var fc = document.getElementById("filechooser");
// Read file as text
function read(file, callback) {
    var reader = new FileReader();

    reader.onload = function() {
        callback(reader.result);
    }

    reader.readAsText(file);
}
// Listener filechooser
fc.addEventListener("change", () => {
    read(fc.files.item(0), (res) => {
        drawEstado(res);
        fetch("/estado", {
                method: 'POST',
                body: JSON.stringify(res),
                headers: {
                    'Content-Type': 'application/json'
                }
            }).then(res => res.json())
            .catch(error => console.error('Error:', error))
            .then(response => console.log('Success:', response));
    })
});

// Listener boton resolver
document.getElementById("solve-btn").addEventListener("click", () => {
    // Check box
    var bfscbox = document.getElementById("bfscbox").checked;
    var dfscbox = document.getElementById("dfscbox").checked;
    var astarcbox = document.getElementById("astarcbox").checked;

    var path = "";
    if (bfscbox) path = "/bfs";
    else if (dfscbox) path = "/dfs";
    else if (astarcbox) path = "/aestar";

    if (path != "") {
        fetch(path, {
                method: 'GET'
            }).then(res => res.json())
            .catch(error => console.error('Error:', error))
            .then(response => {
                getEstadoFinal();
                printSteps(response)
                console.log('Success:', response);
            });
    }
});

function printSteps(solution) {
    var html = "";
    var count = 0;
    var t = document.getElementById("steps-rows");
    solution.forEach(elem => {
        count++;
        html += `
        <tr>
        <th> ${count} </th>
        <td> ${elem["bloque"]} </td>
        <td> ${elem["pasos"]} </td>
        </tr>
      `;
    });
    t.innerHTML = html;
    alert("Numero de pasos: " + solution.length)
}


function getEstadoFinal() {
    fetch("/estadoFinal", {
            method: 'GET'
        }).then(res => res.json())
        .catch(error => console.error('Error:', error))
        .then(response => {
            console.log('Success:', response);
            drawEstado(response);
        });
}

function drawEstado(estado) {

    if (estado != null) {
        var bloques = estado;
        if (typeof(estado) === "string") { // cuando lee el fichero con el tablero.
            estado = estado.substring(2); // primer caracter es el nivel de dificultad y el segundo es un salto de linea.
            estado = estado.replaceAll("\n", " ");
            bloques = estado.split(" ");
        }
        var html = "";
        let rowindex = 1;
        var colors = setColorToBloque(bloques);
        var maxcol = 6;
        let bloque;

        console.log(bloques)
        for (let i = 0; i < 36; i++) {
            bloque = bloques[i];
            maxcol--;
            html += `
                <div class="col" style="background-color:${colors.get(bloque)}; padding:3%">${bloque}</div>
            `;
            if (maxcol == 0) {
                document.getElementById("row" + rowindex).innerHTML = html;
                html = "";
                maxcol = 6;
                rowindex++;
            }
        }
    }
}

function setColorToBloque(bloques) {
    var colorMap = new Map();
    colorMap.set('-', '#F1F1F1F1')
    colorMap.set('X', "#7CFF00")
    bloques.forEach(element => {
        if (element != '-' || element != 'X') {
            if (!colorMap.has(element)) {
                colorMap.set(element, colorRGB());
            }
        };
    });
    return colorMap;
}

function generarNumero(numero) {
    return (Math.random() * numero).toFixed(0);
}

function colorRGB() {
    var coolor = "(" + generarNumero(255) + "," + generarNumero(255) + "," + generarNumero(255) + ")";
    return "rgb" + coolor;
}