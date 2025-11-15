package com.tienda.ui;

import com.tienda.Usuario;
import com.tienda.Carrito;
import com.tienda.Producto;
import com.tienda.Controlador;
import com.tienda.Invalidar_Email;
import com.tienda.CantidadInvalidaException;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * PantallaCatalogo mejorada con estilo Mercado Libre.
 * Muestra productos en cards con imagen, descripción y detalles.
 * Los productos se obtienen del Controlador.
 */
public class PantallaCatalogo {
    private Stage stage;
    private Usuario usuario;
    private Carrito carrito;
    private Producto[] catalogo;
    private String[] coloresProductos = {"#FF6B6B", "#4ECDC4", "#45B7D1", "#FFA07A", "#98D8C8", "#F7DC6F"};
    private int colorIndex = 0;

    public PantallaCatalogo(Usuario usuario, Carrito carrito) {
        this.usuario = usuario;
        this.carrito = carrito;
        this.catalogo = obtenerCatalogoDelControlador();
    }

    /**
     * Obtiene el catálogo del Controlador usando reflexión.
     */
    private Producto[] obtenerCatalogoDelControlador() {
        try {
            Controlador controlador = new Controlador();
            Field field = Controlador.class.getDeclaredField("catalogo");
            field.setAccessible(true);
            return (Producto[]) field.get(controlador);
        } catch (Exception e) {
            System.err.println("Error al obtener catálogo del Controlador: " + e.getMessage());
            return new Producto[0];
        }
    }

