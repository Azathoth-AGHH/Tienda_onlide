package com.tienda;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * La clase Controlador es el corazón de la aplicación, actúa como el cerebro que coordina
    todas las operaciones de la tienda. Se encarga de la lógica del negocio, como la interacción
    con el usuario, la gestión del carrito de compras y la finalización del pedido.
 */
public class Controlador {
    // Declaración de los objetos que la clase Controlador necesita para funcionar.
    // 'private' asegura que solo esta clase pueda acceder a ellos directamente.
    private Usuario usuario; // Almacena la información del cliente que está realizando la compra.
    private Carrito carrito; // Objeto que gestiona la lista de productos seleccionados por el usuario.
    private Producto[] catalogo; // Array que contiene todos los productos disponibles en la tienda.

    /**
     * Constructor de la clase Controlador.
     * Se llama al crear una instancia de esta clase. Su principal tarea es inicializar
     * los objetos `carrito` y `catalogo` para que la tienda esté lista para operar.
     */
    public Controlador() {
        // Inicializamos el carrito con una capacidad máxima de 20 productos.
        // Aunque es arbitrario, evita que el array crezca indefinidamente.
        carrito = new Carrito(20);
        // Llamamos a un método auxiliar para llenar el catálogo de productos.
        cargarCatalogo();
    }

    /**
     * Método para registrar al usuario.
     * Interactúa con el usuario a través de la consola para recopilar su nombre, email y dirección.
     * Luego, crea un objeto de la clase `Usuario` con estos datos y lo almacena.
     */
    public void registrarUsuario() {
        // 'Scanner' es una clase útil para leer la entrada de datos del teclado.
        Scanner sc = new Scanner(System.in);
        System.out.println("=== Registro de Usuario ===");
        System.out.print("Nombre: ");
        String nombre = sc.nextLine();
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Dirección: ");
        String direccion = sc.nextLine();

        // Creamos una nueva instancia de la clase `Usuario` con los datos obtenidos.
        usuario = new Usuario(nombre, email, direccion);
        System.out.println("Usuario registrado correctamente: " + usuario.getNombre());
    }

