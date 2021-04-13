/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import Controlador.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
/**
 *
 * @author Anderson
 */
public class Simulador  {

    /**
     * @param contadorPrograma the contadorPrograma to set
     */
    public void setContadorPrograma(String contadorPrograma) {
        this.contadorPrograma = contadorPrograma;
    }
	// Assign constant integer values to each control line signal
    public static final int HLT = 0;
    public static final int MI = 1;
    public static final int RI = 2;
    public static final int RO = 3;
    public static final int IO = 4;
    public static final int II = 5;
    public static final int AI = 6;
    public static final int AO = 7;
    public static final int SO = 8;
    public static final int SU = 9;
    public static final int BI = 10;
    public static final int OI = 11;
    public static final int CE = 12;
    public static final int CO = 13;
    public static final int J = 14;
    public static final int FI = 15;
    
    private int stepCount;
    private Boolean resetearLineas;
    private Boolean bloquear = false;
    private HashMap <Integer,Boolean> lineasControl;
    private HashMap<String,String> yaEntro;
    private RAM ram; 
    private String valorActual;
    private String valorActualEnBinarioRAM;
    private String valorActualEnBinarioIR;
    private String valorActualEnBinarioMAR;
    private String valorActualEnBinarioAC;
    private String valorActualEnBinarioB;
    private String valorActualEnBinarioALU;
    private String valorActualEnBinarioSTA;
    private String valorActualAcarreoSuma;
    private String valorActualAcarreoResta;
    private String resultadoOUT;
    private String resultadoRTA;
    private String instruccionActual;
    private String contadorPrograma;
    private Boolean saltoJMP= false;
    private Boolean yaEjecuto= false;
    private int contador=1;
    private int siguientePosicion =0;
    private boolean ejecutar;
    private String lineaControlEjecutando="";
    
    

    public Simulador(RAM ram) {
        this.ram = ram;
        this.lineasControl = new HashMap<Integer,Boolean>(); 
        this.yaEntro =new HashMap<String,String>();
        
    }
        
        
        
