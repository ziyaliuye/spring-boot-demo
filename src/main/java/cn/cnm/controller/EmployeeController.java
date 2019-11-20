package cn.cnm.controller;

import cn.cnm.pojo.Employee;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.Collection;

/**
 * @author lele
 * @version 1.0
 * @Description 使用RestFul风格做员工的CRUD
 * @Email 414955507@qq.com
 * @date 2019/11/18 22:28
 */
@Controller
public class EmployeeController {
    // 查询所有员工并返回, 写上资源路径然后指定请求方式
    @GetMapping("/emps")
    public String list(Model model) {
        Collection<Employee> employees = null;
        model.addAttribute("emps", employees);
        /* 会自动解析并寻找静态资源文件夹下的对应文件夹的资源 */
        return "emp/list";
    }

    // 员工添加， 添加完后重定向到查询页面
    public String addEmpo(Employee employee) {
        /* 调用保存员工信息的service方法 */
        System.out.println(employee);
        // forward：请求转发， redirect：重定向请求, 在重定向时， /代表项目根目录（和jsp一致）
        return "redirect:emps";
    }

    // 查询出当前员工信息， 跳转到员工修改页面， 并在页面上回显员工信息
    public String toEditPage(@PathVariable("id") Integer id, Model model) {
        // 查询出员工信息
        // Employee employee = employeeService.get(id);
        // 将员工放入model传入到新页面准备回显
        model.addAttribute("emp", null);
        // 回到修改页面（add是一个修改和添加功能二合一的页面）
        return "emp/add";
    }

    // 员工删除
    public String deleteEmployee(@PathVariable("id") Integer id) {
        // 删除员工操作
        // employeeService.delete(id);
        return "redirect:/emps";
    }
}
