import java.util.ArrayList;
import java.util.Scanner;

public class FloristeriaAvesProgramadoras {

    static Scanner sc = new Scanner(System.in);

    // inventario (flores)
    static ArrayList<String> flores_tipo = new ArrayList<>();
    static ArrayList<Integer> flores_cant = new ArrayList<>();
    static ArrayList<Integer> flores_precios = new ArrayList<>();

    // carrito (compra_user)
    static ArrayList<String> compra_user_tipo = new ArrayList<>();
    static ArrayList<Integer> compra_user_cantidad = new ArrayList<>();
    static ArrayList<Integer> compra_user_precio = new ArrayList<>();

    // variables del pago
    static String pago_opc = "";
    static int dinero_user = 0;
    static int cuotas = 0;

    public static String capitalizar(String texto) {
        return texto.substring(0, 1).toUpperCase() + texto.substring(1);
    }

    public static void mostrar_menu() {
        System.out.println("\nQué desea hacer?");
        System.out.println("1. Comprar flores");
        System.out.println("2. Pagar");
        System.out.println("3. Generar factura");
        System.out.println("4. Salir");
    }

    public static void agregar_al_carrito() {
        if (flores_tipo.size() == 0) {
            System.out.println("Ya no queda stock, puede pagar lo que tiene en el carrito");
        } else {
            // aqui se muestra el stock
            System.out.println("Flores disponibles:");
            for (int i = 0; i < flores_tipo.size(); i++) {
                System.out.println("- " + capitalizar(flores_tipo.get(i)) + ": " + flores_cant.get(i)
                        + " disponibles a $" + flores_precios.get(i) + " c/u");
            }

            System.out.print("Seleccione la flor que desea comprar: ");
            String flor_opc = sc.nextLine().toLowerCase().trim();
            int idx = flores_tipo.indexOf(flor_opc);

            if (idx != -1) {
                System.out.print("Ingrese la cantidad que desea comprar: ");
                if (sc.hasNextInt()) {
                    int flor_cant = sc.nextInt();
                    sc.nextLine();

                    if (flor_cant > 0) {
                        if (flor_cant <= flores_cant.get(idx)) {
                            // Si la flor ya está en el carrito, solo sumamos la cantidad
                            int pos = compra_user_tipo.indexOf(flor_opc);
                            if (pos != -1) {
                                compra_user_cantidad.set(pos, compra_user_cantidad.get(pos) + flor_cant);
                            } else {
                                compra_user_tipo.add(flor_opc);
                                compra_user_cantidad.add(flor_cant);
                                compra_user_precio.add(flores_precios.get(idx));
                            }

                            // Restamos del inventario
                            flores_cant.set(idx, flores_cant.get(idx) - flor_cant);
                            System.out.println("¡Se han añadido " + flor_cant + " " + flor_opc + "(s) al carrito!");

                            // Removemos del inventario las flores si llegan a 0
                            if (flores_cant.get(idx) == 0) {
                                flores_tipo.remove(idx);
                                flores_cant.remove(idx);
                                flores_precios.remove(idx);
                            }
                        } else {
                            System.out.println("Cantidad no disponible");
                        }
                    } else {
                        System.out.println("Cantidad inválida");
                    }
                } else {
                    sc.nextLine();
                    System.out.println("Cantidad inválida");
                }
            } else {
                System.out.println("La flor " + flor_opc + " no está disponible");
            }
        }
    }

    public static void quitar_del_carrito() {
        if (compra_user_tipo.size() == 0) {
            System.out.println("El carrito de compras está vacío. ");
        } else {
            System.out.println("En su carrito tiene:");
            for (int i = 0; i < compra_user_tipo.size(); i++) {
                System.out.println("- " + capitalizar(compra_user_tipo.get(i)) + ": " + compra_user_cantidad.get(i));
            }

            System.out.print("Seleccione la flor que desea quitar: ");
            String flor_quitar = sc.nextLine().toLowerCase().trim();
            int j = compra_user_tipo.indexOf(flor_quitar);

            if (j != -1) {
                System.out.print("Ingrese la cantidad que desea quitar: ");
                if (sc.hasNextInt()) {
                    int cant_quitar = sc.nextInt();
                    sc.nextLine();

                    if (cant_quitar > 0 && cant_quitar <= compra_user_cantidad.get(j)) {
                        // Devolvemos al inventario
                        int pos = flores_tipo.indexOf(flor_quitar);
                        if (pos != -1) {
                            flores_cant.set(pos, flores_cant.get(pos) + cant_quitar);
                        } else {
                            flores_tipo.add(flor_quitar);
                            flores_cant.add(cant_quitar);
                            flores_precios.add(compra_user_precio.get(j));
                        }

                        compra_user_cantidad.set(j, compra_user_cantidad.get(j) - cant_quitar);
                        if (compra_user_cantidad.get(j) == 0) {
                            compra_user_tipo.remove(j);
                            compra_user_cantidad.remove(j);
                            compra_user_precio.remove(j);
                        }
                        System.out.println("Se han quitado " + cant_quitar + " " + flor_quitar + "(s) del carrito");
                    } else {
                        System.out.println("Cantidad inválida");
                    }
                } else {
                    sc.nextLine();
                    System.out.println("Cantidad inválida");
                }
            } else {
                System.out.println("La flor " + flor_quitar + " no esta en su carrito");
            }
        }
    }

