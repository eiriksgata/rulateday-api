package indi.eiriksgata.rulateday.api.service;

import indi.eiriksgata.rulateday.api.exception.CommonBaseException;
import indi.eiriksgata.rulateday.api.pojo.book.Book;
import indi.eiriksgata.rulateday.api.pojo.book.BookCategory;
import indi.eiriksgata.rulateday.api.pojo.book.BookChapter;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BookService {
    List<BookCategory> findAllBookCategory();

    List<Book> findAllBook();

    List<BookChapter> findAllChapterByBookId(Integer bookId);

    @Transactional(rollbackFor = CommonBaseException.class)
    BookCategory saveBookCategory(BookCategory bookCategory);

    @Transactional(rollbackFor = CommonBaseException.class)
    BookCategory addBookCategory(BookCategory bookCategory);

    @Transactional(rollbackFor = CommonBaseException.class)
    BookCategory updatedBookCategory(BookCategory bookCategory);


    @Transactional(rollbackFor = CommonBaseException.class)
    Book saveBook(Book book);

    @Transactional(rollbackFor = CommonBaseException.class)
    Book addBook(Book book);

    @Transactional(rollbackFor = CommonBaseException.class)
    Book updateBook(Book book);

    @Transactional(rollbackFor = CommonBaseException.class)
    void deleteBook(Integer id);

    @Transactional(rollbackFor = CommonBaseException.class)
    void deleteBookCategory(Integer bookCategoryId);

    @Transactional(rollbackFor = CommonBaseException.class)
    void deleteBookChapter(int bookChapterId);

    @Transactional(rollbackFor = CommonBaseException.class)
    BookChapter saveBookChapter(BookChapter bookChapter);

    @Transactional(rollbackFor = CommonBaseException.class)
    BookChapter addBookChapter(BookChapter bookChapter);

    @Transactional(rollbackFor = CommonBaseException.class)
    BookChapter updateBookChapter(BookChapter bookChapter);

    BookChapter findBookChapterById(Integer chapterId);
}