    public void procesos() {
        bloquear = true;
        if (this.getStepCount() == 12) {
            this.setStepCount((byte) 1);
            siguientePosicion=siguientePosicion+1;  
            contador++;
        } else {
            this.setStepCount((this.getStepCount() + 1));
        }
            
        if (this.getStepCount() == 1) {
            this.resetearLineasDeControl();
            lineasControl= new HashMap<Integer,Boolean>();  
            lineasControl.put(CO, true);
            lineasControl.put(MI, true);
            this.setLineasControl(lineasControl);

        } else if (this.stepCount == 2) {
            this.resetearLineasDeControl();
            lineasControl= new HashMap<Integer,Boolean>();     
            lineasControl.put(CE, true);
            lineasControl.put(RO, true);
            lineasControl.put(II, true);
            this.setLineasControl(lineasControl);

        } else{
            setValorActual(ram.getValoresPorDireccion().get(String.valueOf(siguientePosicion)));
            setInstruccionActual(ram.getInstruccionesPorDireccion().get(String.valueOf(siguientePosicion)));

            if (getInstruccionActual() .equals("JMP")) {
                if(!yaEjecuto){
                    yaEntro.put("JMP", getValorActual()+"-"+this.stepCount);
                    setYaEntro(yaEntro);
                
                    if (this.stepCount == 3) {
                        this.resetearLineasDeControl();
                        setContadorPrograma(this.convertirDecimalABinarioManual(contador));
                        this.setLineaControlEjecutando("PC");
                        lineasControl= new HashMap<Integer,Boolean>();     
                        lineasControl.put(IO, true);
                        lineasControl.put(J, true);
                        this.setLineasControl(lineasControl);
                    }
                    if (this.stepCount == 4) {
                        this.resetearLineasDeControl();
                    }
                    if (this.stepCount == 5) {
                        this.resetearLineasDeControl();
                        setValorActualEnBinarioRAM(this.convertirDecimalABinarioManual(Long.parseLong((this.getValorActual()))));
                        this.setLineaControlEjecutando("RAM");
                    }
                    if (this.stepCount == 6) {
                        this.resetearLineasDeControl();
                        setValorActualEnBinarioIR(String.valueOf(this.convertirDecimalABinarioManual(Long.parseLong(this.getValorActual()))));
                        this.setLineaControlEjecutando("IR");
                    }
                    if (this.stepCount == 7) {
                        this.resetearLineasDeControl();
                        setValorActualEnBinarioMAR(this.convertirDecimalABinarioManual(Long.parseLong(this.getValorActual())));
                        this.setLineaControlEjecutando("MAR");
                    }
                    if (this.stepCount == 8) {
                        this.resetearLineasDeControl();
                        System.out.println("Long.parseLong(ram.getValoresPorDireccion().get(this.getValorActual())))-->" + Long.parseLong(ram.getValoresPorDireccion().get(this.getValorActual())));
                        System.out.println("Long.parseLong(ram.getValoresPorDireccion().get(this.getValorActual())))-->" + (ram.getInstruccionesPorDireccion().get(this.getValorActual())));
                        siguientePosicion = Integer.parseInt(this.getValorActual());
                        setInstruccionActual((ram.getInstruccionesPorDireccion().get(this.getValorActual())));
                        setValorActual((ram.getValoresPorDireccion().get(this.getValorActual())));
                        setSaltoJMP(true);
                    }                      
     
                }
            }
            if (getInstruccionActual().equals("NOP")) {
                this.setContadorPrograma(this.convertirDecimalABinarioManual(contador));
                        this.setLineaControlEjecutando("PC");
                if (this.stepCount == 3) {
                    this.resetearLineasDeControl();
                }
                if (this.stepCount == 4) {
                    this.resetearLineasDeControl();
                }
                if (this.stepCount == 5) {
                    this.resetearLineasDeControl();
                }
            }
            if (getInstruccionActual().equals("LDA") ) {
                if(getSaltoJMP()){
                    this.stepCount = 3;
                    contador = 2;
                }
                
                this.setContadorPrograma(this.convertirDecimalABinarioManual(contador));
                        this.setLineaControlEjecutando("PC");
                if (this.stepCount == 3) {
                    this.resetearLineasDeControl();
                    lineasControl= new HashMap<Integer,Boolean>();     
                    lineasControl.put(IO, true);
                    lineasControl.put(MI, true);
                    this.setLineasControl(lineasControl);
                    setValorActualEnBinarioRAM(String.valueOf(this.convertirDecimalABinarioManual(Long.parseLong(this.getValorActual()))));
                    this.setLineaControlEjecutando("RAM");
                }
                if (this.stepCount == 4) {
                    this.resetearLineasDeControl();
                    lineasControl= new HashMap<Integer,Boolean>();     
                    lineasControl.put(RO, true);
                    lineasControl.put(AI, true);
                    this.setLineasControl(lineasControl);
                    setValorActualEnBinarioIR(String.valueOf(this.convertirDecimalABinarioManual(Long.parseLong(this.getValorActual()))));                    
                    this.setLineaControlEjecutando("IR");
                }
                if (this.stepCount == 5) {
                    this.resetearLineasDeControl();
                    setValorActualEnBinarioMAR(this.convertirDecimalABinarioManual(Long.parseLong(this.getValorActual())));
                    this.setLineaControlEjecutando("MAR");
                }
                if (this.stepCount == 6) {
                    this.resetearLineasDeControl();
                    setValorActualEnBinarioRAM(this.convertirDecimalABinarioManual(Long.parseLong(ram.getValoresPorDireccion().get(this.getValorActual()))));
                    this.setLineaControlEjecutando("RAM");
                }
                if (this.stepCount == 7) {
                    this.resetearLineasDeControl();
                    setValorActualEnBinarioAC(this.convertirDecimalABinarioManual(Long.parseLong(ram.getValoresPorDireccion().get(this.getValorActual()))));
                    this.setLineaControlEjecutando("AC");
                }
                if (this.stepCount == 8) {
                    this.resetearLineasDeControl();
                    setValorActualEnBinarioRAM("00000000");
                    this.setLineaControlEjecutando("RAM");
                }
            }
            if (getInstruccionActual().equals("ADD") ) {
                if(getSaltoJMP()){
                    this.stepCount = 3;
                    contador = 2;
                    setSaltoJMP(false);
                    yaEjecuto = true;
                }
                
                if (this.stepCount == 3) {
                    this.resetearLineasDeControl();
                    setContadorPrograma(this.convertirDecimalABinarioManual(contador));
                        this.setLineaControlEjecutando("PC");
                    lineasControl= new HashMap<Integer,Boolean>();     
                    lineasControl.put(IO, true);
                    lineasControl.put(MI, true);
                    this.setLineasControl(lineasControl);
                }
                if (this.stepCount == 4) {
                    this.resetearLineasDeControl();
                    lineasControl= new HashMap<Integer,Boolean>();     
                    lineasControl.put(RO, true);
                    lineasControl.put(BI, true);
                    this.setLineasControl(lineasControl);
                }
                if (this.stepCount == 5) {
                    this.resetearLineasDeControl();
                    lineasControl= new HashMap<Integer,Boolean>();     
                    lineasControl.put(AI, true);
                    lineasControl.put(SO, true);
                    lineasControl.put(FI, true);
                    this.setLineasControl(lineasControl);
                }
                if (this.stepCount == 6) {
                    this.resetearLineasDeControl();
                    setValorActualEnBinarioRAM(this.convertirDecimalABinarioManual(Long.parseLong((this.getValorActual()))));
                    this.setLineaControlEjecutando("RAM");
                }
                if (this.stepCount == 7) {
                    this.resetearLineasDeControl();
                    setValorActualEnBinarioIR(String.valueOf(this.convertirDecimalABinarioManual(Long.parseLong(this.getValorActual()))));
                    this.setLineaControlEjecutando("IR");
                }
                if (this.stepCount == 8) {
                    this.resetearLineasDeControl();
                    setValorActualEnBinarioMAR(this.convertirDecimalABinarioManual(Long.parseLong(this.getValorActual())));
                    this.setLineaControlEjecutando("MAR");
                }
                if (this.stepCount == 9) {
                    this.resetearLineasDeControl();
                    setValorActualEnBinarioRAM(this.convertirDecimalABinarioManual(Long.parseLong(ram.getValoresPorDireccion().get(this.getValorActual()))));
                    this.setLineaControlEjecutando("RAM");
                }
                if (this.stepCount == 10) {
                    this.resetearLineasDeControl();
                    setValorActualEnBinarioB(getValorActualEnBinarioRAM());
                    this.setLineaControlEjecutando("B");
                }                        
                if (this.stepCount == 11) {
                    this.resetearLineasDeControl();
                    long resultado;
                    long b =convertirBinarioADecimalManual(getValorActualEnBinarioB());
                    long acumulador =convertirBinarioADecimalManual(getValorActualEnBinarioAC());
                    resultado=b+acumulador;
                    setValorActualAcarreoSuma(String.valueOf(this.acarreos(String.valueOf(b),String.valueOf(acumulador))));
                    setValorActualEnBinarioALU(String.valueOf(resultado));
                    this.setLineaControlEjecutando("ALU");
                }                        
                if (this.stepCount == 12) {
                    this.resetearLineasDeControl();
                    setValorActualEnBinarioAC(this.convertirDecimalABinarioManual(Long.parseLong(getValorActualEnBinarioALU())));
                    this.setLineaControlEjecutando("AC");
                }
            }
            if (getInstruccionActual().equals("SUB") ) {
                if(getSaltoJMP()){
                    this.stepCount = 3;
                    contador = 2;
                    setSaltoJMP(false);
                    yaEjecuto = true;
                }
                
                if (this.stepCount == 3) {
                    this.resetearLineasDeControl();
                    setContadorPrograma(this.convertirDecimalABinarioManual(contador));
                        this.setLineaControlEjecutando("PC");
                    lineasControl= new HashMap<Integer,Boolean>();     
                    lineasControl.put(IO, true);
                    lineasControl.put(MI, true);
                    this.setLineasControl(lineasControl);
                }
                if (this.stepCount == 4) {
                    this.resetearLineasDeControl();
                    lineasControl= new HashMap<Integer,Boolean>();     
                    lineasControl.put(RO, true);
                    lineasControl.put(BI, true);
                    this.setLineasControl(lineasControl);
                }
                if (this.stepCount == 5) {
                    this.resetearLineasDeControl();
                    lineasControl= new HashMap<Integer,Boolean>();     
                    lineasControl.put(SU, true);
                    lineasControl.put(SO, true);
                    lineasControl.put(FI, true);
                    lineasControl.put(AI, true);
                    this.setLineasControl(lineasControl);
                }
                if (this.stepCount == 6) {
                    this.resetearLineasDeControl();
                    setValorActualEnBinarioRAM(this.convertirDecimalABinarioManual(Long.parseLong((this.getValorActual()))));
                    this.setLineaControlEjecutando("RAM");
                }
                if (this.stepCount == 7) {
                    this.resetearLineasDeControl();
                    setValorActualEnBinarioIR(String.valueOf(this.convertirDecimalABinarioManual(Long.parseLong(this.getValorActual()))));
                    this.setLineaControlEjecutando("IR");
                }
                if (this.stepCount == 8) {
                    this.resetearLineasDeControl();
                    setValorActualEnBinarioMAR(this.convertirDecimalABinarioManual(Long.parseLong(this.getValorActual())));
                    this.setLineaControlEjecutando("MAR");
                }
                if (this.stepCount == 9) {
                    this.resetearLineasDeControl();
                    setValorActualEnBinarioRAM(this.convertirDecimalABinarioManual(Long.parseLong(ram.getValoresPorDireccion().get(this.getValorActual()))));
                    this.setLineaControlEjecutando("RAM");
                }
                if (this.stepCount == 10) {
                    this.resetearLineasDeControl();
                    setValorActualEnBinarioB(getValorActualEnBinarioRAM());
                    this.setLineaControlEjecutando("B");
                }                        
                if (this.stepCount == 11) {
                    this.resetearLineasDeControl();
                    long resultado;
                    long b =convertirBinarioADecimalManual(getValorActualEnBinarioB());
                    long acumulador =convertirBinarioADecimalManual(getValorActualEnBinarioAC());
                    resultado=acumulador-b;
                    setValorActualEnBinarioALU(String.valueOf(resultado));
                    this.setLineaControlEjecutando("ALU");
                }                        
                if (this.stepCount == 12) {
                    this.resetearLineasDeControl();
                    setValorActualEnBinarioAC(this.convertirDecimalABinarioManual(Long.parseLong(getValorActualEnBinarioALU())));
                    this.setLineaControlEjecutando("AC");
                }
            }
                    
            if (getInstruccionActual().equals("STA")) {
                if(getSaltoJMP()){
                    this.stepCount = 3;
                    contador = 2;
                    setSaltoJMP(false);
                    yaEjecuto = true;
                }
                
                if (this.stepCount == 3) {
                    this.resetearLineasDeControl();
                    setContadorPrograma(this.convertirDecimalABinarioManual(contador));
                        this.setLineaControlEjecutando("PC");
                    lineasControl= new HashMap<Integer,Boolean>();     
                    lineasControl.put(IO, true);
                    lineasControl.put(MI, true);
                    this.setLineasControl(lineasControl);
                }
                if (this.stepCount == 4) {
                    this.resetearLineasDeControl();
                    lineasControl= new HashMap<Integer,Boolean>();     
                    lineasControl.put(RI, true);
                    lineasControl.put(AO, true);
                    this.setLineasControl(lineasControl);
                }
                if (this.stepCount == 5) {
                    this.resetearLineasDeControl();
                    setValorActualEnBinarioRAM(this.convertirDecimalABinarioManual(Long.parseLong((this.getValorActual()))));
                    this.setLineaControlEjecutando("RAM");
                }
                if (this.stepCount == 6) {
                    this.resetearLineasDeControl();
                    setValorActualEnBinarioIR(String.valueOf(this.convertirDecimalABinarioManual(Long.parseLong(this.getValorActual()))));
                    this.setLineaControlEjecutando("IR");
                }
                if (this.stepCount == 7) {
                    this.resetearLineasDeControl();
                    setValorActualEnBinarioMAR(this.convertirDecimalABinarioManual(Long.parseLong(this.getValorActual())));
                    this.setLineaControlEjecutando("MAR");
                }
                if (this.stepCount == 8) {
                    this.resetearLineasDeControl();
                    setValorActualEnBinarioRAM(this.convertirDecimalABinarioManual(Long.parseLong(ram.getValoresPorDireccion().get(this.getValorActual()))));
                    this.setLineaControlEjecutando("RAM");
                }
                if (this.stepCount == 9) {
                    this.resetearLineasDeControl();
                    setValorActualEnBinarioB("0");
                    this.setLineaControlEjecutando("B");
                }                        
                if (this.stepCount == 10) {
                    this.resetearLineasDeControl();
                    setValorActualEnBinarioSTA(String.valueOf(convertirBinarioADecimalManual(getValorActualEnBinarioAC())));
                    this.setLineaControlEjecutando("STA");
                }
            }
                    
            if (getInstruccionActual().equals("LDI")) {
                if (this.stepCount == 3) {
                    this.resetearLineasDeControl();
                    setContadorPrograma(this.convertirDecimalABinarioManual(contador));
                        this.setLineaControlEjecutando("PC");
                    lineasControl= new HashMap<Integer,Boolean>();     
                    lineasControl.put(RO, true);
                    lineasControl.put(AI, true);
                    this.setLineasControl(lineasControl);
                }
                if (this.stepCount == 4) {
                    this.resetearLineasDeControl();

                }
                if (this.stepCount == 5) {
                    this.resetearLineasDeControl();
                }
                if (this.stepCount == 6) {
                    this.resetearLineasDeControl();
                    setValorActualEnBinarioRAM(this.convertirDecimalABinarioManual(Long.parseLong((this.getValorActual()))));
                    this.setLineaControlEjecutando("RAM");
                }
                if (this.stepCount == 7) {
                    this.resetearLineasDeControl();
                    setValorActualEnBinarioIR(String.valueOf(this.convertirDecimalABinarioManual(Long.parseLong(this.getValorActual()))));
                    this.setLineaControlEjecutando("IR");
                }
                if (this.stepCount == 8) {
                    this.resetearLineasDeControl();
                    setValorActualEnBinarioMAR(this.convertirDecimalABinarioManual(Long.parseLong(this.getValorActual())));
                    this.setLineaControlEjecutando("MAR");
                }
                if (this.stepCount == 9) {
                    this.resetearLineasDeControl();
                    setValorActualEnBinarioRAM(this.convertirDecimalABinarioManual(Long.parseLong(ram.getValoresPorDireccion().get(this.getValorActual()))));
                    this.setLineaControlEjecutando("RAM");
                }
                if (this.stepCount == 10) {
                    this.resetearLineasDeControl();
                    setValorActualEnBinarioB(getValorActualEnBinarioRAM());
                    this.setLineaControlEjecutando("B");
                }                        

                if (this.stepCount == 11) {
                    this.resetearLineasDeControl();
                    setValorActualEnBinarioAC(this.convertirDecimalABinarioManual(Long.parseLong(ram.getValoresPorDireccion().get(this.getValorActual()))));
                    this.setLineaControlEjecutando("AC");
                }
            }

            if (getInstruccionActual() .equals("JC")) {
                if(Long.getLong(getValorActualAcarreoSuma())>0){
                    if (this.stepCount == 3) {
                        this.resetearLineasDeControl();
                        setContadorPrograma(this.convertirDecimalABinarioManual(contador));
                        this.setLineaControlEjecutando("PC");
                    }

                    if (this.stepCount == 4) {
                        this.resetearLineasDeControl();
                        lineasControl= new HashMap<Integer,Boolean>();     
                        lineasControl.put(IO, true);
                        lineasControl.put(J, true);
                        this.setLineasControl(lineasControl);
                    }

                    if (this.stepCount == 5) {
                        this.resetearLineasDeControl();
                    }

                    if (this.stepCount == 6) {
                        this.resetearLineasDeControl();
                    }
                } 
            }
            if (getInstruccionActual() .equals("JZ")) {
                    this.setLineaControlEjecutando("JZ");
                if(Long.getLong(getValorActualAcarreoSuma())==0){
                    if (this.stepCount == 3) {
                        this.resetearLineasDeControl();
                        setContadorPrograma(this.convertirDecimalABinarioManual(contador));
                        this.setLineaControlEjecutando("PC");
                    }

                    if (this.stepCount == 4) {
                        this.resetearLineasDeControl();
                        lineasControl= new HashMap<Integer,Boolean>();     
                        lineasControl.put(IO, true);
                        lineasControl.put(J, true);
                        this.setLineasControl(lineasControl);
                    }

                    if (this.stepCount == 5) {
                        this.resetearLineasDeControl();
                    }

                    if (this.stepCount == 6) {
                        this.resetearLineasDeControl();
                    }
                } 
            }
            if (getInstruccionActual() .equals("OUT")) {
                    if (this.stepCount == 3) {
                        this.resetearLineasDeControl();
                        setContadorPrograma(this.convertirDecimalABinarioManual(contador));
                        this.setLineaControlEjecutando("PC");
                        lineasControl= new HashMap<Integer,Boolean>();     
                        lineasControl.put(AO, true);
                        lineasControl.put(OI, true);
                        this.setLineasControl(lineasControl);
                    }
                    if (this.stepCount == 4) {
                        this.resetearLineasDeControl();
                        this.setLineaControlEjecutando("OUT");
                    }
                    if (this.stepCount == 5) {
                        this.resetearLineasDeControl();
                        setResultadoOUT((this.getValorActualEnBinarioAC()));
                        setResultadoRTA(String.valueOf(convertirBinarioADecimalManual(this.getValorActualEnBinarioAC())));
                        this.setLineaControlEjecutando("RTA");
                    }
            }
            if (getInstruccionActual() .equals("HLT")) {
                    if (this.stepCount == 3) {
                        this.resetearLineasDeControl();
                        setContadorPrograma(this.convertirDecimalABinarioManual(contador));
                        this.setLineaControlEjecutando("PC");
                        lineasControl= new HashMap<Integer,Boolean>();     
                        lineasControl.put(HLT, true);
                        this.setLineasControl(lineasControl);
                    }
                    if (this.stepCount == 4) {
                        this.resetearLineasDeControl();
                    }
                    if (this.stepCount == 5) {
                        this.resetearLineasDeControl();
                        setBloquear(false);
                    }
            }
                
            }


    }
    

    
    
