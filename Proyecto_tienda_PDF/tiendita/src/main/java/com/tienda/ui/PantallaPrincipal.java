package com.tienda.ui;

import com.tienda.Usuario;
import com.tienda.Carrito;
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

/**
 * PantallaPrincipal muestra el menú principal de la tienda con opciones para:
 * - Ver el catálogo
 * - Ver el carrito
 * - Finalizar compra
 */
public class PantallaPrincipal {
    private Stage stage;
    private Usuario usuario;
    private Carrito carrito;

    public PantallaPrincipal(Usuario usuario) {
        this.usuario = usuario;
        this.carrito = new Carrito(20);
    }

    /**
     * Muestra la pantalla principal.
     */
    public void mostrar(Stage primaryStage) {
        this.stage = primaryStage;

        // Crear el layout principal
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #f0f0f0;");

        // Encabezado
        VBox encabezado = crearEncabezado();
        root.setTop(encabezado);

        // Contenido central
        VBox contenido = crearContenidoPrincipal();
        root.setCenter(contenido);

        // Pie de página con datos del usuario
        HBox piePagina = crearPiePagina();
        root.setBottom(piePagina);

        // Crear la escena
        Scene scene = new Scene(root, 800, 600);

        // Configurar el stage
        stage.setTitle("City Market - Menu Principal");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Crea el encabezado de la pantalla.
     */
    private VBox crearEncabezado() {
        VBox encabezado = new VBox();
        encabezado.setStyle("-fx-background-color: #1f3a4d;");
        encabezado.setPadding(new Insets(20));
        encabezado.setAlignment(Pos.CENTER);

        Label titulo = new Label("CITY MARKET");
        titulo.setFont(Font.font("Arial", FontWeight.BOLD, 32));
        titulo.setStyle("-fx-text-fill: white;");

        Label subtitulo = new Label("Tu tienda online de confianza");
        subtitulo.setFont(Font.font("Arial", 14));
        subtitulo.setStyle("-fx-text-fill: #b0b0b0;");

        encabezado.getChildren().addAll(titulo, subtitulo);
        return encabezado;
    }

    /**
     * Crea el contenido principal con los botones del menú.
     */
    private VBox crearContenidoPrincipal() {
        VBox contenido = new VBox();
        contenido.setPadding(new Insets(60));
        contenido.setSpacing(20);
        contenido.setAlignment(Pos.CENTER);

        Label labelMenu = new Label("Menu Principal");
        labelMenu.setFont(Font.font("Arial", FontWeight.BOLD, 24));

        // Botón Ver Catálogo
        Button btnCatalogo = crearBoton("Ver Catalogo", 250);
        btnCatalogo.setOnAction(e -> {
            PantallaCatalogo pantallaCatalogo = new PantallaCatalogo(usuario, carrito);
            pantallaCatalogo.mostrar(stage);
        });

        // Botón Ver Carrito
        Button btnCarrito = crearBoton("Ver Carrito", 250);
        btnCarrito.setOnAction(e -> {
            PantallaCarrito pantallaCarrito = new PantallaCarrito(usuario, carrito);
            pantallaCarrito.mostrar(stage);
        });

        // Botón Finalizar Compra
        Button btnFinalizar = crearBoton("Finalizar Compra", 250);
        btnFinalizar.setOnAction(e -> {
            if (carrito.getContador() == 0) {
                Alert alerta = new Alert(Alert.AlertType.WARNING);
                alerta.setTitle("Carrito Vacio");
                alerta.setHeaderText("No hay productos");
                alerta.setContentText("Debes agregar productos antes de finalizar la compra.");
                alerta.showAndWait();
            } else {
                // Mostrar el ticket
                carrito.imprimirTicket(usuario);
                
                Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                alerta.setTitle("Compra Finalizada");
                alerta.setHeaderText("Gracias por su compra");
                alerta.setContentText("Tu compra ha sido procesada exitosamente.\nTotal: $" + String.format("%.2f", carrito.calcularTotal()));
                alerta.showAndWait();

                // Volver al menu principal
                carrito.vaciarCarrito();
                mostrar(stage);
            }
        });

        // Botón Salir
        Button btnSalir = crearBoton("Salir", 250);
        btnSalir.setStyle("-fx-font-size: 14; -fx-font-weight: bold; -fx-background-color: #d32f2f; -fx-text-fill: white;");
        btnSalir.setOnMouseEntered(e -> btnSalir.setStyle("-fx-font-size: 14; -fx-font-weight: bold; -fx-background-color: #b71c1c; -fx-text-fill: white;"));
        btnSalir.setOnMouseExited(e -> btnSalir.setStyle("-fx-font-size: 14; -fx-font-weight: bold; -fx-background-color: #d32f2f; -fx-text-fill: white;"));
        btnSalir.setOnAction(e -> stage.close());

        contenido.getChildren().addAll(
                labelMenu,
                new Separator(),
                btnCatalogo,
                btnCarrito,
                btnFinalizar,
                btnSalir
        );

        return contenido;
    }

    /**
     * Crea el pie de página con información del usuario.
     */
    private HBox crearPiePagina() {
        HBox piePagina = new HBox();
        piePagina.setStyle("-fx-background-color: #1f3a4d;");
        piePagina.setPadding(new Insets(15));
        piePagina.setSpacing(40);

        Label labelUsuario = new Label("Usuario: " + usuario.getNombre());
        labelUsuario.setStyle("-fx-text-fill: white; -fx-font-size: 12;");

        Label labelEmail = new Label("Email: " + usuario.getEmail());
        labelEmail.setStyle("-fx-text-fill: white; -fx-font-size: 12;");

        Label labelDireccion = new Label("Direccion: " + usuario.getDireccion());
        labelDireccion.setStyle("-fx-text-fill: white; -fx-font-size: 12;");

        piePagina.getChildren().addAll(labelUsuario, labelEmail, labelDireccion);
        return piePagina;
    }

    /**
     * Método auxiliar para crear botones con estilo consistente.
     */
    private Button crearBoton(String texto, int ancho) {
        Button boton = new Button(texto);
        boton.setPrefHeight(50);
        boton.setPrefWidth(ancho);
        boton.setStyle("-fx-font-size: 14; -fx-font-weight: bold; -fx-background-color: #1f3a4d; -fx-text-fill: white;");
        boton.setOnMouseEntered(e -> boton.setStyle("-fx-font-size: 14; -fx-font-weight: bold; -fx-background-color: #153a4d; -fx-text-fill: white;"));
        boton.setOnMouseExited(e -> boton.setStyle("-fx-font-size: 14; -fx-font-weight: bold; -fx-background-color: #1f3a4d; -fx-text-fill: white;"));
        return boton;
    }

    public Carrito getCarrito() {
        return carrito;
    }
}