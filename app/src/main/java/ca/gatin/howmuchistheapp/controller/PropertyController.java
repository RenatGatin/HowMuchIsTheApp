package ca.gatin.howmuchistheapp.controller;

import android.content.Context;

import java.util.List;

import ca.gatin.howmuchistheapp.dataAccess.DBObjectDataSource;
import ca.gatin.howmuchistheapp.model.Property;

/**
 * @author RGatin
 * @since 12-Oct-2015
 */
public class PropertyController {

    DBObjectDataSource<Property> dboProperty = null;
    private List<Property> properties;

    public PropertyController(Context context){
        dboProperty = new DBObjectDataSource<Property>(context);
    }

    public String getValue(String propertyName){
        getPropertiesFromDB();

        Property property = null;
        for(Property prop : properties){
            if(prop.getKey().equals(propertyName))
                property = prop;
        }
        if(property == null){
            property = new Property(propertyName,null);
        }

        return property.getValue();
    }

    public void setValue(String propertyName, String propertyValue){
        getPropertiesFromDB();

        Property property = null;
        for(Property prop : properties){
            if(prop.getKey().equals(propertyName))
                property = prop;
        }
        if(property == null){
            property = new Property(propertyName,propertyValue);
            properties.add(property);
        }
        else
            property.setValue(propertyValue);

        dboProperty.insertUpdate(property);
    }

    public void delete(String propertyName) {
        Property property = null;

        getPropertiesFromDB();

        for (Property prop : properties) {
            if (prop.getKey().equals(propertyName))
                property = prop;
        }

        if(property != null)
            dboProperty.deleteObject(property);
    }

    private void getPropertiesFromDB(){
        Property filter = new Property();
        properties = dboProperty.getObjects(filter);
    }
}