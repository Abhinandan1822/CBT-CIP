LIBRARY CATALOG SYSTEM

DESCRIPTION:

The Library Catalog System is a Java-based console application that allows users to manage a collection of books in a library. It enables users to add books, search for books by their name or writer, and display the list of all available books. This simple and interactive program is designed for beginners to understand object-oriented programming principles, such as classes, methods, and encapsulation.

FEATURES:

1.Add a Book: Users can add a book by providing the writer's name and the book's title.
2.Search for Books:
	--By Book Name.
	--By Writer Name.
3.List All Books: Displays all books currently in the library.
4.Exit the Program: Exits the application gracefully.


HOW TO RUN:
Prerequisites
	--Java Development Kit (JDK) 8 or later.
	--A text editor or an Integrated Development Environment (IDE) like IntelliJ IDEA, Eclipse, or NetBeans.


STEPS TO RUN:
Clone the repository or download the LibraryCatalogSystem.java file.

Copy code:
	git clone https://github.com/<your-username>/LibraryCatalogSystem.git
	cd LibraryCatalogSystem

Open the file in your preferred IDE or text editor.

Compile the code using the javac command:

Copy code
	javac LibraryCatalogSystem.java
Run the program:

Copy code
	java LibCatalogSys


USAGE:
When the program starts, you'll see the following menu:

Our Library:
1. Add Book
2. Search By Name
3. Search By Writer
4. List All The Books added
5. Exit

Enter your choice: 
Example Interaction:

To add a book:
	Enter 1.
	Provide the book name and writer.
To search for a book by name:
	Enter 2.
	Input the book name.
To search for a book by writer:
	Enter 3.
	Input the writer's name.
To list all books:
	Enter 4.
To exit:
	Enter 5.


CODE STRUCTURE
Book Class
	Represents a book with writer and name attributes.
		Includes methods:
		getWriter()
		getName()
		toString() (for displaying book details).
LibRecords Class
	Manages the library's collection of books.
		Methods:
		addBook(writer, name): Adds a new book.
		searchBooks(query, searchByName): Searches books by name or writer.
		listOfBooks(): Displays all books in the library.
LibCatalogSys Class
	Contains the main method for running the program.
	Provides a menu-based user interface.


POSSIBLE MODIFICATIONS:
Add functionality to remove books.
Save and load book data to/from a file for persistence.
Implement sorting options for the list of books.
Improve search to allow partial matching.

