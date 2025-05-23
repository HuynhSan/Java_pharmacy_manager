/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pharmacy.app.GUI.WorkSchedule;

import com.pharmacy.app.BUS.RequestBUS;
import com.pharmacy.app.DAO.AttendanceDAO;
import com.pharmacy.app.DAO.ContractDAO;
import com.pharmacy.app.DAO.SalaryComponentsDAO;
import com.pharmacy.app.DAO.WorkSchedulesDAO;
import com.pharmacy.app.DTO.AttendanceDTO;
import com.pharmacy.app.DTO.ContractDTO;
import com.pharmacy.app.DTO.PayrollDetailsDTO;
import com.pharmacy.app.DTO.WorkScheduleDTO;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author phong
 */
public class SalaryCalculationService {
    private ContractDAO contractDAO;
    private AttendanceDAO attendanceDAO;
    private WorkSchedulesDAO workScheduleDAO;
    private SalaryComponentsDAO salaryComponentDAO;
    
    public SalaryCalculationService() {
        this.contractDAO = new ContractDAO();
        this.attendanceDAO = new AttendanceDAO();
        this.workScheduleDAO = new WorkSchedulesDAO();
        this.salaryComponentDAO = new SalaryComponentsDAO();
    }
    
    public List<PayrollDetailsDTO> calculateSalary(String employeeID, String payrollID, YearMonth month) {
        List<PayrollDetailsDTO> payrollDetails = new ArrayList<>();
        
        // Get employee contract for base salary
        ContractDTO contract = contractDAO.getCurrentContractByEmployeeID(employeeID);
        if (contract == null) {
            throw new RuntimeException("No active contract found for employee: " + employeeID);
        }
        
        BigDecimal baseSalary = contract.getBaseSalary();
        BigDecimal hourlyRate = baseSalary.divide(BigDecimal.valueOf(26 * 8), 2, RoundingMode.HALF_UP);
        
        // Get attendance and schedule data for the month
        List<AttendanceDTO> attendances = attendanceDAO.getAttendanceByEmployeeAndMonth(employeeID, month);
        List<WorkScheduleDTO> schedules = workScheduleDAO.getScheduleByEmployeeAndMonth(employeeID, month);
        
        // SC01: Actual basic salary
        int actualWorkDays = attendances.size();
        BigDecimal actualBasicSalary = baseSalary.divide(BigDecimal.valueOf(26), 2, RoundingMode.HALF_UP)
                                                 .multiply(BigDecimal.valueOf(actualWorkDays));
        payrollDetails.add(new PayrollDetailsDTO(payrollID, "SC01", actualWorkDays, actualBasicSalary));
        
        // SC02: Overtime allowance on weekdays (150%)
        double weekdayOvertimeHours = calculateWeekdayOvertimeHours(attendances, schedules);
        BigDecimal weekdayOvertimeAmount = hourlyRate.multiply(BigDecimal.valueOf(1.5))
                                                    .multiply(BigDecimal.valueOf(weekdayOvertimeHours));
        payrollDetails.add(new PayrollDetailsDTO(payrollID, "SC02", (int)weekdayOvertimeHours, weekdayOvertimeAmount));
        
        // SC03: Overtime allowance on weekends (200%)
        double weekendOvertimeHours = calculateWeekendOvertimeHours(attendances, schedules);
        BigDecimal weekendOvertimeAmount = hourlyRate.multiply(BigDecimal.valueOf(2.0))
                                                    .multiply(BigDecimal.valueOf(weekendOvertimeHours));
        payrollDetails.add(new PayrollDetailsDTO(payrollID, "SC03", (int)weekendOvertimeHours, weekendOvertimeAmount));
        
        // SC04: Overtime allowance on holidays (300%)
        double holidayOvertimeHours = calculateHolidayOvertimeHours(attendances, schedules);
        BigDecimal holidayOvertimeAmount = hourlyRate.multiply(BigDecimal.valueOf(3.0))
                                                    .multiply(BigDecimal.valueOf(holidayOvertimeHours));
        payrollDetails.add(new PayrollDetailsDTO(payrollID, "SC04", (int)holidayOvertimeHours, holidayOvertimeAmount));
        
        // SC05: Travel allowance (Fixed 200,000 VND)
        BigDecimal travelAllowance = new BigDecimal("200000");
        payrollDetails.add(new PayrollDetailsDTO(payrollID, "SC05", 1, travelAllowance));
        
        // SC06: Performance bonus (0 for now - can be manually adjusted)
        payrollDetails.add(new PayrollDetailsDTO(payrollID, "SC06", 0, BigDecimal.ZERO));
        
        // SC07: Deduction for unauthorized leave
        int unauthorizedLeaveDays = calculateUnauthorizedLeaveDays(employeeID, month, actualWorkDays);
        BigDecimal unauthorizedLeaveDeduction = baseSalary.divide(BigDecimal.valueOf(26), 2, RoundingMode.HALF_UP)
                                                         .multiply(BigDecimal.valueOf(unauthorizedLeaveDays))
                                                         .negate(); // Negative for deduction
        payrollDetails.add(new PayrollDetailsDTO(payrollID, "SC07", unauthorizedLeaveDays, unauthorizedLeaveDeduction));
        
        // SC08: Deduction for late shift
        int lateCount = calculateLateCount(attendances);
        BigDecimal lateDeduction = BigDecimal.valueOf(20000).multiply(BigDecimal.valueOf(lateCount)).negate();
        payrollDetails.add(new PayrollDetailsDTO(payrollID, "SC08", lateCount, lateDeduction));
        
        // SC09: Personal income tax deduction
        BigDecimal totalBeforeTax = payrollDetails.stream()
                                                 .filter(detail -> !detail.getComponentID().equals("SC09"))
                                                 .map(PayrollDetailsDTO::getAmount)
                                                 .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal taxDeduction = calculateTaxDeduction(totalBeforeTax);
        payrollDetails.add(new PayrollDetailsDTO(payrollID, "SC09", 
                          taxDeduction.compareTo(BigDecimal.ZERO) < 0 ? 1 : 0, taxDeduction));
        
        return payrollDetails;
    }
    
