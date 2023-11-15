package com.github.eiriksgata.rulateday.platform.entity;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TemplateCutResult {
    private String slider;
    private String background;
    private int height = 0;
    private int width = 0;
    private int x = 0;
    private int y = 0;
}