    public void inicializar(){
        lineasControl = new HashMap<Integer,Boolean>(); 
        this.setYaEntro(new HashMap<String,String>());
        this.setValorActual("0");
        this.setValorActualEnBinarioRAM("0");
        this.setValorActualEnBinarioIR("0");
        this.setValorActualEnBinarioMAR("0");
        this.setValorActualEnBinarioAC("0");
        this.setValorActualEnBinarioB("0");
        this.setValorActualEnBinarioALU("0");
        this.setValorActualEnBinarioSTA("0");
        this.setValorActualAcarreoSuma("0");
        this.setValorActualAcarreoResta("0");
        this.setResultadoOUT("0");
        this.setResultadoRTA("0");
        this.setInstruccionActual("0");
        this.setContadorPrograma("0");
        this.setSaltoJMP(false);
        this.yaEjecuto= false;
        this.contador=1;
        this.siguientePosicion =0;
        this.setEjecutar(false);
        this.setLineaControlEjecutando("");
        this.setStepCount(0);
        this.setBloquear(true);
        
    }
            
    private void resetearLineasDeControl() {
        setResetearLineas(true);
        Reloj.getReloj().setIsHalted(false);
    }


    /**
     * @return the lineasControl
     */
    public HashMap <Integer,Boolean> getLineasControl() {
        return lineasControl;
    }

