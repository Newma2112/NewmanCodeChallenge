package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReportingStructureServiceImplTest {
    private String reportingStructureUrl;
    private String employeeIdWithFour;
    private String employeeIdWithTwo;
    private String employeeIdWithZero;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setup() {
        reportingStructureUrl = "http://localhost:" + port + "/reportingStructure/{id}";
        employeeIdWithFour = "16a596ae-edd3-4847-99fe-c4518e82c86f";
        employeeIdWithTwo = "03aa1462-ffa9-4978-901b-7c001562cf6f";
        employeeIdWithZero = "b7839309-3348-463b-a7e3-5de1c168beb3";
    }

    @Test
    public void testBuild(){
        ReportingStructure reportingStructure = restTemplate.getForEntity(reportingStructureUrl, ReportingStructure.class, employeeIdWithFour).getBody();
        assertEquals(4, reportingStructure.getNumberOfReports());

        reportingStructure = restTemplate.getForEntity(reportingStructureUrl, ReportingStructure.class, employeeIdWithTwo).getBody();
        assertEquals(2, reportingStructure.getNumberOfReports());

        reportingStructure = restTemplate.getForEntity(reportingStructureUrl, ReportingStructure.class, employeeIdWithZero).getBody();
        assertEquals(0, reportingStructure.getNumberOfReports());
    }

    @Test
    public void testReportingStructureFillOut(){
        ReportingStructure reportingStructure = restTemplate.getForEntity(reportingStructureUrl, ReportingStructure.class, employeeIdWithFour).getBody();

        Employee topEmployee = reportingStructure.getEmployee();

        Employee directReport1 = topEmployee.getDirectReports().get(0);
        assertEquals("McCartney", directReport1.getLastName());

        Employee directReport2 = topEmployee.getDirectReports().get(1);
        assertEquals("Starr", directReport2.getLastName());
    }
}
