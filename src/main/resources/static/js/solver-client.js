var fc = document.getElementById("filechooser");
var colors;

/**
 * Lee un fichero y lo devuelve mediante 
 * el callback como un string.
 * @param {*} file 
 * @param {*} callback 
 */
function read(file, callback) {
    var reader = new FileReader();

    reader.onload = function() {
        callback(reader.result);
    }

    reader.readAsText(file);
}

// ### LISTENERS ###

/**
 * FileReader listener
 * Lee un fichero .puzzle y lo dibuja. 
 * Hace una peticion POST para guardar el estado del puzzle.
 */
fc.addEventListener("change", () => {
    read(fc.files.item(0), (res) => {
        var extension = fc.files.item(0).name.split('.').pop().toLowerCase();
        if(extension === 'puzzle') {
            fetch("/estado", {
                    method: 'POST',
                    body: res,
                    headers: {
                        'Content-Type': 'application/json'
                    }
                }).then(res => res.json())
                .catch(error => console.error('Error:', error))
                .then(estado => drawEstado(estado, "readfile"));
        }
        else {
            alert("The file must have a .puzzle extension.")
        }
    })
});

/**
 * Solve button Listener
 * Revisa que checkbox ha sido seleccionado y hace
 * una peticion GET para recibir la solucion con el algoritmo
 * seleccionado.
 */
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
                printSteps(response);
            });
    }
});

/**
 * Listener Select element
 * Envia una peticion POST con el identificador del puzzle
 * de demo para que se cargue en caso de no querer subir un fichero
 * .puzzle.
 */
document.getElementById("puzzle-select").addEventListener("change", () => {
		
    let elem = document.getElementById("puzzle-select");
    let val = elem.value;
    
    fetch('/setEstadoById', {
            method: 'POST',
            body: JSON.stringify(val),
            headers: {
                'Content-type':'application/json'
            }
        }).then(res => res.json())
        .catch(error => console.error('Error:', error))
        .then(response => {
            drawEstado(response, "select");
        });

});

// ### FUNCTIONS ###

/**
 * Imprime los pasos en formato tabla.
 * @param {*} solution 
 */
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

/**
 * GET - Obtiene el estado final del puzzle cargado
 * actualmente y lo imprime.
 */
function getEstadoFinal() {
    fetch("/estadoFinal", {
            method: 'GET'
        }).then(res => res.json())
        .catch(error => console.error('Error:', error))
        .then(response => {
            drawEstado(response, "call");
        });
}

/**
 * Dibuja en el tablero el estado pasado por parametro.
 * Estado debe ser un array[]: cada bloque un elemento.
 * @param {*} estado 
 */
function drawEstado(estado, event) {

    if (estado != null) {
        var bloques = estado;

        var html = "";
        let rowindex = 1;

        if (event === "select" || event === "readfile"){
            colors = setColorToBloque(bloques);
        }

        var maxcol = 6;
        let bloque;

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

/**
 * Elige un color aleatorio para cada tipo de bloque
 * @param {*} bloques 
 * @returns {map} Map con los colores para cada bloque.
 */
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

/**
 * Genera un numero aleatorio, entre 0 y el
 * valor pasado por parametro.
 * @param {*} numero 
 * @returns {int} entre 0 y param
 */
function generarNumero(numero) {
    return (Math.random() * numero).toFixed(0);
}

/**
 * Devuelve un color aleatorio con el formato
 * rgb(r,g,b).
 * @returns {string} rgb(r,g,b)
 */
function colorRGB() {
    var coolor = "(" + generarNumero(255) + "," + generarNumero(255) + "," + generarNumero(255) + ")";
    return "rgb" + coolor;
}


