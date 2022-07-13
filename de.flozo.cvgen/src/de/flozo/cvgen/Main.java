package de.flozo.cvgen;

import de.flozo.common.appearance.*;
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
            System.out.println(address_content.getFirstName() + " " + address_content.getLastName());
            Enclosure enclosure_content = Datasource.getInstance().enclosureByName("dummy_certificate");
            System.out.println(enclosure_content.getFileId());
            Line line = Datasource.getInstance().lineById(1);
            Address newAddress = new Address(0, "entry_name", "Dr", "Peter", "James", "Miller", "Main street", "10b", "98765", "Heretown", "Otherland", "0987654321", "24680", "my.mail@address.org", "");
//            Datasource.getInstance().insertAddress(newAddress);

            LengthUnit lengthUnit = Datasource.getInstance().lengthUnitById(4);
            System.out.println(lengthUnit);
            Length length = new Length(1, "new length", 123, lengthUnit);
            System.out.println(length);
//            Datasource.getInstance().insertLength(length);
            Position position = new Position(0, "new position", length, length);
            System.out.println(position);
            Datasource.getInstance().insertPosition(position);
            System.out.println(Datasource.getInstance().positionById(20));


        } finally {
            Datasource.getInstance().close();
        }

    }
}
