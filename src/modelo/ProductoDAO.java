package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {

    int r;
    PreparedStatement ps;
    ResultSet rs;
    Connection con;
    Conexion acceso = new Conexion();

    public int actualizarStock(int cant, int idp) throws SQLException {
        String sql = "update producto set Stock=? where id=?";
        try {
            con = acceso.Conectar();
            ps = con.prepareStatement(sql);
            ps.setInt(1, cant);
            ps.setInt(2, idp);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cerrarConexiones();
        }
        return r;
    }

    public Producto listarID(int id) {
        Producto p = new Producto();
        String sql = "select * from producto where id=?";
        try {
            con = acceso.Conectar();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                p = extraerProductoDeResultSet(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cerrarConexiones();
        }
        return p;
    }

    //*************MANTENIMIENTO CRUD**************
    public List listarProducto() {
        String sql = "select * from producto";
        List<Producto> listaprod = new ArrayList<>();
        try {
            con = acceso.Conectar();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Producto p = extraerProductoDeResultSet(rs);
                listaprod.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cerrarConexiones();
        }
        return listaprod;
    }

    public int agregar(Producto p) {
        String sql = "insert into producto(Nombres,Precio,Stock,Estado)values(?,?,?,?)";
        try {
            con = acceso.Conectar();
            ps = con.prepareStatement(sql);
            ps.setString(1, p.getNom());
            ps.setDouble(2, p.getPre());
            ps.setInt(3, p.getStock());
            ps.setString(4, p.getEstado());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cerrarConexiones();
        }
        return r;
    }

//    public int actualizarStock(int cant, int id) {
//        String sql = "update producto set Stock=? where IdProducto=?";
//        try {
//            con = acceso.Conectar();
//            ps = con.prepareStatement(sql);          
//            ps.setInt(1, cant);           
//            ps.setInt(2, id);
//            ps.executeUpdate();
//        } catch (Exception e) {
//        }
//        return r;   }
    public int actualizar(Producto p) {
        String sql = "update producto set Nombres=?,Precio=?,Stock=?,Estado=? where id=?";
        try {
            con = acceso.Conectar();
            ps = con.prepareStatement(sql);
            ps.setString(1, p.getNom());
            ps.setDouble(2, p.getPre());
            ps.setInt(3, p.getStock());
            ps.setString(4, p.getEstado());
            ps.setInt(5, p.getId());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cerrarConexiones();
        }
        return r;
    }

    public int delete(int id) {
        String sql = "delete from producto where id=?";
        try {
            con = acceso.Conectar();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cerrarConexiones();
        }
        return r;
    }

    private Producto extraerProductoDeResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt(1);
        String nom = rs.getString(2);
        double pre = rs.getDouble(3);
        int stock = rs.getInt(4);
        String estado = rs.getString(5);
        return new Producto(id, nom, pre, stock, estado);
    }

    private void cerrarConexiones() {
        try {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (con != null) {
                con.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
