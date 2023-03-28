package indi.eiriksgata.rulateday.api.controller;

import indi.eiriksgata.rulateday.mapper.Dnd5ePhbDataMapper;
import indi.eiriksgata.rulateday.mapper.InfiniteLibLuMapper;
import indi.eiriksgata.rulateday.mapper.RuleBookMapper;
import indi.eiriksgata.rulateday.pojo.QueryDataBase;
import indi.eiriksgata.rulateday.pojo.RuleBook;
import indi.eiriksgata.rulateday.pojo.book.Book;
import indi.eiriksgata.rulateday.pojo.book.BookCategory;
import indi.eiriksgata.rulateday.pojo.book.BookChapter;
import indi.eiriksgata.rulateday.api.service.BookService;
import indi.eiriksgata.rulateday.api.vo.ResponseBean;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api
@RestController
@RequestMapping("/api/v1")
public class BookController {

    @Autowired
    BookService bookService;

    @Autowired
    RuleBookMapper ruleBookMapper;

    @Autowired
    Dnd5ePhbDataMapper dnd5ePhbDataMapper;

    @Autowired
    InfiniteLibLuMapper infiniteLibLuMapper;

    @GetMapping("/book/category/list")
    public ResponseBean<?> findAllBookCategoryList() {
        return ResponseBean.success(
                bookService.findAllBookCategory()
        );
    }

    @GetMapping("/book/list")
    public ResponseBean<?> findBookList() {
        return ResponseBean.success(
                bookService.findAllBook()
        );
    }

    @PutMapping("/book/chapter/title")
    public ResponseBean<?> findChapterTitle(@RequestBody Book book) {
        return ResponseBean.success(
                bookService.findAllChapterByBookId(book.getId())
        );
    }

    @PutMapping("/book/category/save")
    public ResponseBean<?> bookCategoryAdd(@RequestBody BookCategory bookCategory) {
        return ResponseBean.success(bookService.saveBookCategory(bookCategory));
    }

    @PutMapping("/book/save")
    public ResponseBean<?> bookCreate(@RequestBody Book book) {
        return ResponseBean.success(
                bookService.saveBook(book)
        );
    }

    @PutMapping("/book/chapter/id")
    public ResponseBean<?> findBookChapterById(@RequestBody BookChapter bookChapter) {
        return ResponseBean.success(
                bookService.findBookChapterById(bookChapter.getId())
        );
    }

    @DeleteMapping("/book/delete")
    public ResponseBean<?> bookDelete(@RequestBody Book book) {
        bookService.deleteBook(book.getId());
        return ResponseBean.success();
    }

    @DeleteMapping("/book/category/delete")
    public ResponseBean<?> bookCategoryDelete(@RequestBody BookCategory bookCategory) {
        bookService.deleteBookCategory(bookCategory.getId());
        return ResponseBean.success();
    }

    @DeleteMapping("/book/chapter/delete")
    public ResponseBean<?> bookChapterDelete(@RequestBody BookChapter bookChapter) {
        bookService.deleteBookChapter(bookChapter.getId());
        return ResponseBean.success();
    }

    @PutMapping("/book/chapter/save")
    public ResponseBean<?> bookChapterSave(@RequestBody BookChapter bookChapter) {
        return ResponseBean.success(bookService.saveBookChapter(bookChapter));

    }

