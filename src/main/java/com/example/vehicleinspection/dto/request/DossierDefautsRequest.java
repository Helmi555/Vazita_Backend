package com.example.vehicleinspection.dto.request;

import java.util.ArrayList;
import java.util.List;

public class DossierDefautsRequest {

    private List<Integer> codeAlterations=new ArrayList<>();
    private List<String> codeDefauts=new ArrayList<>();

    public List<String> getCodeDefauts() {
        return codeDefauts;
    }

    public void setCodeDefauts(List<String> codeDefauts) {
        this.codeDefauts = codeDefauts;
    }

    public List<Integer> getCodeAlterations() {
        return codeAlterations;
    }

    public void setCodeAlterations(List<Integer> codeAlterations) {
        this.codeAlterations = codeAlterations;
    }

    public DossierDefautsRequest(List<Integer> codeAlterations, List<String> codeDefauts) {
        this.codeAlterations = codeAlterations;
        this.codeDefauts = codeDefauts;
    }

    public DossierDefautsRequest() {
    }
}