    /**
     * @param lineasControl the lineasControl to set
     */
    public void setLineasControl(HashMap <Integer,Boolean> lineasControl) {
        this.lineasControl = lineasControl;
    }

    /**
     * @return the stepCount
     */
    public int getStepCount() {
        return stepCount;
    }

    /**
     * @param stepCount the stepCount to set
     */
    public void setStepCount(int stepCount) {
        this.stepCount = stepCount;
    }

    /**
     * @return the resetearLineas
     */
    public Boolean getResetearLineas() {
        return resetearLineas;
    }

    /**
     * @param resetearLineas the resetearLineas to set
     */
    public void setResetearLineas(Boolean resetearLineas) {
        this.resetearLineas = resetearLineas;
    }

    
    /**
     * @return the valorActual
     */
    public String getValorActual() {
        return valorActual;
    }

    /**
     * @param valorActual the valorActual to set
     */
    public void setValorActual(String valorActual) {
        this.valorActual = valorActual;
    }

    /**
     * @return the instruccionActual
     */
    public String getInstruccionActual() {
        return instruccionActual;
    }

    /**
     * @param instruccionActual the instruccionActual to set
     */
    public void setInstruccionActual(String instruccionActual) {
        this.instruccionActual = instruccionActual;
    }

    /**
     * @return the contadorPrograma
     */
    public String getContadorPrograma() {
        return contadorPrograma;
    }

