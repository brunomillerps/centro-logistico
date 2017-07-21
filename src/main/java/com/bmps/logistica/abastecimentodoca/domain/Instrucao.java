package com.bmps.logistica.abastecimentodoca.domain;

import org.apache.commons.lang3.builder.RecursiveToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Instrucao {

    public static final String ABASTECIMENTO = "Abastecimento";
    public static final String TRANSFERENCIA = "Transferência";
    public static final String CAMINHAO = "Caminhão";

    private Long step;
    private Long packageId;
    private String from;
    private String to;

    public Instrucao(Long step, Long packageId, String from, String to) {
        this.step = step;
        this.packageId = packageId;
        this.from = (from.equals(ABASTECIMENTO) || from.equals(TRANSFERENCIA)? String.format("Zona de %s", from): String.format("Zona do %s", from));
        this.to = (to.equals(ABASTECIMENTO) || to.equals(TRANSFERENCIA)? String.format("Zona de %s", to): String.format("Zona do %s", to));
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

    @Override
    public String toString() {
        Object object = this;
        return ToStringBuilder.reflectionToString(object,getToStringStyle());
    }

    protected ToStringStyle getToStringStyle() {
        return new RecursiveToStringStyle();
    }
}
