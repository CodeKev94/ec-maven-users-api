package com.demo;

import java.io.*;
import java.security.MessageDigest;
import java.sql.*;

public class VulnerableExample {

    // 🔥 Hardcoded secrets (SAST lo detecta)
    private static final String DB_USER = "root";
    private static final String DB_PASS = "123456";
    private static final String SECRET_KEY = "mysecretkey";

    public static void main(String[] args) throws Exception {

        String userInput = "admin' OR '1'='1";

        // 🔥 Vulnerabilidad 1: SQL Injection
        Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/testdb", DB_USER, DB_PASS);

        Statement stmt = conn.createStatement();
        String query = "SELECT * FROM users WHERE username = '" + userInput + "';";
        ResultSet rs = stmt.executeQuery(query);
        System.out.println("Query ejecutada: " + query);


        // 🔥 Vulnerabilidad 2: Hardcoded MD5 (hash débil)
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update("password123".getBytes());
        byte[] digest = md.digest();
        System.out.println("Hash MD5: " + digest);


        // 🔥 Vulnerabilidad 3: Path Traversal
        String file = "../../etc/passwd";
        BufferedReader br = new BufferedReader(new FileReader(file));
        System.out.println("Contenido del archivo: " + br.readLine());


        // 🔥 Vulnerabilidad 4: Command Injection
        String userCmd = "ls -la"; 
        Runtime.getRuntime().exec("sh -c " + userCmd);
        System.out.println("Comando ejecutado: " + userCmd);
    }
}
