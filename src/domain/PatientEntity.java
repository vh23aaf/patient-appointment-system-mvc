package domain;

import java.util.LinkedList;
import java.util.List;

/**
 * PatientEntity representing system patients.
 * @author Vishal Hirani
 */
public class PatientEntity extends UserEntity {
    private String nhsNum;
    private String dob;
    private String addr;
    private String pcode;
    private String sex;
    private String emergName;
    private String emergPhone;
    private String regDate;
    private String gpSurgId;
    
    private List<AppointmentEntity> appts;
    private List<MedicalRecordEntity> records;
    
    public PatientEntity() {
        super();
        this.nhsNum = "";
        this.dob = "";
        this.addr = "";
        this.pcode = "";
        this.sex = "";
        this.emergName = "";
        this.emergPhone = "";
        this.regDate = "";
        this.gpSurgId = "";
        this.appts = new LinkedList<>();
        this.records = new LinkedList<>();
    }
    
    public PatientEntity(String id, String fName, String lName, String dob,
                        String nhsNum, String sex, String contact, String mail,
                        String addr, String pcode, String emergName,
                        String emergPhone, String regDate, String gpSurgId) {
        super(id, "", "", fName, lName, contact, "Patient", mail);
        this.nhsNum = nhsNum;
        this.dob = dob;
        this.addr = addr;
        this.pcode = pcode;
        this.sex = sex;
        this.emergName = emergName;
        this.emergPhone = emergPhone;
        this.regDate = regDate;
        this.gpSurgId = gpSurgId;
        this.appts = new LinkedList<>();
        this.records = new LinkedList<>();
    }
    
    // Accessors
    public String getNhsNum() { return nhsNum; }
    public String getDob() { return dob; }
    public String getAddr() { return addr; }
    public String getPcode() { return pcode; }
    public String getSex() { return sex; }
    public String getEmergName() { return emergName; }
    public String getEmergPhone() { return emergPhone; }
    public String getRegDate() { return regDate; }
    public String getGpSurgId() { return gpSurgId; }
    public List<AppointmentEntity> getAppts() { return appts; }
    public List<MedicalRecordEntity> getRecords() { return records; }
    
    // Mutators
    public void setNhsNum(String nhsNum) { this.nhsNum = nhsNum; }
    public void setDob(String dob) { this.dob = dob; }
    public void setAddr(String addr) { this.addr = addr; }
    public void setPcode(String pcode) { this.pcode = pcode; }
    public void setSex(String sex) { this.sex = sex; }
    public void setEmergName(String name) { this.emergName = name; }
    public void setEmergPhone(String phone) { this.emergPhone = phone; }
    public void setRegDate(String date) { this.regDate = date; }
    public void setGpSurgId(String id) { this.gpSurgId = id; }
    
    public boolean scheduleAppt(AppointmentEntity appt) {
        if (appt != null) {
            appts.add(appt);
            return true;
        }
        return false;
    }
    
    public boolean modifyAppt(String apptId, AppointmentEntity newAppt) {
        for (int i = 0; i < appts.size(); i++) {
            if (appts.get(i).getApptId().equals(apptId)) {
                appts.set(i, newAppt);
                return true;
            }
        }
        return false;
    }
    
    public boolean removeAppt(String apptId) {
        for (int i = 0; i < appts.size(); i++) {
            if (appts.get(i).getApptId().equals(apptId)) {
                appts.get(i).setApptStatus("Cancelled");
                return true;
            }
        }
        return false;
    }
    
    public List<MedicalRecordEntity> fetchRecords() {
        return new LinkedList<>(records);
    }
    
    public void addRecord(MedicalRecordEntity rec) {
        if (rec != null) records.add(rec);
    }
    
    @Override
    public boolean verifyCredentials() {
        return !id.isEmpty() && !fName.isEmpty();
    }
    
    @Override
    public String toString() {
        return "Patient ID: " + id + ", Name: " + getCompleteName() + 
               ", NHS: " + nhsNum + ", DOB: " + dob + ", Sex: " + sex;
    }
    
    public String formatAsCSV() {
        return id + "," + fName + "," + lName + "," + dob + "," +
               nhsNum + "," + sex + "," + contact + "," + mail + "," +
               "\"" + addr + "\"," + pcode + "," + emergName + "," +
               emergPhone + "," + regDate + "," + gpSurgId;
    }
}
