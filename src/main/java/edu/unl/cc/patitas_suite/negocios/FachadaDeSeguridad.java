package edu.unl.cc.patitas_suite.negocios;

import edu.unl.cc.patitas_suite.dominio.comun.Usuario;
import edu.unl.cc.patitas_suite.dominio.seguridad.Rol;
import edu.unl.cc.patitas_suite.excepciones.CredentialInvalidException;
import edu.unl.cc.patitas_suite.excepciones.EncryptorException;
import edu.unl.cc.patitas_suite.excepciones.EntityNotFoundException;
import edu.unl.cc.patitas_suite.negocios.servicios.RepositorioDeRoles;
import edu.unl.cc.patitas_suite.negocios.servicios.RepositorioDeUsuarios;
import edu.unl.cc.patitas_suite.utiles.GestorDeCifrado;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Stateless
public class FachadaDeSeguridad implements Serializable {

    @Inject
    private RepositorioDeUsuarios repositorioDeUsuarios;

    @Inject
    private RepositorioDeRoles repositorioDeRoles;

    public Rol createRol(Rol rol)  {
            repositorioDeRoles.find(rol.getNombre());
            Rol rolPersisted = repositorioDeRoles.save(rol);
            return rolPersisted;
    }
    public Usuario updateContrasena(Long usuarioId, String nuevaClave) throws Exception {
        Usuario usuarioBD = repositorioDeUsuarios.find(usuarioId); // 1. Traes desde BD

        if (usuarioBD == null) {
            throw new Exception("Usuario no encontrado");
        }

        String pwdEncrypted = GestorDeCifrado.encrypt(nuevaClave);
        usuarioBD.setClave(pwdEncrypted);
        usuarioBD.setPrimerIngreso(false);

        return repositorioDeUsuarios.save(usuarioBD); // 2. Solo guarda lo necesario
    }
    public Usuario create(Usuario usuario) throws Exception {
        String pwdEncrypted = GestorDeCifrado.encrypt(usuario.getClave());
        usuario.setClave(pwdEncrypted);
        try {
            repositorioDeUsuarios.find(usuario.getNombre());
        } catch (EntityNotFoundException e){
            Usuario userPersisted = repositorioDeUsuarios.save(usuario);
            return userPersisted;
        }
        throw new Exception("Ya existe un usuario con ese nombre");
    }

    public Usuario update(Usuario usuario) throws Exception {
        if (usuario.getId() == null){
            return create(usuario);
        }
        String pwdEncrypted = GestorDeCifrado.encrypt(usuario.getClave());
        usuario.setClave(pwdEncrypted);
        try {
            Usuario userFound = repositorioDeUsuarios.find(usuario.getNombre());
            if  (!userFound.getId().equals(usuario.getId())){
                throw new Exception("Ya existe otro usuario con ese nombre");
            }
        } catch (EntityNotFoundException ignored) {
        }
        return repositorioDeUsuarios.save(usuario);
    }
    public List<Rol> findAllRoles() throws EntityNotFoundException {
        return  repositorioDeRoles.allRoles();
    }

    public Usuario find(Long id) throws EntityNotFoundException {
        return repositorioDeUsuarios.find(id);
    }
    public Rol findRolId(Long id) throws EntityNotFoundException {
        return repositorioDeRoles.findRolId(id);
    }

    public Usuario authenticate(String nombre, String clave) throws CredentialInvalidException, EncryptorException {
        try{
            Usuario userFound = repositorioDeUsuarios.find(nombre);
            String pwdEncrypted = GestorDeCifrado.encrypt(clave);
            System.out.println("---> OOOOO: " + pwdEncrypted + " " + userFound.getClave() + " " + pwdEncrypted.equals(userFound.getClave()));
            if (pwdEncrypted.equals(userFound.getClave())){
                return userFound;
            }
            throw new CredentialInvalidException("Credenciales inválidas para el usuario: " + nombre);
        } catch (EntityNotFoundException e){
            throw new CredentialInvalidException("Credenciales inválidas para el usuario: " + nombre);
        }
    }

    public List<Usuario> findUsers(String criterio) throws EntityNotFoundException {
        return repositorioDeUsuarios.findWithLike(criterio);
    }
    public List<Usuario> findUsers() throws EntityNotFoundException {
        return repositorioDeUsuarios.findAllUsuarios();
    }

    public Set<Rol> findAllRolesWithPermission()  {
        return repositorioDeRoles.findAllWithPermissions();
    }

    public Rol findRolesWithPermission(Long userId) throws EntityNotFoundException {
        Usuario usuario = repositorioDeUsuarios.find(userId);
        Rol r = repositorioDeRoles.find("ADMINISTRADOR");
        return r;
    }

}
