package com.bmps.logistica.abastecimentodoca.domain;

public class Instrucao {

    private Long step;
    private Long packageId;
    private String from;
    private String to;

    public Instrucao(Long step, Long packageId, String from, String to) {
        this.step = step;
        this.packageId = packageId;
        this.from = from;
        this.to = to;
    }

    public Long getStep() {
        return step;
    }

    public void setStep(Long step) {
        this.step = step;
    }

    public Long getPackageId() {
        return packageId;
    }

    public void setPackageId(Long packageId) {
        this.packageId = packageId;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