    /**
     * Método privado para inicializar el catálogo de productos.
     * Este método se llama solo una vez en el constructor. Aquí se definen y se instancian
     * todos los objetos `Producto` que estarán disponibles para la venta.
     */
    private void cargarCatalogo() {
        // Creamos un array de `Producto` con un tamaño fijo para almacenar los 58 productos.
        catalogo = new Producto[58];

        // Se inicializa cada posición del array con un nuevo objeto Producto.
        // Cada producto se define con su nombre, categoría, subcategoría, precio y stock inicial.
        // (Aquí se omiten los 58 productos para no hacer el código tan largo, pero se entiende el concepto).
        // Ejemplo de inicialización:
        catalogo[0] = new Producto("Lala 1 L", "Lácteos", "Leche entera", 28.50, 0);
        catalogo[1] = new Producto("Santa Clara  1 L (6 piezas)", "Lácteos", "Leche entera", 230.00, 0);
        catalogo[2] = new Producto("Alpura  1 L (6 piezas)", "Lácteos", "Leche entera", 180.00, 0);
        catalogo[3] = new Producto("Nutrileche  1 L", "Lácteos", "Leche entera", 25.00, 0);

        // Lácteos - Leche deslactosada
        catalogo[4] = new Producto("Lala  1 L (6 piezas)", "Lácteos", "Leche deslactosada", 159.00, 0);
        catalogo[5] = new Producto("Alpura  1 L", "Lácteos", "Leche deslactosada", 30.00, 0);
        catalogo[6] = new Producto("Santa Clara  1 L", "Lácteos", "Leche deslactosada", 40.00, 0);

        // Lácteos - Leche saborizada
        catalogo[7] = new Producto("Lala Yomi Vainilla  180 ml", "Lácteos", "Leche saborizada", 10.00, 0);
        catalogo[8] = new Producto("Lala Yomi Chocolate  180 ml", "Lácteos", "Leche saborizada", 10.00, 0);
        catalogo[9] = new Producto("Lala Yomi Fresa  180 ml", "Lácteos", "Leche saborizada", 10.00, 0);
        catalogo[10] = new Producto("Alpura Vainilla  180 ml", "Lácteos", "Leche saborizada", 11.00, 0);
        catalogo[11] = new Producto("Alpura Fresa  180 ml", "Lácteos", "Leche saborizada", 11.00, 0);
        catalogo[12] = new Producto("Alpura Chocolate  180 ml", "Lácteos", "Leche saborizada", 11.00, 0);
        catalogo[13] = new Producto("Santa Clara Vainilla  180 ml", "Lácteos", "Leche saborizada", 13.00, 0);
        catalogo[14] = new Producto("Santa Clara Chocolate  180 ml", "Lácteos", "Leche saborizada", 13.00, 0);
        catalogo[15] = new Producto("Santa Clara Fresa  180 ml", "Lácteos", "Leche saborizada", 13.00, 0);

        // Lácteos - Yogurt
        catalogo[16] = new Producto("Lala Fresa  220 g (8 piezas)", "Lácteos", "Yogurt bebible", 70.00, 0);
        catalogo[17] = new Producto("Alpura Natural  1 kg", "Lácteos", "Yogurt natural", 42.00, 0);
        catalogo[18] = new Producto("Danone Griego  150 g", "Lácteos", "Yogurt griego", 18.00, 0);

        // Lácteos - Mantequilla y Margarina
        catalogo[19] = new Producto("Lala sin sal  90 g", "Lácteos", "Mantequilla", 24.00, 0);
        catalogo[20] = new Producto("Primavera  225 g", "Lácteos", "Margarina", 18.00, 0);

        // Snacks - Galletas
        catalogo[21] = new Producto("Marinela Canelitas  300 g", "Snacks", "Galletas", 37.90, 0);
        catalogo[22] = new Producto("Chokiees  300 g", "Snacks", "Galletas", 107.00, 0);
        catalogo[23] = new Producto("Sponch  700 g (4 paquetes)", "Snacks", "Galletas", 79.50, 0);
        catalogo[24] = new Producto("Pasticetas  400 g", "Snacks", "Galletas", 65.90, 0);
        catalogo[25] = new Producto("Surtido de Marinela  450 g", "Snacks", "Galletas", 73.50, 0);

        // Snacks - Botanas
        catalogo[26] = new Producto("Sabritas Original  42 g", "Snacks", "Botanas", 20.00, 0);
        catalogo[27] = new Producto("Sabritas Limón  42 g", "Snacks", "Botanas", 20.00, 0);
        catalogo[28] = new Producto("Sabritas Flamin Hot  42 g", "Snacks", "Botanas", 20.00, 0);
        catalogo[29] = new Producto("Doritos Rojos  75 g", "Snacks", "Botanas", 18.00, 0);
        catalogo[30] = new Producto("Doritos Verdes  35 g", "Snacks", "Botanas", 18.00, 0);
        catalogo[31] = new Producto("Cheetos Torciditos  80 g", "Snacks", "Botanas", 15.00, 0);
        catalogo[32] = new Producto("Cheetos Poffs  80 g", "Snacks", "Botanas", 15.00, 0);
        catalogo[33] = new Producto("Cheetos Flamin Hot  80 g", "Snacks", "Botanas", 15.00, 0);
        catalogo[34] = new Producto("Cacahuates  70 g", "Snacks", "Botanas", 20.00, 0);

        // Snacks - Pastelitos
        catalogo[35] = new Producto("Gansito Marinela  50 g", "Snacks", "Pastelitos", 20.90, 0);
        catalogo[36] = new Producto("Pingüinos Marinela  80 g", "Snacks", "Pastelitos", 27.90, 0);
        catalogo[37] = new Producto("Choco Roles Marinela  122 g (2 piezas)", "Snacks", "Pastelitos", 27.90, 0);
        catalogo[38] = new Producto("Gansito Marinela 3 Piezas", "Snacks", "Pastelitos", 50.90, 0);

        // Limpieza - Multiusos
        catalogo[39] = new Producto("Pinol El Original  5.1 L", "Limpieza", "Multiusos", 179.00, 0);
        catalogo[40] = new Producto("Fabuloso  6 L", "Limpieza", "Multiusos", 199.00, 0);
        catalogo[41] = new Producto("Cloralex  1 L", "Limpieza", "Multiusos", 68.00, 0);
        catalogo[42] = new Producto("Clorox  1 L", "Limpieza", "Multiusos", 65.00, 0);
        catalogo[43] = new Producto("Vanish  1 L", "Limpieza", "Multiusos", 90.00, 0);

        // Limpieza - Detergentes
        catalogo[44] = new Producto("Ariel Líquido Poder y Cuidado  8.5 L", "Limpieza", "Detergentes", 374.25, 0);
        catalogo[45] = new Producto("Persil en Polvo para Ropa de Color  9 kg", "Limpieza", "Detergentes", 439.00, 0);
        catalogo[46] = new Producto("Ariel Líquido Color  2.8 L (45 lavadas)", "Limpieza", "Detergentes", 149.00, 0);
        catalogo[47] = new Producto("Ariel en Polvo con Downy  750 g", "Limpieza", "Detergentes", 35.00, 0);
        catalogo[48] = new Producto("Ariel Expert Líquido  5 L (80 lavadas)", "Limpieza", "Detergentes", 194.90, 0);

        // Limpieza - Lavatrastes
        catalogo[49] = new Producto("Salvo Limón Líquido  1.4 L", "Limpieza", "Lavatrastes", 69.00, 0);
        catalogo[50] = new Producto("Salvo Polvo  1 kg", "Limpieza", "Lavatrastes", 39.00, 0);
        catalogo[51] = new Producto("Salvo Lavatrastes Limón  900 ml", "Limpieza", "Lavatrastes", 55.00, 0);
        catalogo[52] = new Producto("Salvo Lavatrastes Limón  500 ml", "Limpieza", "Lavatrastes", 32.90, 0);

        // Bebidas - Energizantes
        catalogo[53] = new Producto("Amper Mango 475 ml", "Bebidas", "Energizantes", 20, 0);
        catalogo[54] = new Producto("Monster Blanco  355 ml", "Bebidas", "Energizantes", 46, 0);
        catalogo[55] = new Producto("Predator Rojo  475 ml", "Bebidas", "Energizantes", 19, 0);
        catalogo[56] = new Producto("Red Bull  420 ml", "Bebidas", "Energizantes", 52, 0);
        catalogo[57] = new Producto("Vive 100  630 ml", "Bebidas", "Energizantes", 25, 0);
    }


