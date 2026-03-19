package com.universidad.pedidos;

import com.universidad.pedidos.command.ComandoAplicarDescuento;
import com.universidad.pedidos.command.ComandoConfirmar;
import com.universidad.pedidos.command.HistorialComandos;
import com.universidad.pedidos.cor.ValidadorCredito;
import com.universidad.pedidos.cor.ValidadorMonto;
import com.universidad.pedidos.cor.ValidadorPedido;
import com.universidad.pedidos.cor.ValidadorStock;
import com.universidad.pedidos.modelo.Pedido;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PedidosApp implements CommandLineRunner {

    private final HistorialComandos historial;

    public PedidosApp(HistorialComandos historial) {
        this.historial = historial;
    }

    public static void main(String[] args) {
        SpringApplication.run(PedidosApp.class, args);
    }

    @Override
    public void run(String... args) {
        ValidadorPedido cadena = new ValidadorStock();
        cadena.setNext(new ValidadorMonto())
            .setNext(new ValidadorCredito());

        Pedido p1 = new Pedido("P-001", "PROD-001", 3, 45000.0, true);

        System.out.println("--- Validando pedido P-001 ---");
        boolean valido = cadena.validar(p1);
        System.out.println("Resultado validación: " + valido);

        if (valido) {
            historial.ejecutar(new ComandoConfirmar(p1));
            historial.ejecutar(new ComandoAplicarDescuento(p1, 10));
            System.out.println("Estado actual: " + p1);

            System.out.println();
            System.out.println("--- Deshaciendo última acción ---");
            historial.deshacer();
            System.out.println("Estado después de undo: " + p1);
        }

        System.out.println();
        System.out.println("--- Validando pedido P-002 (sin crédito) ---");
        Pedido p2 = new Pedido("P-002", "PROD-002", 2, 30000.0, false);
        cadena.validar(p2);
    }
}
