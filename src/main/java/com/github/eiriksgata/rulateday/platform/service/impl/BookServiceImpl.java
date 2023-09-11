package com.github.eiriksgata.rulateday.platform.service.impl;

import com.github.eiriksgata.rulateday.platform.exception.CommonBaseException;
import com.github.eiriksgata.rulateday.platform.service.BookService;
import com.github.eiriksgata.rulateday.platform.mapper.BookCategoryMapper;
import com.github.eiriksgata.rulateday.platform.mapper.BookChapterMapper;
import com.github.eiriksgata.rulateday.platform.mapper.BookListMapper;
import com.github.eiriksgata.rulateday.platform.pojo.book.Book;
import com.github.eiriksgata.rulateday.platform.pojo.book.BookCategory;
import com.github.eiriksgata.rulateday.platform.pojo.book.BookChapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {


    @Autowired
    BookCategoryMapper bookCategoryMapper;

    @Autowired
    BookChapterMapper bookChapterMapper;

    @Autowired
    BookListMapper bookListMapper;


    @Override
    public List<BookCategory> findAllBookCategory() {
        return bookCategoryMapper.selectAllBookCategory();
    }


    @Override
    public List<Book> findAllBook() {
        return bookListMapper.selectAllBook();
    }


    @Override
    public List<BookChapter> findAllChapterByBookId(Integer bookId) {
        return bookChapterMapper.selectChapterTitleByBookId(bookId);
    }

    @Override
    @Transactional(rollbackFor = CommonBaseException.class)
    public BookCategory saveBookCategory(BookCategory bookCategory) {
        if (bookCategory.getId() == null || bookCategory.getId() == -1) {
            return addBookCategory(bookCategory);
        }

        BookCategory temp = bookCategoryMapper.selectById(bookCategory.getId());
        if (temp == null) {
            return addBookCategory(bookCategory);
        } else {
            temp.setTitle(bookCategory.getTitle());
            temp.setIcon(bookCategory.getIcon());
            temp.setMetaDescription(bookCategory.getMetaDescription());
            temp.setMetaKeywords(bookCategory.getMetaKeywords());
            return updatedBookCategory(temp);
        }
    }

    @Override
    @Transactional(rollbackFor = CommonBaseException.class)
    public BookCategory addBookCategory(BookCategory bookCategory) {
        bookCategory.setId(null);
        bookCategory.setCreatedAt(new Date());
        bookCategory.setUpdatedAt(new Date());
        bookCategory.setPid(0);
        bookCategoryMapper.insert(bookCategory);
        return bookCategory;
    }

    @Override
    @Transactional(rollbackFor = CommonBaseException.class)
    public BookCategory updatedBookCategory(BookCategory bookCategory) {
        bookCategoryMapper.updateById(bookCategory);
        return bookCategory;
    }

    @Override
    @Transactional(rollbackFor = CommonBaseException.class)
    public Book saveBook(Book book) {
        if (book.getId() == null || book.getId() == -1) {
            return addBook(book);
        }
        Book temp = bookListMapper.selectById(book.getId());
        if (temp == null) {
            return addBook(book);
        } else {
            temp.setTitle(book.getTitle());
            temp.setAuthor(book.getAuthor());
            temp.setContent(book.getContent());
            temp.setCategoryId(book.getCategoryId());
            temp.setPicture(book.getPicture());
            return updateBook(temp);
        }
    }

    @Override
    @Transactional(rollbackFor = CommonBaseException.class)
    public Book addBook(Book book) {
        book.setId(null);
        book.setCreatedAt(new Date());
        book.setUpdatedAt(new Date());
        bookListMapper.insert(book);
        return book;
    }

    @Override
    @Transactional(rollbackFor = CommonBaseException.class)
    public Book updateBook(Book book) {
        bookListMapper.updateById(book);
        return book;
    }

    @Override
    @Transactional(rollbackFor = CommonBaseException.class)
    public void deleteBook(Integer bookId) {
        bookListMapper.deleteById(bookId);
    }

    @Override
    @Transactional(rollbackFor = CommonBaseException.class)
    public void deleteBookCategory(Integer bookCategoryId) {
        bookListMapper.deleteById(bookCategoryId);
    }

    @Override
    @Transactional(rollbackFor = CommonBaseException.class)
    public void deleteBookChapter(int bookChapterId) {
        bookChapterMapper.deleteById(bookChapterId);
    }

    @Override
    @Transactional(rollbackFor = CommonBaseException.class)
    public BookChapter saveBookChapter(BookChapter bookChapter) {

        if (bookChapter.getId() == null || bookChapter.getId() == -1) {
            return addBookChapter(bookChapter);
        }

        BookChapter temp = bookChapterMapper.selectById(bookChapter.getId());
        if (temp == null) {
            return addBookChapter(bookChapter);
        } else {
            temp.setTitle(bookChapter.getTitle());
            temp.setChapter(bookChapter.getChapter());
            temp.setBookId(bookChapter.getBookId());
            temp.setSource(bookChapter.getSource());
            temp.setStatus(bookChapter.getStatus());
            return updateBookChapter(temp);
        }

    }

    @Override
    @Transactional(rollbackFor = CommonBaseException.class)
    public BookChapter addBookChapter(BookChapter bookChapter) {
        bookChapter.setId(null);
        bookChapter.setCreatedAt(new Date());
        bookChapter.setUpdatedAt(new Date());
        bookChapterMapper.insert(bookChapter);
        return bookChapter;
    }


    @Override
    @Transactional(rollbackFor = CommonBaseException.class)
    public BookChapter updateBookChapter(BookChapter bookChapter) {
        bookChapterMapper.updateById(bookChapter);
        return bookChapter;
    }

    @Override
    public BookChapter findBookChapterById(Integer chapterId) {
        return bookChapterMapper.selectById(chapterId);
    }

}
