/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.RAM;
import Modelo.Simulador;

/**
 *
 * @author Alejandra
 */
public class Fachada {
    
    private RAM ram;
    private Simulador simulador;
    
    
    public Fachada(RAM ram){
        this.ram = ram;
        this.simulador = new Simulador(ram);
    }
    
    public RAM getRAM() {
        return ram;
    }
    public Simulador getSimulador() {
        return simulador;
    }
    
   
    public boolean ramTieneDatosEnAlgunaDireccion(){        
        Boolean hayDatos = false;
        for(int i = 0;i<16;i++){            
            if(!"0".equals(ram.getValoresPorDireccion().get(String.valueOf(i))) && 
                    !"LDA".equals(ram.getInstruccionesPorDireccion().get(String.valueOf(i)))){                
                hayDatos =true;
                break;
            }
        }
        return hayDatos;
    }
    
}
