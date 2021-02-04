package model;

public class InHouse extends Part{

    /** If the InHouse part is added then this private field will be use to set the machineId's data. */
    private int machineId;

    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /** Returns enter machineId
     * @return Returns the machine id of part. */
    public int getMachineId() {
        return machineId;
    }

    /** Setter to update machineId
        @param machineId Updated machineId. */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

    /** This method checks each field to make sure they fall within the logical parameters of the project.
     @param name Name of part.
     @param price Price of part.
     @param stock Available amount of part.
     @param min Minimum amount allowed.
     @param max Maximum amount allowed.
     @return Returns either empty string or list of errors.
     */
    public static String inHouseLogicCheck(String name, double price, int stock, int min, int max){

        String thingsToFix = "";
        if(name.isEmpty()){
            thingsToFix += "Must enter a part name.\n";
        }
        if(price < 0){
            thingsToFix += "Price must have a value at $0 or more.\n";
        }
        if (stock < min || stock > max){
            thingsToFix += "Stock amount must be between Minimum amount and Max amount.\n";
        }
        if (min >= max || min < 0){
            thingsToFix += "Minimum value must be less than max but not lower than 0.\n";
        }
        if (max <= min){
            thingsToFix += "Max amount must be greater than Minimum amount.\n";
        }
        return thingsToFix;
    }
}