    /**
     * @return the valorActualEnBinarioMAR
     */
    public String getValorActualEnBinarioMAR() {
        return valorActualEnBinarioMAR;
    }

    /**
     * @param valorActualEnBinarioMAR the valorActualEnBinarioMAR to set
     */
    public void setValorActualEnBinarioMAR(String valorActualEnBinarioMAR) {
        this.valorActualEnBinarioMAR = valorActualEnBinarioMAR;
    }

    
    public String convertirDecimalABinarioManual(long decimal) {
	if (decimal <= 0) {
		return "0";
	}
	StringBuilder binario = new StringBuilder();
	while (decimal > 0) {
            
		short residuo = (short) (decimal % 2);
		decimal = decimal / 2;
		binario.insert(0, String.valueOf(residuo));
	}
	return binario.toString();
    }

    /**
     * @return the valorActualEnBinario
     */
    public String getValorActualEnBinarioRAM() {
        return valorActualEnBinarioRAM;
    }

    /**
     * @param valorActualEnBinarioRAM the valorActualEnBinario to set
     */
    public void setValorActualEnBinarioRAM(String valorActualEnBinarioRAM) {
        this.valorActualEnBinarioRAM = valorActualEnBinarioRAM;
    }

    /**
     * @return the valorActualEnBinarioIR
     */
    public String getValorActualEnBinarioIR() {
        return valorActualEnBinarioIR;
    }

