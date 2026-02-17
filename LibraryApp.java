import java.sql.*;
import java.util.Scanner;

public class LibraryApp { //determines the structure of the library app and also contains main method
    private static final Connection conn = DBConnection.getConnection();
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        // Check if database connection is established
        if (conn == null) {
            System.out.println("Error: Could not connect to the database.");
            System.out.println("Please ensure:");
            System.out.println("1. MySQL server is running");
            System.out.println("2. Database 'librarydb' exists");
            System.out.println("3. Credentials are correct (root/Kanishka$123)");
            return;
        }

        int choice;
        do {
            System.out.println("\n=== LIBRARY MANAGEMENT SYSTEM ===");
            System.out.println("1. Add Book");
            System.out.println("2. Display All Books");
            System.out.println("3. Update Book Details");
            System.out.println("4. Delete Book");
            System.out.println("5. Search Books");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    addBook();
                    break;
                case 2:
                    displayBooks();
                    break;
                case 3:
                    updateBook();
                    break;
                case 4:
                    deleteBook();
                    break;
                case 5:
                    searchBook();
                    break;
                case 6:
                    System.out.println("Exiting... Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice! Try again.");
                    break;
            }
        } while (choice != 6);
    }

    private static void addBook() {
        try {
            System.out.print("Enter title: ");
            String title = sc.nextLine();
            System.out.print("Enter author: ");
            String author = sc.nextLine();
            System.out.print("Enter price: ");
            double price = sc.nextDouble();
            System.out.print("Enter number of copies: ");
            int copies = sc.nextInt();
            sc.nextLine();

            boolean available = copies > 0;

            String sql = "INSERT INTO books (title, author, price, copies, available) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, title);
            ps.setString(2, author);
            ps.setDouble(3, price);
            ps.setInt(4, copies);
            ps.setBoolean(5, available);
            ps.executeUpdate();
            System.out.println("Book added successfully!");
        } catch (Exception e) {
            System.out.println("Error adding book: " + e.getMessage());
        }
    }

    private static void displayBooks() {
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM books");
            System.out.println("\nID | Title | Author | Price | Copies | Availability");
            System.out.println("-------------------------------------------------------------");
            while (rs.next()) {
                Book b = new Book(
                        rs.getInt("book_id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getDouble("price"),
                        rs.getInt("copies"),
                        rs.getBoolean("available")
                );
                System.out.println(b);
            }
        } catch (Exception e) {
            System.out.println("Error displaying books: " + e.getMessage());
        }
    }

    private static void updateBook() {
        try {
            System.out.print("Enter Book ID to update: ");
            int id = sc.nextInt();
            sc.nextLine();

            System.out.print("Enter new price: ");
            double price = sc.nextDouble();
            System.out.print("Enter new number of copies: ");
            int copies = sc.nextInt();
            sc.nextLine();
            boolean available = copies > 0;

            String sql = "UPDATE books SET price=?, copies=?, available=? WHERE book_id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setDouble(1, price);
            ps.setInt(2, copies);
            ps.setBoolean(3, available);
            ps.setInt(4, id);
            int rows = ps.executeUpdate();

            if (rows > 0) System.out.println("Book updated successfully!");
            else System.out.println("Book not found.");
        } catch (Exception e) {
            System.out.println("Error updating book: " + e.getMessage());
        }
    }

    private static void deleteBook() {
        try {
            System.out.print("Enter Book ID to delete: ");
            int id = sc.nextInt();
            sc.nextLine();

            String sql = "DELETE FROM books WHERE book_id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            int rows = ps.executeUpdate();

            if (rows > 0) System.out.println("Book deleted successfully!");
            else System.out.println("Book not found.");
        } catch (Exception e) {
            System.out.println("Error deleting book: " + e.getMessage());
        }
    }

    private static void searchBook() {
        try {
            System.out.print("Search by title or author: ");
            String keyword = sc.nextLine();

            String sql = "SELECT * FROM books WHERE title LIKE ? OR author LIKE ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + keyword + "%");
            ps.setString(2, "%" + keyword + "%");
            ResultSet rs = ps.executeQuery();

            boolean found = false;
            System.out.println("\nResults:");
            while (rs.next()) {
                found = true;
                Book b = new Book(
                        rs.getInt("book_id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getDouble("price"),
                        rs.getInt("copies"),
                        rs.getBoolean("available")
                );
                System.out.println(b);
            }
            if (!found) System.out.println("No matching books found.");
        } catch (Exception e) {
            System.out.println("Error searching book: " + e.getMessage());
        }
    }
}
