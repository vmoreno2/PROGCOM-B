import java.util.Scanner;

public class Floristeria {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String[] tipos = {"Rosa", "Tulipan", "Girasol", "Amapola", "Lirio"};
        int[] cantidades = {1, 17, 3, 8, 5};
        int[] precios = {4000, 80000, 5000, 15000, 23000};
        int[] carrito = new int[5];

        boolean estado = true;
        boolean pagado = false;
        double subtotal = 0;
        String metodo = "";

        System.out.println("Bienvenido a mi floristeria.");

        while (estado) {
            System.out.println("***MENU***");
            System.out.println("1. Visualizar nuestras flores");
            System.out.println("2. Comprar");
            System.out.println("3. Pagar");
            System.out.println("4. Factura de compra");
            System.out.println("5. Salir");
            System.out.print("Ingrese su opcion: ");
            int opcion = sc.nextInt();

            switch (opcion) {
                case 1:
                    for (int i = 0; i < tipos.length; i++) {
                        System.out.println(tipos[i] + ": " + cantidades[i] + " disponible, valor por unidad: " + precios[i]);
                    }
                    break;

                case 2:
                    System.out.print("Ingrese el nombre de la flor: ");
                    String flor = sc.next();
                    int posicion = -1;
                    for (int i = 0; i < tipos.length; i++) {
                        if (tipos[i].equalsIgnoreCase(flor)) {
                            posicion = i;
                        }
                    }
                    if (posicion == -1) {
                        System.out.println("Esa flor no existe en nuestro catalogo.");
                    } else if (cantidades[posicion] == 0) {
                        System.out.println("No quedan unidades de " + tipos[posicion]);
                    } else {
                        System.out.print("Cuantas unidades desea comprar? (Disponibles: " + cantidades[posicion] + "): ");
                        int cant = sc.nextInt();
                        if (cant <= 0) {
                            System.out.println("La cantidad debe ser mayor a 0.");
                        } else if (cant > cantidades[posicion]) {
                            System.out.println("No hay suficiente stock.");
                        } else {
                            cantidades[posicion] = cantidades[posicion] - cant;
                            carrito[posicion] = carrito[posicion] + cant;
                            subtotal = subtotal + cant * precios[posicion];
                            System.out.println("Se agregaron " + cant + " " + tipos[posicion] + " a tu carrito.");
                        }
                    }
                    break;

                case 3:
                    if (subtotal == 0) {
                        System.out.println("Primero debes comprar algo.");
                    } else {
                        System.out.println("Metodo de pago:");
                        System.out.println("1. Efectivo");
                        System.out.println("2. Tarjeta Debito");
                        System.out.println("3. Tarjeta Credito");
                        System.out.println("4. PSE");
                        int m = sc.nextInt();
                        if (m == 1) {
                            metodo = "Efectivo";
                        } else if (m == 2) {
                            metodo = "Tarjeta Debito";
                        } else if (m == 3) {
                            metodo = "Tarjeta Credito";
                        } else {
                            metodo = "PSE";
                        }
                        System.out.println("Debe pagar " + (subtotal + subtotal * 0.19));
                        System.out.print("Pagar? (si/no): ");
                        String rta = sc.next();
                        if (rta.equalsIgnoreCase("si")) {
                            pagado = true;
                            System.out.println("Gracias por su compra!");
                        } else {
                            System.out.println("PAGA!");
                        }
                    }
                    break;

                case 4:
                    if (pagado) {
                        System.out.println("***FACTURA DE COMPRA***");
                        for (int i = 0; i < tipos.length; i++) {
                            if (carrito[i] > 0) {
                                System.out.println(tipos[i] + " - Cantidad: " + carrito[i] + " - Subtotal: " + carrito[i] * precios[i]);
                            }
                        }
                        System.out.println("Subtotal: " + subtotal);
                        System.out.println("Total pagado (IVA incluido): " + (subtotal + subtotal * 0.19));
                        System.out.println("Metodo de pago: " + metodo);
                    } else {
                        System.out.println("PAGA!");
                    }
                    break;

                case 5:
                    System.out.println("Hasta pronto!");
                    estado = false;
                    break;

                default:
                    System.out.println("Opcion no valida.");
            }
        }
    }
}