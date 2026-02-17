public class Book {//determines the structure of a book object
    private int id;
    private String title;
    private String author;
    private double price;
    private int copies;
    private boolean available;

    public Book(int id, String title, String author, double price, int copies, boolean available) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.price = price;
        this.copies = copies;
        this.available = available;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public double getPrice() { return price; }
    public int getCopies() { return copies; }
    public boolean isAvailable() { return available; }

    @Override
    public String toString() {
        return String.format("%d | %-20s | %-15s | %.2f | %d | %s",
                id, title, author, price, copies, available ? "Available" : "Not Available");
    }
}