    public static void pagar() {
        if (compra_user_tipo.size() > 0) {
            // se calcula el total de lo comprado
            int total_pagar = 0;
            for (int i = 0; i < compra_user_tipo.size(); i++) {
                int subtotal = compra_user_cantidad.get(i) * compra_user_precio.get(i);
                total_pagar += subtotal;
            }

            // tabla de compras
            System.out.println("\nUsted compro:");
            System.out.printf("%-15s | %-10s | %-15s | %-15s%n", "Flores", "Cantidad", "Precio Unitario", "Subtotal");
            System.out.println("-----------------------------------------------------------------");
            for (int i = 0; i < compra_user_tipo.size(); i++) {
                int subtotal = compra_user_cantidad.get(i) * compra_user_precio.get(i);
                System.out.printf("%-15s | %-10d | $%-14d | $%-14d%n", capitalizar(compra_user_tipo.get(i)),
                        compra_user_cantidad.get(i), compra_user_precio.get(i), subtotal);
            }
            System.out.println("\nTotal a pagar: $" + total_pagar + "\n");

            System.out.println("Seleccione el medio de pago: \n");
            System.out.println("1. Efectivo");
            System.out.println("2. Tarjeta de crédito");
            System.out.println("3. Tarjeta de débito");
            System.out.println("4. Otro medio de pago");
            System.out.println("Seleccione su opción: ");
            pago_opc = sc.nextLine().trim();

            // verificacion del dinero (efectivo o débito)
            if (pago_opc.equals("1") || pago_opc.equals("3")) {
                System.out.println("\nUsted ha seleccionado la opción de pago " + pago_opc + ".");
                System.out.print("¿Cuánto dinero tiene disponible? $");
                if (sc.hasNextInt()) {
                    dinero_user = sc.nextInt();
                    sc.nextLine();
                    if (dinero_user >= total_pagar) {
                        System.out.println("Pago exitoso. Le sobran: $" + (dinero_user - total_pagar));
                    } else {
                        System.out.println("Dinero insuficiente, le faltan: $" + (total_pagar - dinero_user));
                        pago_opc = "";
                    }
                } else {
                    sc.nextLine();
                    System.out.println("Valor inválido");
                    pago_opc = "";
                }
            }
            // verificacion a crédito
            else if (pago_opc.equals("2")) {
                System.out.println("\nUsted ha seleccionado la opción de pago " + pago_opc + ".");
                System.out.print("¿En cuántas cuotas desea pagar?: ");
                if (sc.hasNextInt()) {
                    cuotas = sc.nextInt();
                    sc.nextLine();
                    if (cuotas > 0) {
                        System.out.println("Pagará " + cuotas + " cuota(s) de $" + Math.round((double) total_pagar / cuotas));
                    } else {
                        System.out.println("Número de cuotas inválido");
                        pago_opc = "";
                    }
                } else {
                    sc.nextLine();
                    System.out.println("Número de cuotas inválido");
                    pago_opc = "";
                }
            } else if (pago_opc.equals("4")) {
                System.out.println("\nUsted ha seleccionado la opción de pago " + pago_opc + ".");
                System.out.println("El total a pagar por su compra es: $" + total_pagar);
            } else {
                System.out.println("Opción de pago inválida");
                pago_opc = "";
            }
        } else {
            System.out.println("El carrito de compras está vacío. ");
        }
    }