    private double calculateWeekdayOvertimeHours(List<AttendanceDTO> attendances, List<WorkScheduleDTO> schedules) {
        double totalOvertimeHours = 0;
        for (AttendanceDTO attendance : attendances) {
            WorkScheduleDTO schedule = findScheduleForDate(schedules, attendance.getWorkDate());
            if ((schedule != null && schedule.isIsWeekend() && !schedule.isIsHoliday())) {
                double overtimeHours = calculateOvertimeForDay(attendance, schedule);
                totalOvertimeHours += overtimeHours;
            }
        }
        return totalOvertimeHours;
    }
    
    private double calculateWeekendOvertimeHours(List<AttendanceDTO> attendances, List<WorkScheduleDTO> schedules) {
        double totalOvertimeHours = 0;
        for (AttendanceDTO attendance : attendances) {
            WorkScheduleDTO schedule = findScheduleForDate(schedules, attendance.getWorkDate());
            if (schedule != null && schedule.isIsWeekend() && !schedule.isIsHoliday()) {
                double overtimeHours = calculateOvertimeForDay(attendance, schedule);
                totalOvertimeHours += overtimeHours;
            }
        }
        return totalOvertimeHours;
    }
    
    private double calculateHolidayOvertimeHours(List<AttendanceDTO> attendances, List<WorkScheduleDTO> schedules) {
        double totalOvertimeHours = 0;
        for (AttendanceDTO attendance : attendances) {
            WorkScheduleDTO schedule = findScheduleForDate(schedules, attendance.getWorkDate());
            if (schedule != null && schedule.isIsHoliday()) {
                double overtimeHours = calculateOvertimeForDay(attendance, schedule);
                totalOvertimeHours += overtimeHours;
            }
        }
        return totalOvertimeHours;
    }
    
    private WorkScheduleDTO findScheduleForDate(List<WorkScheduleDTO> schedules, LocalDate date) {
        return schedules.stream()
                       .filter(schedule -> schedule.getWorkDate().equals(date))
                       .findFirst()
                       .orElse(null);
    }
    
    private double calculateOvertimeForDay(AttendanceDTO attendance, WorkScheduleDTO schedule) {
        // Assuming 8-hour standard work day
        // Calculate actual work hours and subtract 8 to get overtime
        // This is a simplified calculation - you may need to adjust based on your business logic
        if ("OFF".equals(schedule.getShiftId())) {
            return Math.max(0, attendance.getActualWorkHours());
        }
        else {
            return Math.max(0, attendance.getActualWorkHours() - 8);
        }
    }
    
    private int calculateUnauthorizedLeaveDays(String employeeID, YearMonth month, int actualWorkDays) {
        // Calculate expected work days in the month (exclude weekends and holidays)
        int expectedWorkDays = calculateExpectedWorkDays(month);
        RequestBUS req = new RequestBUS();
        return Math.max(0, expectedWorkDays - actualWorkDays - req.getCountRequestNonSalary(employeeID));
    }
    
    private int calculateExpectedWorkDays(YearMonth month) {
        // This should calculate working days excluding weekends and holidays
        // Simplified to 26 days for now
        return 26;
    }
    
    private int calculateLateCount(List<AttendanceDTO> attendances) {
        int lateCount = 0;
        for (AttendanceDTO attendance : attendances) {
            if (attendance.isLate()) { // You'll need to implement this method in AttendanceDTO
                lateCount++;
            }
        }
        return lateCount;
    }
    
    private BigDecimal calculateTaxDeduction(BigDecimal totalSalary) {
        BigDecimal taxThreshold = new BigDecimal("11000000");
        if (totalSalary.compareTo(taxThreshold) > 0) {
            // 5% tax rate for simplicity
            BigDecimal taxableAmount = totalSalary.subtract(taxThreshold);
            return taxableAmount.multiply(new BigDecimal("0.05")).negate();
        }
        return BigDecimal.ZERO;
    }
}