    /**
     * @param valorActualEnBinarioIR the valorActualEnBinarioIR to set
     */
    public void setValorActualEnBinarioIR(String valorActualEnBinarioIR) {
        this.valorActualEnBinarioIR = valorActualEnBinarioIR;
    }

    /**
     * @return the valorActualEnBinarioAC
     */
    public String getValorActualEnBinarioAC() {
        
        return valorActualEnBinarioAC;
    }

    /**
     * @param valorActualEnBinarioAC the valorActualEnBinarioAC to set
     */
    public void setValorActualEnBinarioAC(String valorActualEnBinarioAC) {
        String cerosIzq ="0";            
        while(valorActualEnBinarioAC.length() !=8){
            valorActualEnBinarioAC = (cerosIzq+valorActualEnBinarioAC);
        }
        this.valorActualEnBinarioAC = valorActualEnBinarioAC;
    }

    /**
     * @return the bloquear
     */
    public Boolean getBloquear() {
        return bloquear;
    }

    /**
     * @param bloquear the bloquear to set
     */
    public void setBloquear(Boolean bloquear) {
        this.bloquear = bloquear;
    }

    /**
     * @return the valorActualEnBinarioB
     */
    public String getValorActualEnBinarioB() {
        return valorActualEnBinarioB;
    }

