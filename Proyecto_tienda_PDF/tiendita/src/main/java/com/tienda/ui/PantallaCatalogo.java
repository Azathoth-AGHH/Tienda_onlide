package com.tienda.ui;

import com.tienda.Usuario;
import com.tienda.Carrito;
import com.tienda.Producto;
import com.tienda.Invalidar_Email;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;

/**
 * PantallaCatalogo muestra el catálogo de productos organizados por categorías.
 */
public class PantallaCatalogo {
    private Stage stage;
    private Usuario usuario;
    private Carrito carrito;
    private Producto[] catalogo;

    public PantallaCatalogo(Usuario usuario, Carrito carrito) {
        this.usuario = usuario;
        this.carrito = carrito;
        this.catalogo = crearCatalogo();
    }

    /**
     * Muestra la pantalla del catálogo.
     */
    public void mostrar(Stage primaryStage) {
        this.stage = primaryStage;

        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #f0f0f0;");

        // Encabezado
        VBox encabezado = crearEncabezado();
        root.setTop(encabezado);

        // Contenido con categorías
        HBox contenido = crearContenidoCatalogo();
        root.setCenter(contenido);

        // Pie de página
        HBox piePagina = crearPiePagina();
        root.setBottom(piePagina);

        Scene scene = new Scene(root, 1000, 700);
        stage.setTitle("City Market - Catalogo");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Crea el encabezado.
     */
    private VBox crearEncabezado() {
        VBox encabezado = new VBox();
        encabezado.setStyle("-fx-background-color: #1f3a4d;");
        encabezado.setPadding(new Insets(15));
        encabezado.setAlignment(Pos.CENTER);

        Label titulo = new Label("CATALOGO DE PRODUCTOS");
        titulo.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        titulo.setStyle("-fx-text-fill: white;");

        encabezado.getChildren().add(titulo);
        return encabezado;
    }

    /**
     * Crea el contenido principal del catálogo con categorías y productos.
     */
    private HBox crearContenidoCatalogo() {
        HBox contenido = new HBox();
        contenido.setPadding(new Insets(20));
        contenido.setSpacing(20);

        // Panel izquierdo - Categorías
        VBox panelCategorias = crearPanelCategorias();
        panelCategorias.setPrefWidth(200);

        // Panel derecho - Productos
        VBox panelProductos = new VBox();
        panelProductos.setPadding(new Insets(10));
        panelProductos.setSpacing(10);
        panelProductos.setStyle("-fx-border-color: #ccc; -fx-border-radius: 5;");

        ScrollPane scrollProductos = new ScrollPane(panelProductos);
        scrollProductos.setFitToWidth(true);

        // Al hacer clic en una categoría, mostrar productos
        List<String> categorias = obtenerCategorias();
        for (String categoria : categorias) {
            Button btnCategoria = new Button(categoria);
            btnCategoria.setPrefWidth(190);
            btnCategoria.setStyle("-fx-font-size: 12; -fx-padding: 10;");
            btnCategoria.setOnAction(e -> mostrarProductosPorCategoria(panelProductos, categoria));

            if (panelCategorias.getChildren().isEmpty()) {
                panelCategorias.getChildren().add(btnCategoria);
                mostrarProductosPorCategoria(panelProductos, categoria);
            } else {
                panelCategorias.getChildren().add(btnCategoria);
            }
        }

        contenido.getChildren().addAll(
                new ScrollPane(panelCategorias),
                scrollProductos
        );

        return contenido;
    }

    /**
     * Crea el panel de categorías.
     */
    private VBox crearPanelCategorias() {
        VBox panel = new VBox();
        panel.setStyle("-fx-border-color: #ccc; -fx-border-radius: 5; -fx-background-color: white;");
        panel.setPadding(new Insets(10));
        panel.setSpacing(10);

        Label labelCategorias = new Label("Categorias");
        labelCategorias.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        panel.getChildren().add(labelCategorias);

        return panel;
    }

    /**
     * Muestra los productos de una categoría en el panel.
     */
    private void mostrarProductosPorCategoria(VBox panelProductos, String categoria) {
        panelProductos.getChildren().clear();

        Label labelCategoria = new Label("Categoria: " + categoria);
        labelCategoria.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        panelProductos.getChildren().add(labelCategoria);

        String subcategoriaActual = "";

        for (int i = 0; i < catalogo.length; i++) {
            Producto p = catalogo[i];
            if (p.getCategoria().equals(categoria)) {
                // Mostrar subcategoría si cambia
                if (!p.getSubcategoria().equals(subcategoriaActual)) {
                    subcategoriaActual = p.getSubcategoria();
                    Label labelSubcategoria = new Label("--- " + subcategoriaActual + " ---");
                    labelSubcategoria.setFont(Font.font("Arial", FontWeight.BOLD, 12));
                    labelSubcategoria.setStyle("-fx-text-fill: #1f3a4d;");
                    panelProductos.getChildren().add(labelSubcategoria);
                }

                // Crear card del producto
                HBox cardProducto = crearCardProducto(p, i);
                panelProductos.getChildren().add(cardProducto);
            }
        }
    }

    /**
     * Crea una card visual para cada producto.
     */
    private HBox crearCardProducto(Producto producto, int indiceProducto) {
        HBox card = new HBox();
        card.setStyle("-fx-border-color: #ddd; -fx-border-radius: 5; -fx-background-color: white;");
        card.setPadding(new Insets(10));
        card.setSpacing(10);

        // Información del producto
        VBox infoProducto = new VBox();
        infoProducto.setSpacing(5);

        Label nombre = new Label(producto.getNombre());
        nombre.setFont(Font.font("Arial", FontWeight.BOLD, 12));

        Label precio = new Label("Precio: $" + String.format("%.2f", producto.getPrecio()));
        precio.setStyle("-fx-text-fill: #d32f2f; -fx-font-weight: bold;");

        infoProducto.getChildren().addAll(nombre, precio);

        // Campo de cantidad
        VBox cantidadBox = new VBox();
        cantidadBox.setSpacing(5);
        Label labelCantidad = new Label("Cantidad:");
        Spinner<Integer> spinnerCantidad = new Spinner<>(1, 100, 1);
        spinnerCantidad.setPrefWidth(70);

        cantidadBox.getChildren().addAll(labelCantidad, spinnerCantidad);

        // Botón agregar
        Button btnAgregar = new Button("Agregar al Carrito");
        btnAgregar.setStyle("-fx-font-size: 11; -fx-padding: 8;");
        btnAgregar.setOnAction(e -> {
            int cantidad = spinnerCantidad.getValue();
            try {
                if (cantidad <= 0) {
                    throw new CantidadInvalidaException("Error: La cantidad debe ser mayor a 0.");
                }
                if (cantidad > 100) {
                    throw new CantidadInvalidaException("Error: La cantidad no puede exceder 100 unidades.");
                }

                Producto nuevoProducto = new Producto(
                        producto.getNombre(),
                        producto.getCategoria(),
                        producto.getSubcategoria(),
                        producto.getPrecio(),
                        cantidad
                );

                carrito.agregarProducto(nuevoProducto);

                Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                alerta.setTitle("Exito");
                alerta.setHeaderText("Producto agregado");
                alerta.setContentText(producto.getNombre() + " ha sido agregado al carrito.");
                alerta.showAndWait();

            } catch (CantidadInvalidaException | Invalidar_Email ex) {
                Alert alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setTitle("Error");
                alerta.setHeaderText("Error al agregar");
                alerta.setContentText(ex.getMessage());
                alerta.showAndWait();
            }
        });

        card.getChildren().addAll(infoProducto, new Separator(), cantidadBox, btnAgregar);
        HBox.setHgrow(infoProducto, javafx.scene.layout.Priority.ALWAYS);

        return card;
    }

    /**
     * Obtiene las categorías únicas del catálogo.
     */
    private List<String> obtenerCategorias() {
        List<String> categorias = new ArrayList<>();
        for (Producto p : catalogo) {
            if (!categorias.contains(p.getCategoria())) {
                categorias.add(p.getCategoria());
            }
        }
        return categorias;
    }

    /**
     * Crea el catálogo de productos.
     */
    private Producto[] crearCatalogo() {
        Producto[] catalogo = new Producto[58];

        catalogo[0] = new Producto("Lala 1 L", "Lacteos", "Leche entera", 28.50, 0);
        catalogo[1] = new Producto("Santa Clara 1 L (6 piezas)", "Lacteos", "Leche entera", 230.00, 0);
        catalogo[2] = new Producto("Alpura 1 L (6 piezas)", "Lacteos", "Leche entera", 180.00, 0);
        catalogo[3] = new Producto("Nutrileche 1 L", "Lacteos", "Leche entera", 25.00, 0);

        catalogo[4] = new Producto("Lala 1 L (6 piezas)", "Lacteos", "Leche deslactosada", 159.00, 0);
        catalogo[5] = new Producto("Alpura 1 L", "Lacteos", "Leche deslactosada", 30.00, 0);
        catalogo[6] = new Producto("Santa Clara 1 L", "Lacteos", "Leche deslactosada", 40.00, 0);

        catalogo[7] = new Producto("Lala Yomi Vainilla 180 ml", "Lacteos", "Leche saborizada", 10.00, 0);
        catalogo[8] = new Producto("Lala Yomi Chocolate 180 ml", "Lacteos", "Leche saborizada", 10.00, 0);
        catalogo[9] = new Producto("Lala Yomi Fresa 180 ml", "Lacteos", "Leche saborizada", 10.00, 0);
        catalogo[10] = new Producto("Alpura Vainilla 180 ml", "Lacteos", "Leche saborizada", 11.00, 0);
        catalogo[11] = new Producto("Alpura Fresa 180 ml", "Lacteos", "Leche saborizada", 11.00, 0);
        catalogo[12] = new Producto("Alpura Chocolate 180 ml", "Lacteos", "Leche saborizada", 11.00, 0);
        catalogo[13] = new Producto("Santa Clara Vainilla 180 ml", "Lacteos", "Leche saborizada", 13.00, 0);
        catalogo[14] = new Producto("Santa Clara Chocolate 180 ml", "Lacteos", "Leche saborizada", 13.00, 0);
        catalogo[15] = new Producto("Santa Clara Fresa 180 ml", "Lacteos", "Leche saborizada", 13.00, 0);

        catalogo[16] = new Producto("Lala Fresa 220 g (8 piezas)", "Lacteos", "Yogurt bebible", 70.00, 0);
        catalogo[17] = new Producto("Alpura Natural 1 kg", "Lacteos", "Yogurt natural", 42.00, 0);
        catalogo[18] = new Producto("Danone Griego 150 g", "Lacteos", "Yogurt griego", 18.00, 0);

        catalogo[19] = new Producto("Lala sin sal 90 g", "Lacteos", "Mantequilla", 24.00, 0);
        catalogo[20] = new Producto("Primavera 225 g", "Lacteos", "Margarina", 18.00, 0);

        catalogo[21] = new Producto("Marinela Canelitas 300 g", "Snacks", "Galletas", 37.90, 0);
        catalogo[22] = new Producto("Chokiees 300 g", "Snacks", "Galletas", 107.00, 0);
        catalogo[23] = new Producto("Sponch 700 g (4 paquetes)", "Snacks", "Galletas", 79.50, 0);
        catalogo[24] = new Producto("Pasticetas 400 g", "Snacks", "Galletas", 65.90, 0);
        catalogo[25] = new Producto("Surtido de Marinela 450 g", "Snacks", "Galletas", 73.50, 0);

        catalogo[26] = new Producto("Sabritas Original 42 g", "Snacks", "Botanas", 20.00, 0);
        catalogo[27] = new Producto("Sabritas Limon 42 g", "Snacks", "Botanas", 20.00, 0);
        catalogo[28] = new Producto("Sabritas Flamin Hot 42 g", "Snacks", "Botanas", 20.00, 0);
        catalogo[29] = new Producto("Doritos Rojos 75 g", "Snacks", "Botanas", 18.00, 0);
        catalogo[30] = new Producto("Doritos Verdes 35 g", "Snacks", "Botanas", 18.00, 0);
        catalogo[31] = new Producto("Cheetos Torciditos 80 g", "Snacks", "Botanas", 15.00, 0);
        catalogo[32] = new Producto("Cheetos Poffs 80 g", "Snacks", "Botanas", 15.00, 0);
        catalogo[33] = new Producto("Cheetos Flamin Hot 80 g", "Snacks", "Botanas", 15.00, 0);
        catalogo[34] = new Producto("Cacahuates 70 g", "Snacks", "Botanas", 20.00, 0);

        catalogo[35] = new Producto("Gansito Marinela 50 g", "Snacks", "Pastelitos", 20.90, 0);
        catalogo[36] = new Producto("Pinguinos Marinela 80 g", "Snacks", "Pastelitos", 27.90, 0);
        catalogo[37] = new Producto("Choco Roles Marinela 122 g (2 piezas)", "Snacks", "Pastelitos", 27.90, 0);
        catalogo[38] = new Producto("Gansito Marinela 3 Piezas", "Snacks", "Pastelitos", 50.90, 0);

        catalogo[39] = new Producto("Pinol El Original 5.1 L", "Limpieza", "Multiusos", 179.00, 0);
        catalogo[40] = new Producto("Fabuloso 6 L", "Limpieza", "Multiusos", 199.00, 0);
        catalogo[41] = new Producto("Cloralex 1 L", "Limpieza", "Multiusos", 68.00, 0);
        catalogo[42] = new Producto("Clorox 1 L", "Limpieza", "Multiusos", 65.00, 0);
        catalogo[43] = new Producto("Vanish 1 L", "Limpieza", "Multiusos", 90.00, 0);

        catalogo[44] = new Producto("Ariel Liquido Poder y Cuidado 8.5 L", "Limpieza", "Detergentes", 374.25, 0);
        catalogo[45] = new Producto("Persil en Polvo para Ropa de Color 9 kg", "Limpieza", "Detergentes", 439.00, 0);
        catalogo[46] = new Producto("Ariel Liquido Color 2.8 L (45 lavadas)", "Limpieza", "Detergentes", 149.00, 0);
        catalogo[47] = new Producto("Ariel en Polvo con Downy 750 g", "Limpieza", "Detergentes", 35.00, 0);
        catalogo[48] = new Producto("Ariel Expert Liquido 5 L (80 lavadas)", "Limpieza", "Detergentes", 194.90, 0);

        catalogo[49] = new Producto("Salvo Limon Liquido 1.4 L", "Limpieza", "Lavatrastes", 69.00, 0);
        catalogo[50] = new Producto("Salvo Polvo 1 kg", "Limpieza", "Lavatrastes", 39.00, 0);
        catalogo[51] = new Producto("Salvo Lavatrastes Limon 900 ml", "Limpieza", "Lavatrastes", 55.00, 0);
        catalogo[52] = new Producto("Salvo Lavatrastes Limon 500 ml", "Limpieza", "Lavatrastes", 32.90, 0);

        catalogo[53] = new Producto("Amper Mango 475 ml", "Bebidas", "Energizantes", 20, 0);
        catalogo[54] = new Producto("Monster Blanco 355 ml", "Bebidas", "Energizantes", 46, 0);
        catalogo[55] = new Producto("Predator Rojo 475 ml", "Bebidas", "Energizantes", 19, 0);
        catalogo[56] = new Producto("Red Bull 420 ml", "Bebidas", "Energizantes", 52, 0);
        catalogo[57] = new Producto("Vive 100 630 ml", "Bebidas", "Energizantes", 25, 0);

        return catalogo;
    }

    /**
     * Crea el pie de página con botones de navegación.
     */
    private HBox crearPiePagina() {
        HBox piePagina = new HBox();
        piePagina.setStyle("-fx-background-color: #1f3a4d;");
        piePagina.setPadding(new Insets(10));
        piePagina.setSpacing(10);
        piePagina.setAlignment(Pos.CENTER);

        Button btnVolver = new Button("Volver al Menu");
        btnVolver.setStyle("-fx-font-size: 12; -fx-padding: 8;");
        btnVolver.setOnAction(e -> {
            PantallaPrincipal pantallaPrincipal = new PantallaPrincipal(usuario);
            pantallaPrincipal.mostrar(stage);
        });

        piePagina.getChildren().add(btnVolver);
        return piePagina;
    }
}