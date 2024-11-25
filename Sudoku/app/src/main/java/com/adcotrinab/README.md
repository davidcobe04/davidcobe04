#SHORT DAM GAMES
#Un proyecto colaborativo de DAM2

**Pasos para incorporar un juego**

1. Elijo mi prefijo, basado en mi apellido y las iniciales de mi nombre. Lo utilizaré para 
cualquier nombre personalizado.

2. En res/values/games_arrays se incorpora la información correspondiente al juego, siempre siguiendo 
el mismo orden en el array (si el nombre de mi juego está en la posición 3, el resto de los datos 
deben estar en la misma posición)

3. En navigation/Destinations se añaden las tres screens del juego con mi prefijo antes.

4. En navigation/Navigation se incorporan las tres rutas, para poder saltar entre las diferentes screens 

5. En ui/gamescreens se incorporan las tres screens creadas. La primera con la descripción (usando 
las funciones de GenericComponents), la segunda con el juego y la tercera con el resultado de la partida

Cualquier recurso que necesitemos (imágenes, listas, etc.) se añade a **res** usando nuestro prefijo
en el nombre, seguido del nombre del recurso.