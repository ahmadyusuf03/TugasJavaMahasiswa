/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package javadb;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Ahmad Yusuf
 */
public class JavaDb {
    private static final String URL = "jdbc:mysql://localhost:3306/modul";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection getConnection() {
        Connection conn = null;
        try {
            // Muat driver MySQL (opsional di versi Java modern)
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Buat koneksi
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Koneksi berhasil!");
        } catch (ClassNotFoundException e) {
            System.out.println("Driver JDBC tidak ditemukan: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Koneksi gagal: " + e.getMessage());
        }
        return conn;
    }
    
    public static void insert(Item item) {
        String sql = "INSERT INTO items (name, quantity) VALUES (?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, item.name);
            stmt.setInt(2, item.quantity);
            stmt.executeUpdate();
            System.out.println("Data berhasil ditambahkan.");
        } catch (SQLException e) {
            System.out.println("Error insert: " + e.getMessage());
        }
    }

    // READ
    public static List<Item> getAll() {
        List<Item> list = new ArrayList<>();
        String sql = "SELECT * FROM items";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Item item = new Item(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getInt("quantity")
                );
                list.add(item);
            }
        } catch (SQLException e) {
            System.out.println("Error read: " + e.getMessage());
        }
        return list;
    }

    // UPDATE
    public static void update(Item item) {
        String sql = "UPDATE items SET name = ?, quantity = ? WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, item.name);
            stmt.setInt(2, item.quantity);
            stmt.setInt(3, item.id);
            stmt.executeUpdate();
            System.out.println("Data berhasil diperbarui.");
        } catch (SQLException e) {
            System.out.println("Error update: " + e.getMessage());
        }
    }

    // DELETE
    public static void delete(int id) {
        String sql = "DELETE FROM items WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("Data berhasil dihapus.");
        } catch (SQLException e) {
            System.out.println("Error delete: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        // Uji koneksi
        Connection conn = getConnection();
        if (conn != null) {
            try {
                conn.close();
                System.out.println("Koneksi ditutup.");
            } catch (SQLException e) {
                System.out.println("Gagal menutup koneksi: " + e.getMessage());
            }
        }
    }
    
}
