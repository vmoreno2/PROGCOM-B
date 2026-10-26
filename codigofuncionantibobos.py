import tkinter as tk
from tkinter import messagebox, simpledialog
import os
import random

ROSA_FONDO = "#ffe4f0"
ROSA_FUERTE = "#ff5fa2"
ROSA_BOTON = "#ff8fc1"
LETRA = "Comic Sans MS"

# Clase que guarda y revisa los usuarios en un archivo de texto
class Usuarios:

    # Crea la lista de usuarios y la llena con lo que hay en el archivo
    def __init__(self):
        self.archivo = "usuarios.txt"
        self.lista = {}
        self.cargar()

    # Lee los usuarios que ya están guardados en el archivo
    def cargar(self):
        if os.path.exists(self.archivo):
            archivo = open(self.archivo, "r")
            for linea in archivo:
                datos = linea.strip().split(",")
                if len(datos) == 3:
                    self.lista[datos[0]] = [datos[1], datos[2]]
            archivo.close()

    # Guarda un usuario nuevo en el archivo
    def guardar(self, usuario, clave, palabra):
        self.lista[usuario] = [clave, palabra]
        archivo = open(self.archivo, "a")
        archivo.write(usuario + "," + clave + "," + palabra + "\n")
        archivo.close()

    # Revisa si el usuario existe y si la contraseña es correcta
    def revisar(self, usuario, clave):
        if usuario in self.lista and self.lista[usuario][0] == clave:
            return True
        return False

