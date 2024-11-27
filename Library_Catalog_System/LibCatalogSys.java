import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//This class represents a book in the library
class Book{
	private String writer;
	private String name;

	Book(String writer, String name){
		this.writer = writer;
		this.name = name;
	}

	String getWriter(){
		return writer;
	}
	String getName(){
		return name;
	}

	@Override
	public String toString(){
		return "Writer: " + writer +", Name: " + name;
	}
}

//This class is added to manage the library records
class LibRecords{
	private List<Book> books;

	LibRecords(){
		books = new ArrayList<>();
	}
	// Now we will add a book here
	void addBook(String writer, String name){
		books.add(new Book(writer,name));
		System.out.println("Books are added successfully in the stack!");
	}
	//We can search for books by name and writer
	void searchBooks(String query, boolean searchByName){
		String type = searchByName ? "name" : "writer"; //
		System.out.println("Searching for books by " + type + ": " + query);
		boolean found = false;
		for (Book book : books) {
            if (searchByName && book.getName().equalsIgnoreCase(query) ||
                !searchByName && book.getWriter().equalsIgnoreCase(query)){
                System.out.println(book);
                found = true;
            }
        }    
		if(!found){
			System.out.println("So there are no books found matched as per the given input by you " + type + ".");
		}
	}
	//Now all the books will be listed here in an organized manner
	void listOfBooks(){
		if(books.isEmpty()){
			System.out.println("No books available in our library.");
		}
		else{
			System.out.println("Library Books: ");
			for(Book book : books){
				System.out.println(book);
			}
		}
	}
	//void deleteBook(String query, boolean deleteByName){
			//CAN ADD THIS IN FUTURE
	//}
}
//The final part will be the main class, which will handle the interaction part
class LibCatalogSys{
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		LibRecords recs = new LibRecords();

		while(true){
			System.out.println("\nOur Library:");
			System.out.println("1. Add Book");
			System.out.println("2. Search By Name");
			System.out.println("3. Search By Writer");
			System.out.println("4. List All The Books added");
			System.out.println("5. Exit");
			System.out.println("\nEnter your choice: ");

			int choice;
			try{
				choice = Integer.parseInt(scanner.nextLine());
			}
			catch (NumberFormatException e){
				System.out.println("Invalid input. Please enter a numeric choice.");
				continue;
			}

			switch(choice){
			case 1 :
				System.out.println("Enter book name here: ");
				String name = scanner.nextLine();
				System.out.println("Enter book writer here: ");
				String writer = scanner.nextLine();
				recs.addBook(writer,name); // Fixed my code here
				break;

			case 2:
				System.out.println("Enter name to search: ");
				String bookName = scanner.nextLine();
				recs.searchBooks(bookName, true);
				break;	

			case 3:
				System.out.println("Enter writer to search: ");	
				String bookWriter = scanner.nextLine();
				recs.searchBooks(bookWriter, false);
				break;

			case 4:
				recs.listOfBooks();
				break;

			case 5:
				System.out.println("Thanks For Your Time! GoodBye... EXIT");
				scanner.close();
				System.exit(0);

			default:
				System.out.println("Invalid choice by you! please try again sir.");		
			}
		}
	}
}