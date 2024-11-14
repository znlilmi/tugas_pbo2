/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package config;
import java.awt.HeadlessException;
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
    
    public Connection getKoneksi() {
    Connection koneksi = null;
    try {
        String url = "jdbc:mysql://localhost:3306/2210010415_pbo2"; // Ganti sesuai konfigurasi Anda
        String user = "root"; // Ganti dengan username database Anda
        String password = ""; // Ganti dengan password database Anda

        koneksi = DriverManager.getConnection(url, user, password);
    } catch (SQLException e) {
        System.out.println("Koneksi gagal: " + e.getMessage());
    }
    return koneksi;
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

    public void SimpanDinamisBibit(String id_bibit, String[] nama_bibit, String[] jenis_bibit, int[] jumlah, String[] tanggal) {
    try {
        if (duplicateKey("bibit", "id_bibit", id_bibit)) {
            JOptionPane.showMessageDialog(null, "id_bibit sudah terdaftar");
        } else { 
            String SQL = "INSERT INTO bibit (id_bibit, nama_bibit, jenis_bibit, jumlah, tanggal) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement simpan = getKoneksi().prepareStatement(SQL);

            for (int i = 0; i < nama_bibit.length; i++) {
                simpan.setString(1, id_bibit);
                simpan.setString(2, nama_bibit[i]);
                simpan.setString(3, jenis_bibit[i]);
                simpan.setInt(4, jumlah[i]);
                simpan.setString(5, tanggal[i]);

                simpan.addBatch(); // Tambahkan ke batch
            }

            simpan.executeBatch(); // Eksekusi batch
            System.out.println("Data Berhasil Disimpan");
            simpan.close();
            getKoneksi().close();
        }
    } catch (Exception e) {
        System.out.println(e.toString());
    }
}

    
    public void SimpanDinamisKelompok(String id_kelompok, String nama_kelompok, String desa, String tahun_bentuk, String jumlah_anggota, String luas_lahan, String ketua_kelompok) {
    try {
        if (duplicateKey("kelompok", "id_kelompok", id_kelompok)) {
            JOptionPane.showMessageDialog(null, "kelompok sudah terdaftar");
        } else { 
            String SQL = "INSERT INTO kelompok_tani (id_kelompok, nama_kelompok, desa, tahun_bentuk, jumlah_anggota, luas_lahan, ketua_kelompok) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement simpan = getKoneksi().prepareStatement(SQL);
            simpan.setString(1, id_kelompok);
            simpan.setString(2, nama_kelompok);
            simpan.setString(3, desa);
            simpan.setString(4, tahun_bentuk);
            simpan.setString(5, jumlah_anggota);
            simpan.setString(6, luas_lahan);
            simpan.setString(7, ketua_kelompok);
            
            System.out.println("Data Berhasil Disimpan");
            simpan.executeUpdate();
            simpan.close();
            getKoneksi().close();
        }
    } catch (Exception e) {
        System.out.println(e.toString());
    }
}
    

    public void UpdateDinamis(String NamaTabel, String PrimaryKey, String IsiPrimary,String[] Field, String[] Value){
        
        try {
           String SQLUbah = "UPDATE "+NamaTabel+" SET "+getFieldValueEdit(Field, Value)+" WHERE "+PrimaryKey+"='"+IsiPrimary+"'";
           Statement perintah = getKoneksi().createStatement();
           perintah.executeUpdate(SQLUbah);
           perintah.close();
           getKoneksi().close();
        } catch (SQLException e) {
            System.err.println(e.toString());
        }
        
    }

    public void HapusDinamis(String NamaTabel, String PK, String isi){
        try {
            String SQL="DELETE FROM "+NamaTabel+" WHERE "+PK+"='"+isi+"'";
            Statement perintah = getKoneksi().createStatement();
            perintah.executeUpdate(SQL);
            perintah.close();
            JOptionPane.showMessageDialog(null,"Data Berhasil Dihapus");
        } catch (HeadlessException | SQLException e) {
            System.out.println(e.toString());
        }
    }

    

    public void SimpanDinamis(String bibit, String[] field, String[] value) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private String getFieldValueEdit(String[] Field, String[] Value) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

   

   
}
