///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package Controlador;
//
//import Modelo.RAM;
//import java.util.HashMap;
//import java.util.Map;
//import org.junit.After;
//import org.junit.AfterClass;
//import static org.junit.Assert.*;
//import org.junit.Before;
//import org.junit.BeforeClass;
//import org.junit.Test;
//
///**
// *
// * @author Anderson
// */
//public class RAMFacadeTest {
//    
//    private Fachada rAMFacade;
//    
//    public RAMFacadeTest() {
//    }
//    
//    @BeforeClass
//    public static void setUpClass() {
//        
//    }
//    
//    @AfterClass
//    public static void tearDownClass() {
//    }
//    
//    @Before
//    public void setUp() {
//        rAMFacade = new Fachada();
//    }
//    
//    @After
//    public void tearDown() {
//    }
//
//    // TODO add test methods here.
//    // The methods must be annotated with annotation @Test. For example:
//    //
//    @Test
//    public void crearFirmaDarRAM() {
//        RAM memoriaRAM = rAMFacade.darRAM();  
//    }
//    
//    @Test
//    public void teniendoComputadorCuandoDarRAMEntoncesNoNull() {
//        RAM memoriaRAM = rAMFacade.darRAM();  
//        
//        assertNotNull(memoriaRAM);
//    }
//    
//    @Test
//    public void teniendoRAMCuandoDarRAMEntoncesTodasLasDireccionesVacias() {        
//        Map<String,String> direccionesRAM = new HashMap<String,String>();
//        for(int i = 0;i<16;i++){
//            direccionesRAM.put(String.valueOf(i), "00000000");
//        }
//        
//        RAM memoriaRAM = rAMFacade.darRAM();  
//        
//        assertNotNull(memoriaRAM);
//        assertEquals(null,direccionesRAM,memoriaRAM.darDirecciones());
//    }
//}
