package com.poc.employees.response.model;

import com.poc.employees.model.Employee;

public class ResponsePair {
        private Employee firstEmployee;
        private Employee secondEmployee;

        public ResponsePair(Employee firstEmployee, Employee secondEmployee){
            this.firstEmployee = firstEmployee;
            this.secondEmployee = secondEmployee;
        }
        public Employee getFirstEmployee() {
            return firstEmployee;
        }

        public Employee getSecondEmployee() {
            return secondEmployee;
        }

}
