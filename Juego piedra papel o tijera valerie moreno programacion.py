import random

opciones = ["piedra", "papel", "tijera", "lagarto", "spock"]

reglas = {
    "piedra": {"lagarto": "Aplasta Lagarto", "tijera": "Aplasta Tijera"},
    "papel": {"piedra": "Envuelve Piedra", "spock": "Destroza Spock"},
    "tijera": {"papel": "Corta Papel", "lagarto": "Decapita Lagarto"},
    "lagarto": {"spock": "Envenena Spock", "papel": "Devora Papel"},
    "spock": {"tijera": "Rompe Tijera", "piedra": "Vence Piedra"}
}

puntuacion = {"Usuario": 0, "Computadora": 0, "Empates": 0}

racha_actual = 0
mejor_racha = 0

jugando = True

while jugando:
    print("Opciones disponibles: piedra, papel, tijera, lagarto, spock")
    jugada_usuario = input("Elige tu jugada: ").lower()

    if jugada_usuario not in opciones:
        print("Opción no válida, intenta de nuevo.")
        continue

    jugada_computadora = random.choice(opciones)
    print("La computadora eligió:", jugada_computadora)

    if jugada_usuario == jugada_computadora:
        print("Empate")
        puntuacion["Empates"] = puntuacion["Empates"] + 1
        racha_actual = 0
    elif jugada_computadora in reglas[jugada_usuario]:
        razon = reglas[jugada_usuario][jugada_computadora]
        print("Ganaste!", jugada_usuario, razon, jugada_computadora)
        puntuacion["Usuario"] = puntuacion["Usuario"] + 1
        racha_actual = racha_actual + 1
        if racha_actual > mejor_racha:
            mejor_racha = racha_actual
    elif jugada_usuario in reglas[jugada_computadora]:
        razon = reglas[jugada_computadora][jugada_usuario]
        print("Perdiste!", jugada_computadora, razon, jugada_usuario)
        puntuacion["Computadora"] = puntuacion["Computadora"] + 1
        racha_actual = 0
    else:
        print("Resultado desconocido")

    print("Racha actual de victorias:", racha_actual)

    continuar = input("¿Quieres jugar otra ronda? (super si /no ya me rindo xd): ").lower()
    if continuar != "s":
        jugando = False

print("")
print("Resumen final de la partida")
for clave in puntuacion:
    print(clave, ":", puntuacion[clave])
print("Mejor racha de victorias seguidas:", mejor_racha)
print("Gracias por jugar")