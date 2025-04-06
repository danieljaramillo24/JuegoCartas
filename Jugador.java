import java.util.HashSet;
import java.util.Random;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.swing.JPanel;

public class Jugador {

    private int TOTAL_CARTAS = 10;
    private int MARGEN = 10;
    private int DISTANCIA = 40;

    private Carta[] cartas = new Carta[TOTAL_CARTAS];
    private Random r = new Random(); // la suerte del jugador

    public void repartir() {
        for (int i = 0; i < TOTAL_CARTAS; i++) {
            cartas[i] = new Carta(r);
        }
    }

    public void mostrar(JPanel pnl) {
        pnl.removeAll();

        int posicion = MARGEN + (TOTAL_CARTAS - 1) * DISTANCIA;
        for (Carta carta : cartas) {
            carta.mostrar(pnl, posicion, MARGEN);
            posicion -= DISTANCIA;
        }

        pnl.repaint();
    }


    public String getGrupos() {
        String mensaje = "No se encontraron grupos";
        int pintas = Pinta.values().length;
        int cartasNumero = NombreCarta.values().length;
        Set<String> cartasUnicas = new HashSet<>();
        String mensajeEscalera = "";
        int[] contadores = new int[NombreCarta.values().length];
        for (Carta carta : cartas) {
            contadores[carta.getNombre().ordinal()]++;
        }

        String[][] agrupacionesCartas = new String[pintas][cartasNumero];

        for(Carta carta : cartas){
            agrupacionesCartas[carta.getPinta().ordinal()][carta.getNombre().ordinal()] = carta.getNombre().name();
        }
    
        for (int i=0;i<pintas;i++){
            for (int j=1;j<cartasNumero-1;j++) {
                if((agrupacionesCartas[i][j-1]!=null && agrupacionesCartas[i][j]!=null) || (agrupacionesCartas[i][j]!=null && agrupacionesCartas[i][j+1]!=null)){
                    cartasUnicas.add(agrupacionesCartas[i][j-1]);
                    cartasUnicas.add(agrupacionesCartas[i][j]);
                    cartasUnicas.add(agrupacionesCartas[i][j+1]);
                }
            }
            if(!cartasUnicas.isEmpty()){
                cartasUnicas.remove(null);
                mensajeEscalera+= cartasUnicas.toString() + " de " + Pinta.values()[i] + " conforman un " + Grupo.values()[cartasUnicas.size()] + " de " + Pinta.values()[i] + "\n";
                cartasUnicas.clear();
            }
 
        }

        boolean hayGrupos = false;
        for (int contador : contadores) {
            if (contador >= 2) {
                hayGrupos = true;
                break;
            }
        }

        if (hayGrupos) {
            mensaje = "Se encontraron los siguientes grupos:\n";
            int fila = 0;
            for (int contador : contadores) {
                if (contador >= 2) {
                    mensaje += Grupo.values()[contador] + " de " + NombreCarta.values()[fila] + "\n";
                }
                fila++;
            }
        }
        mensaje += mensajeEscalera;
        return mensaje;
    }

}
