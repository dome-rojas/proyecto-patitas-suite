package edu.unl.cc.patitas_suite.controladores;

import edu.unl.cc.patitas_suite.negocios.FachadaDeEmpleado;
import edu.unl.cc.patitas_suite.negocios.FachadaDeHabitacion;
import edu.unl.cc.patitas_suite.negocios.FachadaDeMascota;
import jakarta.inject.Inject;

import java.io.Serializable;

public class DashBoardAdminBean implements Serializable {
    @Inject
    private FachadaDeMascota fachadaDeMascota;
    @Inject
    private FachadaDeEmpleado empleadoService;
    @Inject
    private FachadaDeHabitacion habitacionService;
}
