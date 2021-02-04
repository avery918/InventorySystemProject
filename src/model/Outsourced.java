package model;


public class Outsourced extends Part{

    /** If the Outsourced part is added then this private field will be use to set the company's name. */
    private String companyName;

    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /** getter to return company's name.
     * @return Returns the company's name. */
    public String getCompanyName() {
        return companyName;
    }

    /** Setter to update company's name.
     * @param companyName Updates company's name.  */
    public void setCompanyName(String companyName ) {
        this.companyName = companyName;
    }

    /** This method checks each field to make sure they fall within the logical parameters of the project.
     @param name Name of part.
     @param price Price of part.
     @param stock Available amount of part.
     @param min Minimum amount allowed.
     @param max Maximum amount allowed.
     @param companyName Company supplying the part.
     @return Returns either empty string or list of errors.
     */
    public static String logicCheck(String name, double price, int stock, int min, int max, String companyName){

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
        if (companyName.isEmpty()){
            thingsToFix += "Must enter a company Name.\n";
        }
        return thingsToFix;
    }
}