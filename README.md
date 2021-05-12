# unblockme-solver-web
Java, Spring boot, bootstrap.  
[Deployed in heroku](https://unblockme-solver-web.herokuapp.com/)

Busca solucion a puzzles estilo unblockme utilizando los siguientes algoritmos de busqueda:
- Busqueda en anchura (BFS)
- Busqueda en profundidad (DFS)
- Busqueda A estrella

## Cargar un puzzle
Hay un menu desplegable con puzzles de ejemplo para elegir.
Para importar un puzzle personalizado ha de tener el siguiente formato:

Archivo de texto con extension .puzzle, 6x6. La primera linea es informativa, se refiere al nivel de dificultad de los puzzles de ejemplo
que hay en la carpeta /puzzles.

- 8
- a a - - - b
- c - - d - b
- c X X d - b
- c - - d - -
- e - - - f f
- e - g g g -
