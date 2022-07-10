package de.flozo.cvgen;

import de.flozo.db.Datasource;

public class Main {

    public static void main(String[] args) {

        Datasource.getInstance().open();
        try {
            System.out.println(Datasource.getInstance().queryTextFormatById(1));
            System.out.println(Datasource.getInstance().queryTextStyleById(2));
            System.out.println(Datasource.getInstance().queryAnchorById(4));
            System.out.println(Datasource.getInstance().queryLineStyleById(2));
            System.out.println("*********");
            System.out.println(Datasource.getInstance().queryElementById(1));
            System.out.println(Datasource.getInstance().queryElementById(2));
            System.out.println(Datasource.getInstance().queryElementById(3));

        } finally {
            Datasource.getInstance().close();
        }

    }
}