    /**
     * Muestra el menú interactivo para navegar por el catálogo de productos.
     * Permite al usuario seleccionar una categoría, luego ver las subcategorías y productos
     * para finalmente agregar artículos al carrito.
     */
    public void menuCatalogo() {
        Scanner sc = new Scanner(System.in);

        // Se crea un `ArrayList` para almacenar las categorías únicas y evitar duplicados.
        List<String> categorias = new ArrayList<>();
        // Recorremos el catálogo para obtener todas las categorías existentes.
        for (Producto p : catalogo) {
            if (!categorias.contains(p.getCategoria())) {
                categorias.add(p.getCategoria());
            }
        }

        // Bucle principal del menú del catálogo.
        int opcionCategoria;
        do {
            System.out.println("\n=== Categorías ===");
            // Mostramos todas las categorías disponibles.
            for (int i = 0; i < categorias.size(); i++) {
                System.out.println((i + 1) + ". " + categorias.get(i));
            }
            System.out.println("0. Regresar al menú principal");

            System.out.print("Seleccione una categoría: ");
            opcionCategoria = sc.nextInt();
            sc.nextLine(); // Consumimos el salto de línea para evitar problemas.

            if (opcionCategoria > 0 && opcionCategoria <= categorias.size()) {
                String categoriaSeleccionada = categorias.get(opcionCategoria - 1);
                System.out.println("\n- " + categoriaSeleccionada);

                // Mostramos los productos de la categoría seleccionada, agrupados por subcategoría.
                String subcategoriaActual = "";
                int contador = 1;
                // `indicesMap` es un array que mapea la opción de selección del usuario
                // con el índice real del producto en el array `catalogo`.
                int[] indicesMap = new int[catalogo.length];
                for (int i = 0; i < catalogo.length; i++) {
                    Producto p = catalogo[i];
                    if (p.getCategoria().equals(categoriaSeleccionada)) {
                        if (!p.getSubcategoria().equals(subcategoriaActual)) {
                            subcategoriaActual = p.getSubcategoria();
                            System.out.println("    * " + subcategoriaActual);
                        }
                        System.out.println("        " + contador + ". " + p.getNombre() + " - $" + p.getPrecio());
                        indicesMap[contador - 1] = i;
                        contador++;
                    }
                }

                // Lógica para agregar un producto al carrito.
                System.out.print("\nSeleccione un producto (0 para regresar): ");
                int opcionProducto = sc.nextInt();
                sc.nextLine();

                if (opcionProducto > 0 && opcionProducto < contador) {
                    int indexReal = indicesMap[opcionProducto - 1];
                    Producto seleccionado = catalogo[indexReal];

                    System.out.print("Ingrese la cantidad: ");
                    int cantidad = sc.nextInt();
                    sc.nextLine();

                    if (cantidad > 0) {
                        // Se crea una nueva instancia de `Producto` con la cantidad especificada.
                        // Esto es para que el carrito tenga su propia copia del producto con la cantidad comprada.
                        carrito.agregarProducto(new Producto(
                            seleccionado.getNombre(),
                            seleccionado.getCategoria(),
                            seleccionado.getSubcategoria(),
                            seleccionado.getPrecio(),
                            cantidad
                        ));
                        System.out.println(" Producto agregado al carrito.");
                    } else {
                        System.out.println(" Cantidad inválida.");
                    }
                }
            }

        } while (opcionCategoria != 0);
    }