    public static void generar_factura() {
        System.out.println("\n=================================================================");
        System.out.println("                  AVES PROGRAMADORAS");
        System.out.println("                    NIT: 9018086767");
        System.out.println("             ¡Somos evasores de impuestos!");
        System.out.println("=================================================================");
        System.out.println("                         FACTURA DE VENTA");
        System.out.println("=================================================================");

        // encabezado
        System.out.printf("%-15s | %-10s | %-15s | %-15s%n", "Flores", "Cantidad", "Precio Unitario", "Subtotal");
        System.out.println("-----------------------------------------------------------------");

        // productos comprados
        int total_pagar = 0;
        for (int i = 0; i < compra_user_tipo.size(); i++) {
            int subtotal = compra_user_cantidad.get(i) * compra_user_precio.get(i);
            total_pagar += subtotal;
            System.out.printf("%-15s | %-10d | $%-14d | $%-14d%n", capitalizar(compra_user_tipo.get(i)),
                    compra_user_cantidad.get(i), compra_user_precio.get(i), subtotal);
        }

        System.out.println("-----------------------------------------------------------------");
        System.out.printf("%-44s $%d%n", "TOTAL A PAGAR:", total_pagar);
        System.out.println("=================================================================");

        // info pago
        if (compra_user_tipo.size() > 0) {
            System.out.println("                    INFORMACIÓN DEL PAGO");
            System.out.println("-----------------------------------------------------------------");

            if (pago_opc.equals("1")) {
                System.out.println("Medio de pago: Efectivo");
                System.out.println("Total recibido: $" + dinero_user);
                System.out.println("Cambio: $" + (dinero_user - total_pagar));
            } else if (pago_opc.equals("2")) {
                System.out.println("Medio de pago: Tarjeta de crédito");
                System.out.println("Cuotas: " + cuotas);
                System.out.println("Valor aproximado por cuota: $" + Math.round((double) total_pagar / cuotas));
                System.out.println("Total recibido: Pago con tarjeta de crédito");
                System.out.println("Cambio: $0");
            } else if (pago_opc.equals("3")) {
                System.out.println("Medio de pago: Tarjeta de débito");
                System.out.println("Total recibido: Pago con tarjeta de débito");
                System.out.println("Cambio: $0");
            } else if (pago_opc.equals("4")) {
                System.out.println("Medio de pago: Otro medio de pago");
                System.out.println("Total recibido: Pago por otro medio");
                System.out.println("Cambio: $0");
            } else {
                System.out.println("El pago aún no se ha realizado");
            }

            System.out.println("-----------------------------------------------------------------");
            System.out.println("        ¡GRACIAS POR COMPRAR EN AVES PROGRAMADORAS!");
            System.out.println("=================================================================");
        } else {
            System.out.println("No hay productos en la factura.");
        }
    }

    public static void main(String[] args) {
        // llenamos el inventario
        flores_tipo.add("margarita");
        flores_tipo.add("azucena");
        flores_tipo.add("rosa");

        flores_cant.add(10);
        flores_cant.add(20);
        flores_cant.add(30);

        flores_precios.add(1200);
        flores_precios.add(3000);
        flores_precios.add(1200);

        boolean salir = false;

        while (!salir) {
            mostrar_menu();

            System.out.println("Seleccione su opción: ");
            String opc = sc.nextLine().trim();

            switch (opc) {
                case "1":
                    System.out.println("Ha seleccionado: Comprar flores\n");

                    // si no hay flores ni nada en el carrito, se sale del programa
                    if (flores_tipo.size() == 0 && compra_user_tipo.size() == 0) {
                        System.out.println("Lo siento, estamos sin stock");
                        salir = true;
                        break;
                    }

                    System.out.println("1. Agregar al carrito");
                    System.out.println("2. Quitar del carrito (me arrepentí)");
                    System.out.println("Seleccione su opción: ");
                    String sub_opc = sc.nextLine().trim();

                    if (sub_opc.equals("1")) {
                        agregar_al_carrito();
                    } else if (sub_opc.equals("2")) {
                        quitar_del_carrito();
                    } else {
                        System.out.println("Numero incorrecto");
                    }
                    break;

                case "2":
                    pagar();
                    break;

                case "3":
                    generar_factura();
                    if (compra_user_tipo.size() == 0) {
                        salir = true;
                    }
                    break;

                case "4":
                    System.out.println("Ha seleccionado: Salir\n");
                    salir = true;
                    break;

                default:
                    System.out.println("Número incorrecto, por favor seleccione uno correcto");
            }
        }

        System.out.println("Gracias por emplear nuestros servicios. Feliz día");
    }
}