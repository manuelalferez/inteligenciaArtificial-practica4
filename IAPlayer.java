package conecta4;

/**
 * Esta clase representa la inteligencia artificial cuyo objetivo es ganar a su adversario humano.
 * <p>
 * IAPlayer realizará aquellos movimiento con menor valor.
 */
public class IAPlayer extends Player {
    private final int SIN_JUGADA = -1;
    private int CONECTA_N = 0;
    private final int SIN_GANADOR = 0;
    private final int PEOR_VALORACION_MIN = 1;
    private final int PEOR_VALORACION_MAX = -1;

    /**
     * @param tablero Representación del tablero de juego
     * @param conecta Número de fichas consecutivas para ganar
     * @return Jugador ganador (si lo hay)
     */
    @Override
    public int turnoJugada(Grid tablero, int conecta) {
        CONECTA_N = conecta;
        System.out.println("1");
        Grid copia_tablero = new Grid(tablero.getFilas(), tablero.getColumnas(), "assets/player1.png", "assets/player2.png");
        System.out.println("2");
        copiarTablero(tablero, copia_tablero);
        System.out.println("3");
        int mejorJugada = algoritmoMinMax(copia_tablero);
        return tablero.checkWin(tablero.setButton(mejorJugada, Conecta4.PLAYER2), mejorJugada, conecta);
    }

    /**
     * @param tablero Estado actual del tablero
     * @return La columna con el mejor movimiento
     */
    private int algoritmoMinMax(Grid tablero) {
        int mejor_jugada = SIN_JUGADA;
        mejor_jugada = minimizar(tablero);
        return mejor_jugada;
    }

    private int maximizar(Grid tablero) {
        int mejor_jugada = SIN_JUGADA;
        Grid copia_tablero = tablero;
        int valoracion = PEOR_VALORACION_MAX;
        int mejor_valoracion = valoracion;

        for (int col = 0; col < tablero.getColumnas(); col++) {
            if (!copia_tablero.fullColumn(col)) {
                int estado_del_juego = tablero.checkWin(copia_tablero.setButton(col, Conecta4.PLAYER1), col, CONECTA_N);
                if (estado_del_juego == SIN_GANADOR) {
                    valoracion = mayorValor(valoracion, maximizar(copia_tablero));
                    if (valoracion > mejor_valoracion) {
                        mejor_valoracion = valoracion;
                        mejor_jugada = col;
                    }
                    copia_tablero = tablero;
                } else {
                    return estado_del_juego;
                }
            }
        }
        return mejor_jugada;
    }

    private int minimizar(Grid tablero) {
        int mejor_jugada = SIN_JUGADA;
        Grid copia_tablero = tablero;
        int valoracion = PEOR_VALORACION_MIN;
        int mejor_valoracion = valoracion;

        for (int col = 0; col < tablero.getColumnas(); col++) {
            if (!copia_tablero.fullColumn(col)) {
                int estado_del_juego = tablero.checkWin(copia_tablero.setButton(col, Conecta4.PLAYER2), col, CONECTA_N);
                if (estado_del_juego == SIN_GANADOR) {
                    valoracion = menorValor(valoracion, maximizar(copia_tablero));
                    if (valoracion < mejor_valoracion) {
                        mejor_valoracion = valoracion;
                        mejor_jugada = col;
                    }
                    copia_tablero = tablero;
                } else {
                    return estado_del_juego;
                }
            }
        }
        return mejor_jugada;
    }

    private int menorValor(int primer, int segundo) {
        return primer < segundo ? primer : segundo;
    }

    private int mayorValor(int primer, int segundo) {
        return primer > segundo ? primer : segundo;
    }

    private void copiarTablero(Grid tablero_origen, Grid tablero_destino) {
        int casillas[][] = tablero_origen.toArray();
        System.out.println("Tablero destino inicio:");
        tablero_destino.print();
        for (int fila = 0; fila < tablero_origen.getFilas(); fila++) {
            for (int col = 0; col < tablero_origen.getColumnas(); col++) {
                if(tablero_origen.getButton(col,fila)==1){
                    tablero_destino.setButton(col, Conecta4.PLAYER1);
                }else if(tablero_origen.getButton(col,fila)==-1){
                    tablero_destino.setButton(col, Conecta4.PLAYER2);
                }
            }
        }
        System.out.println("Tablero destino después:");
        tablero_destino.print();
    }

    // Método para mostrar el estado actual del tablero por la salida estándar
    private void print(int tablero[][]) {
        //System.out.println("Conecta-N:");
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.print(tablero[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

}
