package org.example;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.io.Serializable;
import java.util.Objects;

public class Equation implements Serializable {

    private static final long serialVersionUID = Equation.class.getName().hashCode();



    @Id
    @MongoId
    private String id;

    @Indexed(unique = true)
    @Field(targetType = FieldType.STRING, write = Field.Write.NON_NULL)
    private String equation;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEquation() {
        return equation;
    }

    public void setEquation(String equation) {
        this.equation = equation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Equation equation1 = (Equation) o;
        return id.equals(equation1.id) && equation.equals(equation1.equation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, equation);
    }

    @Override
    public String toString() {
        return "Equation{" +
                "id='" + id + '\'' +
                ", equation='" + equation + '\'' +
                '}';
    }
}
