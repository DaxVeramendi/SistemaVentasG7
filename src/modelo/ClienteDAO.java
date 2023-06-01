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
    public int add(Object[] o) {  
        int r=0;
        String sql = "insert into cliente(Dni,Nombres,Direccion,Estado)values(?,?,?,?)";
        try {
            con=cn.Conectar();
            ps=con.prepareStatement(sql);
            ps.setObject(1, o[0]);
            ps.setObject(2, o[1]);
            ps.setObject(3, o[2]);
            ps.setObject(4, o[3]);
            r=ps.executeUpdate();
        } catch (Exception e) {
        }
        return r;
    }

    @Override
    public int actualizar(Object[] o) {
        int r=0;
       String sql="update cliente set Dni=?,Nombres=?,Direccion=?,Estado=? where IdCliente=?";
        try {
            con=cn.Conectar();
            ps=con.prepareStatement(sql);
            ps.setObject(1, o[0]);
            ps.setObject(2, o[1]);
            ps.setObject(3, o[2]);
            ps.setObject(4, o[3]);
            ps.setObject(5, o[4]);
            r=ps.executeUpdate();
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
