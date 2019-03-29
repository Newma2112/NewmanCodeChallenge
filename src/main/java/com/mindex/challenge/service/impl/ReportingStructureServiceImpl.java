package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.EmployeeService;
import com.mindex.challenge.service.ReportingStructureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReportingStructureServiceImpl implements ReportingStructureService{
    private static final Logger LOG = LoggerFactory.getLogger(ReportingStructureServiceImpl.class);

    @Autowired
    private EmployeeService employeeService;

    @Override
    public ReportingStructure build(String id){
        LOG.debug("Building Report Structure for employee with id [{}]", id);

        ReportingStructure reportingStructure = new ReportingStructure();

        Employee employee = employeeService.read(id);

        reportingStructure.setEmployee(employee);

        reportingStructure.setNumberOfReports(calculateNumberOfReports(employee));

        return reportingStructure;
    }

    /**
     * Calculates the number of direct reports to the employee and
     * populates employee object with direct reports complete data.
     * @param employee
     * @return number of reports
     */
    private int calculateNumberOfReports(Employee employee){
        int numberOfReports = 0;

        if (employee.getDirectReports() != null){
            List<Employee> filledOutDirectReports = new ArrayList<>();
            numberOfReports = employee.getDirectReports().size();

            for(Employee directReport: employee.getDirectReports()){
                directReport = employeeService.read(directReport.getEmployeeId());
                numberOfReports += calculateNumberOfReports(directReport);
                filledOutDirectReports.add(directReport);
            }

            employee.setDirectReports(filledOutDirectReports);
        }

        return numberOfReports;
    }
}
