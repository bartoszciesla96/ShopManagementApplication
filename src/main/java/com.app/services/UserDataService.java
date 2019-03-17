package com.app.services;

import com.app.exceptions.MyException;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Scanner;

public class UserDataService {
    private Scanner scanner = new Scanner(System.in);

    public String getString(String message) {
        System.out.println(message);
        return scanner.nextLine();
    }

    public int getInt(String message) {
        System.out.println(message);

        String text = scanner.nextLine();
        if (!text.matches("\\d+")) {
            throw new MyException("INT VALUE IS NOT CORRECT");
        }
        return Integer.parseInt(text);
    }

    public BigDecimal getBigDecimal(String message) {
        System.out.println(message);

        String text = scanner.nextLine();
        if(!text.matches("\\d+")) {
            throw new MyException("VALUE IS NOT A NUMBER");
        }
        DecimalFormat df = new DecimalFormat();
        df.setParseBigDecimal(true);
        try {
            return (BigDecimal) df.parse(text);
        }
        catch (Exception e) {
            throw new MyException("BIGDECIMAL IS NOT CORRECT");
        }
    }

    public void close() {
        if (scanner != null) {
            scanner.close();
            scanner = null;
        }
    }
}
