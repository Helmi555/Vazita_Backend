package com.example.vehicleinspection.dto.request;

import java.util.ArrayList;
import java.util.List;

public class DossierDefautsRequest {

    private List<String> codeDefauts=new ArrayList<>();

    public List<String> getCodeDefauts() {
        return codeDefauts;
    }

    public void setCodeDefauts(List<String> codeDefauts) {
        this.codeDefauts = codeDefauts;
    }


    public DossierDefautsRequest(List<String> codeDefauts) {

        this.codeDefauts = codeDefauts;
    }

    public DossierDefautsRequest() {
    }

    @Override
    public String toString() {
        return "DossierDefautsRequest{" +
                "codeDefauts=" + codeDefauts +
                '}';
    }
}
