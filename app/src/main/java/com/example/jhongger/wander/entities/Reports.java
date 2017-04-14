package com.example.jhongger.wander.entities;

import java.util.List;




    public class Reports {
        List<Report> reports;
        public Reports(List<Report> reports) {
            this.reports = reports;
        }

    public List<Report> getReports() {
        return reports;
    }

    public void setReports(List<Report> reports) {
        this.reports = reports;
    }
}

