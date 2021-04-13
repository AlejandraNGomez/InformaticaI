/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Alejandra
 */
public class RAM {

    private Map<String,String> valoresPorDireccion;
    private Map<String,String> instruccionesPorDireccion;
//    private Map<String,String> valoresPorDireccionProgramaDefecto;
//    private Map<String,String> instruccionesPorDireccionProgramaDefecto;

    public RAM(){
        valoresPorDireccion = new HashMap<String,String>();
        instruccionesPorDireccion = new HashMap<String,String>();
        for(int i = 0;i<16;i++){
            valoresPorDireccion.put(String.valueOf(i), "0");
            instruccionesPorDireccion.put(String.valueOf(i), "LDA");
        }
    }
    
    public Map<String,String> getValoresPorDireccion() {
        return valoresPorDireccion;
    }
    
    public void setValoresPorDireccion(Map<String,String> valoresPorDireccion) {
        this.valoresPorDireccion = valoresPorDireccion;
    }
    
    public Map<String,String> getInstruccionesPorDireccion() {
        return instruccionesPorDireccion;
    }
    
    public void setinstruccionesPorDireccion(Map<String,String> instruccionesPorDireccion) {
        this.instruccionesPorDireccion = instruccionesPorDireccion;
    }
    
    public void programaPorDefecto(){        
        this.valoresPorDireccion.put("0", "9");
        this.instruccionesPorDireccion.put("0", "LDA");
        
        this.valoresPorDireccion.put("1", "10");
        this.instruccionesPorDireccion.put("1", "ADD");
        
        this.valoresPorDireccion.put("2", "11");
        this.instruccionesPorDireccion.put("2", "ADD");

        this.valoresPorDireccion.put("3", "12");
        this.instruccionesPorDireccion.put("3", "SUB");
        
        this.valoresPorDireccion.put("4", "224");
        this.instruccionesPorDireccion.put("4", "OUT");

        this.valoresPorDireccion.put("5", "240");
        this.instruccionesPorDireccion.put("5", "HLT");
        
        this.valoresPorDireccion.put("9", "16");
        this.instruccionesPorDireccion.put("9", "LDA");
        
        this.valoresPorDireccion.put("10", "20");
        this.instruccionesPorDireccion.put("10", "LDA");
        
        this.valoresPorDireccion.put("11", "24");
        this.instruccionesPorDireccion.put("11", "LDA");
        
        this.valoresPorDireccion.put("12", "32");
        this.instruccionesPorDireccion.put("12", "LDA");
        
    } 
    
    public void borrarPrograma(){    
        
        for(int i = 0;i<16;i++){
            valoresPorDireccion.put(String.valueOf(i), " 0  0  0  0  0  0   0   0");
            instruccionesPorDireccion.put(String.valueOf(i), "LDA");
        }    
        
        
    } 
}
