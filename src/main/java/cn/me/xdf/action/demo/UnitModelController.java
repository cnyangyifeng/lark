package cn.me.xdf.action.demo;

import cn.me.xdf.common.web.RequestUtils;
import cn.me.xdf.common.web.springmvc.annotation.FormBean;
import cn.me.xdf.model.demo.UnitModel;
import cn.me.xdf.service.base.AttMainService;
import cn.me.xdf.service.demo.UnitModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: xiaobin268
 * Date: 13-10-23
 * Time: 上午9:54
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping(value = "/demo/unit")
public class UnitModelController {

    public void queryUnit() {
        System.out.println("查询所有的Unit------------------------------");
    }

    @Autowired
    private UnitModelService unitModelService;

    @Autowired
    private AttMainService attMainService;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String init() {
        unitModelService.saveTest();
        return "/demo/unit/index";
    }

    /*
        public String save2(@FormBean("bean1") bEAN1 B,@FormBean("bean3") bEAN3 B2) {

        }*/
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(NativeWebRequest request) {
       /* UnitModel unitModel = cn.me.xdf.common.web.
        unitModelService.save(unitModel);
        attMainService.saveAttMainMachine(unitModel);*/
        UnitModel unitModel = new UnitModel();
        RequestUtils.getBeanByAttr(request, "unit", unitModel);
        System.out.println("unitModel=" + unitModel.getFdName());
        return "redirect:/demo/unit/index";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String edit(Model model, @PathVariable("id") String id) {
        UnitModel unitModel = unitModelService.get(UnitModel.class, id);
        //attMainService.convertModelAttMain(unitModel);
        model.addAttribute("bean", unitModel);
        return "/demo/unit/edit";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(UnitModel unitModel) {
        unitModelService.save(unitModel);
        //attMainService.updateAttMainMachine(unitModel);
        return "redirect:/demo/unit/index";
    }
}
