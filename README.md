# Pac-Man en Java

Proyecto final de Programación Orientada a Objetos — Universidad Distrital Francisco José de Caldas  
Facultad de Ingeniería | Ingeniería de Sistemas  
Docente: Nancy Gélvez García | Grupo 020-83

---

## Integrantes

- Juliana Gutiérrez Rodríguez
- Aaron Steven Hernández Rojas

---

## Descripción

Versión funcional del videojuego clásico Pac-Man desarrollada en Java con Java Swing, aplicando los principios fundamentales de la Programación Orientada a Objetos. El juego cuenta con tres niveles de dificultad progresiva, tres tipos de fantasmas con comportamientos distintos, sistema de vidas, power-ups y persistencia de puntajes.

---

## Controles

| Tecla | Acción |
|-------|--------|
| `↑` `↓` `←` `→` | Mover a Pac-Man |
| `Enter` | Iniciar partida / Continuar al siguiente nivel |
| `P` | Pausar / Reanudar el juego |
| `R` | Reiniciar partida |
| `ESC` | Salir del juego |

---

## Dinámica del juego

**Objetivo:** come todos los pellets (puntos blancos) del laberinto para avanzar al siguiente nivel. Si comes todos los de los tres niveles, ganas.

**Pellets y power-ups:**
- Pellet normal — 10 puntos
- Cereza 🍒 — 100 puntos
- Naranja 🍊 — 120 puntos
- Power-up (punto cyan grande) — vuelve a los fantasmas vulnerables durante 8 segundos. Si chocas con un fantasma vulnerable ganas 200 puntos y el fantasma reaparece en otro lugar.

**Fantasmas:**
- **Rojo** — persigue a Pac-Man directamente calculando la posición más cercana.
- **Naranja** — se mueve en línea recta y elige una dirección aleatoria al encontrar una pared.
- **Rosado** — igual que el naranja pero con una posición de inicio distinta.

Si un fantasma normal choca con Pac-Man, pierdes una vida y Pac-Man vuelve a su posición inicial. El juego termina al perder las 3 vidas.

**Niveles:** cada nivel tiene un mapa distinto y los personajes se mueven más rápido.

| Nivel | Velocidad fantasmas | Velocidad Pac-Man |
|-------|--------------------|--------------------|
| 1 | Normal | Normal |
| 2 | Rápida | Rápida |
| 3 | Muy rápida | Muy rápida |

**Top 5:** al terminar cada partida (victoria o game over) el puntaje se guarda automáticamente. El top 5 se muestra en el menú principal.

---

## Requisitos

- Java 11 o superior
- No requiere dependencias externas

---

## ¿Cómo ejecutar?

**1. Clonar el repositorio**
```bash
git clone https://github.com/JuliGR05/Pacman-Java.git
cd Pacman-Java
```

**2. Compilar**
```bash
javac -sourcepath . -d out Main.java
```

**3. Ejecutar**
```bash
java -cp out Main
```

También puedes abrirlo directamente en VS Code o IntelliJ IDEA y ejecutar `Main.java`.

> **Nota:** las imágenes están en la carpeta `Recursos/`. Si el juego abre sin imágenes, asegúrate de ejecutarlo desde la raíz del proyecto.

---

## Arquitectura del repositorio

```
Pacman-Java/
│
├── Main.java                        # Punto de entrada, crea la ventana JFrame
│
├── Modelo/                          # Lógica del juego (sin dependencias de Swing)
│   ├── Personaje.java               # Clase abstracta base — define mover(), posición, dirección
│   ├── Pacman.java                  # Hereda Personaje, implementa Runnable
│   ├── Fantasma.java                # Clase abstracta — hereda Personaje, implementa Runnable
│   ├── FantasmaRojo.java            # Persigue a Pac-Man directamente
│   ├── FantasmaNaranja.java         # Movimiento lineal + aleatorio
│   ├── FantasmaRosado.java          # Movimiento lineal + aleatorio
│   ├── Laberinto.java               # Matriz 2D con los tres mapas de nivel
│   ├── ModeloJuego.java             # Orquesta el juego: hilos, colisiones, estado, niveles
│   ├── ScoreModel.java              # Maneja el puntaje
│   ├── EstadoJuego.java             # Enum: INICIO, JUGANDO, PAUSA, SIGUIENTE_NIVEL, GAME_OVER, VICTORIA
│   └── PersistenciaPuntajes.java    # Lee y escribe el top 5 en puntajes.txt
│
├── Vista/
│   └── PanelJuego.java              # Renderizado con paintComponent, botones del menú, game loop
│
├── Controlador/
│   └── ControladorJuego.java        # KeyListener — traduce teclas en acciones sobre el modelo
│
├── Recursos/                        # Imágenes de los personajes
│   ├── pacmanArriba/Abajo/Izquierda/Derecha.png
│   ├── FantasmaRojo/Naranja/Rosado/Vulnerable.png
│   ├── Cereza.png
│   └── Naranja.png
│
└── puntajes.txt                     # Top 5 generado automáticamente al jugar
```

---

## Conceptos de POO aplicados

| Concepto | Dónde |
|----------|-------|
| Clase abstracta | `Personaje`, `Fantasma` |
| Herencia | `Pacman` y `Fantasma` extienden `Personaje`; `FantasmaRojo/Naranja/Rosado` extienden `Fantasma` |
| Polimorfismo | `mover()` se comporta distinto en cada subclase de `Fantasma` |
| Encapsulamiento | Atributos privados/protected con getters y setters en todas las clases |
| Interfaces | `Pacman` y `Fantasma` implementan `Runnable` |
| Enumeración | `EstadoJuego` |

---

## Tecnologías

- Java — lenguaje principal
- Java Swing — interfaz gráfica
- Git & GitHub — control de versiones
