package com.fasoulis;

import dao.ProductManagmentDAO;
import jdk.swing.interop.SwingInterOpUtils;
import org.w3c.dom.ls.LSOutput;
import pojo.Product;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Application {
    static Scanner input = new Scanner(System.in);
    static ProductManagmentDAO dao = new ProductManagmentDAO();
    final static int prefix = 5;

    public static List<Product> blockchain = new ArrayList<Product>();

    public static void main(String[] args) {
        boolean quit = false;
        printActions();
        while(!quit) {
            System.out.println("\nEnter action: (6 to show available actions)");
            int action = input.nextInt();
            input.nextLine();

            switch (action) {
                case 0:
                    System.out.println("\nShutting down...");
                    quit = true;
                    break;
                case 1:
                    viewProducts();
                    break;
                case 2:
                    addProduct();
                    break;
                case 3:
                    searchProductById();
                    break;
                case 4:
                    addManyProducts();
                    break;
                case 5:
                    searchProductsByCode();
                    break;
                case 6:
                    printActions();
                    break;
                case 7:
                    viewTimeStatisticsOfProduct();
                    break;
                default:
                    System.out.println("Invalid Operation!Please try again");
            }
        }
    }

    //-----------Μέθοδος εκτύπωσης διαθέσιμων λειτουργιών προς τον χρήστη--------
    private static void printActions() {
        System.out.println("\nAvailable actions:\npress");
        System.out.println("0  - to shutdown\n" +
                "1  - to print products in block chain\n" +
                "2  - to add a new product in block chain\n" +
                "3  - to search for a product by its id\n" +
                "4  - to add many products in block chain\n" +
                "5  - to search for a product by its code\n" +
                "6  - to print a list of available actions\n"+
                "7  - to view time statistics of a product\n"
        );
        System.out.println("Choose your action: ");
    }
//---------------Επιστρέφει την Λίστα με τα προϊόντα του blockChain---------

    public static void viewProducts(){
        List<Product> productList = dao.getAllProducts();
        for (Product product: productList) {
            displayProduct(product);
        }
    }
    //--------------------Επιστρέφει ενα προϊόν-------------------------------------

    private static void displayProduct(Product product){
        System.out.println("-----------------------------------------------------");
        System.out.println("Product ID " + product.getId());
        System.out.println("Product CODE " + product.getCodeOfProduct());
        System.out.println("Product TITLE " + product.getTitleOfProduct());
        System.out.println("Product TIMESTAMP " + product.getTimeStamp());
        System.out.println("Product PRICE " + product.getPrice());
        System.out.println("Product DESCRIPRTION " + product.getDescriptionOfProduct());
        System.out.println("Product Previous Hash " + product.getPreviousHash());
        System.out.println("Product Hash " + product.getHash());
        System.out.println("-----------------------------------------------------");
    }
//------------------Προσθήκη ενός προϊόντος-----------------------
    public static void addProduct(){

/*System.out.println("-------------------------------");
        System.out.println("Enter product ID: ");
        System.out.println("-------------------------------");
        int id = input.nextInt();*/

        System.out.println("-------------------------------");
        System.out.println("Enter product CODE: ");
        System.out.println("-------------------------------");
        int CodeOfProduct = input.nextInt();

        input.nextLine();
        System.out.println("-------------------------------");
        System.out.println("Enter product TITLE: ");
        System.out.println("-------------------------------");
        String TitleOfProduct = input.nextLine();

        input.nextLine();
        System.out.println("-------------------------------");
        System.out.println("Enter product PRICE: ");
        System.out.println("-------------------------------");
        int Price= input.nextInt();

        input.nextLine();
        System.out.println("-------------------------------");
        System.out.println("Enter product DESCRIPTION: ");
        System.out.println("-------------------------------");
        String DescriptionOfProduct= input.nextLine();

        //Έναρξη καταγραφής χρόνου
        long startTime = System.nanoTime();
        if (dao.countProducts()==0) {
            Product product = new Product(CodeOfProduct, TitleOfProduct, Price, DescriptionOfProduct,new Date().getTime(), "0");
            product.mineBlock(prefix);
            int status =dao.addProduct(product);
            System.out.println("Genesis block created");
            if (status==1){
                System.out.println("Product added successfully");
            }else{
                System.out.println("An error occured");
            }
        }else {
            Product product = new Product(CodeOfProduct, TitleOfProduct, Price, DescriptionOfProduct,new Date().getTime(), dao.getProductById(dao.countProducts()).getHash());
            product.mineBlock(prefix);
            int status = dao.addProduct(product);
            System.out.println("block created");

            if (status==1){
                System.out.println("Product/s added successfully");
            }else{
                System.out.println("An error occured");
            }
        }
        //Πέρας καταγραφής χρόνου
        long endTime = System.nanoTime();
        long duration = endTime-startTime;
        System.out.println("Total time ellapsed: "+(float)duration/1000000000 +" seconds");
    }
    //-----------Προσθήκη πολλών προϊόντων ταυτόχρονα------------

    public static void addManyProducts(){
        System.out.println("How many products do you want to add to block  ?");
        int number = input.nextInt();

        for (int i = 0; i <number ; i++) {
            addProduct();
        }
    }
    //------------Εύρεση προϊόντος με βάση το id------------------

    public static void searchProductById(){
        System.out.println("-------------------------------");
        System.out.println("Enter product ID: ");
        System.out.println("-------------------------------");
        int id = input.nextInt();

        Product product = dao.getProductById(id);
        displayProduct(product);
    }
//------------Εύρεση προϊόντος με βάση το code------------------

    public static void searchProductsByCode(){
            System.out.println("-------------------------------");
            System.out.println("Enter product CODE: ");
            System.out.println("-------------------------------");
            int code = input.nextInt();

            int numberOfProducts = dao.getProductCountByCode(code);
            switch (numberOfProducts){
                case 0:
                    System.out.println("No such Product found");
                    break;
                case 1:
                    System.out.println("Product Exists one time in blockChain with the following details");
                    Product product = dao.getProductByCode(code);
                    displayProduct(product);
                    break;
                case 2:
                    System.out.println("The product exists more than one time with the following details ");
                    Product product1 = dao.getProductByCode(code);
                    displayProduct(product1);
                    break;
            }
    }
    //------------Εύρεση χρονικών σφραγίδων(timestamps) ενος προιόντος με βάση το code του------------------
    private static void viewTimeStatisticsOfProduct() {
        System.out.println("-------------------------------");
        System.out.println("Enter product CODE: ");
        System.out.println("-------------------------------");
        int code = input.nextInt();

        int numberOfProducts = dao.getProductCountByCode(code);
        switch (numberOfProducts){
            case 0:
                System.out.println("No such Product found");
                break;
            case 1:
                System.out.println("Product Exists one time in blockChain with the following timestamp");
                Product product = dao.getProductByCode(code);
                System.out.println(product.getTimeStamp());
                break;
            case 2:
                System.out.println("The product exists more than one time with the following timestamps ");
                List<Product> productList1 = dao.getProductsByCode(code);
                for (Product productListw: productList1) {
                    System.out.println(productListw.getTimeStamp());
                }
                break;
            default:
                System.out.println("The product exists more than one time with the following timestamps ");
                List<Product> productList = dao.getProductsByCode(code);
                for (Product productListw: productList) {
                    System.out.println(productListw.getTimeStamp());
                }
                break;
        }
    }
}
