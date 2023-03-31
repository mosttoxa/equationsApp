package com.mosttoxa;

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

    @Indexed(unique = false)
    @Field(targetType = FieldType.STRING, write = Field.Write.NON_NULL)
    private String result;

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

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Equation equation1 = (Equation) o;
        return id.equals(equation1.id) && equation.equals(equation1.equation) && result.equals(equation1.result);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, equation, result);
    }

    @Override
    public String toString() {
        return "Equation{" +
                "id='" + id + '\'' +
                ", equation='" + equation + '\'' +
                ", result='" + result + '\'' +
                '}';
    }
}
