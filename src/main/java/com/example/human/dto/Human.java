package com.example.human.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;




@Data
public class Human {

    @Schema(description = "Scehma -id",example = "111111")
    private Long id;

    @Schema(description = "Schema - name",example = "Yossi Tal")
    private String name;

    @Schema(description = "Schema - age",example = "43")
    private int age;

    public Human() {
    }

    public Human(Long id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public Human(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public void setAll(Long id, String name, int age){
        this.id = id;
        this.name = name;
        this.age = age;
    }



}

