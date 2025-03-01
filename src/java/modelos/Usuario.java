package modelos;


public class Usuario {

    private String id;
    private String usuario;
    private String email;
    private String contraseña;
    private int rol;

    // Constructor
    public Usuario(String id, String usuario, String email, String contraseña, int rol) {
        this.id = id;
        this.usuario = usuario;
        this.email = email;
        this.contraseña = contraseña;
        this.rol = rol;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getEmail() {
        return email;
    }

    public String getContraseña() {
        return contraseña;
    }

    public int getRol() {
        return rol;
    }

    // Setters
    public void setId(String id) {
        this.id = id;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public void setRol(int rol) {
        this.rol = rol;
    }


}
