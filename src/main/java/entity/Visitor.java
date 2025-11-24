package entity;

import java.util.Scanner;

public class Visitor extends Person {

    Visitor(String ime,String oib)
    {
        super(ime,oib);
    }

    @Override
    public void basicInformation() {
        System.out.println("Posjetitelj:" + super.getName() + super.getOIB());
    }
}
