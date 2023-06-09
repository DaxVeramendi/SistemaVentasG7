
package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VendedorDAO {
    PreparedStatement ps;
    ResultSet rs;    
    
    Conexion acceso=new Conexion();
    Connection con;
    
    
    //Code Smell: Long Method; Técnica de Refactorización: Extract Method
    //A "ValidarVendedor" se le extrajo 1 método siendo este: "obtenerDatosVendedor"
    public EntidadVendedor ValidarVendedor(String dni, String user) {
        EntidadVendedor ev = new EntidadVendedor();
        String sql = "select * from vendedor where Dni=? and User=?";

        try {
            con = acceso.Conectar();
            ps = con.prepareStatement(sql);
            ps.setString(1, dni);
            ps.setString(2, user);
            rs = ps.executeQuery();
            ev = obtenerDatosVendedor(rs);
        } catch (Exception e) {
        }

        return ev;
    }
    //Método extraído desde "ValidarVendedor"
    private EntidadVendedor obtenerDatosVendedor(ResultSet rs) throws SQLException {
        EntidadVendedor ev = new EntidadVendedor();

        while (rs.next()) {
            ev.setId(rs.getInt(1));
            ev.setDni(rs.getString(2));
            ev.setNom(rs.getString(3));
            ev.setTel(rs.getString(4));
            ev.setEstado(rs.getString(5));
            ev.setUser(rs.getString(6));
        }

        return ev;
    }
    
    //Code Smell:concatenación de cadenas en una consulta SQL
     public Vendedor listarVendedorId(String dni) {
        Vendedor v=new Vendedor();
        String sql = "select * from vendedor where Dni=?";
        try {
            con = acceso.Conectar();
            ps = con.prepareStatement(sql);
            ps.setString(1, dni);
            rs = ps.executeQuery();
            while (rs.next()) {
                v.setId(rs.getInt(1));
                v.setDni(rs.getString(2));
                v.setNom(rs.getString(3));
                v.setTel(rs.getString(4));
                v.setEstado(rs.getString(5));
                v.setUser(rs.getString(6));
            }
            
           //Code Smell:Uso incorrecto de excepciones genéricas
        } catch (Exception e) {
        }
        return v;
    }
    //********CRUD - Principal**************

    public List listarVendedor() {
        String sql = "select * from vendedor";
        List<Vendedor> listaVendedor = new ArrayList<>();
        try {
            con = acceso.Conectar();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Vendedor ven = new Vendedor();
                ven.setId(rs.getInt(1));
                ven.setDni(rs.getString(2));
                ven.setNom(rs.getString(3));
                ven.setTel(rs.getString(4));
                ven.setEstado(rs.getString(5));
                ven.setUser(rs.getString(6));
                listaVendedor.add(ven);
            }
        } catch (Exception e) {
        }
        return listaVendedor;
    }

    public int agregar(Vendedor v) {
        int r=0;
        String sql = "insert into vendedor(Dni,Nombres,Telefono,Estado,User)values(?,?,?,?,?)";
        try {
            con = acceso.Conectar();
            ps = con.prepareStatement(sql);
            ps.setString(1, v.getDni());
            ps.setString(2, v.getNom());
            ps.setString(3, v.getTel());
            ps.setString(4, v.getEstado());
            ps.setString(5, v.getUser());
            ps.executeUpdate();
        } catch (Exception e) {
        }
        return r;
    }

    public int actualizar(Vendedor v) {
        int r=0;
        String sql = "update vendedor set Dni=?, Nombres=?,Telefono=?,Estado=? Where IdVendedor=?";
        try {
            con = acceso.Conectar();
            ps = con.prepareStatement(sql);
            ps.setString(1, v.getDni());
            ps.setString(2, v.getNom());
            ps.setString(3, v.getTel());
            ps.setString(4, v.getEstado());
            ps.setInt(5, v.getId());
            r = ps.executeUpdate();
            if (r == 1) {
                r = 1;
            } else {
                r = 0;
            }
        } catch (Exception e) {
            System.err.println("" + e);
        }
        return r;
    }

    public int delete(int id) {
        int r=0;
        String sql = "delete from vendedor where IdVendedor=?";
        try {
            con = acceso.Conectar();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
        }
        return r;
    }
}
