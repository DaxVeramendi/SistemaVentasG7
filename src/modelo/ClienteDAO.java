package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO implements CRUD {

    Connection con;
    Conexion cn = new Conexion();
    PreparedStatement ps;
    ResultSet rs;
    Cliente co=new Cliente();
    
    //Code Smell: Long Method; Técnica de Refactorización: Extract Method
    //A "listarID" se le extrajo 1 método siendo este: "obtenerDatosCliente"
    public Cliente listarID(String dni) {
    Cliente c = new Cliente();
    String sql = "select * from cliente where Dni=?";

    try {
        con = cn.Conectar();
        ps = con.prepareStatement(sql);
        ps.setString(1, dni);
        rs = ps.executeQuery();
        c = obtenerDatosCliente(rs);
    } catch (Exception e) {
        // Manejo de excepciones
    }

    return c;
}
    //Método extraído desde "listarID"
    private Cliente obtenerDatosCliente(ResultSet rs) throws SQLException {
    Cliente c = new Cliente();

    while (rs.next()) {
        c.setId(rs.getInt(1));
        c.setDni(rs.getString(2));
        c.setNom(rs.getString(3));
        c.setDir(rs.getString(4));
        c.setEstado(rs.getString(5));
    }

    return c;
}
    
    //Metodos de Mantenimiento CRUD
    @Override
    public List listar() {
        List<Cliente> lista = new ArrayList<>();
        String sql = "select * from cliente";
        try {
            con = cn.Conectar();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Cliente c = new Cliente();
                c.setId(rs.getInt(1));
                c.setDni(rs.getString(2));
                c.setNom(rs.getString(3));
                c.setDir(rs.getString(4));
                c.setEstado(rs.getString(5));
                lista.add(c);
            }
        } catch (Exception e) {
        }
        return lista;
    }

    @Override
    public int add(Cliente cliente) {
    int r = 0;
    String sql = "INSERT INTO cliente(Dni, Nombres, Direccion, Estado) VALUES (?, ?, ?, ?)";
    try (Connection con = cn.Conectar();
         PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setString(1, cliente.getDni());
        ps.setString(2, cliente.getNom());
        ps.setString(3, cliente.getDir());
        ps.setString(4, cliente.getEstado());
        r = ps.executeUpdate();
    } catch (Exception e) {
    }
    return r;
}

public int actualizar(Cliente cliente) {
    int r = 0;
    String sql = "UPDATE cliente SET Dni=?, Nombres=?, Direccion=?, Estado=? WHERE IdCliente=?";
    try (Connection con = cn.Conectar();
         PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setString(1, cliente.getDni());
        ps.setString(2, cliente.getNom());
        ps.setString(3, cliente.getDir());
        ps.setString(4, cliente.getEstado());
        ps.setInt(5, cliente.getId());
        r = ps.executeUpdate();
    } catch (Exception e) {
    }
    return r;
}


    @Override
    public void eliminar(int id) {
        String sql="delete from cliente where IdCliente=?";
        try {
            con=cn.Conectar();
            ps=con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
        }
    }

}
