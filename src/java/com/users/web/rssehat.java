package com.users.web;

import com.users.web.koneksi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author hp
 */
@ManagedBean
@RequestScoped
public class rssehat {

    /**
     * Creates a new instance of rssehat
     */
    
    private String id_pasien;
    public void setid_pasien(String id_pasien) {
        this.id_pasien = id_pasien;
    }
    public String getid_pasien() {
        return id_pasien;
    }

    private String nama_pasien;
    public void setnama_pasien(String nama_pasien) {
        this.nama_pasien = nama_pasien;
    }
    public String getnama_pasien() {
        return nama_pasien;
    }
    
    private String alamat;
    public void setalamat(String alamat) {
        this.alamat = alamat;
    }
    public String getalamat() {
        return alamat;
    }
   
    private Map<String,Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap(); 

public String Edit_Mahasiswa(){
     FacesContext fc = FacesContext.getCurrentInstance();
     Map<String,String > params = fc.getExternalContext().getRequestParameterMap();
     String Field_NIM = params.get("action");
     try {
          koneksi obj_koneksi = new koneksi();
          Connection connection = obj_koneksi.get_connection();
          Statement st = connection.createStatement();
          ResultSet rs = st.executeQuery("select * from pasien where id_pasien="+Field_NIM);
          rssehat obj_Mahasiswa = new rssehat();
          rs.next();
          obj_Mahasiswa.setid_pasien(rs.getString("id_pasien"));
          obj_Mahasiswa.setnama_pasien(rs.getString("nama_pasien"));
          obj_Mahasiswa.setalamat(rs.getString("alamat"));
          sessionMap.put("EditMahasiswa", obj_Mahasiswa);  
      } catch (Exception e) {
            System.out.println(e);
      }
     return "/Edit.xhtml?faces-redirect=true";   
}

public String Delete_Mahasiswa(){
      FacesContext fc = FacesContext.getCurrentInstance();
      Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
      String Field_NIM = params.get("action");
      try {
         koneksi obj_koneksi = new koneksi();
         Connection connection = obj_koneksi.get_connection();
         PreparedStatement ps = connection.prepareStatement("delete from pasien where id_pasien=?");
         ps.setString(1, Field_NIM);
         System.out.println(ps);
         ps.executeUpdate();
        } catch (Exception e) {
         System.out.println(e);
        }
       return "/index.xhtml?faces-redirect=true";   
}

public String Update_Mahasiswa(){
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
	String Update_NIM= params.get("Update_NIM");
        try {
            koneksi obj_koneksi = new koneksi();
            Connection connection = obj_koneksi.get_connection();
            PreparedStatement ps = connection.prepareStatement("update mahasiswa set id_pasien=?, nama_pasien=?, alamat=? where id_pasien=?");
            ps.setString(1, id_pasien);
            ps.setString(2, nama_pasien);
            ps.setString(3, alamat);
            ps.setString(4, Update_NIM);
            System.out.println(ps);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
       return "/index.xhtml?faces-redirect=true";   
}
    
    public ArrayList getGet_all_mahasiswa() throws Exception{
        ArrayList list_of_mahasiswa=new ArrayList();
             Connection connection=null;
        try {
            koneksi obj_koneksi = new koneksi();
            connection = obj_koneksi.get_connection();
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("Select * from pasien");
            while(rs.next()){
                rssehat obj_Mahasiswa = new rssehat();
                obj_Mahasiswa.setid_pasien(rs.getString("id_pasien"));
                obj_Mahasiswa.setnama_pasien(rs.getString("nama_pasien"));
                obj_Mahasiswa.setalamat(rs.getString("alamat"));
                list_of_mahasiswa.add(obj_Mahasiswa);
            }
        } catch (Exception e) {
            System.out.println(e);
        }finally{
            if(connection!=null){
                connection.close();
            }
        }
        return list_of_mahasiswa;
}
    
public String Tambah_Mahasiswa(){
        try {
            Connection connection=null;
            koneksi obj_koneksi = new koneksi();
            connection = obj_koneksi.get_connection();
            PreparedStatement ps=connection.prepareStatement("insert into pasien(id_pasien, nama_pasien, alamat) value('"+id_pasien+"','"+nama_pasien+"','"+alamat+"')");
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
        return "/index.xhtml?faces-redirect=true";
    }
    
    public rssehat() {
    }
    
}