package com.tienda;

/**
 * Excepción personalizada para errores relacionados con la validación del correo electrónico.
 * Se lanza cuando el email del usuario no cumple con el formato válido.
 */
public class Invalidar_Email extends Exception {
    public Invalidar_Email(String mensaje) {
        super(mensaje);
    }

    public Invalidar_Email(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}

/**
 * Excepción personalizada para errores de validación de usuario.
 * Se lanza cuando los datos del usuario son inválidos o incompletos.
 */
class UsuarioInvalidoException extends Exception {
    public UsuarioInvalidoException(String mensaje) {
        super(mensaje);
    }

    public UsuarioInvalidoException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}

/**
 * Excepción personalizada para errores de selección de producto.
 * Se lanza cuando el usuario selecciona un producto que no existe en el catálogo.
 */
class ProductoNoEncontradoException extends Exception {
    public ProductoNoEncontradoException(String mensaje) {
        super(mensaje);
    }

    public ProductoNoEncontradoException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}

/**
 * Excepción personalizada para errores de carrito.
 * Se lanza cuando hay problemas al agregar productos al carrito.
 */
class CarritoLlenoException extends Exception {
    public CarritoLlenoException(String mensaje) {
        super(mensaje);
    }

    public CarritoLlenoException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}

/**
 * Excepción personalizada para errores de cantidad de producto.
 * Se lanza cuando la cantidad ingresada no es válida.
 */
class CantidadInvalidaException extends Exception {
    public CantidadInvalidaException(String mensaje) {
        super(mensaje);
    }

    public CantidadInvalidaException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}