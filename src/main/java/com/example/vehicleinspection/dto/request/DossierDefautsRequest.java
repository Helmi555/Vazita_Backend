package com.example.vehicleinspection.dto.request;

import java.util.ArrayList;
import java.util.List;

public class DossierDefautsRequest {

    private List<Integer> codeAlterations=new ArrayList<>();

    public List<Integer> getCodeAlterations() {
        return codeAlterations;
    }

    public void setCodeAlterations(List<Integer> codeAlterations) {
        this.codeAlterations = codeAlterations;
    }

    public DossierDefautsRequest(List<Integer> codeAlterations) {
        this.codeAlterations = codeAlterations;
    }

    public DossierDefautsRequest() {
    }
}
