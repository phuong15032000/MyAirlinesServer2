package myairlinesserver2;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Admin;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author DELL
 */
class transferfile2 extends Thread implements Serializable {

    private static Connection connection = null;
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/myairlines";
    static final String DB_USER = "root";
    static final String DB_PASS = "admin";

    static Socket ClientSoc, dataSoc;
    static ServerSocket DataSoc;
    DataInputStream dinput;
    DataOutputStream doutput;

    transferfile2(Socket soc, ServerSocket datasoc) {
        try {
            ClientSoc = soc;
            DataSoc = datasoc;
            dinput = new DataInputStream(ClientSoc.getInputStream());
            doutput = new DataOutputStream(ClientSoc.getOutputStream());
            System.out.println("FTP Client Connected ...");
            String use = dinput.readUTF();
            if (use.compareTo("test") == 0) {
                String pass = dinput.readUTF();
                if (pass.compareTo("test") == 0) {
                    doutput.writeUTF("Success");
                    System.out.println("User logged in successfully");

                } else {
                    doutput.writeUTF("Failure");
                }

            } else {
                doutput.writeUTF("Failure");
            }
            start();

        } catch (Exception ex) {
        }
    }

    void login() throws IOException, SQLException {

        String username = dinput.readUTF();
        String password = dinput.readUTF();

        System.out.println("da nhan dc du lieu dang nhap username=" + username + "pass=" + password);
        String query = "select * from admin where username = ? and password= ?";

        connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setString(1, username);
        stmt.setString(2, password);
        ResultSet rs = (ResultSet) stmt.executeQuery();
        int numberRow = 0;
        while (rs.next()) {
            //doutput.writeUTF("success");
            System.out.println("co 1 truong");
            numberRow++;
        }
        if (numberRow != 0) {
            doutput.writeUTF("success");
            System.out.println("co");
        }
        if (numberRow == 0) {
            doutput.writeUTF("fail");
            System.out.println("khong");
        }
        connection.close();
    }

    void getInfor() throws IOException, SQLException {
        String username = dinput.readUTF();
        System.out.println("da nhan dc du lieu update infor");
        connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
        PreparedStatement stmt = connection.prepareStatement("select * from admin where username = ?");
        stmt.setString(1, username);
        ResultSet rs = (ResultSet) stmt.executeQuery();
        Admin admin = new Admin();
        while (rs.next()) {
            admin.setAdminId(rs.getInt("adminId"));
            admin.setName(rs.getString("name"));
            admin.setPhoneNumber(rs.getString("phoneNumber"));
            admin.setIdentifyNumber(rs.getString("identifyNumber"));
            admin.setEmail(rs.getString("email"));
            admin.setUsername(rs.getString("username"));
            admin.setPassword(rs.getString("password"));
            admin.setAddress(rs.getString("address"));
        }
        System.out.println("da lay dc thong tin admin");
        ObjectOutputStream objectOutput;
        objectOutput = new ObjectOutputStream(ClientSoc.getOutputStream());
        objectOutput.writeObject(admin);

        connection.close();

    }

    void update() throws IOException, ClassNotFoundException, SQLException {
        Admin admin = new Admin();
        ObjectInputStream objectInput = new ObjectInputStream(ClientSoc.getInputStream());

        admin = (Admin) objectInput.readObject();
        System.out.println("da nhan dc object admin");
        connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
        PreparedStatement stmt = connection.prepareStatement("update admin set name=?, phoneNumber=?, identifyNumber=?, email=?, address=? where username=?;");
        stmt.setString(1, admin.getName());
        stmt.setString(2, admin.getPhoneNumber());
        stmt.setString(3, admin.getIdentifyNumber());
        stmt.setString(4, admin.getEmail());
        stmt.setString(5, admin.getAddress());
        stmt.setString(6, admin.getUsername());
        stmt.execute();
        System.out.println("da nhan dc lenh chay");
        connection.close();
        doutput.writeUTF("updateSuc");
    }

    void checkPass() throws IOException, SQLException {
        String username = dinput.readUTF();
        String password = dinput.readUTF();
        System.out.println("da nhan dc pass cu");
        connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
        PreparedStatement stmt = connection.prepareStatement("select * from admin where username = ? and password= ?");
        stmt.setString(1, username);
        stmt.setString(2, password);
        ResultSet rs = (ResultSet) stmt.executeQuery();
        int numberRow = 0;
        while (rs.next()) {
            //doutput.writeUTF("success");
            System.out.println("co 1 truong");
            numberRow++;
        }
        if (numberRow != 0) {
            doutput.writeUTF("success");
            System.out.println("co");
        }
        if (numberRow == 0) {
            doutput.writeUTF("fail");
            System.out.println("khong");
        }
        connection.close();
    }

    void changePass() throws IOException {

        String username = dinput.readUTF();
        String password = dinput.readUTF();
        System.out.println("da nhan dc pass moi");
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            PreparedStatement stmt = connection.prepareStatement("update admin set password=? where username=?;");
            stmt.setString(1, password);
            stmt.setString(2, username);
            stmt.execute();
            doutput.writeUTF("success");
        } catch (SQLException ex) {
            Logger.getLogger(transferfile2.class.getName()).log(Level.SEVERE, null, ex);
            doutput.writeUTF("fail");
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(transferfile2.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    void signup() throws IOException, ClassNotFoundException {

        Admin admin = new Admin();
        ObjectInputStream objectInput = new ObjectInputStream(ClientSoc.getInputStream());

        admin = (Admin) objectInput.readObject();
        System.out.println("da nhan dc object admin");
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            PreparedStatement stmt = connection.prepareStatement("insert into admin (name, phoneNumber, identifyNumber, email, address, username, password) values (?,?,?,?,?,?,?);");
            stmt.setString(1, admin.getName());
            stmt.setString(2, admin.getPhoneNumber());
            stmt.setString(3, admin.getIdentifyNumber());
            stmt.setString(4, admin.getEmail());
            stmt.setString(5, admin.getAddress());
            stmt.setString(6, admin.getUsername());
            stmt.setString(7, admin.getPassword());
            stmt.execute();
            System.out.println("da nhan dc lenh chay");
            connection.close();
            doutput.writeUTF("singupSuc");
        } catch (SQLException ex) {
            Logger.getLogger(transferfile2.class.getName()).log(Level.SEVERE, null, ex);
            doutput.writeUTF("signupFail");
        }
    }

    public void run() {

        while (true) {
            try {

                //System.out.println("Waiting for Command ...");
                String Command = dinput.readUTF();
                if (Command.compareTo("login") == 0) {
                    System.out.println("\tlogin Command Received ...");
                    login();
                    continue;
                } else if (Command.compareTo("infor") == 0) {
                    System.out.println("\tgetInfor Command Received ...");
                    getInfor();
                    continue;
                } else if (Command.compareTo("update") == 0) {
                    System.out.println("\tupdate Command Received ...");
                    update();
                    continue;
                } else if (Command.compareTo("checkPass") == 0) {
                    System.out.println("\tcheckPass Command Received ...");
                    checkPass();
                    continue;
                } else if (Command.compareTo("changePass") == 0) {
                    System.out.println("\tchangePass Command Received ...");
                    changePass();
                    continue;
                } else if (Command.compareTo("signup") == 0) {
                    System.out.println("\tsignup Command Received ...");
                    signup();
                    continue;
                }

            } catch (Exception ex) {
            }
        }
    }

}
