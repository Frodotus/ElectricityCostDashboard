package com.vesanieminen.froniusvisualizer.components;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.theme.lumo.LumoUtility;

public class DoubleLabel extends Div {
    private final Span spanTop;
    private final Span spanBottom;

    public DoubleLabel(String titleTop, String titleBottom) {
        spanTop = new Span(titleTop);
        spanTop.addClassNames(LumoUtility.FontSize.LARGE);
        spanBottom = new Span(titleBottom);
        spanBottom.addClassNames(LumoUtility.FontSize.LARGE);
        add(spanTop, spanBottom);
        addClassNames(LumoUtility.Display.FLEX, LumoUtility.FlexDirection.COLUMN, LumoUtility.Flex.GROW, LumoUtility.AlignItems.CENTER);
    }

    public Span getSpanTop() {
        return spanTop;
    }

    public Span getSpanBottom() {
        return spanBottom;
    }


    public void setTitleTop(String title) {
        spanTop.setText(title);
    }

    public void setTitleBottom(String title) {
        spanBottom.setText(title);
    }

    public void addClassNamesToSpans(String... classNames) {
        spanTop.addClassNames(classNames);
        spanBottom.addClassNames(classNames);
    }

}
