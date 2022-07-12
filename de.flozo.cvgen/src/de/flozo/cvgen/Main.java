package de.flozo.cvgen;

import de.flozo.common.appearance.Element;
import de.flozo.common.appearance.Line;
import de.flozo.common.content.Address;
import de.flozo.common.content.Enclosure;
import de.flozo.db.Datasource;

public class Main {

    public static void main(String[] args) {

        Datasource.getInstance().open();
        try {
            System.out.println(Datasource.getInstance().textFormatById(1));
            System.out.println(Datasource.getInstance().textStyleById(2));
            System.out.println(Datasource.getInstance().anchorById(4));
            System.out.println(Datasource.getInstance().lineStyleById(2));
            System.out.println("*********");
            System.out.println(Datasource.getInstance().elementById(1));
            Element address_field_appearance = Datasource.getInstance().elementByName("address_field");

            System.out.println(address_field_appearance.getLineStyle().getLineCap().getName());
            System.out.println(address_field_appearance.getAnchor().getValue());
            System.out.println(address_field_appearance.getWidth().getValue());
            Address address_content = Datasource.getInstance().addressByLabel("dummy_address");
            System.out.println(address_content.getEMailAddress());
            Enclosure enclosure_content = Datasource.getInstance().enclosureByName("dummy_certificate");
            System.out.println(enclosure_content.getFileId());
            Line line = Datasource.getInstance().lineById(1);
            System.out.println(line.getLength());

        } finally {
            Datasource.getInstance().close();
        }

    }
}