    @PutMapping("/book/test/import/dnd5e")
    public ResponseBean<?> bookTestImportDnd5e() {
        List<QueryDataBase> result = dnd5ePhbDataMapper.selectAllArmorWeapon();
        for (QueryDataBase queryDataBase : result) {
            BookChapter bookChapter = new BookChapter();
            bookChapter.setBookId(2);
            bookChapter.setStatus("1");
            bookChapter.setTitle(queryDataBase.getName());
            bookChapter.setChapter(queryDataBase.getDescribe());
            bookService.saveBookChapter(bookChapter);
        }
        result = dnd5ePhbDataMapper.selectAllMM();
        for (QueryDataBase queryDataBase : result) {
            BookChapter bookChapter = new BookChapter();
            bookChapter.setBookId(2);
            bookChapter.setStatus("1");
            bookChapter.setTitle(queryDataBase.getName());
            bookChapter.setChapter(queryDataBase.getDescribe());
            bookService.saveBookChapter(bookChapter);
        }
        result = dnd5ePhbDataMapper.selectAllSkillPhb();
        for (QueryDataBase queryDataBase : result) {
            BookChapter bookChapter = new BookChapter();
            bookChapter.setBookId(2);
            bookChapter.setStatus("1");
            bookChapter.setTitle(queryDataBase.getName());
            bookChapter.setChapter(queryDataBase.getDescribe());
            bookService.saveBookChapter(bookChapter);
        }
        result = dnd5ePhbDataMapper.selectAllToolsPhb();
        for (QueryDataBase queryDataBase : result) {
            BookChapter bookChapter = new BookChapter();
            bookChapter.setBookId(2);
            bookChapter.setStatus("1");
            bookChapter.setTitle(queryDataBase.getName());
            bookChapter.setChapter(queryDataBase.getDescribe());
            bookService.saveBookChapter(bookChapter);
        }
        result = dnd5ePhbDataMapper.selectAllSpellListPhb();
        for (QueryDataBase queryDataBase : result) {
            BookChapter bookChapter = new BookChapter();
            bookChapter.setBookId(2);
            bookChapter.setStatus("1");
            bookChapter.setTitle(queryDataBase.getName());
            bookChapter.setChapter(queryDataBase.getDescribe());
            bookService.saveBookChapter(bookChapter);
        }

        result = dnd5ePhbDataMapper.selectAllMagicItemsDmg();
        for (QueryDataBase queryDataBase : result) {
            BookChapter bookChapter = new BookChapter();
            bookChapter.setBookId(2);
            bookChapter.setStatus("1");
            bookChapter.setTitle(queryDataBase.getName());
            bookChapter.setChapter(queryDataBase.getDescribe());
            bookService.saveBookChapter(bookChapter);
        }

        result = dnd5ePhbDataMapper.selectAllCreaturePhbDmg();
        for (QueryDataBase queryDataBase : result) {
            BookChapter bookChapter = new BookChapter();
            bookChapter.setBookId(2);
            bookChapter.setStatus("1");
            bookChapter.setTitle(queryDataBase.getName());
            bookChapter.setChapter(queryDataBase.getDescribe());
            bookService.saveBookChapter(bookChapter);
        }

        result = dnd5ePhbDataMapper.selectAllRuleDmg();
        for (QueryDataBase queryDataBase : result) {
            BookChapter bookChapter = new BookChapter();
            bookChapter.setBookId(2);
            bookChapter.setStatus("1");
            bookChapter.setTitle(queryDataBase.getName());
            bookChapter.setChapter(queryDataBase.getDescribe());
            bookService.saveBookChapter(bookChapter);
        }

        result = dnd5ePhbDataMapper.selectAllRule();
        for (QueryDataBase queryDataBase : result) {
            BookChapter bookChapter = new BookChapter();
            bookChapter.setBookId(2);
            bookChapter.setStatus("1");
            bookChapter.setTitle(queryDataBase.getName());
            bookChapter.setChapter(queryDataBase.getDescribe());
            bookService.saveBookChapter(bookChapter);
        }

        result = dnd5ePhbDataMapper.selectAllClasses();
        for (QueryDataBase queryDataBase : result) {
            BookChapter bookChapter = new BookChapter();
            bookChapter.setBookId(2);
            bookChapter.setStatus("1");
            bookChapter.setTitle(queryDataBase.getName());
            bookChapter.setChapter(queryDataBase.getDescribe());
            bookService.saveBookChapter(bookChapter);
        }

        result = dnd5ePhbDataMapper.selectAllBackgroundPhb();
        for (QueryDataBase queryDataBase : result) {
            BookChapter bookChapter = new BookChapter();
            bookChapter.setBookId(2);
            bookChapter.setStatus("1");
            bookChapter.setTitle(queryDataBase.getName());
            bookChapter.setChapter(queryDataBase.getDescribe());
            bookService.saveBookChapter(bookChapter);
        }

        result = dnd5ePhbDataMapper.selectAllFeat();
        for (QueryDataBase queryDataBase : result) {
            BookChapter bookChapter = new BookChapter();
            bookChapter.setBookId(2);
            bookChapter.setStatus("1");
            bookChapter.setTitle(queryDataBase.getName());
            bookChapter.setChapter(queryDataBase.getDescribe());
            bookService.saveBookChapter(bookChapter);
        }

        result = dnd5ePhbDataMapper.selectAllRaces();
        for (QueryDataBase queryDataBase : result) {
            BookChapter bookChapter = new BookChapter();
            bookChapter.setBookId(2);
            bookChapter.setStatus("1");
            bookChapter.setTitle(queryDataBase.getName());
            bookChapter.setChapter(queryDataBase.getDescribe());
            bookService.saveBookChapter(bookChapter);
        }


        return ResponseBean.success();
    }

    @PutMapping("/book/test/import/coc7")
    public ResponseBean<?> bookTestImportCoc7() {
        List<RuleBook> list = ruleBookMapper.selectAll();
        for (RuleBook ruleBook : list) {
            BookChapter bookChapter = new BookChapter();
            bookChapter.setBookId(1);
            bookChapter.setStatus("1");
            bookChapter.setTitle(ruleBook.getTitle());
            bookChapter.setChapter(ruleBook.getContent());
            bookService.saveBookChapter(bookChapter);
        }
        return ResponseBean.success();
    }

    @PutMapping("/book/test/import/infinite")
    public ResponseBean<?> bookTestImportInfinite() {
        List<QueryDataBase> list = infiniteLibLuMapper.selectAll();
        for (QueryDataBase queryDataBase : list) {
            BookChapter bookChapter = new BookChapter();
            bookChapter.setBookId(3);
            bookChapter.setStatus("1");
            bookChapter.setTitle(queryDataBase.getName());
            bookChapter.setChapter(queryDataBase.getDescribe());
            bookService.saveBookChapter(bookChapter);
        }
        return ResponseBean.success();
    }


}