# Clase de la ventana del login
class Login:

    # Arma la ventana rosada con las cajas de texto y los botones
    def __init__(self, ventana):
        self.ventana = ventana
        self.usuarios = Usuarios()
        self.intentos = 0
        self.burlas = 0
        self.max_burlas = 5
        self.descansando = False
        self.color_actual = 0
        self.colores = ["#ff5fa2", "#ff8fc1", "#c77dff", "#ffb3d1"]
        self.frases = ["¡No me atrapas!", "Jeje ♥", "¿Me buscabas?", "Muy lento ✿", "Por aquí no", "Casi ♡"]

        ventana.title("♡ Login ♡")
        ventana.geometry("360x570")
        ventana.configure(bg=ROSA_FONDO)
        ventana.resizable(False, False)

        self.decoracion = tk.Label(ventana, text="✿ ♥ ✿ ♥ ✿ ♥ ✿", bg=ROSA_FONDO, fg=ROSA_FUERTE, font=(LETRA, 14))
        self.decoracion.pack(pady=(10, 0))

        tk.Label(ventana, text="♡ LOGIN ♡", bg=ROSA_FONDO, fg=ROSA_FUERTE, font=(LETRA, 20, "bold")).pack()

        self.mensaje = tk.Label(ventana, text="", bg=ROSA_FONDO, fg="#d6336c", font=(LETRA, 10))
        self.mensaje.pack(pady=5)

        tk.Label(ventana, text="Usuario ★", bg=ROSA_FONDO, fg=ROSA_FUERTE, font=(LETRA, 11)).pack()
        self.caja_usuario = tk.Entry(ventana, font=(LETRA, 11), justify="center", relief="flat",
                                     highlightthickness=2, highlightbackground=ROSA_BOTON, highlightcolor=ROSA_FUERTE)
        self.caja_usuario.pack(pady=3)

        tk.Label(ventana, text="Contraseña ★", bg=ROSA_FONDO, fg=ROSA_FUERTE, font=(LETRA, 11)).pack()
        self.caja_clave = tk.Entry(ventana, show="♥", font=(LETRA, 11), justify="center", relief="flat",
                                   highlightthickness=2, highlightbackground=ROSA_BOTON, highlightcolor=ROSA_FUERTE)
        self.caja_clave.pack(pady=3)

        self.recordar = tk.IntVar()
        tk.Checkbutton(ventana, text="Recordarme", variable=self.recordar, bg=ROSA_FONDO, fg=ROSA_FUERTE,
                       selectcolor="white", activebackground=ROSA_FONDO, activeforeground=ROSA_FUERTE,
                       font=(LETRA, 10)).pack(pady=3)

        self.contenedor = tk.Frame(ventana, width=340, height=160, bg=ROSA_FONDO)
        self.contenedor.pack()

        self.boton_entrar = tk.Button(self.contenedor, text="Entrar ♥", width=14, command=self.entrar,
                                      bg=ROSA_BOTON, fg="white", activebackground=ROSA_FUERTE,
                                      activeforeground="white", relief="flat", font=(LETRA, 10, "bold"))
        self.boton_entrar.place(relx=0.5, rely=0.5, anchor="center")
        self.boton_entrar.bind("<Enter>", self.escapar)
        self.contenedor.bind("<Motion>", self.mouse_cerca)

        self.caja_usuario.bind("<KeyRelease>", self.revisar_cajas)
        self.caja_clave.bind("<KeyRelease>", self.revisar_cajas)

        self.crear_boton_bailarin("¿Olvidaste tu contraseña? ✿", self.olvide_clave)
        self.crear_boton_bailarin("¿No tienes cuenta? Regístrate ♡", self.registrar)

        self.decoracion_abajo = tk.Label(ventana, text="♥ ✿ ★ ✿ ♥", bg=ROSA_FONDO, fg=ROSA_FUERTE, font=(LETRA, 14))
        self.decoracion_abajo.pack(pady=5)

        self.cargar_recordado()
        self.parpadear()

    # Crea un botón que tiembla cuando el mouse pasa por encima
    def crear_boton_bailarin(self, texto, comando):
        marco = tk.Frame(self.ventana, width=320, height=40, bg=ROSA_FONDO)
        marco.pack()
        boton = tk.Button(marco, text=texto, command=comando, bg="white", fg=ROSA_FUERTE,
                          activebackground=ROSA_BOTON, activeforeground="white", relief="flat", font=(LETRA, 10))
        boton.place(relx=0.5, rely=0.5, anchor="center")
        boton.bind("<Enter>", lambda evento: self.temblar(boton, 0))

    # Mueve el botón de lado a lado varias veces, como si temblara
    def temblar(self, boton, paso):
        movimientos = [-8, 8, -6, 6, -4, 4, -2, 2, 0]
        if paso < len(movimientos):
            boton.place(relx=0.5, rely=0.5, x=movimientos[paso], anchor="center")
            self.ventana.after(40, self.temblar, boton, paso + 1)

    # Cambia el color de las flores cada medio segundo
    def parpadear(self):
        self.color_actual = (self.color_actual + 1) % len(self.colores)
        self.decoracion.config(fg=self.colores[self.color_actual])
        self.decoracion_abajo.config(fg=self.colores[self.color_actual])
        self.ventana.after(500, self.parpadear)

    # Si el mouse se acerca al botón "Entrar", el botón huye antes de que lo toques
    def mouse_cerca(self, evento):
        centro_x = self.boton_entrar.winfo_x() + self.boton_entrar.winfo_width() / 2
        centro_y = self.boton_entrar.winfo_y() + self.boton_entrar.winfo_height() / 2
        if abs(evento.x - centro_x) < self.boton_entrar.winfo_width() / 2 + 30:
            if abs(evento.y - centro_y) < self.boton_entrar.winfo_height() / 2 + 30:
                self.escapar(evento)

    # El botón "Entrar" huye a un lugar al azar, lejos del mouse
    def escapar(self, evento):
        if self.descansando:
            return
        vacias = self.caja_usuario.get().strip() == "" or self.caja_clave.get() == ""
        if vacias or self.burlas < self.max_burlas:
            if vacias:
                self.mensaje.config(text="Llena los campos antes de continuar ♡")
            else:
                self.burlas = self.burlas + 1
                self.mensaje.config(text="Jeje, todavía no ✿ (" + str(self.burlas) + "/" + str(self.max_burlas) + ")")

            ancho = self.boton_entrar.winfo_width()
            alto = self.boton_entrar.winfo_height()
            mouse_x = self.contenedor.winfo_pointerx() - self.contenedor.winfo_rootx()
            mouse_y = self.contenedor.winfo_pointery() - self.contenedor.winfo_rooty()

            for intento in range(30):
                x = random.randint(ancho // 2, 340 - ancho // 2)
                y = random.randint(alto // 2, 160 - alto // 2)
                if abs(x - mouse_x) > ancho or abs(y - mouse_y) > alto + 30:
                    break

            self.boton_entrar.place(relx=0, rely=0, x=x, y=y, anchor="center")
            self.boton_entrar.config(text=random.choice(self.frases))
            self.descansando = True
            self.ventana.after(200, self.terminar_descanso)
        else:
            self.boton_entrar.config(text="Ya, entra ♥")
            self.mensaje.config(text="")

    # Deja que el botón vuelva a escaparse después de un momento
    def terminar_descanso(self):
        self.descansando = False

    # Si se borra alguna caja, el botón vuelve a burlarse desde cero
    def revisar_cajas(self, evento):
        if self.caja_usuario.get().strip() == "" or self.caja_clave.get() == "":
            self.burlas = 0

    # Si alguien marcó "Recordarme", escribe su usuario en la caja
    def cargar_recordado(self):
        if os.path.exists("recordado.txt"):
            archivo = open("recordado.txt", "r")
            usuario = archivo.read().strip()
            archivo.close()
            if usuario != "":
                self.caja_usuario.insert(0, usuario)
                self.recordar.set(1)

    # Guarda el usuario si marcó "Recordarme", o lo borra si no
    def guardar_recordado(self, usuario):
        archivo = open("recordado.txt", "w")
        if self.recordar.get() == 1:
            archivo.write(usuario)
        archivo.close()

    # Revisa los datos cuando se oprime "Entrar"
    def entrar(self):
        usuario = self.caja_usuario.get().strip()
        clave = self.caja_clave.get()

        if usuario == "" or clave == "":
            self.mensaje.config(text="Llena todos los campos")
            return

        if self.usuarios.revisar(usuario, clave):
            self.intentos = 0
            self.burlas = 0
            self.boton_entrar.config(text="Entrar ♥")
            self.mensaje.config(text="")
            self.guardar_recordado(usuario)
            messagebox.showinfo("Bienvenido", "Hola " + usuario + ", entraste")
        else:
            self.intentos = self.intentos + 1
            if self.intentos >= 3:
                self.boton_entrar.config(state="disabled")
                self.mensaje.config(text="3 intentos fallidos. Espera 30 segundos")
                self.ventana.after(30000, self.desbloquear)
            else:
                self.mensaje.config(text="Usuario o contraseña incorrectos")

    # Vuelve a activar el botón después de esperar
    def desbloquear(self):
        self.intentos = 0
        self.boton_entrar.config(state="normal", text="Entrar ♥")
        self.mensaje.config(text="")

    # Crea una cuenta nueva con lo que está escrito en las cajas
    def registrar(self):
        usuario = self.caja_usuario.get().strip()
        clave = self.caja_clave.get()

        if usuario == "" or clave == "":
            self.mensaje.config(text="Llena todos los campos para registrarte")
        elif " " in usuario or "," in usuario or "," in clave:
            self.mensaje.config(text="No uses espacios ni comas")
        elif len(usuario) < 4:
            self.mensaje.config(text="El usuario debe tener mínimo 4 letras")
        elif len(clave) < 6:
            self.mensaje.config(text="La contraseña debe tener mínimo 6 caracteres")
        elif clave.isdigit() or clave.isalpha():
            self.mensaje.config(text="La contraseña debe tener letras y números")
        elif usuario in self.usuarios.lista:
            self.mensaje.config(text="Ese usuario ya existe")
        else:
            palabra = simpledialog.askstring("Registro", "Escribe una palabra secreta por si olvidas la contraseña:")
            if palabra == None or palabra.strip() == "" or "," in palabra:
                self.mensaje.config(text="Escribe una palabra secreta válida (sin comas)")
            else:
                self.usuarios.guardar(usuario, clave, palabra.strip())
                self.mensaje.config(text="")
                messagebox.showinfo("Registro", "Usuario creado, ya puedes entrar")

    # Muestra la contraseña si la palabra secreta es correcta
    def olvide_clave(self):
        usuario = simpledialog.askstring("Recuperar", "Escribe tu usuario:")
        if usuario == None:
            return
        palabra = simpledialog.askstring("Recuperar", "Escribe tu palabra secreta:")
        if palabra == None:
            return

        usuario = usuario.strip()
        if usuario in self.usuarios.lista and self.usuarios.lista[usuario][1] == palabra.strip():
            messagebox.showinfo("Recuperar", "Tu contraseña es: " + self.usuarios.lista[usuario][0])
        else:
            messagebox.showerror("Recuperar", "Usuario o palabra secreta incorrectos")

ventana = tk.Tk()
Login(ventana)
ventana.mainloop()