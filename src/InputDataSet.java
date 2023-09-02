public class InputDataSet {

    private String buying;
    private String maint;
    private String doors;
    private String persons;
    private String lug_boot;
    private String safety;
    private String classValues;

    public InputDataSet(String buying, String maint, String doors, String persons, String lug_boot, String safety, String classValues) {
        this.buying = buying;
        this.maint = maint;
        this.doors = doors;
        this.persons = persons;
        this.lug_boot = lug_boot;
        this.safety = safety;
        this.classValues = classValues;
    }

    public String getBuying() {
        return buying;
    }

    public void setBuying(String buying) {
        this.buying = buying;
    }

    public String getMaint() {
        return maint;
    }

    public void setMaint(String maint) {
        this.maint = maint;
    }

    public String getDoors() {
        return doors;
    }

    public void setDoors(String doors) {
        this.doors = doors;
    }

    public String getPersons() {
        return persons;
    }

    public void setPersons(String persons) {
        this.persons = persons;
    }

    public String getLug_boot() {
        return lug_boot;
    }

    public void setLug_boot(String lug_boot) {
        this.lug_boot = lug_boot;
    }

    public String getSafety() {
        return safety;
    }

    public void setSafety(String safety) {
        this.safety = safety;
    }

    public String getClassValues() {
        return classValues;
    }

    public void setClassValues(String classValues) {
        this.classValues = classValues;
    }
}
