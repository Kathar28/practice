package com.app.formapplication.documents;

public enum DocumentType {
    WAYBILL("Накладная"),
    PAYMENT("Платёжка"),
    BID("Заявка на оплату");

    DocumentType(String typeName){
        this.typeName = typeName;
    }

    private final String typeName;

    public String getTypeName() {
        return typeName;
    }
}