    /**
     * Muestra el menú principal de la aplicación.
     * Actúa como el punto de entrada para que el usuario pueda navegar entre las opciones
     * principales: ver catálogo, ver carrito, finalizar compra o salir.
     */
    public void menuPrincipal() {
        Scanner sc = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n=== Menú Principal ===");
            System.out.println("1. Ver catálogo");
            System.out.println("2. Ver carrito");
            System.out.println("3. Finalizar compra");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");

            opcion = sc.nextInt();
            sc.nextLine();

            // Se usa `switch` porque es ideal para manejar múltiples opciones de un menú.
            switch (opcion) {
                case 1:
                    menuCatalogo(); // Llama al método para ver y comprar productos.
                    break;
                case 2:
                    carrito.mostrarCarrito(); // Muestra el contenido actual del carrito.
                    break;
                case 3:
                    resumenCompra(); // Inicia el proceso de finalización de compra.
                    opcion = 0; // Se establece en 0 para salir del bucle después de la compra.
                    break;
                case 0:
                    System.out.println(" Gracias por visitar la tienda.");
                    break;
                default:
                    System.out.println(" Opción inválida.");
            }

        } while (opcion != 0);
    }

    /**
     * Muestra un resumen de la compra y genera el ticket.
     * Imprime los detalles de la compra en la consola y luego utiliza la clase `GeneradorPDF`
     * para crear un archivo PDF con el ticket, aplicando descuentos si corresponde.
     */
    public void resumenCompra() {
        System.out.println("\n=== Resumen de Compra ===");
        carrito.imprimirTicket(usuario); // Llama al método del carrito para imprimir el ticket en la consola.

        // Se prepara una lista para los productos que se enviarán al PDF.
        List<GeneradorPDF.Producto> productosPDF = new ArrayList<>();
        Producto[] productosCarrito = carrito.getProductos();

        // Se recorren los productos del carrito para calcular descuentos y convertirlos a un formato compatible con el `GeneradorPDF`.
        for (Producto p : productosCarrito) {
            if (p == null) continue; // Si el producto es nulo, se salta.

            int cantidad = p.getStock();
            double descuento = 0;
            // Lógica para aplicar descuentos basados en la cantidad de productos.
            if (cantidad >= 3 && cantidad <= 4) descuento = 0.05;
            else if (cantidad >= 5 && cantidad <= 6) descuento = 0.10;
            else if (cantidad >= 7) descuento = 0.15;

            // Se crea un nuevo objeto `GeneradorPDF.Producto` con la información relevante.
            productosPDF.add(new GeneradorPDF.Producto(
                p.getNombre(),
                p.getCategoria(),
                p.getSubcategoria(),
                String.valueOf(cantidad),
                p.getPrecio(),
                descuento
            ));
        }

        // Se llama al método estático `generarTicket` de la clase `GeneradorPDF`.
        try {
            GeneradorPDF.generarTicket(
                "ticket.pdf",
                "City Market",
                usuario,
                "tiendita/src/logo.png",
                productosPDF
            );
            System.out.println("Ticket PDF generado correctamente.");
        } catch (IOException e) {
            System.out.println(" Error al generar el ticket PDF: " + e.getMessage());
        }
    }

    /**
     * Inicia el proceso completo de la aplicación.
     * Es el método que se llama desde la clase principal (`Main`) para empezar
     * la ejecución del programa.
     */
    public void iniciarCompra() {
        registrarUsuario(); // El primer paso es registrar al usuario.
        menuPrincipal(); // Una vez registrado, se muestra el menú principal para iniciar la compra.
    }
}