    /**
     * @param valorActualEnBinarioB the valorActualEnBinarioB to set
     */
    public void setValorActualEnBinarioB(String valorActualEnBinarioB) {
        this.valorActualEnBinarioB = valorActualEnBinarioB;
    }

    /**
     * @return the valorActualEnBinarioALU
     */
    public String getValorActualEnBinarioALU() {
        return valorActualEnBinarioALU;
    }

    /**
     * @param valorActualEnBinarioALU the valorActualEnBinarioALU to set
     */
    public void setValorActualEnBinarioALU(String valorActualEnBinarioALU) {
        this.valorActualEnBinarioALU = valorActualEnBinarioALU;
    }

    public static long convertirBinarioADecimalManual(String binario) {
        // A este número le vamos a sumar cada valor binario
        long decimal = 0;
        int posicion = 0;
        // Recorrer la cadena...
        for (int x = binario.length() - 1; x >= 0; x--) {
            // Saber si es 1 o 0; primero asumimos que es 1 y abajo comprobamos
            short digito = 1;
            if (binario.charAt(x) == '0') {
                digito = 0;
            }

            double multiplicador = Math.pow(2, posicion);
            decimal += digito * multiplicador;
            posicion++;
        }
        return decimal;
      }

    /**
     * @return the valorActualEnBinarioSTA
     */
    public String getValorActualEnBinarioSTA() {
        return valorActualEnBinarioSTA;
    }

