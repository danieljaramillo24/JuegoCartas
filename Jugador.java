import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Random;
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

    public int[] contarCartas() {
        int[] contadores = new int[NombreCarta.values().length];
        for (Carta carta : cartas) {
            contadores[carta.getNombre().ordinal()]++;
        }
        return contadores;
    }
    
    //Método para obtener las posibles escaleras que haya en el maso
    public String getEscalera(){
        int cartasNumero = NombreCarta.values().length;
        int pintas = Pinta.values().length;
        //Se usará un set para evitar la duplicación de cartas
        Set<String> cartasUnicas = new LinkedHashSet<>();
        String mensajeEscalera = "";
        String[][] agrupacionesCartas = new String[pintas][cartasNumero];
        //Este for toma cada carta y la mete dentro de la posición que le corresponde en cada pinta
        for(Carta carta : cartas){
            agrupacionesCartas[carta.getPinta().ordinal()][carta.getNombre().ordinal()] = carta.getNombre().name();
        }
        //Este doble ciclo for se encarga de recorrer la matriz de pintas y encontrar si hay escaleras
        for (int i=0;i<pintas;i++){
            for (int j=1;j<cartasNumero;j++) {
                //Se verifica que la carta anterior y la siguiente estén
                if(agrupacionesCartas[i][j-1]!=null && agrupacionesCartas[i][j]!=null){
                    cartasUnicas.add(agrupacionesCartas[i][j-1]);
                    cartasUnicas.add(agrupacionesCartas[i][j]);
                    //Se verifica si ya hay un conjunto de escaleras y la siguiente carta no pertenece al conjunto
                    if(j<cartasNumero-1 && agrupacionesCartas[i][j]!=null && agrupacionesCartas[i][j+1]==null){
                        mensajeEscalera+= cartasUnicas.toString() + " de " + Pinta.values()[i] + " conforman un " + Grupo.values()[cartasUnicas.size()] + " de " + Pinta.values()[i] + "\n";
                        cartasUnicas.clear();
                    }
                }
            }
            //Verifica que haya escaleras en las pintas
            if(!cartasUnicas.isEmpty()){
                mensajeEscalera+= cartasUnicas.toString() + " de " + Pinta.values()[i] + " conforman un " + Grupo.values()[cartasUnicas.size()] + " de " + Pinta.values()[i] + "\n";
                cartasUnicas.clear();
            }
 
        }
        return mensajeEscalera;
    }

    public String getGrupos() {
        String mensaje = "No se encontraron grupos";
        
        int[] contadores = contarCartas();



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
        String mensajeEscalera = getEscalera();
        mensaje += mensajeEscalera;
        return mensaje;
    }

    public int calcularPuntaje() {
        int puntaje = 0;
    
        int[] contadores = contarCartas();
    
        for (Carta carta : cartas) {
            int cantidad = contadores[carta.getNombre().ordinal()];
            if (cantidad == 1) { // Solo cuentan las que no están en grupo (Cartas no duplicadas)
                
                // Creamos un switch para definir el valor por defecto (10), para acumular si se trata de cartas alfanuméricas, y el valor de su posición ordinal + 1 si se trata de una carta numérica.
                switch (carta.getNombre()) {
                    case AS:
                    case JACK:
                    case QUEEN:
                    case KING:
                        puntaje += 10;
                        break;
                    default:
                        puntaje += carta.getNombre().ordinal() + 1;
                        break;
                }
            }
        }
    
        return puntaje;
    }


}

