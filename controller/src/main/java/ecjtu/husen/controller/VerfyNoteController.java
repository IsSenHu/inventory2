package ecjtu.husen.controller;

import ecjtu.husen.model.OperationNote;
import ecjtu.husen.service.VerfyNoteService;
import ecjtu.husen.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * RequiresPermissions:shiro的注解，必须拥有指定的权限才能操作这些方法
 * @author 11785
 */
@Controller
public class VerfyNoteController {
    @Autowired
    private VerfyNoteService verfyNoteService;

    /**
     * 分页显示所有的入库操作记录
     * @param currentPage
     * @return
     */
    @RequestMapping("/pageNote.action")
    private ModelAndView pageNote(@RequestParam(defaultValue = "1") Integer currentPage){
        Page<OperationNote> page = verfyNoteService.page(currentPage);
        return new ModelAndView("views/jsp/note")
                .addObject(page);
    }

    /**
     * 分页显示所有的出库操作记录
     * @param currentPage
     * @return
     */
    @RequestMapping("/pageNote2.action")
    private ModelAndView pageNote2(@RequestParam(defaultValue = "1") Integer currentPage){
        Page<OperationNote> page = verfyNoteService.page2(currentPage);
        return new ModelAndView("views/jsp/note2")
                .addObject(page);
    }

    /**
     * 分页显示所有的发货操作记录
     * @param currentPage
     * @return
     */
    @RequestMapping("/pageNote3.action")
    private ModelAndView pageNote3(@RequestParam(defaultValue = "1") Integer currentPage){
        Page<OperationNote> page = verfyNoteService.page3(currentPage);
        return new ModelAndView("views/jsp/note3")
                .addObject(page);
    }
}