    /**
     * @param valorActualEnBinarioSTA the valorActualEnBinarioSTA to set
     */
    public void setValorActualEnBinarioSTA(String valorActualEnBinarioSTA) {
        this.valorActualEnBinarioSTA = valorActualEnBinarioSTA;
    }
    
    public static int acarreos(String s1, String s2)
    {
        int cuenta=0; //cuenta de acarreos
        int acarreo=0; //acarreo actual
        int i=0; //posición por la izquierda
        boolean terminado=false; //bandera para controlar el bucle
        while (!terminado)
        {
            //Coger el siguiente dígito de cada número por la izquierda. 
            //Si he superado la longitud, el dígito en el que estoy es 0
            int i1=i<s1.length()?s1.charAt(s1.length()-i-1)-'0':0; 
            int i2=i<s2.length()?s2.charAt(s2.length()-i-1)-'0':0;
            int suma=acarreo+i1+i2; //sumamos ambos mas el posible acarreo anterior
            acarreo=suma>=10?1:0; //Hay acarreo si la suma es es > 10
            cuenta+=acarreo;
            i++; //pasar al siguiente por la izquierda
            //terminamos si hemos sobrepasado la longitud de alguna de las dos cifras y no hay acarreos
            terminado=!(acarreo>0) && (i>=s1.length() || i>s2.length());
        }
        return cuenta;
    }

    /**
     * @return the valorActualAcarreoSuma
     */
    public String getValorActualAcarreoSuma() {
        return valorActualAcarreoSuma;
    }

    /**
     * @param valorActualAcarreoSuma the valorActualAcarreoSuma to set
     */
    public void setValorActualAcarreoSuma(String valorActualAcarreoSuma) {
        this.valorActualAcarreoSuma = valorActualAcarreoSuma;
    }

    /**
     * @return the valorActualAcarreoResta
     */
    public String getValorActualAcarreoResta() {
        return valorActualAcarreoResta;
    }

    /**
     * @param valorActualAcarreoResta the valorActualAcarreoResta to set
     */
    public void setValorActualAcarreoResta(String valorActualAcarreoResta) {
        this.valorActualAcarreoResta = valorActualAcarreoResta;
    }

    /**
     * @return the resultadoOUT
     */
    public String getResultadoOUT() {
        return resultadoOUT;
    }

    /**
     * @param resultadoOUT the resultadoOUT to set
     */
    public void setResultadoOUT(String resultadoOUT) {
        this.resultadoOUT = resultadoOUT;
    }

    /**
     * @return the resultadoRTA
     */
    public String getResultadoRTA() {
        return resultadoRTA;
    }

    /**
     * @param resultadoRTA the resultadoRTA to set
     */
    public void setResultadoRTA(String resultadoRTA) {
        this.resultadoRTA = resultadoRTA;
    }

    /**
     * @return the ejecutar
     */
    public boolean isEjecutar() {
        return ejecutar;
    }

    /**
     * @param ejecutar the ejecutar to set
     */
    public void setEjecutar(boolean ejecutar) {
        this.ejecutar = ejecutar;
    }

    /**
     * @return the yaEntro
     */
    public HashMap<String,String> getYaEntro() {
        return yaEntro;
    }

    /**
     * @param yaEntro the yaEntro to set
     */
    public void setYaEntro(HashMap<String,String> yaEntro) {
        this.yaEntro = yaEntro;
    }

    /**
     * @return the saltoJMP
     */
    public Boolean getSaltoJMP() {
        return saltoJMP;
    }

    /**
     * @param saltoJMP the saltoJMP to set
     */
    public void setSaltoJMP(Boolean saltoJMP) {
        this.saltoJMP = saltoJMP;
    }

    /**
     * @return the lineaControlEjecutando
     */
    public String getLineaControlEjecutando() {
        return lineaControlEjecutando;
    }

    /**
     * @param lineaControlEjecutando the lineaControlEjecutando to set
     */
    public void setLineaControlEjecutando(String lineaControlEjecutando) {
        this.lineaControlEjecutando = lineaControlEjecutando;
    }
}
