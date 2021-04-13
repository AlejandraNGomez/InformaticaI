
import Controlador.Fachada;
import Vista.Simulador;



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 */
public class Principal {
    static Simulador simuladorVista;
    static Fachada fachada;
    
    public static void main(String args[]) {
        fachada = new Fachada(new Modelo.RAM());   
        simuladorVista = new Simulador(fachada);
        simuladorVista.setResizable(false);
        simuladorVista.setVisible(true);
        
    }
}
