/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package config;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

/**
 *
 * @author USER
 */
public class dbCRUD {
    String jdbcUrl = "jdbc:mysql://localhost:3306/2210010415_pbo2";
    String username = "root";
    String password = "";
    private String jdbcURL;

    public dbCRUD() {
        try (Connection Koneksi = DriverManager.getConnection(jdbcUrl, username, password)) {
            Driver mysqlDriver = new com.mysql.jdbc.Driver();
            DriverManager.registerDriver(mysqlDriver);

            System.out.println("Berhasil!");
        } catch (SQLException error) {
            System.err.println(error.toString());
        }
    }
    
    public dbCRUD(String url, String user, String pass){
        
        try(Connection Koneksi = DriverManager.getConnection(url, user, pass)) {
            Driver mysqldriver = new com.mysql.jdbc.Driver();
            DriverManager.registerDriver(mysqldriver);
            
            System.out.println("Berhasil");
        } catch (Exception error) {
            System.err.println(error.toString());
        }
        
    }
    
    public Connection getKoneksi() throws SQLException{
        try {
            Driver mysqldriver = new com.mysql.jdbc.Driver();
            DriverManager.registerDriver(mysqldriver);
            
        } catch (SQLException e) {
            System.err.println(e.toString());
        }
        
        return DriverManager.getConnection(this.jdbcURL, this.username, this.password);
    }

    public boolean duplicateKey(String table, String PrimaryKey, String value){
        boolean hasil=false;
        
        try {
            Statement sts = getKoneksi().createStatement();
            ResultSet rs = sts.executeQuery("SELECT*FROM "+table+" WHERE "+PrimaryKey+" ='"+value+"'");
            hasil = rs.next();
            
            rs.close();
            sts.close();
            getKoneksi().close();
            
        } catch (Exception e) {
            System.err.println(e.toString());
        }
        
        
        return hasil;
    }
    
    
    public String getTabelField (String[] TabelField)
    {
        String hasilnya="";
        int deteksiIndexAkhir=TabelField.length-1;
        try{
            for (int i = 0; i < TabelField.length; i++){
                if (i==deteksiIndexAkhir){
                    hasilnya=hasilnya+TabelField[i];                    
                }else{
                    hasilnya=hasilnya+TabelField[i]+',';
                }
            }
        }catch (Exception e){
            System.out.println(e.toString());
        }
        return "("+hasilnya+")";
    }
    
    public String getIsiTabel(String[] IsiTabelnya){
        String hasilnya="";
        int DeteksiTabel=IsiTabelnya.length-1;
        try{
               for (int i = 0; i < IsiTabelnya.length;i++){
                if (i==DeteksiTabel){
                    hasilnya=hasilnya+"'"+IsiTabelnya[i]+"'";
                } else{
                    hasilnya=hasilnya+"'"+IsiTabelnya[i]+"',";
               }
            } 
        }catch (Exception e){
                System.out.println(e.toString());
        }
        return "("+hasilnya+")";
    }

    public void SimpanDinamisStatement(String Kode, String Judul, String Genre, String stok, String tahun){
        
        try {
            if (duplicateKey("DVD","KodeDVD",Kode)){
                JOptionPane.showMessageDialog(null, "Kode sudah terdaftar");
            } else{
                String SQL = "INSERT INTO DVD (KodeDVD,Judul,genre,stok,tahun) Value('"+Kode+"','"+Judul+"','"+Genre+"','"+stok+"','"+tahun+"')";
                Statement perintah = getKoneksi().createStatement();
                
                perintah.executeUpdate(SQL);
                perintah.close();
                getKoneksi().close();
                JOptionPane.showMessageDialog(null, "Data Berhasil Disimpan");
                
            }
        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }

    public void UpdateDinamisPreparedstmt(String Kode, String Judul, String Genre, int stok, String tahun) {
        try {
            if (!duplicateKey("DVD", "KodeDVD", Kode)) {
                JOptionPane.showMessageDialog(null, "Kode DVD tidak ditemukan");
            } else {
                String SQL = "UPDATE DVD SET judul = ?, Genre = ?, stok = ?, tahun = ? WHERE KodeDVD = ?";
                PreparedStatement update = getKoneksi().prepareStatement(SQL);
                update.setString(1, Judul);
                update.setString(2, Genre);
                update.setInt(3, stok);
                update.setString(4, tahun);
                update.setString(5, Kode);
                update.executeUpdate();

                update.close();
                getKoneksi().close();
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public void HapusDinamisPreparedstmt(String Kode) {
        try {
            if (!duplicateKey("DVD", "KodeDVD", Kode)) {
                JOptionPane.showMessageDialog(null, "Kode DVD tidak ditemukan");
            } else {
                String SQL = "DELETE FROM DVD WHERE KodeDVD = ?";
                PreparedStatement hapus = getKoneksi().prepareStatement(SQL);
                hapus.setString(1, Kode);
                hapus.executeUpdate();

                hapus.close();
                getKoneksi().close();

                JOptionPane.showMessageDialog(null, "Data berhasil dihapus");
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public void UpdateDinamis(String bibit, String id_bibt, String text, String[] fieldToUpdate, String[] newValue) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void SimpanDinamis(String bibit, String[] field, String[] value) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void HapusDinamis(String nama_bibit, String id_bibit, String text) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

   

   
}
