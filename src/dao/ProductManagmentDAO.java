package dao;

import dbutil.DBUtil;
import pojo.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Ακολουθούν οι μέθοδοι επεξεργασίας μεταξύ εφαρμογής και βάσης(το γνωστό Repository)

public class ProductManagmentDAO {
 // ---------Μέθοδος επιστροφής ΟΛΩΝ ΤΩΝ προϊόντων του BlockChain--------------------------
    public List<Product> getAllProducts(){
        List<Product> products = new ArrayList<>();
        try {
            Connection connection = DBUtil.getConnection();
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM blockchain.block");

            while (rs.next()){
                Product product = new Product(rs.getInt("id"),
                                              rs.getInt("CodeOfProduct"),
                                              rs.getString("TitleOfProduct"),
                                              rs.getInt("Price"),
                                              rs.getString("DescriptionOfProduct"),
                                              rs.getLong("TimeStamp"),
                                              rs.getString("previousHash"),
                                              rs.getString("hash")
                                              );
                products.add(product);
            }
            DBUtil.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }
    //----------------------------Μέθοδος επιστροφής ΠΛΗΘΟΥΣ ΠΡΟΙΟΝΤΩΝ ΤΟΥ blocChain-----------------
    public int countProducts(){
        int counter = 0;
        try {
            Connection connection = DBUtil.getConnection();
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM blockchain.block");

            while (rs.next()){
                Product product = new Product(rs.getInt("id"),
                        rs.getInt("CodeOfProduct"),
                        rs.getString("TitleOfProduct"),
                        rs.getInt("Price"),
                        rs.getString("DescriptionOfProduct"),
                        rs.getLong("TimeStamp"),
                        rs.getString("previousHash"),
                        rs.getString("hash")
                );
                counter++;
            }
            DBUtil.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return counter;
    }
 //---------------------- Μέθοδος επιστροφής ενός προϊόντος απο το id ---------

    public Product getProductById(int id){
        Product product=null;
        try {
            Connection connection = DBUtil.getConnection();
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM blockchain.block WHERE id = ?");
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                product = new Product(rs.getInt("id"),
                        rs.getInt("CodeOfProduct"),
                        rs.getString("TitleOfProduct"),
                        rs.getInt("Price"),
                        rs.getString("DescriptionOfProduct"),
                        rs.getLong("TimeStamp"),
                        rs.getString("previousHash"),
                        rs.getString("hash")
                );
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return product;
    }
//-----------Μέθοδος επιστροφής ΑΡΙΘΜΟΥ προϊόντων με βάση το code τους-------------------------------
    public int getProductCountByCode(int code){
        List<Product> products = new ArrayList<>();
        try {
            Connection connection = DBUtil.getConnection();
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM blockchain.block WHERE CodeOfProduct = ?");
            ps.setInt(1,code);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Product product = new Product(rs.getInt("id"),
                        rs.getInt("CodeOfProduct"),
                        rs.getString("TitleOfProduct"),
                        rs.getInt("Price"),
                        rs.getString("DescriptionOfProduct"),
                        rs.getLong("TimeStamp"),
                        rs.getString("previousHash"),
                        rs.getString("hash")
                );
                products.add(product);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return products.size();
    }
    //---------------------- Μέθοδος επιστροφής ενός προϊόντος απο το code  ---------
    public Product getProductByCode(int code){
        Product product=null;
        try {
            Connection connection = DBUtil.getConnection();
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM blockchain.block WHERE CodeOfProduct = ?");
            ps.setInt(1,code);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                product = new Product(rs.getInt("id"),
                        rs.getInt("CodeOfProduct"),
                        rs.getString("TitleOfProduct"),
                        rs.getInt("Price"),
                        rs.getString("DescriptionOfProduct"),
                        rs.getLong("TimeStamp"),
                        rs.getString("previousHash"),
                        rs.getString("hash")
                );
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return product;
    }
    //---------------------- Μέθοδος επιστροφής ΛΙΣΤΑΣ προϊόντων απο το code  ---------
    public List<Product> getProductsByCode(int code){
        List<Product> products = new ArrayList<>();
        try {
            Connection connection = DBUtil.getConnection();
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM blockchain.block WHERE CodeOfProduct = ?");
            ps.setInt(1,code);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Product product = new Product(rs.getInt("id"),
                        rs.getInt("CodeOfProduct"),
                        rs.getString("TitleOfProduct"),
                        rs.getInt("Price"),
                        rs.getString("DescriptionOfProduct"),
                        rs.getLong("TimeStamp"),
                        rs.getString("previousHash"),
                        rs.getString("hash")
                );
                products.add(product);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return products;
    }
    //---------------------- Μέθοδος προσθήκης πρoϊόντος απο τον χρήστη στην βάση ---------------
    public int addProduct(Product product){
        int status= 0;
        try {
            Connection connection = DBUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement("INSERT INTO blockchain.block (CodeOfProduct,TitleOfProduct,Price,DescriptionOfProduct,TimeStamp,previousHash,hash)  VALUES (?,?,?,?,?,?,?)");
             ps.setInt(1,product.getCodeOfProduct());
             ps.setString(2,product.getTitleOfProduct());
             ps.setInt(3,product.getPrice());
             ps.setString(4,product.getDescriptionOfProduct());
             //------Την χρονοσφραγίδα και τα hash- previous hash την αποθηκεύουμε στην βάση αυτόματα χωρίς να την ζητάμε απο τον χρήστη-------
            ps.setLong(5,product.getTimeStamp());
            ps.setString(6,product.getPreviousHash());
            ps.setString(7,product.getHash());

             status = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return status;
    }
}