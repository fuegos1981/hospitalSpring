package com.fuegos1981.hospitalSpring.model;

/**
 * This interface is intended  for all models that have only one single field Name (category, diagnosis, role).
 * @author Sinkevych Olena
 *
 */
public interface SimpleModel {
    void setId(int id);
    int getId();
    void setName(String name);
    String getName();

    static SimpleModel getSimpleInstance(String classNameParam){
        switch (classNameParam){
            case "Category": return new Category();
            case "Diagnosis": return  new Diagnosis();
            default: return null;
        }
        /*
        try {
            Class<?> clazz = Class.forName("com.fuegos1981.hospitalSpring.model."+classNameParam);
            //System.out.println("!!!!!!!!!!!!MY"+clazz.getName());
            return  (SimpleModel) clazz.getConstructor().newInstance();
        }
        catch( Exception e ) {
            return null;
        }

         */
    }
}
