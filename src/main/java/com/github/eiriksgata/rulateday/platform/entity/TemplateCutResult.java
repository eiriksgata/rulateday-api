package com.github.eiriksgata.rulateday.platform.entity;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TemplateCutResult {
    private String slider;
    private String background;
    private int yHeight;
    private int xWidth;
}
