package Util;

import java.util.ArrayList;

public class BookDetail {
    private static ArrayList<Book> arrayList=new ArrayList<>();
    public void addBook(String bookID, String quantity){
        Book book = new Book();
        book.bookID=bookID;
        book.quantity=quantity;
        BookDetail.arrayList.add(book);
    }

    public static ArrayList<Book> getAllBook() {
        return arrayList;
    }

    public static void clearAllBook() {
        BookDetail.arrayList.clear();
    }

    public  class Book{
        public String getBookID() {
            return bookID;
        }

        public String getQuantity() {
            return quantity;
        }

        private String bookID,quantity;

    }
}
