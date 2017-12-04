package com.youe.yc.spiderclient.template.entity;

public abstract class Expression {
    private String expression;

    public Expression() {
    }

    public Expression(String expression) {
        this.expression = expression;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }
}