    /**
     * Muestra la pantalla del catálogo con estilo Mercado Libre.
     */
    public void mostrar(Stage primaryStage) {
        this.stage = primaryStage;

        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #f5f5f5;");

        // Encabezado con logo
        VBox encabezado = crearEncabezado();
        root.setTop(encabezado);

        // Barra de búsqueda y filtros
        HBox barraBusqueda = crearBarraBusqueda();
        
        // Contenido con categorías y productos
        VBox contenido = new VBox();
        contenido.setStyle("-fx-background-color: #f5f5f5;");
        
        BorderPane panelContenido = new BorderPane();
        
        // Panel lateral - Categorías
        VBox panelCategorias = crearPanelCategorias();
        panelContenido.setLeft(panelCategorias);
        
        // Panel central - Productos
        VBox panelProductos = new VBox();
        panelProductos.setPadding(new Insets(20));
        panelProductos.setSpacing(10);
        
        ScrollPane scrollProductos = new ScrollPane(panelProductos);
        scrollProductos.setFitToWidth(true);
        scrollProductos.setStyle("-fx-control-inner-background: #f5f5f5;");
        
        // Cargar categorías
        List<String> categorias = obtenerCategorias();
        for (String categoria : categorias) {
            Button btnCategoria = new Button(categoria);
            btnCategoria.setPrefWidth(180);
            btnCategoria.setStyle("-fx-font-size: 13; -fx-padding: 12; -fx-background-color: white; -fx-border-color: #ddd; -fx-border-width: 0 0 1 0;");
            btnCategoria.setOnAction(e -> mostrarProductosPorCategoria(panelProductos, categoria));
            
            if (panelCategorias.getChildren().size() == 1) {
                panelCategorias.getChildren().add(btnCategoria);
                mostrarProductosPorCategoria(panelProductos, categoria);
            } else {
                panelCategorias.getChildren().add(btnCategoria);
            }
        }
        
        panelContenido.setCenter(scrollProductos);
        contenido.getChildren().addAll(barraBusqueda, panelContenido);
        
        VBox.setVgrow(panelContenido, javafx.scene.layout.Priority.ALWAYS);
        root.setCenter(contenido);

        // Pie de página
        HBox piePagina = crearPiePagina();
        root.setBottom(piePagina);

        Scene scene = new Scene(root, 1400, 800);
        stage.setTitle("City Market - Catálogo");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Crea el encabezado con logo estilo Mercado Libre.
     */
    private VBox crearEncabezado() {
        VBox encabezado = new VBox();
        encabezado.setStyle("-fx-background-color: #fff159;");
        encabezado.setPadding(new Insets(15, 20, 15, 20));
        encabezado.setSpacing(10);

        HBox logoBar = new HBox();
        logoBar.setAlignment(Pos.CENTER_LEFT);
        logoBar.setSpacing(10);

        // Logo simulado
        Circle logo = new Circle(25);
        logo.setFill(Color.web("#3483FA"));
        Label logoText = new Label("CITY");
        logoText.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        logoText.setStyle("-fx-text-fill: white;");
        
        Label market = new Label("MARKET");
        market.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        market.setStyle("-fx-text-fill: #3483FA;");

        logoBar.getChildren().addAll(logo, logoText, market);

        Label titulo = new Label("Catálogo de Productos");
        titulo.setFont(Font.font("Arial", FontWeight.BOLD, 22));
        titulo.setStyle("-fx-text-fill: #333;");

        encabezado.getChildren().addAll(logoBar, titulo);
        return encabezado;
    }

    /**
     * Crea la barra de búsqueda.
     */
    private HBox crearBarraBusqueda() {
        HBox barra = new HBox();
        barra.setStyle("-fx-background-color: white;");
        barra.setPadding(new Insets(15, 20, 15, 20));
        barra.setSpacing(10);
        barra.setAlignment(Pos.CENTER_LEFT);

        TextField busqueda = new TextField();
        busqueda.setPromptText("Buscar productos...");
        busqueda.setPrefHeight(40);
        busqueda.setPrefWidth(600);
        busqueda.setStyle("-fx-font-size: 13; -fx-padding: 10; -fx-border-color: #ddd; -fx-border-radius: 5;");

        Button btnBuscar = new Button("Buscar");
        btnBuscar.setPrefHeight(40);
        btnBuscar.setPrefWidth(100);
        btnBuscar.setStyle("-fx-font-size: 13; -fx-background-color: #3483FA; -fx-text-fill: white; -fx-padding: 10;");

        barra.getChildren().addAll(busqueda, btnBuscar);
        return barra;
    }

    /**
     * Crea el panel de categorías lateral.
     */
    private VBox crearPanelCategorias() {
        VBox panel = new VBox();
        panel.setStyle("-fx-background-color: white; -fx-border-color: #ddd; -fx-border-width: 0 1 0 0;");
        panel.setPadding(new Insets(15));
        panel.setSpacing(5);
        panel.setPrefWidth(200);

        Label labelCategorias = new Label("Categorías");
        labelCategorias.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        labelCategorias.setStyle("-fx-text-fill: #333;");
        panel.getChildren().add(labelCategorias);
        
        Separator sep = new Separator();
        panel.getChildren().add(sep);

        return panel;
    }

    /**
     * Muestra los productos de una categoría en formato grid.
     */
    private void mostrarProductosPorCategoria(VBox panelProductos, String categoria) {
        panelProductos.getChildren().clear();

        Label labelCategoria = new Label(categoria);
        labelCategoria.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        labelCategoria.setStyle("-fx-text-fill: #333;");
        panelProductos.getChildren().add(labelCategoria);

        // Grid de productos
        FlowPane gridProductos = new FlowPane();
        gridProductos.setStyle("-fx-background-color: #f5f5f5;");
        gridProductos.setHgap(20);
        gridProductos.setVgap(20);
        gridProductos.setPrefWrapLength(0);

        for (int i = 0; i < catalogo.length; i++) {
            Producto p = catalogo[i];
            if (p != null && p.getCategoria().equals(categoria)) {
                VBox cardProducto = crearCardProducto(p, i);
                gridProductos.getChildren().add(cardProducto);
            }
        }

        ScrollPane scroll = new ScrollPane(gridProductos);
        scroll.setFitToWidth(true);
        scroll.setStyle("-fx-control-inner-background: #f5f5f5;");
        
        VBox.setVgrow(scroll, javafx.scene.layout.Priority.ALWAYS);
        panelProductos.getChildren().add(scroll);
    }

    /**
     * Crea una card de producto estilo Mercado Libre.
     */
    private VBox crearCardProducto(Producto producto, int indice) {
        VBox card = new VBox();
        card.setStyle("-fx-background-color: white; -fx-border-color: #ddd; -fx-border-radius: 5;");
        card.setPadding(new Insets(0));
        card.setSpacing(10);
        card.setPrefWidth(220);
        card.setPrefHeight(320);

        // Imagen del producto
        VBox imagenBox = new VBox();
        imagenBox.setStyle("-fx-background-color: #f9f9f9;");
        imagenBox.setPrefHeight(150);
        imagenBox.setAlignment(Pos.CENTER);

        ImageView imagenView = cargarImagen(producto);
        imagenBox.getChildren().add(imagenView);

        // Información del producto
        VBox infoBox = new VBox();
        infoBox.setPadding(new Insets(12));
        infoBox.setSpacing(8);

        // Nombre del producto
        Label nombre = new Label(producto.getNombre());
        nombre.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        nombre.setStyle("-fx-text-fill: #333; -fx-wrap-text: true;");
        nombre.setWrapText(true);

        // Descripción simulada
        Label descripcion = new Label(producto.getSubcategoria());
        descripcion.setFont(Font.font("Arial", 11));
        descripcion.setStyle("-fx-text-fill: #999;");
        descripcion.setWrapText(true);

        // Precio destacado
        Label precio = new Label("$" + String.format("%.2f", producto.getPrecio()));
        precio.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        precio.setStyle("-fx-text-fill: #3483FA;");

        // Stock disponible
        Label stock = new Label("Stock disponible");
        stock.setFont(Font.font("Arial", 10));
        stock.setStyle("-fx-text-fill: #00a82d;");

        infoBox.getChildren().addAll(nombre, descripcion, precio, stock);

        // Panel de compra
        HBox compraBox = new HBox();
        compraBox.setPadding(new Insets(10));
        compraBox.setSpacing(5);
        compraBox.setAlignment(Pos.CENTER);

        Spinner<Integer> spinner = new Spinner<>(1, 100, 1);
        spinner.setPrefWidth(70);
        spinner.setStyle("-fx-font-size: 11;");

        Button btnAgregar = new Button("Agregar");
        btnAgregar.setPrefHeight(35);
        btnAgregar.setStyle("-fx-font-size: 11; -fx-font-weight: bold; -fx-background-color: #3483FA; -fx-text-fill: white; -fx-padding: 8;");
        btnAgregar.setOnAction(e -> {
            int cantidad = spinner.getValue();
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

        compraBox.getChildren().addAll(spinner, btnAgregar);

        card.getChildren().addAll(imagenBox, infoBox, compraBox);
        return card;
    }

    /**
     * Carga la imagen de un producto desde la carpeta de recursos con subcategorías.
     */
    private ImageView cargarImagen(Producto producto) {
        ImageView imageView = new ImageView();
        imageView.setFitWidth(150);
        imageView.setFitHeight(150);
        imageView.setPreserveRatio(true);

        try {
            // Construir nombre del archivo desde el nombre del producto
            String nombreProducto = producto.getNombre()
                .toLowerCase()
                .replaceAll(" ", "-")
                .replaceAll("[^a-z0-9-]", "");

            // Construir nombre de subcategoría
            String subcategoria = producto.getSubcategoria()
                .toLowerCase()
                .replaceAll(" ", "-")
                .replaceAll("[^a-z0-9-]", "");

            // Ruta: imagenes/categoria/subcategoria/nombre-producto.png
            String ruta = "file:imagenes/" + producto.getCategoria().toLowerCase() + "/" +subcategoria + "/" +nombreProducto + ".png";

            Image imagen = new Image(ruta);
            imageView.setImage(imagen);

        } catch (Exception e) {
            // Si hay error, mostrar icono de paquete
            System.err.println("Error cargando imagen para " + producto.getNombre() + ": " + e.getMessage());
            Label imagenLabel = new Label("📦");
            imagenLabel.setFont(Font.font("Arial", 60));
        }

        return imageView;
    }

    /**
     * Obtiene las categorías únicas del catálogo.
     */
    private List<String> obtenerCategorias() {
        List<String> categorias = new ArrayList<>();
        for (Producto p : catalogo) {
            if (p != null && !categorias.contains(p.getCategoria())) {
                categorias.add(p.getCategoria());
            }
        }
        return categorias;
    }

    /**
     * Crea el pie de página.
     */
    private HBox crearPiePagina() {
        HBox piePagina = new HBox();
        piePagina.setStyle("-fx-background-color: #222;");
        piePagina.setPadding(new Insets(15));
        piePagina.setSpacing(20);
        piePagina.setAlignment(Pos.CENTER_LEFT);

        Button btnVolver = new Button("Volver al Menu");
        btnVolver.setStyle("-fx-font-size: 12; -fx-padding: 10; -fx-background-color: #3483FA; -fx-text-fill: white;");
        btnVolver.setOnAction(e -> {
            PantallaPrincipal pantallaPrincipal = new PantallaPrincipal(usuario, carrito);
            pantallaPrincipal.mostrar(stage);
        });

        piePagina.getChildren().add(btnVolver);
        return piePagina;
    }
}