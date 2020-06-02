package com.employee.employee.controller;

import com.employee.employee.model.Employee;
import com.employee.employee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class EmployeeController {
    private EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/")
    public String viewHome(Model model) {
        //model.addAttribute("employeeList", employeeService.getAllEmployees());
        return findPaginated(1, model);
    }

    @GetMapping(path = "/newEmployeeForm")
    public String newEmployeeForm(Model model) {
        Employee employee = new Employee();
        model.addAttribute("employee", employee);
        return "newEmployee";
    }

    @PostMapping(path = "/saveEmployee")
    public String saveEmployee(@ModelAttribute("employee") Employee employee) {
        employeeService.createEmployee(employee);
        return "redirect:/";
    }

    @GetMapping(path = "/employeeEditForm/{id}")
    public String editEmployeeForm(@PathVariable(value = "id") Long id, Model model) {
        Employee employee = employeeService.getEmployeeById(id);
        model.addAttribute("employee", employee);
        return "editEmployee";
    }

    @GetMapping(path = "/deleteEmployee/{id}")
    public String deleteEmployee(@PathVariable(value = "id") Long id) {
        employeeService.deleteEmployee(id);
        return "redirect:/";
    }

    @GetMapping("/page/{pageNumber}")
    public String findPaginated(@PathVariable(value = "pageNumber") int pageNumber, Model model) {
        int pageSize = 5;

        Page< Employee > page = employeeService.findAllWithPagination(pageNumber, pageSize);
        List< Employee > employeesList = page.getContent();

        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalElements", page.getTotalElements());
        model.addAttribute("employeesList", employeesList);
        return "home";
    }
}
