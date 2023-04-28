package uz.devops.templating4j;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

public class Dummy {

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class Student implements Serializable {
        String name;
        String surname;
        String phone;
        Subject subject;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class Subject implements Serializable {
        String name;
        Integer score;
    }

}
